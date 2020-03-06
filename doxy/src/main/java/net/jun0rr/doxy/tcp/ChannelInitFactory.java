/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 *
 * @author juno
 */
public interface ChannelInitFactory<H> extends Supplier<ChannelInitializer<SocketChannel>> {
  
  public ChannelInitFactory<H> addMessageHandler(Supplier<H> sup);
  
  public ChannelInitFactory<H> addConnectHandler(Supplier<Consumer<WritableTcpChannel>> sup);
  
  public ChannelInitFactory<H> addSSLHandler(Supplier<SslHandler> sup);
  
  public List<Supplier<H>> messageHandlers();
  
  public List<Supplier<Consumer<WritableTcpChannel>>> connectHandlers();
  
}
