/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;


/**
 *
 * @author Juno
 */
public class LazyFinal<T> {
  
  private final AtomicReference<T> obj;
  
  public LazyFinal() {
    this.obj = new AtomicReference<>();
  }
  
  public T get() throws IllegalStateException {
    if(obj.get() == null) {
      throw new IllegalStateException("LazyFinal not initialized");
    }
    return obj.get();
  }
  
  public void init(T o) throws IllegalStateException {
    if(obj.compareAndExchange(null, o) != null) {
      throw new IllegalStateException("Value already defined");
    }
  }
  
  public boolean isInitialized() {
    return obj.get() != null;
  }
  
  public void ifInitialized(Consumer<T> c) {
    if(isInitialized()) {
      c.accept(obj.get());
    }
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(this.obj);
    hash = 59 * hash + Objects.hashCode(this.obj.get());
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
    final LazyFinal<?> other = (LazyFinal<?>) obj;
    if (!Objects.equals(this.obj, other.obj)) {
      return false;
    }
    return Objects.equals(this.obj.get(), other.obj.get());
  }
  
  @Override
  public String toString() {
    return "LazyFinal{" + obj + '}';
  }
  
}
