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
public enum Shortcut implements Script {
  
  COPY,
  ;
  
  private Shortcut(Script... ss) {
    this.script = new ScriptCombo(ss);
  }
  
  private final ScriptCombo script;
  
  @Override
  public void exec(Robotnic r) throws ScriptException {
    script.exec(r);
  }
  
  @Override
  public String toString() {
    return "Shortcut{ " + script.toString().replace("Combo", "") + " }";
  }
  
}
