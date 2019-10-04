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
import java.util.stream.Collectors;


/**
 *
 * @author juno
 */
public class ConstructorImpl extends ScopedImpl {
  
  private final String name;
  
  private final List<ParameterImpl> parameters;
  
  public ConstructorImpl(Scope s, String name) {
    super(s);
    this.name = Objects.requireNonNull(name);
    this.parameters = new ArrayList<>();
  }
  
  public ConstructorImpl(Scope s, Constructor c, String name) {
    this(s, name);
    Arrays.asList(c.getParameters()).stream()
        .map(p->new ParameterImpl(p))
        .forEach(parameters::add);
  }
  
  public ConstructorImpl(Constructor c, String name) {
    this(Scope.forMods(c.getModifiers()), c, name);
  }
  
  @Override
  public String getSourceCode() {
    //System.out.println("--- ConstructorImpl ---");
    //System.out.println("super.getSourceCode(): '" + super.getSourceCode() + "'");
    StringBuilder sb = new StringBuilder(super.getSourceCode())
        .append(scope.name().toLowerCase())
        .append(" ")
        .append(name)
        .append("(");
    parameters.forEach(p->sb.append(p.getSourceCode()).append(","));
    if(!parameters.isEmpty()) sb.deleteCharAt(sb.length() -1);
    sb.append(") { ").append("super(");
    parameters.forEach(p->sb.append(p.getName()).append(","));
    if(!parameters.isEmpty()) sb.deleteCharAt(sb.length() -1);
    return sb.append("); }").toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.name);
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
    final ConstructorImpl other = (ConstructorImpl) obj;
    if (!Objects.equals(this.name, other.name)) {
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
