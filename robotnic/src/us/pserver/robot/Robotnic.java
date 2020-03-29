/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;


/**
 *
 * @author f6036477
 */
public class Robotnic {

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
        Sleeper.of(rdm.nextInt(3000*60) + 3000*60).sleep();
      }
    }
  }

  private static void script(Robot rb, Random rdm) {
    //select firefox
    rb.mouseMove(250, 1055);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    //select tab1
    rb.mouseMove(70, 20);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    //F5 reload
    key(rb, KeyEvent.VK_F5);
    rb.delay(rdm.nextInt(4000) + 6000);
    //open news
    rb.mouseMove(660, 400);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(4000) + 6000);
    //intranet
    rb.mouseMove(320, 185);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(4000) + 6000);

    //vdi
    rb.mouseMove(305, 1055);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    sisbb(rb, rdm);
    
    //select firefox
    rb.mouseMove(510, 1010);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //select fluxo
    rb.mouseMove(1040, 110);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //F5 reload
    key(rb, KeyEvent.VK_F5);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //select e-mail
    rb.mouseMove(845, 110);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //select folder enviados
    rb.mouseMove(830, 465);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //select inbox
    rb.mouseMove(830, 390);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //select first e-mail
    rb.mouseMove(1040, 345);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //select Softphone
    rb.mouseMove(560, 1010);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //select Info
    rb.mouseMove(485, 425);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //select Pess
    rb.mouseMove(410, 430);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
  }
  
  
  private static void sisbb(Robot rb, Random rdm) {
    //select sisbb
    rb.mouseMove(220, 1010);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 2000);
    
    //reconnect
    rb.mouseMove(700, 328);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 5000);
    rb.mouseMove(660, 328);
    click(rb, MouseEvent.BUTTON1_DOWN_MASK);
    rb.delay(rdm.nextInt(1000) + 5000);
    
    //ENTER
    key(rb, KeyEvent.VK_ENTER);
    rb.delay(rdm.nextInt(1000) + 5000);
    
    //chave/senha
    key(rb, KeyEvent.VK_F);
    key(rb, KeyEvent.VK_NUMPAD6);
    key(rb, KeyEvent.VK_NUMPAD0);
    key(rb, KeyEvent.VK_NUMPAD3);
    key(rb, KeyEvent.VK_NUMPAD6);
    key(rb, KeyEvent.VK_NUMPAD4);
    key(rb, KeyEvent.VK_NUMPAD7);
    key(rb, KeyEvent.VK_NUMPAD7);
    key(rb, KeyEvent.VK_NUMPAD6);
    key(rb, KeyEvent.VK_NUMPAD5);
    key(rb, KeyEvent.VK_NUMPAD4);
    key(rb, KeyEvent.VK_NUMPAD1);
    key(rb, KeyEvent.VK_NUMPAD0);
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
    rb.delay(rdm.nextInt(1000) + 5000);
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
