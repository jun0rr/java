/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.bitbox.type;

import java.util.function.IntPredicate;
import java.util.function.Predicate;


/**
 *
 * @author juno
 */
public interface TypeMatching {
  
  public boolean match(Class c);
  
  public boolean match(byte id);
  
  
  public static TypeMatching of(Predicate<Class> p, IntPredicate pid) {
    return new TypeMatching() {
      @Override
      public boolean match(Class c) {
        return p.test(c);
      }
      @Override
      public boolean match(byte id) {
        return pid.test(id);
      }
    };
  }
  
}
