/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import us.pserver.tools.Unchecked;
import us.pserver.tools.compile.impl.Annotated;
import us.pserver.tools.compile.impl.AnnotationImpl;


/**
 *
 * @author Juno
 */
public abstract class AnnotatedBuilder<P extends Builder<?>, A extends Annotated> extends AbstractNestedBuilder<P,A> {
  
  protected final List<AnnotationImpl> annots;
  
  public AnnotatedBuilder(P parent, Consumer<A> onbuild, ClassBuilderContext context) {
    super(parent, onbuild, context);
    this.annots = new ArrayList<>();
  }
  
  public AnnotatedBuilder(ClassBuilderContext context) {
    this(null, null, context);
  }

  public List<AnnotationImpl> getAnnotations() {
    return annots;
  }
  
  public AnnotatedBuilder<P,A> addAnnotation(AnnotationImpl a) {
    if(a != null) {
      this.annots.add(a);
    }
    return this;
  }
  
  public AnnotatedBuilder<P,A> addAnnotation(Annotation a) {
    Map<String,Object> vals = annotationMethods(a)
        .collect(Collectors.toMap(m->m.getName(), m->Unchecked.call(()->m.invoke(a))));
    this.annots.add(new AnnotationImpl(a.annotationType(), vals));
    return this;
  }
  
  private Stream<Method> annotationMethods(Annotation a) {
    return Stream.of(a.getClass().getInterfaces())
        .filter(Class::isAnnotation)
        .flatMap(c->Stream.of(c.getMethods()))
        .filter(m->Stream.concat(Stream.of(Proxy.class.getMethods()), Stream.of(Annotation.class.getMethods()))
            .map(n->n.getName().concat(Arrays.toString(n.getParameters())))
            .noneMatch(s->s.equals(m.getName().concat(Arrays.toString(m.getParameters())))));
  }
  
  public AnnotationBuilder<? extends AnnotatedBuilder> newAnnotation(Class<? extends Annotation> type) {
    return new AnnotationBuilder<>(this, annots::add, context).setType(type);
  }
  
  public abstract A build();
  
}
