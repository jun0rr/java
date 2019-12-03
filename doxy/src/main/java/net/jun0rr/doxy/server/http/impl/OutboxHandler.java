/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Optional;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpResponse;


/**
 *
 * @author Juno
 */
public class OutboxHandler implements HttpHandler {
  
  private final DoxyEnvironment env;
  
  public OutboxHandler(DoxyEnvironment env) {
    this.env = env;
  }

  @Override
  public Optional<HttpExchange> handle(HttpExchange he) throws Exception {
    Optional<Packet> opt = he.request().body();
    HttpResponse res;
    if(opt.isEmpty()) {
      res = HttpResponse.of(he.response().protocolVersion(), HttpResponseStatus.BAD_REQUEST, he.response().headers());
      XErrorHeaders xe = new XErrorHeaders();
      xe.setErrorType("BadRequest")
          .setErrorTrace(getClass().getName() + ".handle(HttpExchange):30")
          .setErrorMessage("Body Request Missing")
          .toHeaders(res.headers());
    }
    else {
      env.outbox().offerLast(opt.get());
      res = HttpResponse.of(he.response().protocolVersion(), HttpResponseStatus.OK, he.response().headers());
      res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
    }
    return he.send(res);
  }
  
}
