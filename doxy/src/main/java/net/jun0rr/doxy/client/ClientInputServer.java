/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import net.jun0rr.doxy.DoxyEnvironment;
import us.pserver.tools.Hash;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class ClientInputServer implements Runnable {
  
  private final DoxyEnvironment env;
  
  private final ServerSocketChannel server;
  
  private volatile boolean running;
  
  public ClientInputServer(DoxyEnvironment env) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment");
    this.server = Unchecked.call(()->ServerSocketChannel.open());
    this.running = false;
  }
  
  public boolean isRunning() {
    return running;
  }
  
  public void start() {
    running = true;
    env.executor().submit(this);
  }
  
  @Override
  public void run() {
    try {
      try (server) {
        server.bind(new InetSocketAddress(env.configuration().getHost(), env.configuration().getPort()));
        while(running) {
          SocketChannel socket = server.accept();
          String sid = Hash.sha256().of(socket.getLocalAddress().toString());
          env.channels().put(sid, socket);
          env.executor().submit(new ClientInputHandler(env, sid, socket));
        }
      }
    }
    catch(Exception e) {
      throw Unchecked.unchecked(e);
    }
  }
  
}
