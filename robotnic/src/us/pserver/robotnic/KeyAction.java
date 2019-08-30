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
  
  
  
  public static KeyAction of(Key k, Action a) {
    return new KeyAction() {
      @Override
      public Key getKey() {
        return k;
      }
      @Override
      public Action getAction() {
        return a;
      }
    };
  }
  
  public static KeyAction type(Key k) {
    return of(k, Action.TYPE);
  }
  
  public static KeyAction press(Key k) {
    return of(k, Action.PRESS);
  }
  
  public static KeyAction release(Key k) {
    return of(k, Action.RELEASE);
  }
  
}
