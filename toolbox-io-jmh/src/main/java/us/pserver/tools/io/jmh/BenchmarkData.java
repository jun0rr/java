/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.io.jmh;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.stream.IntStream;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import us.pserver.tools.io.DynamicBuffer;
import us.pserver.tools.io.MultiBuffer;


/**
 *
 * @author juno
 */
@State(Scope.Benchmark)
public class BenchmarkData {
  
  public static final int MAX_INTS = 100_000;
  
  public int numOfInts = MAX_INTS;
  
  @Param({"1024"})
  public int bufSize;
  
  public DynamicBuffer heapDynamic;
  
  public DynamicBuffer directDynamic;
  
  private DynamicBuffer filledHeapDynamic;
  
  private DynamicBuffer filledDirectDynamic;
  
  public MultiBuffer heapMulti;
  
  public MultiBuffer directMulti;
  
  private MultiBuffer filledHeapMulti;
  
  private MultiBuffer filledDirectMulti;
  
  public int filledLimit;
  
  public int[] ints;
  
  public BenchmarkData() {}
  
  @Setup(Level.Trial)
  public void initData() {
    ints = new int[numOfInts];
    Random rdm = new Random();
    IntStream.range(0, numOfInts)
        .forEach(i -> ints[i] = rdm.nextInt());
    heapDynamic = new DynamicBuffer(bufSize, false);
    directDynamic = new DynamicBuffer(bufSize, true);
    heapMulti = new MultiBuffer(() -> ByteBuffer.allocate(bufSize));
    directMulti = new MultiBuffer(() -> ByteBuffer.allocateDirect(bufSize));
    filledHeapDynamic = new DynamicBuffer(bufSize, false);
    IntStream.range(0, numOfInts)
        .map(i -> ints[i])
        .peek(i -> filledHeapDynamic.putInt(i))
        .peek(i -> filledDirectDynamic.putInt(i))
        .peek(i -> filledHeapMulti.putInt(i))
        .forEach(i -> filledDirectMulti.putInt(i));
    filledLimit = filledHeapDynamic.position();
  }
  
  public DynamicBuffer getFilledHeapDynamic() {
    filledHeapDynamic.position(0).limit(filledLimit);
    return filledHeapDynamic;
  }
  
  public DynamicBuffer getFilledDirectDynamic() {
    filledDirectDynamic.position(0).limit(filledLimit);
    return filledDirectDynamic;
  }
  
  public MultiBuffer getFilledHeapMulti() {
    filledHeapMulti.position(0).limit(filledLimit);
    return filledHeapMulti;
  }
  
  public MultiBuffer getFilledDirectMulti() {
    filledDirectMulti.position(0).limit(filledLimit);
    return filledDirectMulti;
  }
  
}
