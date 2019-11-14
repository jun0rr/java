/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.impl;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.function.Function;
import net.jun0rr.doxy.server.HttpResponse;


/**
 *
 * @author Juno
 */
public class ServerErrorFunction implements Function<Throwable,HttpResponse> {
  
  @Override
  public HttpResponse apply(Throwable th) {
    th.printStackTrace();
    HttpResponse res = HttpResponse.of(HttpResponseStatus.INTERNAL_SERVER_ERROR);
    res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
    res.headers().set("x-error-type", th.getClass().getName());
    res.headers().set("x-error-message", th.getMessage());
    if(th.getCause() != null) {
      res.headers().set("x-error-cause", th.getCause());
    }
    return res;
  }

}
