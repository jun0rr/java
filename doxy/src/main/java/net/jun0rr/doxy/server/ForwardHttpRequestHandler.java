/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import java.util.Objects;
import java.util.Optional;


/**
 *
 * @author Juno
 */
public class ForwardHttpRequestHandler<T> extends AbstractHttpRequestHandler<T> {
  
  private final HttpRequestHandler<T> handler;
  
  public ForwardHttpRequestHandler(HttpRequestHandler<T> h) {
    this.handler = Objects.requireNonNull(h);
  }

  @Override
  public Optional<HttpRequest<?>> httpRequest(ChannelHandlerContext c, HttpRequest<T> req) throws Exception {
    return handler.httpRequest(c, req); 
  }
  
  @Override 
  public void exceptionCaught(ChannelHandlerContext chc, Throwable thrwbl) throws Exception {
    handler.exceptionCaught(chc, thrwbl);
  }
  
  @Override
  public void channelReadComplete(ChannelHandlerContext chc) throws Exception {
    handler.channelReadComplete(chc);
  }
  
  @Override 
  public void channelRegistered(ChannelHandlerContext chc) throws Exception {
    handler.channelRegistered(chc);
  }
  
  @Override 
  public void channelUnregistered(ChannelHandlerContext chc) throws Exception {
    handler.channelUnregistered(chc);
  }
  
  @Override 
  public void channelActive(ChannelHandlerContext chc) throws Exception {
    handler.channelActive(chc);
  }
  
  @Override 
  public void channelInactive(ChannelHandlerContext chc) throws Exception {
    handler.channelInactive(chc);
  }
  
  @Override 
  public void userEventTriggered(ChannelHandlerContext chc, Object o) throws Exception {
    handler.userEventTriggered(chc, o);
  }
  
  @Override 
  public void channelWritabilityChanged(ChannelHandlerContext chc) throws Exception {
    handler.channelWritabilityChanged(chc);
  }
  
  @Override 
  public void handlerAdded(ChannelHandlerContext chc) throws Exception {
    handler.handlerAdded(chc);
  }
  
  @Override 
  public void handlerRemoved(ChannelHandlerContext chc) throws Exception {
    handler.handlerRemoved(chc);
  }
  
}
