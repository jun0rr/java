/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


/**
 *
 * @author juno
 */
public class ConstructorImpl extends ModifiableImpl {
  
  private final String name;
  
  private final List<ParameterImpl> parameters;
  
  private final List<FieldInitializer> inits;
  
  public ConstructorImpl(String name, int mods) {
    super(mods);
    this.name = Objects.requireNonNull(name);
    this.parameters = new ArrayList<>();
    this.inits = new ArrayList<>();
  }
  
  public ConstructorImpl(Constructor c, String name, int mods) {
    this(name, mods);
    Arrays.asList(c.getParameters()).stream()
        .map(p->new ParameterImpl(p))
        .forEach(parameters::add);
  }
  
  public ConstructorImpl(Constructor c, String name) {
    this(c, name, c.getModifiers());
  }
  
  public List<ParameterImpl> parameters() {
    return parameters;
  }
  
  public List<FieldInitializer> fieldInitializers() {
    return inits;
  }
  
  public ConstructorImpl add(ParameterImpl pi) {
    if(pi != null) {
      Predicate<ParameterImpl> match = p->pi.getName().equals(p.getName()) 
          && pi.getType().equals(p.getType());
      parameters.stream().filter(match).findAny().ifPresent(parameters::remove);
      parameters.add(pi);
    }
    return this;
  }
  
  public ConstructorImpl add(FieldInitializer fi) {
    if(fi != null) {
      Predicate<FieldInitializer> match = f->fi.getField().equals(f.getField()) 
          && fi.getType().equals(f.getType());
      inits.stream().filter(match).findAny().ifPresent(inits::remove);
      inits.add(fi);
    }
    return this;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode())
        .append(getScope().name().toLowerCase())
        .append(" ")
        .append(name)
        .append("(");
    parameters.forEach(p->sb.append(p.getSourceCode()).append(","));
    if(!parameters.isEmpty()) sb.deleteCharAt(sb.length() -1);
    sb.append(") { ").append("super(");
    parameters.forEach(p->sb.append(p.getName()).append(","));
    if(!parameters.isEmpty()) sb.deleteCharAt(sb.length() -1);
    sb.append("); ");
    inits.forEach(f->sb.append(f.getSourceCode()));
    return sb.append("} ").toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.name);
    hash = 97 * hash + Objects.hashCode(this.parameters);
    hash = 97 * hash + this.getModifiers();
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if(obj == null) {
      return false;
    }
    if(getClass() != obj.getClass()) {
      return false;
    }
    final ConstructorImpl other = (ConstructorImpl) obj;
    if(this.getModifiers() != other.getModifiers()) {
      return false;
    }
    if(!Objects.equals(this.name, other.name)) {
      return false;
    }
    if(!Objects.equals(this.parameters, other.parameters)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
