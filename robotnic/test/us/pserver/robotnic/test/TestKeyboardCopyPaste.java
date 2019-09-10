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
 * @author Juno
 */
public class TestKeyboardCopyPaste {
  
  //Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam et.
  //Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam et.
  
  public static void main(String[] args) {
    Robotnic r = Robotnic.getDefault();
    r.delay(5000);
    ScriptCombo nl = ScriptCombo.of(Keyboard.ENTER, RepeatingCombo.of(Key.SLASH, 2));
    Keyboard.COPY.accept(r);
    Keyboard.END.accept(r);
    nl.accept(r);
    Keyboard.PASTE.accept(r);
  }
  
}
