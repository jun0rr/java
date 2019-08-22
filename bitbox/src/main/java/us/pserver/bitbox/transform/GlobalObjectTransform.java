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
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Stream;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.bitbox.spec.GetterTarget;
import us.pserver.bitbox.spec.ObjectSpec;
import us.pserver.tools.Indexed;
import us.pserver.tools.Pair;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class GlobalObjectTransform implements BitTransform<Object> {
  
  public static final byte BYTE_ID = 13;
  
  private final PolymorphMapTransform dtran;
  
  private final BitBoxConfiguration cfg;
  
  public GlobalObjectTransform(BitBoxConfiguration cfg) {
    this.cfg = Objects.requireNonNull(cfg);
    this.dtran = new PolymorphMapTransform(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return !cfg.containsTransform(c);
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  private ObjectSpec getOrCreateSpec(Class c) {
    Optional<ObjectSpec> opt = cfg.specFor(c);
    if(opt.isEmpty()) {
      ObjectSpec spec = ObjectSpec.createSpec(c, cfg);
      cfg.addSpec(spec);
      return spec;
    }
    return opt.get();
  }
  
  /**
   * [byte][int][Class][PolymorphMap]
   * @param o
   * @param b
   * @return 
   */
  @Override
  public int box(Object o, BitBuffer b) {
    int startPos = b.position();
    b.put(BYTE_ID);
    if(o == null) {
      b.putInt(0);
      return 1 + Integer.BYTES;
    }
    Class c = o.getClass();
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    ObjectSpec spec = getOrCreateSpec(c);
    Map<String,Object> m = obj2map(o, spec);
    int len = 1 + Integer.BYTES;
    b.position(startPos + len);
    len += ctran.box(spec.serialType().orElse(c), b);
    int mpos = b.position();
    len += dtran.box(m, b);
    b.putInt(startPos + 1, mpos);
    b.position(startPos + len);
    return len;
  }
  
  private Map<String,Object> obj2map(Object obj, ObjectSpec spec) {
    Map<String,Object> m = new TreeMap<>();
    Set<GetterTarget> getters = spec.getters();
    getters.stream()
        .map(g -> new Pair<String,Object>(g.getName(), g.apply(obj)))
        .filter(p -> p.a != null && p.b != null)
        .forEach(p -> m.put(p.a, p.b));
    return m;
  }
  
  @Override
  public Object unbox(BitBuffer b) {
    int pos = b.position();
    byte id = b.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not an object buffer (%d)", id, BYTE_ID));
    int mpos = b.getInt();
    if(mpos == 0) return null;
    b.position(pos + 1 + Integer.BYTES);
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    Class c = ctran.unbox(b);
    Map m = dtran.unbox(b);
    ObjectSpec spec = getOrCreateSpec(c);
    Object[] args = new Object[spec.constructor().getParameterTypes().size()];
    Stream<String> pars = spec.constructor().getParameterNames().stream();
    pars.map(Indexed.builder())
        .forEach(x -> args[x.index()] = m.get(x.value()));
    return spec.constructor().apply(args);
  }
  
}
