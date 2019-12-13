/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.Bootstrap;
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
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class TcpClient2 implements Closeable {
  
  private final EventLoopGroup group;
  private ChannelFuture future;
  private final InternalLogger log;
  private final Bootstrap boot;
  private final List<Supplier<TcpHandler>> handlers;
  
  public TcpClient2(Bootstrap bootstrap) {
    this.boot = Objects.requireNonNull(bootstrap, "Bad null Bootstrap");
    this.group = boot.config().group();
    this.log = InternalLoggerFactory.getInstance(getClass());
    this.handlers = new LinkedList<>();
  }
  
  public TcpClient2(EventLoopGroup group) {
    this(bootstrap(group));
  }
  
  public TcpClient2() {
    this(bootstrap());
  }
  
  public static TcpClient2 open() {
    return new TcpClient2();
  }
  
  public static TcpClient2 open(EventLoopGroup group) {
    return new TcpClient2(group);
  }
  
  public static TcpClient2 open(Bootstrap boot) {
    return new TcpClient2(boot);
  }
  
  private static Bootstrap bootstrap() {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        //.option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(new NioEventLoopGroup(1));
  }
  
  private static Bootstrap bootstrap(EventLoopGroup group) {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        //.option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(group);
  }
  
  public List<Supplier<TcpHandler>> handlers() {
    return handlers;
  }
  
  public TcpClient2 addHandler(Supplier<TcpHandler> handler) {
    if(handler != null) handlers.add(handler);
    return this;
  }
  
  private Bootstrap initHandlers(Bootstrap sbt) {
    List<Supplier<ChannelHandler>> ls = new LinkedList<>();
    ls.add(TcpOutboundHandler::new);
    Function<Supplier<TcpHandler>,Supplier<ChannelHandler>> fn = s->()->new TcpAcceptHandler(this, s.get());
    handlers.stream().map(fn).forEach(ls::add);
    ls.add(TcpUcaughtExceptionHandler::new);
    return sbt.handler(new AddingLastChannelInitializer(ls));
  }
  
  private void channelConnected() {
    System.out.printf("TcpClient.channelConnected( %s )%n", future);
    if(future == null) throw new IllegalStateException("Channel not connected");
  }
  
  private void channelNotConnected() {
    System.out.printf("TcpClient.channelNotConnected( %s )%n", future);
    if(future != null) throw new IllegalStateException("Channel already connected");
  }
  
  public TcpClient2 connect(Host host) {
    channelNotConnected();
    future = initHandlers(boot).connect(host.toSocketAddr());
    log.info("TcpClient connected at: {}", host);
    return this;
  }
  
  public TcpClient2 send(Object msg) {
    if(msg != null) {
      channelConnected();
      future.channel().writeAndFlush(msg).addListener(new ChannelFutureListener() { 
        @Override 
        public void operationComplete(ChannelFuture f) throws Exception {
          System.out.println("--- SEND FUTURE COMPLETED! ---");
        }
      });
    }
    return this;
  }
  
  @Override
  public void close() {
    channelConnected();
    //future.syncUninterruptibly();
    future.channel().close();
    group.shutdownGracefully();
    //Unchecked.call(()->group.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS));
  }
  
}
