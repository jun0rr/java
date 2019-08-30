/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;


/**
 *
 * @author juno
 */
public enum MouseButton {
  
  BUTTON1(1024),
  BUTTON2(2048),
  BUTTON3(4096);
  
  private MouseButton(int mask) {
    this.mask = mask;
  }
  
  private final int mask;
  
  public int getMask() {
    return mask;
  }
  
}
