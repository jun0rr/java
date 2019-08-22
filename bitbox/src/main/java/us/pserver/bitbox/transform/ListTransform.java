package us.pserver.bitbox.transform;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;


public class ListTransform<T> implements BitTransform<List<T>> {
  
  public static final byte BYTE_ID = 18;
  
  private final BitBoxConfiguration cfg;
  
  public ListTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return List.class.isAssignableFrom(c);
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.of(List.class);
  }
  
  /**
   * [byte][Collection]
   * @param l
   * @param buf
   * @return 
   */
  @Override
  public int box(List<T> l, BitBuffer buf) {
    BitTransform<Collection> trans = cfg.getTransform(Collection.class);
    buf.put(BYTE_ID);
    return 1 + trans.box(l, buf);
  }
  
  @Override
  public List<T> unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a List buffer (%d)", id, BYTE_ID));
    BitTransform<Collection> trans = cfg.getTransform(Collection.class);
    return (List<T>) trans.unbox(buf);
  }
  
}
