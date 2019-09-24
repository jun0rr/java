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
public class DimensionDef implements IDimension2D {
  
  private final int w, h;
  
  public DimensionDef(int width, int height) {
    this.w = width;
    this.h = height;
  }

  @Override
  public int width() {
    return w;
  }
  
  @Override
  public int height() {
    return h;
  }
  
  @Override
  public IDimension2D scale(double x) {
    return new DimensionDef(translateInt(w,x), translateInt(h,x));
  }
  
  @Override
  public IDimension2D scale(double w, double h) {
    return new DimensionDef(translateInt(this.w,w), translateInt(this.h,h));
  }
  
  @Override
  public int compareTo(IDimension2D o) {
    return Integer.compare(this.w + this.h, o.width() + o.height());
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + this.w;
    hash = 53 * hash + this.h;
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
    final IDimension2D other = (IDimension2D) obj;
    if (this.w != other.width()) {
      return false;
    }
    return this.h == other.height();
  }
  
  @Override
  public String toString() {
    return "IDimension2D{" + "width=" + w + ", height=" + h + '}';
  }
  
}
