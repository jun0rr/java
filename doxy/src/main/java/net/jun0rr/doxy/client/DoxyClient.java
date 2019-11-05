/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.net.ServerSocket;
import java.util.Objects;
import net.jun0rr.doxy.DoxyConfig;


/**
 *
 * @author juno
 */
public class DoxyClient {
  
  private final DoxyConfig config;
  
  private ServerSocket server;
  
  public DoxyClient(DoxyConfig cfg) {
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyConfig");
  }
  
  public DoxyConfig getConfig() {
    return config;
  }
  
  public 
  
}
