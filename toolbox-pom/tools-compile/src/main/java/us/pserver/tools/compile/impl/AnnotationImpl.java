/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import us.pserver.tools.compile.SourceCode;


/**
 *
 * @author juno
 */
public class AnnotationImpl implements SourceCode, Typeable {
  
  private final Class<? extends Annotation> type;
  
  private final Map<String,Object> vals;
  
  public AnnotationImpl(Class<? extends Annotation> type, Map<String,Object> vals) {
    this.type = Objects.requireNonNull(type);
    this.vals = Collections.unmodifiableMap(vals);
  }
  
  @Override
  public Class<? extends Annotation> getType() {
    return type;
  }
  
  public Map<String,Object> values() {
    return vals;
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder("@");
    sb.append(type.getName());
    if(!vals.isEmpty()) {
      sb.append("(");
      Iterator<Entry<String,Object>> es = vals.entrySet().iterator();
      while(es.hasNext()) {
        Entry<String,Object> e = es.next();
        sb.append(e.getKey())
            .append("=")
            .append(AnnotationSourceCode.of(e.getValue()).getSourceCode())
            .append(",");
      }
      sb.deleteCharAt(sb.length() -1).append(")");
    }
    return sb.toString();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 19 * hash + Objects.hashCode(this.type);
    hash = 19 * hash + Objects.hashCode(this.vals);
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final AnnotationImpl other = (AnnotationImpl) obj;
    if (!Objects.equals(this.type, other.type)) {
      return false;
    }
    if (!Objects.equals(this.vals, other.vals)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}