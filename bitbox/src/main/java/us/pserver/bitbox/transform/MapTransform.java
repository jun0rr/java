package us.pserver.bitbox.transform;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.Pair;
import us.pserver.tools.io.BitBuffer;

public class MapTransform<K,V> implements BitTransform<Map<K,V>> {
  
  public static final byte BYTE_ID = 23;
  
  private final BitBoxConfiguration cfg;
  
  public MapTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
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
  
  private static <K,V> Stream<Pair<K,V>> combine(List<K> lk, List<V> lv) {
    int size = Math.min(lk.size(), lv.size());
    return IntStream.range(0, size)
        .mapToObj(i -> new Pair<>(lk.get(i), lv.get(i)));
  }
  
  /**
   * [byte][int][List][List]
   * @param m
   * @param buf
   * @return 
   */
  @Override
  public int box(Map<K, V> m, BitBuffer buf) {
    int startPos = buf.position();
    buf.put(BYTE_ID);
    int len = 1 + Integer.BYTES;
    if(m.isEmpty()) {
      buf.putInt(0);
      return len;
    }
    List<K> lk = new ArrayList<>(m.keySet());
    List<V> lv = new ArrayList<>();
    lk.forEach(k -> lv.add(m.get(k)));
    BitTransform<List> trans = cfg.getTransform(List.class);
    buf.position(startPos + len);
    len += trans.box(lk, buf);
    int vpos = buf.position();
    len += trans.box(lv, buf);
    buf.putInt(startPos + 1, vpos);
    buf.position(startPos + len);
    return len;
  }
  
  @Override
  public Map<K, V> unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a map buffer (%d)", id, BYTE_ID));
    int vpos = buf.getInt();
    Map<K,V> m = new HashMap<>();
    if(vpos == 0) return m;
    BitTransform<List> trans = cfg.getTransform(List.class);
    List<K> lk = (List<K>) trans.unbox(buf);
    List<V> lv = (List<V>) trans.unbox(buf.position(vpos));
    combine(lk, lv).forEach(p -> m.put(p.a, p.b));
    return m;
  }
  
}
