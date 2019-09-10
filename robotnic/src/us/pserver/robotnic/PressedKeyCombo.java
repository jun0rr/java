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
public class PressedKeyCombo implements Script {
  
  private final Key[] keys;
  
  public PressedKeyCombo(Key... ks) {
    this.keys = ks;
  }

  @Override
  public void accept(Robotnic r) throws ScriptException {
    KeyAction.press(keys[0]).accept(r);
    Stream.of(keys).skip(1).map(k->KeyAction.type(k)).forEach(a->a.accept(r));
    KeyAction.release(keys[0]).accept(r);
  }
  
  
  
  public static PressedKeyCombo of(Key... ks) {
    return new PressedKeyCombo(ks);
  }
  
}
