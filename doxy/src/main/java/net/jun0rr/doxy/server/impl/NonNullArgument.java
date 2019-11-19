/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.impl;

import java.lang.reflect.Field;
import java.util.Objects;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class NonNullArgument<T> {
  
  private final T value;
  
  private final Class cls;
  
  private final String parName;
  
  public NonNullArgument(T val, String parName, Class cls) {
    this.value = val;
    this.parName = Objects.requireNonNull(parName, "Bad null argument [parName:String]");
    this.cls = Objects.requireNonNull(cls, "Bad null argument [cls:Class]");
  }
  
  public String getArgumentId() {
    Field f = Unchecked.call(()->cls.getDeclaredField(parName));
    return String.format("[%s:%s]", f.getName(), f.getType().getName());
  }
  
  public T getOrThrow() throws IllegalArgumentException {
    if(value == null) {
      throw new IllegalArgumentException("Bad null argument " + getArgumentId());
    }
    return value;
  }
  
  public static <U> U require(U val, String name, Class cls) {
    return new NonNullArgument<>(val, name, cls).getOrThrow();
  }
  
}
