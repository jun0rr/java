package br.com.bb.sso;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;



/**
 * Conector HTTP para recuperar as informações do
 * usuario do servidor de autenticação SSO.
 * @author Juno Roesler - F6036477
 */
public class SSOConnector {
	
  private final TrustStrategy ditecUnsecureStrategy = new TrustStrategy() {
      @Override
      public boolean isTrusted(X509Certificate[] chain,
        String authType) throws CertificateException {
        return true;
      }
  };
  
  private final Executor executor;
  
	private final String server;
	
	private final String token;
	
	
	/**
	 * Construtor padrão que recebe o endereço do servidor SSO
	 * e o token de autenticação.
	 * @param ssoServer Endereço do servidor SSO.
	 * @param tokenId Token de autenticação SSO.
	 */
	public SSOConnector(String ssoServer, String tokenId) {
		if(ssoServer == null || ssoServer.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"Servidor SSO invalido: "+ ssoServer
			);
		}
		if(tokenId == null || tokenId.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"SSO Token ID invalido: "+ tokenId
			);
		}
		this.server = ssoServer;
		this.token = tokenId;
    this.executor = createUnsecureExecutor();
	}
  
  
  public Executor createUnsecureExecutor() {
    try {
      SSLContext ditecUnsecureContext = SSLContextBuilder.create()
          .loadTrustMaterial(ditecUnsecureStrategy).build();
      SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(
          ditecUnsecureContext, NoopHostnameVerifier.INSTANCE
      );
      CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(factory).build();
      return Executor.newInstance(client);
    }
    catch(Exception e) {
      throw new RuntimeException(e.toString(), e);
    }
  }
	
	
	/**
	 * Retorna o endereço do servidor SSO.
	 * @return String
	 */
	public String getSSOServer() {
		return server;
	}
	
	
	/**
	 * Retorna o token de autenticação SSO.
	 * @return String
	 */
	public String getSSOTokenId() {
		return token;
	}
  
  
  private URI print_return(URI uri) {
    //System.out.printf("* SSOConnector.getURI(): '%s'%n", uri);
    return uri;
  }
	
	
	/**
	 * Cria uma URI para obter as informações do usuário
	 * a partir do endereço do servidor SSO.
	 * @return URI
	 * @throws URISyntaxException Em caso de erro na
	 * formação da URI
	 */
	public URI getURI() throws URISyntaxException {
		return print_return(new URIBuilder()
				.setScheme("https")
				.setHost(server)
				.setPath("/sso/identity/attributes")
				.setParameter("subjectid", token)
				.build());
	}
  
  
	/**
	 * Conecta no servidor SSO e retorna um objeto SSOParser
	 * com as informações recuperadas.
	 * @return SSOParser com as informações recuperadas.
	 * @throws IOException em caso de erro na conexão HTTP.
	 */
	public SSOParser connect() throws IOException {
		try {
      //System.out.println("# SSOConnector.connect(): " + getURI());
      //HttpResponse hresp = Executor.newInstance().execute(Request.Get(getURI())).returnResponse();
      HttpResponse hresp = executor.execute(Request.Get(getURI())).returnResponse();
			return new SSOParser(EntityUtils.toString(hresp.getEntity()));
		} 
    catch(URISyntaxException e) {
			throw new IOException(e);
		}
	}
  
}
