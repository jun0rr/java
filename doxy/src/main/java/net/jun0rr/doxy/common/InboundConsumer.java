/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import io.netty.channel.ChannelHandlerContext;


/**
 *
 * @author juno
 */
public interface InboundConsumer {
  
  public InboundResult accept(ChannelHandlerContext ctx, Object msg) throws Exception;
  
}
