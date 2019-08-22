/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.io.jmh;

import java.util.stream.IntStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import us.pserver.tools.io.DynamicBuffer;


/**
 *
 * @author juno
 */
public class DynamicBufferBenchmark {
  
  @Fork(1)
  @Benchmark
  @Warmup(iterations = 3)
  @Measurement(iterations = 5)
  @OperationsPerInvocation(BenchmarkData.MAX_INTS)
  //@BenchmarkMode(Mode.AverageTime)
  //@OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void heap_put_ints_stream(BenchmarkData data, Blackhole blh) {
    long intsSum = IntStream.range(0, data.numOfInts)
        .map(i -> data.ints[i])
        .peek(blh::consume)
        .peek(i -> data.heapDynamic.putInt(i))
        .mapToLong(i -> (long)i)
        .sum();
    blh.consume(intsSum);
  }
  
  @Fork(1)
  @Benchmark
  @Warmup(iterations = 3)
  @Measurement(iterations = 5)
  @OperationsPerInvocation(BenchmarkData.MAX_INTS)
  //@BenchmarkMode(Mode.AverageTime)
  //@OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void heap_put_ints_for(BenchmarkData data, Blackhole blh) {
    long intsSum = 0;
    for(int i = 0; i < data.numOfInts; i++) {
      int r = data.ints[i];
      intsSum += r;
      blh.consume(r);
      blh.consume(data.heapDynamic.putInt(r));
    }
    blh.consume(intsSum);
  }
  
  @Fork(1)
  @Benchmark
  @Warmup(iterations = 3)
  @Measurement(iterations = 5)
  @OperationsPerInvocation(BenchmarkData.MAX_INTS)
  public void heap_get_ints_stream(BenchmarkData data, Blackhole blh) {
    DynamicBuffer buf = data.getFilledHeapDynamic();
    long intsSum = IntStream.range(0, data.numOfInts)
        .map(i -> buf.getInt())
        .peek(blh::consume)
        .mapToLong(i -> (long)i)
        .sum();
    blh.consume(intsSum);
  }
  
  @Fork(1)
  @Benchmark
  @Warmup(iterations = 3)
  @Measurement(iterations = 5)
  @OperationsPerInvocation(BenchmarkData.MAX_INTS)
  public void heap_get_ints_for(BenchmarkData data, Blackhole blh) {
    DynamicBuffer buf = data.getFilledHeapDynamic();
    long intsSum = 0;
    for(int i = 0; i < data.numOfInts; i++) {
      int r = buf.getInt();
      intsSum += r;
      blh.consume(r);
    }
    blh.consume(intsSum);
  }
  
}
