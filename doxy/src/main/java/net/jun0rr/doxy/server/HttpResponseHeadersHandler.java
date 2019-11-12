/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import net.jun0rr.doxy.DoxyConfig;


/**
 *
 * @author juno
 */
public class HttpResponseHeadersHandler extends AbstractHttpResponseHandler {
  
  private final HttpHeaders headers;
  
  private final DateTimeFormatter datefmt;
  
  private final DoxyConfig config;
  
  public HttpResponseHeadersHandler(DoxyConfig cfg) {
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyConfig");
    this.headers = new DefaultHttpHeaders();
    this.datefmt = DateTimeFormatter
        .ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz")
        .withZone(ZoneId.of("GMT"));
  }
  
  public HttpHeaders headers() {
    return headers;
  }
  
  @Override
  public Optional<HttpResponse> httpResponse(ChannelHandlerContext ctx, HttpResponse res) throws Exception {
    res.<ByteBuf>content().ifPresent(b->headers.set(HttpHeaderNames.CONTENT_LENGTH, b.readableBytes()));
    headers.add(HttpHeaderNames.DATE, datefmt.format(Instant.now()))
        .add(HttpHeaderNames.SERVER, config.getServerName());
    res.headers().setAll(headers());
    writeAndClose(ctx, res);
    return Optional.empty();
  }
  
}
