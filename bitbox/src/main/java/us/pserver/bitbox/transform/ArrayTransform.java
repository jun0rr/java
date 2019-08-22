/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class ArrayTransform<T> implements BitTransform<T[]> {
  
  public static final byte BYTE_ID = 0;
  
  private final BitBoxConfiguration cfg;
  
  public ArrayTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c.isArray() && !c.getComponentType().isPrimitive();
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][int*(elts + 1)][class][object*(length)]
   * @param ts
   * @param buf
   * @return 
   */
  @Override
  public int box(T[] ts, BitBuffer buf) {
    Class<T> cls = (Class<T>) ts.getClass().getComponentType();
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    BitTransform<T> trans = cfg.getTransform(cls);
    int pos = buf.position();
    int len = 1 + Integer.BYTES * (ts.length + 1);
    buf.position(pos + len);
    len += ctran.box(trans.serialType().orElse(cls), buf);
    int[] idx = new int[ts.length];
    int i = 0;
    for(T o : ts) {
      idx[i++] = buf.position();
      len += trans.box(o, buf);
    }
    buf.position(pos).put(BYTE_ID).putInt(ts.length);
    IntStream.of(idx).forEach(buf::putInt);
    buf.position(pos + len);
    return len;
  }
  
  @Override
  public T[] unbox(BitBuffer buf) {
    int pos = buf.position();
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not an array buffer (%d)", id, BYTE_ID));
    int size = buf.getInt();
    buf.position(pos + 1 + Integer.BYTES * (size + 1));
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    Class<T> cls = ctran.unbox(buf);
    BitTransform<T> trans = cfg.getTransform(cls);
    T[] ts = (T[]) Array.newInstance(cls, size);
    IntStream.range(0, size)
        .forEach(i -> ts[i] = trans.unbox(buf));
    return ts;
  }
  
}
