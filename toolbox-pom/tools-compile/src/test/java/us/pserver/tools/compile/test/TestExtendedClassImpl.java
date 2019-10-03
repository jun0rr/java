/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import org.junit.jupiter.api.Test;
import us.pserver.tools.compile.ExtendedClassImpl;


/**
 *
 * @author juno
 */
public class TestExtendedClassImpl {
  
  @Test
  public void test_extended_class_impl() {
    ExtendedClassImpl ci = new ExtendedClassImpl(IPerson.class);
    System.out.println(ci.getSourceCode());
  }
  
}
