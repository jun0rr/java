/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.tcp.TcpClient;
import net.jun0rr.doxy.tcp.TcpHandler;
import net.jun0rr.doxy.tcp.TcpServer;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Timer;


/**
 *
 * @author Juno
 */
public class TestTcpChannelPerformance {
  
  private static final int COUNT = 1000;
  
  @Test
  public void tcpClient() throws InterruptedException {
    System.out.println("----- tcpClient() -----");
    Timer tm = new Timer.Nanos();
    for(int j = 0; j < 5; j++) {
    CountDownLatch cd = new CountDownLatch(COUNT-1);
    Supplier<TcpHandler> h = ()->x->{
      return x.send();
    };
    TcpServer server = TcpServer.open()
        .addMessageHandler(h)
        .bind(Host.of("0.0.0.0:4321"))
        .onComplete(c->System.out.println("* Server listening: " + c.localAddress()))
        .start()
        .sync();
    Host target = Host.of("localhost:4321");
    EventLoopGroup clients = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()*2);
    if(j == 0) tm.start();
    for(int i = 0; i < COUNT; i++) {
      ByteBuf msg = Unpooled.copiedBuffer("Hello World!", StandardCharsets.UTF_8);
      TcpClient.open(clients)
          .addMessageHandler(()->x->{
            cd.countDown();
            return x.close();
          })
          .connect(target)
          .send(msg)
          .start();
    }
    cd.await();
    if(j == 4) tm.lapAndStop();
    else tm.lap();
    System.out.println(tm);
    server.shutdown().sync();
    clients.shutdownGracefully();
    }
  }
  
}
