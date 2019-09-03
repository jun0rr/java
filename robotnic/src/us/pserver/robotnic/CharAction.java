/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;


/**
 *
 * @author Juno
 */
public enum CharAction implements Script {
  
  A_LOWER('a', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Keyboard.A)
  ),
  A_UPPER('A', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Keyboard.A)
  ),
  A_ACUTE_LOWER('á', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Keyboard.ACUTE), 
      KeyAction.type(Keyboard.A)
  ),
  A_ACUTE_UPPER('Á', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Keyboard.ACUTE), 
      KeyAction.type(Keyboard.A)
  ),
  A_CIRC_LOWER('â', 
      r->OS.setCapsLockEnabled(false), 
      KeyPressCombo.of(Keyboard.SHIFT, Keyboard.TILDE),
      KeyAction.type(Keyboard.A)
  ),
  A_CIRC_UPPER('Â', 
      r->OS.setCapsLockEnabled(true), 
      KeyPressCombo.of(Keyboard.SHIFT, Keyboard.TILDE),
      KeyAction.type(Keyboard.A)
  ),
  A_GRAVE_LOWER('à', 
      r->OS.setCapsLockEnabled(false), 
      KeyPressCombo.of(Keyboard.SHIFT, Keyboard.ACUTE),
      KeyAction.type(Keyboard.A)
  ),
  A_GRAVE_UPPER('À', 
      r->OS.setCapsLockEnabled(true), 
      KeyPressCombo.of(Keyboard.SHIFT, Keyboard.ACUTE),
      KeyAction.type(Keyboard.A)
  ),
  A_TILDE_LOWER('ã', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Keyboard.TILDE),
      KeyAction.type(Keyboard.A)
  ),
  A_TILDE_UPPER('Ã', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Keyboard.TILDE),
      KeyAction.type(Keyboard.A)
  ),
  A_UMBRA_LOWER('ä', 
      r->OS.setCapsLockEnabled(false), 
      KeyPressCombo.of(Keyboard.SHIFT, Keyboard._6),
      KeyAction.type(Keyboard.A)
  ),
  A_UMBRA_UPPER('Ä', 
      r->OS.setCapsLockEnabled(true), 
      KeyPressCombo.of(Keyboard.SHIFT, Keyboard._6),
      KeyAction.type(Keyboard.A)
  ),
  
  B_LOWER('b', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Keyboard.B)
  ),
  B_UPPER('B', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Keyboard.B)
  ),
  
  C_LOWER('c', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Keyboard.C)
  ),
  C_UPPER('C', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Keyboard.C)
  ),
  
  CIRC('^', 
      KeyPressCombo.of(Keyboard.SHIFT, Keyboard.TILDE),
      KeyAction.type(Keyboard.SPACE)
  ),
  ;
  
  private CharAction(char c, Script... ss) {
    this.c = c;
    this.script = new ScriptCombo(ss);
  }
  
  private final char c;
  
  private final Script script;
  
  public char getChar() {
    return c;
  }
  
  @Override
  public void exec(Robotnic r) {
    this.script.exec(r);
  }
  
}
