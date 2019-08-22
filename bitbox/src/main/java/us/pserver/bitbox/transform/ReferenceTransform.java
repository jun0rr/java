/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Objects;
import java.util.Optional;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.bitbox.BitTransform;
import us.pserver.bitbox.Reference;
import us.pserver.bitbox.ReferenceService;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class ReferenceTransform implements BitTransform<Reference> {
  
  public static final byte BYTE_ID = 29;
  
  private final ReferenceService service;
  
  private final BitBoxConfiguration cfg;
  
  public ReferenceTransform(ReferenceService service, BitBoxConfiguration cfg) {
    this.service = Objects.requireNonNull(service);
    this.cfg = Objects.requireNonNull(cfg);
  }
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return Reference.class.isAssignableFrom(c);
  }
  
  /**
   * [byte][long][Class]
   * @param obj
   * @param buf
   * @return 
   */
  @Override
  public int box(Reference obj, BitBuffer buf) {
    buf.put(BYTE_ID);
    if(obj == null || Reference.BAD_REFERENCE.equals(obj)) {
      buf.putLong(Long.MIN_VALUE);
      return Long.BYTES;
    }
    int len = 1 + Long.BYTES;
    buf.putLong(obj.getId());
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    len += ctran.box(obj.getType(), buf);
    return len;
  }
  
  @Override
  public Reference unbox(BitBuffer buf) {
    byte bid = buf.get();
    if(BYTE_ID != bid) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a Reference buffer (%d)", bid, BYTE_ID));
    long id = buf.getLong();
    if(Long.MIN_VALUE == id) {
      return Reference.BAD_REFERENCE;
    }
    BitTransform<Class> ctran = cfg.getTransform(Class.class);
    return service.getFor(id, ctran.unbox(buf));
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.of(Reference.class);
  }
  
}
