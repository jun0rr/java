/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Consumer;
import us.pserver.tools.compile.impl.AnnotationImpl;
import us.pserver.tools.compile.impl.FieldImpl;
import us.pserver.tools.compile.impl.FieldInitializer;


/**
 *
 * @author Juno
 */
public class FieldBuilder<P extends Builder<?>> extends AnnotatedBuilder<P,FieldImpl> {
  
  private Optional<String> name;
  
  private Optional<Class<?>> type;
  
  private int mods;
  
  private Optional<FieldInitializer> init;
  
  public FieldBuilder(P parent, Consumer<FieldImpl> onbuild, ClassBuilderContext ctx) {
    super(parent, onbuild, ctx);
    this.name = Optional.empty();
    this.type = Optional.empty();
    this.init = Optional.empty();
    this.mods = Modifier.PRIVATE;
  }
  
  public FieldBuilder<P> setName(String name) {
    this.name = Optional.ofNullable(name);
    return this;
  }
  
  public Optional<String> getName() {
    return name;
  }
  
  public FieldBuilder<P> setType(Class<?> type) {
    this.type = Optional.ofNullable(type);
    return this;
  }
  
  public Optional<Class<?>> getType() {
    return type;
  }
  
  public int getMods() {
    return mods;
  }
  
  public FieldBuilder<P> setMods(int mods) {
    this.mods = mods;
    return this;
  }
  
  public FieldBuilder<P> setInitializer(FieldInitializer init) {
    this.init = Optional.ofNullable(init);
    return this;
  }
  
  public Optional<FieldInitializer> getInitializer() {
    return init;
  }
  
  public FieldInitializerBuilder<FieldBuilder<P>> newInitializer() {
    return new FieldInitializerBuilder<>(this, this::setInitializer, context);
  }
  
  @Override
  public FieldBuilder<P> addAnnotation(AnnotationImpl a) {
    super.addAnnotation(a);
    return this;
  }
  
  @Override
  public FieldBuilder<P> addAnnotation(Annotation a) {
    super.addAnnotation(a);
    return this;
  }
  
  @Override
  public AnnotationBuilder<FieldBuilder<P>> newAnnotation(Class<? extends Annotation> type) {
    return new AnnotationBuilder<>(this, this::addAnnotation, context);
  }
  
  @Override
  public FieldImpl build() {
    return new FieldImpl(
        annots, 
        type.orElseThrow(()->new IllegalStateException("Bad Null Field Type")), 
        name.orElseThrow(()->new IllegalStateException("Bad Null Field Name")), 
        init, 
        mods
    );
  }
  
}
