/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.test;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestAtomicReference {
  @Test
  public void method() {
    AtomicReference<String> ref = new AtomicReference<>();
    if(ref.compareAndExchange(null, "Hello") == null) {
      System.out.println("-> CompareAndExchange(null, 'Hello') successful!");
    }
    System.out.printf("-> CompareAndExchange(null, 'World') = %s%n", ref.compareAndExchange(null, "World"));
  }
}
