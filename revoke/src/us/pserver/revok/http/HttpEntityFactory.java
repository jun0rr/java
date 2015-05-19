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

package us.pserver.revok.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.util.EntityUtils;
import us.pserver.cdr.StringByteConverter;
import us.pserver.cdr.crypt.CryptKey;
import us.pserver.revok.protocol.JsonSerializer;
import us.pserver.revok.protocol.ObjectSerializer;
import us.pserver.revok.protocol.XmlSerializer;
import us.pserver.streams.IO;
import us.pserver.streams.MixedWriteBuffer;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 08/04/2015
 */
public class HttpEntityFactory {

  public static final ContentType 
      TYPE_X_JAVA_ROB = ContentType.create(
          "application/x-java-rob", Consts.UTF_8);
  
  
  private MixedWriteBuffer buffer;
  
  private final StringByteConverter scv;
  
  private ContentType type;
  
  private CryptKey key;
  
  private Object obj;
  
  private InputStream input;
  
  private ObjectSerializer serial;
  
  
  public HttpEntityFactory(ContentType type) {
    if(type == null)
      type = TYPE_X_JAVA_ROB;
    this.type = type;
    buffer = new MixedWriteBuffer();
    scv = new StringByteConverter();
    key = null;
    obj = null;
    input = null;
    serial = new JsonSerializer();
  }
  
  
  public HttpEntityFactory(ContentType type, ObjectSerializer os) {
    this(type);
    if(os == null) os = new JsonSerializer();
    serial = os;
  }
  
  
  public HttpEntityFactory(ObjectSerializer os) {
    this(TYPE_X_JAVA_ROB);
    if(os == null) os = new JsonSerializer();
    serial = os;
  }
  
  
  public HttpEntityFactory() {
    this(TYPE_X_JAVA_ROB);
  }
  
  
  public static HttpEntityFactory instance(ContentType type) {
    return new HttpEntityFactory(type);
  }
  
  
  public static HttpEntityFactory instance(ContentType type, ObjectSerializer os) {
    return new HttpEntityFactory(type, os);
  }
  
  
  public static HttpEntityFactory instance(ObjectSerializer os) {
    return new HttpEntityFactory(os);
  }
  
  
  public static HttpEntityFactory instance() {
    return new HttpEntityFactory();
  }
  
  
  public ObjectSerializer getObjectSerializer() {
    return serial;
  }
  
  
  public HttpEntityFactory setObjectSerializer(ObjectSerializer serializer) {
    if(serializer != null) {
      serial = serializer;
    }
    return this;
  }
  
  
  public HttpEntityFactory enableCryptCoder(CryptKey key) {
    if(key != null) {
      buffer.getCoderFactory().setCryptCoderEnabled(true, key);
      this.key = key;
    }
    return this;
  }
  
  
  public HttpEntityFactory disableAllCoders() {
    buffer.getCoderFactory().clearCoders();
    return this;
  }
  
  
  public HttpEntityFactory disableCryptCoder() {
    buffer.getCoderFactory().setCryptCoderEnabled(false, null);
    return this;
  }
  
  
  public HttpEntityFactory enableGZipCoder() {
    buffer.getCoderFactory().setGZipCoderEnabled(true);
    return this;
  }
  
  
  public HttpEntityFactory disableGZipCoder() {
    buffer.getCoderFactory().setGZipCoderEnabled(false);
    return this;
  }
  
  
  public HttpEntityFactory enableBase64Coder() {
    buffer.getCoderFactory().setBase64CoderEnabled(true);
    return this;
  }
  
  
  public HttpEntityFactory disableBase64Coder() {
    buffer.getCoderFactory().setBase64CoderEnabled(false);
    return this;
  }
  
  
  public HttpEntityFactory put(CryptKey key) {
    if(key != null) {
      this.key = key;
    }
    return this;
  }
  
  
  public HttpEntityFactory put(Object obj) {
    if(obj != null) {
      this.obj = obj;
    }
    return this;
  }
  
  
  public HttpEntityFactory put(InputStream is) {
    if(is != null) {
      this.input = is;
    }
    return this;
  }
  
  
  public HttpEntity create() throws IOException {
    if(key == null && obj == null && input == null)
      return null;
    
    buffer.clear();
    buffer.write(scv.convert(XmlConsts.START_XML));
    // Encoded OutputStream
    OutputStream os = buffer.getOutputStream();
    
    if(key != null) {
      // write plain data
      buffer.write(scv.convert(XmlConsts.START_CRYPT_KEY));
      buffer.write(scv.convert(key.toString()));
      buffer.write(scv.convert(XmlConsts.END_CRYPT_KEY));
    }
    if(obj != null || input != null) {
      buffer.write(scv.convert(XmlConsts.START_CONTENT));
    }
    if(obj != null) {
      os.write(scv.convert(XmlConsts.START_ROB));
      os.write(serial.toBytes(obj));
      os.write(scv.convert(XmlConsts.END_ROB));
      os.flush();
    }
    if(input != null) {
      os.write(scv.convert(XmlConsts.START_STREAM));
      IO.tr(input, os);
      os.write(scv.convert(XmlConsts.END_STREAM));
      os.flush();
    }
    if(obj != null || input != null) {
      os.write(scv.convert(XmlConsts.END_CONTENT));
      os.flush();
    }
    os.write(scv.convert(XmlConsts.END_XML));
    os.flush();
    os.close();
    
    InputStream istream = buffer.getReadBuffer().getRawInputStream();
    return new InputStreamEntity(istream, istream.available(), type);
  }
  
  
  public static void main(String[] args) throws IOException {
    HttpEntityFactory fac = HttpEntityFactory.instance(new XmlSerializer());
        //.enableGZipCoder()
        //.enableCryptCoder(
          //  CryptKey.createRandomKey(CryptAlgorithm.AES_CBC_PKCS5));
    class MSG {
      String str;
      public MSG(String s) { str = s; }
      public String toString() { return "MSG{str="+ str+ "}"; }
    }
    fac.put(new MSG("Hello EntityFactory!"));
    HttpEntity ent = fac.create();
    ent.writeTo(System.out);
    System.out.println();
    
    ent = fac.create();
    HttpEntityParser ep = HttpEntityParser.instance(new XmlSerializer());//.enableGZipCoder();
    ep.parse(ent);
    System.out.println("* key: "+ ep.getCryptKey());
    System.out.println("* rob: "+ ep.getObject());
    EntityUtils.consume(ent);
  }
  
}