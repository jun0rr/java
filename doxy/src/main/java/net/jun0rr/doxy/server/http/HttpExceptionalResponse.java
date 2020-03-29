/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Objects;
import net.jun0rr.doxy.server.http.HttpResponse;


/**
 *
 * @author Juno
 */
public class HttpExceptionalResponse extends HttpResponse.HttpResponseImpl {
  
  public HttpExceptionalResponse(HttpResponseStatus s, Throwable t) {
    super(s);
    Objects.requireNonNull(t, "Bad null Throwable");
    headers().set("x-error-type", t.getClass());
    if(t.getMessage() != null && !t.getMessage().isBlank()) {
      headers().set("x-error-message", t.getMessage());
    }
    if(t.getCause() != null) {
      headers().set("x-error-cause", Objects.toString(t.getCause()));
    }
    StackTraceElement[] elts = t.getStackTrace();
    for(int i = 0; i < Math.min(3, elts.length); i++) {
      headers().set("x-error-trace-" + i, elts[i]);
    }
    headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
  }
  
  public static HttpResponse of(HttpResponseStatus s, Throwable t) {
    return new HttpExceptionalResponse(s, t);
  }
  
}
