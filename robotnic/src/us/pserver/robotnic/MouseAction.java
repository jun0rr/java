/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.awt.Point;


/**
 *
 * @author juno
 */
public interface MouseAction extends Script {
  
  public MouseButton getButton();
  
  public Action getAction();
  
  public Point getPoint();
  
  public int getAmount();
  
  @Override
  public default void exec(Robotnic r) throws ScriptException {
    if(getPoint() != null) {
      r.mmove(getPoint());
    }
    for(int i = 0; i < getAmount(); i++) {
      switch(getAction()) {
        case PRESS:
          r.mpress(getButton());
          break;
        case RELEASE:
          r.mrelease(getButton());
          break;
        case SCROLL:
          r.mscroll(1);
          break;
        default:
          r.mclick(getButton());
          break;
      }
    }
  }
  
  
  public static MouseAction of(MouseButton b, Action a, Point p, int amount) {
    return new MouseAction() {
      @Override
      public MouseButton getButton() {
        return b;
      }
      @Override
      public Action getAction() {
        return a;
      }
      @Override
      public Point getPoint() {
        return p;
      }
      @Override
      public int getAmount() {
        return amount;
      }
    };
  }
  
  public static MouseAction of(MouseButton b, Action a, Point p) {
    return of(b, a, p, 1);
  }
  
  public static MouseAction of(MouseButton b, Action a) {
    return of(b, a, null, 1);
  }
  
  public static MouseAction click(MouseButton b) {
    return of(b, Action.CLICK, null, 1);
  }
  
  public static MouseAction click(MouseButton b, Point p) {
    return of(b, Action.CLICK, p, 1);
  }
  
  public static MouseAction click(MouseButton b, Point p, int amount) {
    return of(b, Action.CLICK, p, amount);
  }
  
  public static MouseAction click(MouseButton b, int amount) {
    return of(b, Action.CLICK, null, amount);
  }
  
  public static MouseAction press(MouseButton b) {
    return of(b, Action.PRESS, null, 1);
  }
  
  public static MouseAction press(MouseButton b, Point p) {
    return of(b, Action.PRESS, p, 1);
  }
  
  public static MouseAction release(MouseButton b) {
    return of(b, Action.RELEASE, null, 1);
  }
  
  public static MouseAction release(MouseButton b, Point p) {
    return of(b, Action.CLICK, p, 1);
  }
  
}
