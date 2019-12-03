/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import net.jun0rr.doxy.impl.PacketImpl;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public interface Packet {
  
  public String channelID();
  
  public long order();
  
  public int originalLength();
  
  public boolean isEncoded();
  
  public int encodeLength();
  
  public HostConfig remote();
  
  public ByteBuffer data();
  
  public ByteBuffer toByteBuffer();
  
  
  
  public static Packet of(ByteBuffer buf) {
    int bufpos = buf.position();
    int buflim = buf.limit();
    int len = buf.getInt();
    buf.limit(bufpos + len);
    int orilen = buf.getInt();
    long ord = buf.getLong();
    int adrlen = buf.get();
    byte[] addr = new byte[adrlen];
    buf.get(addr);
    int port = buf.getInt();
    InetAddress iadr = Unchecked.call(()->InetAddress.getByAddress(addr));
    HostConfig rem = HostConfig.of(iadr.getHostAddress(), port);
    boolean enc = buf.get() == 1;
    int idlen = buf.getInt();
    String sid = getUTF(buf, idlen);
    ByteBuffer data = buf.slice();
    buf.limit(buflim).position(bufpos + len);
    return new PacketImpl(sid, data, rem, ord, orilen, enc);
  }
  
  private static String getUTF(ByteBuffer buf, int length) {
    int lim = buf.limit();
    buf.limit(buf.position() + length);
    String str = StandardCharsets.UTF_8.decode(buf).toString();
    buf.limit(lim);
    return str;
  }
  
}
