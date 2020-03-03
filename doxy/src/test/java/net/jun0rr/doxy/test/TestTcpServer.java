/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.tcp.TcpClient;
import net.jun0rr.doxy.tcp.TcpHandler;
import net.jun0rr.doxy.tcp.TcpServer;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Timer;
import us.pserver.tools.Unchecked;
import net.jun0rr.doxy.tcp.WritableTcpChannel;


/**
 *
 * @author Juno
 */
public class TestTcpServer {
  
  @Test
  public void echoServer() {
    System.out.println("------ echoServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    Supplier<TcpHandler> hnd = ()-> x->{
      System.out.println("[SERVER] " + x.<ByteBuf>message().toString(StandardCharsets.UTF_8));
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
    hnd = ()-> x->{
      System.out.println("[CLIENT] " + x.<ByteBuf>message().toString(StandardCharsets.UTF_8));
      return x.empty();
    };
    ByteBuf msg1 = Unpooled.copiedBuffer("Hello", StandardCharsets.UTF_8);
    ByteBuf msg2 = Unpooled.copiedBuffer(" ", StandardCharsets.UTF_8);
    ByteBuf msg3 = Unpooled.copiedBuffer("World", StandardCharsets.UTF_8);
    System.out.printf("* Sending: %s", msg1.toString(StandardCharsets.UTF_8));
    System.out.printf(msg2.toString(StandardCharsets.UTF_8));
    System.out.println(msg3.toString(StandardCharsets.UTF_8));
    Timer tm = new Timer.Nanos().start();
    TcpClient cli = TcpClient.open()
        .addMessageHandler(hnd)
        .connect(Host.of("localhost:3344"))
        .onComplete(f->System.out.printf("- Client Connected: %s --> %s%n", f.localAddress(), f.remoteAddress()))
        .write(msg1)
        .write(msg2)
        .onComplete(f->System.out.println("- Message Sent"))
        .start();
    cli.write(msg3)
        .onComplete(c->System.out.println("- Success send msg3"))
        .sync();
    tm.stop();
    System.out.println(tm);
    Unchecked.call(()->server.group().awaitTermination(10, TimeUnit.MINUTES));
    cli
        .closeChannel()
        .onComplete(g->System.out.printf("- Channel closed (%s)%n", g))
        .shutdown()
        .onShutdown(g->System.out.printf("- Shutdown Complete (%s)%n", g))
        .sync();
  }
  
  @Test
  public void timestampServer() {
    System.out.println("------ timestampServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    Supplier<Consumer<WritableTcpChannel>> chandler = ()-> c->{
      ByteBuf msg = c.channel().get().alloc().buffer(Long.BYTES);
      msg.writeLong(Instant.now().toEpochMilli());
      c.write(msg).closeChannel();
      if(count.decrementAndGet() <= 0) {
        c.shutdown();
      }
    };
    TcpServer server = TcpServer.open()
        .addConnectHandler(chandler)
        .bind(Host.of("0.0.0.0", 3344))
        .onComplete(c->System.out.println("- Server listening on " + c.localAddress()))
        .start()
        .sync()
        ;
    Supplier<TcpHandler> hnd = ()-> x->{
      Instant timestamp = Instant.ofEpochMilli(x.<ByteBuf>message().readLong());
      System.out.printf("[CLIENT] Timestamp: %s%n", timestamp);
      return x.close();
    };
    TcpClient cli = TcpClient.open()
        .addMessageHandler(hnd)
        .connect(Host.of("localhost:3344"))
        .onComplete(f->System.out.printf("- Client Connected: %s --> %s%n", f.localAddress(), f.remoteAddress()))
        .start()
        .sync();
    Unchecked.call(()->server.group().awaitTermination(10, TimeUnit.MINUTES));
    cli
        //.shutdown();
        .shutdown()
        .onShutdown(g->System.out.printf("- Client Shutdown Complete (%s)%n", g))
        .sync();
  }
  
}
