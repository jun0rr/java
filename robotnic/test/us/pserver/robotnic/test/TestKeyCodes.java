/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.KeyEvent;


/**
 *
 * @author juno
 */
public class TestKeyCodes {
  
  ///æ©
  
  public static void main(String[] args) throws AWTException {
    GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    Robot r = new Robot(screen);
    r.delay(2000);
    r.keyPress(47);
    r.delay(30);
    r.keyRelease(47);
    r.keyPress(KeyEvent.VK_COMPOSE);
    r.keyPress(KeyEvent.VK_A);
    r.delay(30);
    r.keyRelease(KeyEvent.VK_A);
    r.keyPress(KeyEvent.VK_E);
    r.delay(30);
    r.keyRelease(KeyEvent.VK_E);
    r.delay(30);
    r.keyRelease(KeyEvent.VK_COMPOSE);
    
    r.keyPress(KeyEvent.VK_COMPOSE);
    r.keyPress(KeyEvent.VK_O);
    r.delay(30);
    r.keyRelease(KeyEvent.VK_O);
    r.keyPress(KeyEvent.VK_C);
    r.delay(30);
    r.keyRelease(KeyEvent.VK_C);
    r.delay(30);
    r.keyRelease(KeyEvent.VK_COMPOSE);
  }
  
}
