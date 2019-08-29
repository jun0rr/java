/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robot;

import java.awt.Point;


/**
 *
 * @author juno
 */
public interface MouseAction extends Script {
  
  public MButton getButton();
  
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
  
}
