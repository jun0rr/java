/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;


/**
 *
 * @author juno
 */
public class MethodImpl extends Annotated implements SourceCode {
  
  private final Scope scope;
  
  private final Optional<Class> retype;
  
  private final String name;
  
  private final List<ParameterImpl> parameters;
  
  
  public MethodImpl(Scope s, Class r, String n) {
    super();
    this.scope = Objects.requireNonNull(s);
    this.retype = Optional.ofNullable(r);
    this.name = Objects.requireNonNull(n);
    this.parameters = new LinkedList<>();
  }
  
  public MethodImpl(Scope s, String n) {
    this(s, null, n);
  }
  
  public Optional<Class> getReturnType() {
    return retype;
  }
  
  public String getName() {
    return name;
  }
  
  public List<ParameterImpl> parameters() {
    return parameters;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode());
    sb.append(scope.name().toLowerCase())
        .append(" ")
        .append(retype.map(Class::getName).orElse("void"))
        .append(" ")
        .append(name)
        .append("( ");
    if(!parameters.isEmpty()) {
      for(int i = 0; i < parameters.size(); i++) {
        sb.append(parameters.get(i));
        if(i < parameters.size() -1) sb.append(", ");
      }
      sb.append(" ");
    }
    return sb.append(")").toString();
  }
  
}
