/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 *
 * @author Juno
 */
public class EncodeRequestHandler extends AbstractRoutableHttpRequestHandler {
  
  public EncodeRequestHandler() {
    super(HttpRoute.of("\\/encode.*", HttpMethod.GET, HttpMethod.POST));
  }

  @Override
  public Optional<HttpRequest<?>> httpRequest(ChannelHandlerContext ctx, HttpRequest<ByteBuf> req) throws Exception {
    System.out.println("-> ENCODE_HANDLER");
    StringBuilder buf = new StringBuilder();
    buf.append("VERSION: ").append(req.protocolVersion()).append("\r\n");
    buf.append("HOSTNAME: ").append(req.headers().get(HttpHeaderNames.HOST, "unknown")).append("\r\n");
    buf.append("REQUEST_URI: ").append(req.uri()).append("\r\n\r\n");

    HttpHeaders headers = req.headers();
    if (!headers.isEmpty()) {
        for (Map.Entry<String, String> h: headers) {
            CharSequence key = h.getKey();
            CharSequence value = h.getValue();
            buf.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
        }
        buf.append("\r\n");
    }

    QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri());
    Map<String, List<String>> params = queryStringDecoder.parameters();
    if (!params.isEmpty()) {
        for (Map.Entry<String, List<String>> p: params.entrySet()) {
            String key = p.getKey();
            List<String> vals = p.getValue();
            for (String val : vals) {
                buf.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
            }
        }
        buf.append("\r\n");
    }
    
    ByteBuf content;
    Optional<ByteBuf> body = req.body();
    if (body.isPresent() && body.get().isReadable()) {
        buf.append("CONTENT: ");
        buf.append(body.get().toString(CharsetUtil.UTF_8));
        buf.append("\r\n");
        content = Unpooled.copiedBuffer(body.get());
    }
    else {
      content = Unpooled.EMPTY_BUFFER;
    }

    buf.append("END OF CONTENT\r\n");

    HttpResponse<ByteBuf> response = HttpResponse.of(HTTP_1_1, 
        req.decoderResult().isSuccess()? OK : BAD_REQUEST, 
        req.headers(), content
    );
    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
    writeAndClose(ctx, response);
    return Optional.empty();
  }


}