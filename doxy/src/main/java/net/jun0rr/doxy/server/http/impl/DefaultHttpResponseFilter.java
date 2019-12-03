/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.Optional;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpResponse;
import net.jun0rr.doxy.server.http.HttpResponseFilter;


/**
 *
 * @author Juno
 */
public class DefaultHttpResponseFilter implements ChannelOutboundHandler, HttpResponseFilter {
  
  private final HttpResponseFilter filter;
  
  public DefaultHttpResponseFilter(HttpResponseFilter flt) {
    this.filter = Objects.requireNonNull(flt, "Bad null HttpHandler");
  }
  
  @Override
  public Optional<HttpResponse> filter(ChannelHandlerContext ctx, HttpResponse he) throws Exception {
    return filter.filter(ctx, he);
  }
  
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise cp) throws Exception {
    try {
      if(msg instanceof HttpResponse) {
        filter(ctx, (HttpResponse)msg).ifPresent(r->ctx.write(r, cp));
      }
      else if(msg instanceof FullHttpResponse) {
        filter(ctx, HttpResponse.of((FullHttpResponse)msg)).ifPresent(r->ctx.write(r, cp));
      }
      else if(msg instanceof HttpExchange) {
        filter(ctx, ((HttpExchange)msg).response()).ifPresent(r->ctx.write(r, cp));
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
