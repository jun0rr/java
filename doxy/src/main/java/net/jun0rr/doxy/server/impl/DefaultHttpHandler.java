/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.impl;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.jun0rr.doxy.server.HttpExchange;
import net.jun0rr.doxy.server.HttpHandler;
import net.jun0rr.doxy.server.HttpRequest;
import net.jun0rr.doxy.server.HttpResponse;


/**
 *
 * @author Juno
 */
public class DefaultHttpHandler implements ChannelInboundHandler, HttpHandler {
  
  private final HttpHandler handler;
  
  private final Function<Throwable,HttpResponse> exceptionHandler;
  
  public DefaultHttpHandler(HttpHandler hnd, Function<Throwable,HttpResponse> exceptionHandler) {
    this.handler = Objects.requireNonNull(hnd, "Bad null HttpHandler");
    this.exceptionHandler = Objects.requireNonNull(exceptionHandler, "Bad null exception handler Function");
  }
  
  public DefaultHttpHandler(HttpHandler hnd) {
    this(hnd, new ServerErrorFunction());
  }
  
  @Override
  public Optional<HttpExchange> handle(HttpExchange he) throws Exception {
    return handler.handle(he);
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      if(msg instanceof HttpRequest) {
        handle(HttpExchange.of(ctx, (HttpRequest)msg)).ifPresent(ctx::write);
      }
      else if(msg instanceof FullHttpRequest) {
        handle(HttpExchange.of(ctx, HttpRequest.of((FullHttpRequest)msg))).ifPresent(ctx::write);
      }
      else if(msg instanceof HttpExchange) {
        handle((HttpExchange)msg).ifPresent(ctx::write);
      }
      else {
        throw new IllegalArgumentException("Unexpected message type: " + msg.getClass());
      }
    }
    catch(Exception e) {
      this.exceptionCaught(ctx, e);
    }
  }
  
  @Override 
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
    writeAndClose(ctx, exceptionHandler.apply(e));
  }
  
  public void writeAndClose(ChannelHandlerContext ctx, HttpResponse res) throws Exception {
    ctx.writeAndFlush(res.toNettyResponse()).addListener(ChannelFutureListener.CLOSE);
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
