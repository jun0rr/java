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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.LinkedList;
import java.util.Queue;
import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 01/11/2017
 */
public class BufferQueueChannel {

  private final Queue<ByteBuffer> queue;
  
  private final SeekableByteChannel channel;
  
  
  public BufferQueueChannel(SeekableByteChannel channel) {
    this(channel, new LinkedList<>());
  }
  
  
  public BufferQueueChannel(SeekableByteChannel channel, Queue<ByteBuffer> queue) {
    this.channel = NotNull.of(channel).getOrFail("Bad null SeekableByteChannel");
    this.queue = NotNull.of(queue).getOrFail("Bad null Queue");
  }
  
  
  public BufferQueueChannel add(ByteBuffer buf) {
    if(buf != null && buf.hasRemaining()) {
      queue.add(buf);
    }
    return this;
  }
  
  
  public int size() {
    return queue.size();
  }
  
  
  public BufferQueueChannel clear() {
    queue.clear();
    return this;
  }
  
  
  public long write() throws IOException {
    return 0;
  }
  
}
