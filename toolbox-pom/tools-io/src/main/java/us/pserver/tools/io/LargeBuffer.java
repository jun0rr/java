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
public final class LargeBuffer {
  
  private final List<ByteBuffer> buffers;
  
  private final Supplier<ByteBuffer> bufferSource;
  
  private long position, limit, mark;
  
  private final long unitSize;
  
  private final ByteBuffer temp;
  
  
  private LargeBuffer(List<ByteBuffer> buffers, Supplier<ByteBuffer> bufferSource, ByteBuffer buffer, long position, long limit, long mark) {
    this.bufferSource = bufferSource;
    this.buffers = buffers;
    this.temp = buffer;
    this.position = position;
    this.limit = limit;
    this.mark = mark;
    this.unitSize = buffers.get(0).capacity();
  }
  
  public LargeBuffer(Supplier<ByteBuffer> bufferSource) {
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
    return Math.min(buffers.size() -1, (int)Math.floor((double)position / unitSize));
  }
  
  public ByteBuffer current() {
    return buffers.get(index());
  }
  
  public BitBuffer compact() {
    throw new UnsupportedOperationException();
  }
  
  public long position() {
    return position;
  }
  
  public LargeBuffer position(long pos) {
    ensureSize(pos, true);
    buffers.forEach(b -> b.position(0));
    position = pos;
    buffers.stream().limit(index()).forEach(b -> b.position(b.limit()));
    current().position((int)(position - (unitSize * index())));
    return this;
  }
  
  public long remaining() {
    return limit - position;
  }
  
  public boolean hasRemaining() {
    return remaining() > 0;
  }
  
  public LargeBuffer mark() {
    this.mark = position();
    return this;
  }
  
  public LargeBuffer reset() {
    position(mark);
    return this;
  }
  
  public LargeBuffer rewind() {
    return position(0);
  }
  
  public long capacity() {
    return buffers.size() * unitSize;
  }
  
  public long limit() {
    return limit;
  }
  
  public LargeBuffer limit(long lim) {
    ensureSize(lim, false);
    limit = lim;
    int idx = Math.min(buffers.size() -1, (int)Math.floor((double) limit / unitSize));
    buffers.stream().limit(idx+1).forEach(b -> b.limit(b.capacity()));
    buffers.get(idx).limit((int)(limit - (idx * unitSize)));
    return this;
  }
  
  private void ensureSize(long size, boolean resetLimit) {
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

  public LargeBuffer get(byte[] src) {
    return get(src, 0, src.length);
  }
  
  public LargeBuffer get(byte[] dst, int offset, int length) {
    if(remaining() < length) {
      throw new BufferUnderflowException();
    }
    int total = length;
    int off = offset;
    while(total > 0) {
      ByteBuffer cur = current();
      int len = Math.min(cur.remaining(), total);
      cur.get(dst, off, len);
      position += len;
      off += len;
      total -= len;
    }
    return this;
  }
  
  public LargeBuffer get(ByteBuffer buf) {
    long total = Math.min((int)this.remaining(), (long)buf.remaining());
    while(total > 0) {
      ByteBuffer cur = current();
      long len = Math.min(cur.remaining(), total);
      int lim = cur.limit();
      cur.limit((int)(cur.position() + len));
      buf.put(cur);
      cur.limit(lim);
      total -= len;
      position += len;
    }
    return this;
  }

  public LargeBuffer get(BitBuffer buf) {
    int total = Math.min((int)this.remaining(), buf.remaining());
    while(total > 0) {
      ByteBuffer cur = current();
      int len = Math.min(cur.remaining(), total);
      int lim = cur.limit();
      cur.limit(cur.position() + len);
      buf.put(cur);
      cur.limit(lim);
      total -= len;
      position += len;
    }
    return this;
  }
  
  public char getChar() {
    if(remaining() < Character.BYTES) {
      throw new BufferUnderflowException();
    }
    temp.clear();
    temp.limit(Character.BYTES);
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
    temp.clear();
    temp.limit(Double.BYTES);
    get(temp);
    temp.flip();
    return temp.getDouble();
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
    temp.clear();
    temp.limit(Float.BYTES);
    get(temp);
    temp.flip();
    return temp.getFloat();
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
    temp.clear();
    temp.limit(Integer.BYTES);
    get(temp);
    temp.flip();
    return temp.getInt();
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
    temp.clear();
    temp.limit(Long.BYTES);
    get(temp);
    temp.flip();
    return temp.getLong();
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
    temp.clear();
    temp.limit(Short.BYTES);
    get(temp);
    temp.flip();
    return temp.getShort();
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
    String str;
    ByteBuffer cur = current();
    if(cur.remaining() >= len) {
      int lim = cur.limit();
      cur.limit(cur.position() + len);
      str = StandardCharsets.UTF_8.decode(cur).toString();
    }
    else {
      ByteBuffer b = ByteBuffer.allocate(len);
      get(b);
      b.flip();
      str = StandardCharsets.UTF_8.decode(b).toString();
    }
    return str;
  }

  public LargeBuffer put(byte b) {
    ensureSize(position() + 1, true);
    current().put(b);
    position++;
    return this;
  }

  public LargeBuffer put(long pos, byte b) {
    position(pos);
    return put(b);
  }
  
  public LargeBuffer put(byte[] src) {
    return put(src, 0, src.length);
  }
  
  public LargeBuffer put(byte[] src, int offset, int length) {
    ensureSize(position() + length, true);
    return put(ByteBuffer.wrap(src, offset, length));
  }

  public LargeBuffer put(ByteBuffer src) {
    ensureSize(position() + src.remaining(), true);
    while(src.hasRemaining()) {
      ByteBuffer cur = current();
      int len = Math.min(cur.remaining(), src.remaining());
      int lim = src.limit();
      src.limit(src.position() + len);
      cur.put(src);
      src.limit(lim);
      position += len;
    }
    return this;
  }
  
  public LargeBuffer put(BitBuffer src) {
    ensureSize(position() + src.remaining(), true);
    while(src.hasRemaining()) {
      ByteBuffer cur = current();
      int len = Math.min(cur.remaining(), src.remaining());
      int lim = src.limit();
      src.limit(src.position() + len);
      src.get(cur);
      src.limit(lim);
      position += len;
    }
    return this;
  }
  
  public LargeBuffer putChar(int index, char value) {
    position(index);
    return putChar(value);
  }

  public LargeBuffer putChar(char value) {
    ensureSize(position() + Character.BYTES, true);
    temp.clear();
    temp.putChar(value);
    temp.flip();
    put(temp);
    return this;
  }

  public LargeBuffer putDouble(int index, double value) {
    position(index);
    return putDouble(value);
  }

  public LargeBuffer putDouble(double value) {
    ensureSize(position() + Double.BYTES, true);
    temp.clear();
    temp.putDouble(value);
    temp.flip();
    put(temp);
    return this;
  }

  public LargeBuffer putFloat(int index, float value) {
    position(index);
    return putFloat(value);
  }

  public LargeBuffer putFloat(float value) {
    ensureSize(position() + Float.BYTES, true);
    temp.clear();
    temp.putFloat(value);
    temp.flip();
    put(temp);
    return this;
  }

  public LargeBuffer putInt(int index, int value) {
    position(index);
    return putInt(value);
  }

  public LargeBuffer putInt(int value) {
    ensureSize(position() + Integer.BYTES, true);
    temp.clear();
    temp.putInt(value);
    temp.flip();
    put(temp);
    return this;
  }

  public LargeBuffer putLong(int index, long value) {
    position(index);
    return putLong(value);
  }

  public LargeBuffer putLong(long value) {
    ensureSize(position() + Long.BYTES, true);
    temp.clear();
    temp.putLong(value);
    temp.flip();
    put(temp);
    return this;
  }

  public LargeBuffer putShort(int index, short value) {
    position(index);
    return putShort(value);
  }

  public LargeBuffer putShort(short value) {
    ensureSize(position() + Short.BYTES, true);
    temp.clear();
    temp.putShort(value);
    temp.flip();
    put(temp);
    return this;
  }
  
  public LargeBuffer putUTF8(String str) {
    ensureSize(position() + str.length(), true);
    return put(str.getBytes(StandardCharsets.UTF_8));
  }

  public LargeBuffer duplicate() {
    return new LargeBuffer(buffers, bufferSource, temp, position, limit, mark);
  }

  public ByteBuffer slice() {
    ByteBuffer b = (buffers.get(0).isDirect() 
        ? ByteBuffer.allocateDirect((int)remaining()) 
        : ByteBuffer.allocate((int)remaining())
    );
    get(b);
    b.flip();
    return b;
  }

  public LargeBuffer clear() {
    buffers.forEach(ByteBuffer::clear);
    position = 0;
    limit = capacity();
    mark = 0;
    return this;
  }

  public LargeBuffer flip() {
    limit = position();
    buffers.stream()
        .takeWhile(b -> b.position() > 0)
        .forEach(ByteBuffer::flip);
    position = mark = 0;
    return this;
  }

  public byte[] array() {
    throw new UnsupportedOperationException();
  }

  public ByteOrder order() {
    return current().order();
  }

  public LargeBuffer order(ByteOrder order) {
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
  
  public int writeTo(WritableByteChannel chl) throws IOException {
    int total = 0;
    while(hasRemaining()) {
      int len = chl.write(current());
      position += len;
      total += len;
    }
    return total;
  }
  
  public int writeTo(BitBuffer buf) {
    int total = 0;
    while(hasRemaining() && buf.hasRemaining()) {
      int len = buf.readFrom(current());
      position += len;
      total += len;
    }
    return total;
  }

  public int writeTo(ByteBuffer buf) {
    int total = 0;
    while(hasRemaining() && buf.hasRemaining()) {
      ByteBuffer cur = current();
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
    while((read = chl.read(current())) != -1) {
      position += read;
      total += read;
    }
    return total;
  }

  public int readFrom(ByteBuffer buf) {
    int min = Math.min((int)remaining(), buf.remaining());
    put(buf);
    return min;
  }
  
  public int readFrom(BitBuffer buf) {
    int min = Math.min((int)remaining(), buf.remaining());
    put(buf);
    return min;
  }
  
  public WritableByteChannel toWritableByteChannel() {
    final LargeBuffer _this = this;
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