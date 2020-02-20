/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class AsyncTcpChannel implements TcpChannel {
  
  private final List<Supplier<TcpHandler>> handlers;
  
  private volatile Future future;
  
  private final EventLoopGroup group;
  
  private final Bootstrap boot;
  
  private Host target;
  
  public AsyncTcpChannel(Bootstrap boot) {
    this.boot = Objects.requireNonNull(boot, "Bad null Bootstrap");
    this.future = null;
    this.group = boot.config().group();
    this.handlers = new LinkedList<>();
    this.target = null;
  }
  
  public static AsyncTcpChannel open() {
    return open(bootstrap(new NioEventLoopGroup(1)));
  }
  
  public static AsyncTcpChannel open(EventLoopGroup group) {
    return open(bootstrap(group));
  }
  
  public static AsyncTcpChannel open(Bootstrap boot) {
    return new AsyncTcpChannel(boot);
  }
  
  private static Bootstrap bootstrap(EventLoopGroup group) {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(group);
  }
  
  /**
   * Throws an IllegalStateException if channel is NOT created.
   */
  protected void channelCreated() {
    if(future == null) throw new IllegalStateException("Channel not connected");
  }
  
  /**
   * Throws an IllegalStateException if channel is created.
   */
  protected void channelNotCreated() {
    if(future != null) throw new IllegalStateException("Channel already connected");
  }
  
  private Bootstrap initHandlers(Bootstrap sbt) {
    List<Supplier<ChannelHandler>> ls = new LinkedList<>();
    ls.add(TcpOutboundHandler::new);
    Function<Supplier<TcpHandler>,Supplier<ChannelHandler>> fn = s->()->new TcpInboundHandler(this, s.get());
    handlers.stream().map(fn).forEach(ls::add);
    ls.add(TcpUcaughtExceptionHandler::new);
    return sbt.handler(new AddingLastChannelInitializer(ls));
  }
  
  @Override
  public AsyncTcpChannel addMessageHandler(Supplier<TcpHandler> h) {
    channelNotCreated();
    if(h != null) {
      handlers.add(h);
    }
    return this;
  }
  
  @Override
  public List<Supplier<TcpHandler>> messageHandlers() {
    return future == null ? handlers : Collections.unmodifiableList(handlers);
  }
  
  @Override
  public AsyncTcpChannel onComplete(Consumer<Channel> success) {
    return onComplete(success, Unchecked::unchecked);
  }
  
  @Override
  public AsyncTcpChannel onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    channelCreated();
    future.addListener(f->{
      if(f.isSuccess() && f instanceof ChannelFuture) {
        success.accept(((ChannelFuture)f).channel());
      }
      else if(f.cause() != null) {
        error.accept(f.cause());
      }
    });
    return this;
  }
  
  @Override
  public AsyncTcpChannel onShutdown(Consumer<EventLoopGroup> success) {
    return onShutdown(success, Unchecked::unchecked);
  }
  
  @Override
  public AsyncTcpChannel onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    channelCreated();
    future.addListener(f->{
      if(f.isSuccess()) {
        success.accept(group);
      }
      else if(f.cause() != null) {
        error.accept(f.cause());
      }
    });
    return this;
  }
  
  public AsyncTcpChannel connect(Host host) {
    if(host != null) {
      this.target = host;
    }
    return this;
  }
  
  public AsyncTcpChannel send(Object msg) {
    channelCreated();
    Objects.requireNonNull(msg, "Bad null message Object");
    future.addListener(f->{
      if(future instanceof ChannelFuture) {
        future = ((ChannelFuture)future).channel().writeAndFlush(msg);
      }
    });
    return this;
  }
  
  @Override
  public AsyncTcpChannel start() {
    Objects.requireNonNull(target, "Bad null target Host");
    future = initHandlers(boot).connect(target.toSocketAddr());
    return this;
  }
  
  @Override
  public AsyncTcpChannel sync() {
    channelCreated();
    CountDownLatch cd = new CountDownLatch(1);
    future.addListener(f->cd.countDown());
    Unchecked.call(()->cd.await());
    return this;
  }
  
  @Override
  public AsyncTcpChannel closeChannel() {
    channelCreated();
    future.addListener(f->{
      if(future instanceof ChannelFuture) {
        future = ((ChannelFuture)future).channel().close();
      }
    });
    return this;
  }
  
  @Override
  public AsyncTcpChannel shutdown() {
    channelCreated();
    if(future instanceof ChannelFuture) {
      future = ((ChannelFuture)future).channel().close().addListener(f->{
        future = group.shutdownGracefully();
      });
    }
    return this;
  }
  
  @Override
  public EventLoopGroup group() {
    return group;
  }
  
  @Override
  public Optional<Channel> channel() {
    channelCreated();
    if(future instanceof ChannelFuture) {
      return Optional.of(((ChannelFuture)future).channel());
    }
    return Optional.empty();
  }
  
  @Override
  public void close() throws IOException {
    closeChannel();
  }
  
}
