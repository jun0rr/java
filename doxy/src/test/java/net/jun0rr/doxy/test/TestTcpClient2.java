/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.tcp.TcpClient;
import net.jun0rr.doxy.tcp.TcpHandler;
import net.jun0rr.doxy.tcp.TcpServer;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestTcpClient2 {
  
  //@Test
  public void echoServer() {
    System.out.println("------ echoServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    Supplier<TcpHandler> hnd = ()-> x->{
      x.send();
      if(count.decrementAndGet() <= 0) {
        x.shutdown();
      }
      return x.empty();
    };
    TcpServer server = new TcpServer(Host.of("0.0.0.0", 3344))
        .addAcceptHandler(hnd)
        .start();
    hnd = ()-> x->{
      if(x.message().isPresent()) {
        ByteBuf buf = (ByteBuf) x.message().get();
        System.out.printf("* Server: '%s'%n", buf.toString(StandardCharsets.UTF_8));
      }
      return x.empty();
    };
    ByteBuf msg = Unpooled.copiedBuffer("Hello", StandardCharsets.UTF_8);
    System.out.printf("* Sending '%s'...%n", msg.toString(StandardCharsets.UTF_8));
    TcpClient cli = TcpClient.open()
        .addHandler(hnd)
        .connect(Host.of("localhost:3344"))
        .send(msg);
        //.closeOnComplete();
    server.sync();
    cli.close();
  }
  
  @Test
  public void echoServerNettyClient() {
    System.out.println("------ echoServerNettyClient ------");
    final AtomicInteger count = new AtomicInteger(1);
    Supplier<TcpHandler> hnd = ()-> x->{
      x.send();
      if(count.decrementAndGet() <= 0) {
        x.shutdown();
      }
      return x.empty();
    };
    TcpServer server = new TcpServer(Host.of("0.0.0.0", 3344))
        .addAcceptHandler(hnd)
        .start();
    
    Bootstrap boot = new Bootstrap();
    boot.channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(new NioEventLoopGroup(4));
    boot.handler(new ChannelInitializer<SocketChannel>() {
      @Override
      protected void initChannel(SocketChannel c) throws Exception {
        c.pipeline().addLast(new ChannelInboundHandler() {
          @Override
          public void channelRegistered(ChannelHandlerContext chc) throws Exception {
            System.out.println("channelRegistered");
          }
          @Override
          public void channelUnregistered(ChannelHandlerContext chc) throws Exception {
            System.out.println("channelUnregistered");
          }
          @Override
          public void channelActive(ChannelHandlerContext chc) throws Exception {
            System.out.println("channelActive");
          }
          @Override
          public void channelInactive(ChannelHandlerContext chc) throws Exception {
            System.out.println("channelInactive");
          }
          @Override
          public void channelRead(ChannelHandlerContext chc, Object o) throws Exception {
            System.out.println("channelRead");
            ByteBuf buf = (ByteBuf) o;
            System.out.printf("* Server: '%s'%n", buf.toString(StandardCharsets.UTF_8));
            buf.release();
          }
          @Override
          public void channelReadComplete(ChannelHandlerContext chc) throws Exception {
            System.out.println("channelReadComplete");
          }
          @Override
          public void userEventTriggered(ChannelHandlerContext chc, Object o) throws Exception {
            System.out.println("userEventTriggered");
          }
          @Override
          public void channelWritabilityChanged(ChannelHandlerContext chc) throws Exception {
            System.out.println("channelWritabilityChanged");
          }
          @Override
          public void exceptionCaught(ChannelHandlerContext chc, Throwable thrwbl) throws Exception {
            System.out.println("exceptionCaught");
          }
          @Override
          public void handlerAdded(ChannelHandlerContext chc) throws Exception {
            System.out.println("handlerAdded");
          }
          @Override
          public void handlerRemoved(ChannelHandlerContext chc) throws Exception {
            System.out.println("handlerRemoved");
          }
        });
      }
    });
    
    ChannelFutureListener close = new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture f) throws Exception {
        System.out.println("!! closing");
      }
    };
    ChannelFutureListener send = new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture f) throws Exception {
        System.out.println("!! writing");
        f.channel().writeAndFlush(Unpooled.copiedBuffer("Hello", StandardCharsets.UTF_8))
            .addListener(close);
      }
    };
    ChannelFuture fut = boot
        .connect(Host.of("localhost", 3344).toSocketAddr())
        .addListener(send);
    server.sync();
    fut.channel().close();
  }
  
  //@Test
  public void timestampServer() {
    System.out.println("------ timestampServer ------");
    final AtomicInteger count = new AtomicInteger(1);
    Supplier<TcpHandler> hnd = ()-> x->{
      ByteBuf msg = x.context().alloc().buffer(Long.BYTES);
      msg.writeLong(Instant.now().toEpochMilli());
      x.sendAndClose(msg);
      if(count.decrementAndGet() <= 0) {
        x.shutdown();
      }
      return x.empty();
    };
    TcpServer server = new TcpServer(Host.of("0.0.0.0", 3344))
        .addConnectHandler(hnd)
        .start();
    hnd = ()-> x->{
      if(x.message().isPresent()) {
        ByteBuf buf = (ByteBuf) x.message().get();
        Instant timestamp = Instant.ofEpochMilli(buf.readLong());
        System.out.printf("* Server Timestamp: %s%n", timestamp);
      }
      return x.close();
    };
    TcpClient.open()
        .addHandler(hnd)
        .connect(Host.of("localhost:3344"));
    server.sync();
  }
  
}
