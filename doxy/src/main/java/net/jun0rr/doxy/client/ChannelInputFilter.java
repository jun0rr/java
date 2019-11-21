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
import java.nio.ByteBuffer;
import java.util.Objects;
import net.jun0rr.doxy.DoxyChannel;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.impl.DoxyChannelImpl;
import net.jun0rr.doxy.impl.PacketImpl;
import org.jose4j.lang.JoseException;
import us.pserver.tools.Hash;


/**
 *
 * @author Juno
 */
public class ChannelInputFilter implements ChannelInboundHandler {
  
  private final DoxyEnvironment env;
  
  private final JwtClientFactory jwf;
  
  public ChannelInputFilter(DoxyEnvironment env) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment");
    this.jwf = new JwtClientFactory(env);
  }
  
  @Override 
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      String sid = Hash.sha256().of(String.join("->", 
          ctx.channel().localAddress().toString(), 
          ctx.channel().remoteAddress().toString())
      );
      DoxyChannel ch = env.getChannelById(sid)
          .orElse(createDoxyChannel(sid, ctx.channel()));
      ByteBuf buf = (ByteBuf) msg;
      ByteBuffer bb = buf.isDirect() 
          ? ByteBuffer.allocateDirect(buf.readableBytes()) 
          : ByteBuffer.allocate(buf.readableBytes());
      buf.readBytes(bb);
      bb.flip();
      env.http().send(new PacketImpl(sid, bb, ch.nextOrder(), bb.remaining()));
    }
    catch(Exception e) {
      this.exceptionCaught(ctx, e);
    }
  }
  
  private DoxyChannel createDoxyChannel(String id, Channel ch) throws JoseException {
    DoxyChannel dc = new DoxyChannelImpl(env, id, jwf.getAuthenticationToken(id), ch);
    env.channels().add(dc);
    return dc;
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
