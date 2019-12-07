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
import java.util.concurrent.CountDownLatch;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;
import us.pserver.tools.Unchecked;
import net.jun0rr.doxy.cfg.Host;


/**
 *
 * @author juno
 */
public class EchoServer {
  
  public static final String DEFAULT_BIND_HOST = "localhost:4455";
  
  private final NioEventLoopGroup accept;
  private final NioEventLoopGroup handle;
  private ChannelFuture channel;
  private final CountDownLatch countdown;
  private final Host bind;
  private final InternalLogger log;
  
  
  public EchoServer(Host bind, int inputCount) {
    this.accept = new NioEventLoopGroup(1);
    this.handle = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
    this.countdown = new CountDownLatch(inputCount);
    this.bind = bind;
    this.log = InternalLoggerFactory.getInstance(getClass());
  }
  
  public EchoServer(int inputCount) {
    this(Host.of(DEFAULT_BIND_HOST), inputCount);
  }
  
  public EchoServer() {
    this(Host.of(DEFAULT_BIND_HOST), 1);
  }
  
  private ServerBootstrap bootstrap() {
    return new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_READ, true)
        .group(accept, handle)
        .childHandler(new AddingLastChannelInitializer(new EchoHandler(countdown)));
  }
  
  public void start() {
    channel = bootstrap().bind(bind.toSocketAddr());
    log.info("EchoServer started: {}", bind);
    Unchecked.call(()->countdown.await());
    channel.channel().close();
    accept.shutdownGracefully();
    handle.shutdownGracefully();
  }
  




  public class EchoHandler implements ChannelInboundHandler {

    private final InternalLogger log;
    
    private final CountDownLatch countdown;

    public EchoHandler(CountDownLatch countdown) {
      this.log = InternalLoggerFactory.getInstance(getClass());
      this.countdown = countdown;
    }

    @Override 
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      ByteBuf buf = (ByteBuf) msg;
      log.info("Message Received ({} bytes): {}", buf.readableBytes(), ctx.channel().remoteAddress());
      ctx.writeAndFlush(msg);
      countdown.countDown();
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
