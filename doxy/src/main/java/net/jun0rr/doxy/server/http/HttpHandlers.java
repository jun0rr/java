/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.jun0rr.doxy.server.http.impl.BadRequestHandler;
import net.jun0rr.doxy.server.http.impl.DefaultHttpHandler;
import net.jun0rr.doxy.server.http.impl.DefaultHttpRequestFilter;
import net.jun0rr.doxy.server.http.impl.DefaultHttpResponseFilter;
import net.jun0rr.doxy.server.http.impl.HttpResponseHeadersFilter;
import net.jun0rr.doxy.server.http.impl.HttpResponseWriter;
import net.jun0rr.doxy.server.http.impl.HttpRouteHandler;
import net.jun0rr.doxy.server.http.impl.RoutableHttpHandler;
import net.jun0rr.doxy.server.http.impl.SSLHandlerFactory;
import net.jun0rr.doxy.server.http.impl.ServerErrorFunction;
import net.jun0rr.doxy.server.http.impl.UncaughtExceptionHandler;
import net.jun0rr.doxy.tcp.InboundHandler;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class HttpHandlers {
  
  private final Map<HttpRoute, Supplier<HttpHandler>> handlers;
  
  private final List<Supplier<HttpRequestFilter>> requestFilters;
  
  private final List<Supplier<HttpResponseFilter>> responseFilters;
  
  private final List<Supplier<HttpHandler>> exchangeFilters;
  
  private Supplier<HttpHandler> defaultHandler;
  
  private Function<Throwable,Optional<HttpResponse>> exceptionHandler;
  
  private final HttpServerConfig config;
  
  public HttpHandlers(HttpServerConfig cfg) {
    this.handlers = new HashMap<>();
    this.requestFilters = new LinkedList<>();
    this.responseFilters = new LinkedList<>();
    this.exchangeFilters = new LinkedList<>();
    this.config = Objects.requireNonNull(cfg, "Bad null HttpServerConfig");
    this.defaultHandler = BadRequestHandler::new;
    this.exceptionHandler = new ServerErrorFunction();
  }
  
  public HttpHandlers addRouteHandler(HttpRoute r, Supplier<HttpHandler> s) {
    if(r != null && s != null) {
      handlers.put(r, s);
    }
    return this;
  }
  
  public Optional<Supplier<HttpHandler>> getRouteHandler(HttpRoute r) {
    return Optional.ofNullable(handlers.get(r));
  }
  
  public Optional<Supplier<HttpHandler>> removeRouteHandler(HttpRoute r) {
    return Optional.ofNullable(handlers.remove(r));
  }
  
  public Stream<Map.Entry<HttpRoute,Supplier<HttpHandler>>> streamRouteHandlers() {
    return handlers.entrySet().stream();
  }
  
  public HttpHandlers setDefaultRouteHandler(Supplier<HttpHandler> s) {
    if(s != null) {
      defaultHandler = s;
    }
    return this;
  }
  
  public Supplier<HttpHandler> getDefaultRouteHandler() {
    return defaultHandler;
  }
  
  public HttpHandlers setUncaughtExceptionHandler(Function<Throwable,Optional<HttpResponse>> s) {
    if(s != null) {
      exceptionHandler = s;
    }
    return this;
  }
  
  public Function<Throwable,Optional<HttpResponse>> getUncaughtExceptionHandler() {
    return exceptionHandler;
  }
  
  public HttpHandlers addRequestFilter(Supplier<HttpRequestFilter> s) {
    if(s != null) {
      requestFilters.add(s);
    }
    return this;
  }
  
  public List<Supplier<HttpRequestFilter>> requestFilters() {
    return requestFilters;
  }
  
  public HttpHandlers addResponseFilter(Supplier<HttpResponseFilter> s) {
    if(s != null) {
      responseFilters.add(s);
    }
    return this;
  }
  
  public List<Supplier<HttpResponseFilter>> responseFilters() {
    return responseFilters;
  }
  
  public HttpHandlers addFilter(Supplier<HttpHandler> s) {
    if(s != null) {
      exchangeFilters.add(s);
    }
    return this;
  }
  
  public List<Supplier<HttpHandler>> filters() {
    return exchangeFilters;
  }
  
  private SslHandler createSSLHandler(ByteBufAllocator alloc) {
    return Unchecked.call(()->SSLHandlerFactory.newServerHandler2(config, alloc));
  }
  
  protected ChannelInitializer<SocketChannel> createServerInitializer() {
    return new ChannelInitializer<>() {
      @Override
      protected void initChannel(SocketChannel c) throws Exception {
        c.pipeline().addLast(
            createSSLHandler(c.alloc()),
            new HttpServerCodec(),
            new HttpObjectAggregator(1024*1024),
            new HttpResponseWriter(),
            new DefaultHttpResponseFilter(new HttpResponseHeadersFilter(config))
        );
        responseFilters.stream().forEach(f->c.pipeline().addLast(new DefaultHttpResponseFilter(f.get(), exceptionHandler)));
        requestFilters.forEach(f->c.pipeline().addLast(new DefaultHttpRequestFilter(f.get())));
        exchangeFilters.forEach(f->c.pipeline().addLast(new DefaultHttpHandler(f.get())));
        List<RoutableHttpHandler> routables = new LinkedList<>();
        streamRouteHandlers().forEach(e->routables.add(
            RoutableHttpHandler.of(e.getKey(), e.getValue().get()))
        );
        c.pipeline().addLast(new DefaultHttpHandler(new HttpRouteHandler(routables, defaultHandler.get())));
        c.pipeline().addLast(new UncaughtExceptionHandler(exceptionHandler));
      }
    };
  }
  
}
