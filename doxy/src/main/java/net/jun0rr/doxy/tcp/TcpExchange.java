/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import net.jun0rr.doxy.common.MessageContainer;


/**
 *
 * @author Juno
 */
public interface TcpExchange extends MessageContainer {
  
  public ChannelHandlerContext context();
  
  public Map<String,Object> attributes();
  
  public TcpExchange setAttr(String key, Object val);
  
  public <A> Optional<A> getAttr(String key);
  
  public TcpChannel tcpChannel();
  
  /**
   * Close the channel.
   * @return Empty Optional
   */
  public Optional<? extends TcpExchange> close();
  
  /**
   * Close and shutdown the EventLoopGroup.
   * @return Empty Optional
   * @see net.jun0rr.doxy.tcp.TcpExchange#close() 
   */
  public Optional<? extends TcpExchange> shutdown();
  
  /**
   * Send the message (aborting the inbound pipeline).
   * @param msg Message to send.
   * @return Emtpy Optional.
   */
  public Optional<? extends TcpExchange> send(Object msg);
  
  /**
   * Send the message (aborting the inbound pipeline).
   * @return Emtpy Optional.
   */
  public Optional<? extends TcpExchange> send();
  
  /**
   * Send the message (aborting the inbound pipeline) and close the channel.
   * @param msg Message to send.
   * @return Emtpy Optional.
   */
  public Optional<? extends TcpExchange> sendAndClose(Object msg);
  
  /**
   * Send the message (aborting the inbound pipeline) and close the channel.
   * @return Emtpy Optional.
   */
  public Optional<? extends TcpExchange> sendAndClose();
  
  /**
   * Return an empty optional.
   * @return Emtpy Optional.
   */
  @Override
  public Optional<? extends TcpExchange> empty();
  
  /**
   * Return a TcpExchange with the new message.
   * @param msg New message.
   * @return TcpExchange with new message.
   */
  @Override
  public Optional<? extends TcpExchange> withMessage(Object msg);
  
  /**
   * Return a TcpExchange without message.
   * @return TcpExchange without message.
   */
  @Override
  public Optional<? extends TcpExchange> forward();
  
  
  
  public static TcpExchange of(TcpChannel channel, ChannelHandlerContext ctx, Object msg) {
    return new TcpExchangeImpl(channel, ctx, new TreeMap<>(), msg);
  }
  
  
  
  
  
  static class TcpExchangeImpl implements TcpExchange {
    
    protected final TcpChannel channel;
    
    protected final ChannelHandlerContext context;
    
    protected final Map<String,Object> attributes;
    
    protected final Object message;
    
    public TcpExchangeImpl(TcpChannel channel, ChannelHandlerContext ctx, Map<String,Object> attrs, Object msg) {
      this.channel = channel;
      this.context = ctx;
      this.attributes = attrs;
      this.message = msg;
    }
    
    @Override
    public Optional<? extends TcpExchange> shutdown() {
      close();
      channel.shutdown();
      return empty();
    }
    
    @Override
    public ChannelHandlerContext context() {
      return context;
    }
    
    @Override
    public TcpChannel tcpChannel() {
      return channel;
    }
    
    @Override
    public Map<String, Object> attributes() {
      return attributes;
    }
    
    @Override
    public TcpExchange setAttr(String key, Object val) {
      if(key != null && val != null) {
        attributes.put(key, val);
      }
      return this;
    }
    
    @Override
    public <T> Optional<T> getAttr(String key) {
      return Optional.ofNullable((T)attributes.get(key));
    }
    
    @Override
    public <T> T message() {
      return (T) message;
    }
    
    @Override
    public Optional<? extends TcpExchange> withMessage(Object msg) {
      return Optional.of(new TcpExchangeImpl(channel, context, attributes, Optional.of(msg)));
    }
    
    @Override
    public Optional<? extends TcpExchange> send(Object msg) {
      if(message() != msg) {
        ReferenceCountUtil.release(message);
      }
      context.writeAndFlush(msg);
      return empty();
    }
    
    @Override
    public Optional<? extends TcpExchange> send() {
      return send(message);
    }
    
    @Override
    public Optional<? extends TcpExchange> sendAndClose(Object msg) {
      if(message() != msg) {
        ReferenceCountUtil.release(message);
      }
      context.writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
      return empty();
    }
    
    @Override
    public Optional<? extends TcpExchange> sendAndClose() {
      return sendAndClose(message);
    }
    
    @Override
    public Optional<? extends TcpExchange> close() {
      ReferenceCountUtil.release(message);
      context.close();
      return empty();
    }
    
    @Override
    public Optional<? extends TcpExchange> empty() {
      return Optional.empty();
    }


    @Override
    public Optional<? extends TcpExchange> forward() {
      return Optional.of(this);
    }
    
  }
  
}
