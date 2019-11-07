/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

import java.nio.ByteBuffer;
import java.util.Objects;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.SecKey;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class PacketImpl implements Packet {
  
  private final String sid;
  
  private final long order;
  
  private final SecKey key;
  
  private final ByteBuffer data;
  
  public PacketImpl(String srcid, ByteBuffer data, SecKey key, long ord) {
    this.sid = Objects.requireNonNull(srcid, "Bad null Source ID");
    this.order = ord;
    this.key = key;
    this.data = data;
  }
  
  @Override
  public String getID() {
    return sid;
  }
  
  @Override
  public long getOrder() {
    return order;
  }
  
  @Override
  public SecKey getSecKey() {
    return key;
  }
  
  @Override
  public ByteBuffer getRawData() {
    return data;
  }
  
  @Override
  public BitBuffer encode() {
    int len = Long.BYTES + Integer.BYTES + sid.length() + data.remaining();
    BitBuffer buf = BitBuffer.dynamicBuffer(len, data.isDirect());
    buf.putLong(order)
        .putInt(sid.length())
        .putUTF8(sid)
        .put(data);
    return buf.flip();
  }
  
}
