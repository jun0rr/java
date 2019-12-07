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
import net.jun0rr.doxy.common.DoxyChannel;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpResponse;


/**
 *
 * @author Juno
 */
public class CloseHandler implements HttpHandler {

  private final DoxyEnvironment env;
  
  public CloseHandler(DoxyEnvironment env) {
    this.env = env;
  }

  @Override
  public Optional<HttpExchange> handle(HttpExchange he) throws Exception {
    Optional<Packet> body = he.request().body();
    body.ifPresent(p->env.getChannelById(p.channelID()).ifPresent(DoxyChannel::close));
    HttpResponse res = HttpResponse.of(he.request().protocolVersion(), HttpResponseStatus.OK, he.response().headers());
    res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
    return he.send(res);
  }
  
}
