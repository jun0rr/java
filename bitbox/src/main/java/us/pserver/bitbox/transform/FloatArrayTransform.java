/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Optional;
import java.util.stream.IntStream;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class FloatArrayTransform implements BitTransform<float[]> {
  
  public static final byte BYTE_ID = 11;
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c.isArray() && c.getComponentType() == float.class;
  }


  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][int][float*(length)]
   * @param ds
   * @param buf
   * @return 
   */
  @Override
  public int box(float[] ds, BitBuffer buf) {
    buf.put(BYTE_ID).putInt(ds.length);
    IntStream.range(0, ds.length).forEach(i -> buf.putFloat(ds[i]));
    return 1 + Integer.BYTES + ds.length * Float.BYTES;
  }
  
  @Override
  public float[] unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a float array buffer (%d)", id, BYTE_ID));
    int len = buf.getInt();
    float[] ds = new float[len];
    IntStream.range(0, len).forEach(i -> ds[i] = buf.getFloat());
    return ds;
  }
  
}
