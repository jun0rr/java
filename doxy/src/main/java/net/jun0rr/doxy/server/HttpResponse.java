/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.util.Objects;
import java.util.Optional;


/**
 *
 * @author Juno
 */
public interface HttpResponse<T> extends io.netty.handler.codec.http.HttpResponse {
  
  public Optional<T> content();
  
  public FullHttpResponse toNettyResponse();
  
  
  
  public static <U> HttpResponse<U> of(HttpVersion vrs, HttpResponseStatus sts, HttpHeaders hds, U cnt) {
    return new HttpResponseImpl<>(vrs, sts, hds, cnt);
  }
  
  public static <U> HttpResponse<U> of(HttpVersion vrs, HttpResponseStatus sts) {
    return new HttpResponseImpl<>(vrs, sts);
  }
  
  public static <U> HttpResponse<U> of(HttpResponseStatus sts, U cnt) {
    return new HttpResponseImpl<>(sts, cnt);
  }
  
  public static <U> HttpResponse<U> of(HttpResponseStatus sts) {
    return new HttpResponseImpl<>(sts);
  }
  
  
  
  
  
  public static class HttpResponseImpl<T> extends DefaultHttpResponse implements HttpResponse<T> {

    private final Optional<T> content;
    
    public HttpResponseImpl(HttpVersion version, HttpResponseStatus status, HttpHeaders headers, T content) {
      super(version, status, headers);
      this.content = Optional.ofNullable(content);
    }
    
    public HttpResponseImpl(HttpVersion version, HttpResponseStatus status) {
      super(version, status);
      this.content = Optional.empty();
    }
    
    public HttpResponseImpl(HttpResponseStatus status, T content) {
      super(HttpVersion.HTTP_1_1, status);
      this.content = Optional.ofNullable(content);
    }
    
    public HttpResponseImpl(HttpResponseStatus status) {
      this(HttpVersion.HTTP_1_1, status);
    }
    
    @Override
    public Optional<T> content() {
      return content;
    }
    
    @Override
    public FullHttpResponse toNettyResponse() {
      if(content.isEmpty()) {
        return new DefaultFullHttpResponse(protocolVersion(), status());
      }
      else if(content.get() instanceof ByteBuf) {
        return new DefaultFullHttpResponse(protocolVersion(), status(), (ByteBuf)content.get());
      }
      else {
        throw new IllegalStateException("Content type not allowed: " + content.get().getClass().getName());
      }
    }
    
    @Override
    public int hashCode() {
      int hash = 7;
      hash = 59 * hash + Objects.hashCode(this.protocolVersion());
      hash = 59 * hash + Objects.hashCode(this.status());
      hash = 59 * hash + Objects.hashCode(this.headers());
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
      final HttpResponse<?> other = (HttpResponse<?>) obj;
      if(!Objects.equals(this.protocolVersion(), other.protocolVersion())) {
        return false;
      }
      if(!Objects.equals(this.status(), other.status())) {
        return false;
      }
      return Objects.equals(this.headers(), other.headers());
    }
    
    @Override
    public String toString() {
      return "HttpResponse{" + "version=" + protocolVersion() + ", status=" + status() + '}';
    }
    
  }
  
}
