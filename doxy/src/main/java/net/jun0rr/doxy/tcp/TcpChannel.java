/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import java.util.Optional;
import java.util.function.Consumer;


/**
 *
 * @author Juno
 */
public interface TcpChannel extends AutoCloseable {
  
  public TcpChannel onComplete(Consumer<Channel> success);
  
  public TcpChannel onComplete(Consumer<Channel> success, Consumer<Throwable> error);
  
  public TcpChannel onShutdown(Consumer<EventLoopGroup> success);
  
  public TcpChannel onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error);
  
  public TcpChannel start();
  
  public TcpChannel sync();
  
  public TcpChannel closeChannel();
  
  public TcpChannel shutdown();
  
  public TcpChannel awaitShutdown();
  
  public EventLoopGroup group();
  
  public Optional<Channel> nettyChannel();
  
}
