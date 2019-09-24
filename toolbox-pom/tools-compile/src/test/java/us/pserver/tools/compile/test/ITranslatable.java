/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;


/**
 *
 * @author juno
 */
public interface ITranslatable {
  
  public ITranslatable translate(double x);
  
  public ITranslatable translate(double x, double y);
  
  
  
  public static int translateInt(int i, double t) {
    return Double.valueOf(Math.rint(t*i)).intValue();
  }
  
}
