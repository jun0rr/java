/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpRequestFilter;
import net.jun0rr.doxy.server.http.HttpResponseFilter;
import net.jun0rr.doxy.server.http.HttpServer;
import net.jun0rr.doxy.server.http.HttpServerConfig;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestHttpServer {
  @Test
  public void method() throws InterruptedException {
    try {
      HttpServerConfig cfg = HttpServerConfig.of(Paths.get("d:/java/doxy.jks"), "32132155".toCharArray(), "Doxy-1.0");
      //HttpServerConfig cfg = HttpServerConfig.of(Paths.get("/home/juno/java/doxy.jks"), "32132155".toCharArray(), "Doxy-1.0");
      Supplier<HttpRequestFilter> f1 = ()-> (c,r)-> {
        System.out.println("[SERVER] HttpRequestFilter (f1) >>>");
        System.out.printf("   [%s] %s - %s%n", r.method(), r.uri(), r.protocolVersion());
        r.headers().forEach(e->System.out.printf("      - %s: %s%n", e.getKey(), e.getValue()));
        return Optional.of(r);
      };
      Supplier<HttpRequestFilter> f2 = ()-> (c,r)-> {
        System.out.println("[SERVER] HttpRequestFilter (f2) >>>");
        r.<ByteBuf>message()
            .map(b->b.toString(StandardCharsets.UTF_8))
            .ifPresent(s->System.out.printf("   *** MSG ***%n   %s%n   *** *** ***%n", s));
        return r.message().isPresent() 
            ? r.withMessage(r.<ByteBuf>message().get().toString(StandardCharsets.UTF_8)) 
            : r.forward();
      };
      Supplier<HttpHandler> h1 = ()-> x-> {
        System.out.println("[SERVER] HttpHandler (h1) >>>");
        x.response().headers().set(CONNECTION, CLOSE);
        x.response().headers().set("x-doxy-custom", "subliminar message");
        //return x.withMessage("Hello from Server!");
        return x.forward();
      };
      Supplier<HttpResponseFilter> r1 = ()-> (c,r)-> {
        System.out.println("[SERVER] HttpResponseFilter (r1) >>>");
        r.message().ifPresent(s->System.out.printf("   *** MSG ***%n   %s%n   *** *** ***%n", s));
        return r.forward();
      };
      CountDownLatch cd = new CountDownLatch(1);
      AutoCloseable auto = ()->cd.countDown();
      Supplier<HttpResponseFilter> r2 = ()-> (c,r)-> {
        try (auto) {
          System.out.println("[SERVER] HttpResponseFilter (r2) >>>");
          return r.message().isPresent() 
              ? r.withMessage(Unpooled.copiedBuffer(r.<String>message().get(), StandardCharsets.UTF_8)) 
              : r.forward();
        }
      };
      HttpServer server = HttpServer.open(cfg);
      server.httpHandlers()
          .addRequestFilter(f1)
          .addRequestFilter(f2)
          .setDefaultRouteHandler(h1)
          .addResponseFilter(r2)
          .addResponseFilter(r1);
      server.bind(Host.of("0.0.0.0:4321"))
          .onComplete(c->System.out.println("* HttpServer1 listening: " + c.localAddress()))
          .start()
          .sync();
      cd.await();
      server.shutdown();
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
