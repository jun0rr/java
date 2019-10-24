/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


/**
 *
 * @author juno
 */
public class FieldImpl extends ParameterImpl {
  
  private final Optional<FieldInitializer> init;
  
  private final int mods;
  
  public FieldImpl(Collection<AnnotationImpl> ans, Class type, String name, Optional<FieldInitializer> init, int mods) {
    super(ans, type, name);
    this.mods = mods;
    this.init = init;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(
        Scope.forMods(mods).name().toLowerCase()
    ).append(" ");
    if(Modifier.isStatic(mods)) sb.append("static ");
    if(Modifier.isTransient(mods)) sb.append("transient ");
    if(Modifier.isVolatile(mods)) sb.append("volatile ");
    else if(Modifier.isFinal(mods)) sb.append("final ");
    sb.append(getType().getName())
        .append(" ")
        .append(getName());
    if(init.isPresent()) {
      String sini = init.get().getSourceCode();
      sb.append(" ").append(sini.substring(sini.indexOf("=")));
    }
    else {
      sb.append("; ");
    }
    return sb.toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 13 * hash + this.mods;
    hash = 13 * hash + Objects.hashCode(this.getName());
    hash = 13 * hash + Objects.hashCode(this.getType());
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
    final FieldImpl other = (FieldImpl) obj;
    if (this.mods != other.mods) {
      return false;
    }
    if(!Objects.equals(this.getName(), other.getName())) {
      return false;
    }
    if(!Objects.equals(this.getType(), other.getType())) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }

}
