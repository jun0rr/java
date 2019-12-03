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
public interface HttpRequestFilter {
  
  public Optional<HttpRequest> filter(ChannelHandlerContext ctx, HttpRequest req) throws Exception;
  
  public default Optional<HttpRequest> send(ChannelHandlerContext ctx, HttpResponse res) throws Exception {
    ctx.writeAndFlush(res.toNettyResponse()).addListener(ChannelFutureListener.CLOSE);
    return Optional.empty();
  }
  
}
