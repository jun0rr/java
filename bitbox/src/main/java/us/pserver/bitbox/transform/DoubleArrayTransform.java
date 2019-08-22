/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class DoubleArrayTransform implements BitTransform<double[]> {
  
  public static final byte BYTE_ID = 8;
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c.isArray() && c.getComponentType() == double.class;
  }


  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][int][double*(length)]
   * @param ds
   * @param buf
   * @return 
   */
  @Override
  public int box(double[] ds, BitBuffer buf) {
    buf.put(BYTE_ID).putInt(ds.length);
    DoubleStream.of(ds).forEach(buf::putDouble);
    return 1 + Integer.BYTES + ds.length * Double.BYTES;
  }
  
  @Override
  public double[] unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a double array buffer (%d)", id, BYTE_ID));
    int len = buf.getInt();
    double[] ds = new double[len];
    IntStream.range(0, len).forEach(i -> ds[i] = buf.getDouble());
    return ds;
  }
  
}
