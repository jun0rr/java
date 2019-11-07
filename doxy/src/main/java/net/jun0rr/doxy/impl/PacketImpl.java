/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

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
  
  private final BitBuffer data;
  
  public PacketImpl(String srcid, long ord, SecKey key, BitBuffer data) {
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
  public BitBuffer getClearData() {
    return data;
  }
  
  @Override
  public BitBuffer getRawData() {
    return data;
  }
  
}
