/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.util.Random;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.impl.LambdaMethodImpl;


/**
 *
 * @author Juno
 */
public class TestLambdaMethodImpl {
  
  @Test
  public void test_function_method_impl() {
    try {
      //Function<String,Integer> fn = s->new Random().nextInt();
      //LambdaMethodImpl fi = new LambdaMethodImpl(fn.getClass(), Reflect.of(IPerson.class).selectMethod("str2int").method().get());
      //System.out.println(fi.getSourceCode());
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
