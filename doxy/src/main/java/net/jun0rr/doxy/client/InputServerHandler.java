/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import net.jun0rr.doxy.common.DoxyChannel;
import net.jun0rr.doxy.common.DoxyChannel.DoxyChannelImpl;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.common.Packet.PacketImpl;
import net.jun0rr.doxy.common.PacketEncoder;
import us.pserver.tools.Hash;


/**
 *
 * @author Juno
 */
public class InputServerHandler implements ChannelInboundHandler {
  
  private final DoxyEnvironment env;
  
  private final PacketEncoder encoder;
  
  private final InternalLogger log;
  
  public InputServerHandler(DoxyEnvironment env) {
    this.env = env;
    this.encoder = new PacketEncoder(
        env.configuration().getSecurityConfig().getCryptAlgorithm(), 
        env.getPrivateKey()
    );
    this.log = InternalLoggerFactory.getInstance(getClass());
  }
  
  private DoxyChannel createDoxyChannel(String cid, Channel c) {
    log.info("newconn {} -> {} : {}", c.localAddress(), c.remoteAddress(), cid);
    DoxyChannel dc = new DoxyChannelImpl(env, cid, c);
    env.channels().add(dc);
    c.closeFuture().addListener(f->{
      env.channels().remove(dc);
      env.http().close(cid);
    });
    return dc;
  }
  
  private DoxyChannel getOrCreateDoxyChannel(Channel c) {
    String cid = Hash.sha256().of(String.join("->", 
        c.localAddress().toString(), 
        c.remoteAddress().toString())
    );
    return env.getChannelById(cid).orElse(createDoxyChannel(cid, c));
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    DoxyChannel dc = getOrCreateDoxyChannel(ctx.channel());
    ByteBuf buf = (ByteBuf) msg;
    ByteBuffer cont = env.alloc(buf.readableBytes());
    buf.readBytes(cont);
    cont.flip();
    buf.release();
    Packet p = new PacketImpl(dc.uid(), cont, env.configuration().getRemoteHost(), dc.nextOrder(), cont.remaining(), false);
    env.outbox().offerLast(encoder.encodePacket(p));
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
