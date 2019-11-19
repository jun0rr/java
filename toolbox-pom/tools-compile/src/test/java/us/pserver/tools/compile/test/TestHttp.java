/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.junit.jupiter.api.Test;


/**
 *
 * @author juno
 */
public class TestHttp {
  @Test
  public void method() throws Exception {
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
      HttpClient hc = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .proxy(ProxySelector.of(new InetSocketAddress("localhost", 40080)))
          .sslContext(sc)
          .followRedirects(HttpClient.Redirect.ALWAYS)
          .build();
      HttpRequest req = HttpRequest.newBuilder(new URI("https://www.google.com"))
          .GET()
          .version(HttpClient.Version.HTTP_2)
          .header("Proxy-Authorization", "Basic ZjYwMzY0Nzc6OTg3NDEwMjU=")
          .build();
      System.out.println("* sending request...");
      HttpResponse<String> resp = hc.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
      System.out.printf("* %s - %d%n", resp.version(), resp.statusCode());
      System.out.println("* Headers: ");
      resp.headers().map().forEach((k,v)->System.out.printf("  - %s: %s%n", k, v));
      System.out.println(resp.body());
    }
    catch(Exception e) {
      e.printStackTrace();
      //throw e;
    }
  }
}
