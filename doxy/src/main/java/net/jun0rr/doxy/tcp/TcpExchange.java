/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
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
  
  public ConnectedTcpChannel connectedChannel();
  
  public TcpChannel mainChannel(); 
  
  public TcpExchange withPromise(ChannelPromise prms);
  
  /**
   * Return a TcpExchange with the new message.
   * @param msg New message.
   * @return TcpExchange with new message.
   */
  @Override
  public TcpExchange withMessage(Object msg);
  
  /**
   * Return an empty optional.
   * @return Emtpy Optional.
   */
  @Override
  public Optional<? extends TcpExchange> empty();
  
  /**
   * Return a TcpExchange without message.
   * @return TcpExchange without message.
   */
  @Override
  public Optional<? extends TcpExchange> forward();
  
  
  
  public static TcpExchange of(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, Object msg) {
    return new TcpExchangeImpl(channel, connected, ctx, new TreeMap<>(), msg);
  }
  
  
  
  
  
  static class TcpExchangeImpl implements TcpExchange {
    
    protected final ConnectedTcpChannel connected;
    
    protected final TcpChannel channel;
    
    protected final ChannelHandlerContext context;
    
    protected final Map<String,Object> attributes;
    
    protected final Object message;
    
    
    public TcpExchangeImpl(TcpChannel channel, ConnectedTcpChannel connected, ChannelHandlerContext ctx, Map<String,Object> attrs, Object msg) {
      this.channel = channel;
      this.connected = connected;
      this.context = ctx;
      this.attributes = attrs;
      this.message = msg;
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
    public TcpExchange withPromise(ChannelPromise prms) {
      return new TcpExchangeImpl(channel, connected.withPromise(prms), context, attributes, message);
    }
    
    @Override
    public TcpExchange withMessage(Object msg) {
      return new TcpExchangeImpl(channel, connected, context, attributes, msg);
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
