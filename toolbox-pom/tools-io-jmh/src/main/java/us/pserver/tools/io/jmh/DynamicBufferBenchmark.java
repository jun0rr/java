/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.io.jmh;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;


/**
 *
 * @author juno
 */
public class DynamicBufferBenchmark {
  
  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void heap_put_ints_stream(BenchmarkData data, Blackhole blh) {
    IntStream.range(0, data.numOfInts)
        .map(i -> data.ints[i])
        .mapToObj(i -> data.heapDynamic.putInt(i))
        .forEach(blh::consume);
  }
  
  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void heap_put_ints_for(BenchmarkData data, Blackhole blh) {
    for(int i = 0; i < data.numOfInts; i++) {
      int r = data.ints[i];
      blh.consume(data.heapDynamic.putInt(r));
      blh.consume(r);
    }
  }
  
  @Benchmark
  public void heap_get_ints(BenchmarkData data, Blackhole blh) {
    
    long intsSum = IntStream.range(0, data.numOfInts)
        .map(i -> data.h)
  }
  
}
