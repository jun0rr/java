/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.util.stream.Stream;


/**
 *
 * @author Juno
 */
public class WindowsAltCombo implements Script {
  
  private final Key[] keys;
  
  public WindowsAltCombo(Key... ks) {
    this.keys = ks;
  }

  @Override
  public void accept(Robotnic r) throws ScriptException {
    KeyAction.press(Key.ALT).accept(r);
    Stream.of(keys).map(k->KeyAction.type(k)).forEach(a->a.accept(r));
    r.delay(30);
    KeyAction.release(Key.ALT).accept(r);
  }
  
  
  
  public static WindowsAltCombo of(Key... ks) {
    return new WindowsAltCombo(ks);
  }
  
}
