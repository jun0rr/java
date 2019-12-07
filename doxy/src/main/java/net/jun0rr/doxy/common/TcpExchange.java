/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;


/**
 *
 * @author Juno
 */
public interface TcpExchange {
  
  public TcpServer server();
  
  public ChannelHandlerContext context();
  
  public Map<String,Object> attributes();
  
  public TcpExchange setAttribute(String key, Object val);
  
  public Optional<Object> message();
  
  public Optional<TcpExchange> send(Object msg);
  
  public Optional<TcpExchange> send();
  
  public Optional<TcpExchange> withMessage(Object msg);
  
  public Optional<TcpExchange> noMessage();
  
  
  
  public static TcpExchange of(TcpServer srv, ChannelHandlerContext ctx, Object msg) {
    return new TcpExchangeImpl(srv, ctx, new TreeMap<>(), Optional.of(msg));
  }
  
  
  
  
  
  static class TcpExchangeImpl implements TcpExchange {
    
    private final TcpServer server;
    
    private final ChannelHandlerContext context;
    
    private final Map<String,Object> attributes;
    
    private final Optional<Object> message;
    
    public TcpExchangeImpl(TcpServer srv, ChannelHandlerContext ctx, Map<String,Object> attrs, Optional<Object> msg) {
      this.server = srv;
      this.context = ctx;
      this.attributes = attrs;
      this.message = msg;
    }
    
    @Override
    public TcpServer server() {
      return server;
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
    public TcpExchange setAttribute(String key, Object val) {
      if(key != null && val != null) {
        attributes.put(key, val);
      }
      return this;
    }
    
    @Override
    public Optional<Object> message() {
      return message;
    }
    
    @Override
    public Optional<TcpExchange> send(Object msg) {
      context.writeAndFlush(msg);
      return Optional.empty();
    }
    
    @Override
    public Optional<TcpExchange> send() {
      return send(message.orElse(Unpooled.EMPTY_BUFFER));
    }
    
    @Override
    public Optional<TcpExchange> withMessage(Object msg) {
      return Optional.of(new TcpExchangeImpl(server, context, attributes, Optional.of(msg)));
    }
    
    @Override
    public Optional<TcpExchange> noMessage() {
      return Optional.of(new TcpExchangeImpl(server, context, attributes, Optional.empty()));
    }
    
  }
  
}
