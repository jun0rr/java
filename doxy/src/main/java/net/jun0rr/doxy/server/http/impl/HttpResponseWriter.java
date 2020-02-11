/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import java.net.SocketAddress;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpResponse;


/**
 *
 * @author Juno
 */
public class HttpResponseWriter implements ChannelOutboundHandler {
  
  public HttpResponseWriter() {}
  
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise cp) throws Exception {
    try {
      Object res = msg;
      boolean close = false;
      if(msg instanceof HttpResponse) {
        //res = ((HttpResponse) msg).toNettyResponse();
        res = ((HttpResponse) msg);
      }
      else if(msg instanceof HttpExchange) {
        //res = ((HttpExchange) msg).response().toNettyResponse();
        res = ((HttpExchange) msg).response();
      }
      if(msg instanceof FullHttpResponse) {
        close = ((FullHttpResponse) msg).headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE, true);
      }
      ChannelFuture f = ctx.writeAndFlush(res, cp);
      if(close) f.addListener(ChannelFutureListener.CLOSE);
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
  public void bind(ChannelHandlerContext ctx, SocketAddress sa, ChannelPromise cp) throws Exception {
    ctx.bind(sa, cp);
  }
  
  @Override
  public void connect(ChannelHandlerContext ctx, SocketAddress sa, SocketAddress sa1, ChannelPromise cp) throws Exception {
    ctx.connect(sa, sa1, cp);
  }
  
  @Override
  public void disconnect(ChannelHandlerContext ctx, ChannelPromise cp) throws Exception {
    ctx.disconnect(cp);
  }
  
  @Override
  public void close(ChannelHandlerContext ctx, ChannelPromise cp) throws Exception {
    ctx.close(cp);
  }
  
  @Override
  public void deregister(ChannelHandlerContext ctx, ChannelPromise cp) throws Exception {
    ctx.deregister(cp);
  }
  
  @Override
  public void read(ChannelHandlerContext ctx) throws Exception {
    ctx.read();
  }
  
  @Override
  public void flush(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }
  
  @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {}
  
  @Override public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {}
  
}
