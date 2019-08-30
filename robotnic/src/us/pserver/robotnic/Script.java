/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;


/**
 *
 * @author juno
 */
@FunctionalInterface
public interface Script {

  public void exec(Robotnic r) throws ScriptException;
  
}
