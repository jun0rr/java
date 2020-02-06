/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import net.jun0rr.doxy.cfg.*;


/**
 *
 * @author juno
 */
public interface HttpServerConfig {
  
  public Host getBindHost();
  
  public SecurityConfig getSecurityConfig();
  
  public String getServerName();
  
  public String getUserAgent();
  
  public int getBufferSize();
  
  public int getThreadPoolSize();
  
  public boolean isDirectBuffer();
  
  
  
  public static HttpServerConfigBuilder builder() {
    return HttpServerConfigBuilder.newBuilder();
  }
  
  public static HttpServerConfig of(Host bind, SecurityConfig security, String serverName, String userAgent, int threadPoolSize, int bufferSize, boolean directBuffer) {
    return new HttpServerConfigImpl(bind, security, serverName, userAgent, threadPoolSize, bufferSize, directBuffer);
  }
  
  
  
  
  
  public static class HttpServerConfigImpl implements HttpServerConfig {

    private final Host host;

    private final SecurityConfig security;

    private final int threadPoolSize;

    private final int bufferSize;

    private final boolean directBuffer;

    private final String serverName;

    private final String userAgent;


    public HttpServerConfigImpl(Host bind, SecurityConfig security, String serverName, String userAgent, int threadPoolSize, int bufferSize, boolean directBuffer) {
      this.host = bind;
      this.security = security;
      this.threadPoolSize = threadPoolSize;
      this.bufferSize = bufferSize;
      this.directBuffer = directBuffer;
      this.serverName = serverName;
      this.userAgent = userAgent;
    }

    @Override
    public Host getBindHost() {
      return host;
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
      return "HttpServerConfig{" 
          + "  - bind=" + host + "\n"
          + "  - serverName=" + serverName + "\n"
          + "  - userAgent=" + userAgent + "\n"
          + "  - security=" + security + "\n"
          + "  - threadPoolSize=" + threadPoolSize + "\n"
          + "  - bufferSize=" + bufferSize + "\n"
          + "  - directBuffer=" + directBuffer + "\n"
          + '}';
    }

  }

  
}
