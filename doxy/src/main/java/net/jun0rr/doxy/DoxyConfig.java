/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.util.concurrent.ExecutorService;


/**
 *
 * @author juno
 */
public interface DoxyConfig {
  
  public int getPort();
  
  public String getHost();
  
  public int getTargetPort();
  
  public String getTargetHost();
  
  public int getProxyPort();
  
  public String getProxyHost();
  
  public String getProxyUser();
  
  public String getProxyPassword();
  
  public int getBufferSize();
  
  public int getThreadPoolSize();
  
  public boolean isDirectBuffer();
  
  public byte[] getSecretKey();
  
  public String getServerName();
  
  public String getUserAgent();
  
}
