/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.nio.channels.SocketChannel;
import java.util.Objects;


/**
 *
 * @author juno
 */
public class ClientConnectionHandler implements Runnable {
  
  private final SocketChannel source;
  
  private final DoxyClientConfig config;
  
  public ClientConnectionHandler(DoxyClientConfig cfg, SocketChannel src) {
    this.source = Objects.requireNonNull(src, "Bad null SocketChannel");
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyClientConfig");
  }
  
  public SocketChannel getSourceChannel() {
    return source;
  }
  
  public DoxyClientConfig getConfig() {
    return config;
  }
  
  @Override
  public void run() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
