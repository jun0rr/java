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
public interface IPoint2D extends ITranslatable, Comparable<IPoint2D> {

  public int x();
  
  public int y();
  
  public IPoint2D translate(double x);
  
  public IPoint2D translate(double x, double y);
  
}
