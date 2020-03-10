/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import net.jun0rr.doxy.server.http.HttpExchange;
import net.jun0rr.doxy.server.http.HttpHandler;


/**
 *
 * @author juno
 */
public class HttpResponseHeadersHandler implements HttpHandler {
  
  private final DateTimeFormatter datefmt;
  
  private final String serverName;
  
  public HttpResponseHeadersHandler(String serverName) {
    this.serverName = Objects.requireNonNull(serverName, "Bad null server name");
    this.datefmt = DateTimeFormatter
        .ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz")
        .withZone(ZoneId.of("GMT"));
  }
  
  @Override
  public Optional<HttpExchange> apply(HttpExchange x) throws Exception {
    System.out.println("[HttpResponseHeadersHandler] message=" + x.response().message());
    x.response().headers()
        .set(HttpHeaderNames.DATE, datefmt.format(Instant.now()))
        .set(HttpHeaderNames.SERVER, serverName);
    if(x.response().message() != null) {
      ByteBuf buf;
      if(x.response().message() instanceof CharSequence) {
        buf = Unpooled.copiedBuffer(x.response().<CharSequence>message(), StandardCharsets.UTF_8);
      }
      else {
        buf = x.response().message();
      }
      if(buf.readableBytes() > 0) {
        x.response().headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
      }
      return x.withMessage(buf);
    }
    return x.forward();
  }
  
}
