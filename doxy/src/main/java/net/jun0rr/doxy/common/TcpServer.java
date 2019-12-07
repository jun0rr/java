/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import us.pserver.tools.Unchecked;
import net.jun0rr.doxy.cfg.Host;


/**
 *
 * @author juno
 */
public class TcpServer {
  
  private final EventLoopGroup accept;
  private final EventLoopGroup handle;
  private ChannelFuture channel;
  private final Host bind;
  private final InternalLogger log;
  private final ServerBootstrap boot;
  private final List<TcpHandler> handlers;
  
  public TcpServer(ServerBootstrap bootstrap, Host bind) {
    this.boot = bootstrap != null ? bootstrap : bootstrap();
    this.accept = boot.config().group();
    this.handle = boot.config().childGroup();
    this.bind = bind;
    this.log = InternalLoggerFactory.getInstance(getClass());
    this.handlers = new LinkedList<>();
  }
  
  public TcpServer(Host bind) {
    this(null, bind);
  }
  
  public List<TcpHandler> handlers() {
    return handlers;
  }
  
  public TcpServer addHandler(TcpHandler handler) {
    if(handler != null) handlers.add(handler);
    return this;
  }
  
  private ServerBootstrap initHandlers(ServerBootstrap sbt) {
    List<ChannelHandler> ls = new LinkedList<>();
    ls.add(new TcpOutboundHandler());
    handlers.stream()
        .map(h->new TcpChannelHandler(this, h))
        .forEach(ls::add);
    ls.add(new TcpUcaughtExceptionHandler());
    return sbt.childHandler(new AddingLastChannelInitializer(ls));
  }
  
  private static ServerBootstrap bootstrap() {
    return new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(new NioEventLoopGroup(1), new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2));
  }
  
  public TcpServer start() {
    channel = initHandlers(boot).bind(bind.toSocketAddr());
    log.info("TcpServer listening at: {}", bind);
    return this;
  }
  
  public void sync() {
    Unchecked.call(()->accept.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS));
  }
  
  public void stop() {
    channel.channel().close();
    accept.shutdownGracefully();
    handle.shutdownGracefully();
  }
  
}
