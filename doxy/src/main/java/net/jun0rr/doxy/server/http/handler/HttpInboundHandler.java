/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.handler;

import net.jun0rr.doxy.tcp.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Objects;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpRequest;
import net.jun0rr.doxy.server.http.HttpResponse;


/**
 *
 * @author Juno
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
  
  private final TcpChannel channel;
  
  private final HttpHandler handler;
  
  public HttpInboundHandler(TcpChannel chn, HttpHandler hnd) {
    this.handler = Objects.requireNonNull(hnd, "Bad null HttpHandler");
    this.channel = Objects.requireNonNull(chn, "Bad null TcpChannel");
  }
  
  private HttpExchange exchange(ChannelHandlerContext ctx, Object msg) {
    ConnectedTcpChannel cnc = new ConnectedTcpChannel(ctx.channel().newSucceededFuture(), null);
    System.out.println("[HttpInboundHandler] message=" + msg.getClass());
    HttpExchange ex;
    if(msg instanceof FullHttpRequest) {
      ex = HttpExchange.of(
          channel, cnc, ctx, 
          HttpRequest.of((FullHttpRequest)msg), 
          HttpResponse.of(HttpResponseStatus.OK)
      );
    }
    else if(msg instanceof HttpRequest) {
      ex = HttpExchange.of(
          channel, cnc, ctx, 
          (HttpRequest) msg, 
          HttpResponse.of(HttpResponseStatus.OK)
      );
    }
    else if(msg instanceof HttpExchange) {
      ex = (HttpExchange) msg;
    }
    else {
      throw new HttpInboundException("Unknown message type: %s", msg.getClass());
    }
    return ex;
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    handler.apply(exchange(ctx, msg)).ifPresent(ctx::fireChannelRead);
  }
  
  
  
  public class HttpInboundException extends RuntimeException {
    
    public HttpInboundException(Throwable e) {
      this(e.toString(), e);
    }
    
    public HttpInboundException(Throwable e, String msg) {
      super(msg, e);
    }
    
    public HttpInboundException(String msg) {
      super(msg);
    }
    
    public HttpInboundException(String msg, Object... args) {
      super(String.format(msg, args));
    }
    
    public HttpInboundException(Throwable e, String msg, Object... args) {
      super(String.format(msg, args));
    }
    
  }
  
}
