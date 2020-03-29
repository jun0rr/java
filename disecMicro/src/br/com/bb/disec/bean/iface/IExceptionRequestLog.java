package br.com.bb.disec.bean.iface;

/**
 * Generated JavaBean to encapsulate information
 * from the Database table [intranet.exception_request_log]
 */
public interface IExceptionRequestLog {

  /**
   * Table column name [request_id: int].
   */
  public static final String COLUMN_REQUEST_ID = "request_id";

  /**
   * Table column name [url: varchar].
   */
  public static final String COLUMN_URL = "url";

  /**
   * Table column name [post_param: varchar].
   */
  public static final String COLUMN_POST_PARAM = "post_param";

  /**
   * Table column name [user_agent: varchar].
   */
  public static final String COLUMN_USER_AGENT = "user_agent";

  /**
   * Table column name [ip: varchar].
   */
  public static final String COLUMN_IP = "ip";

  /**
   * Table column name [ts_request: timestamp].
   */
  public static final String COLUMN_TS_REQUEST = "ts_request";


  /**
   * Get the value relative to the database
   * column [request_id: int].
   * @return The value of the column [request_id].
   */
  public java.lang.Integer getRequestId();

  /**
   * Set the value relative to the database
   * column [request_id: int].
   * @param requestId The value of the column [request_id].
   * @return This modified object instance.
   */
  public IExceptionRequestLog setRequestId( java.lang.Integer requestId );


  /**
   * Get the value relative to the database
   * column [url: varchar].
   * @return The value of the column [url].
   */
  public java.lang.String getUrl();

  /**
   * Set the value relative to the database
   * column [url: varchar].
   * @param url The value of the column [url].
   * @return This modified object instance.
   */
  public IExceptionRequestLog setUrl( java.lang.String url );


  /**
   * Get the value relative to the database
   * column [post_param: varchar].
   * @return The value of the column [post_param].
   */
  public java.lang.String getPostParam();

  /**
   * Set the value relative to the database
   * column [post_param: varchar].
   * @param postParam The value of the column [post_param].
   * @return This modified object instance.
   */
  public IExceptionRequestLog setPostParam( java.lang.String postParam );


  /**
   * Get the value relative to the database
   * column [user_agent: varchar].
   * @return The value of the column [user_agent].
   */
  public java.lang.String getUserAgent();

  /**
   * Set the value relative to the database
   * column [user_agent: varchar].
   * @param userAgent The value of the column [user_agent].
   * @return This modified object instance.
   */
  public IExceptionRequestLog setUserAgent( java.lang.String userAgent );


  /**
   * Get the value relative to the database
   * column [ip: varchar].
   * @return The value of the column [ip].
   */
  public java.lang.String getIp();

  /**
   * Set the value relative to the database
   * column [ip: varchar].
   * @param ip The value of the column [ip].
   * @return This modified object instance.
   */
  public IExceptionRequestLog setIp( java.lang.String ip );


  /**
   * Get the value relative to the database
   * column [ts_request: timestamp].
   * @return The value of the column [ts_request].
   */
  public java.sql.Timestamp getTsRequest();

  /**
   * Set the value relative to the database
   * column [ts_request: timestamp].
   * @param tsRequest The value of the column [ts_request].
   * @return This modified object instance.
   */
  public IExceptionRequestLog setTsRequest( java.sql.Timestamp tsRequest );


}