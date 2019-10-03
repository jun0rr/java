/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.ConstructorImpl;


/**
 *
 * @author juno
 */
public class TestConstructorImpl {
  
  @Test
  public void test_constructor_impl() {
    Constructor cct = Reflect.of(PointDef.class).selectConstructor(int.class, int.class).constructor().get();
    ConstructorImpl ci = new ConstructorImpl(cct);
    System.out.println(ci);
  }
  
}
