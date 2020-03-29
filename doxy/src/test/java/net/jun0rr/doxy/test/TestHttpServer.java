/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import io.netty.handler.codec.http.HttpMethod;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.server.http.HttpServerHandlerSetup;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpRoute;
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
      SSLHandlerFactory factory = SSLHandlerFactory.forServer(Paths.get("d:/java/doxy.jks"), "32132155".toCharArray());
      //SSLHandlerFactory factory = SSLHandlerFactory.forServer(Paths.get("/home/juno/java/doxy.jks"), "32132155".toCharArray());
      ChannelHandlerSetup<HttpHandler> setup = HttpServerHandlerSetup.newSetup()
          .addResponseFilter(()->x->{
            System.out.println("[SERVER] HttpHandler (R2) >>> " + x.response().message());
            x.response().headers().forEach(e->System.out.printf("   - %s: %s%n", e.getKey(), e.getValue()));
            x.channel().events()
                .write(x)
                .close()
                .onComplete(c->x.bootstrapChannel().events().shutdown().execute())
                .shutdown()
                .execute();
            return x.empty();
          })
          .addResponseFilter(()->x->{
            System.out.println("[SERVER] HttpHandler (R1) >>> " + x.response().message());
            return x.forward();
          })
          .addRouteHandler(HttpRoute.of("/.*", HttpMethod.POST), ()->x->{
            x.response().headers().add("x-routeA", "/.*");
            return x.withMessage(x.request().message()).forward();
          })
          .addRouteHandler(HttpRoute.of("/echo.*", HttpMethod.POST), ()->x->{
            x.response().headers().set(CONNECTION, CLOSE);
            x.response().headers().add("x-routeB", "/echo.*");
            return x.withMessage(x.request().message()).forward();
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
              return x.withRequest(x.request().withMessage(msg)).forward();
            }
            return x.forward();
          })
          .addMessageHandler(()->x->{
            System.out.println("[SERVER] HttpHandler (H1) >>> " + x.request().message());
            x.response().headers().set("x-doxy-custom", "subliminar message");
            return x.forward();
            //return x.send();
          });
      HttpServer server = HttpServer.open(setup);
      server.bind(Host.of("0.0.0.0:4321"))
          .onComplete(c->System.out.println("[SERVER] listening on: " + c.localAddress()))
          .executeSync();
      server.events().awaitShutdown();
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
