/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.io.test;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tinylog.Logger;
import us.pserver.tools.io.BitBuffer;
import us.pserver.tools.io.DefaultBitBuffer;
import us.pserver.tools.IndexedInt;
import us.pserver.tools.Reflect;
import us.pserver.tools.io.MultiBitBuffer;


/**
 *
 * @author juno
 */
public class TestMultiBuffer {
  
  @Test
  public void test_put_flip_get() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Logger.debug(buf);
      byte[] bs = new byte[30];
      IntStream.range(1, 31)
          .mapToObj(IndexedInt.builder())
          .forEach(i -> bs[i.index()] = (byte) i.value());
      buf.put(bs);
      Logger.debug(buf);
      buf.flip();
      byte[] other = new byte[30];
      buf.get(other);
      Logger.debug(buf);
      Assertions.assertArrayEquals(bs, other);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_set_posisition_gt_capacity() throws Exception {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Logger.debug(buf);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>fieldGetterAsSupplier().get();
      Assertions.assertEquals(0, buf.position());
      Assertions.assertEquals(10, buf.limit());
      Assertions.assertEquals(10, buf.capacity());
      Assertions.assertEquals(0, index.getAsInt());
      Assertions.assertEquals(0, buffers.get(0).position());
      
      buf.position(18);
      Logger.debug(buf);
      Assertions.assertEquals(18, buf.position());
      Assertions.assertEquals(20, buf.limit());
      Assertions.assertEquals(20, buf.capacity());
      Assertions.assertEquals(1, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(8, buffers.get(1).position());
      
      buf.position(0);
      Logger.debug(buf);
      Assertions.assertEquals(0, buf.position());
      Assertions.assertEquals(0, index.getAsInt());
      Assertions.assertEquals(0, buffers.get(0).position());
      Assertions.assertEquals(0, buffers.get(1).position());
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_set_posisition_gt_limit() throws Exception {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Logger.debug(buf);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>fieldGetterAsSupplier().get();
      Assertions.assertEquals(0, buf.position());
      Assertions.assertEquals(10, buf.limit());
      Assertions.assertEquals(10, buf.capacity());
      Assertions.assertEquals(0, index.getAsInt());
      Assertions.assertEquals(0, buffers.get(0).position());
      
      buf.limit(15);
      buf.position(16);
      Logger.debug(buf);
      Assertions.assertEquals(16, buf.position());
      Assertions.assertEquals(20, buf.limit());
      Assertions.assertEquals(20, buf.capacity());
      Assertions.assertEquals(4, buf.remaining());
      Assertions.assertEquals(1, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(6, buffers.get(1).position());
      
      buf.position(0);
      Logger.debug(buf);
      Assertions.assertEquals(0, buf.position());
      Assertions.assertEquals(0, index.getAsInt());
      Assertions.assertEquals(0, buffers.get(0).position());
      Assertions.assertEquals(0, buffers.get(1).position());
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_put_get_int_growing() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>fieldGetterAsSupplier().get();
      byte[] bs = new byte[8];
      IntStream.range(1, 9).mapToObj(IndexedInt.builder()).forEach(i -> bs[i.index()] = (byte)i.value());
      
      buf.put(bs);
      Assertions.assertEquals(8, buf.position());
      Assertions.assertEquals(10, buf.limit());
      Assertions.assertEquals(10, buf.capacity());
      Assertions.assertEquals(0, index.getAsInt());
      Assertions.assertEquals(8, buffers.get(0).position());
      Logger.debug(buf);
      
      buf.putInt(Integer.MAX_VALUE);
      Assertions.assertEquals(12, buf.position());
      Assertions.assertEquals(20, buf.limit());
      Assertions.assertEquals(20, buf.capacity());
      Assertions.assertEquals(1, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(2, buffers.get(1).position());

      Logger.debug(buf);
      Assertions.assertEquals(Integer.MAX_VALUE, buf.getInt(8));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_put_get_double_growing() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>fieldGetterAsSupplier().get();
      byte[] bs = new byte[8];
      IntStream.range(1, 9).mapToObj(IndexedInt.builder()).forEach(i -> bs[i.index()] = (byte)i.value());
      
      buf.put(bs);
      Assertions.assertEquals(8, buf.position());
      Assertions.assertEquals(10, buf.limit());
      Assertions.assertEquals(10, buf.capacity());
      Assertions.assertEquals(0, index.getAsInt());
      Assertions.assertEquals(8, buffers.get(0).position());
      Logger.debug(buf);
      
      buf.putDouble(Double.MAX_VALUE);
      Assertions.assertEquals(16, buf.position());
      Assertions.assertEquals(20, buf.limit());
      Assertions.assertEquals(20, buf.capacity());
      Assertions.assertEquals(1, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(6, buffers.get(1).position());

      Logger.debug(buf);
      Assertions.assertEquals(Double.MAX_VALUE, buf.getDouble(8));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_put_utf8_growing() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>fieldGetterAsSupplier().get();
      
      String str = "Vivamus porta orci aliquam";
      
      buf.putUTF8(str);
      Assertions.assertEquals(26, buf.position());
      Assertions.assertEquals(30, buf.limit());
      Assertions.assertEquals(30, buf.capacity());
      Assertions.assertEquals(2, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(10, buffers.get(1).position());
      Assertions.assertEquals(6, buffers.get(2).position());
      Logger.debug(buf);
      
      buf.putDouble(Double.MAX_VALUE);
      Assertions.assertEquals(34, buf.position());
      Assertions.assertEquals(40, buf.limit());
      Assertions.assertEquals(40, buf.capacity());
      Assertions.assertEquals(3, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(10, buffers.get(1).position());
      Assertions.assertEquals(10, buffers.get(2).position());
      Assertions.assertEquals(4, buffers.get(3).position());

      Logger.debug(buf);
      buf.flip();
      Assertions.assertEquals(str, buf.getUTF8(str.length()));
      Assertions.assertEquals(Double.MAX_VALUE, buf.getDouble());
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_toByteBuffer() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>dynamicFieldGetterAsFunction().apply(buf);
      
      IntStream.range(10101, 10111).forEach(i -> buf.putInt(i));
      Assertions.assertEquals(40, buf.position());
      Assertions.assertEquals(40, buf.limit());
      Assertions.assertEquals(40, buf.capacity());
      Assertions.assertEquals(3, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(10, buffers.get(1).position());
      Assertions.assertEquals(10, buffers.get(2).position());
      Assertions.assertEquals(10, buffers.get(3).position());
      Logger.debug(buf);
      
      buf.flip();
      ByteBuffer bb = buf.toByteBuffer();
      IntStream.range(10101, 10111)
          .peek(i -> Assertions.assertEquals(i, buf.getInt()))
          .forEach(i -> Assertions.assertEquals(i, bb.getInt()));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_toByteArray() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>dynamicFieldGetterAsFunction().apply(buf);
      
      IntStream.range(10101, 10111).forEach(i -> buf.putInt(i));
      Assertions.assertEquals(40, buf.position());
      Assertions.assertEquals(40, buf.limit());
      Assertions.assertEquals(40, buf.capacity());
      Assertions.assertEquals(3, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(10, buffers.get(1).position());
      Assertions.assertEquals(10, buffers.get(2).position());
      Assertions.assertEquals(10, buffers.get(3).position());
      Logger.debug(buf);
      
      buf.flip();
      ByteBuffer bb = ByteBuffer.wrap(buf.toByteArray());
      IntStream.range(10101, 10111)
          .peek(i -> Assertions.assertEquals(i, buf.getInt()))
          .forEach(i -> Assertions.assertEquals(i, bb.getInt()));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_writeTo_ByteArray() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>dynamicFieldGetterAsFunction().apply(buf);
      
      IntStream.range(10101, 10111).forEach(i -> buf.putInt(i));
      Assertions.assertEquals(40, buf.position());
      Assertions.assertEquals(40, buf.limit());
      Assertions.assertEquals(40, buf.capacity());
      Assertions.assertEquals(3, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(10, buffers.get(1).position());
      Assertions.assertEquals(10, buffers.get(2).position());
      Assertions.assertEquals(10, buffers.get(3).position());
      Logger.debug(buf);
      
      buf.flip();
      ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES * 10);
      buf.writeTo(bb);
      bb.flip();
      buf.flip();
      IntStream.range(10101, 10111)
          .peek(i -> Assertions.assertEquals(i, buf.getInt()))
          .forEach(i -> Assertions.assertEquals(i, bb.getInt()));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_readFrom_ByteArray() {
    try {
      ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES * 10);
      IntStream.range(10101, 10111).forEach(i -> bb.putInt(i));
      bb.flip();
      
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBitBuffer buf = new MultiBitBuffer(s);
      Reflect<MultiBitBuffer> ref = Reflect.of(buf, MethodHandles.lookup()).withPrivateLookup();
      IntSupplier index = ref.selectMethod("index").methodAsLambda(IntSupplier.class);
      List<BitBuffer> buffers = ref.selectField("buffers").<List<BitBuffer>>dynamicFieldGetterAsFunction().apply(buf);
      
      buf.readFrom(bb);
      Assertions.assertEquals(40, buf.position());
      Assertions.assertEquals(40, buf.limit());
      Assertions.assertEquals(40, buf.capacity());
      Assertions.assertEquals(3, index.getAsInt());
      Assertions.assertEquals(10, buffers.get(0).position());
      Assertions.assertEquals(10, buffers.get(1).position());
      Assertions.assertEquals(10, buffers.get(2).position());
      Assertions.assertEquals(10, buffers.get(3).position());
      Logger.debug(buf);
      
      buf.flip();
      bb.flip();
      IntStream.range(10101, 10111)
          .peek(i -> Assertions.assertEquals(i, buf.getInt()))
          .forEach(i -> Assertions.assertEquals(i, bb.getInt()));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
