/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools;

import java.util.function.Predicate;


/**
 *
 * @author juno
 */
@FunctionalInterface
public interface Evaluable {
  
  public boolean eval();
  
  public default Evaluable and(Evaluable b) {
    return ()->eval() && b.eval();
  }
  
  public default Evaluable or(Evaluable b) {
    return ()->eval() || b.eval();
  }
  
  public default Evaluable negate() {
    return ()->!eval();
  }
  
  public default Predicate<Void> toPredicate() {
    return v->eval();
  }
  
}
