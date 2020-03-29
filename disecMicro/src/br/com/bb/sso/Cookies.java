package br.com.bb.sso;

import io.undertow.server.handlers.Cookie;


/**
 * Enum com os nomes de cookies HTTP utilizados pelo 
 * controle de sessão
 * @author Juno Roesler - F6036477
 */
public enum Cookies {
	
  /**
   * Cookie para armazenar a sessao do banco de dados.
   */
  SESSIONDB,
	
  /**
   * Cookie para armazenar a sessao JWT.
   */
  JWTSESSION,
	
	/**
	 * Cookie para armazenar a data de criação da sessão.
	 */
	JIDCREATED, 
	
	/**
	 * Cookie para armazenar o hash MD5 de identificação da sessão.
	 */
	JIDHASH, 
	
	/**
	 * Cookie que armazena o token de autenticação SSO.
	 */
	BBSSOToken, 
	
	/**
	 * Cookie que armazena o endereço do servidor de autenticação SSO.
	 */
	ssoacr, 
	
	/**
	 * Cookie que armazena o ID de sessão do tomcat.
	 */
	JSESSIONID,
  
	/**
	 * Cookie que armazena o ID de sessão do tomcat.
	 */
	LtpaToken,
  
	/**
	 * Cookie que armazena o ID de sessão do tomcat.
	 */
	LtpaToken2;
  
  
  public static String getCookieValue(Cookie[] cks, String name) {
    if(cks == null || name == null) return null;
    for(int i = 0; i < cks.length; i++) {
      if(cks[i].getName().equals(name)) {
        return cks[i].getValue();
      }
    }
    return null;
  }
	
  public static Cookie getCookie(Cookie[] cks, String name) {
    if(cks == null || name == null) return null;
    for(int i = 0; i < cks.length; i++) {
      if(cks[i].getName().equals(name)) {
        return cks[i];
      }
    }
    return null;
  }
	
  public static boolean containsCookie(Cookie[] cks, String name) {
    if(cks == null || name == null) return false;
    for(int i = 0; i < cks.length; i++) {
      if(cks[i].getName().equals(name)) {
        return true;
      }
    }
    return false;
  }
	
  public static String getCookieValue(Cookie[] cks, Cookies ck) {
    return getCookieValue(cks, ck.name());
  }
	
  public static Cookie getCookie(Cookie[] cks, Cookies ck) {
    return getCookie(cks, ck.name());
  }
	
  public static boolean containsCookie(Cookie[] cks, Cookies ck) {
    return containsCookie(cks, ck.name());
  }
	
}
