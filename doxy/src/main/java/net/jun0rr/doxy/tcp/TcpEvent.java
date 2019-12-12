/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;


/**
 *
 * @author juno
 */
@FunctionalInterface
public interface TcpEvent<T> {
  
  public ChannelFuture process(T obj);
  
  
  
  public static interface ConnectEvent extends TcpEvent<Bootstrap> {}
  
  public static interface FutureEvent extends TcpEvent<ChannelFuture> {}
  
}
