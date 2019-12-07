/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import java.util.Optional;


/**
 *
 * @author Juno
 */
@FunctionalInterface
public interface HttpResponseFilter {
  
  public Optional<HttpResponse> filter(ChannelHandlerContext ctx, HttpResponse res) throws Exception;
  
  public default Optional<HttpResponse> send(ChannelHandlerContext ctx, HttpResponse res) throws Exception {
    ctx.writeAndFlush(res.toNettyResponse()).addListener(ChannelFutureListener.CLOSE);
    return Optional.empty();
  }
  
}
