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
public enum KeyboardAction implements Script {
  
  COPY(KeyAction.press(Keyboard.CTRL), 
      KeyAction.press(Keyboard.C), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard.C), 
      KeyAction.release(Keyboard.CTRL)
  ),
  
  CUT(KeyAction.press(Keyboard.CTRL), 
      KeyAction.press(Keyboard.X), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard.X), 
      KeyAction.release(Keyboard.CTRL)
  ),
  
  PASTE(KeyAction.press(Keyboard.CTRL), 
      KeyAction.press(Keyboard.V), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard.V), 
      KeyAction.release(Keyboard.CTRL)
  ),
  
  UNDO(KeyAction.press(Keyboard.CTRL), 
      KeyAction.press(Keyboard.Z), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard.Z), 
      KeyAction.release(Keyboard.CTRL)
  ),
  
  PIPE(KeyAction.press(Keyboard.SHIFT), 
      KeyAction.press(Keyboard.BACKSLASH), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard.BACKSLASH), 
      KeyAction.release(Keyboard.SHIFT)
  ),
  
  LESSER(KeyAction.press(Keyboard.SHIFT), 
      KeyAction.press(Keyboard.COMMA), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard.COMMA), 
      KeyAction.release(Keyboard.SHIFT)
  ),
  
  GREATER(KeyAction.press(Keyboard.SHIFT), 
      KeyAction.press(Keyboard.PERIOD), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard.PERIOD), 
      KeyAction.release(Keyboard.SHIFT)
  ),
  
  SHARP(KeyAction.press(Keyboard.SHIFT), 
      KeyAction.press(Keyboard._3), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard._3), 
      KeyAction.release(Keyboard.SHIFT)
  ),
  
  PERCENT(KeyAction.press(Keyboard.SHIFT), 
      KeyAction.press(Keyboard._5), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard._5), 
      KeyAction.release(Keyboard.SHIFT)
  ),
  
  DEGREES(KeyAction.press(Keyboard.ALT_GRAPH), 
      KeyAction.press(Keyboard.SLASH), 
      Delay.fixed(50), 
      KeyAction.release(Keyboard.SLASH), 
      KeyAction.release(Keyboard.ALT_GRAPH)
  );
  
  private KeyboardAction(Script... ss) {
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
