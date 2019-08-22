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
public class IntArrayTransform implements BitTransform<int[]> {
  
  public static final byte BYTE_ID = 16;
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c.isArray() && c.getComponentType() == int.class;
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][int*(length + 1)]
   * @param is
   * @param buf
   * @return 
   */
  @Override
  public int box(int[] is, BitBuffer buf) {
    buf.put(BYTE_ID).putInt(is.length);
    IntStream.of(is).forEach(buf::putInt);
    return 1 + Integer.BYTES + Integer.BYTES * is.length;
  }
  
  @Override
  public int[] unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not an int array buffer (%d)", id, BYTE_ID));
    int len = buf.getInt();
    int[] is = new int[len];
    IntStream.range(0, len).forEach(i -> is[i] = buf.getInt());
    return is;
  }
  
}
