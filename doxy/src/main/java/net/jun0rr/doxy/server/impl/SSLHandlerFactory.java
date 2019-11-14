/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.impl;

import io.netty.handler.ssl.SslHandler;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 *
 * @author juno
 */
public class SSLHandlerFactory {
  
  private final Path keystorePath;
  
  public SSLHandlerFactory(Path keystorePath) {
    this.keystorePath = Objects.requireNonNull(keystorePath, "Bad null keystore Path");
  }
  
  private SSLContext createSSLContext(char[] keystorePass) throws IOException {
    try {
      KeyStore ks = KeyStore.getInstance(keystorePath.toFile(), keystorePass);
      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      kmf.init(ks, keystorePass);
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
  
  public SslHandler create(char[] keystorePass) throws IOException {
    SSLContext ctx = createSSLContext(keystorePass);
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
