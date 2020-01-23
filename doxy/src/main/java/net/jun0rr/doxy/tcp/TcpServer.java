/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.Closeable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import us.pserver.tools.Unchecked;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;


/**
 *
 * @author juno
 */
public class TcpServer implements Closeable {
  
  private final EventLoopGroup accept;
  private final EventLoopGroup handle;
  private ChannelFuture channel;
  private final Host bind;
  private final InternalLogger log;
  private final ServerBootstrap boot;
  private final List<Supplier<TcpHandler>> acceptHandlers;
  private final List<Supplier<TcpHandler>> connectHandlers;
  
  public TcpServer(ServerBootstrap bootstrap, Host bind) {
    this.boot = bootstrap != null ? bootstrap : bootstrap();
    this.accept = boot.config().group();
    this.handle = boot.config().childGroup();
    this.bind = bind;
    this.log = InternalLoggerFactory.getInstance(getClass());
    this.acceptHandlers = new LinkedList<>();
    this.connectHandlers = new LinkedList<>();
  }
  
  public TcpServer(Host bind) {
    this(null, bind);
  }
  
  public List<Supplier<TcpHandler>> acceptHandlers() {
    return acceptHandlers;
  }
  
  public List<Supplier<TcpHandler>> connectHandlers() {
    return acceptHandlers;
  }
  
  public TcpServer addAcceptHandler(Supplier<TcpHandler> handler) {
    if(handler != null) acceptHandlers.add(handler);
    return this;
  }
  
  public TcpServer addConnectHandler(Supplier<TcpHandler> handler) {
    if(handler != null) connectHandlers.add(handler);
    return this;
  }
  
  private ServerBootstrap initHandlers(ServerBootstrap sbt) {
    List<Supplier<ChannelHandler>> ls = new LinkedList<>();
    Function<Supplier<TcpHandler>,Supplier<ChannelHandler>> fn = s->()->new TcpConnectHandler(this, s.get());
    ls.add(TcpOutboundHandler::new);
    connectHandlers.stream().map(fn).forEach(ls::add);
    fn = s->()->new TcpInboundHandler(this, s.get());
    acceptHandlers.stream().map(fn).forEach(ls::add);
    ls.add(TcpUcaughtExceptionHandler::new);
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
  
  @Override
  public void close() {
    stop();
  }
  
}
