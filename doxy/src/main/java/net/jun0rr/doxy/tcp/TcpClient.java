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
public class TcpClient implements Closeable {
  
  private final EventLoopGroup group;
  private ChannelFuture future;
  private final InternalLogger log;
  private final Bootstrap boot;
  private final List<Supplier<TcpHandler>> handlers;
  private final BlockingDeque<Pair<Consumer<Channel>,Consumer<Throwable>>> completers;
  private final AtomicInteger events;
  
  public TcpClient(Bootstrap bootstrap) {
    System.out.println("* TcpClient<init>: " + bootstrap);
    this.boot = Objects.requireNonNull(bootstrap, "Bad null Bootstrap");
    System.out.println("* TcpClient<init>: boot OK!");
    this.group = boot.config().group();
    System.out.println("* TcpClient<init>: group OK!");
    this.log = InternalLoggerFactory.getInstance(getClass());
    System.out.println("* TcpClient<init>: log OK!");
    this.handlers = new LinkedList<>();
    System.out.println("* TcpClient<init>: handlers OK!");
    this.completers = new LinkedBlockingDeque<>();
    System.out.println("* TcpClient<init>: completers OK!");
    this.events = new AtomicInteger(0);
    System.out.println("* TcpClient<init>: events OK!");
    System.out.println("* TcpClient<init>: Done!");
  }
  
  public TcpClient(EventLoopGroup group) {
    this(bootstrap(group));
  }
  
  public TcpClient() {
    this(bootstrap());
  }
  
  public static TcpClient open() {
    return new TcpClient();
  }
  
  public static TcpClient create(EventLoopGroup group) {
    return new TcpClient(group);
  }
  
  public static TcpClient create(Bootstrap boot) {
    return new TcpClient(boot);
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
  
  public TcpClient addHandler(Supplier<TcpHandler> handler) {
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
  
  public TcpClient connect(Host host) {
    System.out.printf("TcpClient.connect( %s )%n", host);
    channelNotConnected();
    events.incrementAndGet();
    future = initHandlers(boot).connect(host.toSocketAddr()).addListener(listener());
    log.info("TcpClient connected at: {}", host);
    return this;
  }
  
  public TcpClient send(Object msg) {
    if(msg != null) {
      onComplete(c -> {
        events.incrementAndGet();
        future = c.writeAndFlush(msg).addListener(listener());
      });
    }
    return this;
  }
  
  public TcpClient onComplete(Consumer<Channel> success) {
    return onComplete(success, t->Unchecked.unchecked(t));
  }
  
  public TcpClient onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    if(success != null && error != null) {
      completers.offerLast(new Pair<>(success, error));
    }
    return this;
  }
  
  private ChannelFutureListener listener() {
    return new ChannelFutureListener() { 
      @Override 
      public void operationComplete(ChannelFuture f) throws Exception {
        while(completers.size() >= events.get()) {
          Pair<Consumer<Channel>,Consumer<Throwable>> p = completers.pollFirst();
          if(p != null) {
            if(f.isSuccess()) p.a.accept(f.channel());
            else p.b.accept(f.cause());
          }
        }
        events.decrementAndGet();
      }
    };
  }
  
  public Channel sync() {
    channelConnected();
    CountDownLatch count = new CountDownLatch(1);
    onComplete(c->count.countDown());
    Unchecked.call(()->count.await());
    return future.channel();
  }
  
  @Override
  public void close() {
    closeOnComplete();
  }
  
  public TcpClient closeOnComplete() {
    return onComplete(c->{
      future = c.close().addListener(listener());
    });
  }
  
  public TcpClient shutdownOnComplete() {
    onComplete(c -> { 
      future = c.close().addListener(listener());
      group.shutdownGracefully();
    });
    return this;
  }
  
}
