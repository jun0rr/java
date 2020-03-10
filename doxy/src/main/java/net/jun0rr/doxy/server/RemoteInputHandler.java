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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.common.Packet.PacketImpl;
import net.jun0rr.doxy.common.PacketEncoder;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.tcp.TcpExchange;
import net.jun0rr.doxy.tcp.TcpHandler;


/**
 *
 * @author Juno
 */
public class RemoteInputHandler implements TcpHandler {
  
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
  public Optional<TcpExchange> apply(TcpExchange ex) throws Exception {
    throw new UnsupportedOperationException();
    //if(ex.message().isPresent()) {
      //ByteBuf buf = (ByteBuf) ex.message().get();
      //ByteBuffer cont = env.alloc(buf.readableBytes());
      //buf.readBytes(cont);
      //cont.flip();
      //buf.release();
      //Packet p = new PacketImpl(cid, cont, env.configuration().getRemoteHost(), order.getAndIncrement(), cont.remaining(), false);
      //env.inbox().offerLast(encoder.encodePacket(p));
    //}
    //return ex.empty();
  }
  
}
