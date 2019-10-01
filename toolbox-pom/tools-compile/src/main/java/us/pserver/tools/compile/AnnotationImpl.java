/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;


/**
 *
 * @author juno
 */
public class AnnotationImpl implements SourceCode, Typeable {
  
  private final Class type;
  
  private final Map<String,Object> vals;
  
  public AnnotationImpl(Class type) {
    this.type = Objects.requireNonNull(type);
    vals = new LinkedHashMap<>();
  }
  
  @Override
  public Class getType() {
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
      sb.append("{");
      Iterator<Entry<String,Object>> es = vals.entrySet().iterator();
      while(es.hasNext()) {
        Entry<String,Object> e = es.next();
        sb.append(e.getKey()).append("=");
        if(e.getValue() instanceof CharSequence) {
          sb.append("\"").append(e.getValue()).append("\"");
        }
        else {
          sb.append(e.getValue());
        }
        sb.append(",");
      }
      sb.deleteCharAt(sb.length() -1).append("}");
    }
    return sb.toString();
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
