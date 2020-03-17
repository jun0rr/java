/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import java.util.Optional;


/**
 *
 * @author juno
 */
public class ConnectedTcpChannel extends AbstractTcpChannel {
  
  protected final ChannelPromise promise;
  
  public ConnectedTcpChannel(ChannelFuture cf, ChannelPromise prms) {
    super(cf.channel().eventLoop().parent());
    this.future = cf;
    this.promise = prms;
  }
  
  public ConnectedTcpChannel withPromise(ChannelPromise prms) {
    return new ConnectedTcpChannel((ChannelFuture)future, prms);
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

}
