/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import java.util.Optional;


/**
 *
 * @author Juno
 */
public interface TcpChannel extends AutoCloseable {
  
  public EventChain events();
  
  public EventLoopGroup group();
  
  public Optional<Channel> nettyChannel();
  
}
