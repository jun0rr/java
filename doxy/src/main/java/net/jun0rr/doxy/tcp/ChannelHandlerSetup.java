/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


/**
 *
 * @author juno
 */
public interface ChannelHandlerSetup<I,H> {
  
  public ChannelHandlerSetup<I,H> addMessageHandler(Supplier<H> sup);
  
  public ChannelHandlerSetup<I,H> addConnectHandler(Supplier<H> sup);
  
  public ChannelHandlerSetup<I,H> enableSSL(SSLHandlerFactory shf);
  
  public Optional<SSLHandlerFactory> disableSSL();
  
  public Optional<SSLHandlerFactory> sslHandlerFactory();
  
  public List<Supplier<H>> messageHandlers();
  
  public List<Supplier<H>> connectHandlers();
  
  public ChannelInitializer<SocketChannel> create(I input);
  
}
