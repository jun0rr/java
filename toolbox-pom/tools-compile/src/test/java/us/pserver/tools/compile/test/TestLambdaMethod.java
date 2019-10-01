/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;


/**
 *
 * @author juno
 */
public class TestLambdaMethod {
  @Test
  public void find_lambda() {
    Function<Integer,String> fn = Objects::toString;
    System.out.println(fn.getClass().getName());
    System.out.println("...");
    Optional<Class<?>> oc = Arrays.asList(fn.getClass().getInterfaces()).stream()
        .filter(this::isFunctional)
        .findFirst();
    System.out.println(oc);
    Reflect.of(oc.get()).streamMethods()
        .filter(m->!m.isDefault())
        .filter(m->Modifier.isAbstract(m.getModifiers()))
        .filter(m->Modifier.isPublic(m.getModifiers()))
        .forEach(m->System.out.println("--> " + m));
    System.out.println("...");
    
  }
  
  private boolean isFunctional(Class<?> c) {
    return Arrays.asList(c.getAnnotations()).stream()
        .anyMatch(a->FunctionalInterface.class.isAssignableFrom(a.getClass()));
  }
}
