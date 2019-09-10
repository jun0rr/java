/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import us.pserver.robotnic.impl.DefaultRobotnic;


/**
 *
 * @author juno
 */
public interface Robotnic {
  
  public String getClipboardString();
  
  public Robotnic setClipboardString(String str);
  
  public Transferable getClipboardContent();
  
  public Robotnic setClipboardContent(Transferable t);
  
  public boolean getKeyLockState(Key k);
  
  public Robotnic setKeyLockState(Key k, boolean state);
  
  public Robotnic mmove(Point p);
  
  public Robotnic mclick(MouseButton b);
  
  public Robotnic mclick(MouseButton b, int n);
  
  public Robotnic mclick(Point p, MouseButton b, int n);
  
  public Robotnic mdrag(MouseButton b, Point from, Point to);
  
  public Robotnic mpress(MouseButton b);
  
  public Robotnic mrelease(MouseButton b);
  
  public Robotnic mscroll(int n);
  
  public Robotnic kpress(Key k);
  
  public Robotnic krelease(Key k);
  
  public Robotnic ktype(Key k);
  
  public Robotnic ktype(String s);
  
  public Robotnic delay(int ms);
  
  public Robotnic randomDelay(int until);
  
  public Robotnic mixDelay(int fixed, int random);
  
  public Image screenshot();
  
  public Image screenshot(Rectangle r);
  
  public Robotnic stringType(String s);
  
  public Robotnic waitFor(String s, Rectangle r);
  
  public Robotnic waitFor(Image i, Point p);
  
  public Point find(Image i);
  
  public Robotnic script(Script... ss);
  
  
  
  public static Robotnic of(GraphicsDevice gdev) {
    return new DefaultRobotnic(gdev);
  }
  
  public static Robotnic getDefault() {
    return new DefaultRobotnic(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
  }
  
}
