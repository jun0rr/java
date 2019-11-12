/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import java.util.Objects;
import java.util.Optional;


/**
 *
 * @author Juno
 */
public interface HttpRequest<T extends Object> extends io.netty.handler.codec.http.HttpRequest {
  
  public Optional<T> body();
  
  public FullHttpRequest toNettyRequest();
  
  
  
  public static <U> HttpRequest<U> of(HttpVersion vrs, HttpMethod mth, String uri, U body) {
    return new HttpRequestImpl<>(vrs, mth, uri, body);
  }
  
  public static HttpRequest<Void> of(HttpVersion vrs, HttpMethod mth, String uri) {
    return new HttpRequestImpl<>(vrs, mth, uri);
  }
  
  
  
  
  
  public static class HttpRequestImpl<T extends Object> extends DefaultHttpRequest implements HttpRequest<T> {
    
    private final Optional<T> body;

    public HttpRequestImpl(HttpVersion vrs, HttpMethod mth, String uri, T body) {
      super(vrs, mth, uri);
      this.body = Optional.ofNullable(body);
    }
    
    public HttpRequestImpl(HttpVersion vrs, HttpMethod mth, String uri) {
      this(vrs, mth, uri, null);
    }
    
    @Override
    public Optional<T> body() {
      return body;
    }
    
    @Override
    public FullHttpRequest toNettyRequest() {
      if(body.isEmpty()) {
        return new DefaultFullHttpRequest(protocolVersion(), method(), uri());
      }
      else if(body.get() instanceof ByteBuf) {
        return new DefaultFullHttpRequest(protocolVersion(), method(), uri(), (ByteBuf)body.get());
      }
      else {
        throw new IllegalStateException("Body type not allowed: " + body.get().getClass().getName());
      }
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
      final HttpRequest<?> other = (HttpRequest<?>) obj;
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
