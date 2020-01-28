/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.AddingLastChannelInitializer;


/**
 *
 * @author juno
 */
public class TcpClient extends AbstractTcpChannel {
  
  public TcpClient(Bootstrap bootstrap) {
    super(bootstrap);
  }
  
  public static TcpClient open() {
    return open(bootstrap(new NioEventLoopGroup(1)));
  }
  
  public static TcpClient open(EventLoopGroup group) {
    return open(bootstrap(group));
  }
  
  public static TcpClient open(Bootstrap boot) {
    return new TcpClient(boot);
  }
  
  private static Bootstrap bootstrap(EventLoopGroup group) {
    return new Bootstrap()
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .option(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(group);
  }
  
  @Override
  public TcpClient addMessageHandler(Supplier<TcpHandler> handler) {
    super.addMessageHandler(handler);
    return this;
  }
  
  private Bootstrap initHandlers(Bootstrap sbt) {
    List<Supplier<ChannelHandler>> ls = new LinkedList<>();
    ls.add(TcpOutboundHandler::new);
    Function<Supplier<TcpHandler>,Supplier<ChannelHandler>> fn = s->()->new TcpInboundHandler(this, s.get());
    messageHandlers.stream().map(fn).forEach(ls::add);
    ls.add(TcpUcaughtExceptionHandler::new);
    return sbt.handler(new AddingLastChannelInitializer(ls));
  }
  
  public TcpClient connect(Host host) {
    TcpEvent.ConnectEvent evt = b -> {
      //System.out.println("--- [CLIENT] CONNECT ---");
      ChannelFuture f = initHandlers((Bootstrap)b).connect(host.toSocketAddr());
      return f;
    };
    addListener(evt);
    return this;
  }
  
  public TcpClient send(Object msg) {
    if(msg != null) {
      TcpEvent.ChannelFutureEvent evt = f -> {
        //System.out.println("--- [CLIENT] SEND ---");
        return f.channel().writeAndFlush(msg);
      };
      addListener(evt);
    }
    return this;
  }
  
  @Override
  public TcpClient onComplete(Consumer<Channel> success) {
    super.onComplete(success);
    return this;
  }
  
  @Override
  public TcpClient onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    super.onComplete(success, error);
    return this;
  }
  
  @Override
  public TcpClient onShutdown(Consumer<EventLoopGroup> success) {
    super.onShutdown(success);
    return this;
  }
  
  @Override
  public TcpClient onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    super.onShutdown(success, error);
    return this;
  }
  
  @Override
  public TcpClient start() {
    super.start();
    return this;
  }
  
  @Override
  public TcpClient sync() {
    super.sync();
    return this;
  }
  
}
