/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import net.jun0rr.doxy.server.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import net.jun0rr.doxy.DoxyConfig;
import us.pserver.tools.LazyFinal;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class InputServer {
  
  private final LazyFinal<ChannelFuture> channel;
  
  private final EventLoopGroup accept;
  
  private final EventLoopGroup handle;
  
  private final DoxyConfig config;
  
  public InputServer(DoxyConfig cfg) {
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyConfig (cfg)");
    this.channel = new LazyFinal<>();
    this.accept = new NioEventLoopGroup(1);
    this.handle = new NioEventLoopGroup(cfg.getThreadPoolSize());
  }
  
  private ServerBootstrap bootstrap() {
    return new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
        .group(accept, handle)
        .childHandler(handlers.createInitializer());
  }
  
  public void stop() {
    accept.shutdownGracefully();
    handle.shutdownGracefully();
    ChannelFuture f = channel.get().channel().closeFuture().syncUninterruptibly();
    if(!f.isSuccess()) throw Unchecked.unchecked(f.cause());
  }
  
  public void start() {
    channel.init(bootstrap().bind(config.getHost().toSocketAddr()));
    System.out.println("* HttpServer started: " + config.getHost());
    channel.get().syncUninterruptibly().awaitUninterruptibly();
    Unchecked.call(()->accept.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS));
  }
  
  
  
  private class InputServerInit extends ChannelInitializer<SocketChannel> {
    
    @Override
    protected void initChannel(SocketChannel c) throws Exception {
      c.pipeline().addLast(
          //createSSL().newHandler(c.alloc()),
          new HttpServerCodec(),
          new HttpObjectAggregator(1024*1024)
          //new HttpHeadersOutputFilter(config)
          //new HttpRequestRouteHandler()
              //.addHandler(new EncodeHandler())
              //.addHandler(new DecodeRequestHandler())
      );
    }

  }
  
}
