/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.test;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tinylog.Logger;
import us.pserver.bitbox.BitBuffer;
import us.pserver.bitbox.impl.DefaultBitBuffer;
import us.pserver.bitbox.impl.MultiBuffer;
import us.pserver.tools.IndexedInt;


/**
 *
 * @author juno
 */
public class TestMultiBuffer {
  
  @Test
  public void test_put_flip_get() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBuffer buf = new MultiBuffer(s);
      System.out.println(buf);
      byte[] bs = new byte[30];
      IntStream.range(1, 31)
          .mapToObj(IndexedInt.builder())
          .forEach(i -> bs[i.index()] = (byte) i.value());
      buf.put(bs);
      System.out.println(buf);
      buf.flip();
      byte[] other = new byte[30];
      buf.get(other);
      System.out.println(buf);
      Assertions.assertArrayEquals(bs, other);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_pos_gt_cap() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBuffer buf = new MultiBuffer(s);
      Logger.debug(buf);
      buf.position(18);
      Logger.debug(buf);
      Logger.debug(buf.buffers2string());
      buf.position(0);
      Logger.debug(buf);
      Logger.debug(buf.buffers2string());
      //Assertions.assertEquals(Byte.MAX_VALUE, Byte.MAX_VALUE);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_put_int_growing() {
    try {
      Supplier<BitBuffer> s = () -> new DefaultBitBuffer(10, false);
      MultiBuffer buf = new MultiBuffer(s);
      byte[] bs = new byte[8];
      IntStream.range(1, 9).mapToObj(IndexedInt.builder()).forEach(i -> bs[i.index()] = (byte)i.value());
      buf.put(bs);
      Logger.debug(buf);
      buf.putInt(Integer.MAX_VALUE);
      Logger.debug(buf);
      Logger.debug(buf.buffers2string());
      Assertions.assertEquals(Integer.MAX_VALUE, buf.getInt(8));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
