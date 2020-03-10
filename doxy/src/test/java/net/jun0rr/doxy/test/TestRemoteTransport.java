/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.DoxyConfig;
import net.jun0rr.doxy.cfg.DoxyConfigBuilder;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.common.PacketDecoder;
import net.jun0rr.doxy.common.PacketEncoder;
import net.jun0rr.doxy.server.RemoteTransport;
import net.jun0rr.doxy.tcp.TcpHandler;
import net.jun0rr.doxy.tcp.TcpServer;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestRemoteTransport {
  /*
  @Test
  public void remoteTransport() throws Exception {
    System.out.println("------ remoteTransport ------");
    try {
      final AtomicInteger count = new AtomicInteger(1);
      Supplier<TcpHandler> hnd = ()->x->{
        System.out.println("* Client connected: " + x.context().channel().remoteAddress());
        if(x.message().isPresent()) {
          ByteBuf buf = (ByteBuf) x.message().get();
          System.out.println("* Message received: " + buf.toString(StandardCharsets.UTF_8));
          System.out.println();
        }
        x.send();
        if(count.decrementAndGet() <= 0) {
          x.shutdown();
        }
        return x.empty();
      };
      TcpServer server = TcpServer.open()
          .addMessageHandler(hnd)
          .bind(Host.of("0.0.0.0", 3344))
          .onComplete(c->System.out.println("- Server listening on " + c.localAddress()))
          .start()
          .sync();

      DoxyConfig cfg = DoxyConfigBuilder.newBuilder().configSources().fromResourceProps().load().build();
      System.out.println(cfg);
      DoxyEnvironment env = DoxyEnvironment.of(cfg);
      PacketEncoder enc = new PacketEncoder(cfg.getSecurityConfig().getCryptAlgorithm(), env.getPublicKey());
      PacketDecoder dec = new PacketDecoder(cfg.getSecurityConfig().getCryptAlgorithm(), env.getPrivateKey());

      RemoteTransport transport = new RemoteTransport(env);
      transport.start();

      ByteBuffer content = StandardCharsets.UTF_8.encode("Hello World!");
      Packet p = Packet.of("1234", content, Host.of("localhost", 3344), 1, content.remaining(), false);
      env.outbox().offerLast(enc.encodePacket(p));

      System.out.println("* Waiting for response...");
      p = env.inbox().takeFirst();
      p = dec.decodePacket(p);
      System.out.println("* Packet received: " + p);
      System.out.println("* Packet content : " + StandardCharsets.UTF_8.decode(p.data()));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  */
}
