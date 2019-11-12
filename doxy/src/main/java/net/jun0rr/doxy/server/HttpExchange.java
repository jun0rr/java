/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.channel.ChannelHandlerContext;
import java.util.Map;


/**
 *
 * @author Juno
 */
public interface HttpExchange {
  
  public HttpRequest request();
  
  public HttpResponse response();
  
  public ChannelHandlerContext context();
  
  public Map<String, Object> attributes();
  
  public HttpExchange withRequest(HttpRequest req);
  
  public HttpExchange withResponse(HttpResponse res);
  
  public void sendResponse();
  
}
