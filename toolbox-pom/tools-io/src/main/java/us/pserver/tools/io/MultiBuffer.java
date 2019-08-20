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

package us.pserver.tools.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import us.pserver.tools.Indexed;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 04/12/2018
 */
public final class MultiBuffer implements BitBuffer {
  
  private final List<ByteBuffer> buffers;
  
  private final Supplier<ByteBuffer> bufferSource;
  
  private int position, limit, mark;
  
  private final int unitSize;
  
  private final ByteBuffer temp;
  
  
  private MultiBuffer(List<ByteBuffer> buffers, Supplier<ByteBuffer> bufferSource, ByteBuffer buffer, int position, int limit, int mark) {
    this.bufferSource = bufferSource;
    this.buffers = buffers;
    this.temp = buffer;
    this.position = position;
    this.limit = limit;
    this.mark = mark;
    this.unitSize = buffers.get(0).capacity();
  }
  
  public MultiBuffer(Supplier<ByteBuffer> bufferSource) {
    this.bufferSource = Objects.requireNonNull(bufferSource);
    this.buffers = new ArrayList<>();
    ByteBuffer buf = bufferSource.get().clear();
    this.unitSize = buf.capacity();
    buffers.add(buf);
    this.position = 0;
    this.limit = buf.limit();
    this.mark = position;
    this.temp = bufferSource.get();
  }
  
  private int index() {
    return Math.min(buffers.size() -1, position / unitSize);
  }
  
  private int limitIndex() {
    return Math.min(buffers.size() -1, limit / unitSize);
  }
  
  private ByteBuffer current() {
    return buffers.get(index());
  }
  
  private int currentpos() {
    return position - index() * unitSize;
  }
  
  private int currentlim() {
    return limit - limitIndex() * unitSize;
  }
  
  private ByteBuffer setupCurrent() {
    ByteBuffer b = current();
    b.clear().position(currentpos());
    if(limitIndex() == index()) b.limit(currentlim());
    return b;
  }
  
  public BitBuffer compact() {
    throw new UnsupportedOperationException();
  }
  
  public int position() {
    return position;
  }
  
  public BitBuffer position(int pos) {
    if(pos < 0) throw new IllegalArgumentException("Bad position: " + pos);
    ensureSize(pos);
    position = pos;
    return this;
  }
  
  public int remaining() {
    return limit - position;
  }
  
  public boolean hasRemaining() {
    return remaining() > 0;
  }
  
  public BitBuffer mark() {
    this.mark = position;
    return this;
  }
  
  public BitBuffer reset() {
    position = mark;
    return this;
  }
  
  public BitBuffer rewind() {
    position = mark = 0;
    return this;
  }
  
  public int capacity() {
    return buffers.size() * unitSize;
  }
  
  public int limit() {
    return limit;
  }
  
  public BitBuffer limit(int lim) {
    if(limit < position) throw new IllegalArgumentException("Bad limit (limit < position): " + lim);
    ensureSize(lim);
    limit = lim;
    return this;
  }
  
  private void ensureSize(int size) {
    while(size > capacity()) {
      buffers.add(bufferSource.get());
    }
    limit = capacity();
  }
  
  public byte get() {
    if(!hasRemaining()) {
      throw new BufferUnderflowException();
    }
    byte b = setupCurrent().get();
    position++;
    return b;
  }

  public byte get(int index) {
    position(index);
    ByteBuffer cur = setupCurrent();
    byte b = cur.get();
    position++;
    return b;
  }
  
  public BitBuffer get(byte[] dst, int offset, int length) {
    if(remaining() < length) {
      throw new BufferUnderflowException();
    }
    int total = length;
    int off = offset;
    while(total > 0) {
      ByteBuffer cur = setupCurrent();
      int len = Math.min(cur.remaining(), total);
      cur.get(dst, off, len);
      position += len;
      off += len;
      total -= len;
    }
    return this;
  }
  
  public BitBuffer get(ByteBuffer buf) {
    int total = Math.min(this.remaining(), buf.remaining());
    while(total > 0) {
      ByteBuffer cur = setupCurrent();
      int len = Math.min(cur.remaining(), total);
      cur.limit(cur.position() + len);
      buf.put(cur);
      total -= len;
      position += len;
    }
    return this;
  }

  public BitBuffer get(BitBuffer buf) {
    int total = Math.min(this.remaining(), buf.remaining());
    while(total > 0) {
      ByteBuffer cur = setupCurrent();
      int len = Math.min(cur.remaining(), total);
      cur.limit(cur.position() + len);
      buf.put(cur);
      total -= len;
      position += len;
    }
    return this;
  }
  
  public char getChar() {
    if(remaining() < Character.BYTES) {
      throw new BufferUnderflowException();
    }
    temp.clear().limit(Character.BYTES);
    get(temp);
    temp.flip();
    return temp.getChar();
  }

  public char getChar(int index) {
    if(index >= limit() - Character.BYTES) {
      throw new BufferUnderflowException();
    }
    position(index);
    return getChar();
  }

  public double getDouble() {
    if(remaining() < Double.BYTES) {
      throw new BufferUnderflowException();
    }
    temp.clear().limit(Double.BYTES);
    get(temp);
    temp.flip();
    return temp.getDouble();
  }

  public double getDouble(int index) {
    if(index >= limit() - Double.BYTES) {
      throw new BufferUnderflowException();
    }
    position = index;
    return getDouble();
  }

  public float getFloat() {
    if(remaining() < Float.BYTES) {
      throw new BufferUnderflowException();
    }
    temp.clear().limit(Float.BYTES);
    get(temp);
    temp.flip();
    return temp.getFloat();
  }

  public float getFloat(int index) {
    if(index >= limit() - Float.BYTES) {
      throw new BufferUnderflowException();
    }
    position = index;
    return getFloat();
  }

  public int getInt() {
    if(remaining() < Integer.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer cur = setupCurrent();
    if(cur.remaining() >= Integer.BYTES) {
      position += Integer.BYTES;
      return cur.getInt();
    }
    else {
      temp.clear().limit(Integer.BYTES);
      get(temp);
      temp.flip();
      return temp.getInt();
    }
  }

  public int getInt(int index) {
    if(index >= limit() - Integer.BYTES) {
      throw new BufferUnderflowException();
    }
    position = index;
    return getInt();
  }

  public long getLong() {
    if(remaining() < Long.BYTES) {
      throw new BufferUnderflowException();
    }
    temp.clear().limit(Long.BYTES);
    get(temp);
    temp.flip();
    return temp.getLong();
  }

  public long getLong(int index) {
    if(index >= limit() - Long.BYTES) {
      throw new BufferUnderflowException();
    }
    position = index;
    return getLong();
  }

  public short getShort() {
    if(remaining() < Short.BYTES) {
      throw new BufferUnderflowException();
    }
    temp.clear().limit(Short.BYTES);
    get(temp);
    temp.flip();
    return temp.getShort();
  }

  public short getShort(int index) {
    if(index >= limit() - Short.BYTES) {
      throw new BufferUnderflowException();
    }
    position = index;
    return getShort();
  }
  
  public String getUTF8(int len) {
    if(remaining() < len) {
      throw new BufferUnderflowException();
    }
    byte[] bs = new byte[len];
    get(bs);
    return new String(bs, StandardCharsets.UTF_8);
  }

  public BitBuffer put(byte b) {
    ensureSize(position + 1);
    setupCurrent().put(b);
    position++;
    return this;
  }

  public BitBuffer put(int pos, byte b) {
    position(pos);
    return put(b);
  }

  public BitBuffer put(byte[] src, int offset, int length) {
    return put(ByteBuffer.wrap(src, offset, length));
  }

  public BitBuffer put(ByteBuffer src) {
    ensureSize(position + src.remaining());
    while(src.hasRemaining()) {
      ByteBuffer cur = setupCurrent();
      int len = Math.min(cur.remaining(), src.remaining());
      int lim = src.limit();
      src.limit(src.position() + len);
      cur.put(src);
      src.limit(lim);
      position += len;
    }
    return this;
  }
  
  public BitBuffer put(BitBuffer src) {
    ensureSize(position() + src.remaining());
    while(src.hasRemaining()) {
      ByteBuffer cur = setupCurrent();
      int len = Math.min(cur.remaining(), src.remaining());
      int lim = src.limit();
      src.limit(src.position() + len);
      src.get(cur);
      src.limit(lim);
      position += len;
    }
    return this;
  }
  
  public BitBuffer putChar(int index, char value) {
    position(index);
    return putChar(value);
  }

  public BitBuffer putChar(char value) {
    temp.clear();
    temp.putChar(value);
    temp.flip();
    put(temp);
    return this;
  }

  public BitBuffer putDouble(int index, double value) {
    position(index);
    return putDouble(value);
  }

  public BitBuffer putDouble(double value) {
    temp.clear();
    temp.putDouble(value);
    temp.flip();
    put(temp);
    return this;
  }

  public BitBuffer putFloat(int index, float value) {
    position(index);
    return putFloat(value);
  }

  public BitBuffer putFloat(float value) {
    temp.clear();
    temp.putFloat(value);
    temp.flip();
    put(temp);
    return this;
  }

  public BitBuffer putInt(int index, int value) {
    position(index);
    return putInt(value);
  }

  public BitBuffer putInt(int value) {
    ByteBuffer cur = setupCurrent();
    if(cur.remaining() >= Integer.BYTES) {
      position += Integer.BYTES;
      cur.putInt(value);
    }
    else {
      temp.clear();
      temp.putInt(value);
      temp.flip();
      put(temp);
    }
    return this;
  }

  public BitBuffer putLong(int index, long value) {
    position(index);
    return putLong(value);
  }

  public BitBuffer putLong(long value) {
    ByteBuffer cur = setupCurrent();
    if(cur.remaining() >= Long.BYTES) {
      position += Long.BYTES;
      cur.putLong(value);
    }
    else {
      temp.clear();
      temp.putLong(value);
      temp.flip();
      put(temp);
    }
    return this;
  }

  public BitBuffer putShort(int index, short value) {
    position(index);
    return putShort(value);
  }

  public BitBuffer putShort(short value) {
    ByteBuffer cur = setupCurrent();
    if(cur.remaining() >= Short.BYTES) {
      position += Short.BYTES;
      cur.putShort(value);
    }
    else {
      temp.clear();
      temp.putShort(value);
      temp.flip();
      put(temp);
    }
    return this;
  }
  
  public BitBuffer putUTF8(String str) {
    return put(str.getBytes(StandardCharsets.UTF_8));
  }

  public BitBuffer duplicate() {
    return new MultiBuffer(buffers, bufferSource, temp, position, limit, mark);
  }

  public BitBuffer slice() {
    ByteBuffer b = (buffers.get(0).isDirect() 
        ? ByteBuffer.allocateDirect(remaining()) 
        : ByteBuffer.allocate(remaining())
    );
    get(b);
    b.flip();
    return new DefaultBitBuffer(b);
  }

  public BitBuffer clear() {
    position = 0;
    limit = capacity();
    mark = 0;
    return this;
  }

  public BitBuffer flip() {
    limit = position();
    position = 0;
    return this;
  }

  public byte[] array() {
    throw new UnsupportedOperationException();
  }

  public ByteOrder order() {
    return current().order();
  }

  public BitBuffer order(ByteOrder order) {
    buffers.forEach(b -> b.order(order));
    return this;
  }

  public void close() {
    clear();
    buffers.clear();
  }

  public boolean isDirect() {
    return current().isDirect();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BitBuffer{ ");
    sb.append("position=").append(position());
    sb.append(", limit=").append(limit());
    sb.append(", capacity=").append(capacity());
    sb.append(", index=").append(index());
    sb.append(", current=").append(setupCurrent());
    //sb.append(", order=").append(order());
    //sb.append(", direct=").append(current().isDirect());
    sb.append(" }");
    return sb.toString();
  }
  
  public byte[] toByteArray() {
    byte[] array = new byte[capacity()];
    int pos = position();
    int lim = limit();
    position(0);
    limit(capacity());
    get(array);
    limit(lim);
    position(pos);
    return array;
  }

  public ByteBuffer toByteBuffer() {
    ByteBuffer b = current().isDirect() 
        ? ByteBuffer.allocateDirect(capacity()) 
        : ByteBuffer.allocate(capacity());
    int pos = position();
    int lim = limit();
    position(0);
    limit(capacity());
    get(b);
    limit(lim);
    position(pos);
    b.limit(lim);
    b.position(mark);
    b.mark();
    b.position(pos);
    return b;
  }

  public int writeTo(WritableByteChannel chl) throws IOException {
    int total = 0;
    while(hasRemaining()) {
      int len = chl.write(setupCurrent());
      position += len;
      total += len;
    }
    return total;
  }
  
  public int writeTo(BitBuffer buf) {
    int total = 0;
    while(hasRemaining() && buf.hasRemaining()) {
      int len = buf.readFrom(setupCurrent());
      position += len;
      total += len;
    }
    return total;
  }

  public int writeTo(ByteBuffer buf) {
    int total = 0;
    while(hasRemaining() && buf.hasRemaining()) {
      ByteBuffer cur = setupCurrent();
      int len = Math.min(cur.remaining(), buf.remaining());
      int lim = cur.limit();
      cur.limit(cur.position() + len);
      buf.put(cur);
      cur.limit(lim);
      position += len;
      total += len;
    }
    return total;
  }

  public int readFrom(ReadableByteChannel chl) throws IOException {
    int total = 0;
    int read = -1;
    while((read = chl.read(setupCurrent())) != -1) {
      position += read;
      total += read;
    }
    return total;
  }

  public int readFrom(ByteBuffer buf) {
    int min = Math.min(remaining(), buf.remaining());
    put(buf);
    return min;
  }
  
  public int readFrom(BitBuffer buf) {
    int min = Math.min(remaining(), buf.remaining());
    put(buf);
    return min;
  }
  
  public WritableByteChannel toWritableByteChannel() {
    final MultiBuffer _this = this;
    return new WritableByteChannel() {
      @Override
      public int write(ByteBuffer bb) throws IOException {
        int rem = bb.remaining();
        _this.put(bb);
        return rem;
      }
      @Override public boolean isOpen() { return true; }
      @Override public void close() {}
    };
  }
  
  public OutputStream toOutputStream() {
    return Channels.newOutputStream(toWritableByteChannel());
  }
  
}