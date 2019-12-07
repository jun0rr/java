/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.Closeable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;


/**
 *
 * @author juno
 */
public class TcpClient implements Closeable {
  
  private final EventLoopGroup group;
  private ChannelFuture channel;
  private final InternalLogger log;
  private final Bootstrap boot;
  private final List<TcpHandler> handlers;
  
  public TcpClient(Bootstrap bootstrap) {
    this.boot = bootstrap != null ? bootstrap : bootstrap();
    this.group = boot.config().group();
    this.log = InternalLoggerFactory.getInstance(getClass());
    this.handlers = new LinkedList<>();
  }
  
  public TcpClient() {
    this(null);
  }
  
  public List<TcpHandler> handlers() {
    return handlers;
  }
  
  public TcpClient addHandler(TcpHandler handler) {
    if(handler != null) handlers.add(handler);
    return this;
  }
  
  private Bootstrap initHandlers(Bootstrap sbt) {
    List<ChannelHandler> ls = new LinkedList<>();
    ls.add(new TcpOutboundHandler());
    handlers.stream()
        .map(h->new TcpChannelHandler(this, h))
        .forEach(ls::add);
    ls.add(new TcpUcaughtExceptionHandler());
    return sbt.handler(new AddingLastChannelInitializer(ls));
  }
  
  private static Bootstrap bootstrap() {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(new NioEventLoopGroup(1));
  }
  
  public ChannelFuture connect(Host host) {
    channel = initHandlers(boot).connect(host.toSocketAddr());
    log.info("TcpClient connected at: {}", host);
    return channel;
  }
  
  public void connect(Host host, Consumer<Channel> connected) {
    connect(host).addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture f) throws Exception {
        connected.accept(f.channel());
      }
    });
  }
  
  @Override
  public void close() {
    channel.channel().close();
    group.shutdownGracefully();
  }
  
}
