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
  
  private final Host host;
  
  private final Path pkpath;
  
  private final Path pubpath;
  
  private final Path kspath;
  
  private final char[] kspass;
  
  private final int threadPoolSize;
  
  private final int bufferSize;
  
  private final boolean directBuffer;
  
  private final String serverName;
  
  private final String userAgent;
  
  private final String cryptAlg;
  
  
  public HttpServerConfigBuilder() {
    this.host = null;
    this.threadPoolSize = 0;
    this.serverName = null;
    this.userAgent = null;
    this.bufferSize = 0;
    this.directBuffer = false;
    this.kspath = null;
    this.pkpath = null;
    this.pubpath = null;
    this.kspass = null;
    this.cryptAlg = null;
  }


  public HttpServerConfigBuilder(Host host, Path privateKey, Path publicKey, Path keystore, char[] keystorePass, String serverName, String userAgent, String cryptAlg, int threadPoolSize, int bufferSize, boolean directBuffer) {
    this.host = host;
    this.threadPoolSize = threadPoolSize;
    this.serverName = serverName;
    this.userAgent = userAgent;
    this.bufferSize = bufferSize;
    this.directBuffer = directBuffer;
    this.kspath = keystore;
    this.pkpath = privateKey;
    this.pubpath = publicKey;
    this.kspass = keystorePass;
    this.cryptAlg = cryptAlg;
  }
  
  
  public HttpServerConfigBuilder bindHost(String hostname, int port) {
    return new HttpServerConfigBuilder(Host.of(hostname, port), pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder bindHost(Host host) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder threadPoolSize(int threadPoolSize) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder bufferSize(int bufferSize) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder directBuffer(boolean directBuffer) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder serverName(String serverName) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder userAgent(String userAgent) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder privateKeyPath(Path pkpath) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder publicKeyPath(Path pubpath) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder keystorePath(Path kspath) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder keystorePassword(char[] kspass) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HttpServerConfigBuilder cryptAlgorithm(String cryptAlg) {
    return new HttpServerConfigBuilder(host, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public Host getBindHost() {
    return host;
  }
  
  public int getBufferSize() {
    return bufferSize;
  }
  
  public int getThreadPoolSize() {
    return threadPoolSize;
  }
  
  public boolean isDirectBuffer() {
    return directBuffer;
  }
  
  public Path getPrivateKeyPath() {
    return pkpath;
  }
  
  public Path getPublicKeyPath() {
    return pubpath;
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
  
  public String getCryptAlgorithm() {
    return userAgent;
  }
  
  public ConfigSourceBuilder configSources() {
    return new ConfigSourceBuilder();
  }
  
  public HttpServerConfig build() {
    Objects.requireNonNull(host, "Bad null Host");
    Objects.requireNonNull(cryptAlg, "Bad null cryptography algorithm");
    if(threadPoolSize <= 0) throw new IllegalStateException("Bad thread pool size: " + threadPoolSize);
    if(bufferSize <= 0) throw new IllegalStateException("Bad buffer size: " + bufferSize);
    return HttpServerConfig.of(
        host, 
        SecurityConfig.of(pkpath, pubpath, kspath, kspass, cryptAlg),
        serverName,
        userAgent,
        threadPoolSize, 
        bufferSize, 
        directBuffer
    );
  }
  
  
  
  public static HttpServerConfigBuilder newBuilder() {
    return new HttpServerConfigBuilder();
  }
  
}
