/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import io.netty.channel.Channel;
import java.io.EOFException;
import java.io.IOException;
import java.util.Optional;


/**
 *
 * @author juno
 */
public interface DoxyChannel extends AutoCloseable {
  
  public DoxyEnvironment environment();
  
  public String uid();
  
  public String authToken();
  
  public long nextOrder();
  
  public Channel channel();
  
  @Override public void close();
  
  public Optional<Packet> readPacket() throws EOFException, IOException;
  
  public void writePacket(Packet p) throws IOException;
  
}
