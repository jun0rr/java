/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robot;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;


/**
 *
 * @author juno
 */
public interface Robotnic {
  
  public Robotnic mouse(MouseAction... mas);
  
  public Robotnic mrepeat(MouseAction m, int n);
  
  public Robotnic mmove(Point p);
  
  public Robotnic mclick(MButton b);
  
  public Robotnic mclick(MButton b, int n);
  
  public Robotnic mclick(Point p, MButton b, int n);
  
  public Robotnic mdrag(MButton b, Point from, Point to);
  
  public Robotnic mpress(MButton b);
  
  public Robotnic mrelease(MButton b);
  
  public Robotnic mscroll(int n);
  
  public Robotnic key(KeyAction... kas);
  
  public Robotnic krepeat(KeyAction k, int n);
  
  public Robotnic kpress(Key k);
  
  public Robotnic krelease(Key k);
  
  public Robotnic ktype(Key k);
  
  public Robotnic ktype(String s);
  
  public Robotnic delay(long ms);
  
  public Robotnic mixDelay(long fixed, long random);
  
  public Image screenshot();
  
  public Image screenshot(Rectangle r);
  
  public Robotnic waitFor(String s, Rectangle r);
  
  public Robotnic waitFor(Image i, Point p);
  
  public Robotnic script(Script... ss);
  
}
