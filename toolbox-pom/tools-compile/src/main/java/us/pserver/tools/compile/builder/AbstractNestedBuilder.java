/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.util.Optional;
import java.util.function.Consumer;


/**
 *
 * @author Juno
 */
public abstract class AbstractNestedBuilder<P extends Builder<?>,T> implements NestedBuilder<P,T> {
  
  protected final Optional<P> parent;
  
  protected final Optional<Consumer<T>> onbuild;
  
  protected AbstractNestedBuilder(P parent, Consumer<T> onbuild) {
    this.parent = Optional.ofNullable(parent);
    this.onbuild = Optional.ofNullable(onbuild);
  }
  
  protected AbstractNestedBuilder() {
    this(null, null);
  }
  
  @Override
  public abstract T build();
  
  @Override
  public P buildStep() {
    onbuild.ifPresent(c->c.accept(this.build()));
    return parent.orElseThrow(()->new IllegalStateException("Not in NestedBuilder context"));
  }
  
}
