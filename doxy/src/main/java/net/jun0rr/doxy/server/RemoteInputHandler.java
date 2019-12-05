/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;
import net.jun0rr.doxy.cfg.HostConfig;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.common.Packet.PacketImpl;
import net.jun0rr.doxy.common.PacketEncoder;


/**
 *
 * @author Juno
 */
public class RemoteInputHandler implements ChannelInboundHandler {
  
  private final DoxyEnvironment env;
  
  private final String cid;
  
  private final AtomicLong order;
  
  private final PacketEncoder encoder;
  
  public RemoteInputHandler(DoxyEnvironment env, String channelID) {
    this.env = env;
    this.cid = channelID;
    this.order = new AtomicLong(0L);
    this.encoder = new PacketEncoder(
        env.configuration().getSecurityConfig().getCryptAlgorithm(), 
        env.getPrivateKey()
    );
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf buf = (ByteBuf) msg;
    ByteBuffer cont = env.alloc(buf.readableBytes());
    buf.readBytes(cont);
    cont.flip();
    buf.release();
    InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
    HostConfig rem = HostConfig.of(addr.getAddress().getHostAddress(), addr.getPort());
    Packet p = new PacketImpl(cid, cont, rem, order.getAndIncrement(), cont.remaining(), false);
    env.inbox().offerLast(encoder.encodePacket(p));
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
