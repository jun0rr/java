/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import us.pserver.tools.Hash;
import us.pserver.tools.Reflect;


/**
 *
 * @author juno
 */
public class ExtendedClassImpl extends Annotated {
  
  private final Class<?> base;
  
  private final List<MethodImpl> methods;
  
  private final String className;
  
  public ExtendedClassImpl(Class<?> base) {
    super();
    this.base = Objects.requireNonNull(base);
    this.methods = new LinkedList<>();
    Predicate<Method> isPublic = m->Modifier.isPublic(m.getModifiers());
    Reflect.of(base).streamMethods()
        .filter(isPublic)
        .filter(m -> Reflect.of(Object.class).streamMethods()
            .filter(isPublic).noneMatch(om->om.equals(m)))
        .map(m->new DummyMethodImpl(m))
        .forEach(methods::add);
    Hash h = Hash.sha1()
        .put(base.getName())
        .put("$toolsImpl_")
        .put(String.valueOf(System.currentTimeMillis() + new Random().nextLong()));
    this.className = base.getName().concat("$toolsImpl_").concat(h.get());
  }
  
  public List<MethodImpl> methods() {
    return methods;
  }
  
  public ExtendedClassImpl add(MethodImpl mth) {
    if(mth != null) {
      Predicate<MethodImpl> match = m -> m.getName().equals(mth.getName()) 
          && m.getReturnType().equals(mth.getReturnType())
          && m.parameters().equals(mth.parameters());
      methods.stream().filter(match).findFirst().ifPresent(methods::remove);
      methods.add(mth);
    }
    return this;
  }
  
  public String getClassName() {
    return className;
  }
  
  @Override
  public String getSourceCode() {
    String simpleName = className.substring(className.lastIndexOf(".")+1);
    StringBuilder sb = new StringBuilder("package ")
        .append(base.getPackageName())
        .append("; \n")
        .append(super.getSourceCode())
        .append(Scope.PUBLIC.name().toLowerCase())
        .append(" class ")
        .append(simpleName)
        .append(base.isInterface() ? " implements " : " extends ")
        .append(base.getName())
        .append(" { \n");
    if(base.isInterface()) {
      sb.append(new ConstructorImpl(Scope.PUBLIC, simpleName).getSourceCode()).append(" \n");
    }
    else {
      Reflect.of(base).streamConstructors()
          .map(c->new ConstructorImpl(c, simpleName))
          .forEach(c->sb.append(c.getSourceCode()).append(" \n"));
    }
    methods.forEach(m->sb.append(m.getSourceCode()).append(" \n"));
    return sb.append("}").toString();
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
  public CompilationUnit getCompilationUnit() {
    return new CompilationUnit(className).append(getSourceCode());
  }
  
}
