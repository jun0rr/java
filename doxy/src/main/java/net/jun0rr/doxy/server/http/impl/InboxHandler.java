/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.impl.PacketCollection;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpResponse;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class InboxHandler implements HttpHandler {

  private final DoxyEnvironment env;
  
  public InboxHandler(DoxyEnvironment env) {
    this.env = env;
  }

  @Override
  public Optional<HttpExchange> handle(HttpExchange he) throws Exception {
    env.executor().submit(longPollHandler(he));
    return Optional.empty();
  }
  
  private Runnable longPollHandler(HttpExchange he) {
    return () -> {
      Optional<Packet> opt = Optional.ofNullable(Unchecked.call(()->env.inbox().pollFirst(env.configuration().getServerTimeout(), TimeUnit.MILLISECONDS)));
      HttpResponse res;
      if(opt.isEmpty()) {
        res = HttpResponse.of(he.request().protocolVersion(), HttpResponseStatus.NO_CONTENT, he.response().headers());
      }
      else {
        PacketCollection col = new PacketCollection();
        col.add(opt.get());
        int size = env.inbox().size();
        env.inbox().stream().limit(size).forEach(col::add);
        ByteBuf buf = Unpooled.copiedBuffer(col.toByteBuffer());
        res = HttpResponse.of(he.request().protocolVersion(), HttpResponseStatus.OK, he.response().headers(), buf);
      }
      he.send(res);
    };
  }
  
}
