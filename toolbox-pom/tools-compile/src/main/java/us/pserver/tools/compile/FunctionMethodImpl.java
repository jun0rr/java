/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Objects;
import java.util.function.Function;


/**
 *
 * @author juno
 */
public class FunctionMethodImpl extends MethodImpl {
  
  public static final String B2F_CODE = "java.util.function.Function<String,java.util.function.Function> b2f = b64 -> { java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(java.util.Base64.getDecoder().decode(b64)); try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bis)) { return (java.util.function.Function) ois.readObject(); } catch(Exception e) { throw new RuntimeException(e.toString(), e); }};";
  
  private final Function fn;
  
  public FunctionMethodImpl(Function fn, Scope s, Class r, String n) {
    super(s, r, n);
    this.fn = Objects.requireNonNull(fn);
  }
  
  public FunctionMethodImpl(Function fn, Method m) {
    super(m);
    this.fn = Objects.requireNonNull(fn);
  }
  
  public FunctionMethodImpl(Function fn, Scope s, String n) {
    super(s, n);
    this.fn = Objects.requireNonNull(fn);
  }
  
  private String f2b(Function fn) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
      oos.writeObject(fn);
    }
    catch(IOException e) {
      throw new RuntimeException(e.toString(), e);
    }
    return Base64.getEncoder().encodeToString(bos.toByteArray());
  }
  
  private Function b2f(String b64) {
    ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(b64));
    try (ObjectInputStream ois = new ObjectInputStream(bis)) {
      return (Function) ois.readObject();
    }
    catch(Exception e) {
      throw new RuntimeException(e.toString(), e);
    }
  }
  
  @Override
  public String getSourceCode() {
    StringBuilder sb = new StringBuilder(super.getSourceCode());
    ParameterImpl p = parameters().get(0);
    return sb.append(" { ")
        .append(B2F_CODE)
        .append(" ")
        .append("java.util.function.Function fn = b2f.apply(\"")
        .append(f2b(fn))
        .append("\"); ")
        .append("return (")
        .append(getReturnType().get().getName())
        .append(") fn.apply(")
        .append(p.getName())
        .append(");}")
        .toString();
  }
  
  @Override
  public String toString() {
    return getSourceCode();
  }
  
}
