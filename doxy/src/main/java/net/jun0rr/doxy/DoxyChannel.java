/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import io.netty.channel.Channel;


/**
 *
 * @author juno
 */
public interface DoxyChannel extends AutoCloseable {
  
  public DoxyEnvironment environment();
  
  public String uid();
  
  public long nextOrder();
  
  public Channel channel();
  
  @Override public void close();
  
  public void writePacket(Packet p);
  
}
