/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Objects;
import net.jun0rr.doxy.tcp.ConnectedTcpChannel;
import net.jun0rr.doxy.tcp.TcpChannel;
import net.jun0rr.doxy.tcp.TcpExchange;
import net.jun0rr.doxy.tcp.TcpHandler;


/**
 *
 * @author Juno
 */
public class TcpConnectHandler extends ChannelInboundHandlerAdapter {
  
  private final TcpChannel channel;
  
  private final TcpHandler handler;
  
  public TcpConnectHandler(TcpChannel ch, TcpHandler handler) {
    this.handler = Objects.requireNonNull(handler, "Bad null TcpHandler");
    this.channel = Objects.requireNonNull(ch, "Bad null TcpChannel");
  }
  
  @Override 
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    TcpExchange x = TcpExchange.of(channel, new ConnectedTcpChannel(ctx.channel().newSucceededFuture(), null), ctx, null);
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
