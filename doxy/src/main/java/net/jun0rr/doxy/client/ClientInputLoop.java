/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import net.jun0rr.doxy.DoxyChannel;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.impl.AbstractRunnable;
import net.jun0rr.doxy.impl.DoxyChannelImpl;
import us.pserver.tools.Hash;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class ClientInputLoop extends AbstractRunnable {
  
  private final ServerSocketChannel server;
  
  public ClientInputLoop(DoxyEnvironment env) {
    super(env);
    this.server = Unchecked.call(()->ServerSocketChannel.open());
  }
  
  private void bindServer() throws IOException {
    server.bind(new InetSocketAddress(
        env.configuration().getHost(), 
        env.configuration().getPort())
    );
  }
  
  @Override
  public void run() {
    try {
      try(server) {
        bindServer();
        while(isRunning()) {
          env.executor().submit(reading(doxyChannel(server.accept())));
        }
      }
    }
    catch(IOException e) {
      throw Unchecked.unchecked(e);
    }
  }
  
  private DoxyChannel doxyChannel(SocketChannel sc) {
    String sid = Hash.sha256().of(String.join("->", 
        Unchecked.call(()->sc.getLocalAddress()).toString(), 
        Unchecked.call(()->sc.getRemoteAddress()).toString())
    );
    DoxyChannel ch = new DoxyChannelImpl(env, sid, sc);
    env.channels().add(ch);
    return ch;
  }
  
  private Runnable reading(DoxyChannel ch) {
    return () -> {
      try(ch) {
        while(true) {
          ch.readPacket().ifPresent(env.http()::send);
        }
      }
      catch(EOFException o) {
        //No more data available, channel will be closed.
      }
      catch(Exception e) {
        throw Unchecked.unchecked(e);
      }
    };
  }
  
}
