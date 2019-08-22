/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class CollectionTransform<T> implements BitTransform<Collection<T>> {
  
  public static final byte BYTE_ID = 7;
  
  private final BitBoxConfiguration cfg;
  
  public CollectionTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return Collection.class.isAssignableFrom(c);
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.of(Collection.class);
  }
  
  /**
   * [byte][int*(length+1)][Class][object*(length)]
   * @param c
   * @param buf
   * @return 
   */
  @Override
  public int box(Collection<T> c, BitBuffer buf) {
    int pos = buf.position();
    buf.put(BYTE_ID);
    if(c == null || c.isEmpty()) {
      buf.putInt(0);
      return 1 + Integer.BYTES;
    }
    Class<T> cls = (Class<T>) c.stream()
        .map(Object::getClass)
        .findAny().get();
    BitTransform<T> trans = cfg.getTransform(cls);
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    int len = 1 + Integer.BYTES * (c.size() + 1);
    buf.position(pos + len);
    len += ctran.box(trans.serialType().orElse(cls), buf);
    int[] idx = new int[c.size()];
    int i = 0;
    for(T o : c) {
      if(o == null) {
        idx[i++] = -1;
      }
      else {
        idx[i++] = buf.position();
        len += trans.box(o, buf);
      }
    }
    buf.position(pos).putInt(c.size());
    IntStream.of(idx).forEach(buf::putInt);
    buf.position(pos + len);
    return len;
  }
  
  @Override
  public Collection<T> unbox(BitBuffer buf) {
    int pos = buf.position();
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a collection buffer (%d)", id, BYTE_ID));
    int size = buf.getInt();
    if(size == 0) {
      return Collections.EMPTY_LIST;
    }
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    int[] idx = new int[size];
    IntStream.range(0, size)
        .forEach(i -> idx[i] = buf.getInt());
    Class<T> cls = ctran.unbox(buf);
    BitTransform<T> trans = cfg.getTransform(cls);
    return IntStream.of(idx)
        .filter(i -> i >= 0)
        .mapToObj(i -> trans.unbox(buf.position(i)))
        .collect(Collectors.toList());
  }
  
}
