/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

import java.nio.file.Path;
import java.util.Objects;
import net.jun0rr.doxy.DoxyConfig;


/**
 *
 * @author Juno
 */
public class DoxyConfigImpl implements DoxyConfig {
  
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
  
  private final Path keystorePath;
  
  
  public DoxyConfigImpl(int port, String host, int targetPort, String targetHost, int proxyPort, String proxyHost, String proxyUser, String proxyPass, String serverName, String userAgent, int threadPoolSize, int bufferSize, boolean directBuffer, Path keystorePath) {
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
    this.keystorePath = keystorePath;
  }
  
  
  @Override
  public int getPort() {
    return port;
  }
  
  @Override
  public String getHost() {
    return host;
  }

  @Override
  public int getTargetPort() {
    return targetPort;
  }
  
  @Override
  public String getTargetHost() {
    return targetHost;
  }
  
  @Override
  public int getProxyPort() {
    return proxyPort;
  }
  
  @Override
  public String getProxyHost() {
    return proxyHost;
  }
  
  @Override
  public String getProxyUser() {
    return proxyUser;
  }
  
  @Override
  public String getProxyPassword() {
    return proxyPass;
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
  public byte[] getSecretKey() {
    return null;
  }
  
  public Path getSSLKeystorePath() {
    return keystorePath;
  }


  @Override
  public int hashCode() {
    int hash = 7;
    hash = 11 * hash + this.port;
    hash = 11 * hash + Objects.hashCode(this.host);
    hash = 11 * hash + this.targetPort;
    hash = 11 * hash + Objects.hashCode(this.targetHost);
    hash = 11 * hash + this.proxyPort;
    hash = 11 * hash + Objects.hashCode(this.proxyHost);
    hash = 11 * hash + Objects.hashCode(this.proxyUser);
    hash = 11 * hash + Objects.hashCode(this.proxyPass);
    hash = 11 * hash + this.threadPoolSize;
    hash = 11 * hash + this.bufferSize;
    hash = 11 * hash + (this.directBuffer ? 1 : 0);
    return hash;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DoxyConfig other = (DoxyConfig) obj;
    if (this.port != other.getPort()) {
      return false;
    }
    if (this.targetPort != other.getTargetPort()) {
      return false;
    }
    if (this.proxyPort != other.getProxyPort()) {
      return false;
    }
    if (this.threadPoolSize != other.getThreadPoolSize()) {
      return false;
    }
    if (this.bufferSize != other.getBufferSize()) {
      return false;
    }
    if (this.directBuffer != other.isDirectBuffer()) {
      return false;
    }
    if (!Objects.equals(this.host, other.getHost())) {
      return false;
    }
    if (!Objects.equals(this.targetHost, other.getTargetHost())) {
      return false;
    }
    if (!Objects.equals(this.proxyHost, other.getProxyHost())) {
      return false;
    }
    if (!Objects.equals(this.proxyUser, other.getProxyUser())) {
      return false;
    }
    return Objects.equals(this.proxyPass, other.getProxyPassword());
  }


  @Override
  public String toString() {
    return "DoxyConfig{" 
        + "  - port=" + port + "\n"
        + "  - host=" + host + "\n"
        + "  - targetPort=" + targetPort + "\n"
        + "  - targetHost=" + targetHost + "\n"
        + "  - proxyPort=" + proxyPort + "\n"
        + "  - proxyHost=" + proxyHost + "\n"
        + "  - proxyUser=" + proxyUser + "\n"
        + "  - proxyPass=" + proxyPass + "\n"
        + "  - threadPoolSize=" + threadPoolSize + "\n"
        + "  - bufferSize=" + bufferSize + "\n"
        + "  - directBuffer=" + directBuffer + "\n"
        + '}';
  }
  
}
