/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.EOFException;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import net.jun0rr.doxy.DoxyChannel;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.impl.AbstractRunnable;
import net.jun0rr.doxy.impl.DoxyChannelImpl;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class RemoteTransport extends AbstractRunnable {
  
  private final NioEventLoopGroup group;
  
  public RemoteTransport(DoxyEnvironment env) {
    super(env);
    this.group = new NioEventLoopGroup(env.configuration().getThreadPoolSize());
  }
  
  private Optional<Packet> pollOutboxTimeout() {
    return Optional.ofNullable(Unchecked.call(()->
        env.outbox().pollFirst(100, TimeUnit.MILLISECONDS))
    );
  }
  
  @Override
  public void run() {
    while(isRunning()) {
      pollOutboxTimeout().ifPresent(t->
          env.executor().submit(writing(t))
      );
    }
  }
  
  private Runnable writing(Packet p) {
    return () -> {
      try {
        env.getChannelById(p.channelID())
            .orElse(createChannel(p))
            .writePacket(p);
      }
      catch(IOException e) {
        throw Unchecked.unchecked(e);
      }
    };
  }
  
  private DoxyChannel createChannel(Packet p) throws IOException {
    Channel ch = new Bootstrap()
        .channel(NioSocketChannel.class)
        .group(group)
        .option(ChannelOption.SO_SNDBUF, env.configuration().getBufferSize())
        .option(ChannelOption.SO_RCVBUF, env.configuration().getBufferSize())
        .option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.AUTO_READ, true)
        .remoteAddress(p.remote().toSocketAddr())
        .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel c) throws Exception {
              c.pipeline().addLast(new RemoteInputHandler(env, p.channelID()));
            }
        })
        .connect().syncUninterruptibly()
        .channel();
    DoxyChannel dc = new DoxyChannelImpl(env, p.channelID(), ch);
    env.channels().add(dc);
    return dc;
  }
  
}
