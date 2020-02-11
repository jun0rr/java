/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.jun0rr.doxy.server.http.HttpServerConfig;


/**
 *
 * @author juno
 */
public class SSLHandlerFactory {
  
  private static SSLContext newServerSSLContext(HttpServerConfig cfg) throws IOException {
    try {
      KeyStore ks = KeyStore.getInstance(
          cfg.getKeystorePath().toFile(), 
          cfg.getKeystorePassword()
      );
      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(ks, cfg.getKeystorePassword());
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(kmf.getKeyManagers(), TRUST_ALL_CERTS, null);
      return ctx;
    } catch(KeyManagementException 
        | KeyStoreException 
        | NoSuchAlgorithmException 
        | UnrecoverableKeyException 
        | CertificateException ex) {
      throw new IOException(ex.toString(), ex);
    }
  }
  
  public static SslHandler newServerHandler(HttpServerConfig cfg) throws IOException {
    if(cfg.getKeystorePath() == null || !Files.exists(cfg.getKeystorePath())) {
      throw new IllegalStateException("Bad null/missing keystore file (cfg.getKeystorePath): " + cfg.getKeystorePath());
    }
    if(cfg.getKeystorePassword()== null || cfg.getKeystorePassword().length < 1) {
      throw new IllegalStateException("Bad null/empty keystore password (cfg.getKeystorePass)");
    }
    SSLContext ctx = newServerSSLContext(cfg);
    SSLEngine eng = ctx.createSSLEngine();
    eng.setUseClientMode(false);
    eng.setNeedClientAuth(false);
    return new SslHandler(eng);
  }
  
  public static SslHandler newServerHandler2(HttpServerConfig cfg, ByteBufAllocator alloc) throws IOException {
    if(cfg.getKeystorePath() == null || !Files.exists(cfg.getKeystorePath())) {
      throw new IllegalStateException("Bad null/missing keystore file (cfg.getKeystorePath): " + cfg.getKeystorePath());
    }
    if(cfg.getKeystorePassword()== null || cfg.getKeystorePassword().length < 1) {
      throw new IllegalStateException("Bad null/empty keystore password (cfg.getKeystorePass)");
    }
    try {
      KeyStore ks = KeyStore.getInstance(
          cfg.getKeystorePath().toFile(), 
          cfg.getKeystorePassword()
      );
      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(ks, cfg.getKeystorePassword());
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
  
  public static SslHandler newClientHandler(ByteBufAllocator alloc) throws IOException {
    return SslContextBuilder.forClient()
        .trustManager(InsecureTrustManagerFactory.INSTANCE)
        .build()
        .newHandler(alloc);
  }
  
  
  
	private static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[] { new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		public void checkClientTrusted(X509Certificate[] certs, String authType) {}
		public void checkServerTrusted(X509Certificate[] certs, String authType) {}
		} 
	};
  
}
