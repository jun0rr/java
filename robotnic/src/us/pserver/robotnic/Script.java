/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.util.function.Consumer;


/**
 *
 * @author juno
 */
@FunctionalInterface
public interface Script extends Consumer<Robotnic> {

  /**
   * {@inheritDoc}
   * @throws ScriptException in case of error executing script.
   */
  @Override
  public void accept(Robotnic r) throws ScriptException;
  
}
