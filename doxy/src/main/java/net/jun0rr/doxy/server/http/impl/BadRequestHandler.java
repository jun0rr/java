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
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpResponse;


/**
 *
 * @author Juno
 */
public class BadRequestHandler implements HttpHandler {

  @Override
  public Optional<HttpExchange> handle(HttpExchange he) throws Exception {
    HttpResponse res = HttpResponse.of(HttpResponseStatus.BAD_REQUEST);
    res.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
    return he.send(res);
  }
  
}