/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.util.Objects;


/**
 *
 * @author juno
 */
public class RectangleDef implements IRectangle2D {
  
  private final IPoint2D point;
  
  private final IDimension2D dim;
  
  public RectangleDef(IPoint2D p, IDimension2D d) {
    this.point = Objects.requireNonNull(p);
    this.dim = Objects.requireNonNull(d);
  }
  
  public RectangleDef(int x, int y, int width, int height) {
    this(new PointDef(x, y), new DimensionDef(width, height));
  }

  @Override
  public IPoint2D point() {
    return point;
  }
  
  @Override
  public IDimension2D dimension() {
    return dim;
  }
  
  @Override
  public IRectangle2D scale(double d) {
    return new RectangleDef(point, dim.scale(d));
  }
  
  @Override
  public IRectangle2D scale(double w, double h) {
    return new RectangleDef(point, dim.scale(w, h));
  }


  @Override
  public IRectangle2D translate(double x) {
    return new RectangleDef(point.translate(x), dim);
  }
  
  @Override
  public IRectangle2D translate(double x, double y) {
    return new RectangleDef(point.translate(x, y), dim);
  }
  
  @Override
  public int compareTo(IRectangle2D o) {
    return Integer.compare(
        point.x() + point.y() + dim.width() + dim.height(), 
        o.point().x() + o.point().y() + o.dimension().width() + o.dimension().height()
    );
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 89 * hash + Objects.hashCode(this.point);
    hash = 89 * hash + Objects.hashCode(this.dim);
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
    final IRectangle2D other = (IRectangle2D) obj;
    if (!Objects.equals(this.point, other.point())) {
      return false;
    }
    return Objects.equals(this.dim, other.dimension());
  }
  
  @Override
  public String toString() {
    return "IRectangle2D{" + "point=" + point + ", dim=" + dim + '}';
  }
  
}
