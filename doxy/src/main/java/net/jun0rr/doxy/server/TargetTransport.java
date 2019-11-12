/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import net.jun0rr.doxy.DoxyChannel;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.impl.AbstractRunnable;
import net.jun0rr.doxy.impl.DoxyChannelImpl;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class TargetTransport extends AbstractRunnable {
  
  private final BlockingDeque<Packet> outbox;
  
  private final BlockingDeque<Packet> inbox;
  
  public TargetTransport(DoxyEnvironment env) {
    super(env);
    this.outbox = new LinkedBlockingDeque<>();
    this.inbox = new LinkedBlockingDeque<>();
  }
  
  public BlockingDeque<Packet> outbox() {
    return outbox;
  }
  
  public BlockingDeque<Packet> inbox() {
    return outbox;
  }
  
  private Optional<Packet> pollOutbox() {
    return Optional.ofNullable(Unchecked.call(()->
        outbox.pollFirst(100, TimeUnit.MILLISECONDS))
    );
  }
  
  @Override
  public void run() {
    while(isRunning()) {
      pollOutbox().ifPresent(p->
          env.executor().submit(writingPacket(p))
      );
    }
  }
  
  private Runnable writingPacket(Packet p) {
    return () -> {
      try {
        env.getChannelById(p.getID())
            .orElse(createChannel(p.getID()))
            .writePacket(p);
      }
      catch(IOException e) {
        throw Unchecked.unchecked(e);
      }
    };
  }
  
  private DoxyChannel createChannel(String id) throws IOException {
    SocketAddress target = new InetSocketAddress(
        env.configuration().getTargetHost(), 
        env.configuration().getTargetPort()
    );
    SocketChannel sock = SocketChannel.open(target);
    DoxyChannel ch = new DoxyChannelImpl(env, id, sock);
    env.channels().add(ch);
    env.executor().submit(readingLoop(ch));
    return ch;
  }
  
  private Runnable readingLoop(DoxyChannel ch) {
    return () -> {
      try(ch) {
        while(true) {
          ch.readPacket().ifPresent(inbox::offerLast);
        }
      }
      catch(EOFException e) {
        //No more data available, channel will be closed.
      }
      catch(IOException e) {
        throw Unchecked.unchecked(e);
      }
    };
  }
  
}
