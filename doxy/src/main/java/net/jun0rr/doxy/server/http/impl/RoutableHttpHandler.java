/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import java.util.Objects;
import java.util.Optional;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpRoute;
import net.jun0rr.doxy.server.http.Routable;


/**
 *
 * @author juno
 */
public interface RoutableHttpHandler extends HttpHandler, Routable {
  
  public static RoutableHttpHandler of(HttpRoute r, HttpHandler h) {
    return new RoutableHttpHandlerImpl(r, h);
  }
  
  
  
  
  
  public static class RoutableHttpHandlerImpl implements RoutableHttpHandler {
    
    private final HttpRoute route;
    
    private final HttpHandler handler;
    
    public RoutableHttpHandlerImpl(HttpRoute hrt, HttpHandler hnd) {
      this.route = Objects.requireNonNull(hrt, "Bad null HttpRoute");
      this.handler = Objects.requireNonNull(hnd, "Bad null HttpHandler");
    }
    
    @Override
    public Optional<HttpExchange> handle(HttpExchange he) throws Exception {
      return handler.handle(he);
    }
    
    @Override
    public boolean match(HttpRoute r) {
      return route.match(r);
    }
    
    @Override
    public int hashCode() {
      int hash = 7;
      hash = 17 * hash + Objects.hashCode(this.route);
      hash = 17 * hash + Objects.hashCode(this.handler);
      return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (RoutableHttpHandler.class.isAssignableFrom(obj.getClass())) {
        return false;
      }
      final RoutableHttpHandler other = (RoutableHttpHandler) obj;
      return other.match(route);
    }
    
  }
  
}
