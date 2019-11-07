/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.impl.PacketImpl;
import us.pserver.tools.Unchecked;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author Juno
 */
public class ClientInputHandler implements Runnable {
  
  private final DoxyEnvironment env;
  
  private final String sockid;
  
  private final SocketChannel socket;
  
  private final ByteBuffer rbuf;
  
  public ClientInputHandler(DoxyEnvironment env, String sid, SocketChannel sc) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment (env)");
    this.sockid = Objects.requireNonNull(sid, "Bad null SocketID (sid)");
    this.socket = Objects.requireNonNull(sc, "Bad null SocketChannel (sc)");
    this.rbuf = env.configuration().isDirectBuffer()
        ? ByteBuffer.allocateDirect(env.configuration().getBufferSize())
        : ByteBuffer.allocate(env.configuration().getBufferSize());
  }
  
  @Override
  public void run() {
    try {
      try (socket) {
        int read;
        while((read = socket.read(rbuf)) != -1) {
          if(read > 0) {
            rbuf.flip();
            BitBuffer data = BitBuffer.of(env.configuration().getBufferSize(), env.configuration().isDirectBuffer());
            data.put(rbuf);
            data.flip();
            env.http().send(new PacketImpl(sockid, env.nextPacketOrder(), null, data));
          }
          rbuf.compact();
        }
        env.channels().remove(sockid);
      }
    }
    catch(IOException e) {
      throw Unchecked.unchecked(e);
    }
  }
  
}
