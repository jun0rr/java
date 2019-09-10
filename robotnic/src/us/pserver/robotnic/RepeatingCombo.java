/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.util.Objects;
import java.util.stream.IntStream;


/**
 *
 * @author juno
 */
public class RepeatingCombo implements Script {
  
  private final Key key;
  
  private final int times;
  
  public RepeatingCombo(Key k, int n) {
    this.key = k;
    this.times = n;
  }
  
  @Override
  public void accept(Robotnic r) {
    IntStream.range(0, times)
        .peek(k -> System.out.printf("* typing: %s%n", k))
        .mapToObj(i -> KeyAction.type(key))
        .forEach(k -> k.accept(r));
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + Objects.hashCode(this.key);
    hash = 47 * hash + this.times;
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
    final RepeatingCombo other = (RepeatingCombo) obj;
    if (this.times != other.times) {
      return false;
    }
    if (this.key != other.key) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return "RepeatingCombo{" + key.name() + " * " + times + '}';
  }
  
  
  
  public static RepeatingCombo of(Key k, int n) {
    return new RepeatingCombo(k, n);
  }
  
}
