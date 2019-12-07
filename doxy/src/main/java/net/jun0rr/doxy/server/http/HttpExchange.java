/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;


/**
 *
 * @author Juno
 */
public interface HttpExchange {
  
  public HttpRequest request();
  
  public HttpResponse response();
  
  public ChannelHandlerContext context();
  
  public Map<String, Object> attributes();
  
  public Optional<HttpExchange> withRequest(HttpRequest req);
  
  public Optional<HttpExchange> withResponse(HttpResponse res);
  
  public Optional<HttpExchange> send();
  
  public Optional<HttpExchange> send(HttpResponse res);
  
  public Optional<HttpExchange> send(Object content);
  
  
  
  public static HttpExchange of(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
    return new HttpExchangeImpl(ctx, req, res);
  }
  
  public static HttpExchange of(ChannelHandlerContext ctx, HttpRequest req) {
    return new HttpExchangeImpl(ctx, req);
  }
  
  
  
  
  
  public static class HttpExchangeImpl implements HttpExchange {
    
    private final HttpRequest request;
    
    private final HttpResponse response;
    
    private final ChannelHandlerContext context;
    
    private final Map<String,Object> attributes;
    
    public HttpExchangeImpl(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
      this.context = Objects.requireNonNull(ctx, "Bad null ChannelHandlerContext");
      this.request = Objects.requireNonNull(req, "Bad null HttpRequest");
      this.response = Objects.requireNonNull(res, "Bad null HttpResponse");
      this.attributes = new TreeMap<>();
    }
    
    public HttpExchangeImpl(ChannelHandlerContext ctx, HttpRequest req) {
      this(ctx, req, HttpResponse.of(HttpResponseStatus.OK));
    }
    
    @Override
    public HttpRequest request() {
      return request;
    }
    
    @Override
    public HttpResponse response() {
      return response;
    }
    
    @Override
    public ChannelHandlerContext context() {
      return context;
    }
    
    @Override
    public Map<String, Object> attributes() {
      return attributes;
    }
    
    @Override
    public Optional<HttpExchange> withRequest(HttpRequest req) {
      return  Optional.of(new HttpExchangeImpl(context, req, response));
    }
    
    @Override
    public Optional<HttpExchange> withResponse(HttpResponse res) {
      return Optional.of(new HttpExchangeImpl(context, request, res));
    }
    
    @Override
    public Optional<HttpExchange> send() {
      return send(response);
    }
    
    @Override
    public Optional<HttpExchange> send(HttpResponse res) {
      ChannelFutureListener closeAndRelease = f -> {
        f.channel().close();
        res.<ByteBuf>content().ifPresent(ByteBuf::release);
      };
      context.writeAndFlush(res.toNettyResponse()).addListener(closeAndRelease);
      return Optional.empty();
    }
    
    @Override
    public Optional<HttpExchange> send(Object content) {
      return send(HttpResponse.of(
          response.protocolVersion(), 
          response.status(), 
          response.headers(), 
          content)
      );
    }
    
  }
  
}
