/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.util.Objects;
import java.util.function.Consumer;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public interface TcpFuture {
  
  public ChannelFuture getChannelFuture();
  
  public Channel channel();
  
  public TcpFuture send(Object msg);
  
  public TcpFuture onComplete(Consumer<ChannelFuture> lst);
  
  public TcpFuture onComplete(Consumer<ChannelFuture> lst, Consumer<Throwable> err);
  
  public TcpFuture sync();
  
  
  
  
  
  static class TcpFutureImpl implements TcpFuture {
    
    private final ChannelFuture future;
    
    public TcpFutureImpl(ChannelFuture cf) {
      this.future = Objects.requireNonNull(cf, "Bad null ChannelFuture");
    }
    
    @Override
    public ChannelFuture getChannelFuture() {
      return future;
    }
    
    @Override
    public Channel channel() {
      return future.channel();
    }
    
    @Override
    public TcpFuture send(Object msg) {
      return onComplete(f->f.channel().writeAndFlush(msg));
    }
    
    @Override
    public TcpFuture onComplete(Consumer<TcpFuture> lst) {
      return onComplete(lst, Unchecked::unchecked);
    }
    
    @Override
    public TcpFuture onComplete(Consumer<TcpFuture> lst, Consumer<Throwable> err) {
      future.addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture f) throws Exception {
          if(f.isSuccess()) lst.accept(f);
          else err.accept(f.cause());
        }
      });
      return this;
    }
    
    @Override
    public TcpFuture sync() {
      future.syncUninterruptibly();
      return this;
    }
    
  }
  
}
