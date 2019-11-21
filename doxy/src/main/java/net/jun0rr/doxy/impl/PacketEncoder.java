/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Key;
import java.util.zip.GZIPOutputStream;
import javax.crypto.Cipher;
import net.jun0rr.doxy.Packet;
import us.pserver.tools.Unchecked;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class PacketEncoder {
  
  private final Cipher cipher;
  
  public PacketEncoder(String algo, Key key) {
    cipher = Unchecked.call(()->Cipher.getInstance(algo));
    Unchecked.call(()->cipher.init(Cipher.ENCRYPT_MODE, key));
  }
  
  public ByteBuffer encode(Packet p) {
    ByteBuffer buf = p.encode();
    ByteBuffer encbuf = alloc(cipher.getOutputSize(buf.remaining()), buf.isDirect());
    Unchecked.call(()->cipher.doFinal(buf, encbuf));
    encbuf.flip();
    return encbuf;
  }
  
  public ByteBuffer compress(ByteBuffer buf) {
    BitBuffer out = BitBuffer.dynamicBuffer(buf.remaining(), buf.isDirect());
    try(GZIPOutputStream gout = new GZIPOutputStream(out.getOutputStream())) {
      byte[] bs = new byte[buf.remaining()];
      buf.get(bs);
      gout.write(bs);
      gout.finish();
      gout.flush();
      return out.flip().toByteBuffer();
    }
    catch(IOException e) {
      throw Unchecked.unchecked(e);
    }
  }
  
  public ByteBuffer encodeCompress(Packet p) {
    return compress(encode(p));
  }
  
  private ByteBuffer alloc(int size, boolean direct) {
    return direct ? ByteBuffer.allocateDirect(size) : ByteBuffer.allocate(size);
  }
  
}
