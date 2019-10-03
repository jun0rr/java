/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 *
 * @author juno
 */
public class MethodImpl extends ScopedImpl {
  
  private final Optional<Class> retype;
  
  private final String name;
  
  private final List<ParameterImpl> parameters;
  
  
  public MethodImpl(Scope s, Class r, String n) {
    super(s);
    this.retype = Optional.ofNullable(r);
    this.name = Objects.requireNonNull(n);
    this.parameters = new LinkedList<>();
  }
  
  public MethodImpl(Method m) {
    this(Scope.forMods(m.getModifiers()), m.getReturnType(), m.getName());
    Arrays.asList(m.getParameters()).stream()
        .map(p->new ParameterImpl(p))
        .forEach(parameters::add);
  }
  
  public MethodImpl(Scope s, String n) {
    this(s, null, n);
  }
  
  public Optional<Class> getReturnType() {
    return retype;
  }
  
  public String getName() {
    return name;
  }
  
  public List<ParameterImpl> parameters() {
    return parameters;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode());
    sb.append(scope.name().toLowerCase())
        .append(" ")
        .append(retype.map(Class::getName).orElse("void"))
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
    if (getClass() != obj.getClass()) {
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
