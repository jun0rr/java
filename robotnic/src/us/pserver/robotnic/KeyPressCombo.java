/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 *
 * @author Juno
 */
public class KeyPressCombo implements Script {
  
  private final Key[] keys;
  
  public KeyPressCombo(Key... ks) {
    this.keys = Objects.requireNonNull(ks);
  }
  
  public Key[] getKeys() {
    return keys;
  }
  
  @Override
  public void exec(Robotnic r) throws ScriptException {
    Stream.of(keys).map(KeyAction::press).forEach(a->a.exec(r));
    r.delay(30);
    Stream.of(keys).map(Indexed.builder())
        .sorted(Comparator.reverseOrder())
        .map(Indexed::value)
        .map(KeyAction::release)
        .forEach(a -> a.exec(r));
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
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
    final KeyPressCombo other = (KeyPressCombo) obj;
    return Arrays.deepEquals(this.keys, other.keys);
  }


  @Override
  public String toString() {
    return "KeyPressCombo{" + "keys=" + Arrays.toString(keys) + '}';
  }
  
  
  
  public static KeyPressCombo of(Key... ks) {
    return new KeyPressCombo(ks);
  }
  
}
