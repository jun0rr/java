/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Optional;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class ByteArrayTransform implements BitTransform<byte[]> {
  
  public static final byte BYTE_ID = 2;
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c.isArray() && c.getComponentType() == byte.class;
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][int][byte*(length)]
   * @param bs
   * @param buf
   * @return 
   */
  @Override
  public int box(byte[] bs, BitBuffer buf) {
    buf.put(BYTE_ID).putInt(bs.length);
    buf.put(bs);
    return 1 + bs.length + Integer.BYTES;
  }
  
  @Override
  public byte[] unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a byte array buffer (%d)", id, BYTE_ID));
    int len = buf.getInt();
    byte[] bs = new byte[len];
    buf.get(bs);
    return bs;
  }
  
}
