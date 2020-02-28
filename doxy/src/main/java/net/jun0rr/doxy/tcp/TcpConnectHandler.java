/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Objects;
import java.util.function.Consumer;


/**
 *
 * @author Juno
 */
public class TcpConnectHandler extends ChannelInboundHandlerAdapter {
  
  private final Consumer<OutputTcpChannel> handler;
  
  public TcpConnectHandler(Consumer<OutputTcpChannel> handler) {
    this.handler = Objects.requireNonNull(handler, "Bad null TcpHandler");
  }
  
  @Override 
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    handler.accept(new ConnectedTcpChannel(ctx.channel().newSucceededFuture()));
  }
  
}
