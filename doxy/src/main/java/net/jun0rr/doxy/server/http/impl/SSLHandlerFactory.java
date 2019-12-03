/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import io.netty.handler.ssl.SslHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.jun0rr.doxy.DoxyConfig;


/**
 *
 * @author juno
 */
public class SSLHandlerFactory {
  
  private final DoxyConfig config;
  
  public SSLHandlerFactory(DoxyConfig cfg) {
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyConfig");
    if(config.getSecurityConfig().getKeystorePath() == null || !Files.exists(config.getSecurityConfig().getKeystorePath())) {
      throw new IllegalStateException("Bad null/missing keystore file (cfg.getKeystorePath): " + config.getSecurityConfig().getKeystorePath());
    }
    if(config.getSecurityConfig().getKeystorePassword()== null || config.getSecurityConfig().getKeystorePassword().length < 1) {
      throw new IllegalStateException("Bad null/empty keystore password (cfg.getKeystorePass)");
    }
  }
  
  private SSLContext createSSLContext() throws IOException {
    try {
      KeyStore ks = KeyStore.getInstance(
          config.getSecurityConfig().getKeystorePath().toFile(), 
          config.getSecurityConfig().getKeystorePassword()
      );
      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(ks, config.getSecurityConfig().getKeystorePassword());
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
  
  public SslHandler create() throws IOException {
    SSLContext ctx = createSSLContext();
    SSLEngine eng = ctx.createSSLEngine();
    eng.setUseClientMode(false);
    eng.setNeedClientAuth(false);
    return new SslHandler(eng);
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
