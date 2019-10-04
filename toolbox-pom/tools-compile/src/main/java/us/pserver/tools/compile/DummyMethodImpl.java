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
public class DummyMethodImpl extends MethodImpl {
  
  public DummyMethodImpl(Scope s, Class r, String n) {
    super(s, r, n);
  }


  public DummyMethodImpl(Method m) {
    super(m);
  }


  public DummyMethodImpl(Scope s, String n) {
    super(s, n);
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
  
}
