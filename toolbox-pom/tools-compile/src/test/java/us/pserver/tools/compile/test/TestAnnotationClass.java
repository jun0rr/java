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
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;


/**
 *
 * @author Juno
 */
public class TestAnnotationClass {
  
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface MyAnnotation {
    int value() default 0;
    String name() default "MyAnnotation.name";
    Class type() default String.class;
  }
  
  @MyAnnotation(5)
  public static class MyClass {}
  
  @Test
  public void test_annotation_generic_reference() {
    List<Annotation> ans = Arrays.asList(MyClass.class.getAnnotations());
    ans.stream()
        .peek(a->System.out.printf("%s, isAnnotation=%s:%n", a.getClass().getName(), a.getClass().isAnnotation()))
        .flatMap(a->Stream.of(a.getClass().getInterfaces()))
        .forEach(c->System.out.printf("   - %s, isAnnotation=%s%n", c.getName(), c.isAnnotation()));
  }
  
}
