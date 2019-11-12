/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import net.jun0rr.doxy.DoxyChannel;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.impl.AbstractRunnable;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class ClientOutputLoop extends AbstractRunnable {
  
  public static final long SLEEP_MILLIS = 15;
  
  public ClientOutputLoop(DoxyEnvironment env) {
    super(env);
  }
  
  @Override
  public void run() {
    while(isRunning()) {
      env.channels().stream()
          .filter(c->c.socket().isConnected())
          .forEach(c->env.executor().submit(requesting(c)));
      env.channels().stream()
          .filter(c->!c.socket().isConnected())
          .forEach(DoxyChannel::close);
      Unchecked.call(()->Thread.sleep(SLEEP_MILLIS));
    }
  }
  
  private Runnable requesting(DoxyChannel ch) {
    return () -> env.http()
        .receive(ch.uid())
        .ifPresent(p->Unchecked.call(()->ch.writePacket(p)));
  }
  
}
