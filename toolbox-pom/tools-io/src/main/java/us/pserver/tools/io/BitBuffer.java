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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 04/12/2018
 */
public interface BitBuffer {

  public BitBuffer compact();
  
  public byte get();

  public byte get(int index);

  public default BitBuffer get(byte[] dst) {
    return get(dst, 0, dst.length);
  }

  public BitBuffer get(byte[] dst, int offset, int length);
  
  public BitBuffer get(ByteBuffer buf);
  
  public BitBuffer get(BitBuffer buf);

  public char getChar();

  public char getChar(int index);

  public double getDouble();

  public double getDouble(int index);

  public float getFloat();

  public float getFloat(int index);

  public int getInt();

  public int getInt(int index);

  public long getLong();

  public long getLong(int index);

  public short getShort();

  public short getShort(int index);
  
  public String getUTF8(int len);

  public BitBuffer put(byte b);

  public BitBuffer put(int index, byte b);

  public default BitBuffer put(byte[] src) {
    return put(src, 0, src.length);
  }

  public BitBuffer put(byte[] src, int offset, int length);

  public BitBuffer put(ByteBuffer src);
  
  public BitBuffer put(BitBuffer src);
  
  public BitBuffer putChar(int index, char value);

  public BitBuffer putChar(char value);

  public BitBuffer putDouble(int index, double value);

  public BitBuffer putDouble(double value);

  public BitBuffer putFloat(int index, float value);

  public BitBuffer putFloat(float value);

  public BitBuffer putInt(int index, int value);

  public BitBuffer putInt(int value);

  public BitBuffer putLong(int index, long value);

  public BitBuffer putLong(long value);

  public BitBuffer putShort(int index, short value);

  public BitBuffer putShort(short value);
  
  public BitBuffer putUTF8(String str);

  public BitBuffer duplicate();

  public BitBuffer slice();

  public BitBuffer clear();

  public BitBuffer flip();

  public int limit();

  public BitBuffer limit(int newLimit);

  public int position();

  public BitBuffer position(int newPosition);

  public int remaining();

  public boolean hasRemaining();
  
  public int capacity();
  
  public BitBuffer mark();
  
  public BitBuffer reset();

  public BitBuffer rewind();

  public byte[] array();

  public ByteOrder order();

  public BitBuffer order(ByteOrder order);

  public void close();

  public boolean isDirect();

  public byte[] toByteArray();

  public ByteBuffer toByteBuffer();

  public int writeTo(ByteBuffer buf);

  public int writeTo(BitBuffer buf);

  public int writeTo(WritableByteChannel chl) throws IOException;

  public int readFrom(ReadableByteChannel chl) throws IOException;

  public int readFrom(ByteBuffer buf);
  
  public int readFrom(BitBuffer buf);
  
  
  
  public static BitBuffer of(int capacity, boolean direct) {
    return new DefaultBitBuffer(capacity, direct);
  }
  
  public static BitBuffer of(ByteBuffer buf) {
    return new DefaultBitBuffer(buf);
  }
  
  public static BitBuffer of(byte[] bs) {
    return new DefaultBitBuffer(ByteBuffer.wrap(bs));
  }
  
  public static BitBuffer of(byte[] bs, int off, int len) {
    return new DefaultBitBuffer(ByteBuffer.wrap(bs, off, len));
  }
  
  
  public static DynamicBuffer dynamicBuffer(int capacity, boolean direct) {
    return new DynamicBuffer(capacity, direct);
  }
  
  public static DynamicBuffer dynamicBuffer(ByteBuffer buf) {
    return new DynamicBuffer(buf);
  }
  
  public static DynamicBuffer dynamicBuffer(byte[] bs) {
    return new DynamicBuffer(bs);
  }
  
  public static DynamicBuffer dynamicBuffer(byte[] bs, int off, int len) {
    return new DynamicBuffer(bs, off, len);
  }
  
  
  public static MultiBuffer multiBuffer(int capacity, boolean direct) {
    Supplier<ByteBuffer> bbsup = () -> direct ? ByteBuffer.allocateDirect(capacity) : ByteBuffer.allocate(capacity);
    Supplier<BitBuffer> sup = () -> new DefaultBitBuffer(bbsup.get());
    return new MultiBuffer(sup);
  }
  
  public static MultiBuffer multiBuffer(ByteBuffer buf) {
    Supplier<ByteBuffer> bbsup = () -> buf.isDirect() 
        ? ByteBuffer.allocateDirect(buf.capacity()) 
        : ByteBuffer.allocate(buf.capacity());
    AtomicReference<ByteBuffer> ref = new AtomicReference(buf);
    Supplier<BitBuffer> sup = () -> new DefaultBitBuffer(
        ref.compareAndSet(buf, null) ? buf : bbsup.get()
    );
    return new MultiBuffer(sup);
  }
  
  public static MultiBuffer multiBuffer(byte[] bs) {
    Supplier<ByteBuffer> bbsup = () -> ByteBuffer.allocate(bs.length);
    AtomicReference<byte[]> ref = new AtomicReference(bs);
    Supplier<BitBuffer> sup = () -> new DefaultBitBuffer(
        ref.compareAndSet(bs, null) ? ByteBuffer.wrap(bs) : bbsup.get()
    );
    return new MultiBuffer(sup);
  }
  
  public static MultiBuffer multiBuffer(byte[] bs, int off, int len) {
    Supplier<ByteBuffer> bbsup = () -> ByteBuffer.allocate(len);
    AtomicReference<byte[]> ref = new AtomicReference(bs);
    Supplier<BitBuffer> sup = () -> new DefaultBitBuffer(
        ref.compareAndSet(bs, null) ? ByteBuffer.wrap(bs, off, len) : bbsup.get()
    );
    return new MultiBuffer(sup);
  }
  
}