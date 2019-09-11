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
          .ifTrueEval(i->i % 2 == 0)
          .ifTrueApply(i->String.format("[%d] Positive EVEN number!", i))
          .elseApply(i->String.format("[%d] Positive ODD number!", i))
          .endTrueConditional()
          .elseEval(i->i == 0)
          .ifTrueApply(i->String.format("[%d] ZERO!", i))
          .elseApply(i->String.format("[%d] Negative number!", i))
          .endElseConditional()
          .elseThrows(i->new IOException("Bad number: " + i))
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
