/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpRequest;
import net.jun0rr.doxy.server.http.HttpResponse;
import net.jun0rr.doxy.server.http.HttpRequestFilter;


/**
 *
 * @author Juno
 */
public class UncaughtExceptionHandler implements ChannelInboundHandler {
  
  private final Function<Throwable,Optional<HttpResponse>> exceptionHandler;
  
  public UncaughtExceptionHandler(Function<Throwable,Optional<HttpResponse>> exceptionHandler) {
    this.exceptionHandler = Objects.requireNonNull(exceptionHandler, "Bad null exception handler Function");
  }
  
  public UncaughtExceptionHandler() {
    this(new ServerErrorFunction());
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      ctx.write(msg);
    }
    catch(Exception e) {
      this.exceptionCaught(ctx, e);
    }
  }
  
  @Override 
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
    exceptionHandler.apply(e).ifPresent(ctx::write);
  }
  
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelReadComplete();
  }
  
  @Override 
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelRegistered();
  }
  
  @Override 
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelUnregistered();
  }
  
  @Override 
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelActive();
  }
  
  @Override 
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelInactive();
  }
  
  @Override 
  public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
    ctx.fireUserEventTriggered(o);
  }
  
  @Override 
  public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelWritabilityChanged();
  }
  
  @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {}
  
  @Override public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {}
  
}
