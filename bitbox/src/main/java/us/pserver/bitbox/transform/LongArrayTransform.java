/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class LongArrayTransform implements BitTransform<long[]> {
  
  public static final byte BYTE_ID = 21;
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c.isArray() && c.getComponentType() == long.class;
  }


  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][int][long*(length)]
   * @param ls
   * @param buf
   * @return 
   */
  @Override
  public int box(long[] ls, BitBuffer buf) {
    buf.put(BYTE_ID).putInt(ls.length);
    LongStream.of(ls).forEach(buf::putLong);
    return 1 + Integer.BYTES + Long.BYTES * ls.length;
  }
  
  @Override
  public long[] unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a long array buffer (%d)", id, BYTE_ID));
    int len = buf.getInt();
    long[] ls = new long[len];
    IntStream.range(0, len).forEach(i -> ls[i] = buf.getLong());
    return ls;
  }
  
}
