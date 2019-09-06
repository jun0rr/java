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
import us.pserver.robotnic.Key;
import us.pserver.robotnic.Robotnic;


/**
 *
 * @author juno
 */
public class TestLockingKeys {
  
  //]
  
  public static void main(String[] args) throws AWTException {
    //Robot r = new Robot();
    //r.delay(5000);
    //r.keyPress(KeyEvent.VK_D);
    //r.delay(30);
    //r.keyRelease(KeyEvent.VK_D);
    //r.keyPress(KeyEvent.VK_NUM_LOCK);
    //r.delay(30);
    //r.keyRelease(KeyEvent.VK_NUM_LOCK);
    //Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK, true);
    //System.out.println(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK));
    Robotnic r = Robotnic.getDefault();
    System.out.printf("* r.getKeyLockState(Key.NUM_LOCK)       = [%s]%n", r.getKeyLockState(Key.NUM_LOCK));
    System.out.printf("* r.setKeyLockState(Key.NUM_LOCK, false)%n", r.setKeyLockState(Key.NUM_LOCK, false));
    System.out.printf("* r.getKeyLockState(Key.NUM_LOCK)       = [%s]%n", r.getKeyLockState(Key.NUM_LOCK));
    System.out.printf("* r.setKeyLockState(Key.NUM_LOCK, true)%n", r.setKeyLockState(Key.NUM_LOCK, true));
    System.out.printf("* r.getKeyLockState(Key.NUM_LOCK)       = [%s]%n", r.getKeyLockState(Key.NUM_LOCK));
  }
  
}
