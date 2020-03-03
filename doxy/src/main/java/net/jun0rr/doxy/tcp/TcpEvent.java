/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import java.util.EventListener;
import java.util.function.Function;
import java.util.function.UnaryOperator;


/**
 *
 * @author juno
 */
public interface TcpEvent<T> extends Function<T,Future>, EventListener {
  
  @Override public Future apply(T obj);
  
  public static interface ConnectEvent extends TcpEvent<AbstractBootstrap> {}
  
  public static interface ChannelFutureEvent extends TcpEvent<ChannelFuture> {}
  
  public static interface FutureEvent extends TcpEvent<Future> {}
  
  
  
  public static ChannelFutureEvent channel(Function<ChannelFuture,Future> fn) {
    return fn::apply;
  }
  
  public static FutureEvent future(UnaryOperator<Future> fn) {
    return fn::apply;
  }
  
}
