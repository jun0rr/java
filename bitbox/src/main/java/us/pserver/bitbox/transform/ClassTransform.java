package us.pserver.bitbox.transform;

import java.util.Objects;
import java.util.Optional;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.Unchecked;
import us.pserver.tools.io.BitBuffer;


public class ClassTransform implements BitTransform<Class> {
  
  public static final byte BYTE_ID = 6;
  
  private final BitBoxConfiguration cfg;
  
  public ClassTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c == Class.class;
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][CharSequence]
   * @param c
   * @param buf
   * @return 
   */
  @Override
  public int box(Class c, BitBuffer buf) {
    //Logger.debug(c);
    buf.put(BYTE_ID);
    return 1 + cfg.getTransform(String.class).box(c.getName(), buf);
  }
  
  @Override
  public Class unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a class buffer (%d)", id, BYTE_ID));
    String sc = cfg.getTransform(String.class).unbox(buf);
    //Logger.debug(sc);
    Class c = Unchecked.call(() -> cfg.lookup().findClass(sc));
    //Logger.debug(c);
    return c;
  }
  
}
