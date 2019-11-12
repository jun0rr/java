/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import java.util.Optional;


/**
 *
 * @author Juno
 */
public interface HttpRequestHandler extends ChannelInboundHandler {
  
  public Optional<HttpRequest> httpRequest(ChannelHandlerContext ctx, HttpRequest req) throws Exception;
  
}
