package br.com.bb.disec.bean.reader;

import java.sql.*;
import br.com.bb.disec.bean.*;
import br.com.bb.disec.bean.iface.*;

/**
 * BeanReader utility class to generate ExceptionRequestLog
 * JavaBean from a given java.sql.ResultSet.
 */
public class ExceptionRequestLogReader {

  private final ResultSet rset;

  /**
   * Default constructor receiving the ResultSet.
   * @param rs ResultSet containing the ExceptionRequestLog information.
   */
  public ExceptionRequestLogReader( ResultSet rs ) {
    if(rs == null) {
      throw new IllegalArgumentException("ResultSet must be not null");
    }
    this.rset = rs;
  }

  /**
   * Get the ResultSet of this BeanReader.
   * @return java.sql.ResultSet.
   */
  public ResultSet getResultSet() {
    return this.rset;
  }

  /**
   * Identifies if the ResultSet contains a column with the given name.
   * @param col The column name to be verified.
   * @return [true] if the ResultSet contains the column, [false] otherwise.
   */
  private boolean contains( String col ) {
    try {
      return rset.findColumn( col ) > 0;
    } catch( SQLException e ) {
      return false;
    }
  }

  /**
   * Create a ExceptionRequestLog bean with the ResultSet information.
   * @return The created ExceptionRequestLog bean.
   */
  public IExceptionRequestLog readBean() throws SQLException {
    IExceptionRequestLog bean = new ExceptionRequestLog();
    if(this.contains( IExceptionRequestLog.COLUMN_REQUEST_ID )) {
      bean.setRequestId( rset.getInt( IExceptionRequestLog.COLUMN_REQUEST_ID) );
    }
    if(this.contains( IExceptionRequestLog.COLUMN_URL )) {
      bean.setUrl( rset.getString( IExceptionRequestLog.COLUMN_URL) );
    }
    if(this.contains( IExceptionRequestLog.COLUMN_POST_PARAM )) {
      bean.setPostParam( rset.getString( IExceptionRequestLog.COLUMN_POST_PARAM) );
    }
    if(this.contains( IExceptionRequestLog.COLUMN_USER_AGENT )) {
      bean.setUserAgent( rset.getString( IExceptionRequestLog.COLUMN_USER_AGENT) );
    }
    if(this.contains( IExceptionRequestLog.COLUMN_IP )) {
      bean.setIp( rset.getString( IExceptionRequestLog.COLUMN_IP) );
    }
    if(this.contains( IExceptionRequestLog.COLUMN_TS_REQUEST )) {
      bean.setTsRequest( rset.getTimestamp( IExceptionRequestLog.COLUMN_TS_REQUEST) );
    }
    return bean;
  }

}