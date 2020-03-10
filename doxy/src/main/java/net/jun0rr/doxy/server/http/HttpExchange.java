/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.jun0rr.doxy.tcp.ConnectedTcpChannel;
import net.jun0rr.doxy.tcp.TcpChannel;
import net.jun0rr.doxy.tcp.TcpExchange;


/**
 *
 * @author Juno
 */
public interface HttpExchange extends TcpExchange {
  
  public static final ThreadLocal<HttpExchange> CURRENT_EXCHANGE = new ThreadLocal<>();
  
  
  
  public HttpRequest request();
  
  public HttpResponse response();
  
  public HttpExchange withRequest(HttpRequest req);
  
  public HttpExchange withResponse(HttpResponse res);
  
  /**
   * Return a new HttpExchange (filled Optional) with the informed message object.
   * @param msg The message object can be either a HttpRequest, HttpResponse or the HttpResponse body.
   * @return Optional filled with a new HttpExchange.
   */
  @Override 
  public HttpExchange withMessage(Object msg);
  
  @Override
  public HttpExchange withPromise(ChannelPromise prms);
  
  @Override
  public HttpExchange setAttr(String key, Object val);
  
  /**
   * Return this HttpExchange (filled Optional).
   * @return Optional filled with this HttpExchange.
   */
  @Override 
  public Optional<HttpExchange> forward();
  
  /**
   * Return an empty Optional.
   * @return Empty Optional.
   */
  @Override 
  public Optional<HttpExchange> empty();
  
  @Override 
  public HttpRequest message();
  
  
  
  public static HttpExchange of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
    return new HttpExchangeImpl(channel, connected, ctx, req, res);
  }
  
  public static HttpExchange of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, HttpRequest req) {
    return new HttpExchangeImpl(channel, connected, ctx, req);
  }
  
  public static HttpExchange of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx) {
    return  new HttpExchangeImpl(channel, connected, ctx);
  }
  
  
  
  
  
  public static class HttpExchangeImpl extends TcpExchange.TcpExchangeImpl implements HttpExchange {
    
    private final HttpRequest request;
    
    private final HttpResponse response;
    
    public HttpExchangeImpl(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
      super(channel, connected, ctx, new ConcurrentHashMap<>(), req);
      this.request = Objects.requireNonNull(req, "Bad null HttpRequest");
      this.response = Objects.requireNonNull(res, "Bad null HttpResponse");
      CURRENT_EXCHANGE.set(this);
    }
    
    public HttpExchangeImpl(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, HttpRequest req) {
      super(channel, connected, ctx, new ConcurrentHashMap<>(), req);
      this.request = Objects.requireNonNull(req, "Bad null HttpRequest");
      this.response = HttpResponse.of(HttpResponseStatus.OK);
      CURRENT_EXCHANGE.set(this);
    }
    
    public HttpExchangeImpl(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx) {
      super(channel, connected, ctx, new ConcurrentHashMap<>(), HttpRequest.of(HttpVersion.HTTP_1_1, HttpMethod.GET, "/"));
      this.request = this.message();
      this.response = HttpResponse.of(HttpResponseStatus.OK);
      CURRENT_EXCHANGE.set(this);
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
    public HttpExchange withRequest(HttpRequest req) {
      request.dispose();
      return new HttpExchangeImpl(channel, connected, context, req, response);
    }
    
    @Override
    public HttpExchange withResponse(HttpResponse res) {
      response.dispose();
      return new HttpExchangeImpl(channel, connected, context, request, res);
    }
    
    @Override
    public HttpExchange setAttr(String key, Object val) {
      super.setAttr(key, val);
      return this;
    }
    
    private HttpResponse responseWith(Object msg) {
      HttpResponse res = response;
      if(msg instanceof CharSequence) {
        ByteBuf buf = Unpooled.copiedBuffer((CharSequence)msg, StandardCharsets.UTF_8);
        res = response.withMessage(buf);
      }
      return res;
    }
    
    @Override
    public Optional<HttpExchange> empty() {
      return Optional.empty();
    }
    
    @Override
    public HttpRequest message() {
      return request;
    }
    
    @Override
    public HttpExchange withMessage(Object msg) {
      HttpExchange ex = this;
      if(msg != null) {
        if(msg instanceof HttpRequest) {
          ex = withRequest((HttpRequest)msg);
        }
        else if(msg instanceof FullHttpRequest) {
          ex = withRequest(HttpRequest.of((FullHttpRequest)msg));
        }
        else if(msg instanceof HttpResponse) {
          ex = withResponse((HttpResponse)msg);
        }
        else if(msg instanceof FullHttpResponse) {
          ex = withResponse(HttpResponse.of((FullHttpResponse)msg));
        }
        else {
          ex = new HttpExchangeImpl(channel, connected, context, request, responseWith(msg));
        }
      }
      return ex;
    }
    
    @Override
    public Optional<HttpExchange> forward() {
      return Optional.of(this);
    }
    
    @Override
    public Optional<HttpExchange> close() {
      super.close();
      return empty();
    }
    
    @Override
    public Optional<HttpExchange> shutdown() {
      super.shutdown();
      return empty();
    }

  }
  
}
