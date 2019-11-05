/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public interface Packet {
  
  public String getSourceID();
  
  public String getTargetID();
  
  public long getOrder();
  
  public SecKey getSecKey();
  
  public BitBuffer getClearData();
  
  public BitBuffer getRawData();
  
}
