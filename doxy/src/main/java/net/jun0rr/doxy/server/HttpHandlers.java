/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.jun0rr.doxy.DoxyConfig;
import net.jun0rr.doxy.server.impl.BadRequestHandler;
import net.jun0rr.doxy.server.impl.DefaultHttpFilter;
import net.jun0rr.doxy.server.impl.DefaultHttpHandler;
import net.jun0rr.doxy.server.impl.DefaultHttpRequestFilter;
import net.jun0rr.doxy.server.impl.DefaultHttpResponseFilter;
import net.jun0rr.doxy.server.impl.HttpResponseHeadersFilter;
import net.jun0rr.doxy.server.impl.HttpRouteHandler;
import net.jun0rr.doxy.server.impl.RoutableHttpHandler;
import net.jun0rr.doxy.server.impl.ServerErrorFunction;
import net.jun0rr.doxy.server.impl.UncaughtExceptionHandler;


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
  
  private Supplier<Function<Throwable,Optional<HttpResponse>>> exceptionHandler;
  
  private final DoxyConfig config;
  
  public HttpHandlers(DoxyConfig cfg) {
    this.handlers = new HashMap<>();
    this.requestFilters = new LinkedList<>();
    this.responseFilters = new LinkedList<>();
    this.exchangeFilters = new LinkedList<>();
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyConfig");
    this.defaultHandler = BadRequestHandler::new;
    this.exceptionHandler = ServerErrorFunction::new;
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
  
  public HttpHandlers setUncaughtExceptionHandler(Supplier<Function<Throwable,Optional<HttpResponse>>> s) {
    if(s != null) {
      exceptionHandler = s;
    }
    return this;
  }
  
  public Supplier<Function<Throwable,Optional<HttpResponse>>> getUncaughtExceptionHandler() {
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
  
  private boolean isSSLKeystoreSetted() {
    return config.getSSLKeystorePath() != null
        && Files.exists(config.getSSLKeystorePath());
  }
  
  private SslHandler createSSLHandler() {
    return null;
  }
  
  public ChannelInitializer<SocketChannel> createInitializer() {
    return new ChannelInitializer<>() {
      @Override
      protected void initChannel(SocketChannel c) throws Exception {
        c.pipeline().addLast(new HttpServerCodec(),
          new HttpObjectAggregator(1024*1024),
          new DefaultHttpResponseFilter(new HttpResponseHeadersFilter(config))
        );
        responseFilters.forEach(f->c.pipeline().addLast(new DefaultHttpResponseFilter(f.get())));
        requestFilters.forEach(f->c.pipeline().addLast(new DefaultHttpRequestFilter(f.get())));
        exchangeFilters.forEach(f->c.pipeline().addLast(new DefaultHttpFilter(f.get())));
        List<RoutableHttpHandler> routables = new LinkedList<>();
        streamRouteHandlers().forEach(e->routables.add(
            RoutableHttpHandler.of(e.getKey(), e.getValue().get()))
        );
        HttpRouteHandler root = new HttpRouteHandler(routables, defaultHandler.get());
        c.pipeline().addLast(new DefaultHttpHandler(root));
        c.pipeline().addLast(new UncaughtExceptionHandler(exceptionHandler.get()));
      }
    };
  }
  
}
