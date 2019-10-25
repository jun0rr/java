/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.util.Objects;


/**
 *
 * @author Juno
 */
public class ParameterFieldInitializer implements FieldInitializer {
  
  private final String name;
  
  private final String parameterName;
  
  public ParameterFieldInitializer(String name, String parameterName) {
    this.name = Objects.requireNonNull(name);
    this.parameterName = Objects.requireNonNull(parameterName);
  }
  
  public ParameterFieldInitializer(String name) {
    this(name, name);
  }

  @Override
  public String getSourceCode() {
    return "this.".concat(name).concat(" = ").concat(parameterName).concat("; ");
  }
  
  @Override
  public Class getType() {
    return null;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
}
