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
  
  private final String sourceID;
  
  private final String targetID;
  
  private final long order;
  
  private final SecKey key;
  
  private final BitBuffer data;
  
  public PacketImpl(String srcid, String tgtid, long ord, SecKey key, BitBuffer data) {
    this.sourceID = Objects.requireNonNull(srcid, "Bad null Source ID");
    this.targetID = Objects.requireNonNull(srcid, "Bad null Target ID");
    this.order = ord;
    this.key = key;
    this.data = data;
  }
  
  @Override
  public String getSourceID() {
    return sourceID;
  }
  
  @Override
  public String getTargetID() {
    return targetID;
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
