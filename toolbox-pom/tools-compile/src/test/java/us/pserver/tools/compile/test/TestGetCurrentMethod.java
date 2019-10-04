/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;


/**
 *
 * @author juno
 */
public class TestGetCurrentMethod {
  
  @Test
  public void test_get_curent_method() {
    Method thisMethod = Reflect.of(this.getClass()).selectMethod("test_get_curent_method").method().get();
    class Local {};
    Assertions.assertEquals(thisMethod, Local.class.getEnclosingMethod());
  }
  
}
