/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import javax.net.ssl.SSLException;
import net.jun0rr.doxy.tcp.TcpServer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class HttpServer1 extends TcpServer {
  
  private final List<Supplier<HttpHandler>> handlers;
  
  public HttpServer1(ServerBootstrap boot) {
    super(boot);
    this.handlers = new ArrayList<>();
  }
  
  public List<Supplier<HttpHandler>> httpHandlers() {
    return handlers;
  }
  
  public HttpServer1 addHttpHandler(Supplier<HttpHandler> hnd) {
    if(hnd != null) {
      handlers.add(hnd);
    }
    return this;
  }
  
  
  
}
