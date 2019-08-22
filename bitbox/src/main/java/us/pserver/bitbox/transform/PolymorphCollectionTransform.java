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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.Indexed;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class PolymorphCollectionTransform implements BitTransform<Collection> {
  
  public static final byte BYTE_ID = 24;
  
  private final PolymorphNodeTransform ptran;
  
  public PolymorphCollectionTransform(BitBoxConfiguration cfg) {
    this.ptran = new PolymorphNodeTransform(Objects.requireNonNull(cfg));
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
   * [byte][int*(length + 1)][PolymorphNode]
   * @param c
   * @param buf
   * @return 
   */
  @Override
  public int box(Collection c, BitBuffer buf) {
    int pos = buf.position();
    buf.put(BYTE_ID);
    if(c == null || c.isEmpty()) {
      buf.putInt(0);
      return Integer.BYTES;
    }
    int len = 1 + Integer.BYTES * (c.size() + 1);
    buf.putInt(c.size());
    int[] idx = new int[c.size()];
    buf.position(pos + len);
    Stream<Indexed<Object>> stream = c.stream().map(Indexed.builder());
    len = stream.peek(i -> idx[i.index()] = buf.position())
        .mapToInt(i -> ptran.box(i.value(), buf))
        .reduce(len, Integer::sum);
    buf.position(pos + 1 + Integer.BYTES);
    IntStream.of(idx).forEach(buf::putInt);
    buf.position(pos + len);
    return len;
  }
  
  @Override
  public Collection unbox(BitBuffer buf) {
    int pos = buf.position();
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a PolymorphCollection buffer (%d)", id, BYTE_ID));
    int size = buf.getInt();
    if(size == 0) {
      return Collections.EMPTY_LIST;
    }
    int[] idx = new int[size];
    IntStream.range(0, size)
        .forEach(i -> idx[i] = buf.getInt());
    return IntStream.of(idx)
        .peek(buf::position)
        .mapToObj(i -> ptran.unbox(buf))
        .collect(Collectors.toList());
  }
  
}
