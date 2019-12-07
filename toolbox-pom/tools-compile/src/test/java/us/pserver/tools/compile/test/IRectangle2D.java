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
public interface IRectangle2D extends ITranslatable, IScalable, Comparable<IRectangle2D> {
  
  public IPoint2D point();
  
  public IDimension2D dimension();
  
  @Override
  public IRectangle2D scale(double d);
  
  @Override
  public IRectangle2D scale(double w, double h);
  
  @Override
  public IRectangle2D translate(double x);
  
  @Override
  public IRectangle2D translate(double x, double y);
  
}
