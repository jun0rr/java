/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class PolymorphMapTransform implements BitTransform<Map>{
  
  public static final byte BYTE_ID = 26;
  
  private final PolymorphEntryTransform etran;

  public PolymorphMapTransform(BitBoxConfiguration cfg) {
    this.etran = new PolymorphEntryTransform(Objects.requireNonNull(cfg));
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return Map.class.isAssignableFrom(c);
  }


  @Override
  public Optional<Class> serialType() {
    return Optional.of(Map.class);
  }
  
  /**
   * [byte][int][PolymorphEntry*(length)]
   * @param m
   * @param b
   * @return 
   */
  @Override
  public int box(Map m, BitBuffer b) {
    int len = 1 + Integer.BYTES;
    b.put(BYTE_ID).putInt(m.size());
    Set<Map.Entry> entries = m.entrySet();
    return entries.stream()
        .mapToInt(e -> etran.box(e, b))
        .reduce(len, Integer::sum);
  }
  
  @Override
  public Map unbox(BitBuffer b) {
    byte id = b.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a PolymorphMap buffer (%d)", id, BYTE_ID));
    int size = b.getInt();
    return IntStream.range(0, size)
        .mapToObj(i -> etran.unbox(b))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  
}
