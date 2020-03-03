/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import java.nio.file.Path;


/**
 *
 * @author juno
 */
public interface HttpServerConfig {
  
  public Path getKeystorePath();
  
  public char[] getKeystorePassword();
  
  public String getServerName();
  
  
  
  public static HttpServerConfig of(Path kspath, char[] kspass, String serverName) {
    return new HttpServerConfigImpl(kspath, kspass, serverName);
  }
  
  
  
  
  
  public static class HttpServerConfigImpl implements HttpServerConfig {

    private final Path kspath;
    
    private final char[] kspass;

    private final String serverName;


    public HttpServerConfigImpl(Path kspath, char[] kspass, String serverName) {
      this.kspath = kspath;
      this.kspass = kspass;
      this.serverName = serverName;
    }

    @Override
    public Path getKeystorePath() {
      return kspath;
    }
    
    @Override
    public char[] getKeystorePassword() {
      return kspass;
    }
    
    @Override
    public String getServerName() {
      return serverName;
    }

    @Override
    public String toString() {
      return "HttpServerConfig{" 
          + "  - serverName=" + serverName + "\n"
          + "  - keystore=" + kspath + "\n"
          + '}';
    }

  }

  
}
