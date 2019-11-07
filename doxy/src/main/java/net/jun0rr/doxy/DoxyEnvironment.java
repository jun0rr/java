/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import net.jun0rr.doxy.client.HttpPacketRequest;


/**
 *
 * @author Juno
 */
public interface DoxyEnvironment {
  
  public DoxyConfig configuration();
  
  public ExecutorService executor();
  
  /**
   * Deque holding server responses Packet's.
   * @return Deque holding server responses Packet's.
   */
  public List<Packet> inbox();
  
  public int nextIndex();
  
  public Optional<Packet> nextPacket();
  
  public long nextPacketOrder();
  
  public Map<String,SocketChannel> channels();
  
  public HttpPacketRequest http();
  
}
