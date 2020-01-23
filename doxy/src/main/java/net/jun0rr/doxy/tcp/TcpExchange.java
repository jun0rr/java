/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import java.io.Closeable;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public interface TcpExchange {
  
  public ChannelHandlerContext context();
  
  public Map<String,Object> attributes();
  
  public TcpExchange setAttribute(String key, Object val);
  
  public Optional<Object> getAttribute(String key);
  
  public Optional<Object> message();
  
  /**
   * Close the channel.
   * @return Empty Optional
   */
  public Optional<TcpExchange> close();
  
  /**
   * Shutdown the EventLoopGroup.
   * @return Empty Optional
   */
  public Optional<TcpExchange> shutdown();
  
  /**
   * Send the message, aborting the inbound pipeline.
   * @param msg Message to send.
   * @return Emtpy Optional.
   */
  public Optional<TcpExchange> send(Object msg);
  
  /**
   * Send the message, aborting the inbound pipeline.
   * @return Emtpy Optional.
   */
  public Optional<TcpExchange> send();
  
  /**
   * Send the message, closing the channel after. The inbound pipeline is aborted.
   * @param msg Message to send.
   * @return Emtpy Optional.
   */
  public Optional<TcpExchange> sendAndClose(Object msg);
  
  /**
   * Send the message, closing the channel after. The inbound pipeline is aborted.
   * @return Emtpy Optional.
   */
  public Optional<TcpExchange> sendAndClose();
  
  /**
   * Return an empty optional.
   * @return Emtpy Optional.
   */
  public Optional<TcpExchange> empty();
  
  /**
   * Return a TcpExchange with the new message.
   * @param msg New message.
   * @return TcpExchange with new message.
   */
  public Optional<TcpExchange> withMessage(Object msg);
  
  /**
   * Return a TcpExchange without message.
   * @return TcpExchange without message.
   */
  public Optional<TcpExchange> noMessage();
  
  
  
  public static TcpExchange of(Closeable cls, ChannelHandlerContext ctx, Object msg) {
    return new TcpExchangeImpl(cls, ctx, new TreeMap<>(), Optional.of(msg));
  }
  
  public static TcpExchange of(Closeable cls, ChannelHandlerContext ctx) {
    return new TcpExchangeImpl(cls, ctx, new TreeMap<>(), Optional.empty());
  }
  
  
  
  
  
  static class TcpExchangeImpl implements TcpExchange {
    
    private final Closeable cls;
    
    private final ChannelHandlerContext context;
    
    private final Map<String,Object> attributes;
    
    private final Optional<Object> message;
    
    public TcpExchangeImpl(Closeable cls, ChannelHandlerContext ctx, Map<String,Object> attrs, Optional<Object> msg) {
      this.cls = cls;
      this.context = ctx;
      this.attributes = attrs;
      this.message = msg;
    }
    
    @Override
    public Optional<TcpExchange> shutdown() {
      Unchecked.call(()->cls.close());
      return empty();
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
    public Optional<Object> getAttribute(String key) {
      return Optional.ofNullable(attributes.get(key));
    }
    
    @Override
    public Optional<Object> message() {
      return message;
    }
    
    @Override
    public Optional<TcpExchange> withMessage(Object msg) {
      return Optional.of(new TcpExchangeImpl(cls, context, attributes, Optional.of(msg)));
    }
    
    @Override
    public Optional<TcpExchange> noMessage() {
      return Optional.of(new TcpExchangeImpl(cls, context, attributes, Optional.empty()));
    }
    
    @Override
    public Optional<TcpExchange> send(Object msg) {
      context.writeAndFlush(msg);
      return empty();
    }
    
    @Override
    public Optional<TcpExchange> send() {
      return send(message.orElse(Unpooled.EMPTY_BUFFER));
    }
    
    @Override
    public Optional<TcpExchange> sendAndClose(Object msg) {
      context.writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
      return empty();
    }
    
    @Override
    public Optional<TcpExchange> sendAndClose() {
      return sendAndClose(message.orElse(Unpooled.EMPTY_BUFFER));
    }
    
    @Override
    public Optional<TcpExchange> close() {
      context.close();
      return empty();
    }
    
    @Override
    public Optional<TcpExchange> empty() {
      return Optional.empty();
    }
    
  }
  
}
