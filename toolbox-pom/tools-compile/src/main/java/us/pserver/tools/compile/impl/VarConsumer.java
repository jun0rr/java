/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;


/**
 *
 * @author Juno
 */
@FunctionalInterface
public interface VarConsumer {
  
  public void accept(Object... args);
  
}
