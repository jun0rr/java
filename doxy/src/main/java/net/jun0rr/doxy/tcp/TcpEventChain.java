/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class TcpEventChain implements EventChain {
  
  protected final TcpChannel channel;
  
  protected Future future;
  
  protected final List<TcpEvent> events;
  
  public TcpEventChain(TcpChannel c, Future f) {
    this.future = Objects.requireNonNull(f, "Bad null Future");
    this.channel = Objects.requireNonNull(c, "Bad null EventLoopGroup");
    this.events = new LinkedList<>();
  }

  @Override
  public EventChain onComplete(Consumer<Channel> success) {
    return onComplete(success, Unchecked::unchecked);
  }


  @Override
  public EventChain onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    if(success != null && error != null) events.add(f->{
      ChannelFuture cf = (ChannelFuture) f;
      if(cf.isDone()) {
        if(cf.isSuccess()) success.accept(cf.channel());
        else error.accept(cf.cause());
      }
      return cf;
    });
    return this;
  }


  @Override
  public EventChain onShutdown(Consumer<EventLoopGroup> success) {
    return onShutdown(success, Unchecked::unchecked);
  }


  @Override
  public EventChain onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    if(success != null && error != null) {
      TcpEvent.FutureEvent evt = f->{
        if(f.isDone()) {
          if(f.isSuccess()) success.accept(channel.group());
          else error.accept(f.cause());
        }
        return f;
      };
      events.add(evt);
    }
    return this;
  }


  @Override
  public EventChain executeSync() {
    CountDownLatch count = new CountDownLatch(1);
    TcpEvent.FutureEvent evt = f -> {
      count.countDown();
      return f;
    };
    events.add(evt);
    execute();
    Unchecked.call(()->count.await());
    return this;
  }


  @Override
  public EventChain close() {
    TcpEvent.FutureEvent evt = f->{
      if(f instanceof ChannelFuture) {
        ChannelFuture cf = (ChannelFuture) f;
        if(cf.channel().isOpen()) {
          return cf.channel().close();
        }
      }
      return f;
    };
    events.add(evt);
    return this;
  }


  @Override
  public EventChain shutdown() {
    close();
    TcpEvent.FutureEvent evt = f -> {
      return channel.group().shutdownGracefully();
    };
    events.add(evt);
    return this;
  }


  @Override
  public EventChain awaitShutdown() {
    channel.group().terminationFuture().syncUninterruptibly();
    return this;
  }
  
  private GenericFutureListener listener(Iterator<TcpEvent> it) {
    return f -> {
      if(it.hasNext()) {
        future = it.next().apply(f);
        execute(it);
        ((AbstractTcpChannel)channel).future = future;
      }
    };
  }
  
  private void execute(Iterator<TcpEvent> it) {
    if(channel.group().isShutdown() || channel.group().isTerminated()) {
      while(it.hasNext()) {
        future = it.next().apply(future);
      }
    }
    else {
      future.addListener(listener(it));
    }
  }
  
  @Override
  public EventChain execute() {
    execute(Collections.unmodifiableList(events).iterator());
    return this;
  }
  
  @Override
  public EventChain write(Object msg) {
    TcpEvent.ChannelFutureEvent evt = f -> f.channel().writeAndFlush(msg);
    events.add(evt);
    return this;
  }
  
  @Override
  public Future<?> future() {
    return future;
  }
  
  @Override
  public TcpChannel channel() {
    return channel;
  }

}
