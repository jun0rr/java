/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;


/**
 *
 * @author Juno
 */
public abstract class AbstractHttpRequestHandler implements HttpRequestHandler {
  
  public void writeAndClose(ChannelHandlerContext ctx, HttpResponse res) throws Exception {
    ctx.writeAndFlush(res.toNettyResponse()).addListener(ChannelFutureListener.CLOSE);
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      if(msg instanceof HttpRequest) {
        this.httpRequest(ctx, (HttpRequest) msg).ifPresent(ctx::write);
      }
      else if(msg instanceof FullHttpRequest) {
        this.httpRequest(ctx, HttpRequest.of((FullHttpRequest)msg)).ifPresent(ctx::write);
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
    HttpResponse res = HttpResponse.of(HttpResponseStatus.INTERNAL_SERVER_ERROR);
    res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
    res.headers().set("x-error-type", e.getClass().getName());
    res.headers().set("x-error-message", e.getMessage());
    if(e.getCause() != null) {
      res.headers().set("x-error-cause", e.getCause());
    }
    ctx.write(res);
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
