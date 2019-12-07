/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.util.Collection;
import java.util.function.Consumer;
import us.pserver.tools.compile.impl.ParameterImpl;
import us.pserver.tools.compile.impl.SuperConstructorImpl;


/**
 *
 * @author juno
 */
public class SuperConstructorBuilder<P extends Builder<?>> extends ParameterizedBuilder<P,SuperConstructorImpl> {

  public SuperConstructorBuilder(P parent, Consumer<SuperConstructorImpl> onbuild, Collection<ParameterImpl> pars, ClassBuilderContext context) {
    super(parent, onbuild, pars, context);
  }
  
  public SuperConstructorBuilder(P parent, Consumer<SuperConstructorImpl> onbuild, ClassBuilderContext context) {
    super(parent, onbuild, context);
  }
  
  public SuperConstructorBuilder(ClassBuilderContext context) {
    this(null, null, context);
  }
  
  @Override
  public SuperConstructorBuilder<P> addParameter(Class<?> type, String name) {
    super.addParameter(type, name);
    return this;
  }
  
  public SuperConstructorBuilder<P> addParameter(ParameterImpl p) {
    super.addParameter(p);
    return this;
  }
  
  @Override
  public ParameterBuilder<SuperConstructorBuilder<P>> newParameter() {
    return new ParameterBuilder<>(this, this::addParameter, context);
  }
  
  @Override
  public SuperConstructorBuilder<P> clearParameters() {
    super.clearParameters();
    return this;
  }
  
  @Override
  public SuperConstructorImpl build() {
    return new SuperConstructorImpl(parameters);
  }
  
}
