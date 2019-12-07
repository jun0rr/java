/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import static us.pserver.tools.compile.test.ITranslatable.translateInt;


/**
 *
 * @author juno
 */
public class PointDef implements IPoint2D {
  
  private final int x, y;
  
  public PointDef(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int x() {
    return x;
  }
  
  @Override
  public int y() {
    return y;
  }
  
  @Override
  public IPoint2D translate(double t) {
    return new PointDef(translateInt(x, t), translateInt(y, t));
  }
  
  @Override
  public IPoint2D translate(double x, double y) {
    return new PointDef(translateInt(this.x, x), translateInt(this.y, y));
  }
  
  @Override
  public int compareTo(IPoint2D o) {
    return Integer.compare(this.x + this.y, o.x() + o.y());
  }
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + this.x;
    hash = 29 * hash + this.y;
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final IPoint2D other = (IPoint2D) obj;
    if (this.x != other.x()) {
      return false;
    }
    return this.y == other.y();
  }
  
  @Override
  public String toString() {
    return "IPoint2D{" + "x=" + x + ", y=" + y + '}';
  }
  
}
