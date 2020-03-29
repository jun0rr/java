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
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 *
 * @author juno
 */
public interface ChannelHandlerSetup<H extends ChannelHandler> {
  
  public ChannelHandlerSetup<H> addMessageHandler(Supplier<H> sup);
  
  public ChannelHandlerSetup<H> addConnectHandler(Supplier<Consumer<TcpChannel>> sup);
  
  public ChannelHandlerSetup<H> enableSSL(SSLHandlerFactory shf);
  
  public Optional<SSLHandlerFactory> disableSSL();
  
  public Optional<SSLHandlerFactory> sslHandlerFactory();
  
  public List<Supplier<H>> messageHandlers();
  
  public List<Supplier<Consumer<TcpChannel>>> connectHandlers();
  
  public ChannelInitializer<SocketChannel> create(TcpChannel c);
  
}
