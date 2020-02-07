/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import net.jun0rr.doxy.tcp.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;


/**
 *
 * @author juno
 */
public class HttpServer1 extends AbstractTcpChannel {
  
  private final EventLoopGroup childGroup;
  private final HttpServerConfig config;
  private final HttpHandlers handlers;
  
  public HttpServer1(HttpServerConfig cfg, ServerBootstrap bootstrap) {
    super(bootstrap);
    this.childGroup = bootstrap.config().childGroup();
    this.config = Objects.requireNonNull(cfg, "Bad null HttpServerConfig");
    this.handlers = new HttpHandlers(cfg);
  }
  
  public static HttpServer1 open(HttpServerConfig cfg) {
    return new HttpServer1(cfg, bootstrap(new NioEventLoopGroup(2), new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2)));
  }
  
  public static HttpServer1 open(HttpServerConfig cfg, EventLoopGroup parent, EventLoopGroup child) {
    return open(cfg, bootstrap(parent, child));
  }
  
  public static HttpServer1 open(HttpServerConfig cfg, ServerBootstrap boot) {
    return new HttpServer1(cfg, boot);
  }
  
  public HttpHandlers httpHandlers() {
    return handlers;
  }
  
  /**
   * Unsupported operation.
   * @throws UnsupportedOperationException
   */
  @Override
  public HttpServer1 addMessageHandler(Supplier<TcpHandler> handler) {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Unsupported operation.
   * @throws UnsupportedOperationException
   */
  @Override
  public List<Supplier<TcpHandler>> messageHandlers() {
    return messageHandlers;
  }
  
  private static ServerBootstrap bootstrap(EventLoopGroup parent, EventLoopGroup child) {
    return new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(parent, child);
  }
  
  public HttpServer1 bind(Host host) {
    TcpEvent.ConnectEvent evt = b -> {
      //System.out.println("--- [SERVER] BIND ---");
      ServerBootstrap sb = (ServerBootstrap) b;
      sb.childHandler(handlers.createInitializer());
      return sb.bind(host.toSocketAddr());
    };
    addListener(evt);
    return this;
  }
  
  /**
   * Close the channel and shutdown all EventLoopGroup's.
   * @return This TcpClient.
   * @see net.jun0rr.doxy.tcp.TcpClient#close() 
   */
  @Override
  public HttpServer1 shutdown() {
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
  public HttpServer1 onComplete(Consumer<Channel> success) {
    super.onComplete(success);
    return this;
  }
  
  @Override
  public HttpServer1 onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    super.onComplete(success, error);
    return this;
  }
  
  @Override
  public HttpServer1 onShutdown(Consumer<EventLoopGroup> success) {
    super.onShutdown(success);
    return this;
  }
  
  @Override
  public HttpServer1 onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    super.onShutdown(success, error);
    return this;
  }
  
  @Override
  public HttpServer1 start() {
    super.start();
    return this;
  }
  
  @Override
  public HttpServer1 sync() {
    super.sync();
    return this;
  }
  
}
