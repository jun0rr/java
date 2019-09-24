/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;


/**
 *
 * @author juno
 */
@FunctionalInterface
public interface IntBiFunction<T> {
  
  public T apply(int x, int y);
  
}
