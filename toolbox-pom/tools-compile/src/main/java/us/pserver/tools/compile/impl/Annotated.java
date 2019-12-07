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
import us.pserver.tools.compile.SourceCode;


/**
 *
 * @author juno
 */
public abstract class Annotated implements SourceCode {
  
  protected final List<AnnotationImpl> annotations;
  
  public Annotated(Collection<AnnotationImpl> ans) {
    this.annotations = Collections.unmodifiableList(new ArrayList<>(ans));
  }
  
  public List<AnnotationImpl> annotations() {
    return annotations;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder();
    if(!annotations.isEmpty()) {
      annotations.forEach(a->sb.append(a.getSourceCode()).append(" "));
    }
    return sb.toString();
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
