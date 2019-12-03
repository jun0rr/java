/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import net.jun0rr.doxy.DoxyEnvironment;
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
  
  private final DoxyEnvironment env;
  
  private final InternalLogger log;
  
  public InputServer(DoxyEnvironment env) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment");
    this.channel = new LazyFinal<>();
    this.accept = new NioEventLoopGroup(1);
    this.handle = new NioEventLoopGroup(env.configuration().getThreadPoolSize());
    this.log = InternalLoggerFactory.getInstance(getClass());
  }
  
  private ServerBootstrap bootstrap() {
    return new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
        .childOption(ChannelOption.SO_RCVBUF, env.configuration().getBufferSize())
        .childOption(ChannelOption.SO_SNDBUF, env.configuration().getBufferSize())
        .group(accept, handle)
        .handler(new LoggingHandler())
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel c) throws Exception {
            c.pipeline().addLast(new InputServerHandler(env));
          }
        })
        ;
  }
  
  public void stop() {
    accept.shutdownGracefully();
    handle.shutdownGracefully();
    ChannelFuture f = channel.get().channel().closeFuture().syncUninterruptibly();
    if(!f.isSuccess()) throw Unchecked.unchecked(f.cause());
  }
  
  public void start() {
    channel.init(bootstrap().bind(env.configuration().getHost().toSocketAddr()));
    log.info("InputServer started: {}", env.configuration().getHost());
    channel.get().syncUninterruptibly().awaitUninterruptibly();
    Unchecked.call(()->accept.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS));
  }
  
}
