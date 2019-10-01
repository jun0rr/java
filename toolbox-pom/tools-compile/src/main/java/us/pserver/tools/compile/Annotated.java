/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author juno
 */
public abstract class Annotated implements SourceCode {
  
  protected final List<AnnotationImpl> annotations;
  
  public Annotated() {
    this.annotations = new LinkedList<>();
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
