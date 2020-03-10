/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;
import net.jun0rr.doxy.server.http.HttpResponse;


/**
 *
 * @author Juno
 */
public class HttpOutboundHandler extends ChannelOutboundHandlerAdapter {
  
  private final BiFunction<HttpExchange,Throwable,Optional<HttpResponse>> uncaughtHandler;
  
  private final Optional<HttpHandler> delegate;
  
  public HttpOutboundHandler(HttpHandler delegate, BiFunction<HttpExchange,Throwable,Optional<HttpResponse>> uncaughtHandler) {
    this.uncaughtHandler = Objects.requireNonNull(uncaughtHandler, "Bad null UncaughtExceptionHandler BiFunction<HttpExchange,Throwable,Optional<HttpResponse>>");
    this.delegate = Optional.ofNullable(delegate);
  }
  
  public HttpOutboundHandler(BiFunction<HttpExchange,Throwable,Optional<HttpResponse>> uncaughtHandler) {
    this(null, uncaughtHandler);
  }
  
  public HttpOutboundHandler() {
    this(null, new HttpServerErrorHandler());
  }
  
  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise cp) throws Exception {
    try {
      HttpExchange x;
      if(msg instanceof HttpExchange) {
        x = (HttpExchange) msg;
      }
      else {
        x = HttpExchange.CURRENT_EXCHANGE.get().withMessage(msg).get();
      }
      if(delegate.isPresent()) {
        delegate.get().apply(x).ifPresent(e->ctx.writeAndFlush(e.response(), cp));
      }
      else {
        ctx.writeAndFlush(x.response(), cp);
      }
    }
    catch(Exception e) {
      this.exceptionCaught(ctx, e);
    }
  }
  
  @Override 
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
    uncaughtHandler.apply(HttpExchange.CURRENT_EXCHANGE.get(), e).ifPresent(ctx::writeAndFlush);
  }
  
}
