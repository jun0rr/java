/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import us.pserver.tools.compile.impl.AnnotationImpl;


/**
 *
 * @author Juno
 */
public class AnnotationBuilder<P extends Builder<?>> extends AbstractNestedBuilder<P,AnnotationImpl> {
  
  private Optional<Class<? extends Annotation>> type;
  
  private final Map<String,Object> vals;
  
  public AnnotationBuilder(P parent, Consumer<AnnotationImpl> onbuild, ClassBuilderContext context) {
    super(parent, onbuild, context);
    vals = new LinkedHashMap<>();
  }
  
  public AnnotationBuilder<P> putValue(String name, Object value) {
    if(name != null && !name.isBlank() && value != null) {
      vals.put(name, value);
    }
    return this;
  }
  
  public Map<String,Object> valuesMap() {
    return vals;
  }
  
  public AnnotationBuilder<P> setType(Class<? extends Annotation> type) {
    this.type = Optional.ofNullable(type);
    return this;
  }
  
  public Optional<Class<? extends Annotation>> getType() {
    return type;
  }
  
  @Override
  public P buildStep() {
    return super.buildStep();
  }

  @Override
  public AnnotationImpl build() {
    return new AnnotationImpl(type.orElseThrow(
        ()->new IllegalStateException("Annotation type not defined")), vals
    );
  }
  
}
