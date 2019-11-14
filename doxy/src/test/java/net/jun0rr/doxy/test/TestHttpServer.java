/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.handler.codec.http.HttpMethod;
import java.util.Optional;
import net.jun0rr.doxy.DoxyConfigBuilder;
import net.jun0rr.doxy.server.HttpInputFilter;
import net.jun0rr.doxy.server.HttpOutputFilter;
import net.jun0rr.doxy.server.HttpRoute;
import net.jun0rr.doxy.server.HttpServer;
import net.jun0rr.doxy.server.impl.DecodeHandler;
import net.jun0rr.doxy.server.impl.EncodeHandler;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestHttpServer {
  @Test
  public void method() {
    HttpServer server = new HttpServer(DoxyConfigBuilder.newBuilder().build());
    HttpInputFilter fi1 = (c,r)->{
      r.headers().set("x-input-filter1", "passed");
      return Optional.of(r);
    };
    HttpInputFilter fi2 = (c,r)->{
      r.headers().set("x-input-filter2", "passed");
      return Optional.of(r);
    };
    HttpOutputFilter fo1 = (c,r)->{
      r.headers().set("x-output-filter1", "passed");
      return Optional.of(r);
    };
    HttpOutputFilter fo2 = (c,r)->{
      r.headers().set("x-output-filter2", "passed");
      return Optional.of(r);
    };
    server.httpHandlers().addInputFilter(()->fi1);
    server.httpHandlers().addInputFilter(()->fi2);
    server.httpHandlers().addOutputFilter(()->fo1);
    server.httpHandlers().addOutputFilter(()->fo2);
    server.httpHandlers().setRouteHandler(HttpRoute.of("\\/encode.*", HttpMethod.GET, HttpMethod.POST), EncodeHandler::new);
    server.httpHandlers().setRouteHandler(HttpRoute.of("\\/decode.*", HttpMethod.GET), DecodeHandler::new);
    server.start();
  }
}
