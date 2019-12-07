/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http;

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
public interface HttpResponse extends io.netty.handler.codec.http.HttpResponse {
  
  public <T> Optional<T> content();
  
  public FullHttpResponse toNettyResponse();
  
  
  
  public static HttpResponse of(HttpVersion vrs, HttpResponseStatus sts, HttpHeaders hds, Object cnt) {
    return new HttpResponseImpl(vrs, sts, hds, cnt);
  }
  
  public static HttpResponse of(HttpVersion vrs, HttpResponseStatus sts, HttpHeaders hds) {
    return new HttpResponseImpl(vrs, sts, hds);
  }
  
  public static HttpResponse of(HttpVersion vrs, HttpResponseStatus sts) {
    return new HttpResponseImpl(vrs, sts);
  }
  
  public static HttpResponse of(HttpResponseStatus sts, Object cnt) {
    return new HttpResponseImpl(sts, cnt);
  }
  
  public static HttpResponse of(HttpResponseStatus sts) {
    return new HttpResponseImpl(sts);
  }
  
  public static HttpResponse of(FullHttpResponse fr) {
    HttpResponse res = of(fr.protocolVersion(), fr.status(), fr.headers(), fr.content());
    if(!fr.trailingHeaders().isEmpty()) {
      res.headers().setAll(fr.trailingHeaders());
    }
    return res;
  }
  
  
  
  
  
  public static class HttpResponseImpl extends DefaultHttpResponse implements HttpResponse {

    private final Optional<? extends Object> content;
    
    public HttpResponseImpl(HttpVersion version, HttpResponseStatus status, HttpHeaders headers, Object content) {
      super(version, status, headers);
      this.content = Optional.ofNullable(content);
    }
    
    public HttpResponseImpl(HttpVersion version, HttpResponseStatus status, HttpHeaders headers) {
      this(version, status, headers, null);
    }
    
    public HttpResponseImpl(HttpVersion version, HttpResponseStatus status) {
      super(version, status);
      this.content = Optional.empty();
    }
    
    public HttpResponseImpl(HttpResponseStatus status, Object content) {
      super(HttpVersion.HTTP_1_1, status);
      this.content = Optional.ofNullable(content);
    }
    
    public HttpResponseImpl(HttpResponseStatus status) {
      this(HttpVersion.HTTP_1_1, status);
    }
    
    @Override
    public <T> Optional<T> content() {
      return (Optional<T>) content;
    }
    
    @Override
    public FullHttpResponse toNettyResponse() {
      FullHttpResponse res;
      if(content.isEmpty()) {
        res = new DefaultFullHttpResponse(protocolVersion(), status());
      }
      else if(content.get() instanceof ByteBuf) {
        res = new DefaultFullHttpResponse(protocolVersion(), status(), (ByteBuf)content.get());
      }
      else {
        throw new IllegalStateException("Content type not allowed: " + content.get().getClass().getName());
      }
      res.headers().setAll(headers());
      return res;
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
      final HttpResponse other = (HttpResponse) obj;
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
