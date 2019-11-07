/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import net.jun0rr.doxy.impl.PacketImpl;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public interface Packet {
  
  public String getID();
  
  public long getOrder();
  
  public SecKey getSecKey();
  
  public ByteBuffer getRawData();
  
  public BitBuffer encode();
  
  
  
  public static Packet decode(ByteBuffer buf) {
    long ord = buf.getLong();
    int idlen = buf.getInt();
    ByteBuffer bid = ByteBuffer.allocate(idlen);
    buf.get(bid.array());
    String sid = StandardCharsets.UTF_8.decode(bid).toString();
    return new PacketImpl(sid, buf.slice(), null, ord);
  }
  
}
