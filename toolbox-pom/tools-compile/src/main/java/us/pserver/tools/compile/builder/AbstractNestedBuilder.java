/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;


/**
 *
 * @author Juno
 */
public abstract class AbstractNestedBuilder<P extends Builder<?>,T> implements NestedBuilder<P,T> {
  
  protected final Optional<P> parent;
  
  protected final Optional<Consumer<T>> onbuild;
  
  protected final ClassBuilderContext context;
  
  protected AbstractNestedBuilder(P parent, Consumer<T> onbuild, ClassBuilderContext context) {
    this.parent = Optional.ofNullable(parent);
    this.onbuild = Optional.ofNullable(onbuild);
    this.context = Objects.requireNonNull(context, "Bad Null ClassBuilderContext");
  }
  
  protected AbstractNestedBuilder(ClassBuilderContext context) {
    this(null, null, context);
  }
  
  public ClassBuilderContext getClassBuilderContext() {
    return context;
  }
  
  @Override
  public abstract T build();
  
  @Override
  public P buildStep() {
    onbuild.ifPresent(c->c.accept(this.build()));
    return parent.orElseThrow(()->new IllegalStateException("Not in NestedBuilder context"));
  }
  
}
