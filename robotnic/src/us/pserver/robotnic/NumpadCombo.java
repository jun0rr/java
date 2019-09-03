/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;


/**
 *
 * @author Juno
 */
public class NumpadCombo implements Script {
  
  private final Action action;
  
  private final Key[] keys;
  
  public NumpadCombo(Action a, Key... ks) {
    this.action = Objects.requireNonNull(a);
    this.keys = Objects.requireNonNull(ks);
  }
  
  public Action getAction() {
    return action;
  }
  
  public Key[] getKeys() {
    return keys;
  }
  
  private void pressCombo(Robotnic r) {
    Stream.of(keys).map(KeyAction::press).forEach(a->a.exec(r));
    r.delay(30);
    Stream.of(keys).map(Indexed.builder())
        .sorted(Comparator.reverseOrder())
        .map(Indexed::value)
        .map(KeyAction::release)
        .forEach(a -> a.exec(r));
  }
  
  private void typeCombo(Robotnic r) {
    Stream.of(keys).map(KeyAction::type).forEach(a->a.exec(r));
  }
  
  private void releaseCombo(Robotnic r) {
    Stream.of(keys).map(KeyAction::release).forEach(a->a.exec(r));
  }
  
  @Override
  public void exec(Robotnic r) throws ScriptException {
    boolean state = OS.isNumLockEnabled();
    OS.setNumLockEnabled(true);
    switch(action) {
      case PRESS:
        pressCombo(r);
        break;
      case TYPE:
        typeCombo(r);
        break;
      case RELEASE:
        releaseCombo(r);
        break;
      default:
        throw new IllegalArgumentException("Bad KeyCombo Action: " + action);
    }
    OS.setNumLockEnabled(state);
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(action);
    hash = 23 * hash + Arrays.deepHashCode(this.keys);
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final NumpadCombo other = (NumpadCombo) obj;
    if (this.action != other.action) {
      return false;
    }
    return Arrays.deepEquals(this.keys, other.keys);
  }
  
  @Override
  public String toString() {
    return "KeyCombo{" + "action=" + action + ", keys=" + Arrays.toString(keys) + '}';
  }
  
  
  
  public static NumpadCombo press(Key... ks) {
    return new NumpadCombo(Action.PRESS, ks);
  }
  
  public static NumpadCombo type(Key... ks) {
    return new NumpadCombo(Action.TYPE, ks);
  }
  
  public static NumpadCombo release(Key... ks) {
    return new NumpadCombo(Action.RELEASE, ks);
  }
  
}
