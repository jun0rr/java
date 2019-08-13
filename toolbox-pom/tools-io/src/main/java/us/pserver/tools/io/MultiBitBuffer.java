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

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 04/12/2018
 */
public final class MultiBitBuffer implements BitBuffer {
  
  private final List<BitBuffer> buffers;
  
  private final Supplier<BitBuffer> bufferSource;
  
  private int position, limit, mark;
  
  private final int unitSize;
  
  
  private MultiBitBuffer(List<BitBuffer> buffers, Supplier<BitBuffer> bufferSource, int position, int limit, int mark) {
    this.bufferSource = bufferSource;
    this.buffers = buffers;
    this.position = position;
    this.limit = limit;
    this.mark = mark;
    this.unitSize = buffers.get(0).capacity();
  }
  
  public MultiBitBuffer(Supplier<BitBuffer> bufferSource) {
    this.bufferSource = Objects.requireNonNull(bufferSource);
    this.buffers = new ArrayList<>();
    BitBuffer buf = bufferSource.get().clear();
    this.unitSize = buf.capacity();
    buffers.add(buf);
    this.position = 0;
    this.limit = buf.limit();
    this.mark = position;
  }
  
  private int index() {
    return Math.min(buffers.size() -1, Double.valueOf(Math.floor(
        position / Integer.valueOf(unitSize).doubleValue())
    ).intValue());
  }
  
  public BitBuffer current() {
    return buffers.get(index());
  }
  
  public BitBuffer compact() {
    throw new UnsupportedOperationException();
  }
  
  public int position() {
    return position;
  }
  
  public BitBuffer position(int pos) {
    ensureSize(pos, true);
    buffers.forEach(b -> b.position(0));
    position = pos;
    buffers.stream().limit(index()).forEach(b -> b.position(b.limit()));
    current().position(position - (unitSize * index()));
    return this;
  }
  
  public int remaining() {
    return limit - position;
  }
  
  public boolean hasRemaining() {
    return remaining() > 0;
  }
  
  public BitBuffer mark() {
    this.mark = position();
    return this;
  }
  
  public BitBuffer reset() {
    position(mark);
    return this;
  }
  
  public BitBuffer rewind() {
    return position(0);
  }
  
  public int capacity() {
    return buffers.size() * unitSize;
  }
  
  public int limit() {
    return limit;
  }
  
  public BitBuffer limit(int lim) {
    ensureSize(lim, false);
    buffers.forEach(b -> b.limit(b.capacity()));
    limit = lim;
    int idx = Math.min(buffers.size() -1, Double.valueOf(Math.floor(
        limit / Integer.valueOf(unitSize).doubleValue())
    ).intValue());
    buffers.stream().limit(idx+1).forEach(b -> b.limit(b.capacity()));
    buffers.get(idx).limit(limit - (idx * unitSize));
    return this;
  }
  
  private void ensureSize(int size, boolean resetLimit) {
    while(size > capacity()) {
      buffers.add(bufferSource.get());
    }
    if(resetLimit) {
      limit(capacity());
    }
  }
  
  public byte get() {
    if(!current().hasRemaining()) {
      throw new BufferUnderflowException();
    }
    byte b = current().get();
    position++;
    return b;
  }

  public byte get(int index) {
    if(!current().hasRemaining() || index >= limit) {
      throw new BufferUnderflowException();
    }
    position = index;
    byte b = current().get();
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
      int len = Math.min(current().remaining(), total);
      current().get(dst, off, len);
      position += len;
      off += len;
      total -= len;
    }
    return this;
  }
  
  public BitBuffer get(ByteBuffer buf) {
    int total = Math.min(this.remaining(), buf.remaining());
    while(total > 0) {
      int len = Math.min(current().remaining(), total);
      current().get(buf);
      total -= len;
      position += len;
    }
    return this;
  }

  public BitBuffer get(BitBuffer buf) {
    int total = Math.min(this.remaining(), buf.remaining());
    while(total > 0) {
      int len = Math.min(current().remaining(), total);
      current().get(buf);
      total -= len;
      position += len;
    }
    return this;
  }

  public char getChar() {
    if(remaining() < Character.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Character.BYTES);
    get(b);
    b.flip();
    return b.getChar();
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
    ByteBuffer b = ByteBuffer.allocate(Double.BYTES);
    get(b);
    b.flip();
    return b.getDouble();
  }

  public double getDouble(int index) {
    if(index >= limit() - Double.BYTES) {
      throw new BufferUnderflowException();
    }
    position(index);
    return getDouble();
  }

  public float getFloat() {
    if(remaining() < Float.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Float.BYTES);
    get(b);
    b.flip();
    return b.getFloat();
  }

  public float getFloat(int index) {
    if(index >= limit() - Float.BYTES) {
      throw new BufferUnderflowException();
    }
    position(index);
    return getFloat();
  }

  public int getInt() {
    if(remaining() < Integer.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Integer.BYTES);
    get(b);
    b.flip();
    return b.getInt();
  }

  public int getInt(int index) {
    if(index >= limit() - Integer.BYTES) {
      throw new BufferUnderflowException();
    }
    position(index);
    return getInt();
  }

  public long getLong() {
    if(remaining() < Long.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
    get(b);
    b.flip();
    return b.getLong();
  }

  public long getLong(int index) {
    if(index >= limit() - Long.BYTES) {
      throw new BufferUnderflowException();
    }
    position(index);
    return getLong();
  }

  public short getShort() {
    if(remaining() < Short.BYTES) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(Short.BYTES);
    get(b);
    b.flip();
    return b.getShort();
  }

  public short getShort(int index) {
    if(index >= limit() - Short.BYTES) {
      throw new BufferUnderflowException();
    }
    position(index);
    return getShort();
  }
  
  public String getUTF8(int len) {
    if(remaining() < len) {
      throw new BufferUnderflowException();
    }
    ByteBuffer b = ByteBuffer.allocate(len);
    get(b);
    b.flip();
    return StandardCharsets.UTF_8.decode(b).toString();
  }

  public BitBuffer put(byte b) {
    ensureSize(position() + 1, true);
    current().put(b);
    position++;
    return this;
  }

  public BitBuffer put(int pos, byte b) {
    position(pos);
    return put(b);
  }

  public BitBuffer put(byte[] src, int offset, int length) {
    ensureSize(position() + length, true);
    return put(ByteBuffer.wrap(src, offset, length));
  }

  public BitBuffer put(ByteBuffer src) {
    ensureSize(position() + src.remaining(), true);
    while(src.hasRemaining()) {
      int len = Math.min(current().remaining(), src.remaining());
      int lim = src.limit();
      src.limit(src.position() + len);
      current().put(src);
      src.limit(lim);
      position += len;
    }
    return this;
  }
  
  public BitBuffer put(BitBuffer src) {
    ensureSize(position() + src.remaining(), true);
    while(src.hasRemaining()) {
      int len = Math.min(current().remaining(), src.remaining());
      int lim = src.limit();
      src.limit(src.position() + len);
      current().put(src);
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
    ensureSize(position() + Character.BYTES, true);
    ByteBuffer b = ByteBuffer.allocate(Character.BYTES);
    b.putChar(value);
    b.flip();
    return put(b);
  }

  public BitBuffer putDouble(int index, double value) {
    position(index);
    return putDouble(value);
  }

  public BitBuffer putDouble(double value) {
    ensureSize(position() + Double.BYTES, true);
    ByteBuffer b = ByteBuffer.allocate(Double.BYTES);
    b.putDouble(value);
    b.flip();
    return put(b);
  }

  public BitBuffer putFloat(int index, float value) {
    position(index);
    return putFloat(value);
  }

  public BitBuffer putFloat(float value) {
    ensureSize(position() + Float.BYTES, true);
    ByteBuffer b = ByteBuffer.allocate(Float.BYTES);
    b.putFloat(value);
    b.flip();
    return put(b);
  }

  public BitBuffer putInt(int index, int value) {
    position(index);
    return putInt(value);
  }

  public BitBuffer putInt(int value) {
    ensureSize(position() + Integer.BYTES, true);
    ByteBuffer b = ByteBuffer.allocate(Integer.BYTES);
    b.putInt(value);
    b.flip();
    return put(b);
  }

  public BitBuffer putLong(int index, long value) {
    position(index);
    return putLong(value);
  }

  public BitBuffer putLong(long value) {
    ensureSize(position() + Long.BYTES, true);
    ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
    b.putLong(value);
    b.flip();
    return put(b);
  }

  public BitBuffer putShort(int index, short value) {
    position(index);
    return putShort(value);
  }

  public BitBuffer putShort(short value) {
    ensureSize(position() + Short.BYTES, true);
    ByteBuffer b = ByteBuffer.allocate(Short.BYTES);
    b.putShort(value);
    b.flip();
    return put(b);
  }
  
  public BitBuffer putUTF8(String str) {
    ensureSize(position() + str.length(), true);
    ByteBuffer b = StandardCharsets.UTF_8.encode(str);
    return put(b);
  }

  public BitBuffer duplicate() {
    return new MultiBitBuffer(buffers, bufferSource, position, limit, mark);
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
    buffers.forEach(BitBuffer::clear);
    position = 0;
    limit = capacity();
    mark = 0;
    return this;
  }

  public BitBuffer flip() {
    limit = position();
    buffers.stream()
        .takeWhile(b -> b.position() > 0)
        .forEach(BitBuffer::flip);
    position = mark = 0;
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
    sb.append(", current=").append(current());
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
      int len = current().writeTo(chl);
      position += len;
      total += len;
    }
    return total;
  }
  
  public int writeTo(BitBuffer buf) {
    int total = 0;
    while(hasRemaining() && buf.hasRemaining()) {
      int len = current().writeTo(buf);
      position += len;
      total += len;
    }
    return total;
  }

  public int writeTo(ByteBuffer buf) {
    int total = 0;
    while(hasRemaining() && buf.hasRemaining()) {
      int len = current().writeTo(buf);
      position += len;
      total += len;
    }
    return total;
  }

  public int readFrom(ReadableByteChannel chl) throws IOException {
    int total = 0;
    int read = -1;
    while((read = current().readFrom(chl)) != -1) {
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
    final MultiBitBuffer _this = this;
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