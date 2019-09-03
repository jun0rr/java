/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;


/**
 *
 * @author juno
 */
public class TestLockingKeys {
  
  
  public static void main(String[] args) throws AWTException {
    Robot r = new Robot();
    r.keyPress(KeyEvent.VK_NUM_LOCK);
    r.delay(30);
    r.keyRelease(KeyEvent.VK_NUM_LOCK);
    Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK, true);
    System.out.println(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK));
  }
  
}
