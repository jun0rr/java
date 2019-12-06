/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import net.jun0rr.doxy.cfg.HostConfig;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class EchoServer2 {
  
  public static final String DEFAULT_BIND_HOST = "localhost:4455";
  
  private final NioEventLoopGroup accept;
  private final NioEventLoopGroup handle;
  private ChannelFuture channel;
  private final int countdown;
  private final HostConfig bind;
  private final InternalLogger log;
  
  
  public EchoServer2(HostConfig bind, int inputCount) {
    this.accept = new NioEventLoopGroup(1);
    this.handle = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
    this.countdown = inputCount;
    this.bind = bind;
    this.log = InternalLoggerFactory.getInstance(getClass());
  }
  
  public EchoServer2(int inputCount) {
    this(HostConfig.of(DEFAULT_BIND_HOST), inputCount);
  }
  
  public EchoServer2() {
    this(HostConfig.of(DEFAULT_BIND_HOST), 1);
  }
  
  private ServerBootstrap bootstrap() {
    return new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_READ, true)
        .group(accept, handle)
        .childHandler(new AddingLastChannelInitializer(new EchoHandler2(this, countdown)));
  }
  
  public EchoServer2 start() {
    channel = bootstrap().bind(bind.toSocketAddr());
    log.info("EchoServer started: {}", bind);
    return this;
  }
  
  public void sync() {
    Unchecked.call(()->accept.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS));
  }
  
  public void stop() {
    channel.channel().close();
    accept.shutdownGracefully();
    handle.shutdownGracefully();
  }
  




  public class EchoHandler2 implements ChannelInboundHandler {

    private final InternalLogger log;
    
    private final AtomicInteger count;
    
    private final int totalCount;
    
    private final EchoServer2 server;

    public EchoHandler2(EchoServer2 server, int countdown) {
      this.log = InternalLoggerFactory.getInstance(getClass());
      this.count = new AtomicInteger(0);
      this.server = server;
      this.totalCount = countdown;
    }

    @Override 
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      ByteBuf buf = (ByteBuf) msg;
      log.info("Message Received ({} bytes): {}", buf.readableBytes(), ctx.channel().remoteAddress());
      ctx.writeAndFlush(msg);
      if(count.incrementAndGet() >= totalCount) {
        server.stop();
      }
    }

    @Override 
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
      ctx.fireExceptionCaught(e);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      ctx.fireChannelReadComplete();
    }

    @Override 
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      ctx.fireChannelRegistered();
    }

    @Override 
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
      ctx.fireChannelUnregistered();
    }

    @Override 
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      ctx.fireChannelActive();
    }

    @Override 
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      ctx.fireChannelInactive();
    }

    @Override 
    public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
      ctx.fireUserEventTriggered(o);
    }

    @Override 
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
      ctx.fireChannelWritabilityChanged();
    }

    @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {}

    @Override public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {}

  }

}
