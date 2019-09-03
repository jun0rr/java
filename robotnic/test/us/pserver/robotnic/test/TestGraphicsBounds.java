/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import us.pserver.robotnic.Key;


/**
 *
 * @author Juno
 */
public class TestGraphicsBounds {
  
  //[
  
  public static void main(String[] args) throws AWTException {
    int vk = KeyEvent.VK_0;
    System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
    var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    var gds = ge.getScreenDevices();
    for(int i = 0; i < gds.length; i++) {
      System.out.printf("-- GraphicsDevice[%d]{name=%s, .bounds=%s}\n", i, gds[i].getIDstring(), gds[i].getDefaultConfiguration().getBounds());
    }
    Rectangle2D res = new Rectangle2D.Double();
    Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
        .stream().map(d -> d.getDefaultConfiguration().getBounds())
        .peek(System.out::println)
        .forEach(r -> Rectangle2D.union(res, r, res));
    System.out.println(res.getBounds());
    
    Robot robot = new Robot();
    robot.delay(5000);
    robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
    robot.delay(30);
    robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
  }
  
}
