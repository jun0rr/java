/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.awt.Point;
import java.util.Objects;


/**
 *
 * @author Juno
 */
public class DevicePoint {
  
  public int deviceID;
  
  public Point point;
  
  public DevicePoint() {
    this.deviceID = -1;
    point = new Point(0, 0);
  }
  
  public DevicePoint(int devID, Point p) {
    this.deviceID = devID;
    point = p;
  }
  
  @Override
  public int hashCode() {
    int hash = 3;
    hash = 37 * hash + this.deviceID;
    hash = 37 * hash + Objects.hashCode(this.point);
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
    final DevicePoint other = (DevicePoint) obj;
    if (this.deviceID != other.deviceID) {
      return false;
    }
    if (!Objects.equals(this.point, other.point)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return "DevicePoint{" + "deviceID=" + deviceID + ", point=" + point + '}';
  }
  
}
