/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;


/**
 *
 * @author Juno
 */
public interface NestedBuilder<P extends Builder<?>, T> extends Builder<T> {
  
  public P buildStep();
  
}
