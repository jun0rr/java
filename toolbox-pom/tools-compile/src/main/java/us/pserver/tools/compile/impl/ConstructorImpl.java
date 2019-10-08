/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 *
 * @author juno
 */
public class ConstructorImpl extends MethodImpl {
  
  private final List<FieldInitializer> inits;
  
  private final Optional<VarConsumer> consumer;
  
  private final Optional<SuperConstructorImpl> superCall;
  
  public ConstructorImpl(Collection<AnnotationImpl> ans, String name, Collection<ParameterImpl> pars, Optional<SuperConstructorImpl> superCall, Collection<FieldInitializer> inits, Optional<VarConsumer> cs, int mods) {
    super(ans, Optional.empty(), name, pars, mods);
    this.inits = Collections.unmodifiableList(new ArrayList<>(inits));
    this.consumer = cs;
    this.superCall = superCall;
  }
  
  public List<FieldInitializer> fieldInitializers() {
    return inits;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode());
    int ri = sb.indexOf("void ");
    sb.delete(ri, ri+5);
    //sb.append(getScope().name().toLowerCase())
        //.append(" ")
        //.append(name)
        //.append("(");
    //if(!parameters.isEmpty()) {
      //parameters.forEach(p->sb.append(p.getSourceCode()).append(", "));
      //sb.delete(sb.length() -2, sb.length());
    //}
    sb.append(" { ");
    superCall.ifPresent(s->sb.append(s.getSourceCode()));
    inits.forEach(f->sb.append(f.getSourceCode()));
    if(consumer.isPresent()) {
      sb.append(VarConsumer.class.getName())
          .append(" fn = (")
          .append(VarConsumer.class.getName())
          .append(") LAMBDA_MAP.get(\"")
          .append(getMethodSignature())
          .append("\"); fn.accept( ");
      if(!parameters().isEmpty()) {
        parameters().stream()
            .forEach(p->sb.append(p.getName()).append(", "));
        sb.delete(sb.length() -2, sb.length());
      }
      else {
        sb.append("null");
      }
      sb.append(" ); ");
    }
    return sb.append("} ").toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.name);
    hash = 97 * hash + Objects.hashCode(this.parameters);
    hash = 97 * hash + this.getModifiers();
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if(obj == null) {
      return false;
    }
    if(getClass() != obj.getClass()) {
      return false;
    }
    final ConstructorImpl other = (ConstructorImpl) obj;
    if(this.getModifiers() != other.getModifiers()) {
      return false;
    }
    if(!Objects.equals(this.name, other.name)) {
      return false;
    }
    if(!Objects.equals(this.parameters, other.parameters)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }

}
