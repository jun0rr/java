/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.function.Consumer;
import net.jun0rr.doxy.cfg.Host;


/**
 *
 * @author juno
 */
public class TcpServer extends AbstractTcpChannel {
  
  private final EventLoopGroup childGroup;
  
  public TcpServer(ServerBootstrap bootstrap, ChannelHandlerSetup<TcpChannel,TcpHandler> factory) {
    super(bootstrap, factory);
    this.childGroup = bootstrap.config().childGroup();
  }
  
  public static TcpServer open(ChannelHandlerSetup<TcpChannel,TcpHandler> factory) {
    return new TcpServer(bootstrap(
        new NioEventLoopGroup(1), 
        new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2)), 
        factory
    );
  }
  
  public static TcpServer open(EventLoopGroup parent, EventLoopGroup child, ChannelHandlerSetup<TcpChannel,TcpHandler> factory) {
    return open(bootstrap(parent, child), factory);
  }
  
  public static TcpServer open(ServerBootstrap boot, ChannelHandlerSetup<TcpChannel,TcpHandler> factory) {
    return new TcpServer(boot, factory);
  }
  
  private static ServerBootstrap bootstrap(EventLoopGroup parent, EventLoopGroup child) {
    return new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(parent, child);
  }
  
  public TcpServer bind(Host host) {
    TcpEvent.ConnectEvent evt = b -> {
      //System.out.println("--- [SERVER] BIND ---");
      return setupServerBootstrap(TcpServer.this).bind(host.toSocketAddr());
    };
    addListener(evt);
    return this;
  }
  
  @Override
  public TcpServer closeChannel() {
    close();
    return this;
  }
  
  /**
   * Close the channel and shutdown all EventLoopGroup's.
   * @return This TcpClient.
   * @see net.jun0rr.doxy.tcp.TcpClient#close() 
   */
  @Override
  public TcpServer shutdown() {
    close();
    TcpEvent.ChannelFutureEvent e = f -> {
      //System.out.println("--- [SERVER] SHUTDOWN ---");
      childGroup.shutdownGracefully();
      return group.shutdownGracefully();
    };
    addListener(e);
    return this;
  }
  
  public EventLoopGroup childGroup() {
    return childGroup;
  }
  
  @Override
  public TcpServer onComplete(Consumer<Channel> success) {
    super.onComplete(success);
    return this;
  }
  
  @Override
  public TcpServer onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    super.onComplete(success, error);
    return this;
  }
  
  @Override
  public TcpServer onShutdown(Consumer<EventLoopGroup> success) {
    super.onShutdown(success);
    return this;
  }
  
  @Override
  public TcpServer onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    super.onShutdown(success, error);
    return this;
  }
  
  @Override
  public TcpServer start() {
    super.start();
    return this;
  }
  
  @Override
  public TcpServer sync() {
    super.sync();
    return this;
  }
  
}
