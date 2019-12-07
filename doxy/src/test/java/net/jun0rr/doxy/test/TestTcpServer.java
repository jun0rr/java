/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.tcp.TcpClient;
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
    final AtomicInteger count = new AtomicInteger(1);
    TcpHandler hnd = x->{
      x.send();
      if(count.decrementAndGet() <= 0) {
        x.shutdown();
      }
      return x.empty();
    };
    TcpServer server = new TcpServer(Host.of("0.0.0.0", 3344))
        .addHandler(hnd)
        .start();
    hnd = x->{
      if(x.message().isPresent()) {
        ByteBuf buf = (ByteBuf) x.message().get();
        System.out.printf("* Server: '%s'%n", buf.toString(StandardCharsets.UTF_8));
      }
      return x.empty();
    };
    Channel ch = new TcpClient()
        .addHandler(hnd)
        .connect(Host.of("localhost:3344"))
        .syncUninterruptibly()
        .channel();
    ByteBuf msg = Unpooled.copiedBuffer("Hello", StandardCharsets.UTF_8);
    System.out.printf("* Sending '%s'...%n", msg.toString(StandardCharsets.UTF_8));
    ch.writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
    server.sync();
  }
  
}
