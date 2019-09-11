/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.test;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Conditional;


/**
 *
 * @author juno
 */
public class TestConditional {
  
  @Test
  public void test_conditional() {
    try {
      Conditional<Integer,String> cond = Conditional.<Integer,String>eval(i->i > 0)
          .ifTrueReturns("Positive number!")
          .elseReturns("Negative number!")
          .elseThrows(new IOException("Bad number"))
          .and(i->i % 2 == 0)
          .ifTrueReturns("Positive EVEN number!")
          .elseReturns("Positive ODD number!")
          .buildAnd()
          .or(i->i == 0)
          .ifTrueReturns("ZERO!")
          .elseThrows(new IOException("Never gets HERE!"))
          .buildOr()
          .build();
      System.out.println(cond);
      System.out.println(cond.apply(0));
      System.out.println(cond.apply(5));
      System.out.println(cond.apply(10));
      System.out.println(cond.apply(-5));
      System.out.println(cond.apply(-10));
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
}
