/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import java.util.concurrent.atomic.AtomicInteger;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.TcpHandler;
import net.jun0rr.doxy.common.TcpServer;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestEchoServer3 {
  
  @Test
  public void method() {
    final AtomicInteger count = new AtomicInteger(0);
    TcpHandler hnd = x->{
      if(count.decrementAndGet() <= 0) {
        x.server().stop();
        return x.noMessage();
      }
      return x.send();
    };
    new TcpServer(Host.of("0.0.0.0", 3344))
        //.addHandler(TcpExchange::send)
        .addHandler(hnd)
        .start().sync();
  }
  
}
