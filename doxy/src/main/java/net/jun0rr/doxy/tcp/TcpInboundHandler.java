/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Objects;


/**
 *
 * @author Juno
 */
public class TcpInboundHandler extends ChannelInboundHandlerAdapter {
  
  private final TcpHandler handler;
  
  private final TcpChannel channel;
  
  public TcpInboundHandler(TcpChannel cls, TcpHandler handler) {
    this.channel = cls;
    this.handler = Objects.requireNonNull(handler, "Bad null TcpHandler");
  }
  
  private TcpExchange exchange(ChannelHandlerContext ctx, Object msg) {
    return (msg instanceof TcpExchange) ? (TcpExchange)msg : TcpExchange.of(channel, ctx, msg);
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    handler.handle(exchange(ctx, msg)).ifPresent(ctx::fireChannelRead);
  }
  
}
