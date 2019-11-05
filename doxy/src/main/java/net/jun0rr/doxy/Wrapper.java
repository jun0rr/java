/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import net.jun0rr.doxy.impl.PacketImpl;
import us.pserver.tools.Hash;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class Wrapper {
  
  private static final AtomicLong ORDER = new AtomicLong(0L);
  
  public static long nextOrder() {
    return ORDER.getAndIncrement();
  }
  
  public static Optional<Packet> readPacket(SocketChannel src, ByteBuffer buf, DoxyConfig cfg) throws IOException {
    int read = src.read(buf);
    if(read == 0) return Optional.empty();
    else if(read == -1) throw new IOException("Socket disconnected: " + src.getLocalAddress().toString());
    Hash sid = Hash.sha256().put(src.getLocalAddress().toString());
    Hash tid = Hash.sha256().put(String.format("%s:%d", cfg.getTargetHost(), cfg.getTargetPort()));
    buf.flip();
    return Optional.of(new PacketImpl(sid.get(), tid.get(), nextOrder(), null, BitBuffer.of(buf)));
  }
  
}
