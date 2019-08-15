package us.pserver.bitbox.transform;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


public class SetTransform<T> implements BitTransform<Set<T>> {
  
  public static final byte BYTE_ID = 30;
  
  private final BitBoxConfiguration cfg;
  
  public SetTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return Set.class.isAssignableFrom(c);
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.of(Set.class);
  }
  
  @Override
  public int box(Set<T> s, BitBuffer buf) {
    BitTransform<Collection> trans = cfg.getTransform(Collection.class);
    return trans.box(s, buf);
  }
  
  @Override
  public Set<T> unbox(BitBuffer buf) {
    BitTransform<Collection> trans = cfg.getTransform(Collection.class);
    return new HashSet<>(trans.unbox(buf));
  }
  
}
