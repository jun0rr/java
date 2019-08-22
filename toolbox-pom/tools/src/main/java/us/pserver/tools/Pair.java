/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools;

import java.util.Objects;


/**
 *
 * @author juno
 */
public class Pair<A,B> {

  public final A a;
  
  public final B b;
  
  public Pair(A a, B b) {
    this.a = a;
    this.b = b;
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 67 * hash + Objects.hashCode(this.a);
    hash = 67 * hash + Objects.hashCode(this.b);
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
    final Pair<?, ?> other = (Pair<?, ?>) obj;
    if (!Objects.equals(this.a, other.a)) {
      return false;
    }
    return Objects.equals(this.b, other.b);
  }
  
  @Override
  public String toString() {
    return "Pair{" + "a=" + a + ", b=" + b + '}';
  }
  
}
