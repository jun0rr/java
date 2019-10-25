/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import us.pserver.tools.Hash;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.impl.ConstructorImpl;
import us.pserver.tools.compile.impl.ClassImpl;
import us.pserver.tools.compile.impl.DummyMethodImpl;
import us.pserver.tools.compile.impl.FieldImpl;
import us.pserver.tools.compile.impl.MethodImpl;


/**
 *
 * @author Juno
 */
public class ClassBuilder<P extends Builder<?>> extends AnnotatedBuilder<P,ClassImpl> {
  
  private Optional<String> name;
  
  private Optional<Class<?>> superClass;
  
  private final List<Class<?>> interfaces;
  
  private final List<FieldImpl> fields;
  
  private final List<ConstructorImpl> ccts;
  
  private final List<MethodImpl> meths;
  
  private int mods;
  
  public ClassBuilder(P parent, Consumer<ClassImpl> onbuild, ClassBuilderContext context) {
    super(parent, onbuild, context);
    this.name = Optional.empty();
    this.superClass = Optional.empty();
    this.fields = new ArrayList<>();
    this.ccts = new ArrayList<>();
    this.meths = new ArrayList<>();
    this.interfaces = new ArrayList<>();
    this.mods = Modifier.PUBLIC;
  }
  
  public ClassBuilder(String name) {
    this(null, null, new ClassBuilderContext(Objects.requireNonNull(name, "Bad Null Class Name")));
    this.name = Optional.of(name);
  }
  
  public ClassBuilder<P> setName(String name) {
    this.name = Optional.ofNullable(name);
    return this;
  }
  
  public Optional<String> getName() {
    return name;
  }
  
  public int getModifiers() {
    return mods;
  }
  
  public ClassBuilder<P> setModifiers(int mds) {
    this.mods = mds;
    return this;
  }
  
  public ClassBuilder<P> implement(Class<?> iface) {
    if(iface != null) {
      if(!iface.isInterface()) {
        throw new IllegalArgumentException(String.format("%s is not an interface", iface.getName()));
      }
      interfaces.add(iface);
    }
    return this;
  }
  
  public ClassBuilder<P> implement(Class<?>... ifaces) {
    return implement(Arrays.asList(ifaces));
  }
  
  public ClassBuilder<P> implement(Collection<Class<?>> ifaces) {
    if(ifaces != null && !ifaces.isEmpty()) {
      ifaces.stream()
          .filter(c->c.isInterface())
          .forEach(interfaces::add);
    }
    return this;
  }
  
  public ClassBuilder<P> extend(Class<?> superClass) {
    this.superClass = Optional.ofNullable(superClass);
    return this;
  }
  
  private String getSimpleName() {
    return name.get().substring(name.get().lastIndexOf(".") + 1);
  }
  
  private void setupName(Class<?> base) {
    Hash h = Hash.sha1()
      .put(base.getName())
      .put("$toolsImpl_")
      .put(String.valueOf(System.currentTimeMillis() + new Random().nextLong()));
    this.setName(base.getName().concat("$toolsImpl_").concat(h.get()));
  }
  
  private void setupSuperConstructors(Class<?> base) {
    if(base.isInterface()) {
      ccts.add(ConstructorImpl.empty(getSimpleName()));
    }
    else {
      Reflect.of(superClass.get()).streamConstructors()
          .map(c->newConstructor().from(c).setName(getSimpleName()).build())
          .filter(c->ccts.stream().noneMatch(o->c.equals(o)))
          .forEach(ccts::add);
    }
  }
  
  private void setupAbstractMethods(Class<?> base) {
    Predicate<Method> isAbstractPublic = m->Modifier.isAbstract(m.getModifiers()) && Modifier.isPublic(m.getModifiers());
    Comparator<Method> me = (a,b) -> a.getName().concat(Arrays.toString(a.getParameterTypes()))
        .compareTo(b.getName().concat(Arrays.toString(b.getParameterTypes())));
    Reflect.of(base).streamMethods()
        .filter(isAbstractPublic)
        .filter(m -> Reflect.of(Object.class).streamMethods()
            .filter(isAbstractPublic).noneMatch(om->me.compare(om, m) == 0))
        .map(DummyMethodImpl::of)
        .filter(i->meths.stream().noneMatch(m->i.equals(m)))
        .forEach(meths::add);
  }
  
  public Optional<Class<?>> getSuperClass() {
    return superClass;
  }
  
  public ClassBuilder<P> addField(FieldImpl f) {
    if(f != null) {
      this.fields.add(f);
    }
    return this;
  }
  
  public FieldBuilder<ClassBuilder<P>> newField() {
    return new FieldBuilder<>(this, this::addField, context);
  }
  
  public List<FieldImpl> getFields() {
    return fields;
  }
  
  public ClassBuilder<P> addMethod(MethodImpl m) {
    if(m != null) {
      this.meths.add(m);
    }
    return this;
  }
  
  public LambdaMethodBuilder<ClassBuilder<P>> newLambdaMethod() {
    return new LambdaMethodBuilder<>(this, this::addMethod, context);
  }
  
  public List<MethodImpl> getMethods() {
    return meths;
  }
  
  public ClassBuilder<P> addConstructor(ConstructorImpl c) {
    if(c != null) {
      this.meths.add(c);
    }
    return this;
  }
  
  public ConstructorBuilder<ClassBuilder<P>> newConstructor() {
    return new ConstructorBuilder<>(this, this::addConstructor, context).setName(getSimpleName());
  }
  
  public List<ConstructorImpl> getConstructors() {
    return ccts;
  }
  
  public List<Class<?>> getInterfaces() {
    return interfaces;
  }
  
  @Override
  public ClassImpl build() {
    if(this.superClass.isPresent()) {
      this.setupName(superClass.get());
      this.setupSuperConstructors(superClass.get());
      this.setupAbstractMethods(superClass.get());
    }
    interfaces.forEach(this::setupAbstractMethods);
    if(mods == 0) throw new IllegalStateException("Modifiers not defined");
    return new ClassImpl(
        name.orElseThrow(()->new IllegalStateException("Class name not defined")),
        superClass, interfaces, annots,
        ccts, meths, fields, mods
    );
  }
  
}
