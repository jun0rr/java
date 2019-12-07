/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


/**
 *
 * @author Juno
 */
public class GetterMethodImpl extends MethodImpl {
  
  private final FieldImpl field;
  
  public GetterMethodImpl(Collection<AnnotationImpl> ans, FieldImpl f, int mods) {
    super(ans, Optional.of(f.getType()), getterName(f), Collections.EMPTY_LIST, mods);
    this.field = f;
  }
  
  private static String getterName(FieldImpl f) {
    return "get".concat(f.getName().substring(0, 1).toUpperCase()).concat(f.getName().substring(1));
  }
  
  public FieldImpl getField() {
    return field;
  }
  
  @Override
  public String getSourceCode() {
    return super.getSourceCode()
        .concat(" { return ")
        .concat(field.getName())
        .concat("; } ");
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
