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

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 16/09/2017
 */
public class ByteBufferOutputStream extends OutputStream {
  
  public static final IntFunction<ByteBuffer> ALLOC_POLICY_DIRECT = ByteBuffer::allocateDirect;
  
  public static final IntFunction<ByteBuffer> ALLOC_POLICY_HEAP = ByteBuffer::allocate;
  
  public static final int DEFAULT_INITIAL_SIZE = 16 * 1024;
  
  
  private final List<ByteBuffer> buffers;
  
  private final int bufsize;
  
  private final IntFunction<ByteBuffer> alloc;
  
  private int current;
  
  
  public ByteBufferOutputStream(int initialSize, IntFunction<ByteBuffer> allocPolicy) {
    if(initialSize < 32) {
      throw new IllegalArgumentException("Bad initial buffer size: "+ initialSize);
    }
    if(allocPolicy == null) {
      throw new IllegalArgumentException("Bad null allocation policy function");
    }
    this.bufsize = initialSize;
    this.alloc = allocPolicy;
    this.buffers = new ArrayList<>();
    this.current = 0;
    this.buffers.add(alloc.apply(bufsize));
  }
  
  
  public ByteBufferOutputStream(int initialSize) {
    this(initialSize, ALLOC_POLICY_HEAP);
  }
  
  
  public ByteBufferOutputStream(IntFunction<ByteBuffer> allocPolicy) {
    this(DEFAULT_INITIAL_SIZE, allocPolicy);
  }
  
  
  public ByteBufferOutputStream() {
    this(DEFAULT_INITIAL_SIZE, ALLOC_POLICY_HEAP);
  }
  
  
  public int size() {
    return this.buffers.stream()
        .mapToInt(ByteBuffer::position)
        .reduce(0, (i,s)->i+s);
  }
  
  
  public int pages() {
    return this.buffers.size();
  }
  
  
  private ByteBuffer getBuffer(int writeSize) {
    if(this.buffers.get(current).remaining() < writeSize
        && (this.buffers.size() < current + 1 
        ||  this.buffers.get(current + 1).remaining() < writeSize)) {
      this.buffers.set(++current, alloc.apply(
          Math.max(bufsize, writeSize))
      );
    }
    return this.buffers.get(current);
  }
  
  
  @Override
  public void write(int b) {
    this.getBuffer(1).put((byte) b);
  }
  
  
  @Override
  public void write(byte[] bs, int off, int len) {
    this.getBuffer(len).put(bs, off, len);
  }
  
  
  @Override
  public void write(byte[] bs) {
    this.write(bs, 0, bs.length);
  }
  
  
  public void write(ByteBuffer buf) {
    this.getBuffer(buf.remaining()).put(buf);
  }
  
  
  public ByteBufferOutputStream clear() {
    buffers.forEach(ByteBuffer::clear);
    current = 0;
    return this;
  }
  
  
  public ByteBuffer toByteBuffer(IntFunction<ByteBuffer> allocPolicy) {
    this.buffers.forEach(ByteBuffer::flip);
    int size = this.buffers.stream()
        .mapToInt(ByteBuffer::remaining)
        .reduce(0, (i,s)->i+s);
    ByteBuffer buf = allocPolicy.apply(size+1);
    this.buffers.forEach(buf::put);
    buf.flip();
    return buf;
  }
  
  
  public ByteBuffer toByteBuffer() {
    return this.toByteBuffer(this.alloc);
  }

  
  public byte[] toByteArray() {
    this.buffers.forEach(ByteBuffer::flip);
    int size = this.buffers.stream()
        .mapToInt(ByteBuffer::remaining)
        .reduce(0, (i,s)->i+s);
    byte[] buf = new byte[size];
    int idx = 0;
    for(ByteBuffer b : buffers) {
      int rem = b.remaining();
      b.get(buf, idx, rem);
      idx += rem;
    }
    return buf;
  }
  
}
