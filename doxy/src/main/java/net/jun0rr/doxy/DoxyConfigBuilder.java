/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import net.jun0rr.doxy.impl.DoxyConfigImpl;


/**
 *
 * @author Juno
 */
public class DoxyConfigBuilder {
  
  private final int port;
  
  private final String host;
  
  private final int targetPort;
  
  private final String targetHost;
  
  private final int proxyPort;
  
  private final String proxyHost;
  
  private final String proxyUser;
  
  private final String proxyPass;
  
  private final int threadPoolSize;
  
  private final int bufferSize;
  
  private final boolean directBuffer;
  
  private final String serverName;
  
  private final String userAgent;
  
  
  public DoxyConfigBuilder() {
    this.port = 3333;
    this.targetPort = 6060;
    this.proxyPort = 0;
    this.threadPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    this.host = "0.0.0.0";
    this.targetHost = "localhost";
    this.proxyHost = "";
    this.proxyUser = "user";
    this.proxyPass = "password";
    this.serverName = "Doxy-0.1-SNAPSHOT";
    this.userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";
    this.bufferSize = 8*1024;
    this.directBuffer = true;
  }


  private DoxyConfigBuilder(int port, String host, int targetPort, String targetHost, int proxyPort, String proxyHost, String proxyUser, String proxyPass, String serverName, String userAgent, int threadPoolSize, int bufferSize, boolean directBuffer) {
    this.port = port;
    this.host = host;
    this.targetPort = targetPort;
    this.targetHost = targetHost;
    this.proxyPort = proxyPort;
    this.proxyHost = proxyHost;
    this.proxyUser = proxyUser;
    this.proxyPass = proxyPass;
    this.threadPoolSize = threadPoolSize;
    this.bufferSize = bufferSize;
    this.directBuffer = directBuffer;
    this.serverName = serverName;
    this.userAgent = userAgent;
  }
  
  
  public DoxyConfigBuilder port(int p) {
    return new DoxyConfigBuilder(p, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder targetPort(int p) {
    return new DoxyConfigBuilder(port, host, p, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder proxyPort(int p) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, p, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder threadPoolSize(int p) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, p, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder bufferSize(int p) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, p, directBuffer);
  }
  
  public DoxyConfigBuilder directBuffer(boolean direct) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, direct);
  }
  
  public DoxyConfigBuilder host(String host) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder targetHost(String targetHost) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder proxyHost(String proxyHost) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder proxyUser(String proxyUser) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder proxyPassword(String proxyPass) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder serverName(String serverName) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public DoxyConfigBuilder userAgent(String userAgent) {
    return new DoxyConfigBuilder(port, host, targetPort, targetHost, proxyPort, proxyHost, proxyUser, proxyPass, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  public int getPort() {
    return port;
  }
  
  public String getHost() {
    return host;
  }

  public int getTargetPort() {
    return targetPort;
  }
  
  public String getTargetHost() {
    return targetHost;
  }
  
  public int getProxyPort() {
    return proxyPort;
  }
  
  public String getProxyHost() {
    return proxyHost;
  }
  
  public String getProxyUser() {
    return proxyUser;
  }
  
  public String getProxyPassword() {
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
  
  public byte[] getSecretKey() {
    return null;
  }
  
  public DoxyConfig build() {
    if(port <= 0) throw new IllegalStateException("Bad port: " + port);
    if(targetPort <= 0) throw new IllegalStateException("Bad target port: " + targetPort);
    if(threadPoolSize <= 0) throw new IllegalStateException("Bad thread pool size: " + threadPoolSize);
    if(bufferSize <= 0) throw new IllegalStateException("Bad buffer size: " + bufferSize);
    return new DoxyConfigImpl(
        port, 
        host, 
        targetPort, 
        targetHost, 
        proxyPort, 
        proxyHost, 
        proxyUser, 
        proxyPass,
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
