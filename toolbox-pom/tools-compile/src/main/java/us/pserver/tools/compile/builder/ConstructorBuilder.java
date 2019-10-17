/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import us.pserver.tools.compile.impl.ConstructorImpl;
import us.pserver.tools.compile.impl.NewFieldInitializer;
import us.pserver.tools.compile.impl.FieldInitializer;
import us.pserver.tools.compile.impl.ParameterImpl;
import us.pserver.tools.compile.impl.SuperConstructorImpl;
import us.pserver.tools.compile.impl.VarConsumer;


/**
 *
 * @author Juno
 */
public class ConstructorBuilder<P extends Builder<?>> extends AbstractMethodBuilder<P,ConstructorImpl> {
  
  private final List<FieldInitializer> inits;
  
  private Optional<VarConsumer> consumer;
  
  private Optional<SuperConstructorImpl> superCall;


  public ConstructorBuilder(P parent, Consumer<ConstructorImpl> onbuild) {
    super(parent, onbuild);
    this.inits = new ArrayList<>();
    this.consumer = Optional.empty();
    this.superCall = Optional.empty();
  }
  
  public ConstructorBuilder() {
    this(null, null);
  }

  @Override
  public ConstructorBuilder<P> setName(String name) {
    super.setName(name);
    return this;
  }
  
  @Override
  public ConstructorBuilder<P> setReturnType(Class<?> type) {
    super.setReturnType(type);
    return this;
  }
  
  @Override
  public ConstructorBuilder<P> setModifiers(int mods) {
    super.setModifiers(mods);
    return this;
  }
  
  @Override
  public ConstructorBuilder<P> addParameter(ParameterImpl pi) {
    super.addParameter(pi);
    return this;
  }
  
  @Override
  public ConstructorBuilder<P> addParameter(Class<?> type, String name) {
    super.addParameter(type, name);
    return this;
  }
  
  @Override
  public AnnotationBuilder<ConstructorBuilder<P>> newAnnotation(Class<? extends Annotation> type) {
    return (AnnotationBuilder<ConstructorBuilder<P>>) super.newAnnotation(type);
  }
  
  public ConstructorBuilder<P> setParametersConsumer(VarConsumer cs) {
    this.consumer = Optional.ofNullable(cs);
    return this;
  }
  
  public Optional<VarConsumer> getParametersConsumer() {
    return consumer;
  }
  
  public SuperConstructorBuilder<ConstructorBuilder<P>> newSuperConstructor() {
    return new SuperConstructorBuilder<>(this, this::setSuperConstructor, this.params);
  }

  public ConstructorBuilder<P> setSuperConstructor(SuperConstructorImpl sc) {
    this.superCall = Optional.ofNullable(sc);
    return this;
  }
  
  public Optional<SuperConstructorImpl> getSuperConstructor() {
    return superCall;
  }
  
  public List<FieldInitializer> getFieldInitializers() {
    return inits;
  }
  
  public ConstructorBuilder<P> from(Constructor cct) {
    if(cct != null) {
      this.name = Optional.of(cct.getDeclaringClass().getSimpleName());
      this.mods = cct.getModifiers();
      Stream.of(cct.getParameters())
          .map(p->new ParameterImpl(Collections.EMPTY_LIST, p.getType(), p.getName()))
          .forEach(params::add);
      if(cct.getDeclaringClass().getSuperclass() != null) {
        superCall = Stream.of(cct.getDeclaringClass().getSuperclass().getConstructors())
            .filter(c->c.getParameterCount() <= params.size())
            .filter(c->Stream.of(c.getParameterTypes()).allMatch(t->params.stream().anyMatch(pt->t.equals(pt))))
            .sorted((a,b)->Integer.compare(a.getParameterCount(), b.getParameterCount()) * -1)
            .findFirst()
            .map(s->new SuperConstructorImpl(
                Stream.of(s.getParameters())
                    .map(p->new ParameterImpl(Collections.EMPTY_LIST, p.getType(), p.getName()))
                    .collect(Collectors.toList()))
            );
      }
    }
    return this;
  }

  public ConstructorBuilder<P> addInitializer(FieldInitializer fi) {
    if(fi != null) {
      Predicate<FieldInitializer> match = f->fi.getName().equals(f.getName()) 
          && fi.getType().equals(f.getType());
      inits.stream().filter(match).findAny().ifPresent(inits::remove);
      inits.add(fi);
    }
    return this;
  }

  public ConstructorBuilder<P> addInitializer(Class<?> type, String name) {
    if(type != null && name != null) {
      Predicate<FieldInitializer> match = f->f.getName().equals(name)
          && f.getType().equals(type);
      inits.stream().filter(match).findAny().ifPresent(inits::remove);
      inits.add(new NewFieldInitializer(name, type));
    }
    return this;
  }

  @Override
  public ConstructorImpl build() {
    if(mods == 0) throw new IllegalStateException("Modifiers not defined");
    return new ConstructorImpl(
        annots, 
        name.orElseThrow(()->new IllegalStateException("Constructor name not defined")), 
        params, 
        superCall, 
        inits, 
        consumer, 
        mods
    );
  }
  
}
