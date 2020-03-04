/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.jun0rr.doxy.tcp.TcpChannel;
import net.jun0rr.doxy.tcp.TcpExchange;


/**
 *
 * @author Juno
 */
public interface HttpExchange extends TcpExchange {
  
  public HttpRequest request();
  
  public HttpResponse response();
  
  public Optional<HttpExchange> withRequest(HttpRequest req);
  
  public Optional<HttpExchange> withResponse(HttpResponse res);
  
  /**
   * Return a new HttpExchange (filled Optional) with the informed message object.
   * @param msg The message object can be either a HttpRequest, HttpResponse or the HttpResponse message.
   * @return Optional filled with a new HttpExchange.
   */
  @Override public Optional<HttpExchange> withMessage(Object msg);
  
  /**
   * Return this HttpExchange (filled Optional).
   * @return Optional filled with this HttpExchange.
   */
  @Override public Optional<HttpExchange> forward();
  
  /**
   * Return an empty Optional.
   * @return Empty Optional.
   */
  @Override public Optional<HttpExchange> empty();
  
  @Override public Optional<? extends HttpExchange> send();
  
  public Optional<HttpExchange> send(HttpResponse res);
  
  @Override public Optional<HttpExchange> send(Object content);
  
  @Override public Optional<HttpExchange> sendAndClose();
  
  public Optional<HttpExchange> sendAndClose(HttpResponse res);
  
  @Override public Optional<HttpExchange> sendAndClose(Object content);
  
  @Override public HttpRequest message();
  
  @Override public Optional<HttpExchange> close();
  
  @Override public Optional<HttpExchange> shutdown();
  
  
  
  public static HttpExchange of(TcpChannel channel, ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
    return new Impl(channel, ctx, req, res);
  }
  
  public static HttpExchange of(TcpChannel channel, ChannelHandlerContext ctx, HttpRequest req) {
    return new Impl(channel, ctx, req);
  }
  
  
  
  
  
  public static class Impl extends TcpExchange.TcpExchangeImpl implements HttpExchange {
    
    private final HttpRequest request;
    
    private final HttpResponse response;
    
    public Impl(TcpChannel channel, ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
      super(channel, ctx, new ConcurrentHashMap<>(), Optional.of(res));
      this.request = Objects.requireNonNull(req, "Bad null HttpRequest");
      this.response = Objects.requireNonNull(res, "Bad null HttpResponse");
    }
    
    public Impl(TcpChannel channel, ChannelHandlerContext ctx, HttpRequest req) {
      super(channel, ctx, new ConcurrentHashMap<>(), Optional.of(HttpResponse.of(HttpResponseStatus.OK)));
      this.request = Objects.requireNonNull(req, "Bad null HttpRequest");
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
    public Optional<HttpExchange> withRequest(HttpRequest req) {
      return  Optional.of(of(channel, context, req, response));
    }
    
    @Override
    public Optional<HttpExchange> withResponse(HttpResponse res) {
      return Optional.of(of(channel, context, request, res));
    }
    
    @Override 
    public Optional<HttpExchange> send() {
      return send(response);
    }
    
    @Override
    public Optional<HttpExchange> send(HttpResponse res) {
      super.send(res);
      return empty();
    }
    
    @Override
    public Optional<HttpExchange> send(Object msg) {
      return send(HttpResponse.of(
          response.protocolVersion(), 
          response.status(), 
          response.headers(), 
          msg)
      );
    }
    
    @Override
    public Optional<HttpExchange> sendAndClose() {
      return sendAndClose(response);
    }
    
    @Override
    public Optional<HttpExchange> sendAndClose(HttpResponse res) {
      res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
      context.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
      return empty();
    }
    
    @Override
    public Optional<HttpExchange> sendAndClose(Object msg) {
      return send(response.withMessage(msg).get());
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
    public Optional<HttpExchange> withMessage(Object msg) {
      Optional<HttpExchange> opt = empty();
      if(msg != null) {
        if(msg instanceof HttpRequest) {
          opt = Optional.of(of(channel, context, (HttpRequest)msg, response));
        }
        else if(msg instanceof HttpResponse) {
          opt = Optional.of(of(channel, context, request, (HttpResponse)msg));
        }
        else {
          opt = Optional.of(of(channel, context, request, response.withMessage(msg).get()));
        }
      }
      return opt;
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
