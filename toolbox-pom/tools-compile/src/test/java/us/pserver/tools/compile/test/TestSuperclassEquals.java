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
import us.pserver.tools.compile.impl.LambdaMethodImpl;
import us.pserver.tools.compile.impl.MethodImpl;


/**
 *
 * @author juno
 */
public class TestSuperclassEquals {
  
  @Test
  public void test_super_equals() {
    //Method thisMethod = Reflect.of(this.getClass()).selectMethod("test_super_equals").method().get();
    //MethodImpl mi = new MethodImpl(thisMethod){};
    //LambdaMethodImpl fi = new LambdaMethodImpl(Runnable.class, thisMethod);
    //System.out.println(fi.getSourceCode());
    //Assertions.assertEquals(mi, fi);
    //Assertions.assertEquals(mi.hashCode(), fi.hashCode());
    //Assertions.assertTrue(fi.equals(mi));
  }
  
}
