/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Optional;


/**
 *
 * @author juno
 */
public interface DoxyChannel extends AutoCloseable {
  
  public DoxyEnvironment environment();
  
  public String uid();
  
  public long nextOrder();
  
  public SocketChannel socket();
  
  @Override public void close();
  
  public Optional<Packet> readPacket() throws EOFException, IOException;
  
  public void writePacket(Packet p) throws IOException;
  
}
