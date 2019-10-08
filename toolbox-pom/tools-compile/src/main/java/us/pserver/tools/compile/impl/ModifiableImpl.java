/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Modifier;
import java.util.Collection;


/**
 *
 * @author juno
 */
public abstract class ModifiableImpl extends Annotated {
  
  protected final int mods;
  
  public ModifiableImpl(Collection<AnnotationImpl> ans, int mods) {
    super(ans);
    this.mods = mods;
  }
  
  public int getModifiers() {
    return mods;
  }
  
  public Scope getScope() {
    return Scope.forMods(mods);
  }
  
  public boolean isStatic() {
    return Modifier.isStatic(mods);
  }
  
  public boolean isAbstract() {
    return Modifier.isAbstract(mods);
  }
  
  public boolean isFinal() {
    return Modifier.isFinal(mods);
  }
  
  public boolean isSynchronized() {
    return Modifier.isSynchronized(mods);
  }
  
  public boolean isTransient() {
    return Modifier.isTransient(mods);
  }
  
  public boolean isVolatile() {
    return Modifier.isVolatile(mods);
  }
  
}
