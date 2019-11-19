/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.nio.file.Path;


/**
 *
 * @author juno
 */
public interface DoxyConfig {
  
  public HostConfig getHost();
  
  public HostConfig getTarget();
  
  public ProxyConfig getProxyConfig();
  
  public SecurityConfig getSecurityConfig();
  
  public String getServerName();
  
  public String getUserAgent();
  
  public int getBufferSize();
  
  public int getThreadPoolSize();
  
  public boolean isDirectBuffer();
  
}
