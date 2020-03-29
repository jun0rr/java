package br.com.bb.disec.bean.reader;

import java.sql.*;
import br.com.bb.disec.bean.*;
import br.com.bb.disec.bean.iface.*;

/**
 * BeanReader utility class to generate ExceptionLog
 * JavaBean from a given java.sql.ResultSet.
 */
public class ExceptionLogReader {

  private final ResultSet rset;

  /**
   * Default constructor receiving the ResultSet.
   * @param rs ResultSet containing the ExceptionLog information.
   */
  public ExceptionLogReader( ResultSet rs ) {
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
   * Create a ExceptionLog bean with the ResultSet information.
   * @return The created ExceptionLog bean.
   */
  public IExceptionLog readBean() throws SQLException {
    IExceptionLog bean = new ExceptionLog();
    if(this.contains( IExceptionLog.COLUMN_EXCEPTION_ID )) {
      bean.setExceptionId( rset.getInt( IExceptionLog.COLUMN_EXCEPTION_ID) );
    }
    if(this.contains( IExceptionLog.COLUMN_CD_CTU )) {
      bean.setCdCtu( rset.getInt( IExceptionLog.COLUMN_CD_CTU) );
    }
    if(this.contains( IExceptionLog.COLUMN_IS_ROOT_CAUSE )) {
      bean.setIsRootCause( rset.getShort( IExceptionLog.COLUMN_IS_ROOT_CAUSE) );
    }
    if(this.contains( IExceptionLog.COLUMN_PARENT_EXCEPTION_ID )) {
      bean.setParentExceptionId( rset.getInt( IExceptionLog.COLUMN_PARENT_EXCEPTION_ID) );
    }
    if(this.contains( IExceptionLog.COLUMN_CLASS_NAME )) {
      bean.setClassName( rset.getString( IExceptionLog.COLUMN_CLASS_NAME) );
    }
    if(this.contains( IExceptionLog.COLUMN_MESSAGE )) {
      bean.setMessage( rset.getString( IExceptionLog.COLUMN_MESSAGE) );
    }
    if(this.contains( IExceptionLog.COLUMN_REQUEST_ID )) {
      bean.setRequestId( rset.getInt( IExceptionLog.COLUMN_REQUEST_ID) );
    }
    if(this.contains( IExceptionLog.COLUMN_STACK_SIZE )) {
      bean.setStackSize( rset.getInt( IExceptionLog.COLUMN_STACK_SIZE) );
    }
    if(this.contains( IExceptionLog.COLUMN_CD_USU )) {
      bean.setCdUsu( rset.getString( IExceptionLog.COLUMN_CD_USU) );
    }
    if(this.contains( IExceptionLog.COLUMN_NM_USU )) {
      bean.setNmUsu( rset.getString( IExceptionLog.COLUMN_NM_USU) );
    }
    if(this.contains( IExceptionLog.COLUMN_TS_EXCEPTION )) {
      bean.setTsException( rset.getTimestamp( IExceptionLog.COLUMN_TS_EXCEPTION) );
    }
    return bean;
  }

}