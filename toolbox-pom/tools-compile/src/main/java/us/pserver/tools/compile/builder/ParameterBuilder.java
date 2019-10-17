/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.util.Objects;
import java.util.function.Consumer;
import us.pserver.tools.compile.impl.ParameterImpl;


/**
 *
 * @author juno
 */
public class ParameterBuilder<P extends Builder<?>> extends AnnotatedBuilder<P,ParameterImpl> {
  
  protected Class<?> type;
  
  protected String name;
  
  public ParameterBuilder(P parent, Consumer<ParameterImpl> onbuild) {
    super(parent, onbuild);
    this.type = null;
    this.name = null;
  }
  
  public Class<?> getType() {
    return type;
  }
  
  public String getName() {
    return name;
  }
  
  public ParameterBuilder<P> setType(Class<?> type) {
    if(type != null) {
      this.type = type;
    }
    return this;
  }
  
  public ParameterBuilder<P> setName(String name) {
    if(name != null) {
      this.name = name;
    }
    return this;
  }
  
  public ParameterImpl build() {
    return new ParameterImpl(this.annots, 
        Objects.requireNonNull(this.type, "Bad Null Parameter Type"), 
        Objects.requireNonNull(this.name, "Bad Null Parameter Name")
    );
  }
  
}
