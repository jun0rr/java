/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.io.test;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.tinylog.Logger;
import us.pserver.tools.Reflect;
import us.pserver.tools.Timer;
import us.pserver.tools.io.DynamicBuffer;
import us.pserver.tools.io.MultiBuffer;


/**
 *
 * @author juno
 */
public class TestBitBuffersPerformance {
  
  private static final int RANDOM_INTS_SIZE = 100_000;
  
  private static final int[] RANDOM_INTS = createRandomInts();
  
  
  private static int[] createRandomInts() {
    int[] ria = new int[RANDOM_INTS_SIZE];
    Random rdm = new Random();
    IntStream.range(0, RANDOM_INTS_SIZE)
        .forEach(i -> ria[i] = rdm.nextInt());
    return ria;
  }
  
  @Test
  public void test_direct_DynamicBuffer_performance() {
    try {
      DynamicBuffer db = new DynamicBuffer(1024, true);
      Logger.debug("Writing ints to direct DynamicBuffer...");
      Timer tm = new Timer.Nanos().start();
      IntStream.range(0, RANDOM_INTS_SIZE)
          .forEach(i -> db.putInt(RANDOM_INTS[i]));
      Logger.debug(tm.stop());
      Logger.debug("Reading ints from direct DynamicBuffer...");
      db.flip();
      long total = 0;
      tm.clear().start();
      while(db.hasRemaining()) {
        total += db.getInt();
      }
      tm.stop();
      Logger.debug("Done {} - {}", total, tm);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_heap_DynamicBuffer_performance() {
    try {
      DynamicBuffer db = new DynamicBuffer(1024, false);
      Logger.debug("Writing ints to heap DynamicBuffer...");
      Timer tm = new Timer.Nanos().start();
      IntStream.range(0, RANDOM_INTS_SIZE)
          .forEach(i -> db.putInt(RANDOM_INTS[i]));
      Logger.debug(tm.stop());
      Logger.debug("Reading ints from heap DynamicBuffer...");
      db.flip();
      long total = 0;
      tm.clear().start();
      while(db.hasRemaining()) {
        total += db.getInt();
      }
      tm.stop();
      Logger.debug("Done {} - {}", total, tm);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_direct_MultiBuffer_performance() {
    try {
      DecimalFormat df = new DecimalFormat("0.000#ms");
      Supplier<ByteBuffer> sup = () -> ByteBuffer.allocateDirect(4096);
      MultiBuffer db = new MultiBuffer(sup);
      Reflect<MultiBuffer> ref = Reflect.of(db, MethodHandles.lookup()).withPrivateLookup();
      Supplier<Long[]> times = ref.selectField("times").fieldGetterAsSupplier();
      Logger.debug("Writing ints to direct MultiBuffer...");
      Timer tm = new Timer.Nanos().start();
      IntStream.range(0, RANDOM_INTS_SIZE)
          .forEach(i -> db.putInt(RANDOM_INTS[i]));
      Logger.debug(tm.stop());
      Logger.debug("Reading ints from direct DynamicBuffer...");
      db.flip();
      List<Double> millis = Timer.nanosToMillis(Arrays.asList(times.get()));
      Logger.debug("Time 0 - temp.put(): {}", df.format(millis.get(0)));
      Logger.debug("Time 1 - this.put(): {}", df.format(millis.get(1)));
      Logger.debug("Time 2 - ensureSize: {}", df.format(millis.get(2)));
      Logger.debug("Time 3 - put.while : {}", df.format(millis.get(3)));
      long total = 0;
      tm.clear().start();
      while(db.hasRemaining()) {
        total += db.getInt();
      }
      tm.stop();
      Logger.debug("Done {} - {}", total, tm);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void test_heap_MultiBuffer_performance() {
    try {
      Supplier<ByteBuffer> sup = () -> ByteBuffer.allocate(1024);
      DecimalFormat df = new DecimalFormat("0.000#ms");
      MultiBuffer db = new MultiBuffer(sup);
      Reflect<MultiBuffer> ref = Reflect.of(db, MethodHandles.lookup()).withPrivateLookup();
      Supplier<Long[]> times = ref.selectField("times").fieldGetterAsSupplier();
      Logger.debug("Writing ints to direct MultiBuffer...");
      Timer tm = new Timer.Nanos().start();
      IntStream.range(0, RANDOM_INTS_SIZE)
          .forEach(i -> db.putInt(RANDOM_INTS[i]));
      Logger.debug(tm.stop());
      Logger.debug("Reading ints from direct DynamicBuffer...");
      db.flip();
      List<Double> millis = Timer.nanosToMillis(Arrays.asList(times.get()));
      Logger.debug("Time 0 - temp.put(): {}", df.format(millis.get(0)));
      Logger.debug("Time 1 - this.put(): {}", df.format(millis.get(1)));
      Logger.debug("Time 2 - ensureSize: {}", df.format(millis.get(2)));
      Logger.debug("Time 3 - put.while : {}", df.format(millis.get(3)));
      long total = 0;
      tm.clear().start();
      while(db.hasRemaining()) {
        total += db.getInt();
      }
      tm.stop();
      Logger.debug("Done {} - {}", total, tm);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
