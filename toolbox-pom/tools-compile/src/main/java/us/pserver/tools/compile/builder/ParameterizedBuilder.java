/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import us.pserver.tools.compile.impl.ParameterImpl;


/**
 *
 * @author juno
 */
public abstract class ParameterizedBuilder<P extends Builder<?>,T> extends AbstractNestedBuilder<P,T> {

  protected final List<ParameterImpl> parameters;
  
  public ParameterizedBuilder(P parent, Consumer<T> onbuild, Collection<ParameterImpl> pars, ClassBuilderContext context) {
    super(parent, onbuild, context);
    this.parameters = new ArrayList<>(pars);
  }
  
  public ParameterizedBuilder(P parent, Consumer<T> onbuild, ClassBuilderContext context) {
    this(parent, onbuild, Collections.EMPTY_LIST, context);
  }
  
  public ParameterizedBuilder(ClassBuilderContext context) {
    this(null, null, context);
  }
  
  public List<ParameterImpl> getParameters() {
    return parameters;
  }
  
  public ParameterizedBuilder<P,T> addParameter(Class<?> type, String name) {
    if(type != null && name != null && !name.isBlank()) {
      parameters.add(new ParameterImpl(Collections.EMPTY_LIST, type, name));
    }
    return this;
  }
  
  public ParameterizedBuilder<P,T> addParameter(ParameterImpl p) {
    if(p != null) {
      parameters.add(p);
    }
    return this;
  }
  
  public ParameterBuilder<? extends ParameterizedBuilder> newParameter() {
    return new ParameterBuilder<>(this, this::addParameter, context);
  }
  
  public ParameterizedBuilder<P,T> clearParameters() {
    parameters.clear();
    return this;
  }
  
}
