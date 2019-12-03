/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import java.util.Objects;
import java.util.Optional;


/**
 *
 * @author Juno
 */
public interface HttpRequest extends io.netty.handler.codec.http.HttpRequest {
  
  public <T> Optional<T> body();
  
  
  
  public static HttpRequest of(HttpVersion vrs, HttpMethod mth, String uri, HttpHeaders hds, Object body) {
    return new HttpRequestImpl(vrs, mth, uri, hds, body);
  }
  
  public static HttpRequest of(HttpVersion vrs, HttpMethod mth, String uri, HttpHeaders hds) {
    return new HttpRequestImpl(vrs, mth, uri, hds);
  }
  
  public static HttpRequest of(HttpVersion vrs, HttpMethod mth, String uri) {
    return new HttpRequestImpl(vrs, mth, uri);
  }
  
  public static HttpRequest of(FullHttpRequest fr) {
    HttpRequest req = of(fr.protocolVersion(), fr.method(), fr.uri(), fr.headers(), fr.content());
    if(!fr.trailingHeaders().isEmpty()) {
      req.headers().setAll(fr.trailingHeaders());
    }
    return req;
  }
  
  
  
  
  
  public static class HttpRequestImpl extends DefaultHttpRequest implements HttpRequest {
    
    private final Optional<? extends Object> body;
    
    public HttpRequestImpl(HttpVersion vrs, HttpMethod mth, String uri, HttpHeaders hds, Object body) {
      super(vrs, mth, uri, hds);
      this.body = Optional.ofNullable(body);
    }
    
    public HttpRequestImpl(HttpVersion vrs, HttpMethod mth, String uri, HttpHeaders hds) {
      this(vrs, mth, uri, hds, null);
    }
    
    public HttpRequestImpl(HttpVersion vrs, HttpMethod mth, String uri) {
      super(vrs, mth, uri);
      this.body = Optional.empty();
    }
    
    @Override
    public <T> Optional<T> body() {
      return (Optional<T>) body;
    }
    
    @Override
    public int hashCode() {
      int hash = 5;
      hash = 73 * hash + Objects.hashCode(this.protocolVersion());
      hash = 73 * hash + Objects.hashCode(this.method());
      hash = 73 * hash + Objects.hashCode(this.uri());
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
      if (!HttpResponse.class.isAssignableFrom(obj.getClass())) {
        return false;
      }
      final HttpRequest other = (HttpRequest) obj;
      if (!Objects.equals(this.protocolVersion(), other.protocolVersion())) {
        return false;
      }
      if (!Objects.equals(this.method(), other.method())) {
        return false;
      }
      return Objects.equals(this.uri(), other.uri());
    }
    
    @Override
    public String toString() {
      return "HttpRequest{" + "version=" + protocolVersion() + ", method=" + method() + ", uri=" + uri() + '}';
    }
    
  }
  
}
