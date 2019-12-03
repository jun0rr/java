/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import net.jun0rr.doxy.HostConfig;
import net.jun0rr.doxy.Packet;


/**
 *
 * @author juno
 */
public class PacketImpl implements Packet {
  
  private final String cid;
  
  private final long order;
  
  private final ByteBuffer data;
  
  private final int orilen;
  
  private final boolean encoded;
  
  private final HostConfig remote;
  
  public PacketImpl(String channelID, ByteBuffer data, HostConfig remote, long ord, int originalLength, boolean encoded) {
    this.cid = Objects.requireNonNull(channelID, "Bad null Source ID");
    this.remote = Objects.requireNonNull(remote, "Bad null remote HostConfig");
    this.order = ord;
    this.data = data;
    this.orilen = originalLength;
    this.encoded = encoded;
  }
  
  @Override
  public String channelID() {
    return cid;
  }
  
  @Override
  public HostConfig remote() {
    return remote;
  }
  
  @Override
  public long order() {
    return order;
  }
  
  @Override
  public ByteBuffer data() {
    return data;
  }
  
  @Override
  public int encodeLength() {
    byte[] raw = remote.toSocketAddr().getAddress().getAddress();
    return Long.BYTES + Integer.BYTES * 4 + Byte.BYTES * 2 + raw.length + cid.length() + data.remaining();
  }
  
  @Override
  public int originalLength() {
    return orilen;
  }
  
  @Override
  public boolean isEncoded() {
    return encoded;
  }
  
  @Override
  public ByteBuffer toByteBuffer() {
    ByteBuffer buf = data.isDirect()
        ? ByteBuffer.allocateDirect(encodeLength())
        : ByteBuffer.allocate(encodeLength());
    byte[] raw = remote.toSocketAddr().getAddress().getAddress();
    System.out.printf("* Packet.toByteBuffer(): rawAddress=%s:%d%n", Arrays.toString(raw), remote.getPort());
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
    return buf.flip();
  }


  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.cid);
    hash = 37 * hash + (int) (this.order ^ (this.order >>> 32));
    hash = 37 * hash + Objects.hashCode(this.remote);
    return hash;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final PacketImpl other = (PacketImpl) obj;
    if (this.order != other.order) {
      return false;
    }
    if (!Objects.equals(this.cid, other.cid)) {
      return false;
    }
    if (!Objects.equals(this.remote, other.remote)) {
      return false;
    }
    return true;
  }


  @Override
  public String toString() {
    return "Packet{" + "cid=" + cid + ", order=" + order + ", data=" + data + ", orilen=" + orilen + ", encoded=" + encoded + ", remote=" + remote + '}';
  }
  
}
