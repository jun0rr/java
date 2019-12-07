/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import us.pserver.tools.Reflect;


/**
 *
 * @author juno
 */
public class LambdaMethodImpl extends MethodImpl {
  
  private final Class<?> lambdaType;
  
  public LambdaMethodImpl(Collection<AnnotationImpl> ans, Class<?> lambdaType, Optional<Class<?>> r, String n, Collection<ParameterImpl> pars, int mods) {
    super(ans, r, n, pars, mods);
    List<Class<?>> ls = new ArrayList<>();
    ls.add(lambdaType);
    ls.addAll(Arrays.asList(lambdaType.getInterfaces()));
    Optional<Class<?>> lambda = ls.stream()
        .filter(this::isFunctional)
        .findFirst();
    System.out.println(lambda);
    if(lambda.isEmpty()) {
      throw new IllegalArgumentException("Not a functional interface: " + lambdaType.getName());
    }
    this.lambdaType = lambda.get();
  }
  
  private boolean isFunctional(Class<?> c) {
    return c.isInterface() 
        && Reflect.of(c).streamMethods()
        .filter(m->Modifier.isPublic(m.getModifiers()))
        .filter(m->!m.isDefault())
        .filter(m->Modifier.isAbstract(m.getModifiers()))
        .count() == 1;
  }
  
  private Method getLambdaMethod() {
    return Reflect.of(lambdaType).streamMethods()
        .filter(m->Modifier.isPublic(m.getModifiers()))
        .filter(m->!m.isDefault())
        .filter(m->Modifier.isAbstract(m.getModifiers()))
        .findAny().get();
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode());
    sb.append(" { ")
        .append(lambdaType.getName())
        .append(" fn = (")
        .append(lambdaType.getName())
        .append(") LAMBDA_MAP.get(\"")
        .append(getMethodSignature())
        .append("\"); ");
    getReturnType().ifPresent(r->sb.append("return (").append(r.getName()).append(") "));
    sb.append("fn.")
        .append(getLambdaMethod().getName())
        .append("(");
    if(!parameters().isEmpty()) {
      parameters().stream()
          .forEach(p->sb.append(p.getName()).append(", "));
      sb.delete(sb.length() -2, sb.length());
    }
    return sb.append("); }").toString();
  }
  
  @Override
  public int hashCode() {
    return super.hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
