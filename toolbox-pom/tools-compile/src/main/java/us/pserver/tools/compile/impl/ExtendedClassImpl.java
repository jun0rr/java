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
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import us.pserver.tools.Hash;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.CompilationUnit;


/**
 *
 * @author juno
 */
public class ExtendedClassImpl extends Annotated {
  
  private final Class<?> base;
  
  private final List<ConstructorImpl2> constructors;
  
  private final List<MethodImpl> methods;
  
  private final List<FieldImpl> fields;
  
  private final String className;
  
  private final FieldImpl lambdaMap;
  
  public ExtendedClassImpl(Class<?> base) {
    super();
    this.base = Objects.requireNonNull(base);
    this.constructors = new ArrayList<>();
    this.methods = new ArrayList<>();
    this.fields = new ArrayList<>();
    this.initAbstractMethods();
    Hash h = Hash.sha1()
        .put(base.getName())
        .put("$toolsImpl_")
        .put(String.valueOf(System.currentTimeMillis() + new Random().nextLong()));
    this.className = base.getName().concat("$toolsImpl_").concat(h.get());
    this.lambdaMap = new FieldImpl(Map.class, "LAMBDA_MAP", 
        new DefaultFieldInitializer("LAMBDA_MAP", LinkedHashMap.class), 
        Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL
    );
    this.fields.add(lambdaMap);
  }
  
  private void initAbstractMethods() {
    Predicate<Method> isAbstractPublic = m->Modifier.isAbstract(m.getModifiers()) && Modifier.isPublic(m.getModifiers());
    Comparator<Method> me = (a,b) -> a.getName().concat(Arrays.toString(a.getParameterTypes()))
        .compareTo(b.getName().concat(Arrays.toString(b.getParameterTypes())));
    Reflect.of(base).streamMethods()
        .filter(isAbstractPublic)
        .filter(m -> Reflect.of(Object.class).streamMethods()
            .filter(isAbstractPublic).noneMatch(om->me.compare(om, m) == 0))
        .map(m->new DummyMethodImpl(m))
        .forEach(methods::add);
  }
  
  public List<ConstructorImpl2> constructors() {
    return constructors;
  }
  
  public List<MethodImpl> methods() {
    return methods;
  }
  
  public List<FieldImpl> fields() {
    return fields;
  }
  
  public ExtendedClassImpl add(ConstructorImpl2 cct) {
    if(cct != null) {
      Predicate<ConstructorImpl2> match = m->m.parameters().equals(cct.parameters());
      constructors.stream().filter(match).findFirst().ifPresent(constructors::remove);
      constructors.add(cct);
    }
    return this;
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
  
  public ExtendedClassImpl add(FieldImpl fld) {
    if(fld != null) {
      Predicate<FieldImpl> match = f -> f.getName().equals(fld.getName()) 
          && f.getType().equals(fld.getType());
      fields.stream().filter(match).findFirst().ifPresent(fields::remove);
      fields.add(fld);
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
    if(!fields.isEmpty()) {
      fields.forEach(f->sb.append(f.getSourceCode()).append("\n"));
    }
    if(!constructors.isEmpty()) {
      constructors.stream()
          .forEach(c->sb.append(c.getSourceCode()).append("\n"));
    }
    else if(base.isInterface()) {
      sb.append(new ConstructorImpl2(simpleName, Modifier.PUBLIC).getSourceCode()).append(" \n");
    }
    if(!base.isInterface()) {
      Reflect.of(base).streamConstructors()
          .map(c->new ConstructorImpl2(c, simpleName))
          .filter(c->constructors.stream().noneMatch(o->c.equals(o)))
          .forEach(c->sb.append(c.getSourceCode()).append(" \n"));
    }
    methods.forEach(m->sb.append(m.getSourceCode()).append(" \n"));
    return sb.append("}").toString();
  }
  
  public CompilationUnit getCompilationUnit() {
    return new CompilationUnit(className).append(getSourceCode());
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(this.base);
    hash = 23 * hash + Objects.hashCode(this.className);
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
    final ExtendedClassImpl other = (ExtendedClassImpl) obj;
    if (!Objects.equals(this.className, other.className)) {
      return false;
    }
    if (!Objects.equals(this.base, other.base)) {
      return false;
    }
    if (!Objects.equals(this.methods, other.methods)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
