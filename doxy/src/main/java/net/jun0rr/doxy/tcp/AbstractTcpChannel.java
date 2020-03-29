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
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Objects;
import net.jun0rr.doxy.cfg.Host;


/**
 *
 * @author juno
 */
public class AbstractTcpChannel implements TcpChannel {
  
  protected final EventLoopGroup group;
  
  protected volatile Future future;
  
  protected SocketAddress local;
  
  protected SocketAddress remote;
  
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
  
  @Override
  public Channel nettyChannel() {
    return  (future != null && future instanceof ChannelFuture)
        ? ((ChannelFuture)future).channel() : null;
  }
  
  @Override
  public EventChain events() {
    channelCreated();
    return new TcpEventChain(this, future);
  }
  
  @Override
  public EventChain closeFuture() {
    channelCreated();
    Future f = (future instanceof ChannelFuture) 
        ? ((ChannelFuture)future).channel().closeFuture() : future;
    return new TcpEventChain(this, f);
  }
  
  @Override
  public void close() throws Exception {
    events().close().execute();
  }


  @Override
  public Host localHost() {
    if(future != null && future instanceof ChannelFuture)  {
      InetSocketAddress addr = (InetSocketAddress) ((ChannelFuture)future).channel().localAddress();
      return Host.of(addr.getHostString(), addr.getPort());
    }
    else {
      return null;
    }
  }


  @Override
  public Host remoteHost() {
    if(future != null && future instanceof ChannelFuture)  {
      InetSocketAddress addr = (InetSocketAddress) ((ChannelFuture)future).channel().remoteAddress();
      return Host.of(addr.getHostString(), addr.getPort());
    }
    else {
      return null;
    }
  }
  
}
