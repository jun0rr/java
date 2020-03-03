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
import java.util.concurrent.atomic.AtomicInteger;
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
  
  private static final int TOTAL = 10000;
  
  public AtomicInteger COUNT = new AtomicInteger(TOTAL);
  
  @Test
  public void tcpClient() throws InterruptedException {
    System.out.println("----- tcpClient() -----");
    Timer tm = new Timer.Nanos();
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
    tm.start();
    for(int i = 0; i < TOTAL; i++) {
      ByteBuf msg = Unpooled.copiedBuffer("Hello World!", StandardCharsets.UTF_8);
      TcpClient.open(clients)
          .addMessageHandler(()->x->{
            COUNT.decrementAndGet();
            return x.close();
          })
          .connect(target)
          .write(msg)
          .start();
    }
    
    System.out.println("* Waiting CountDown...");
    int cc = COUNT.get();
    int retry = 100;
    while(COUNT.get() > 0) {
      if(retry <= 0) {
        System.out.println("# ERROR clients(): COUNT(" + COUNT + ") is static for " + 100*50 + " millis...");
        break;
      }
      Thread.sleep(50);
      if(COUNT.get() == cc) {
        retry--;
      }
      cc = COUNT.get();
    }
      
    tm.stop();
    System.out.println(tm);
    server.shutdown().sync();
    clients.shutdownGracefully();
  }
  
}
