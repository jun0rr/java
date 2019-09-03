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
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;


/**
 *
 * @author Juno
 */
public class TestGraphicsBounds {
  
  
  public static void main(String[] args) throws AWTException {
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
    robot.mouseMove(1919, 843);
    robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
    robot.delay(50);
    robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
  }
  
}
