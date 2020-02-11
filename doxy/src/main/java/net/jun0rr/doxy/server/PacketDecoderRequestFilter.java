/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;
import net.jun0rr.doxy.common.DoxyEnvironment;
import net.jun0rr.doxy.common.Packet;
import net.jun0rr.doxy.common.PacketDecoder;
import net.jun0rr.doxy.server.http.HttpRequest;
import net.jun0rr.doxy.server.http.HttpRequestFilter;


/**
 *
 * @author Juno
 */
public class PacketDecoderRequestFilter implements HttpRequestFilter {
  
  private final DoxyEnvironment env;
  
  private final PacketDecoder decoder;
  
  public PacketDecoderRequestFilter(DoxyEnvironment env) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment");
    this.decoder = new PacketDecoder(env.configuration().getSecurityConfig().getCryptAlgorithm(), env.getPublicKey());
  }
  
  @Override
  public Optional<HttpRequest> filter(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
    Optional<ByteBuf> body = req.content();
    if(!HttpMethod.POST.equals(req.method()) 
        || req.content().isEmpty() 
        || req.<ByteBuf>content().get().readableBytes() < 1) {
      return Optional.of(req);
    }
    ByteBuffer cont = env.alloc(body.get().readableBytes());
    body.get().readBytes(cont);
    cont.flip();
    body.get().release();
    Packet p = decoder.decodePacket(Packet.of(cont));
    return Optional.of(HttpRequest.of(req.protocolVersion(), req.method(), req.uri(), req.headers(), p));
  }
  
}
