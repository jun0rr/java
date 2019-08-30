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
public interface Delay extends Script {
  
  public static Delay fixed(int ms) {
    return new Delay() {
      @Override
      public void exec(Robotnic r) throws ScriptException {
        r.delay(ms);
      }
    };
  }
  
  public static Delay random(int until) {
    return new Delay() {
      @Override
      public void exec(Robotnic r) throws ScriptException {
        r.randomDelay(until);
      }
    };
  }
  
  public static Delay mix(int fixed, int random) {
    return new Delay() {
      @Override
      public void exec(Robotnic r) throws ScriptException {
        r.mixDelay(fixed, random);
      }
    };
  }
  
}
