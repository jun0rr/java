/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
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
import us.pserver.tools.Timer;
import us.pserver.tools.Unchecked;
import net.jun0rr.doxy.tcp.ChannelHandlerSetup;


/**
 *
 * @author Juno
 */
public class TestTcpServer {
  
  @Test
  public void echoServer() {
    System.out.println("------ echoServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    ChannelHandlerSetup<TcpChannel,TcpHandler> channelInitFactory = TcpHandlerSetup.newSetup().addMessageHandler(()-> x->{
      System.out.println("[SERVER] " + x.<ByteBuf>message().toString(StandardCharsets.UTF_8));
      x.send();
      if(count.decrementAndGet() <= 0) {
        x.shutdown();
      }
      return x.empty();
    });
    TcpServer server = TcpServer.open(channelInitFactory)
        .bind(Host.of("0.0.0.0", 4455))
        .onComplete(c->System.out.println("[SERVER] listening on " + c.localAddress()))
        .start()
        .sync();
    channelInitFactory = TcpHandlerSetup.newSetup().addMessageHandler(()-> x->{
      System.out.println("[CLIENT] " + x.<ByteBuf>message().toString(StandardCharsets.UTF_8));
      return x.empty();
    });
    ByteBuf msg1 = Unpooled.copiedBuffer("Hello", StandardCharsets.UTF_8);
    ByteBuf msg2 = Unpooled.copiedBuffer(" ", StandardCharsets.UTF_8);
    ByteBuf msg3 = Unpooled.copiedBuffer("World", StandardCharsets.UTF_8);
    System.out.printf("* Sending: %s", msg1.toString(StandardCharsets.UTF_8));
    System.out.printf(msg2.toString(StandardCharsets.UTF_8));
    System.out.println(msg3.toString(StandardCharsets.UTF_8));
    Timer tm = new Timer.Nanos().start();
    TcpClient cli = TcpClient.open(channelInitFactory)
        .connect(Host.of("localhost:4455"))
        .onComplete(f->System.out.printf("[CLIENT] Connected: %s --> %s%n", f.localAddress(), f.remoteAddress()))
        .write(msg1)
        .write(msg2)
        .onComplete(f->System.out.println("[CLIENT] Message Sent"))
        .start();
    cli.write(msg3)
        .onComplete(c->System.out.println("[CLIENT] Success send msg3"))
        .sync();
    tm.stop();
    System.out.println(tm);
    //while(!count.compareAndSet(0, 0)) Unchecked.call(()->Thread.sleep(50));
    server.awaitShutdown();
    cli.closeChannel()
        .onComplete(g->System.out.printf("[CLIENT] Channel closed (%s)%n", g))
        .shutdown()
        .onShutdown(g->System.out.printf("[CLIENT] Shutdown Complete (%s)%n", g))
        .sync();
    //server.shutdown().sync();
  }
  
  @Test
  public void timestampServer() {
    System.out.println("------ timestampServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    ChannelHandlerSetup<TcpChannel,TcpHandler> channelInitFactory = TcpHandlerSetup.newSetup().addConnectHandler(()-> x->{
      ByteBuf msg = x.alloc().buffer(Long.BYTES);
      msg.writeLong(Instant.now().toEpochMilli());
      x.sendAndClose(msg);
      if(count.decrementAndGet() <= 0) {
        x.shutdown();
      }
      return x.empty();
    //}).enableSSL(SSLHandlerFactory.forServer(Paths.get("d:/java/doxy.jks"), "32132155".toCharArray()));
    }).enableSSL(SSLHandlerFactory.forServer(Paths.get("/media/storage/java/doxy.jks"), "32132155".toCharArray()));
    TcpServer server = TcpServer.open(channelInitFactory)
        .bind(Host.of("0.0.0.0", 3344))
        .onComplete(c->System.out.println("[SERVER] listening on " + c.localAddress()))
        .start()
        .sync()
        ;
    channelInitFactory = TcpHandlerSetup.newSetup().addMessageHandler(()-> x->{
      Instant timestamp = Instant.ofEpochMilli(x.<ByteBuf>message().readLong());
      System.out.printf("[CLIENT] Timestamp: %s%n", timestamp);
      System.out.flush();
      return x.close();
    }).enableSSL(SSLHandlerFactory.forClient());
    TcpClient cli = TcpClient.open(channelInitFactory)
        .connect(Host.of("localhost:3344"))
        .onComplete(f->System.out.printf("[CLIENT] Connected: %s --> %s%n", f.localAddress(), f.remoteAddress()))
        .start()
        .sync();
    //Unchecked.call(()->server.group().awaitTermination(10, TimeUnit.MINUTES));
    //while(!count.compareAndSet(0, 0)) Unchecked.call(()->Thread.sleep(50));
    server.awaitShutdown()
        .onShutdown(g->System.out.println("[SERVER] Shutdown Complete!"));
    cli.shutdown()
        .onShutdown(g->System.out.println("[CLIENT] Shutdown Complete!"))
        .sync();
    //server.shutdown().sync();
  }
  
}
