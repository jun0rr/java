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
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import net.jun0rr.doxy.common.AbstractRunnable;
import net.jun0rr.doxy.common.DoxyChannel;
import net.jun0rr.doxy.common.DoxyChannel.DoxyChannelImpl;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.tcp.TcpClient;
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
        System.out.println("* RemoteTransport.writing: " + p);
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
    System.out.println("* RemoteTransport.createChannel1: creating...");
    Channel ch = TcpClient.create(group)
        .addHandler(new RemoteInputHandler(env, p.channelID()))
        .connect(p.remote())
        .sync();
    System.out.println("* RemoteTransport.createChannel2: " + ch);
    DoxyChannel dc = new DoxyChannelImpl(
        env, p.channelID(), ch
        //TcpClient.create(group)
            //.addHandler(new RemoteInputHandler(env, p.channelID()))
            //.connect(p.remote())
            //.sync()
    );
    env.channels().add(dc);
    return dc;
  }
  
}
