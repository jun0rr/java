/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.impl;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Objects;
import java.util.Random;
import us.pserver.robotnic.Delay;
import us.pserver.robotnic.Key;
import us.pserver.robotnic.KeyAction;
import us.pserver.robotnic.MouseAction;
import us.pserver.robotnic.MouseButton;
import us.pserver.robotnic.Robotnic;
import us.pserver.robotnic.Script;
import us.pserver.robotnic.ScriptCombo;
import us.pserver.robotnic.ScriptException;
import us.pserver.robotnic.Shortcut;


/**
 *
 * @author juno
 */
public class DefaultRobotnic implements Robotnic {
  
  private final Robot robot;
  
  private final Random rdm;
  
  
  public DefaultRobotnic(Robot rbt, Random rdm) {
    this.robot = Objects.requireNonNull(rbt);
    this.rdm = Objects.requireNonNull(rdm);
  }
  
  public DefaultRobotnic() {
    this(ScriptException.call(() -> new Robot()), new Random());
  }
  
  
  @Override
  public Robotnic mouse(MouseAction... mas) {
    return script(new ScriptCombo(mas));
  }


  @Override
  public Robotnic mmove(Point p) {
    robot.mouseMove(p.x, p.y);
    return this;
  }


  @Override
  public Robotnic mclick(MouseButton b) {
    robot.mousePress(b.getMask());
    robot.delay(50);
    robot.mouseRelease(b.getMask());
    return this;
  }


  @Override
  public Robotnic mclick(MouseButton b, int n) {
    for(int i = 0; i < n; i++) {
      mclick(b);
    }
    return this;
  }


  @Override
  public Robotnic mclick(Point p, MouseButton b, int n) {
    return mmove(p).mclick(b, n);
  }


  @Override
  public Robotnic mdrag(MouseButton b, Point from, Point to) {
    return script(MouseAction.press(b, from), Delay.fixed(50), MouseAction.release(b, to));
  }


  @Override
  public Robotnic mpress(MouseButton b) {
    robot.mousePress(b.getMask());
    return this;
  }


  @Override
  public Robotnic mrelease(MouseButton b) {
    robot.mouseRelease(b.getMask());
    return this;
  }


  @Override
  public Robotnic mscroll(int n) {
    robot.mouseWheel(n);
    return this;
  }


  @Override
  public Robotnic key(KeyAction... kas) {
    return script(new ScriptCombo(kas));
  }


  @Override
  public Robotnic kpress(Key k) {
    robot.keyPress(k.getCode());
    return this;
  }


  @Override
  public Robotnic krelease(Key k) {
    robot.keyRelease(k.getCode());
    return this;
  }


  @Override
  public Robotnic ktype(Key k) {
    robot.keyPress(k.getCode());
    robot.delay(50);
    robot.keyRelease(k.getCode());
    return this;
  }


  @Override
  public Robotnic ktype(String s) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }


  @Override
  public Robotnic delay(int ms) {
    if(ms < 60_000) {
      robot.delay(ms);
    }
    else {
      ScriptException.run(() -> Thread.sleep(ms));
    }
    return this;
  }


  @Override
  public Robotnic randomDelay(int until) {
    return delay(rdm.nextInt(until));
  }


  @Override
  public Robotnic mixDelay(int fixed, int random) {
    return delay(fixed).randomDelay(random);
  }


  @Override
  public Image screenshot() {
    return robot.createScreenCapture(new Rectangle(screenSize()));
  }


  @Override
  public Image screenshot(Rectangle r) {
    return robot.createScreenCapture(r);
  }


  @Override
  public Robotnic waitFor(String s, Rectangle r) {
    while(true) {
      mdrag(MouseButton.BUTTON1, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height));
      script(Shortcut.COPY);
      String str = getClipboardString();
      if(s.equals(str)) break;
      delay(50);
    }
    return this;
  }


  @Override
  public Robotnic waitFor(Image i, Point p) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }


  @Override
  public Robotnic script(Script... ss) {
    new ScriptCombo(ss).exec(this);
    return this;
  }
  
  @Override
  public String getClipboardString() {
    return ScriptException.call(() -> getClipboardContent().getTransferData(DataFlavor.stringFlavor)).toString();
  }
  
  @Override
  public Robotnic setClipboardString(String str) {
    setClipboardContent(new StringSelection(str));
    return this;
  }
  
  @Override
  public Transferable getClipboardContent() {
    return ScriptException.call(() -> Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null));
  }
  
  @Override
  public Robotnic setClipboardContent(Transferable t) {
    ScriptException.run(() -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t, null));
    return this;
  }
  
  
  public static void main(String[] args) {
    
  }
  
}
