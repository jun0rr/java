/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import us.pserver.tools.compile.impl.MethodImpl;
import us.pserver.tools.compile.impl.ParameterImpl;


/**
 *
 * @author Juno
 */
public abstract class AbstractMethodBuilder<P extends Builder<?>, M extends MethodImpl> extends AnnotatedBuilder<P,M> {
  
  protected Optional<String> name;
  
  protected Optional<Class<?>> returnType;
  
  protected int mods;
  
  protected final List<ParameterImpl> params;
  

  public AbstractMethodBuilder(P parent, Consumer<M> onbuild, ClassBuilderContext context) {
    super(parent, onbuild, context);
    this.name = Optional.empty();
    this.returnType = Optional.empty();
    this.params = new ArrayList<>();
    this.mods = 0;
  }
  
  public AbstractMethodBuilder(ClassBuilderContext context) {
    this(null, null, context);
  }
  
  public AbstractMethodBuilder<P,M> setName(String name) {
    this.name = Optional.ofNullable(name);
    return this;
  }
  
  public Optional<String> getName() {
    return name;
  }
  
  public Optional<Class<?>> getReturnType() {
    return returnType;
  }
  
  public AbstractMethodBuilder<P,M> setReturnType(Class<?> type) {
    this.returnType = Optional.ofNullable(type);
    return this;
  }
  
  public int getModifiers() {
    return mods;
  }
  
  public AbstractMethodBuilder<P,M> setModifiers(int mods) {
    this.mods = mods;
    return this;
  }
  
  public List<ParameterImpl> getParameters() {
    return params;
  }
  
  public AbstractMethodBuilder<P,M> addParameter(ParameterImpl pi) {
    if(pi != null) {
      Predicate<ParameterImpl> match = p->pi.getName().equals(p.getName()) 
          && pi.getType().equals(p.getType());
      params.stream().filter(match).findAny().ifPresent(params::remove);
      params.add(pi);
    }
    return this;
  }

  public AbstractMethodBuilder<P,M> addParameter(Class<?> type, String name) {
    if(type != null && name != null) {
      Predicate<ParameterImpl> match = p->p.getName().equals(name)
          && p.getType().equals(type);
      params.stream().filter(match).findAny().ifPresent(params::remove);
      params.add(new ParameterImpl(Collections.EMPTY_LIST, type, name));
    }
    return this;
  }
  
  public ParameterBuilder<? extends AbstractMethodBuilder> newParameter() {
    return new ParameterBuilder<>(this, this::addParameter, context);
  }
  
  //@Override
  //public P buildStep() {
    //return super.buildStep();
  //}

  public abstract M build();
  
}
