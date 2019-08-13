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
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 04/12/2018
 */
public final class DefaultBitBuffer implements BitBuffer {

  private final ByteBuffer buffer;
  

  public DefaultBitBuffer(int cap, boolean useDirectBuffer) {
    buffer = useDirectBuffer ? ByteBuffer.allocateDirect(cap) : ByteBuffer.allocate(cap);
  }

  public DefaultBitBuffer(byte[] array) {
    buffer = array != null ? ByteBuffer.wrap(array) : ByteBuffer.allocate(0);
  }

  public DefaultBitBuffer(byte[] array, int offset, int length) {
    buffer = ByteBuffer.wrap(array, offset, length);
  }

  public DefaultBitBuffer(ByteBuffer buffer) {
    this.buffer = buffer;
  }

  public BitBuffer compact() {
    buffer.compact();
    return this;
  }
  
  public byte get() {
    return buffer.get();
  }

  public byte get(int index) {
    return buffer.get(index);
  }

  public BitBuffer get(byte[] dst) {
    buffer.get(dst);
    return this;
  }

  public BitBuffer get(byte[] dst, int offset, int length) {
    buffer.get(dst, offset, length);
    return this;
  }

  public char getChar() {
    return buffer.getChar();
  }

  public char getChar(int index) {
    return buffer.getChar(index);
  }

  public double getDouble() {
    return buffer.getDouble();
  }

  public double getDouble(int index) {
    return buffer.getDouble(index);
  }

  public float getFloat() {
    return buffer.getFloat();
  }

  public float getFloat(int index) {
    return buffer.getFloat(index);
  }

  public int getInt() {
    return buffer.getInt();
  }

  public int getInt(int index) {
    return buffer.getInt(index);
  }

  public long getLong() {
    return buffer.getLong();
  }

  public long getLong(int index) {
    return buffer.getLong(index);
  }

  public short getShort() {
    return buffer.getShort();
  }

  public short getShort(int index) {
    return buffer.getShort();
  }
  
  public String getUTF8(int len) {
    if(remaining() < len) {
      throw new BufferUnderflowException();
    }
    int lim = buffer.limit();
    buffer.limit(buffer.position() + len);
    String utf = StandardCharsets.UTF_8.decode(buffer).toString();
    buffer.limit(lim);
    return utf;
  }

  public BitBuffer put(byte b) {
    buffer.put(b);
    return this;
  }

  public BitBuffer put(int index, byte b) {
    buffer.put(index, b);
    return this;
  }

  public BitBuffer put(byte[] src) {
    buffer.put(src);
    return this;
  }

  public BitBuffer put(byte[] src, int offset, int length) {
    buffer.put(src, offset, length);
    return this;
  }

  public BitBuffer put(ByteBuffer src) {
    buffer.put(src);
    return this;
  }
  
  public BitBuffer put(BitBuffer src) {
    buffer.put(src.toByteBuffer());
    return this;
  }
  
  public BitBuffer putChar(int index, char value) {
    buffer.putChar(index, value);
    return this;
  }

  public BitBuffer putChar(char value) {
    buffer.putChar(value);
    return this;
  }

  public BitBuffer putDouble(int index, double value) {
    buffer.putDouble(index, value);
    return this;
  }

  public BitBuffer putDouble(double value) {
    buffer.putDouble(value);
    return this;
  }

  public BitBuffer putFloat(int index, float value) {
    buffer.putFloat(index, value);
    return this;
  }

  public BitBuffer putFloat(float value) {
    buffer.putFloat(value);
    return this;
  }

  public BitBuffer putInt(int index, int value) {
    buffer.putInt(index, value);
    return this;
  }

  public BitBuffer putInt(int value) {
    buffer.putInt(value);
    return this;
  }

  public BitBuffer putLong(int index, long value) {
    buffer.putLong(index, value);
    return this;
  }

  public BitBuffer putLong(long value) {
    buffer.putLong(value);
    return this;
  }

  public BitBuffer putShort(int index, short value) {
    buffer.putShort(index, value);
    return this;
  }

  public BitBuffer putShort(short value) {
    buffer.putShort(value);
    return this;
  }
  
  public BitBuffer putUTF8(String str) {
    buffer.put(StandardCharsets.UTF_8.encode(str));
    return this;
  }

  public BitBuffer duplicate() {
    return new DefaultBitBuffer(buffer.duplicate());
  }

  public BitBuffer slice() {
    return new DefaultBitBuffer(buffer.slice());
  }

  public BitBuffer clear() {
    buffer.clear();
    return this;
  }

  public BitBuffer flip() {
    buffer.flip();
    return this;
  }

  public int limit() {
    return buffer.limit();
  }

  public BitBuffer limit(int newLimit) {
    buffer.limit(newLimit);
    return this;
  }

  public BitBuffer mark() {
    buffer.mark();
    return this;
  }

  public int position() {
    return buffer.position();
  }

  public BitBuffer position(int newPosition) {
    buffer.position(newPosition);
    return this;
  }

  public int remaining() {
    return buffer.remaining();
  }

  public BitBuffer reset() {
    buffer.reset();
    return this;
  }

  public BitBuffer rewind() {
    buffer.rewind();
    return this;
  }

  public int capacity() {
    return buffer.capacity();
  }

  public boolean hasRemaining() {
    return buffer.hasRemaining();
  }

  public byte[] array() {
    if (!buffer.isDirect()) {
      return buffer.array();
    } else {
      final ByteBuffer duplicate = buffer.duplicate();
      duplicate.flip();
      final byte[] newBuffer = new byte[duplicate.limit()];
      duplicate.get(newBuffer);
      return newBuffer;
    }
  }

  public ByteOrder order() {
    return buffer.order();
  }

  public BitBuffer order(ByteOrder order) {
    buffer.order(order);
    return this;
  }

  public void close() {}

  public boolean isDirect() {
    return buffer.isDirect();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BitBuffer{ ");
    if (buffer != null) {
      sb.append("position=").append(buffer.position());
      sb.append(", limit=").append(buffer.limit());
      sb.append(", capacity=").append(buffer.capacity());
      sb.append(", order=").append(buffer.order());
      sb.append(", direct=").append(buffer.isDirect());
    }
    return sb.append(" }").toString();
  }
  
  public byte[] toByteArray() {
    if(!hasRemaining()) return new byte[0];
    int pos = position();
    byte[] bs = new byte[remaining()];
    get(bs);
    position(pos);
    return bs;
  }

  public ByteBuffer toByteBuffer() {
    return buffer;
  }

  public int writeTo(ByteBuffer buf) {
    int min = Math.min(buf.remaining(), remaining());
    int lim = limit();
    limit(min);
    buf.put(buffer);
    limit(lim);
    return min;
  }

  public int writeTo(BitBuffer buf) {
    int min = Math.min(buf.remaining(), remaining());
    int lim = limit();
    limit(min);
    buf.put(buffer);
    limit(lim);
    return min;
  }

  public int writeTo(WritableByteChannel chl) throws IOException {
    return chl.write(buffer);
  }

  public int readFrom(ReadableByteChannel chl) throws IOException {
    return chl.read(buffer);
  }

  public int readFrom(ByteBuffer buf) {
    int min = Math.min(remaining(), buf.remaining());
    int lim = buf.limit();
    buf.limit(buf.position() + min);
    put(buf);
    buf.limit(lim);
    return min;
  }
  
  public int readFrom(BitBuffer buf) {
    int min = Math.min(remaining(), buf.remaining());
    int lim = buf.limit();
    buf.limit(buf.position() + min);
    put(buf);
    buf.limit(lim);
    return min;
  }
  
  @Override
  public BitBuffer get(ByteBuffer buf) {
    int len = Math.min(remaining(), buf.remaining());
    int lim = buffer.limit();
    buffer.limit(buffer.position() + len);
    buf.put(buffer);
    buffer.limit(lim);
    return this;
  }
  
  @Override
  public BitBuffer get(BitBuffer buf) {
    int len = Math.min(remaining(), buf.remaining());
    int lim = buf.limit();
    buf.limit(buf.position() + len);
    buf.put(buffer);
    buf.limit(lim);
    return this;
  }
  
}