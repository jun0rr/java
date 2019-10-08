/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Modifier;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import us.pserver.tools.compile.builder.LambdaMethodBuilder;


/**
 *
 * @author Juno
 */
public class TestLambdaMethodBuilder {
  
  @Test
  public void test_lambda_method_builder() {
    System.out.println(new LambdaMethodBuilder<>()
        .newAnnotation(Override.class)
        .putValue("value", "hello world")
        .buildStep()
        .setModifiers(Modifier.PUBLIC | Modifier.STATIC)
        .setName("sum")
        .addParameter(Integer.class, "i1")
        .addParameter(Integer.class, "i2")
        .setReturnType(Integer.class)
        .setLambda((BiFunction<Integer,Integer,Integer>)(a,b)->a+b)
        .build());
  }
  
}
