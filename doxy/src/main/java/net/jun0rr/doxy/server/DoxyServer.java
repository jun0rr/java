/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.nio.NioEventLoopGroup;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import net.jun0rr.doxy.common.AbstractRunnable;
import net.jun0rr.doxy.common.DoxyChannel;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.common.PacketCollection;
import net.jun0rr.doxy.common.PacketDecoder;
import net.jun0rr.doxy.common.PacketEncoder;
import net.jun0rr.doxy.common.ToNioBuffer;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpRoute;
import net.jun0rr.doxy.server.http.HttpServer;
import net.jun0rr.doxy.server.http.HttpServerHandlerSetup;
import net.jun0rr.doxy.tcp.ChannelHandlerSetup;
import net.jun0rr.doxy.tcp.SSLHandlerFactory;
import net.jun0rr.doxy.tcp.TcpClient;
import net.jun0rr.doxy.tcp.TcpHandler;
import net.jun0rr.doxy.tcp.TcpHandlerSetup;


/**
 *
 * @author Juno
 */
public class DoxyServer extends AbstractRunnable {
  
  private final PacketEncoder encoder;
  
  private final PacketDecoder decoder;
  
  private final HttpServer server;
  
  public DoxyServer(DoxyEnvironment env) {
    super(env);
    this.encoder = new PacketEncoder(env.configuration().getSecurityConfig().getCryptAlgorithm(), env.getPublicKey());
    this.decoder = new PacketDecoder(env.configuration().getSecurityConfig().getCryptAlgorithm(), env.getPrivateKey());
    SSLHandlerFactory factory = SSLHandlerFactory.forServer(
        env.configuration().getSecurityConfig().getKeystorePath(), 
        env.configuration().getSecurityConfig().getKeystorePassword()
    );
    ChannelHandlerSetup<HttpHandler> setup = HttpServerHandlerSetup.newSetup()
        .addRouteHandler(HttpRoute.get("/pull"), pull())
        .addRouteHandler(HttpRoute.post("/push"), push())
        .enableSSL(factory)
        .addMessageHandler(()->new JwtAuthFilter(env));
    this.server = HttpServer.open(new NioEventLoopGroup(2), new NioEventLoopGroup(env.configuration().getThreadPoolSize()), setup);
  }
  
  private Supplier<HttpHandler> push() {
    return ()->x->{
      if(x.request().message() != null && x.request().<ByteBuf>message().isReadable()) {
        Packet p = Packet.of(ToNioBuffer.apply(x.request().message()));
        getChannel(p).writePacket(decoder.decodePacket(p));
      }
      return x.empty();
    };
  }
  
  private Collection<Packet> getOutbox() throws InterruptedException {
    List<Packet> pcs = new LinkedList<>();
    Packet p = env.outbox().pollFirst(env.configuration().getServerTimeout(), TimeUnit.MILLISECONDS);
    while(p != null) {
      pcs.add(p);
      p = env.outbox().pollFirst();
    }
    return pcs;
  }
  
  private Supplier<HttpHandler> pull() {
    return ()->x->{
      Collection<Packet> pcs = getOutbox();
      if(!pcs.isEmpty()) {
        PacketCollection pc = PacketCollection.of(pcs);
        return x.withMessage(Unpooled.wrappedBuffer(pc.toByteBuffer())).send();
      }
      return x.send();
    };
  }

  private Supplier<HttpHandler> release() {
    return ()->x->{
      
    };
  }
  
  private DoxyChannel getChannel(Packet p) {
    return env.getChannel(p.channelID())
        .orElse(DoxyChannel.of(env, p.channelID(), TcpClient.open(
            new NioEventLoopGroup(env.configuration().getThreadPoolSize(), env.executor()), 
            TcpHandlerSetup.newSetup().addMessageHandler(tcpHandler(p.channelID()))
        ).connect(p.remote()).channel()));
  }
  
  private Supplier<TcpHandler> tcpHandler(String cid) {
    return ()->x->{
      if(x.message() != null && x.<ByteBuf>message().isReadable()) {
        ByteBuffer msg = ToNioBuffer.apply(x.message());
        DoxyChannel dc = env.channels().get(cid);
        Packet p = Packet.of(cid, msg, x.channel().remoteHost(), dc.nextOrder(), msg.remaining(), false);
        env.outbox().offer(encoder.encodePacket(p));
      }
      return x.empty();
    };
  }
  
  @Override
  public void run() {
    
  }
  
}
