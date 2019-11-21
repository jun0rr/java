/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.jun0rr.doxy.DoxyEnvironment;
import net.jun0rr.doxy.Packet;
import net.jun0rr.doxy.impl.BufferBodyPublisher;
import net.jun0rr.doxy.impl.PacketDecoder;
import net.jun0rr.doxy.impl.PacketEncoder;
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
      SSLContext sc = SSLContext.getInstance(TLS);
      sc.init(null, TRUST_ALL_CERTS, new SecureRandom());
      System.setProperty(PROP_DISABLE_SCHEMES, "");
      return sc;
    }
    catch(KeyManagementException | NoSuchAlgorithmException e) {
      throw Unchecked.unchecked(e);
    }
  }
  
  
  private final DoxyEnvironment env;
  
  private final HttpClient client;
  
  private final URI sendUri;
  
  private final URI receiveUri;
  
  private final PacketEncoder encoder;
  
  private final PacketDecoder decoder;
  
  
  public HttpPacketRequest(DoxyEnvironment env) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment");
    this.client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .proxy(ProxySelector.of(env.configuration().getProxyConfig().getProxyHost().toSocketAddr()))
        .sslContext(SSL)
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .executor(env.executor())
        .build();
    this.sendUri = Unchecked.call(()->new URI(new StringBuilder(HTTPS)
        .append(env.configuration().getServerHost())
        .append(URI_ENCODE)
        .toString()));
    this.receiveUri = Unchecked.call(()->new URI(new StringBuilder(HTTPS)
        .append(env.configuration().getServerHost())
        .append(URI_DECODE)
        .toString()));
    this.encoder = new PacketEncoder(env.configuration().getSecurityConfig().getCryptAlgorithm(), env.getPublicKey());
    this.decoder = new PacketDecoder(env.configuration().getSecurityConfig().getCryptAlgorithm(), env.getPrivateKey());
  }
  
  private String getProxyAuthorization() {
    String auth = String.format(AUTH_FORMAT, 
        env.configuration().getProxyConfig().getProxyUser(), 
        new String(env.configuration().getProxyConfig().getProxyPassword())
    );
    return String.format(BASIC_AUTH, Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8)));
  }
  
  public void send(Packet p) {
    HttpRequest req = HttpRequest.newBuilder(sendUri)
        .POST(new BufferBodyPublisher(encoder.encodeCompress(p)))
        .version(HttpClient.Version.HTTP_2)
        .header(HEADER_USER_AGENT, VALUE_USER_AGENT)
        .header(HEADER_PROXY_AUTH, getProxyAuthorization())
        .header(HEADER_X_PID, p.getID())
        .header(HEADER_X_ORDER, String.valueOf(p.getOrder()))
        .build();
    client.sendAsync(req, BodyHandlers.discarding());
  }
  
  public Optional<Packet> receive(String id) {
    HttpRequest req = HttpRequest.newBuilder(receiveUri).GET()
        .version(HttpClient.Version.HTTP_2)
        .header(HEADER_USER_AGENT, VALUE_USER_AGENT)
        .header(HEADER_PROXY_AUTH, getProxyAuthorization())
        .header(HEADER_X_PID, id)
        .build();
    HttpResponse<byte[]> resp = Unchecked.call(()->client.send(req, BodyHandlers.ofByteArray()));
    return resp.statusCode() == HTTP_200_OK
        ? Optional.of(decoder.decodeDecompress(ByteBuffer.wrap(resp.body())))
        : Optional.empty();
  }
  
  
  
  public static final String HEADER_USER_AGENT = "User-Agent";
  
  public static final String HEADER_PROXY_AUTH = "Proxy-Authorization";
  
  public static final String HEADER_X_PID = "x-pid";
  
  public static final String HEADER_X_ORDER = "x-order";
  
  public static final String VALUE_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0";
  
  public static final String AUTH_FORMAT = "%s:%s";
  
  public static final String BASIC_AUTH = "Basic %s";
  
  public static final String HTTPS = "https://";
  
  public static final String HOST_PORT = "%s:%d";
  
  public static final String URI_ENCODE = "/encode";
  
  public static final String URI_DECODE = "/decode";
  
  public static final String PROP_DISABLE_SCHEMES = "jdk.http.auth.tunneling.disabledSchemes";
  
  public static final String TLS = "TLS";
  
  public static final int HTTP_200_OK = 200;
  
}
