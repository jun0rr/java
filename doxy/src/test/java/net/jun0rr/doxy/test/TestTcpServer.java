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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.tcp.TcpClient;
import net.jun0rr.doxy.tcp.TcpClient2;
import net.jun0rr.doxy.tcp.TcpHandler;
import net.jun0rr.doxy.tcp.TcpServer;
import org.junit.jupiter.api.Test;


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
      x.send();
      if(count.decrementAndGet() <= 0) {
        x.shutdown();
      }
      return x.empty();
    };
    TcpServer server = new TcpServer(Host.of("0.0.0.0", 3344))
        .addAcceptHandler(hnd)
        .start();
    hnd = ()-> x->{
      if(x.message().isPresent()) {
        ByteBuf buf = (ByteBuf) x.message().get();
        System.out.printf("* Server: '%s'%n", buf.toString(StandardCharsets.UTF_8));
      }
      return x.empty();
    };
    ByteBuf msg = Unpooled.copiedBuffer("Hello", StandardCharsets.UTF_8);
    System.out.printf("* Sending '%s'...%n", msg.toString(StandardCharsets.UTF_8));
    TcpClient2.open()
        .addHandler(hnd)
        .connect(Host.of("localhost:3344"))
        .send(msg)
        .send(msg)
        .closeOnComplete()
        .startSync();
    server.sync();
  }
  
  @Test
  public void timestampServer() {
    System.out.println("------ timestampServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    Supplier<TcpHandler> hnd = ()-> x->{
      ByteBuf msg = x.context().alloc().buffer(Long.BYTES);
      msg.writeLong(Instant.now().toEpochMilli());
      x.sendAndClose(msg);
      if(count.decrementAndGet() <= 0) {
        x.shutdown();
      }
      return x.empty();
    };
    TcpServer server = new TcpServer(Host.of("0.0.0.0", 3344))
        .addConnectHandler(hnd)
        .start();
    hnd = ()-> x->{
      if(x.message().isPresent()) {
        ByteBuf buf = (ByteBuf) x.message().get();
        Instant timestamp = Instant.ofEpochMilli(buf.readLong());
        System.out.printf("* Server Timestamp: %s%n", timestamp);
      }
      return x.close();
    };
    TcpClient.open()
        .addHandler(hnd)
        .connect(Host.of("localhost:3344"));
    server.sync();
  }
  
}
