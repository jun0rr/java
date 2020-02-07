/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import net.jun0rr.doxy.cfg.*;
import java.nio.file.Path;
import java.util.Objects;


/**
 *
 * @author Juno
 */
public class HttpServerConfigBuilder {
  
  private final Path kspath;
  
  private final char[] kspass;
  
  private final String serverName;
  
  private final String userAgent;
  
  
  public HttpServerConfigBuilder() {
    this.serverName = null;
    this.userAgent = null;
    this.kspath = null;
    this.kspass = null;
  }


  public HttpServerConfigBuilder(Path keystore, char[] keystorePass, String serverName, String userAgent) {
    this.serverName = serverName;
    this.userAgent = userAgent;
    this.kspath = keystore;
    this.kspass = keystorePass;
  }
  
  
  public HttpServerConfigBuilder serverName(String serverName) {
    return new HttpServerConfigBuilder(kspath, kspass, serverName, userAgent);
  }
  
  public HttpServerConfigBuilder userAgent(String userAgent) {
    return new HttpServerConfigBuilder(kspath, kspass, serverName, userAgent);
  }
  
  public HttpServerConfigBuilder keystorePath(Path kspath) {
    return new HttpServerConfigBuilder(kspath, kspass, serverName, userAgent);
  }
  
  public HttpServerConfigBuilder keystorePassword(char[] kspass) {
    return new HttpServerConfigBuilder(kspath, kspass, serverName, userAgent);
  }
  
  public Path getKeystorePath() {
    return kspath;
  }
  
  public char[] getKeystorePassword() {
    return kspass;
  }
  
  public String getServerName() {
    return serverName;
  }
  
  public String getUserAgent() {
    return userAgent;
  }
  
  public HttpServerConfig build() {
    return HttpServerConfig.of(
        kspath,
        kspass,
        serverName,
        userAgent
    );
  }
  
  
  
  public static HttpServerConfigBuilder newBuilder() {
    return new HttpServerConfigBuilder();
  }
  
}
