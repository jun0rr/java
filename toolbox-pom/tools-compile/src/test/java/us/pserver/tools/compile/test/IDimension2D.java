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
public interface IDimension2D extends IScalable, Comparable<IDimension2D> {
  
  public int width();
  
  public int height();
  
  @Override
  public IDimension2D scale(double x);
  
  @Override
  public IDimension2D scale(double w, double h);
  
}
