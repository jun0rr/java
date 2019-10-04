/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.lang.reflect.Method;


/**
 *
 * @author juno
 */
public class FunctionMethodImpl extends MethodImpl {
  
  public FunctionMethodImpl(Class r, String n, int mods) {
    super(r, n, mods);
  }
  
  public FunctionMethodImpl(Method m) {
    super(m);
  }
  
  public FunctionMethodImpl(String n, int mods) {
    super(n, mods);
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode());
    ParameterImpl p = parameters().get(0);
    return sb.append(" { ")
        .append("class Local {}; ")
        .append("java.lang.reflect.Method curMeth = Local.class.getEnclosingMethod(); ")
        .append("java.util.function.Function fn = (java.util.function.Function) LAMBDA_MAP.get(curMeth.getName() + java.util.Arrays.toString(curMeth.getParameterTypes())); ")
        .append("return (")
        .append(getReturnType().get().getName())
        .append(") fn.apply(")
        .append(p.getName())
        .append(");}")
        .toString();
  }
  
  @Override
  public int hashCode() {
    return super.hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
