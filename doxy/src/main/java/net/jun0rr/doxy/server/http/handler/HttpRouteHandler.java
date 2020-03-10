/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.handler;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpResponse;
import net.jun0rr.doxy.server.http.HttpRoute;


/**
 *
 * @author Juno
 */
public class HttpRouteHandler implements HttpHandler {
  
  private final List<RoutableHttpHandler> handlers;
  
  private final HttpHandler defaultHandler;
  
  public HttpRouteHandler(HttpHandler defaultHandler, Collection<RoutableHttpHandler> handlers) {
    this.handlers = List.copyOf(Objects.requireNonNull(handlers, "Bad null RoutableHttpHandler Collection"));
    this.defaultHandler = Objects.requireNonNull(defaultHandler, "Bad null default HttpHandler");
  }
  
  public HttpRouteHandler(Collection<RoutableHttpHandler> handlers) {
    this(BAD_REQUEST, handlers);
  }
  
  private HttpHandler getHandler(HttpRoute r) {
    return handlers.stream()
        .filter(h->h.match(r))
        .map(h->(HttpHandler)h)
        .findFirst()
        .orElse(defaultHandler);
  }
  
  @Override
  public Optional<? extends HttpExchange> apply(HttpExchange he) throws Exception {
    return getHandler(HttpRoute.of(he.request())).apply(he);
  }
  
  @Override
  public String toString() {
    return "HttpRouteHandler{" + "handlers=" + handlers + ", defaultHandler=" + defaultHandler + '}';
  }
  
}
