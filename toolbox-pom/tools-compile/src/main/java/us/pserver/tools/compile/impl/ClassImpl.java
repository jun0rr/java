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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Predicate;
import us.pserver.tools.Hash;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.CompilationUnit;


/**
 *
 * @author juno
 */
public class ClassImpl extends Annotated {
  
  private final Optional<Class<?>> superClass;
  
  private final List<Class<?>> interfaces;
  
  private final List<ConstructorImpl> constructors;
  
  private final List<MethodImpl> methods;
  
  private final List<FieldImpl> fields;
  
  private final String className;
  
  private final int mods;
  
  public ClassImpl(
      String name,
      Optional<Class<?>> superClass, 
      Collection<Class<?>> interfaces,
      Collection<AnnotationImpl> annots,
      Collection<ConstructorImpl> ccts, 
      Collection<MethodImpl> meths, 
      Collection<FieldImpl> fields,
      int mods
  ) {
    super(annots);
    this.className = Objects.requireNonNull(name, "Bad Null Name");
    this.superClass = superClass;
    this.interfaces = new ArrayList<>(interfaces);
    this.constructors = new ArrayList<>(ccts);
    this.methods = new ArrayList<>(meths);
    this.fields = new ArrayList<>(fields);
    this.mods = mods;
    this.initAbstractMethods();
  }
  
  private void initAbstractMethods() {
    if(superClass.isPresent()) {
      Predicate<Method> isAbstractPublic = m->Modifier.isAbstract(m.getModifiers()) && Modifier.isPublic(m.getModifiers());
      Comparator<Method> me = (a,b) -> a.getName().concat(Arrays.toString(a.getParameterTypes()))
          .compareTo(b.getName().concat(Arrays.toString(b.getParameterTypes())));
      Reflect.of(superClass).streamMethods()
          .filter(isAbstractPublic)
          .filter(m -> Reflect.of(Object.class).streamMethods()
              .filter(isAbstractPublic).noneMatch(om->me.compare(om, m) == 0))
          .map(DummyMethodImpl::of)
          .forEach(methods::add);
    }
  }
  
  public List<ConstructorImpl> constructors() {
    return constructors;
  }
  
  public List<MethodImpl> methods() {
    return methods;
  }
  
  public List<FieldImpl> fields() {
    return fields;
  }
  
  public String getClassName() {
    return className;
  }
  
  @Override
  public String getSourceCode() {
    int lastDot = className.lastIndexOf(".");
    String simpleName = className.substring(lastDot + 1);
    StringBuilder sb = new StringBuilder("package ")
        .append(className.substring(0, lastDot))
        .append("; \n")
        .append(super.getSourceCode())
        .append(Scope.forMods(mods).name().toLowerCase())
        .append(" ");
    if(Modifier.isStatic(mods)) sb.append("static ");
    else if(Modifier.isFinal(mods)) sb.append("final ");
    sb.append(" class ").append(simpleName);
    superClass.ifPresent(b->sb.append(" extends ").append(b.getName()));
    if(!interfaces.isEmpty()) {
      sb.append(" implements ");
      interfaces.forEach(c->sb.append(c.getName()).append(", "));
      sb.delete(sb.length() -2, sb.length());
    }
    sb.append(" { \n");
    fields.forEach(f->sb.append(f.getSourceCode()).append("\n"));
    constructors.stream()
        .forEach(c->sb.append(c.getSourceCode()).append(" \n"));
    methods.forEach(f->sb.append(f.getSourceCode()).append(" \n"));
    return sb.append("}").toString();
  }
  
  public CompilationUnit getCompilationUnit() {
    return new CompilationUnit(className).append(getSourceCode());
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(this.superClass);
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
    final ClassImpl other = (ClassImpl) obj;
    if (!Objects.equals(this.className, other.className)) {
      return false;
    }
    if (!Objects.equals(this.superClass, other.superClass)) {
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
