/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import us.pserver.tools.LazyFinal;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class HttpRouteHandler extends AbstractHttpRequestHandler {
  
  private final List<RoutableHttpRequestHandler> handlers;
  
  private final LazyFinal<RoutableHttpRequestHandler> current;
  
  private final DateTimeFormatter datefmt;
  
  public HttpRouteHandler() {
    this.handlers = new ArrayList<>();
    this.current = new LazyFinal<>();
    datefmt = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz").withZone(ZoneId.of("GMT"));
  }
  
  public HttpRouteHandler addHandler(RoutableHttpRequestHandler h) {
    if(h != null) {
      handlers.add(h);
    }
    return this;
  }
  
  public List<RoutableHttpRequestHandler> handlers() {
    return handlers;
  }
  
  public Optional<RoutableHttpRequestHandler> getHandler(HttpRoute r) {
    return handlers.stream().filter(h->h.matchRoute(r)).findFirst();
  }
  
  @Override
  public Optional<HttpRequest<?>> httpRequest(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
    Optional<RoutableHttpRequestHandler> hnd = getHandler(HttpRoute.of(req));
    if(hnd.isPresent()) {
      this.current.init(hnd.get());
      System.out.println("-> ROUTE FOUND: " + hnd.get());
      return current.get().httpRequest(ctx, req);
    }
    else {
      HttpResponse res = HttpResponse.of(HttpResponseStatus.BAD_REQUEST);
      res.headers().add(HttpHeaderNames.CONNECTION, "close");
      res.headers().addInt(HttpHeaderNames.CONTENT_LENGTH, 0);
      res.headers().add(HttpHeaderNames.SERVER, "Doxy-0.1-SNAPSHOT");
      res.headers().add(HttpHeaderNames.DATE, datefmt.format(Instant.now()));
      writeAndClose(ctx, res);
      return Optional.empty();
    }
  }
  
  @Override 
  public void exceptionCaught(ChannelHandlerContext chc, Throwable thrwbl) throws Exception {
    current.ifInitialized(h->Unchecked.call(()->h.exceptionCaught(chc, thrwbl)));
  }
  
  @Override
  public void channelReadComplete(ChannelHandlerContext chc) throws Exception {
    current.ifInitialized(h->{
      try {
        h.channelReadComplete(chc);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
  @Override 
  public void channelRegistered(ChannelHandlerContext chc) throws Exception {
    current.ifInitialized(h->{
      try {
        h.channelRegistered(chc);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
  @Override 
  public void channelUnregistered(ChannelHandlerContext chc) throws Exception {
    current.ifInitialized(h->{
      try {
        h.channelUnregistered(chc);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
  @Override 
  public void channelActive(ChannelHandlerContext chc) throws Exception {
    current.ifInitialized(h->{
      try {
        h.channelActive(chc);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
  @Override 
  public void channelInactive(ChannelHandlerContext chc) throws Exception {
    current.ifInitialized(h->{
      try {
        h.channelInactive(chc);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
  @Override 
  public void userEventTriggered(ChannelHandlerContext chc, Object o) throws Exception {
    current.ifInitialized(h->{
      try {
        h.userEventTriggered(chc, o);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
  @Override 
  public void channelWritabilityChanged(ChannelHandlerContext chc) throws Exception {
    current.ifInitialized(h->{
      try {
        h.channelWritabilityChanged(chc);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
  @Override 
  public void handlerAdded(ChannelHandlerContext chc) throws Exception {
    current.ifInitialized(h->{
      try {
        h.handlerAdded(chc);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
  @Override 
  public void handlerRemoved(ChannelHandlerContext chc) throws Exception {
    current.ifInitialized(h->{
      try {
        h.handlerRemoved(chc);
      }
      catch(Exception e) {
        Unchecked.call(()->h.exceptionCaught(chc, e));
      }
    });
  }
  
}
