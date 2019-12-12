/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.util.function.Consumer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public interface CompletableTcp extends ChannelFutureListener {
  
  public CompletableTcp onComplete(Consumer<ChannelFuture> cs);
  
  public CompletableTcp onSuccess(Consumer<Channel> cs);
  
  public CompletableTcp onError(Consumer<Throwable> cs);
  
  
  
  public static CompletableTcp create() {
    return new CompletableTcpImpl();
  }
  
  public static CompletableTcp create(Consumer<ChannelFuture> complete, Consumer<Channel> success, Consumer<Throwable> error) {
    return new CompletableTcpImpl().onComplete(complete).onSuccess(success).onError(error);
  }
  
  public static CompletableTcp create(Consumer<Channel> success, Consumer<Throwable> error) {
    return new CompletableTcpImpl().onSuccess(success).onError(error);
  }
  
  public static CompletableTcp createOnComplete(Consumer<ChannelFuture> complete) {
    return new CompletableTcpImpl().onComplete(complete);
  }
  
  public static CompletableTcp createOnSuccess(Consumer<Channel> success) {
    return new CompletableTcpImpl().onSuccess(success);
  }
  
  public static CompletableTcp createOnError(Consumer<Throwable> error) {
    return new CompletableTcpImpl().onError(error);
  }
  
  
  
  
  
  static class CompletableTcpImpl implements CompletableTcp {
    
    private Consumer<ChannelFuture> complete;
    
    private Consumer<Channel> success;
    
    private Consumer<Throwable> error;
    
    private volatile ChannelFuture future;
    
    public CompletableTcpImpl() {
      complete = null;
      success = null;
      error = Unchecked::unchecked;
      future = null;
    }
    
    @Override
    public CompletableTcp onComplete(Consumer<ChannelFuture> cs) {
      if(cs != null) {
        if(future != null) cs.accept(future);
        else complete = cs;
      }
      return this;
    }
    
    @Override
    public CompletableTcp onSuccess(Consumer<Channel> cs) {
      if(cs != null) {
        if(future != null) cs.accept(future.channel());
        else success = cs;
      }
      return this;
    }
    
    @Override
    public CompletableTcp onError(Consumer<Throwable> cs) {
      if(cs != null) {
        if(future != null && future.cause() != null) cs.accept(future.cause());
        else error = cs;
      }
      return this;
    }
    
    @Override
    public void operationComplete(ChannelFuture f) throws Exception {
      future = f;
      if(complete != null) complete.accept(f);
      if(success != null && f.isSuccess()) {
        success.accept(f.channel());
      }
      else if(error != null && f.cause() != null) {
        error.accept(f.cause());
      }
    }
    
  }
  
}
