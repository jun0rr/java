/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.io.test;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
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
  
  static class DevNull {
    public static final DevNull INSTANCE = new DevNull();
    private final StringBuilder sb = new StringBuilder();
    public void add(Object o) {
      sb.append("* ").append(o).append("\n");
    }
    public void add(int o) {
      sb.append("* ").append(o).append("\n");
    }
    public void add(long o) {
      sb.append("* ").append(o).append("\n");
    }
    public String toString() {
      return sb.toString();
    }
    public static void set(Object o) {
      INSTANCE.add(o);
    }
    public static void set(int o) {
      INSTANCE.add(o);
    }
    public static void set(long o) {
      INSTANCE.add(o);
    }
  }
  
  @RepeatedTest(5)
  public void test_direct_DynamicBuffer_performance() {
    try {
      System.out.println("************************************************************************");
      DynamicBuffer db = new DynamicBuffer(1024, true);
      Logger.debug("Writing ints to direct DynamicBuffer...");
      Timer tm = new Timer.Nanos().start();
      long sum = IntStream.range(0, RANDOM_INTS_SIZE)
          .map(i -> RANDOM_INTS[i])
          .peek(DevNull::set)
          .peek(i -> db.putInt(i))
          .mapToLong(i -> (long)i)
          .sum();
      DevNull.set(sum);
      tm.stop();
      DevNull.set(db);
      Logger.debug("PUT Done {}", tm);
      db.flip();
      Logger.debug("Reading ints from direct DynamicBuffer...");
      sum = 0;
      tm.clear().start();
      while(db.hasRemaining()) {
        int r = db.getInt();
        DevNull.set(r);
        sum += r;
      }
      tm.stop();
      DevNull.set(sum);
      Logger.debug("GET Done {} - {}", sum, tm);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RepeatedTest(5)
  public void test_heap_DynamicBuffer_performance() {
    try {
      System.out.println("************************************************************************");
      DynamicBuffer db = new DynamicBuffer(1024, false);
      Logger.debug("Writing ints to heap DynamicBuffer...");
      Timer tm = new Timer.Nanos().start();
      long sum = IntStream.range(0, RANDOM_INTS_SIZE)
          .map(i -> RANDOM_INTS[i])
          .peek(DevNull::set)
          .peek(i -> db.putInt(i))
          .mapToLong(i -> (long)i)
          .sum();
      DevNull.set(sum);
      tm.stop();
      DevNull.set(db);
      Logger.debug("PUT Done {}", tm);
      db.flip();
      Logger.debug("Reading ints from heap DynamicBuffer...");
      sum = 0;
      tm.clear().start();
      while(db.hasRemaining()) {
        int r = db.getInt();
        DevNull.set(r);
        sum += r;
      }
      tm.stop();
      DevNull.set(sum);
      Logger.debug("GET Done {} - {}", sum, tm);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RepeatedTest(5)
  public void test_direct_MultiBuffer1_performance() {
    try {
      System.out.println("************************************************************************");
      Supplier<ByteBuffer> sup = () -> ByteBuffer.allocateDirect(1024);
      MultiBuffer db = new MultiBuffer(sup);
      Reflect<MultiBuffer> ref = Reflect.of(db, MethodHandles.lookup()).withPrivateLookup();
      Logger.debug("Writing ints to direct MultiBuffer1...");
      Timer tm = new Timer.Nanos().start();
      long sum = IntStream.range(0, RANDOM_INTS_SIZE)
          .map(i -> RANDOM_INTS[i])
          .peek(DevNull::set)
          .peek(i -> db.putInt(i))
          .mapToLong(i -> (long)i)
          .sum();
      DevNull.set(sum);
      tm.stop();
      DevNull.set(db);
      Logger.debug("PUT Done {}", tm);
      db.flip();
      Logger.debug("Reading ints from direct MultiBuffer1...");
      sum = 0;
      tm.clear().start();
      while(db.hasRemaining()) {
        int r = db.getInt();
        DevNull.set(r);
        sum += r;
      }
      tm.stop();
      DevNull.set(sum);
      Logger.debug("GET Done {} - {}", sum, tm);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RepeatedTest(5)
  public void test_heap_MultiBuffer1_performance() {
    try {
      System.out.println("************************************************************************");
      Supplier<ByteBuffer> sup = () -> ByteBuffer.allocate(1024);
      MultiBuffer db = new MultiBuffer(sup);
      Reflect<MultiBuffer> ref = Reflect.of(db, MethodHandles.lookup()).withPrivateLookup();
      Logger.debug("Writing ints to heap MultiBuffer1...");
      Timer tm = new Timer.Nanos().start();
      long sum = IntStream.range(0, RANDOM_INTS_SIZE)
          .map(i -> RANDOM_INTS[i])
          .peek(DevNull::set)
          .peek(i -> db.putInt(i))
          .mapToLong(i -> (long)i)
          .sum();
      DevNull.set(sum);
      tm.stop();
      DevNull.set(db);
      Logger.debug("PUT Done {}", tm);
      db.flip();
      Logger.debug("Reading ints from heap MultiBuffer1...");
      sum = 0;
      tm.clear().start();
      while(db.hasRemaining()) {
        int r = db.getInt();
        DevNull.set(r);
        sum += r;
      }
      tm.stop();
      DevNull.set(sum);
      Logger.debug("GET Done {} - {}", sum, tm);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  //@AfterAll
  public static void showDevNull() {
    System.out.println(DevNull.INSTANCE);
  }
  
}
