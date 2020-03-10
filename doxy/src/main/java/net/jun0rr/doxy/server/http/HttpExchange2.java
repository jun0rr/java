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
public interface HttpExchange2 extends TcpExchange {
  
  public static final ThreadLocal<HttpExchange2> CURRENT_EXCHANGE = new ThreadLocal<>();
  
  
  
  public HttpRequest request();
  
  public HttpResponse response();
  
  public Optional<HttpExchange2> withRequest(HttpRequest req);
  
  public Optional<HttpExchange2> withResponse(HttpResponse res);
  
  /**
   * Return a new HttpExchange (filled Optional) with the informed message object.
   * @param msg The message object can be either a HttpRequest, HttpResponse or the HttpResponse body.
   * @return Optional filled with a new HttpExchange.
   */
  @Override 
  public Optional<HttpExchange2> withMessage(Object msg);
  
  @Override
  public HttpExchange2 setAttr(String key, Object val);
  
  /**
   * Return this HttpExchange (filled Optional).
   * @return Optional filled with this HttpExchange.
   */
  @Override 
  public Optional<HttpExchange2> forward();
  
  /**
   * Return an empty Optional.
   * @return Empty Optional.
   */
  @Override 
  public Optional<HttpExchange2> empty();
  
  @Override 
  public Optional<HttpExchange2> send();
  
  public Optional<HttpExchange2> send(HttpResponse res);
  
  @Override 
  public Optional<HttpExchange2> send(Object content);
  
  @Override 
  public Optional<HttpExchange2> sendAndClose();
  
  public Optional<HttpExchange2> sendAndClose(HttpResponse res);
  
  @Override 
  public Optional<HttpExchange2> sendAndClose(Object content);
  
  @Override 
  public HttpRequest message();
  
  @Override 
  public Optional<HttpExchange2> close();
  
  @Override 
  public Optional<HttpExchange2> shutdown();
  
  
  
  public static HttpExchange2 of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, HttpRequest req, HttpResponse res, ChannelPromise prms) {
    HttpExchange2 x = new HttpExchangeImpl(channel, connected, ctx, req, res, prms);
    CURRENT_EXCHANGE.set(x);
    return x;
  }
  
  public static HttpExchange2 of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, HttpRequest req) {
    HttpExchange2 x = new HttpExchangeImpl(channel, connected, ctx, req);
    CURRENT_EXCHANGE.set(x);
    return x;
  }
  
  public static HttpExchange2 of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx) {
    HttpExchange2 x = new HttpExchangeImpl(channel, connected, ctx);
    CURRENT_EXCHANGE.set(x);
    return x;
  }
  
  
  
  
  
  public static class HttpExchangeImpl extends TcpExchange.TcpExchangeImpl implements HttpExchange2 {
    
    private final HttpRequest request;
    
    private final HttpResponse response;
    
    public HttpExchangeImpl(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, HttpRequest req, HttpResponse res, ChannelPromise prms) {
      super(channel, connected, ctx, new ConcurrentHashMap<>(), res, prms);
      this.request = Objects.requireNonNull(req, "Bad null HttpRequest");
      this.response = Objects.requireNonNull(res, "Bad null HttpResponse");
    }
    
    public HttpExchangeImpl(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, HttpRequest req) {
      super(channel, connected, ctx, new ConcurrentHashMap<>(), HttpResponse.of(HttpResponseStatus.OK), null);
      this.request = Objects.requireNonNull(req, "Bad null HttpRequest");
      this.response = HttpResponse.of(HttpResponseStatus.OK);
    }
    
    public HttpExchangeImpl(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx) {
      super(channel, connected, ctx, new ConcurrentHashMap<>(), HttpResponse.of(HttpResponseStatus.OK), null);
      this.request = HttpRequest.of(HttpVersion.HTTP_1_1, HttpMethod.GET, "/");
      this.response = HttpResponse.of(HttpResponseStatus.OK);
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
    public Optional<HttpExchange2> withRequest(HttpRequest req) {
      return  Optional.of(of(channel, connected, context, req, response));
    }
    
    @Override
    public Optional<HttpExchange2> withResponse(HttpResponse res) {
      return Optional.of(of(channel, connected, context, request, res));
    }
    
    @Override
    public HttpExchange2 setAttr(String key, Object val) {
      super.setAttr(key, val);
      return this;
    }
    
    private HttpResponse responseWith(Object msg) {
      HttpResponse res = response;
      if(msg instanceof HttpResponse) {
        res = (HttpResponse) msg;
      }
      else if(msg instanceof FullHttpResponse) {
        res = HttpResponse.of((FullHttpResponse)msg);
      }
      else if(msg instanceof CharSequence) {
        ByteBuf buf = Unpooled.copiedBuffer((CharSequence)msg, StandardCharsets.UTF_8);
        res = response.withMessage(buf).get();
      }
      return res;
    }
    
    @Override 
    public Optional<HttpExchange2> send() {
      return send(response);
    }
    
    @Override
    public Optional<HttpExchange2> send(HttpResponse res) {
      super.send(res);
      return empty();
    }
    
    @Override
    public Optional<HttpExchange2> send(Object msg) {
      return send(responseWith(msg));
    }
    
    @Override
    public Optional<HttpExchange2> sendAndClose() {
      return sendAndClose(response);
    }
    
    @Override
    public Optional<HttpExchange2> sendAndClose(HttpResponse res) {
      res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
      context.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
      return empty();
    }
    
    @Override
    public Optional<HttpExchange2> sendAndClose(Object msg) {
      return sendAndClose(responseWith(msg));
    }
    
    @Override
    public Optional<HttpExchange2> empty() {
      return Optional.empty();
    }
    
    @Override
    public HttpRequest message() {
      return request;
    }
    
    @Override
    public Optional<HttpExchange2> withMessage(Object msg) {
      Optional<HttpExchange2> opt = empty();
      if(msg != null) {
        if(msg instanceof HttpRequest) {
          opt = Optional.of(of(channel, connected, context, (HttpRequest)msg, response));
        }
        else if(msg instanceof FullHttpRequest) {
          opt = Optional.of(of(channel, connected, context, HttpRequest.of((FullHttpRequest)msg), response));
        }
        else if(msg instanceof HttpResponse) {
          opt = Optional.of(of(channel, connected, context, request, (HttpResponse)msg));
        }
        else if(msg instanceof FullHttpResponse) {
          opt = Optional.of(of(channel, connected, context, request, HttpResponse.of((FullHttpResponse)msg)));
        }
        else {
          opt = Optional.of(of(channel, connected, context, request, responseWith(msg)));
        }
      }
      return opt;
    }
    
    @Override
    public Optional<HttpExchange2> forward() {
      return Optional.of(this);
    }
    
    @Override
    public Optional<HttpExchange2> close() {
      super.close();
      return empty();
    }
    
    @Override
    public Optional<HttpExchange2> shutdown() {
      super.shutdown();
      return empty();
    }

  }
  
}
