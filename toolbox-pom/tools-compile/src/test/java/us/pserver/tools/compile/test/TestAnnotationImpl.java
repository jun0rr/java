/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import org.junit.jupiter.api.Test;
import us.pserver.tools.compile.impl.AnnotationImpl;
import us.pserver.tools.compile.impl.ParameterImpl;


/**
 *
 * @author juno
 */
public class TestAnnotationImpl {
  @Test
  public void test_annotation() {
    AnnotationImpl a = new AnnotationImpl(Override.class);
    a.values().put("value", "hello");
    a.values().put("iterations", 5);
    System.out.println(a);
  }
  
  @Test
  public void test_parameter() {
    AnnotationImpl a = new AnnotationImpl(Override.class);
    a.values().put("value", "hello");
    a.values().put("iterations", 5);
    AnnotationImpl b = new AnnotationImpl(Deprecated.class);
    b.values().put("value", "world");
    b.values().put("iterations", 1);
    ParameterImpl p = new ParameterImpl(Integer.class, "magicNumber");
    p.annotations().add(a);
    p.annotations().add(b);
    System.out.println(p);
  }
}
