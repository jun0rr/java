/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.util.Objects;


/**
 *
 * @author juno
 */
public class DefaultFieldInitializer implements FieldInitializer {

  private final Class initClass;
  
  private final String name;
  
  public DefaultFieldInitializer(String name, Class initClass) {
    this.name = name;
    this.initClass = initClass;
  }
  
  public DefaultFieldInitializer(FieldImpl f) {
    this(f.getName(), f.getType());
  }
  
  @Override
  public Class getType() {
    return initClass;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public String getSourceCode() {
    return "this."
        .concat(name)
        .concat(" = new ")
        .concat(initClass.getName())
        .concat("(); ");
  }
  
  @Override
  public int hashCode() {
    int hash = 3;
    hash = 89 * hash + Objects.hashCode(this.initClass);
    hash = 89 * hash + Objects.hashCode(this.name);
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
    final DefaultFieldInitializer other = (DefaultFieldInitializer) obj;
    if (!Objects.equals(this.initClass, other.initClass)) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
