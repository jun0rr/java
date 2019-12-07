/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.util.function.Supplier;


/**
 *
 * @author juno
 */
public class SupplierFieldInitializer extends NewFieldInitializer {
  
  public SupplierFieldInitializer(String name, Class initType) {
    super(name, initType);
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder("this.");
    return sb.append(getName())
        .append(" = ((")
        .append(Supplier.class.getName())
        .append("<")
        .append(getType().getName())
        .append(">) LAMBDA_MAP.get(\"")
        .append(getName())
        .append("Supplier<")
        .append(getType().getName())
        .append(">\")).get(); ")
        .toString();
  }
  
  @Override
  public int hashCode() {
    return super.hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
