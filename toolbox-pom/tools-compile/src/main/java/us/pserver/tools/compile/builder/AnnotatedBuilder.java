/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import us.pserver.tools.compile.ProxyBuilder;
import us.pserver.tools.compile.impl.Annotated;
import us.pserver.tools.compile.impl.AnnotationImpl;


/**
 *
 * @author Juno
 */
public abstract class AnnotatedBuilder<P extends Builder<?>, A extends Annotated> extends AbstractNestedBuilder<P,A> {
  
  protected final List<AnnotationImpl> annots;
  
  public AnnotatedBuilder(P parent, Consumer<A> onbuild) {
    super(parent, onbuild);
    this.annots = new ArrayList<>();
  }
  
  public AnnotatedBuilder() {
    this(null, null);
  }

  public List<AnnotationImpl> getAnnotations() {
    return annots;
  }
  
  public AnnotationBuilder<? extends AnnotatedBuilder<P,A>> newAnnotation(Class<? extends Annotation> type) {
    return new AnnotationBuilder<>(this, annots::add).setType(type);
  }
  
  //@Override
  //public P buildStep() {
    //return super.buildStep();
  //}

  public abstract A build();
  
}
