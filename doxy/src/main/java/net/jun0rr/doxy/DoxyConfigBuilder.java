/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import net.jun0rr.doxy.impl.DoxyConfigImpl;


/**
 *
 * @author Juno
 */
public class DoxyConfigBuilder {
  
  public static final String LOCALHOST = "localhost";
  
  public static final String DEFAULT_SERVER_NAME = "Doxy-0.1-SNAPSHOT";
  
  public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";
  
  public static final String DEFAULT_CRYPT_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
  
  public static final int DEFAULT_HOST_PORT = 3333;
  
  public static final int DEFAULT_TARGET_PORT = 6060;
  
  public static final int DEFAULT_PROXY_PORT = 40080;
  
  public static final int DEFAULT_BUFFER_SIZE = 8*1024;
  
  public static final int DEFAULT_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
  
  
  private final HostConfig host;
  
  private final HostConfig target;
  
  private final HostConfig proxy;
  
  private final String proxyUser;
  
  private final char[] proxyPass;
  
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
  
  public DoxyConfigBuilder() {
    this.host = HostConfig.of(LOCALHOST, DEFAULT_HOST_PORT);
    this.target = HostConfig.of(LOCALHOST, DEFAULT_TARGET_PORT);
    this.proxy = HostConfig.of(LOCALHOST, DEFAULT_PROXY_PORT);
    this.threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
    this.proxyUser = null;
    this.proxyPass = null;
    this.serverName = DEFAULT_SERVER_NAME;
    this.userAgent = DEFAULT_USER_AGENT;
    this.bufferSize = DEFAULT_BUFFER_SIZE;
    this.directBuffer = true;
    this.kspath = null;
    this.pkpath = null;
    this.pubpath = null;
    this.kspass = null;
    this.cryptAlg = DEFAULT_CRYPT_ALGORITHM;
  }


  public DoxyConfigBuilder(HostConfig host, HostConfig target, HostConfig proxy, String proxyUser, char[] proxyPass, Path privateKey, Path publicKey, Path keystore, char[] keystorePass, String serverName, String userAgent, String cryptAlg, int threadPoolSize, int bufferSize, boolean directBuffer) {
    this.host = host;
    this.target = target;
    this.proxy = proxy;
    this.threadPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    this.proxyUser = proxyUser;
    this.proxyPass = proxyPass;
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
  
  
  public DoxyConfigBuilder host(String hostname, int port) {
    return new DoxyConfigBuilder(HostConfig.of(hostname, port), target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder target(String hostname, int port) {
    return new DoxyConfigBuilder(host, HostConfig.of(hostname, port), proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder proxy(String hostname, int port) {
    return new DoxyConfigBuilder(host, target, HostConfig.of(hostname, port), proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder threadPoolSize(int threadPoolSize) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder bufferSize(int bufferSize) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder directBuffer(boolean directBuffer) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder proxyUser(String proxyUser) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder proxyPassword(char[] proxyPass) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder serverName(String serverName) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder userAgent(String userAgent) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder privateKeyPath(Path pkpath) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder publicKeyPath(Path pubpath) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder keystorePath(Path kspath) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder keystorePassword(char[] kspass) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder cryptAlgorithm(String cryptAlg) {
    return new DoxyConfigBuilder(host, target, proxy, proxyUser, proxyPass, pkpath, pubpath, kspath, kspass, serverName, userAgent, cryptAlg, threadPoolSize, bufferSize, directBuffer);
  }
  
  public HostConfig getHost() {
    return host;
  }
  
  public HostConfig getTarget() {
    return target;
  }
  
  public HostConfig getProxy() {
    return proxy;
  }
  
  public String getProxyUser() {
    return proxyUser;
  }
  
  public char[] getProxyPassword() {
    return proxyPass;
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
  
  public DoxyConfig build() {
    Objects.requireNonNull(host, "Bad null Host");
    Objects.requireNonNull(target, "Bad null Target");
    Objects.requireNonNull(cryptAlg, "Bad null cryptography algorithm");
    if(threadPoolSize <= 0) throw new IllegalStateException("Bad thread pool size: " + threadPoolSize);
    if(bufferSize <= 0) throw new IllegalStateException("Bad buffer size: " + bufferSize);
    return new DoxyConfigImpl(
        host, 
        target, 
        ProxyConfig.of(proxy, proxyUser, proxyPass),
        SecurityConfig.of(pkpath, pubpath, kspath, kspass, cryptAlg),
        serverName,
        userAgent,
        threadPoolSize, 
        bufferSize, 
        directBuffer
    );
  }
  
  
  
  public static DoxyConfigBuilder newBuilder() {
    return new DoxyConfigBuilder();
  }
  
}
