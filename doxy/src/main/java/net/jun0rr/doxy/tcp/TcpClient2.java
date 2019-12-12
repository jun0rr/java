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
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;
import us.pserver.tools.Pair;
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
  private final BlockingDeque<TcpEvent> events;
  
  public TcpClient2(Bootstrap bootstrap) {
    System.out.println("* TcpClient<init>: " + bootstrap);
    this.boot = Objects.requireNonNull(bootstrap, "Bad null Bootstrap");
    System.out.println("* TcpClient<init>: boot OK!");
    this.group = boot.config().group();
    System.out.println("* TcpClient<init>: group OK!");
    this.log = InternalLoggerFactory.getInstance(getClass());
    System.out.println("* TcpClient<init>: log OK!");
    this.handlers = new LinkedList<>();
    System.out.println("* TcpClient<init>: handlers OK!");
    this.events = new LinkedBlockingDeque<>();
    System.out.println("* TcpClient<init>: events OK!");
    System.out.println("* TcpClient<init>: Done!");
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
  
  public static TcpClient2 create(EventLoopGroup group) {
    return new TcpClient2(group);
  }
  
  public static TcpClient2 create(Bootstrap boot) {
    return new TcpClient2(boot);
  }
  
  private static Bootstrap bootstrap() {
    try {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(new NioEventLoopGroup(1));
    } finally {
      System.out.println("* TcpClient.bootstrap!");
    }
  }
  
  private static Bootstrap bootstrap(EventLoopGroup group) {
    try {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(group);
    } finally {
      System.out.println("* TcpClient.bootstrap!");
    }
  }
  
  public List<Supplier<TcpHandler>> handlers() {
    return handlers;
  }
  
  public TcpClient2 addHandler(Supplier<TcpHandler> handler) {
    System.out.printf("* TcpClient.addHandler( %s )%n", handler);
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
    System.out.printf("TcpClient2.connect( %s )%n", host);
    TcpEvent.ConnectEvent evt = b -> {
      System.out.println("--- CONNECT ---");
      ChannelFuture f = initHandlers(b).connect(host.toSocketAddr());
      log.info("TcpClient connected at: {}", host);
      return f;
    };
    events.offerLast(evt);
    return this;
  }
  
  public TcpClient2 send(Object msg) {
    if(msg != null) {
      TcpEvent.FutureEvent evt = f -> {
        System.out.println("--- SEND ---");
        return f.channel().write(msg);
      };
      events.offerLast(evt);
    }
    return this;
  }
  
  public TcpClient2 onComplete(Consumer<Channel> success) {
    return onComplete(success, t->Unchecked.unchecked(t));
  }
  
  public TcpClient2 onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    if(success != null && error != null) {
      TcpEvent.FutureEvent evt = f -> {
        if(f.isSuccess()) success.accept(f.channel());
        else error.accept(f.cause());
        return f;
      };
      events.add(evt);
    }
    return this;
  }
  
  private ChannelFutureListener listener() {
    return new ChannelFutureListener() { 
      @Override 
      public void operationComplete(ChannelFuture f) throws Exception {
        TcpEvent<ChannelFuture> evt = events.pollFirst();
        System.out.println("--- LISTENER: " + evt + " ---");
        if(evt != null) {
          future = evt.process(f).addListener(listener());
          System.out.println(future);
        }
      }
    };
  }
  
  public Channel startSync() {
    CountDownLatch count = new CountDownLatch(1);
    TcpEvent.FutureEvent evt = f -> {
      System.out.println("--- SYNC ---");
      count.countDown();
      return f;
    };
    events.offerLast(evt);
    TcpEvent<Bootstrap> con = events.pollFirst();
    System.out.println("--- process connect ---");
    future = con.process(boot).addListener(listener());
    Unchecked.call(()->count.await());
    return future.channel();
  }
  
  @Override
  public void close() {
    closeOnComplete();
  }
  
  public TcpClient2 closeOnComplete() {
    TcpEvent.FutureEvent evt = f->{
      System.out.println("--- CLOSE ---");
      return f.channel().close();
    };
    events.offerLast(evt);
    return this;
  }
  
  public TcpClient2 shutdownOnComplete() {
    TcpEvent.FutureEvent evt = f-> {
      ChannelFuture cf = f.channel().close();
      group.shutdownGracefully();
      return cf;
    };
    events.offerLast(evt);
    onComplete(c -> { 
      future = c.close().addListener(listener());
      group.shutdownGracefully();
    });
    return this;
  }
  
}
