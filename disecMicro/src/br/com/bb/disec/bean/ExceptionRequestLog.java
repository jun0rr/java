package br.com.bb.disec.bean;

import br.com.bb.disec.bean.iface.*;

/**
 * Generated JavaBean to encapsulate information
 * from the Database table [intranet.exception_request_log]
 */
public class ExceptionRequestLog implements IExceptionRequestLog {

  private java.lang.Integer requestId;
  private java.lang.String url;
  private java.lang.String postParam;
  private java.lang.String userAgent;
  private java.lang.String ip;
  private java.sql.Timestamp tsRequest;

  public ExceptionRequestLog() {
    this.requestId = 0;
    this.url = null;
    this.postParam = "";
    this.userAgent = null;
    this.ip = null;
    this.tsRequest = null;
  }

  public ExceptionRequestLog(
      java.lang.Integer requestId, 
      java.lang.String url, 
      java.lang.String postParam, 
      java.lang.String userAgent, 
      java.lang.String ip, 
      java.sql.Timestamp tsRequest) {
    this.requestId = requestId;
    this.url = url;
    this.postParam = postParam;
    this.userAgent = userAgent;
    this.ip = ip;
    this.tsRequest = tsRequest;
  }

  /**
   * Get the value relative to the database
   * column [request_id: int].
   * @return The value of the column [request_id].
   */
  @Override
  public java.lang.Integer getRequestId() {
    return requestId;
  }

  /**
   * Set the value relative to the database
   * column [request_id: int].
   * @param requestId The value of the column [request_id].
   * @return This modified object instance.
   */
  @Override
  public ExceptionRequestLog setRequestId( java.lang.Integer requestId ) {
    this.requestId = requestId;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [url: varchar].
   * @return The value of the column [url].
   */
  @Override
  public java.lang.String getUrl() {
    return url;
  }

  /**
   * Set the value relative to the database
   * column [url: varchar].
   * @param url The value of the column [url].
   * @return This modified object instance.
   */
  @Override
  public ExceptionRequestLog setUrl( java.lang.String url ) {
    this.url = url;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [post_param: varchar].
   * @return The value of the column [post_param].
   */
  @Override
  public java.lang.String getPostParam() {
    return postParam;
  }

  /**
   * Set the value relative to the database
   * column [post_param: varchar].
   * @param postParam The value of the column [post_param].
   * @return This modified object instance.
   */
  @Override
  public ExceptionRequestLog setPostParam( java.lang.String postParam ) {
    this.postParam = postParam;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [user_agent: varchar].
   * @return The value of the column [user_agent].
   */
  @Override
  public java.lang.String getUserAgent() {
    return userAgent;
  }

  /**
   * Set the value relative to the database
   * column [user_agent: varchar].
   * @param userAgent The value of the column [user_agent].
   * @return This modified object instance.
   */
  @Override
  public ExceptionRequestLog setUserAgent( java.lang.String userAgent ) {
    this.userAgent = userAgent;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [ip: varchar].
   * @return The value of the column [ip].
   */
  @Override
  public java.lang.String getIp() {
    return ip;
  }

  /**
   * Set the value relative to the database
   * column [ip: varchar].
   * @param ip The value of the column [ip].
   * @return This modified object instance.
   */
  @Override
  public ExceptionRequestLog setIp( java.lang.String ip ) {
    this.ip = ip;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [ts_request: timestamp].
   * @return The value of the column [ts_request].
   */
  @Override
  public java.sql.Timestamp getTsRequest() {
    return tsRequest;
  }

  /**
   * Set the value relative to the database
   * column [ts_request: timestamp].
   * @param tsRequest The value of the column [ts_request].
   * @return This modified object instance.
   */
  @Override
  public ExceptionRequestLog setTsRequest( java.sql.Timestamp tsRequest ) {
    this.tsRequest = tsRequest;
    return this;
  }


}