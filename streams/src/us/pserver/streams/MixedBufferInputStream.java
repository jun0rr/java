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

package us.pserver.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 09/04/2015
 */
public class MixedBufferInputStream extends InputStream {
  
  private MixedReadBuffer buffer;
  
  
  public MixedBufferInputStream(MixedReadBuffer buf) {
    if(buf == null) throw new IllegalArgumentException(
        "[MixedBufferInputStream( MixedReadBuffer )] Invalid MixedReadBuffer: '"+ buf+ "'");
    buffer = buf;
  }
  
  
  @Override
  public int available() throws IOException {
    return (int) buffer.length();
  }
  
  
  @Override
  public void close() {}
  
  
  @Override
  public void mark(int readLimit) {
    try { buffer.mark(); }
    catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  
  @Override
  public boolean markSupported() {
    return true;
  }
  
  
  @Override
  public int read() throws IOException {
    return buffer.read();
  }
  
  
  @Override
  public int read(byte[] bs) throws IOException {
    return buffer.read(bs);
  }
  
  
  @Override
  public int read(byte[] bs, int off, int len) throws IOException {
    return buffer.read(bs, off, len);
  }
  
  
  @Override
  public void reset() {
    try { buffer.reset(); }
    catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  
  @Override
  public long skip(long skip) throws IOException {
    if(skip <= 0) return 0;
    if(skip > buffer.length() - buffer.index())
      skip = buffer.length() - buffer.index();
    buffer.seek(buffer.index() + skip);
    return skip;
  }

}