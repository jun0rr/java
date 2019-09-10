/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import us.pserver.robotnic.Key;
import us.pserver.robotnic.Keyboard;
import us.pserver.robotnic.RepeatingCombo;
import us.pserver.robotnic.Robotnic;
import us.pserver.robotnic.ScriptCombo;


/**
 *
 * @author juno
 */
public class TestNumbers {
  
  //0123456789
  
  public static void main(String[] args) {
    Robotnic r = Robotnic.getDefault();
    r.delay(2000);
    Keyboard._0.accept(r);
    Keyboard._1.accept(r);
    Keyboard._2.accept(r);
    Keyboard._3.accept(r);
    Keyboard._4.accept(r);
    Keyboard._5.accept(r);
    Keyboard._6.accept(r);
    Keyboard._7.accept(r);
    Keyboard._8.accept(r);
    Keyboard._9.accept(r);
  }
  
}
