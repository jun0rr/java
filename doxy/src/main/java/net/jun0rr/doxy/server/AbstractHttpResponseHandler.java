/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.net.SocketAddress;


/**
 *
 * @author Juno
 */
public abstract class AbstractHttpResponseHandler implements HttpResponseHandler {
  
  public void writeAndClose(ChannelHandlerContext ctx, HttpResponse res) throws Exception {
    ctx.writeAndFlush(res.toNettyResponse()).addListener(ChannelFutureListener.CLOSE);
  }
  
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise cp) throws Exception {
    try {
      if(msg instanceof HttpResponse) {
        this.httpResponse(ctx, (HttpResponse) msg).ifPresent(r->ctx.write(r, cp));
      }
      else if(msg instanceof FullHttpResponse) {
        this.httpResponse(ctx, HttpResponse.of((FullHttpResponse)msg)).ifPresent(r->ctx.write(r, cp));
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
