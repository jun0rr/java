/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.io.Serializable;
import java.util.Random;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.FunctionMethodImpl;


/**
 *
 * @author Juno
 */
public class TestFunctionMethodImpl {
  
  @Test
  public void test_function_method_impl() {
    Function<String,Integer> fn = (Function<String,Integer> & Serializable) s->new Random().nextInt();
    FunctionMethodImpl fi = new FunctionMethodImpl(fn, Reflect.of(IPerson.class).selectMethod("str2int").method().get());
    System.out.println(fi.getSourceCode());
  }
  
}
