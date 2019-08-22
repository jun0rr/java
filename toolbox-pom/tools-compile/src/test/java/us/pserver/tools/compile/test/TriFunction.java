/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.util.function.Function;


/**
 *
 * @author juno
 */
@FunctionalInterface
public interface TriFunction<A,B,C,D> {
  
  public D apply(A a, B b, C c);
  
  public default <E> TriFunction<A,B,C,E> andThen(Function<D,E> fn) {
    return (a,b,c) -> fn.apply(apply(a,b,c));
  }
  
}
