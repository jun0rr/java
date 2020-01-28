/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;


/**
 *
 * @author juno
 */
@FunctionalInterface
public interface TcpEvent<T> {
  
  public Future process(T obj);
  
  
  
  public static interface ConnectEvent extends TcpEvent<AbstractBootstrap> {}
  
  public static interface ChannelFutureEvent extends TcpEvent<ChannelFuture> {}
  
  public static interface FutureEvent extends TcpEvent<Future> {}
  
}
