/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import java.nio.file.Path;
import net.jun0rr.doxy.cfg.*;


/**
 *
 * @author juno
 */
public interface HttpServerConfig {
  
  public Path getKeystorePath();
  
  public char[] getKeystorePassword();
  
  public String getServerName();
  
  public String getUserAgent();
  
  
  
  public static HttpServerConfigBuilder builder() {
    return HttpServerConfigBuilder.newBuilder();
  }
  
  public static HttpServerConfig of(Path kspath, char[] kspass, String serverName, String userAgent) {
    return new HttpServerConfigImpl(kspath, kspass, serverName, userAgent);
  }
  
  
  
  
  
  public static class HttpServerConfigImpl implements HttpServerConfig {

    private final Path kspath;
    
    private final char[] kspass;

    private final String serverName;

    private final String userAgent;


    public HttpServerConfigImpl(Path kspath, char[] kspass, String serverName, String userAgent) {
      this.kspath = kspath;
      this.kspass = kspass;
      this.serverName = serverName;
      this.userAgent = userAgent;
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
    public String getUserAgent() {
      return userAgent;
    }

    @Override
    public String toString() {
      return "HttpServerConfig{" 
          + "  - serverName=" + serverName + "\n"
          + "  - userAgent=" + userAgent + "\n"
          + "  - keystore=" + kspath + "\n"
          + '}';
    }

  }

  
}
