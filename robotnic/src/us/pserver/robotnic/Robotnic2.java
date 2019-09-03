/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;


/**
 *
 * @author f6036477
 */
public class Robotnic2 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws AWTException {
    if(args.length > 0 && args[0].equals("-p")) {
      ScreenPoint.main(args);
    }
    else {
      Robot rb = new Robot();
      Random rdm = new Random();
      while(true) {
        script(rb, rdm);
        ScriptException.run(() -> Thread.sleep(rdm.nextInt(2000*60) + 5000*60));
      }
    }
  }
  
  
  private static void script(Robot rb, Random rdm) {
    //select firefox
    rb.mouseMove(252, 1054);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 100);
    //select tab1
    rb.mouseMove(158, 23);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 100);
    //F5 reload
    key(rb, KeyEvent.VK_F5);
    rb.delay(rdm.nextInt(2000) + 4000);
    //open news
    rb.mouseMove(505, 454);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(2000) + 4000);
    //intranet
    rb.mouseMove(332, 183);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(2000) + 4000);

    //vdi
    rb.mouseMove(310, 1054);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 100);
    
    sisbb(rb, rdm);
    
    //select firefox
    rb.mouseMove(562, 1009);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 100);
    
    //select fluxo
    rb.mouseMove(1019, 109);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 100);
    
    //F5 reload
    key(rb, KeyEvent.VK_F5);
    rb.delay(rdm.nextInt(900) + 100);
    
    //select e-mail
    rb.mouseMove(824, 110);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 500);
    
    //select folder fluxo
    rb.mouseMove(782, 646);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 500);
    
    //select inbox
    rb.mouseMove(782, 394);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 500);
    
    //select first e-mail
    rb.mouseMove(1071, 365);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 500);
    
    //select Softphone
    rb.mouseMove(615, 1012);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 500);
    
    //select Info
    rb.mouseMove(494, 511);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 500);
    
    //select Pess
    rb.mouseMove(417, 511);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 500);
  }
  
  
  private static void sisbb(Robot rb, Random rdm) {
    //select sisbb
    rb.mouseMove(224, 1009);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(900) + 500);
    
    //reconnect
    rb.mouseMove(755, 334);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 500);
    rb.mouseMove(713, 334);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 500);
    
    //ENTER
    key(rb, KeyEvent.VK_ENTER);
    rb.delay(rdm.nextInt(1000) + 500);
    
    //chave/senha
    key(rb, KeyEvent.VK_F);
    key(rb, KeyEvent.VK_NUMPAD6);
    key(rb, KeyEvent.VK_NUMPAD0);
    key(rb, KeyEvent.VK_NUMPAD3);
    key(rb, KeyEvent.VK_NUMPAD6);
    key(rb, KeyEvent.VK_NUMPAD4);
    key(rb, KeyEvent.VK_NUMPAD7);
    key(rb, KeyEvent.VK_NUMPAD7);
    key(rb, KeyEvent.VK_NUMPAD7);
    key(rb, KeyEvent.VK_NUMPAD8);
    key(rb, KeyEvent.VK_NUMPAD9);
    key(rb, KeyEvent.VK_NUMPAD6);
    key(rb, KeyEvent.VK_NUMPAD3);
    key(rb, KeyEvent.VK_NUMPAD2);
    key(rb, KeyEvent.VK_NUMPAD5);
    key(rb, KeyEvent.VK_NUMPAD8);
    key(rb, KeyEvent.VK_P);
    key(rb, KeyEvent.VK_E);
    key(rb, KeyEvent.VK_S);
    key(rb, KeyEvent.VK_S);
    key(rb, KeyEvent.VK_O);
    key(rb, KeyEvent.VK_A);
    key(rb, KeyEvent.VK_L);
    key(rb, KeyEvent.VK_ENTER);
    rb.delay(rdm.nextInt(1000) + 500);
    
    //F5
    key(rb, KeyEvent.VK_F5);
    rb.delay(rdm.nextInt(1000) + 500);
  }
  
  
  private static void key(Robot rb, int key) {
    rb.keyPress(key);
    rb.delay(50);
    rb.keyRelease(key);
  }
  
  
  private static void click(Robot rb, int btn) {
    rb.mousePress(btn);
    rb.delay(100);
    rb.mouseRelease(btn);
  }
  
}
