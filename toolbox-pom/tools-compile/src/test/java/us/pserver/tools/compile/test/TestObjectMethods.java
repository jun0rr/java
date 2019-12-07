/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Method;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;


/**
 *
 * @author juno
 */
public class TestObjectMethods {
  @Test
  public void test_object_default_methods() {
    System.out.println("--- Object Methods ---");
    Reflect.of(Object.class).streamMethods().forEach(System.out::println);
    System.out.println("--- String Methods ---");
    Reflect.of(String.class).streamMethods().forEach(System.out::println);
    Optional<Method> swait = Reflect.of(String.class).selectMethod("wait").method();
    Optional<Method> owait = Reflect.of(Object.class).selectMethod("wait").method();
    System.out.println("swait: " + swait);
    System.out.println("owait: " + owait);
    System.out.println("swait.equals( owait ): " + swait.equals(owait));
  }
}
