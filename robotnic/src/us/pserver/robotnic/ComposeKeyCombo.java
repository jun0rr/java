/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 *
 * @author Juno
 */
public class ComposeKeyCombo implements Script {
  
  private final Collection<Script> keys;
  
  public ComposeKeyCombo(Collection<Script> ks) {
    this.keys = ks;
  }

  @Override
  public void accept(Robotnic r) throws ScriptException {
    KeyAction.press(Key.COMPOSE).accept(r);
    keys.forEach(a->a.accept(r));
    r.delay(30);
    KeyAction.release(Key.COMPOSE).accept(r);
  }
  
  
  
  public static ComposeKeyCombo of(Key... ks) {
    return new ComposeKeyCombo(Stream.of(ks).map(k->KeyAction.type(k)).collect(Collectors.toList()));
  }
  
  public static ComposeKeyCombo of(Script... ss) {
    return new ComposeKeyCombo(Arrays.asList(ss));
  }
  
}
