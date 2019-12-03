/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
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
    /*
    buf.putInt(encodeLength())
        .putInt(orilen)
        .putLong(order)
        .put((byte)raw.length)
        .put(raw)
        .putInt(remote.getPort())
        .put((byte)(encoded ? 1 : 0))
        .putInt(cid.length())
        .put(StandardCharsets.UTF_8.encode(cid))
        .put(data);
    */
    int len = buf.getInt();
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
    ByteBuffer bid = ByteBuffer.allocate(idlen);
    buf.get(bid.array());
    String sid = StandardCharsets.UTF_8.decode(bid).toString();
    return new PacketImpl(sid, buf.slice(), rem, ord, orilen, enc);
  }
  
}
