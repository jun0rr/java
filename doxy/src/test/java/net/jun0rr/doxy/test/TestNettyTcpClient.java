/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ForkJoinPool;
import net.jun0rr.doxy.cfg.Host;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Timer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class TestNettyTcpClient {
  
  private static final int TOTAL = 6000;
  
  private volatile int COUNT = TOTAL;
  
  @Test
  public void clients() {
    try {
      EventLoopGroup clients = new NioEventLoopGroup(12);
      //ForkJoinPool.commonPool().submit(()->new TestNettyTcpPerformance().server());
      Host target = Host.of("localhost:4322");
      
      Timer tm = new Timer.Nanos().start();
      for(int i = 0; i < TOTAL; i++) {
        Bootstrap b = new Bootstrap();
        b
            .group(clients)
            //.group(new NioEventLoopGroup(1))
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
              @Override
              public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new ChannelInboundHandlerAdapter() {
                  @Override
                  public void channelRead(ChannelHandlerContext ctx, java.lang.Object msg) {
                    ReferenceCountUtil.release(msg);
                    if(COUNT % 100 == 0) {
                      System.out.println("[" + ctx.channel().localAddress() + "] " + COUNT);
                    }
                    COUNT--;
                    if(COUNT <= 1) {
                      //System.out.printf("[%s] sending !!SHUTDOWN!!", ctx.channel().localAddress());
                      ctx.writeAndFlush(Unpooled.copiedBuffer("!!SHUTDOWN!!", StandardCharsets.UTF_8))
                          .addListener(ChannelFutureListener.CLOSE);
                      return;
                    }
                    ctx.channel().close();
                  }
                });
              }
            });
        b.connect(target.toSocketAddr())//, Host.of("localhost", 22111 + i).toSocketAddr())
            .addListener(f->((ChannelFuture) f).channel().writeAndFlush(
                Unpooled.copiedBuffer("[" 
                    + ((ChannelFuture) f).channel().localAddress() 
                    + "] Hello World!", StandardCharsets.UTF_8)
        ));//.sync();
        //clifs[i].sync();
      }
      
      System.out.println("* Waiting CountDown...");
      int cc = --COUNT;
      int retry = 100;
      while(COUNT > 0) {
        if(retry <= 0) {
          System.out.println("# ERROR clients(): COUNT(" + COUNT + ") is static for " + 100*50 + " millis...");
          break;
        }
        Thread.sleep(50);
        if(COUNT == cc) {
          retry--;
        }
        cc = COUNT;
      }
      //Thread.sleep(8000);
      //cd.await();
      System.out.println("* " + tm.stop());
      //clients.shutdownGracefully().sync();
    }
    catch(Exception e) {
      e.printStackTrace();
      throw Unchecked.unchecked(e);
    }
  }
  
}
