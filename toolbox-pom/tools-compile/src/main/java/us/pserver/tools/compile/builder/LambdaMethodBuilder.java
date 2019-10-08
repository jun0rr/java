/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.impl.LambdaMethodImpl;
import us.pserver.tools.compile.impl.ParameterImpl;


/**
 *
 * @author Juno
 */
public class LambdaMethodBuilder<P extends Builder<?>> extends AbstractMethodBuilder<P,LambdaMethodImpl> {
  
  private Optional<Class<?>> type;
  
  private Optional<Object> lambda;
  
  public LambdaMethodBuilder(P parent, Consumer<LambdaMethodImpl> onbuild) {
    super(parent, onbuild);
    this.type = Optional.empty();
    this.lambda = Optional.empty();
  }
  
  public LambdaMethodBuilder() {
    this(null, null);
  }
  
  @Override
  public LambdaMethodBuilder<P> setName(String name) {
    super.setName(name);
    return this;
  }
  
  @Override
  public LambdaMethodBuilder<P> setReturnType(Class<?> type) {
    super.setReturnType(type);
    return this;
  }
  
  @Override
  public LambdaMethodBuilder<P> setModifiers(int mods) {
    super.setModifiers(mods);
    return this;
  }
  
  @Override
  public LambdaMethodBuilder<P> addParameter(ParameterImpl pi) {
    super.addParameter(pi);
    return this;
  }
  
  @Override
  public LambdaMethodBuilder<P> addParameter(Class<?> type, String name) {
    super.addParameter(type, name);
    return this;
  }
  
  @Override
  public AnnotationBuilder<LambdaMethodBuilder<P>> newAnnotation(Class<? extends Annotation> type) {
    return (AnnotationBuilder<LambdaMethodBuilder<P>>) super.newAnnotation(type);
  }
  
  public Optional<Class<?>> getLambdaType() {
    return type;
  }
  
  public Optional<Object> getLambda() {
    return lambda;
  }
  
  public LambdaMethodBuilder<P> setLambda(Object lambda) {
    this.lambda = Optional.ofNullable(lambda);
    this.lambda.map(Object::getClass).ifPresent(this::setLambdaType);
    return this;
  }
  
  private void setLambdaType(Class<?> type) {
    List<Class<?>> ls = new ArrayList<>();
    ls.add(type);
    ls.addAll(Arrays.asList(type.getInterfaces()));
    this.type = Optional.of(ls.stream()
        .filter(this::isFunctional)
        .findFirst()
        .orElseThrow(()->new IllegalArgumentException("Not a FunctionalInterface type: " + type)));
  }
  
  private boolean isFunctional(Class<?> c) {
    return c.isInterface() 
        && Reflect.of(c).streamMethods()
        .filter(m->Modifier.isPublic(m.getModifiers()))
        .filter(m->!m.isDefault())
        .filter(m->Modifier.isAbstract(m.getModifiers()))
        .count() == 1;
  }
  
  @Override
  public LambdaMethodImpl build() {
    return new LambdaMethodImpl(
        annots, 
        type.orElseThrow(()->new IllegalStateException("Lambda Not Defined")), 
        returnType, 
        name.orElseThrow(()->new IllegalStateException("Method name not defined")), 
        params, 
        mods
    );
  }
  
}
