/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslHandler;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.jun0rr.doxy.server.http.impl.DefaultHttpHandler;
import net.jun0rr.doxy.server.http.impl.DefaultHttpRequestFilter;
import net.jun0rr.doxy.server.http.impl.DefaultHttpResponseFilter;
import net.jun0rr.doxy.server.http.impl.HttpRouteHandler;
import net.jun0rr.doxy.server.http.impl.RoutableHttpHandler;
import net.jun0rr.doxy.server.http.impl.SSLHandlerFactory;
import net.jun0rr.doxy.server.http.impl.UncaughtExceptionHandler;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class HttpClientHandlers {
  
  private final List<HttpResponseFilter> responseFilters;
  
  private final List<HttpHandler> handlers;
  
  public HttpClientHandlers() {
    this.responseFilters = new LinkedList<>();
    this.handlers = new LinkedList<>();
  }
  
  public HttpClientHandlers addResponseFilter(HttpResponseFilter s) {
    if(s != null) {
      responseFilters.add(s);
    }
    return this;
  }
  
  public List<HttpResponseFilter> responseFilters() {
    return responseFilters;
  }
  
  public HttpClientHandlers addHandler(HttpHandler s) {
    if(s != null) {
      handlers.add(s);
    }
    return this;
  }
  
  public List<HttpHandler> handlers() {
    return handlers;
  }
  
  private SslHandler createSSLHandler(ByteBufAllocator alloc) {
    return Unchecked.call(()->SSLHandlerFactory.newClientHandler(alloc));
  }
  
  protected ChannelInitializer<SocketChannel> createServerInitializer() {
    return new ChannelInitializer<>() {
      @Override
      protected void initChannel(SocketChannel c) throws Exception {
        c.pipeline().addLast(
            createSSLHandler(c.alloc()),
            new HttpClientCodec(),
            new HttpObjectAggregator(1024*1024)
        );
        Function<Throwable,Optional<HttpResponse>> exh = t -> { throw Unchecked.unchecked(t); };
        responseFilters.stream().forEach(f->c.pipeline().addLast(new DefaultHttpResponseFilter(f, exh)));
        handlers.forEach(f->c.pipeline().addLast(new DefaultHttpHandler(f.get())));
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
