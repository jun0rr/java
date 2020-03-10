/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCounted;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import net.jun0rr.doxy.common.MessageContainer;


/**
 *
 * @author Juno
 */
public interface TcpExchange2 extends MessageContainer {
  
  public ChannelHandlerContext context();
  
  public Map<String,Object> attributes();
  
  public TcpExchange setAttr(String key, Object val);
  
  public <A> Optional<A> getAttr(String key);
  
  public ConnectedTcpChannel connectedChannel();
  
  public TcpChannel mainChannel(); 
  
  public ByteBufAllocator alloc();
  
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
  public Optional<? extends TcpExchange2> withMessage(Object msg);
  
  /**
   * Return a TcpExchange without message.
   * @return TcpExchange without message.
   */
  @Override
  public Optional<? extends TcpExchange2> forward();
  
  
  
  public static TcpExchange of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, Object msg) {
    return new TcpExchangeImpl(channel, connected, ctx, new TreeMap<>(), msg, null);
  }
  
  public static TcpExchange of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
    return new TcpExchangeImpl(channel, connected, ctx, new TreeMap<>(), msg, promise);
  }
  
  
  
  
  
  static class TcpExchangeImpl implements TcpExchange {
    
    protected final ConnectedTcpChannel connected;
    
    protected final TcpChannel channel;
    
    protected final ChannelHandlerContext context;
    
    protected final Map<String,Object> attributes;
    
    protected final Object message;
    
    protected final ChannelPromise promise;
    
    public TcpExchangeImpl(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, Map<String,Object> attrs, Object msg, ChannelPromise promise) {
      this.channel = channel;
      this.connected = connected;
      this.context = ctx;
      this.attributes = attrs;
      this.message = msg;
      this.promise = promise;
    }
    
    @Override
    public Optional<? extends TcpExchange> shutdown() {
      channel.shutdown();
      return empty();
    }
    
    @Override
    public ChannelHandlerContext context() {
      return context;
    }
    
    @Override
    public ConnectedTcpChannel connectedChannel() {
      return connected;
    }
    
    @Override
    public TcpChannel mainChannel() {
      return channel;
    }
    
    @Override
    public ByteBufAllocator alloc() {
      return connected.nettyChannel().get().alloc();
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
      return Optional.of(new TcpExchangeImpl(channel, connected, context, attributes, msg, promise));
    }
    
    private ChannelFuture writeAndFlush(Object msg) {
      if(promise != null) {
        return context.writeAndFlush(msg, promise);
      }
      else {
        return context.writeAndFlush(msg);
      }
    }
    
    @Override
    public Optional<? extends TcpExchange> send(Object msg) {
      if(msg == null) return empty();
      if(message() != null) {
        if(message() != msg && message() instanceof ReferenceCounted) {
          ReferenceCounted rc = (ReferenceCounted) message();
          if(rc.refCnt() > 0) rc.release(rc.refCnt());
        }
      }
      writeAndFlush(msg);
      return empty();
    }
    
    @Override
    public Optional<? extends TcpExchange> send() {
      return send(message);
    }
    
    @Override
    public Optional<? extends TcpExchange> sendAndClose(Object msg) {
      if(msg == null) return empty();
      if(message() != null) {
        if(message() != msg && message() instanceof ReferenceCounted) {
          ReferenceCounted rc = (ReferenceCounted) message();
          if(rc.refCnt() > 0) rc.release(rc.refCnt());
        }
      }
      writeAndFlush(msg).addListener(ChannelFutureListener.CLOSE);
      return empty();
    }
    
    @Override
    public Optional<? extends TcpExchange> sendAndClose() {
      return sendAndClose(message);
    }
    
    @Override
    public Optional<? extends TcpExchange> close() {
      if(message() != null && message() instanceof ReferenceCounted) {
        ReferenceCounted rc = (ReferenceCounted) message();
        if(rc.refCnt() > 0) rc.release(rc.refCnt());
      }
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
