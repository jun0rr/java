/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import us.pserver.tools.compile.builder.AnnotatedBuilder;
import us.pserver.tools.compile.builder.ClassBuilderContext;
import us.pserver.tools.compile.impl.Annotated;


/**
 *
 * @author Juno
 */
public class TestAnnotationBuilder {
  
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
  public void test_annotation_builder_from_annotation() {   
    try {
      ClassBuilderContext ctx = new ClassBuilderContext(TestAnnotationBuilder.class.getName());
      AnnotatedBuilder ab = new AnnotatedBuilder(ctx){
        public Annotated build() { return null; }
      };
      Stream.of(MyClass.class.getAnnotations()).forEach(a->ab.addAnnotation(a));
      ab.getAnnotations().forEach(System.out::println);
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
