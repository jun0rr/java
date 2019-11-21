/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

import java.nio.file.Path;
import java.util.Objects;
import net.jun0rr.doxy.DoxyConfig;
import net.jun0rr.doxy.HostConfig;
import net.jun0rr.doxy.ProxyConfig;
import net.jun0rr.doxy.SecurityConfig;


/**
 *
 * @author Juno
 */
public class DoxyConfigImpl implements DoxyConfig {
  
  private final HostConfig host;
  
  private final HostConfig server;
  
  private final HostConfig remote;
  
  private final ProxyConfig proxy;
  
  private final SecurityConfig security;
  
  private final int threadPoolSize;
  
  private final int bufferSize;
  
  private final boolean directBuffer;
  
  private final String serverName;
  
  private final String userAgent;
  
  
  public DoxyConfigImpl(HostConfig host, HostConfig server, HostConfig remote, ProxyConfig proxy, SecurityConfig security, String serverName, String userAgent, int threadPoolSize, int bufferSize, boolean directBuffer) {
    this.host = host;
    this.server = server;
    this.remote = remote;
    this.proxy = proxy;
    this.security = security;
    this.threadPoolSize = threadPoolSize;
    this.bufferSize = bufferSize;
    this.directBuffer = directBuffer;
    this.serverName = serverName;
    this.userAgent = userAgent;
  }
  
  @Override
  public HostConfig getHost() {
    return host;
  }
  
  @Override
  public HostConfig getServerHost() {
    return server;
  }
  
  @Override
  public HostConfig getRemoteHost() {
    return remote;
  }
  
  @Override
  public ProxyConfig getProxyConfig() {
    return proxy;
  }
  
  @Override
  public SecurityConfig getSecurityConfig() {
    return security;
  }
  
  @Override
  public String getServerName() {
    return serverName;
  }
  
  @Override
  public String getUserAgent() {
    return userAgent;
  }
  
  @Override
  public int getBufferSize() {
    return bufferSize;
  }
  
  @Override
  public int getThreadPoolSize() {
    return threadPoolSize;
  }
  
  @Override
  public boolean isDirectBuffer() {
    return directBuffer;
  }
  
  @Override
  public String toString() {
    return "DoxyConfig{" 
        + "  - port=" + host + "\n"
        + "  - server=" + server + "\n"
        + "  - remote=" + remote + "\n"
        + "  - proxy=" + proxy + "\n"
        + "  - security=" + security + "\n"
        + "  - serverName=" + serverName + "\n"
        + "  - userAgent=" + userAgent + "\n"
        + "  - threadPoolSize=" + threadPoolSize + "\n"
        + "  - bufferSize=" + bufferSize + "\n"
        + "  - directBuffer=" + directBuffer + "\n"
        + '}';
  }
  
}
