/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;


/**
 *
 * @author juno
 */
public class TestFunctional {
  
  public static interface NotFunctional {
    public int nextInt();
  }
  
  public static abstract class ClassNotFunctional {
    public abstract int nextInt();
  }
  
  private boolean isFunctional(Class<?> c) {
    return c.isInterface() 
        && Reflect.of(c).streamMethods()
        .filter(m->Modifier.isPublic(m.getModifiers()))
        .filter(m->!m.isDefault())
        .filter(m->Modifier.isAbstract(m.getModifiers()))
        .count() == 1;
  }
  
  @Test
  public void test_not_functional() {
    NotFunctional n = ()->5;
    //NotFunctionalClass f = ()->5;
    Class<?> c = NotFunctional.class;
    System.out.printf("isFunctional( %s ): %s%n", c.getName(), isFunctional(c));
    c = ClassNotFunctional.class;
    System.out.printf("isFunctional( %s ): %s%n", c.getName(), isFunctional(c));
    System.out.println(n.nextInt());
  }
  
}
