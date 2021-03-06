/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca é software livre; você pode redistribuí-la e/ou modificá-la sob os
 * termos da Licença Pública Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a versão 2.1 da Licença, ou qualquer
 * versão posterior.
 * 
 * Esta biblioteca é distribuída na expectativa de que seja útil, porém, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE
 * OU ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública
 * Geral Menor do GNU para mais detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral Menor do GNU junto
 * com esta biblioteca; se não, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endereço 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 */

package us.pserver.rob.channel;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import us.pserver.cdr.StringByteConverter;
import us.pserver.cdr.b64.Base64StringCoder;
import us.pserver.cdr.crypt.CryptAlgorithm;
import us.pserver.cdr.crypt.CryptKey;
import static us.pserver.chk.Checker.nullarg;
import us.pserver.http.HttpConst;
import us.pserver.streams.IO;
import us.pserver.streams.MultiCoderBuffer;
import us.pserver.streams.ProtectedInputStream;
import us.pserver.streams.ProtectedOutputStream;
import us.pserver.streams.StreamCoderFactory;
import us.pserver.streams.StreamResult;
import us.pserver.streams.StreamUtils;
import static us.pserver.streams.StreamUtils.EOF;


/**
 * Implementa um canal de transmissão de objetos em rede
 * no formato XML, utilizando <code>XStream</code>.
 * 
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 21/01/2014
 */
public class XmlNetChannel implements Channel {
  
  private Socket sock;
  
  private XStream xst;
  
  private boolean isvalid;
  
  private CryptKey key;
  
  private Base64StringCoder bcd;
  
  private StringByteConverter scv;
  
  private MultiCoderBuffer buffer;
  
  
  /**
   * Construtor padrão que recebe um 
   * <code>Socket</code> para comunicação em rede.
   * @param sc <code>Socket</code>.
   */
  public XmlNetChannel(Socket sc) {
    if(sc == null || sc.isClosed())
      throw new IllegalArgumentException(
          "Invalid Socket ["+ sc+ "]");
    sock = sc;
    isvalid = true;
    buffer = null;
    xst = new XStream();
    scv = new StringByteConverter();
    bcd = new Base64StringCoder();
    key = CryptKey.createRandomKey(CryptAlgorithm.AES_CBC_PKCS5);
  }
  
  
  /**
   * Retorna o <code>Socket</code>.
   * @return <code>Socket</code>.
   */
  public Socket getSocket() {
    return sock;
  }
  
  
  private void writeKey(OutputStream os) throws IOException {
    nullarg(OutputStream.class, os);
    
    StringBuffer sb = new StringBuffer();
    sb.append(HttpConst.BOUNDARY_XML_START)
        .append(HttpConst.BOUNDARY_CRYPT_KEY_START)
        .append(bcd.encode(key.toString()))
        .append(HttpConst.BOUNDARY_CRYPT_KEY_END);
    os.write(scv.convert(sb.toString()));
    os.flush();
  }
  
  
  private void writeTransport(Transport t, OutputStream os) throws IOException {
    nullarg(Transport.class, t);
    nullarg(OutputStream.class, os);
    
    try {
      os.write(scv.convert(HttpConst.BOUNDARY_OBJECT_START));
      xst.toXML(t.getWriteVersion(), os);
      os.write(scv.convert(HttpConst.BOUNDARY_OBJECT_END));
      
      if(t.hasContentEmbedded()) {
        os.write(scv.convert(HttpConst.BOUNDARY_CONTENT_START));
        IO.tr(t.getInputStream(), os);
        os.write(scv.convert(HttpConst.BOUNDARY_CONTENT_END));
      }
      os.flush();
    } 
    catch(IOException e) {
      throw e;
    } 
    catch(Exception e) {
      throw new IOException(e.toString(), e);
    }
  }
  
  
  @Override
  public void write(Transport trp) throws IOException {
    if(trp == null) return;
    if(buffer != null) buffer.close();
    ProtectedOutputStream pos = new ProtectedOutputStream(
        sock.getOutputStream());
    
    writeKey(pos);
      
    OutputStream sout = StreamCoderFactory.getNew()
        .setGZipCoderEnabled(true)
        .setCryptCoderEnabled(true, key)
        .create(pos);
    writeTransport(trp, sout);
    sout.write(scv.convert(HttpConst.BOUNDARY_XML_END));
    sout.flush();
    sout.close();
      
    StreamUtils.writeEOF(pos);
    pos.flush();
  }
  
  
  private CryptKey readKey(InputStream is) throws IOException {
    nullarg(InputStream.class, is);
    String startkey = HttpConst.BOUNDARY_CRYPT_KEY_START;
    StreamResult sr = StreamUtils.readUntilOr(is, startkey, EOF);
    if(sr.isEOFReached() || sr.token() != startkey) 
      return null;
    sr = StreamUtils.readStringUntilOr(is, 
        HttpConst.BOUNDARY_CRYPT_KEY_END, EOF);
    return CryptKey.fromString(bcd.decode(sr.content()));
  }
  
  
  private InputStream readContent(InputStream is) throws IOException {
    nullarg(InputStream.class, is);
    if(buffer != null) buffer.close();
    buffer = new MultiCoderBuffer();
    StreamUtils.transferBetween(is, buffer.getOutputStream(), 
        HttpConst.BOUNDARY_CONTENT_START, 
        HttpConst.BOUNDARY_CONTENT_END);
    return buffer.flip().getInputStream();
  }
  
  
  private Transport readTransport(InputStream is) throws IOException {
    nullarg(InputStream.class, is);
    
    try {
      StreamResult sr = StreamUtils.readBetween(is, 
          HttpConst.BOUNDARY_OBJECT_START, 
          HttpConst.BOUNDARY_OBJECT_END);
      
      Transport t = (Transport) xst.fromXML(sr.content());
      if(t == null)
        throw new IOException("Can not read Transport ["+ t+ "]");
      if(t.hasContentEmbedded())
        t.setInputStream(readContent(is));
      
      return t;
    }
    catch(IOException e) {
      throw e;
    } 
    catch(Exception e) {
      throw new IOException(e.toString(), e);
    }
  }
  
  
  
  @Override
  public Transport read() throws IOException {
    if(buffer != null) buffer.close();
    ProtectedInputStream pin = new ProtectedInputStream(
        sock.getInputStream());
    key = readKey(pin);
    if(key == null) return null;
    
    InputStream sin = StreamCoderFactory.getNew()
        .setGZipCoderEnabled(true)
        .setCryptCoderEnabled(true, key)
        .create(pin);
      
    Transport t = readTransport(sin);
    sin.close();
    return t;
  }
  
  
  /**
   * <code>XmlNetChannel</code> é válido 
   * até que seja explicitamente fechado
   * através do método <code>close()</code>.
   * Pode ser utilizado entre diversos ciclos de
   * leitura e escrita.
   * @return <code>true</code> se não tiver sido 
   * fechado através do método <code>close()</code>.
   */
  @Override
  public boolean isValid() {
    return isvalid;
  }
  
  
  @Override
  public void close() {
    try {
      if(sock != null) {
        sock.shutdownInput();
        sock.shutdownOutput();
        sock.close();
      }
      if(buffer != null) buffer.close();
      isvalid = false;
    } catch(IOException e) {}
  }
  
}
