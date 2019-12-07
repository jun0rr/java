/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
    Map<String,Object> map = new HashMap<>();
    map.put("value", "hello");
    map.put("iterations", 5);
    AnnotationImpl a = new AnnotationImpl(Override.class, map);
    System.out.println(a);
  }
  
  @Test
  public void test_parameter() {
    Map<String,Object> map = new HashMap<>();
    map.put("value", "hello");
    map.put("iterations", 5);
    AnnotationImpl a = new AnnotationImpl(Override.class, map);
    map = new HashMap<>();
    map.put("value", "world");
    map.put("iterations", 1);
    AnnotationImpl b = new AnnotationImpl(Deprecated.class, map);
    ParameterImpl p = new ParameterImpl(Arrays.asList(a, b), Integer.class, "magicNumber");
    System.out.println(p);
  }
}
