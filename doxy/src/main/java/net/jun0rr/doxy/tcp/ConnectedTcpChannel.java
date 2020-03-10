/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class ConnectedTcpChannel implements WritableTcpChannel {
  
  protected final ChannelPromise promise;
  protected final EventLoopGroup group;
  protected final AtomicReference<Future> future;
  
  public ConnectedTcpChannel(ChannelFuture future, ChannelPromise prms) {
    this.group = future.channel().eventLoop().parent();
    this.future = new AtomicReference(future);
    this.promise = prms;
  }
  
  public ConnectedTcpChannel withPromise(ChannelPromise prms) {
    return new ConnectedTcpChannel((ChannelFuture)future.get(), prms);
  }
  
  /**
   * Add listeners to event completion.
   * @param success On success complete.
   * @return This TcpChannel.
   */
  @Override
  public ConnectedTcpChannel onComplete(Consumer<Channel> success) {
    return onComplete(success, Unchecked::unchecked);
  }
  
  /**
   * Add listeners to event completion.
   * @param success On success complete.
   * @param error On failed complete.
   * @return This TcpChannel.
   */
  @Override
  public ConnectedTcpChannel onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    if(success != null && error != null) {
      future.get().addListener(f->{
        if(f.isSuccess()) success.accept(((ChannelFuture)f).channel());
        else error.accept(f.cause());
      });
    }
    return this;
  }
  
  /**
   * Add listeners to shutdown event completion.
   * @param success On success shutdown.
   * @return This TcpChannel.
   */
  @Override
  public ConnectedTcpChannel onShutdown(Consumer<EventLoopGroup> success) {
    return onShutdown(success, Unchecked::unchecked);
  }
  
  /**
   * Adds listeners to shutdown event completion.
   * @param success On success shutdown.
   * @param error On failed shutdown.
   * @return This TcpChannel.
   */
  @Override
  public ConnectedTcpChannel onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    if(success != null && error != null) {
      future.get().addListener(f->{
        if(f.isSuccess()) success.accept(group);
        else error.accept(f.cause());
      });
    }
    return this;
  }
  
  /**
   * Block the current thread until finish all pending events.
   * @return This TcpChannel.
   */
  @Override
  public ConnectedTcpChannel sync() {
    future.get().syncUninterruptibly();
    return this;
  }
  
  /**
   * Unsupported operation
   * @throws UnsupportedOperationException
   */
  @Override
  public ConnectedTcpChannel start() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public ConnectedTcpChannel closeChannel() {
    close();
    return this;
  }
  
  /**
   * Close the channel (do not shutdown the EventLoopGroup).
   * @see net.jun0rr.doxy.tcp.TcpChannel#shutdown() 
   */
  @Override
  public void close() {
    future.get().addListener(f->future.set(((ChannelFuture)f).channel().close()));
  }
  
  @Override
  public ConnectedTcpChannel write(Object obj) {
    future.get().addListener(f->{
      ChannelFuture ff = (ChannelFuture) f;
      future.set(promise != null 
          ? ff.channel().writeAndFlush(obj, promise) 
          : ff.channel().writeAndFlush(obj)
      );
    });
    return this;
  }
  
  /**
   * Close the channel and shutdown the EventLoopGroup.
   * @return This TcpChannel.
   * @see net.jun0rr.doxy.tcp.TcpClient#close() 
   */
  @Override
  public ConnectedTcpChannel shutdown() {
    ((ChannelFuture)future.get()).channel().closeFuture().addListener(f->
        future.set(group.shutdownGracefully())
    );
    close();
    return this;
  }
  
  @Override
  public ConnectedTcpChannel awaitShutdown() {
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
    Future f = future.get();
    if(f != null && f instanceof ChannelFuture) {
      ChannelFuture cf = (ChannelFuture) f;
      return Optional.of(cf.channel());
    }
    return Optional.empty();
  }
  
}
