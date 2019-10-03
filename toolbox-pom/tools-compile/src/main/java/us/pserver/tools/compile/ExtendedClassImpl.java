/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import us.pserver.tools.Hash;
import us.pserver.tools.Reflect;


/**
 *
 * @author juno
 */
public class ExtendedClassImpl extends Annotated {
  
  private final Class<?> base;
  
  private final List<MethodImpl> methods;
  
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
  }
  
  public List<MethodImpl> methods() {
    return methods;
  }
  
  @Override
  public String getSourceCode() {
    Random r = new Random();
    Hash h = Hash.sha1()
        .put(base.getName())
        .put("$toolsImpl_")
        .put(String.valueOf(System.currentTimeMillis() + r.nextLong()));
    StringBuilder sb = new StringBuilder("package ")
        .append(base.getPackageName())
        .append("; \n")
        .append(super.getSourceCode())
        .append(Scope.PUBLIC.name().toLowerCase())
        .append(" class ")
        .append(base.getSimpleName())
        .append("$toolsImpl_")
        .append(h.get())
        .append(base.isInterface() ? " implements " : " extends ")
        .append(base.getName())
        .append(" { \n");
    if(base.isInterface()) {
      sb.append(new ConstructorImpl(Scope.PUBLIC, base).getSourceCode()).append(" \n");
    }
    else {
      Reflect.of(base).streamConstructors()
          .map(c->new ConstructorImpl(c))
          .forEach(c->sb.append(c.getSourceCode()).append(" \n"));
    }
    methods.forEach(m->sb.append(m.getSourceCode()).append(" \n"));
    return sb.append("}").toString();
  }
  
}
