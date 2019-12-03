/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.impl;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import java.util.Objects;


/**
 *
 * @author Juno
 */
public class XErrorHeaders {
  
  private String errorType;
  
  private String errorMessage;
  
  private String errorTrace;
  
  private String errorCause;
  
  public XErrorHeaders() {
    errorType = errorMessage = errorTrace = errorCause;
  }
  
  public XErrorHeaders setErrorType(String type) {
    this.errorType = type;
    return this;
  }
  
  public XErrorHeaders setErrorType(Class cls) {
    this.errorType = cls.getName();
    return this;
  }
  
  public XErrorHeaders setErrorMessage(String msg) {
    this.errorMessage = msg;
    return this;
  }
  
  public XErrorHeaders setErrorMessage(Object o) {
    this.errorMessage = Objects.toString(o);
    return this;
  }
  
  public XErrorHeaders setErrorTrace(String trace) {
    this.errorTrace = trace;
    return this;
  }
  
  public XErrorHeaders setErrorTrace(Object o) {
    this.errorTrace = Objects.toString(o);
    return this;
  }
  
  public XErrorHeaders setErrorCause(String cause) {
    this.errorCause = cause;
    return this;
  }
  
  public XErrorHeaders setErrorCause(Object o) {
    this.errorTrace = Objects.toString(o);
    return this;
  }
  
  public XErrorHeaders setError(Throwable th) {
    setErrorType(th.getClass());
    setErrorMessage(th.getMessage());
    setErrorTrace(th.getStackTrace()[0]);
    if(th.getCause() != null) setErrorCause(th.getCause());
    return this;
  }
  
  public String getErrorType() {
    return errorType;
  }
  
  public String getErrorMessage() {
    return errorMessage;
  }
  
  public String getErrorTrace() {
    return errorTrace;
  }
  
  public String getErrorCause() {
    return errorCause;
  }
  
  public HttpHeaders toHeaders() {
    return toHeaders(new DefaultHttpHeaders());
  }
  
  public HttpHeaders toHeaders(HttpHeaders hd) {
    if(errorType != null && !errorType.isBlank()) {
      hd.set("x-error-type", this.errorType);
    }
    if(errorMessage != null && !errorMessage.isBlank()) {
      hd.set("x-error-message", this.errorMessage);
    }
    if(errorTrace != null && !errorTrace.isBlank()) {
      hd.set("x-error-trace", this.errorTrace);
    }
    if(errorCause != null && !errorCause.isBlank()) {
      hd.set("x-error-cause", this.errorCause);
    }
    return hd;
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 83 * hash + Objects.hashCode(this.errorType);
    hash = 83 * hash + Objects.hashCode(this.errorMessage);
    hash = 83 * hash + Objects.hashCode(this.errorTrace);
    hash = 83 * hash + Objects.hashCode(this.errorCause);
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
    if (getClass() != obj.getClass()) {
      return false;
    }
    final XErrorHeaders other = (XErrorHeaders) obj;
    if (!Objects.equals(this.errorType, other.errorType)) {
      return false;
    }
    if (!Objects.equals(this.errorMessage, other.errorMessage)) {
      return false;
    }
    if (!Objects.equals(this.errorCause, other.errorCause)) {
      return false;
    }
    return Objects.equals(this.errorTrace, other.errorTrace);
  }
  
  @Override
  public String toString() {
    return "XErrorHeaders{" + "errorType=" + errorType + ", errorMessage=" + errorMessage + ", errorTrace=" + errorTrace + ", errorCause=" + errorCause + '}';
  }
  
}
