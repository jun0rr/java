/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import java.util.Objects;
import java.util.Optional;


/**
 *
 * @author juno
 */
public class AbstractTcpChannel implements TcpChannel {
  
  protected final EventLoopGroup group;
  
  protected volatile Future future;
  
  public AbstractTcpChannel(EventLoopGroup group) {
    this.group = Objects.requireNonNull(group, "Bad null EventLoopGroup");
    this.future = null;
  }
  
  /**
   * Throws an IllegalStateException if channel is NOT created.
   */
  protected void channelCreated() {
    if(future == null) throw new IllegalStateException("Channel not connected");
  }
  
  /**
   * Throws an IllegalStateException if channel is created.
   */
  protected void channelNotCreated() {
    if(future != null) throw new IllegalStateException("Channel already connected");
  }
  
  /**
   * Return the main group.
   * @return Main EventLoopGroup.
   */
  @Override
  public EventLoopGroup group() {
    return group;
  }
  
  /**
   * Return the Channel if TcpChannel current state has a channel.
   * @return Non-empty optional if channel is already created and is not closed, empty otherwise.
   */
  @Override
  public Optional<Channel> nettyChannel() {
    if(future != null && future instanceof ChannelFuture) {
      ChannelFuture cf = (ChannelFuture) future;
      return Optional.of(cf.channel());
    }
    return Optional.empty();
  }
  
  @Override
  public EventChain events() {
    channelCreated();
    return new TcpEventChain(this, future);
  }
  
  @Override
  public void close() throws Exception {
    events().close().execute();
  }
  
}
