/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;
import java.util.Optional;
import javax.net.ssl.KeyManagerFactory;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class SSLHandlerFactory {
  
  private final Optional<Path> kspath;
  
  private final Optional<char[]> kspass;
  
  private final ByteBufAllocator alloc;
  
  
  public SSLHandlerFactory(ByteBufAllocator alloc, Path kspath, char[] kspass) {
    this.kspath = Optional.ofNullable(kspath);
    this.kspass = Optional.ofNullable(kspass);
    this.alloc = Objects.requireNonNull(alloc, "Bad null ByteBufAllocator");
  }
  
  public SSLHandlerFactory(ByteBufAllocator alloc) {
    this(alloc, null, null);
  }
  
  
  public static SSLHandlerFactory forServer(ByteBufAllocator alloc, Path kspath, char[] kspass) {
    return new SSLHandlerFactory(alloc,
        Objects.requireNonNull(kspath, "Bad null keystore path"), 
        Objects.requireNonNull(kspass, "Bad null keystore password")
    );
  }
  
  public static SSLHandlerFactory forClient(ByteBufAllocator alloc) {
    return new SSLHandlerFactory(alloc);
  }
  
  
  public SslHandler create() {
    return (kspath.isPresent() && kspass.isPresent()) 
        ? Unchecked.call(()->newServerHandler())
        : Unchecked.call(()->newClientHandler());
  }
  
  private SslHandler newServerHandler() throws IOException {
    if(kspath.isEmpty() || !Files.exists(kspath.get())) {
      throw new IllegalStateException("Bad null/missing keystore file: " + kspath);
    }
    if(kspass.isEmpty() || kspass.get().length < 1) {
      throw new IllegalStateException("Bad null/empty keystore password: " + kspass);
    }
    try {
      KeyStore ks = KeyStore.getInstance(kspath.get().toFile(), kspass.get());
      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(ks, kspass.get());
      return SslContextBuilder.forServer(kmf)
          .trustManager(InsecureTrustManagerFactory.INSTANCE)
          .build()
          .newHandler(alloc);
    } 
    catch(KeyStoreException 
        | NoSuchAlgorithmException 
        | UnrecoverableKeyException 
        | CertificateException ex) {
      throw new IOException(ex.toString(), ex);
    }
  }
  
  private SslHandler newClientHandler() throws IOException {
    return SslContextBuilder.forClient()
        .trustManager(InsecureTrustManagerFactory.INSTANCE)
        .build()
        .newHandler(alloc);
  }
  
}
