/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;


/**
 *
 * @author juno
 */
public class AddingLastChannelInitializer extends ChannelInitializer<SocketChannel> {
  
  private final Collection<Supplier<ChannelHandler>> handlers;
  
  public AddingLastChannelInitializer(Supplier<ChannelHandler>... handlers) {
    this(Arrays.asList(handlers));
  }
  
  public AddingLastChannelInitializer(Collection<Supplier<ChannelHandler>> handlers) {
    this.handlers = Objects.requireNonNull(handlers, "Bad null ChannelHandler Collection");
    if(handlers.isEmpty()) {
      throw new IllegalArgumentException("ChannelHandler Collection is Empty!");
    }
  }

  @Override
  protected void initChannel(SocketChannel c) throws Exception {
    handlers.stream()
        .map(Supplier::get)
        .forEach(c.pipeline()::addLast);
  }
  
}
