/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import java.util.Objects;


/**
 *
 * @author Juno
 */
public abstract class AbstractRoutableHttpRequestHandler extends AbstractHttpRequestHandler implements RoutableHttpRequestHandler {
  
  protected final HttpRoute route;
  
  public AbstractRoutableHttpRequestHandler(HttpRoute r) {
    this.route = Objects.requireNonNull(r, "Bad null HttpRoute");
  }
  
  @Override
  public boolean matchRoute(HttpRoute r) {
    return route.match(r);
  }
  
}
