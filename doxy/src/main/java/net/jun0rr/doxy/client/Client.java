/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.common.PacketEncoder;
import net.jun0rr.doxy.tcp.TcpExchange;
import net.jun0rr.doxy.tcp.TcpServer;
import us.pserver.tools.Hash;


/**
 *
 * @author Juno
 */
public class Client {
  /*
  private final TcpServer server;
  
  private final DoxyEnvironment env;
  
  private final PacketEncoder encoder;
  
  private final AtomicLong order;
  
  public Client(DoxyEnvironment env) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment");
    this.server = new TcpServer(env.configuration().getClientHost());
    this.encoder = new PacketEncoder(env.configuration().getSecurityConfig().getCryptAlgorithm(), env.getPublicKey());
    this.order = new AtomicLong(0L);
    server.addAcceptHandler(this::handler);
  }
  
  private Optional<TcpExchange> handler(TcpExchange x) {
    if(x.message().isPresent()) {
      String cid = Hash.sha256().of(String.join("->", 
          x.context().channel().localAddress().toString(), 
          x.context().channel().remoteAddress().toString()
      ));
      ByteBuf buf = (ByteBuf) x.message().get();
      ByteBuffer data = env.alloc(buf.readableBytes());
      buf.readBytes(data);
      data.flip();
      buf.release();
      Packet p = Packet.of(cid, data, env.configuration().getRemoteHost(), order.getAndIncrement(), data.remaining(), false);
      env.http().sendAsync(encoder.encodePacket(p));
    }
    return x.empty();
  }
  
  public void start() {
    server.start();
  }
  */
  
}
