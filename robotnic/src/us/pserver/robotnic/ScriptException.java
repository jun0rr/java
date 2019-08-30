/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;


/**
 *
 * @author juno
 */
public class ScriptException extends RuntimeException {

  public ScriptException() {
    super();
  }
  
  public ScriptException(String message) {
    super(message);
  }
  
  public ScriptException(String message, Object... args) {
    super(String.format(message, args));
  }
  
  public ScriptException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public ScriptException(Throwable cause) {
    super(cause.toString(), cause);
  }
  
  public ScriptException(Throwable cause, String message, Object... args) {
    super(String.format(message, args), cause);
  }
  
  
  
  public static <S> S call(ThrowableSupplier<S> s) {
    try {
      return s.get();
    }
    catch(Throwable th) {
      throw new ScriptException(th);
    }
  }
  
  
  public static void run(ThrowableRun r) {
    try {
      r.run();
    }
    catch(Throwable th) {
      throw new ScriptException(th);
    }
  }
  
  
  
  
  
  @FunctionalInterface
  public static interface ThrowableSupplier<S> {
    public S get() throws Throwable;
  }
  
  @FunctionalInterface
  public static interface ThrowableRun {
    public void run() throws Throwable;
  }
  
}
