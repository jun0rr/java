/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.tcp.SSLHandlerFactory;
import net.jun0rr.doxy.tcp.TcpChannel;
import net.jun0rr.doxy.tcp.TcpHandlerSetup;
import net.jun0rr.doxy.tcp.TcpClient;
import net.jun0rr.doxy.tcp.TcpHandler;
import net.jun0rr.doxy.tcp.TcpServer;
import org.junit.jupiter.api.Test;
import net.jun0rr.doxy.tcp.ChannelHandlerSetup;
import net.jun0rr.doxy.tcp.EventChain;


/**
 *
 * @author Juno
 */
public class TestTcpServer {
  
  @Test
  public void echoServer() {
    System.out.println("------ echoServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    ChannelHandlerSetup<TcpHandler> setup = TcpHandlerSetup.newSetup().addMessageHandler(()-> x->{
      System.out.println("[SERVER] " + x.<ByteBuf>message().toString(StandardCharsets.UTF_8));
      EventChain ex = x.channel().events().write(x.message()).close();
      if(count.decrementAndGet() <= 0) {
        ex.onComplete(g->x.bootstrapChannel().events().shutdown().execute()).shutdown();
      }
      ex.execute();
      return x.empty();
    });
    TcpChannel server = TcpServer.open(setup)
        .bind(Host.of("0.0.0.0", 4455))
        .onComplete(c->System.out.println("[SERVER] listening on " + c.localAddress()))
        .executeSync()
        .channel();
    setup = TcpHandlerSetup.newSetup().addMessageHandler(()-> x->{
      System.out.println("[CLIENT] " + x.<ByteBuf>message().toString(StandardCharsets.UTF_8));
      return x.empty();
    });
    ByteBuf msg = Unpooled.copiedBuffer("Hello World!", StandardCharsets.UTF_8);
    System.out.printf("* Sending: %s%n", msg.toString(StandardCharsets.UTF_8));
    TcpChannel cli = TcpClient.open(setup)
        .connect(Host.of("localhost:4455"))
        .onComplete(f->System.out.printf("[CLIENT] Connected: %s --> %s%n", f.localAddress(), f.remoteAddress()))
        .write(msg)
        .onComplete(f->System.out.println("[CLIENT] Message Sent"))
        .executeSync()
        .channel();
    server.events()
        .awaitShutdown()
        .onShutdown(g->System.out.printf("[SERVER] Shutdown Complete (%s)%n", g), Throwable::printStackTrace)
        .executeSync();
    cli.events()
        .shutdown()
        .onShutdown(g->System.out.printf("[CLIENT] Shutdown Complete (%s)%n", g), Throwable::printStackTrace)
        .executeSync();
  }
  
  @Test
  public void timestampServer() throws InterruptedException {
    System.out.println("------ timestampServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    ChannelHandlerSetup<TcpHandler> setup = TcpHandlerSetup.newSetup().addConnectHandler(()-> x->{
      ByteBuf msg = x.context().alloc().buffer(Long.BYTES);
      msg.writeLong(Instant.now().toEpochMilli());
      EventChain ec = x.channel().events().write(msg).close();
      if(count.decrementAndGet() <= 0) {
        ec.onComplete(g->x.bootstrapChannel().events().shutdown().execute()).shutdown();
      }
      ec.execute();
      return x.empty();
    }).enableSSL(SSLHandlerFactory.forServer(Paths.get("d:/java/doxy.jks"), "32132155".toCharArray()));
    //}).enableSSL(SSLHandlerFactory.forServer(Paths.get("/media/storage/java/doxy.jks"), "32132155".toCharArray()));
    TcpChannel server = TcpServer.open(setup)
        .bind(Host.of("0.0.0.0", 3344))
        .onComplete(c->System.out.println("[SERVER] listening on " + c.localAddress()))
        .executeSync()
        .channel();
    setup = TcpHandlerSetup.newSetup().addMessageHandler(()-> x->{
      Instant timestamp = Instant.ofEpochMilli(x.<ByteBuf>message().readLong());
      System.out.printf("[CLIENT] Timestamp: %s%n", timestamp);
      x.channel().events().shutdown().execute();
      return x.empty();
    }).enableSSL(SSLHandlerFactory.forClient());
    TcpChannel cli = TcpClient.open(setup)
        .connect(Host.of("localhost:3344"))
        .onComplete(f->System.out.printf("[CLIENT] Connected: %s --> %s%n", f.localAddress(), f.remoteAddress()))
        .executeSync()
        .channel();
    server.events()
        .awaitShutdown()
        .onShutdown(g->System.out.println("[SERVER] Shutdown Complete!"))
        .executeSync();
    cli.events()
        .awaitShutdown()
        .onShutdown(g->System.out.println("[CLIENT] Shutdown Complete!"))
        .executeSync();
  }
  
}
