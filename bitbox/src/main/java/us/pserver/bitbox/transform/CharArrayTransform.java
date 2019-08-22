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
public class CharArrayTransform implements BitTransform<char[]> {
  
  public static final byte BYTE_ID = 4;
  
  private final CharSequenceTransform stran = new CharSequenceTransform();
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c.isArray() && c.getComponentType() == char.class;
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][CharSequence]
   * @param cs
   * @param buf
   * @return 
   */
  @Override
  public int box(char[] cs, BitBuffer buf) {
    buf.put(BYTE_ID);
    return 1 + stran.box(new String(cs), buf);
  }
  
  @Override
  public char[] unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a char array buffer (%d)", id, BYTE_ID));
    return stran.unbox(buf).toString().toCharArray();
  }
  
}
