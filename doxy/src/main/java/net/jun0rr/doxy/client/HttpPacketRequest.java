/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Objects;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.jun0rr.doxy.Packet;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class HttpPacketRequest {
  
  private static final SSLContext SSL = createSSLContext();
  
  private static SSLContext createSSLContext() {
    try {
      TrustManager[] TRUST_ALL_CERTS = new TrustManager[] { 
        new X509TrustManager() {
          public X509Certificate[] getAcceptedIssuers() { return null; }
          public void checkClientTrusted(X509Certificate[] certs, String authType) {}
          public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        } 
      };
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, TRUST_ALL_CERTS, new SecureRandom());
      System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
      return sc;
    }
    catch(KeyManagementException | NoSuchAlgorithmException e) {
      throw Unchecked.unchecked(e);
    }
  }
  
  
  private final DoxyClientConfig config;
  
  private final HttpClient client;
  
  
  public HttpPacketRequest(DoxyClientConfig cfg) {
    this.config = Objects.requireNonNull(cfg, "Bad null DoxyClientConfig");
    this.client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .proxy(ProxySelector.of(new InetSocketAddress(cfg.getProxyHost(), cfg.getProxyPort())))
        .sslContext(SSL)
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .executor(cfg.getExecutor())
        .build();
  }
  
  public void send(Packet p) throws IOException {
    
  }
  
}
