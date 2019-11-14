/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.handler.codec.http.HttpMethod;
import java.security.Security;
import java.util.Optional;
import net.jun0rr.doxy.DoxyConfigBuilder;
import net.jun0rr.doxy.server.HttpHandler;
import net.jun0rr.doxy.server.HttpRoute;
import net.jun0rr.doxy.server.HttpServer;
import net.jun0rr.doxy.server.impl.DecodeHandler;
import net.jun0rr.doxy.server.impl.EncodeHandler;
import org.junit.jupiter.api.Test;
import net.jun0rr.doxy.server.HttpRequestFilter;
import net.jun0rr.doxy.server.HttpResponseFilter;


/**
 *
 * @author Juno
 */
public class TestHttpServer {
  @Test
  public void method() {
    System.out.println("ssl.KeyManagerFactory.algorithm: " + Security.getProperty("ssl.KeyManagerFactory.algorithm"));
    HttpServer server = new HttpServer(DoxyConfigBuilder.newBuilder().build());
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
