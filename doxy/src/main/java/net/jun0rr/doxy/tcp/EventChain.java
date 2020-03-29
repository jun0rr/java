/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import java.util.function.Consumer;


/**
 *
 * @author Juno
 */
public interface EventChain {
  
  public EventChain onComplete(Consumer<Channel> success);
  
  public EventChain onComplete(Consumer<Channel> success, Consumer<Throwable> error);
  
  public EventChain onShutdown(Consumer<EventLoopGroup> success);
  
  public EventChain onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error);
  
  public EventChain execute();
  
  public EventChain executeSync();
  
  public EventChain close();
  
  public EventChain shutdown();
  
  public EventChain awaitShutdown();
  
  public EventChain write(Object obj);
  
  public TcpChannel channel();
  
}
