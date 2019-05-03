/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.transform;

import java.util.Objects;
import java.util.Optional;
import us.pserver.bitbox.BitBoxRegistry;
import us.pserver.bitbox.BitTransform;
import us.pserver.bitbox.Reference;
import us.pserver.bitbox.ReferenceService;
import us.pserver.bitbox.impl.BitBuffer;


/**
 *
 * @author juno
 */
public class ReferenceTransform implements BitTransform<Reference> {
  
  private final ReferenceService service;
  
  public ReferenceTransform(ReferenceService service) {
    this.service = Objects.requireNonNull(service);
  }
  
  @Override
  public boolean match(Class c) {
    return Reference.class.isAssignableFrom(c);
  }
  
  @Override
  public int box(Reference obj, BitBuffer buf) {
    if(obj == null || Reference.BAD_REFERENCE.equals(obj)) {
      buf.putLong(Long.MIN_VALUE);
      return Long.BYTES;
    }
    int len = Long.BYTES;
    buf.putLong(obj.getId());
    BitTransform<Class> ctran = BitBoxRegistry.INSTANCE.getAnyTransform(Class.class);
    len += ctran.box(obj.getType(), buf);
    return len;
  }
  
  @Override
  public Reference unbox(BitBuffer buf) {
    long id = buf.getLong();
    if(Long.MIN_VALUE == id) {
      return Reference.BAD_REFERENCE;
    }
    BitTransform<Class> ctran = BitBoxRegistry.INSTANCE.getAnyTransform(Class.class);
    return service.getFor(id, ctran.unbox(buf));
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.of(Reference.class);
  }
  
}