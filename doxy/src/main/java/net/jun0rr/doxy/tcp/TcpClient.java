/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.util.function.Consumer;
import net.jun0rr.doxy.cfg.Host;


/**
 *
 * @author juno
 */
public class TcpClient extends AbstractTcpChannel implements WritableTcpChannel {
  
  public TcpClient(Bootstrap bootstrap, ChannelHandlerSetup<TcpChannel,TcpHandler> factory) {
    super(bootstrap, factory);
  }
  
  public static TcpClient open(ChannelHandlerSetup<TcpChannel,TcpHandler> factory) {
    return open(bootstrap(new NioEventLoopGroup(1)), factory);
  }
  
  public static TcpClient open(EventLoopGroup group, ChannelHandlerSetup<TcpChannel,TcpHandler> factory) {
    return open(bootstrap(group), factory);
  }
  
  public static TcpClient open(Bootstrap boot, ChannelHandlerSetup<TcpChannel,TcpHandler> factory) {
    return new TcpClient(boot, factory);
  }
  
  public TcpClient connect(Host host) {
    channelNotCreated();
    TcpEvent.ConnectEvent evt = b -> {
      //System.out.println("--- [CLIENT] CONNECT ---");
      return ((Bootstrap)setupBootstrap(TcpClient.this)).connect(host.toSocketAddr());
    };
    addListener(evt);
    return this;
  }
  
  @Override
  public TcpClient write(Object msg) {
    if(msg != null) {
      TcpEvent.ChannelFutureEvent evt = f -> {
        //System.out.println("--- [CLIENT] SEND ---");
        return f.channel().writeAndFlush(msg);
      };
      addListener(evt);
    }
    return this;
  }
  
  @Override
  public TcpClient onComplete(Consumer<Channel> success) {
    super.onComplete(success);
    return this;
  }
  
  @Override
  public TcpClient onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    super.onComplete(success, error);
    return this;
  }
  
  @Override
  public TcpClient onShutdown(Consumer<EventLoopGroup> success) {
    super.onShutdown(success);
    return this;
  }
  
  @Override
  public TcpClient onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    super.onShutdown(success, error);
    return this;
  }
  
  @Override
  public TcpClient closeChannel() {
    super.close();
    return this;
  }
  
  @Override
  public TcpClient start() {
    super.start();
    return this;
  }
  
  @Override
  public TcpClient sync() {
    super.sync();
    return this;
  }
  
}
