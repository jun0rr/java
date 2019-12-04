/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.util.Properties;


/**
 *
 * @author juno
 */
public interface DoxyConfig {
  
  public HostConfig getHost();
  
  public HostConfig getServerHost();
  
  public HostConfig getRemoteHost();
  
  public ProxyConfig getProxyConfig();
  
  public SecurityConfig getSecurityConfig();
  
  public String getServerName();
  
  public String getUserAgent();
  
  public int getBufferSize();
  
  public int getThreadPoolSize();
  
  public boolean isDirectBuffer();
  
  public long getServerTimeout();
  
  
  
  public static DoxyConfigBuilder builder() {
    return DoxyConfigBuilder.newBuilder();
  }
  
  public static DoxyConfig of(Properties prop) {
    return builder().loadProperties(prop).build();
  }
  
  public static DoxyConfig of(HostConfig host, HostConfig server, HostConfig remote, ProxyConfig proxy, SecurityConfig security, String serverName, String userAgent, int threadPoolSize, int bufferSize, boolean directBuffer, long timeout) {
    return new DoxyConfigImpl(host, server, remote, proxy, security, serverName, userAgent, threadPoolSize, bufferSize, directBuffer, timeout);
  }
  
  
  
  
  
  public static class DoxyConfigImpl implements DoxyConfig {

    private final HostConfig host;

    private final HostConfig server;

    private final HostConfig remote;

    private final ProxyConfig proxy;

    private final SecurityConfig security;

    private final int threadPoolSize;

    private final int bufferSize;

    private final long timeout;

    private final boolean directBuffer;

    private final String serverName;

    private final String userAgent;


    public DoxyConfigImpl(HostConfig host, HostConfig server, HostConfig remote, ProxyConfig proxy, SecurityConfig security, String serverName, String userAgent, int threadPoolSize, int bufferSize, boolean directBuffer, long timeout) {
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
      this.timeout = timeout;
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
    public long getServerTimeout() {
      return timeout;
    }

    @Override
    public String toString() {
      return "DoxyConfig{" 
          + "  - host=" + host + "\n"
          + "  - server=" + server + "\n"
          + "  - serverName=" + serverName + "\n"
          + "  - serverTimeout=" + timeout + "\n"
          + "  - remote=" + remote + "\n"
          + "  - proxy=" + proxy + "\n"
          + "  - security=" + security + "\n"
          + "  - userAgent=" + userAgent + "\n"
          + "  - threadPoolSize=" + threadPoolSize + "\n"
          + "  - bufferSize=" + bufferSize + "\n"
          + "  - directBuffer=" + directBuffer + "\n"
          + '}';
    }

  }

  
}
