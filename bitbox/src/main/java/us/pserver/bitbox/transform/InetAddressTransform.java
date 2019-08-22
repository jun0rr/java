package us.pserver.bitbox.transform;

import java.net.InetAddress;
import java.util.Objects;
import java.util.Optional;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.Unchecked;
import us.pserver.tools.io.BitBuffer;

public class InetAddressTransform implements BitTransform<InetAddress> {
  
  public static final byte BYTE_ID = 14;
  
  private final BitBoxConfiguration cfg;
  
  public InetAddressTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return InetAddress.class.isAssignableFrom(c);
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][CharSequence]
   * @param a
   * @param buf
   * @return 
   */
  @Override
  public int box(InetAddress a, BitBuffer buf) {
    BitTransform<String> stran = cfg.getTransform(String.class);
    buf.put(BYTE_ID);
    return 1 + stran.box(a.getHostAddress(), buf);
  }
  
  @Override
  public InetAddress unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not an InetAddress buffer (%d)", id, BYTE_ID));
    BitTransform<String> stran = cfg.getTransform(String.class);
    return Unchecked.call(() ->
        InetAddress.getByName(stran.unbox(buf).toString())
    );
  }
  
}
