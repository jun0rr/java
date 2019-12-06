/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import java.util.Optional;
import java.util.function.Consumer;


/**
 *
 * @author juno
 */
public interface InboundResult {
  
  public Optional<Object> result();
  
  public boolean isFinalOp();
  
  public void ifResultContinue(Consumer<Object> cs);
  
  public void ifContinue(Consumer<Optional<Object>> cs);
  
  public void ifFinalOperation(Consumer<Optional<Object>> cs);
  
  
  
  public static InboundResult continueResult(Object msg) {
    return new InboundResultImpl(msg, false);
  }
  
  public static InboundResult continueResult() {
    return new InboundResultImpl(null, false);
  }
  
  public static InboundResult finalResult(Object msg) {
    return new InboundResultImpl(msg, true);
  }
  
  public static InboundResult finalResult() {
    return new InboundResultImpl(null, true);
  }
  
  
  
  
  
  static class InboundResultImpl implements InboundResult {
    
    private final Optional<Object> result;
    private final boolean finalop;
    
    public InboundResultImpl(Object result, boolean finalop) {
      this.result = Optional.ofNullable(result);
      this.finalop = finalop;
    }

    @Override
    public Optional<Object> result() {
      return result;
    }
    
    @Override
    public boolean isFinalOp() {
      return finalop;
    }
    
    @Override
    public void ifResultContinue(Consumer<Object> cs) {
      if(!finalop && result.isPresent()) {
        cs.accept(result.get());
      }
    }
    
    @Override
    public void ifContinue(Consumer<Optional<Object>> cs) {
      if(!finalop) {
        cs.accept(result);
      }
    }
    
    @Override
    public void ifFinalOperation(Consumer<Optional<Object>> cs) {
      if(finalop) {
        cs.accept(result);
      }
    }
    
  }
  
}
