/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.util.concurrent.ExecutorService;
import net.jun0rr.doxy.DoxyConfig;


/**
 *
 * @author juno
 */
public interface DoxyClientConfig extends DoxyConfig {
  
  public int getProxyPort();
  
  public String getProxyHost();
  
  public String getProxyUser();
  
  public String getProxyPassword();
  
}
