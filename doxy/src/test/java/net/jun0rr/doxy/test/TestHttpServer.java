/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.server.http.HttpHandlerSetup;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpServer;
import net.jun0rr.doxy.tcp.ChannelHandlerSetup;
import net.jun0rr.doxy.tcp.SSLHandlerFactory;
import net.jun0rr.doxy.tcp.TcpChannel;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestHttpServer {
  
  @Test
  public void method() throws InterruptedException {
    try {
      //SSLHandlerFactory factory = SSLHandlerFactory.forServer(Paths.get("d:/java/doxy.jks"), "32132155".toCharArray());
      SSLHandlerFactory factory = SSLHandlerFactory.forServer(Paths.get("/home/juno/java/doxy.jks"), "32132155".toCharArray());
      ChannelHandlerSetup<TcpChannel,HttpHandler> setup = HttpHandlerSetup.newSetup()
          .addResponseFilter(()->x->{
            System.out.println("[SERVER] HttpHandler (R2) >>> " + x.response().message());
            x.response().headers().forEach(e->System.out.printf("   - %s: %s%n", e.getKey(), e.getValue()));
            x.context().writeAndFlush(x.response()).addListener(f->{
              x.connectedChannel().closeChannel().onComplete(c->x.shutdown());
            });
            return x.empty();
          })
          .addResponseFilter(()->x->{
            System.out.println("[SERVER] HttpHandler (R1) >>> " + x.response().message());
            return x.forward();
          })
          .enableSSL(factory)
          .addMessageHandler(()->x->{
            System.out.printf("[SERVER] HttpHandler (F1) >>> [%s] %s - %s%n", x.request().method(), x.request().uri(), x.request().protocolVersion());
            x.request().headers().forEach(e->System.out.printf("   - %s: %s%n", e.getKey(), e.getValue()));
            return x.forward();
          })
          .addMessageHandler(()->x->{
            System.out.println("[SERVER] HttpHandler (F2) >>> " + x.request().message());
            if(x.request().message() != null) {
              String msg = x.request().<ByteBuf>message().toString(StandardCharsets.UTF_8);
              System.out.printf("   *** MSG ***%n   %s%n   *** *** ***%n", msg);
              return x.withRequest(x.request().withMessage(msg).get());
            }
            return x.forward();
          })
          .addMessageHandler(()->x->{
            System.out.println("[SERVER] HttpHandler (H1) >>> " + x.request().message());
            x.response().headers().set(CONNECTION, CLOSE);
            x.response().headers().set("x-doxy-custom", "subliminar message");
            //return x.withMessage(x.request().message());
            return x.sendAndClose(x.response().withMessage(x.request().message()).get());
          });
      HttpServer server = HttpServer.open(setup);
      server.bind(Host.of("0.0.0.0:4321"))
          .onComplete(c->System.out.println("[SERVER] listening on: " + c.localAddress()))
          .start()
          .sync();
      server.awaitShutdown();
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
