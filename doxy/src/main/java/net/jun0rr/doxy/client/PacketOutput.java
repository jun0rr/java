/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import net.jun0rr.doxy.Packet;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class PacketOutput implements Runnable {
  
  private static final BlockingDeque<Packet> OUTPUT = new LinkedBlockingDeque<>();
  
  private final ExecutorService exec;
  
  private final DoxyClientConfig config;
  
  public PacketOutput(ExecutorService es, DoxyClientConfig cfg) {
    this.exec = Objects.requireNonNull(es, "Bad null ExecutorService");
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyClientConfig");
  }
  
  public static void put(Packet p) {
    if(p != null) {
      Unchecked.call(()->OUTPUT.putLast(p));
    }
  }
  
  
  
  private static class OutputHandler implements Runnable {
    
    private final Packet packet;
    
    public OutputHandler(Packet p) {
      this.packet = Objects.requireNonNull(p, "Bad null Packet");
    }
    
    public void run() {
      
    }
    
  }
  
}
