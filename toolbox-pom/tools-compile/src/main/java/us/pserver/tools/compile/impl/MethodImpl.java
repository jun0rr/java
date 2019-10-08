/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 *
 * @author juno
 */
public abstract class MethodImpl extends ModifiableImpl implements Nameable {
  
  protected final Optional<Class<?>> retype;
  
  protected final String name;
  
  protected final List<ParameterImpl> parameters;
  
  
  protected MethodImpl(Collection<AnnotationImpl> ans, Optional<Class<?>> r, String n, Collection<ParameterImpl> pars, int mods) {
    super(ans, mods);
    this.retype = r;
    this.name = Objects.requireNonNull(n);
    this.parameters = Collections.unmodifiableList(new ArrayList<>(pars));
  }
  
  public Optional<Class<?>> getReturnType() {
    return retype;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  public List<ParameterImpl> parameters() {
    return parameters;
  }
  
  public String getMethodSignature() {
    List<Class> cpars = parameters.stream()
        .map(ParameterImpl::getType)
        .collect(Collectors.toList());
    return name + cpars.toString();
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode());
    sb.append(getScope().name().toLowerCase()).append(" ");
    if(Modifier.isStatic(mods)) sb.append("static ");
    sb.append(retype.map(Class::getName).orElse("void"))
        .append(" ")
        .append(name)
        .append("( ");
    if(!parameters.isEmpty()) {
      for(int i = 0; i < parameters.size(); i++) {
        sb.append(parameters.get(i));
        if(i < parameters.size() -1) sb.append(", ");
      }
      sb.append(" ");
    }
    return sb.append(")").toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 41 * hash + Objects.hashCode(this.retype);
    hash = 41 * hash + Objects.hashCode(this.name);
    hash = 41 * hash + Objects.hashCode(this.parameters);
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
    if (!MethodImpl.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    final MethodImpl other = (MethodImpl) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.retype, other.retype)) {
      return false;
    }
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
