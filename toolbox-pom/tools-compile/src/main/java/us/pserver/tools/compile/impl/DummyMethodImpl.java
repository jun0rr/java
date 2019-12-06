/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 *
 * @author juno
 */
public class DummyMethodImpl extends MethodImpl {
  
  public DummyMethodImpl(Collection<AnnotationImpl> ans, Class<?> r, String n, Collection<ParameterImpl> pars, int mods) {
    super(ans, Optional.ofNullable(r), n, pars, mods);
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode()).append(" { ");
    if(getReturnType().isPresent()) {
      if(getReturnType().get() == int.class
          || getReturnType().get() == char.class
          || getReturnType().get() == long.class) {
        sb.append("return 0;");
      }
      else if(getReturnType().get() == byte.class
          || getReturnType().get() == short.class) {
        sb.append("return (byte)0;");
      }
      else if(getReturnType().get() == double.class
          || getReturnType().get() == float.class) {
        sb.append("return 0.0f;");
      }
      else if(getReturnType().get() == boolean.class) {
        sb.append("return false;");
      }
      else {
        sb.append("return null;");
      }
    }
    return sb.append(" } ").toString();
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
  
  public static DummyMethodImpl of(Method m) {
    Class r = Void.class.isAssignableFrom(m.getReturnType()) || void.class == m.getReturnType() ? null : m.getReturnType();
    List<ParameterImpl> pars = Stream.of(m.getParameters())
        .map(ParameterImpl::of)
        .collect(Collectors.toList());
    return new DummyMethodImpl(Collections.EMPTY_LIST, r, m.getName(), pars, m.getModifiers());
  }
  
}