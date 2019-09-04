/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import java.util.stream.Stream;
import us.pserver.robotnic.Keyboard;
import us.pserver.robotnic.Robotnic;


/**
 *
 * @author juno
 */
public class TestKeyboard {
  
  //©{}dD
  
  public static void main(String[] args) {
    Robotnic r = Robotnic.getDefault();
    Keyboard.COPYRIGHT.exec(r);
    Keyboard.CURLY_BRACE_LEFT.exec(r);
    Keyboard.CURLY_BRACE_RIGHT.exec(r);
    Keyboard.D_LOWER.exec(r);
    Keyboard.D_UPPER.exec(r);
    
    //r.delay(5000);
    //Stream.of(Keyboard.values()).forEach(k -> k.exec(r));
  }
  
}
