/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
public class TcpServer extends AbstractTcpChannel {
  
  private final EventLoopGroup childGroup;
  private final List<Supplier<TcpHandler>> connectHandlers;
  
  public TcpServer(ServerBootstrap bootstrap) {
    super(bootstrap);
    this.childGroup = bootstrap.config().childGroup();
    this.connectHandlers = new LinkedList<>();
  }
  
  public static TcpServer open() {
    return new TcpServer(bootstrap(new NioEventLoopGroup(1), new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2)));
  }
  
  public static TcpServer open(EventLoopGroup parent, EventLoopGroup child) {
    return open(bootstrap(parent, child));
  }
  
  public static TcpServer open(ServerBootstrap boot) {
    return new TcpServer(boot);
  }
  
  public List<Supplier<TcpHandler>> connectHandlers() {
    return connectHandlers;
  }
  
  @Override
  public TcpServer addMessageHandler(Supplier<TcpHandler> handler) {
    super.addMessageHandler(handler);
    return this;
  }
  
  public TcpServer addConnectHandler(Supplier<TcpHandler> handler) {
    if(handler != null) connectHandlers.add(handler);
    return this;
  }
  
  private ServerBootstrap initHandlers(ServerBootstrap sbt) {
    List<Supplier<ChannelHandler>> ls = new LinkedList<>();
    Function<Supplier<TcpHandler>,Supplier<ChannelHandler>> fn = s->()->new TcpConnectHandler(this, s.get());
    ls.add(TcpOutboundHandler::new);
    connectHandlers.stream().map(fn).forEach(ls::add);
    fn = s->()->new TcpInboundHandler(this, s.get());
    messageHandlers.stream().map(fn).forEach(ls::add);
    ls.add(TcpUcaughtExceptionHandler::new);
    return sbt.childHandler(new AddingLastChannelInitializer(ls));
  }
  
  private static ServerBootstrap bootstrap(EventLoopGroup parent, EventLoopGroup child) {
    return new ServerBootstrap()
        .channel(NioServerSocketChannel.class)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
        .childOption(ChannelOption.AUTO_READ, Boolean.TRUE)
        .group(parent, child);
  }
  
  public TcpServer bind(Host host) {
    TcpEvent.ConnectEvent evt = b -> {
      //System.out.println("--- [SERVER] BIND ---");
      return initHandlers((ServerBootstrap)b).bind(host.toSocketAddr());
    };
    addListener(evt);
    return this;
  }
  
  /**
   * Close the channel and shutdown all EventLoopGroup's.
   * @return This TcpClient.
   * @see net.jun0rr.doxy.tcp.TcpClient#close() 
   */
  @Override
  public TcpServer shutdown() {
    close();
    TcpEvent.ChannelFutureEvent e = f -> {
      //System.out.println("--- [SERVER] SHUTDOWN ---");
      childGroup.shutdownGracefully();
      return group.shutdownGracefully();
    };
    addListener(e);
    return this;
  }
  
  public EventLoopGroup childGroup() {
    return childGroup;
  }
  
  @Override
  public TcpServer onComplete(Consumer<Channel> success) {
    super.onComplete(success);
    return this;
  }
  
  @Override
  public TcpServer onComplete(Consumer<Channel> success, Consumer<Throwable> error) {
    super.onComplete(success, error);
    return this;
  }
  
  @Override
  public TcpServer onShutdown(Consumer<EventLoopGroup> success) {
    super.onShutdown(success);
    return this;
  }
  
  @Override
  public TcpServer onShutdown(Consumer<EventLoopGroup> success, Consumer<Throwable> error) {
    super.onShutdown(success, error);
    return this;
  }
  
  @Override
  public TcpServer start() {
    super.start();
    return this;
  }
  
  @Override
  public TcpServer sync() {
    super.sync();
    return this;
  }
  
}
