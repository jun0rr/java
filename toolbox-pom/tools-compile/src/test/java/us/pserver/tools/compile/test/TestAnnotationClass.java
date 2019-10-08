/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestAnnotationClass {
  
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface MyAnnotation {
    int intValue() default 0;
  }
  
  @Test
  public void test_annotation_generic_reference() {
    Optional<Class<? extends Annotation>> opt = Optional.of(MyAnnotation.class);
    System.out.println(opt);
  }
  
}
