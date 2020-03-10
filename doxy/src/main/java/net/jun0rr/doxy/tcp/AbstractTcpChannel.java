/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public abstract class AbstractTcpChannel implements TcpChannel {
  
  protected final AbstractBootstrap boot;
  
  protected final ChannelHandlerSetup setup;
  
  protected final EventLoopGroup group;
  
  protected volatile Future future;
  
  protected final BlockingDeque<TcpEvent> events;
  
  protected final AtomicBoolean listening;
  
  
  public AbstractTcpChannel(AbstractBootstrap bootstrap, ChannelHandlerSetup setup) {
    this.boot = Objects.requireNonNull(bootstrap, "Bad null Bootstrap");
    this.setup = Objects.requireNonNull(setup, "Bad null ChannelInitFactory");
    this.group = boot.config().group();
    this.future = null;
    this.events = new LinkedBlockingDeque<>();
    this.listening = new AtomicBoolean(false);
  }
  
  protected static Bootstrap bootstrap(EventLoopGroup group) {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(group);
  }
  
  protected ServerBootstrap setupServerBootstrap(TcpChannel ch) {
    ServerBootstrap sb = (ServerBootstrap) boot;
    return sb.childHandler(setup.create(ch));
  }
  
  protected Bootstrap setupBootstrap(TcpChannel ch) {
    Bootstrap b = (Bootstrap) boot;
    return b.handler(setup.create(ch));
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
  
  /**
   * Return a GenericFutureListener to process pending events.
   * @return GenericFutureListener to process pending events.
   */
  protected GenericFutureListener listener() {
    return new GenericFutureListener() { 
      @Override 
      public void operationComplete(Future f) throws Exception {
        TcpEvent<Future> evt = events.pollFirst();
        if(evt != null) {
          future = evt.apply(f).addListener(listener());
        }
        else listening.compareAndSet(true, false);
      }
    };
  }
  
  /**
   * Add a TcpEvent to events queue.
   * @param evt TcpEvent.
   */
  protected void addListener(TcpEvent evt) {
    events.offerLast(evt);
    if(future != null && listening.compareAndSet(false, true)) {
      future.addListener(listener());
    }
  }
  
  /**
   * Add listeners to event completion.
   * @param success On success complete.
   * @return This TcpChannel.
   */
  @Override
  public AbstractTcpChannel onComplete(Consumer<Channel> success) {
    return onComplete(success, Unchecked::unchecked);
  }
  
  /**
   * Add listeners to event completion.
   * @param success On success complete.
   * @param error On failed complete.
   * @return This TcpChannel.
   */
  @Override
  public AbstractTcpChannel onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    if(success != null && error != null) {
      TcpEvent.ChannelFutureEvent evt = f -> {
        if(f.isSuccess()) success.accept(f.channel());
        else error.accept(f.cause());
        return f;
      };
      addListener(evt);
    }
    return this;
  }
  
  /**
   * Add listeners to shutdown event completion.
   * @param success On success shutdown.
   * @return This TcpChannel.
   */
  @Override
  public AbstractTcpChannel onShutdown(Consumer<EventLoopGroup> success) {
    return onShutdown(success, Unchecked::unchecked);
  }
  
  /**
   * Adds listeners to shutdown event completion.
   * @param success On success shutdown.
   * @param error On failed shutdown.
   * @return This TcpChannel.
   */
  @Override
  public AbstractTcpChannel onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    if(success != null && error != null) {
      TcpEvent.FutureEvent evt = f -> {
        if(f.isSuccess()) success.accept(group);
        else error.accept(f.cause());
        return f;
      };
      addListener(evt);
    }
    return this;
  }
  
  /**
   * Block the current thread until finish all pending events.
   * @return This TcpChannel.
   */
  @Override
  public AbstractTcpChannel sync() {
    channelCreated();
    CountDownLatch count = new CountDownLatch(1);
    TcpEvent.FutureEvent evt = f -> {
      count.countDown();
      return f;
    };
    addListener(evt);
    Unchecked.call(()->count.await());
    return this;
  }
  
  /**
   * Starts processing all TcpChannel events.
   * @return This TcpChannel.
   */
  @Override
  public AbstractTcpChannel start() {
    channelNotCreated();
    TcpEvent<AbstractBootstrap> con = events.pollFirst();
    future = con.apply(boot).addListener(listener());
    return this;
  }
  
  /**
   * Close the channel (do not shutdown the EventLoopGroup).
   * @see net.jun0rr.doxy.tcp.TcpChannel#shutdown() 
   */
  @Override
  public void close() {
    TcpEvent.FutureEvent e = f->{
      if(f instanceof ChannelFuture) {
        ChannelFuture cf = (ChannelFuture) f;
        if(cf.channel().isOpen()) {
          return cf.channel().close();
        }
      }
      return f;
    };
    addListener(e);
  }
  
  /**
   * Close the channel and shutdown the EventLoopGroup.
   * @return This TcpChannel.
   * @see net.jun0rr.doxy.tcp.TcpClient#close() 
   */
  @Override
  public AbstractTcpChannel shutdown() {
    close();
    TcpEvent.FutureEvent e = f -> {
      return group.shutdownGracefully();
    };
    addListener(e);
    return this;
  }
  
  @Override
  public AbstractTcpChannel awaitShutdown() {
    group.terminationFuture().syncUninterruptibly();
    return this;
  }
  
  /**
   * Return the main group.
   * @return Main EventLoopGroup.
   */
  @Override
  public EventLoopGroup group() {
    return group;
  }
  
  /**
   * Return the Channel if TcpChannel current state has a channel.
   * @return Non-empty optional if channel is already created and is not closed, empty otherwise.
   */
  @Override
  public Optional<Channel> nettyChannel() {
    if(future != null && future instanceof ChannelFuture) {
      ChannelFuture cf = (ChannelFuture) future;
      return Optional.of(cf.channel());
    }
    return Optional.empty();
  }
  
}
