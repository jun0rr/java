/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Objects;
import java.util.Optional;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class EnumTransform<T extends Enum> implements BitTransform<T>{
  
  public static final byte BYTE_ID = 10;
  
  private final BitBoxConfiguration cfg;
  
  public EnumTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c.isEnum();
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][Class][CharSequence]
   * @param e
   * @param buf
   * @return 
   */
  @Override
  public int box(T e, BitBuffer buf) {
    BitTransform<String> stran = cfg.getTransform(String.class);
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    Class<T> c = (Class<T>) e.getClass();
    buf.put(BYTE_ID);
    return 1 + ctran.box(c, buf) + stran.box(e.name(), buf);
  }


  @Override
  public T unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not an enum buffer (%d)", id, BYTE_ID));
    BitTransform<String> stran = cfg.getTransform(String.class);
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    Class<T> c = (Class<T>) ctran.unbox(buf);
    String name = stran.unbox(buf);
    return (T) Enum.valueOf(c, name);
  }
  
}
