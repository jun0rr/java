/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robot;


/**
 *
 * @author juno
 */
public interface KeyAction extends Script {
  
  public Key getKey();
  
  public Action getAction();
  
  @Override
  public default void exec(Robotnic r) throws ScriptException {
    switch(getAction()) {
      case PRESS:
        r.kpress(getKey());
        break;
      case RELEASE:
        r.krelease(getKey());
        break;
      default:
        r.ktype(getKey());
        break;
    }
  }
  
  
  
  public
  
}
