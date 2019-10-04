/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.lang.reflect.Modifier;


/**
 *
 * @author juno
 */
public abstract class ModifiableImpl extends Annotated implements SourceCode {
  
  protected final int mods;
  
  public ModifiableImpl(int mods) {
    super();
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
