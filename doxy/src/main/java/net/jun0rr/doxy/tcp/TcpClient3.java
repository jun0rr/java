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
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.Closeable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class TcpClient3 implements Closeable {
  
  private volatile ChannelFuture future;
  private final EventLoopGroup group;
  private final Bootstrap boot;
  private final List<TcpHandler> handlers;
  
  public TcpClient3(Bootstrap bootstrap) {
    this.boot = Objects.requireNonNull(bootstrap, "Bad null Bootstrap");
    this.group = boot.config().group();
    this.handlers = new LinkedList<>();
  }
  
  public TcpClient3(EventLoopGroup group) {
    this(bootstrap(group));
  }
  
  public TcpClient3() {
    this(bootstrap(new NioEventLoopGroup(1)));
  }
  
  public static TcpClient3 open() {
    return new TcpClient3();
  }
  
  public static TcpClient3 open(EventLoopGroup group) {
    return new TcpClient3(group);
  }
  
  public static TcpClient3 open(Bootstrap boot) {
    return new TcpClient3(boot);
  }
  
  private static Bootstrap bootstrap(EventLoopGroup group) {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(group);
  }
  
  public List<TcpHandler> handlers() {
    return handlers;
  }
  
  public TcpClient3 addHandler(TcpHandler handler) {
    if(handler != null) handlers.add(handler);
    return this;
  }
  
  private Bootstrap initHandlers(Bootstrap sbt) {
    List<Supplier<ChannelHandler>> ls = new LinkedList<>();
    ls.add(TcpOutboundHandler::new);
    Function<TcpHandler,Supplier<ChannelHandler>> fn = h->()->new TcpInboundHandler(this, h);
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
  
  public TcpFuture connect(Host host) {
    System.out.printf("TcpClient.connect( %s )%n", host);
    channelNotConnected();
    ChannelFutureListener sendl = new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture f) throws Exception {
        Object msg;
        while((msg = sending.pollFirst()) != null) {
          System.out.println("<< Sending: " + msg);
          f.channel().writeAndFlush(msg);
        }
      }
    };
    future = initHandlers(boot).connect(host.toSocketAddr());
    future.addListener(sendl);
    log.info("TcpClient connected at: {}", host);
    return this;
  }
  
  public TcpClient3 send(Object msg) {
    if(msg != null) {
      sending.offerLast(msg);
    }
    channelConnected();
    //future = future.channel().writeAndFlush(msg);
    //future.addListener(new ChannelFutureListener() {
      //@Override
      //public void operationComplete(ChannelFuture f) throws Exception {
        //future = f.channel().writeAndFlush(msg);
      //}
    //});
    return this;
  }
  
  @Override
  public void close() {
    close(null);
  }
  
  public void closeSync() {
    CountDownLatch cl = new CountDownLatch(1);
    close(new GenericFutureListener() {
      @Override
      public void operationComplete(Future f) throws Exception {
        cl.countDown();
      }
    });
    Unchecked.call(()->cl.await());
  }
  
  private void close(GenericFutureListener fl) {
    channelConnected();
    ChannelFutureListener shutdown = new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture f) throws Exception {
        System.out.println("SHUTDOWN");
        Future ft = group.shutdownGracefully();
        if(fl != null) ft.addListener(fl);
      }
    };
    future.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture f) throws Exception {
        System.out.println("CLOSE");
        f.channel().close().addListener(shutdown);
      }
    });
  }
  
}
