/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Key;
import java.util.zip.GZIPInputStream;
import javax.crypto.Cipher;
import net.jun0rr.doxy.Packet;
import us.pserver.tools.Unchecked;
import us.pserver.tools.io.BitBuffer;


/**
 *
 * @author juno
 */
public class PacketDecoder {
  
  private final Cipher cipher;
  
  public PacketDecoder(String algo, Key key) {
    cipher = Unchecked.call(()->Cipher.getInstance(algo));
    Unchecked.call(()->cipher.init(Cipher.DECRYPT_MODE, key));
  }
  
  public Packet decode(ByteBuffer buf) {
    ByteBuffer decbuf = alloc(cipher.getOutputSize(buf.remaining()), buf.isDirect());
    Unchecked.call(()->cipher.doFinal(buf, decbuf));
    decbuf.flip();
    return Packet.decode(decbuf);
  }
  
  public ByteBuffer decompress(ByteBuffer buf) {
    BitBuffer inbuf = BitBuffer.dynamicBuffer(buf);
    BitBuffer outbuf = BitBuffer.dynamicBuffer(buf.remaining(), buf.isDirect());
    try(GZIPInputStream gin = new GZIPInputStream(inbuf.getInputStream())) {
      byte[] bs = new byte[buf.remaining()];
      int read = -1;
      while((read = gin.read(bs)) != -1) {
        outbuf.put(bs, 0, read);
      }
      return outbuf.flip().toByteBuffer();
    }
    catch(IOException e) {
      throw Unchecked.unchecked(e);
    }
  }
  
  public Packet decodeDecompress(ByteBuffer buf) {
    return decode(decompress(buf));
  }
  
  private ByteBuffer alloc(int size, boolean direct) {
    return direct ? ByteBuffer.allocateDirect(size) : ByteBuffer.allocate(size);
  }
  
}
