/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca � software livre; voc� pode redistribu�-la e/ou modific�-la sob os
 * termos da Licen�a P�blica Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a vers�o 2.1 da Licen�a, ou qualquer
 * vers�o posterior.
 * 
 * Esta biblioteca � distribu�da na expectativa de que seja �til, por�m, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia impl�cita de COMERCIABILIDADE
 * OU ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a Licen�a P�blica
 * Geral Menor do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor do GNU junto
 * com esta biblioteca; se n�o, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endere�o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 */

package us.pserver.tools.io;

import java.io.InputStream;
import java.nio.ByteBuffer;
import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 16/09/2017
 */
public class ByteBufferInputStream extends InputStream {

  private final ByteBuffer buffer;
  
  
  public ByteBufferInputStream(ByteBuffer buf) {
    this.buffer = NotNull.of(buf).getOrFail("Bad null buffer");
    if(buffer.remaining() < 1) {
      throw new IllegalArgumentException("No remaining bytes to read");
    }
  }
  
  
  public int size() {
    return this.buffer.remaining();
  }


  @Override
  public int read() {
    return this.size() < 1 ? -1 : this.buffer.get();
  }
  
  
  @Override
  public int read(byte[] bs, int off, int len) {
    int size = Math.min(len, this.size());
    if(size < 1) {
      return -1;
    }
    this.buffer.get(bs, off, size);
    return size;
  }
  
  
  @Override
  public int read(byte[] bs) {
    return this.read(bs, 0, bs.length);
  }
  
}
