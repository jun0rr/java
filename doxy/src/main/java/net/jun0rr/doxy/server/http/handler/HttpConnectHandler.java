/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.handler;

import net.jun0rr.doxy.tcp.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Objects;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;


/**
 *
 * @author Juno
 */
public class HttpConnectHandler extends ChannelInboundHandlerAdapter {
  
  private final TcpChannel channel;
  
  private final HttpHandler handler;
  
  public HttpConnectHandler(TcpChannel ch, HttpHandler handler) {
    this.handler = Objects.requireNonNull(handler, "Bad null TcpHandler");
    this.channel = Objects.requireNonNull(ch, "Bad null TcpChannel");
  }
  
  @Override 
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    HttpExchange x = HttpExchange.of(channel, new ConnectedTcpChannel(ctx.newSucceededFuture()), ctx);
    SslHandler ssl = ctx.pipeline().get(SslHandler.class);
    GenericFutureListener lst = f->handler.apply(x);
    if(ssl != null) {
      ssl.handshakeFuture().addListener(lst);
    }
    else {
      ctx.newSucceededFuture().addListener(lst);
    }
  }
  
}
