/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.impl;

import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;
import javax.crypto.Cipher;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.impl.PacketImpl;
import net.jun0rr.doxy.server.HttpRequest;
import net.jun0rr.doxy.server.HttpRequestFilter;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class DecryptRequestFilter implements HttpRequestFilter {
  
  private final DoxyEnvironment env;
  
  private final Cipher cipher;
  
  public DecryptRequestFilter(DoxyEnvironment env) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment");
    this.cipher = Unchecked.call(()->Cipher.getInstance(
        env.configuration().getSecurityConfig().getCryptAlgorithm())
    );
    Unchecked.call(()->cipher.init(Cipher.DECRYPT_MODE, env.getPrivateKey()));
  }
  
  @Override
  public Optional<HttpRequest> filter(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
    Optional<Packet> body = req.body();
    if(body.isEmpty() || body.get().originalLength() < 1) {
      return Optional.of(req);
    }
    ByteBuffer dec = ByteBuffer.allocate(body.get().originalLength());
    cipher.doFinal(body.get().getRawData(), dec);
    dec.flip();
    Packet p = new PacketImpl(body.get().getID(), dec, body.get().getOrder(), dec.remaining());
    return Optional.of(HttpRequest.of(req.protocolVersion(), req.method(), req.uri(), req.headers(), p));
  }
  
}
