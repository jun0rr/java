/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import us.pserver.tools.compile.SourceCode;


/**
 *
 * @author Juno
 */
public class SuperConstructorImpl implements SourceCode {
  
  private final List<ParameterImpl> parameters;
  
  public SuperConstructorImpl(Collection<ParameterImpl> pars) {
    this.parameters = Collections.unmodifiableList(new ArrayList<>(pars));
  }
  
  public List<ParameterImpl> getParameters() {
    return parameters;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder("super(");
    if(!parameters.isEmpty()) {
      parameters.forEach(p->sb.append(p.getName()).append(", "));
      sb.delete(sb.length() -2, sb.length());
    }
    return sb.append("); ").toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.parameters);
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
    final SuperConstructorImpl other = (SuperConstructorImpl) obj;
    if (!Objects.equals(this.parameters, other.parameters)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
