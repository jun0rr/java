/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import net.jun0rr.doxy.DoxyChannel;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class DoxyChannelImpl implements DoxyChannel {
  
  private final DoxyEnvironment env;
  
  private final String uid;
  
  private final AtomicLong order;
  
  private final SocketChannel socket;
  
  private final ByteBuffer rbuf;
  
  public DoxyChannelImpl(DoxyEnvironment env, String uid, SocketChannel sc) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment (env)");
    this.uid = Objects.requireNonNull(uid, "Bad null uid String");
    this.socket = Objects.requireNonNull(sc, "Bad null SocketChannel (sc)");
    this.order = new AtomicLong(0L);
    this.rbuf = env.configuration().isDirectBuffer()
        ? ByteBuffer.allocateDirect(env.configuration().getBufferSize())
        : ByteBuffer.allocate(env.configuration().getBufferSize());
  }
  
  @Override
  public DoxyEnvironment environment() {
    return env;
  }
  
  @Override
  public String uid() {
    return uid;
  }
  
  @Override
  public long nextOrder() {
    return order.getAndIncrement();
  }
  
  @Override
  public SocketChannel socket() {
    return socket;
  }
  
  @Override
  public void close() {
    Unchecked.call(()->socket.close());
    env.channels().remove(this);
  }
  
  @Override
  public Optional<Packet> readPacket() throws EOFException, IOException {
    rbuf.compact();
    int read = socket.read(rbuf);
    if(read == -1) throw new EOFException();
    else if(read < 1) return Optional.empty();
    else return Optional.of(new PacketImpl(uid, rbuf, nextOrder()));
  }
  
  @Override
  public void writePacket(Packet p) throws IOException {
    socket.write(p.getRawData());
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(this.uid);
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DoxyChannel other = (DoxyChannel) obj;
    return Objects.equals(this.uid(), other.uid());
  }
  
  @Override
  public String toString() {
    return "DoxyChannel{" + "uid=" + uid + ", order=" + order + ", channel=" + socket + '}';
  }
  
}
