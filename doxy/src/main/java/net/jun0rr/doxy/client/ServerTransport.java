/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.impl.AbstractRunnable;
import net.jun0rr.doxy.impl.PacketCollection;
import net.jun0rr.doxy.impl.PacketDecoder;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class ServerTransport extends AbstractRunnable {
  
  private final PacketDecoder decoder;
  
  public ServerTransport(DoxyEnvironment env) {
    super(env);
    this.decoder = new PacketDecoder(
        env.configuration().getSecurityConfig().getCryptAlgorithm(), 
        env.getPrivateKey()
    );
  }
  
  private Optional<Packet> pollOutboxTimeout() {
    return Optional.ofNullable(Unchecked.call(()->
        env.outbox().pollFirst(100, TimeUnit.MILLISECONDS))
    );
  }
  
  @Override
  public void run() {
    env.executor().submit(reading());
    while(isRunning()) {
      pollOutboxTimeout().ifPresent(p->
          env.executor().submit(writing(p))
      );
    }
  }
  
  private Runnable writing(Packet p) {
    return () -> {
      env.http().send(p);
    };
  }
  
  private Runnable reading() {
    return () -> {
      while(isRunning()) {
        Optional<PacketCollection> col = env.http().receive();
        col.stream()
            .flatMap(PacketCollection::stream)
            .map(p->decoder.decodePacket(p))
            .forEach(p->env.getChannelById(p.channelID()).ifPresent(c->c.writePacket(p)));
      }
    };
  }
  
}
