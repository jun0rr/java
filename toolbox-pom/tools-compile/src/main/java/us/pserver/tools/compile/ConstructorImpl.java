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
  
  private final Class<?> classFor;
  
  private final List<ParameterImpl> parameters;
  
  public ConstructorImpl(Scope s, Class<?> c) {
    super(s);
    this.classFor = Objects.requireNonNull(c);
    this.parameters = new ArrayList<>();
  }
  
  public ConstructorImpl(Scope s, Constructor c) {
    this(s, c.getDeclaringClass());
    Arrays.asList(c.getParameters()).stream()
        .map(p->new ParameterImpl(p))
        .forEach(parameters::add);
  }
  
  public ConstructorImpl(Constructor c) {
    this(Scope.forMods(c.getModifiers()), c);
  }
  
  @Override
  public String getSourceCode() {
    //System.out.println("--- ConstructorImpl ---");
    //System.out.println("super.getSourceCode(): '" + super.getSourceCode() + "'");
    StringBuilder sb = new StringBuilder(super.getSourceCode())
        .append(scope.name().toLowerCase())
        .append(" ")
        .append(classFor.getSimpleName())
        .append("(");
    parameters.forEach(p->sb.append(p.getSourceCode()).append(","));
    if(!parameters.isEmpty()) sb.deleteCharAt(sb.length() -1);
    sb.append(") { ").append("super(");
    parameters.forEach(p->sb.append(p.getName()).append(","));
    if(!parameters.isEmpty()) sb.deleteCharAt(sb.length() -1);
    return sb.append("); }").toString();
  }
  
}
