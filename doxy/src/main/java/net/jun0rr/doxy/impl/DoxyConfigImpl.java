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
  
  private final HostConfig target;
  
  private final ProxyConfig proxy;
  
  private final SecurityConfig security;
  
  private final int threadPoolSize;
  
  private final int bufferSize;
  
  private final boolean directBuffer;
  
  private final String serverName;
  
  private final String userAgent;
  
  
  public DoxyConfigImpl(HostConfig host, HostConfig target, ProxyConfig proxy, SecurityConfig security, String serverName, String userAgent, int threadPoolSize, int bufferSize, boolean directBuffer) {
    this.host = host;
    this.target = target;
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
  public HostConfig getTarget() {
    return target;
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
        + "  - target=" + target + "\n"
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
