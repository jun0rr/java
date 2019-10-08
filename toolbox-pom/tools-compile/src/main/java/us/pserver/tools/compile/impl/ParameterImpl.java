/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Objects;
import us.pserver.tools.compile.SourceCode;


/**
 *
 * @author juno
 */
public class ParameterImpl extends Annotated implements SourceCode, Typeable, Nameable {
  
  private final Class type;
  
  private final String name;
  
  public ParameterImpl(Collection<AnnotationImpl> ans, Class type, String name) {
    super(ans);
    this.type = Objects.requireNonNull(type);
    this.name = Objects.requireNonNull(name);
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public Class getType() {
    return type;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode());
    return sb.append(type.getName()).append(" ").append(name).toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 3;
    hash = 67 * hash + Objects.hashCode(this.type);
    hash = 67 * hash + Objects.hashCode(this.name);
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
    final ParameterImpl other = (ParameterImpl) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.type, other.type)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
