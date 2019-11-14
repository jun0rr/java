/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
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
import net.jun0rr.doxy.server.HttpHandler;
import net.jun0rr.doxy.server.HttpInputFilter;
import net.jun0rr.doxy.server.HttpOutputFilter;
import net.jun0rr.doxy.server.HttpResponse;
import net.jun0rr.doxy.server.HttpRoute;
import us.pserver.tools.Pair;


/**
 *
 * @author Juno
 */
public class HttpHandlers {
  
  private final Map<HttpRoute, Supplier<HttpHandler>> handlers;
  
  private final List<Supplier<HttpInputFilter>> inputFilters;
  
  private final List<Supplier<HttpOutputFilter>> outputFilters;
  
  private Supplier<HttpHandler> defaultHandler;
  
  private Supplier<Function<Throwable,HttpResponse>> exceptionHandler;
  
  private final DoxyConfig config;
  
  public HttpHandlers(DoxyConfig cfg) {
    this.handlers = new HashMap<>();
    this.inputFilters = new LinkedList<>();
    this.outputFilters = new LinkedList<>();
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyConfig");
    this.defaultHandler = BadRequestHandler::new;
    this.exceptionHandler = ServerErrorFunction::new;
  }
  
  public HttpHandlers setRouteHandler(HttpRoute r, Supplier<HttpHandler> s) {
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
  
  public Stream<Pair<HttpRoute,Supplier<HttpHandler>>> streamRouteHandlers() {
    return handlers.entrySet().stream()
        .map(e->new Pair<>(e.getKey(), e.getValue()));
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
  
  public HttpHandlers setUncaughtExceptionHandler(Supplier<Function<Throwable,HttpResponse>> s) {
    if(s != null) {
      exceptionHandler = s;
    }
    return this;
  }
  
  public Supplier<Function<Throwable,HttpResponse>> getUncaughtExceptionHandler() {
    return exceptionHandler;
  }
  
  public HttpHandlers addInputFilter(Supplier<HttpInputFilter> s) {
    if(s != null) {
      inputFilters.add(s);
    }
    return this;
  }
  
  public List<Supplier<HttpInputFilter>> inputFilters() {
    return inputFilters;
  }
  
  public HttpHandlers addOutputFilter(Supplier<HttpOutputFilter> s) {
    if(s != null) {
      outputFilters.add(s);
    }
    return this;
  }
  
  public List<Supplier<HttpOutputFilter>> outputFilters() {
    return outputFilters;
  }
  
  public ChannelInitializer<SocketChannel> createInitializer() {
    return new ChannelInitializer<>() {
      @Override
      protected void initChannel(SocketChannel c) throws Exception {
        c.pipeline().addLast(
          new HttpServerCodec(),
          new HttpObjectAggregator(1024*1024),
          new DefaultHttpOutputFilter(new HttpHeadersOutputFilter(config), exceptionHandler.get())
        );
        inputFilters.forEach(f->c.pipeline().addLast(new DefaultHttpInputFilter(f.get(), exceptionHandler.get())));
        outputFilters.forEach(f->c.pipeline().addLast(new DefaultHttpOutputFilter(f.get(), exceptionHandler.get())));
        List<RoutableHttpHandler> routables = new LinkedList<>();
        streamRouteHandlers().forEach(p->routables.add(RoutableHttpHandler.of(p.a, p.b.get())));
        HttpRouteHandler root = new HttpRouteHandler(routables, defaultHandler.get());
        c.pipeline().addLast(new DefaultHttpHandler(root, exceptionHandler.get()));
      }
    };
  }
  
}
