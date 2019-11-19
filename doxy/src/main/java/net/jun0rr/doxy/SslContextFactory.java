/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy;

import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 *
 * @author juno
 */
public class SslContextFactory {
  
	public static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[] { new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		public void checkClientTrusted(X509Certificate[] certs, String authType) {}
		public void checkServerTrusted(X509Certificate[] certs, String authType) {}
		} 
	};
  
  
  public static SSLContext 
  
  
}
