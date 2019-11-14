/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.jun0rr.doxy.server.HttpExchange;
import net.jun0rr.doxy.server.HttpRequest;
import net.jun0rr.doxy.server.HttpResponse;
import net.jun0rr.doxy.server.HttpRequestFilter;


/**
 *
 * @author Juno
 */
public class DefaultHttpRequestFilter implements ChannelInboundHandler, HttpRequestFilter {
  
  private final HttpRequestFilter filter;
  
  public DefaultHttpRequestFilter(HttpRequestFilter hnd) {
    this.filter = Objects.requireNonNull(hnd, "Bad null HttpHandler");
  }
  
  @Override
  public Optional<HttpRequest> filter(ChannelHandlerContext ctx, HttpRequest he) throws Exception {
    return filter.filter(ctx, he);
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      if(msg instanceof HttpRequest) {
        filter(ctx, (HttpRequest)msg).ifPresent(ctx::fireChannelRead);
      }
      else if(msg instanceof FullHttpRequest) {
        filter(ctx, HttpRequest.of((FullHttpRequest)msg)).ifPresent(ctx::fireChannelRead);
      }
      else if(msg instanceof HttpExchange) {
        filter(ctx, ((HttpExchange)msg).request()).ifPresent(ctx::fireChannelRead);
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
    ctx.fireExceptionCaught(e);
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
