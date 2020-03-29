package br.com.bb.disec.bean.reader;

import java.sql.*;
import br.com.bb.disec.bean.*;
import br.com.bb.disec.bean.iface.*;

/**
 * BeanReader utility class to generate StackTraceLog
 * JavaBean from a given java.sql.ResultSet.
 */
public class StackTraceLogReader {

  private final ResultSet rset;

  /**
   * Default constructor receiving the ResultSet.
   * @param rs ResultSet containing the StackTraceLog information.
   */
  public StackTraceLogReader( ResultSet rs ) {
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
   * Create a StackTraceLog bean with the ResultSet information.
   * @return The created StackTraceLog bean.
   */
  public IStackTraceLog readBean() throws SQLException {
    IStackTraceLog bean = new StackTraceLog();
    if(this.contains( IStackTraceLog.COLUMN_EXCEPTION_ID )) {
      bean.setExceptionId( rset.getInt( IStackTraceLog.COLUMN_EXCEPTION_ID) );
    }
    if(this.contains( IStackTraceLog.COLUMN_STACK_LEVEL )) {
      bean.setStackLevel( rset.getInt( IStackTraceLog.COLUMN_STACK_LEVEL) );
    }
    if(this.contains( IStackTraceLog.COLUMN_CLASS_NAME )) {
      bean.setClassName( rset.getString( IStackTraceLog.COLUMN_CLASS_NAME) );
    }
    if(this.contains( IStackTraceLog.COLUMN_METHOD_NAME )) {
      bean.setMethodName( rset.getString( IStackTraceLog.COLUMN_METHOD_NAME) );
    }
    if(this.contains( IStackTraceLog.COLUMN_FILE_NAME )) {
      bean.setFileName( rset.getString( IStackTraceLog.COLUMN_FILE_NAME) );
    }
    if(this.contains( IStackTraceLog.COLUMN_LINE_NUMBER )) {
      bean.setLineNumber( rset.getInt( IStackTraceLog.COLUMN_LINE_NUMBER) );
    }
    return bean;
  }

}