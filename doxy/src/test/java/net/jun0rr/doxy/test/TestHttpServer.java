/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.handler.codec.http.HttpMethod;
import java.nio.file.Paths;
import java.util.Optional;
import net.jun0rr.doxy.cfg.DoxyConfig;
import net.jun0rr.doxy.cfg.DoxyConfigBuilder;
import net.jun0rr.doxy.server.ex.DecodeHandler;
import net.jun0rr.doxy.server.ex.EncodeHandler;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpRequestFilter;
import net.jun0rr.doxy.server.http.HttpResponseFilter;
import net.jun0rr.doxy.server.http.HttpRoute;
import net.jun0rr.doxy.server.http.HttpServer;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestHttpServer {
  @Test
  public void method() {
    DoxyConfig cfg = DoxyConfigBuilder.newBuilder()
        //.keystorePath(Paths.get("/home/juno/java/doxy.jks"))
        .keystorePath(Paths.get("d:/java/doxy.jks"))
        .keystorePassword("32132155".toCharArray())
        .build();
    HttpServer server = new HttpServer(cfg);
    HttpRequestFilter fi1 = (c,r)->{
      System.out.println("* HttpRequest: " + r);
      r.headers().set("x-input-filter1", "passed");
      return Optional.of(r);
    };
    HttpRequestFilter fi2 = (c,r)->{
      r.headers().set("x-input-filter2", "passed");
      return Optional.of(r);
    };
    HttpHandler f = e -> {
      System.out.println("FILTER_HANDLER!");
      return Optional.of(e);
    };
    HttpResponseFilter fo1 = (c,r)->{
      //throw new IllegalStateException("ResponseFilter Exception!!");
      r.headers().set("x-output-filter1", "passed");
      return Optional.of(r);
    };
    HttpResponseFilter fo2 = (c,r)->{
      r.headers().set("x-output-filter2", "passed");
      return Optional.of(r);
    };
    server.httpHandlers()
        .addRequestFilter(()->fi1)
        .addRequestFilter(()->fi2)
        .addFilter(()->f)
        .addResponseFilter(()->fo1)
        .addResponseFilter(()->fo2)
        .addRouteHandler(HttpRoute.of("\\/encode.*", HttpMethod.GET, HttpMethod.POST), EncodeHandler::new)
        .addRouteHandler(HttpRoute.of("\\/decode.*", HttpMethod.GET), DecodeHandler::new);
    server.start();
  }
}
