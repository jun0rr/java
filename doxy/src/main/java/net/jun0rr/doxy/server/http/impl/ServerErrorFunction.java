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
import java.util.function.Function;
import net.jun0rr.doxy.server.http.HttpResponse;


/**
 *
 * @author Juno
 */
public class ServerErrorFunction implements Function<Throwable,Optional<HttpResponse>> {
  
  @Override
  public Optional<HttpResponse> apply(Throwable th) {
    th.printStackTrace();
    HttpResponse res = HttpResponse.of(HttpResponseStatus.INTERNAL_SERVER_ERROR);
    new XErrorHeaders().setError(th).toHeaders(res.headers());
    res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
    return Optional.of(res);
  }

}
