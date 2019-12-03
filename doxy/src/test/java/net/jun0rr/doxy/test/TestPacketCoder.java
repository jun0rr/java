/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import net.jun0rr.doxy.DerKeyFactory;
import net.jun0rr.doxy.DoxyConfigBuilder;
import net.jun0rr.doxy.HostConfig;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.impl.PacketDecoder;
import net.jun0rr.doxy.impl.PacketEncoder;
import net.jun0rr.doxy.impl.PacketImpl;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestPacketCoder {
  @Test
  public void cryptDecrypt() {
    try {
      PrivateKey pk = DerKeyFactory.loadPrivateKey(Paths.get("d:/java/doxy-pk.der"));
      PublicKey pub = DerKeyFactory.loadPublicKey(Paths.get("d:/java/doxy-pub.der"));
      String algo = "RSA/ECB/OAEPWithSHA-512AndMGF1Padding";
      //String algo = "RSA/ECB/PKCS1Padding";
      Cipher enc = Cipher.getInstance(algo);
      enc.init(Cipher.ENCRYPT_MODE, pub);
      Cipher dec = Cipher.getInstance(algo);
      dec.init(Cipher.DECRYPT_MODE, pk);
      
      ByteBuffer data = StandardCharsets.UTF_8.encode("Hello World");
      ByteBuffer crypt = ByteBuffer.allocate(enc.getOutputSize(data.remaining()));
      
      enc.doFinal(data, crypt);
      System.out.printf("'%s' -->> '%s'%n", data, crypt);
      data.flip();
      crypt.flip();
      System.out.printf("flip: '%s' -->> '%s'%n", data, crypt);
      System.out.printf("'%s' -->> '%s'%n", StandardCharsets.UTF_8.decode(data), StandardCharsets.UTF_8.decode(crypt));
      data.flip();
      crypt.flip();
      
      data = ByteBuffer.allocate(dec.getOutputSize(crypt.remaining()));
      System.out.printf("beforeDecrypt: '%s' -->> '%s'%n", crypt, data);
      //enc.init(Cipher.DECRYPT_MODE, pk);
      dec.doFinal(crypt, data);
      crypt.flip();
      data.flip();
      System.out.printf("flip: '%s' -->> '%s'%n", crypt, data);
      System.out.printf("'%s' -->> '%s'%n", crypt, StandardCharsets.UTF_8.decode(data));
      data.flip();
      crypt.flip();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void packetEncodeDecode() throws IOException {
    try {
      PrivateKey pk = DerKeyFactory.loadPrivateKey(Paths.get("d:/java/doxy-pk.der"));
      PublicKey pub = DerKeyFactory.loadPublicKey(Paths.get("d:/java/doxy-pub.der"));
      PacketEncoder enc = new PacketEncoder(DoxyConfigBuilder.DEFAULT_CRYPT_ALGORITHM, pub);
      PacketDecoder dec = new PacketDecoder(DoxyConfigBuilder.DEFAULT_CRYPT_ALGORITHM, pk);
      ByteBuffer data = StandardCharsets.UTF_8.encode("Hello World");
      Packet p = new PacketImpl("channel-1", data, HostConfig.of("localhost", 6060), 0L, data.remaining(), false);
      System.out.println(p);
      System.out.println(StandardCharsets.UTF_8.decode(p.data()));
      p.data().flip();

      p = enc.encodePacket(p);
      System.out.println(p);
      System.out.println(StandardCharsets.UTF_8.decode(p.data()));
      p.data().flip();

      p = dec.decodePacket(p);
      System.out.println(p);
      System.out.println(StandardCharsets.UTF_8.decode(p.data()));
      p.data().flip();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
}
