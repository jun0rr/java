/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Modifier;
import us.pserver.tools.compile.SourceCode;


/**
 *
 * @author juno
 */
public class ModifiersImpl implements SourceCode {
  
  private final int mods;
  
  public ModifiersImpl(int mods) {
    this.mods = mods;
  }
  
  public int getModifiers() {
    return mods;
  }
  
  public boolean isPublic() {
    return Modifier.isPublic(mods);
  }
  
  public boolean isPrivate() {
    return Modifier.isPrivate(mods);
  }
  
  public boolean isProtected() {
    return Modifier.isProtected(mods);
  }
  
  public boolean isFinal() {
    return Modifier.isFinal(mods);
  }
  
  public boolean isAbstract() {
    return Modifier.isAbstract(mods);
  }
  
  public boolean isInterface() {
    return Modifier.isInterface(mods);
  }
  
  public boolean isNative() {
    return Modifier.isNative(mods);
  }
  
  public boolean isStatic() {
    return Modifier.isStatic(mods);
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
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder();
    if(isPrivate()) sb.append("private ");
    else if(isProtected()) sb.append("protected ");
    else if(isPublic()) sb.append("public ");
    if(isInterface()) sb.append("interface ");
    else if(isAbstract()) sb.append("abstract ");
    if(isStatic()) sb.append("static ");
    if(isSynchronized()) sb.append("synchronized ");
    if(isTransient()) sb.append("transient ");
    if(isVolatile()) sb.append("volatile ");
    else if(isFinal()) sb.append("final ");
    return sb.toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 43 * hash + this.mods;
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ModifiersImpl other = (ModifiersImpl) obj;
    return this.mods == other.mods;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
