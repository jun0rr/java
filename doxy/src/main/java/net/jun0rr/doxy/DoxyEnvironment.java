/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.util.List;
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
  
  public List<Packet> inbox();
  
  public List<DoxyChannel> channels();
  
  public Optional<DoxyChannel> getChannelById(String id);
  
  public HttpPacketRequest http();
  
}
