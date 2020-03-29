package br.com.bb.disec.bean;

import br.com.bb.disec.bean.iface.*;

/**
 * Generated JavaBean to encapsulate information
 * from the Database table [intranet.exception_log]
 */
public class ExceptionLog implements IExceptionLog {

  private java.lang.Integer exceptionId;
  private java.lang.Integer cdCtu;
  private java.lang.Short isRootCause;
  private java.lang.Integer parentExceptionId;
  private java.lang.String className;
  private java.lang.String message;
  private java.lang.Integer requestId;
  private java.lang.Integer stackSize;
  private java.lang.String cdUsu;
  private java.lang.String nmUsu;
  private java.sql.Timestamp tsException;
  private Throwable exception;

  public ExceptionLog() {
    this.exceptionId = 0;
    this.cdCtu = 99999;
    this.isRootCause = 0;
    this.parentExceptionId = 0;
    this.className = null;
    this.message = null;
    this.requestId = 0;
    this.stackSize = 0;
    this.cdUsu = null;
    this.nmUsu = null;
    this.tsException = null;
    this.exception = null;
  }

  public ExceptionLog(
      java.lang.Integer exceptionId, 
      java.lang.Integer cdCtu, 
      java.lang.Short isRootCause, 
      java.lang.Integer parentExceptionId, 
      java.lang.String className, 
      java.lang.String message, 
      java.lang.Integer requestId, 
      java.lang.Integer stackSize, 
      java.lang.String cdUsu, 
      java.lang.String nmUsu, 
      java.sql.Timestamp tsException) {
    this.exceptionId = exceptionId;
    this.cdCtu = cdCtu;
    this.isRootCause = isRootCause;
    this.parentExceptionId = parentExceptionId;
    this.className = className;
    this.message = message;
    this.requestId = requestId;
    this.stackSize = stackSize;
    this.cdUsu = cdUsu;
    this.nmUsu = nmUsu;
    this.tsException = tsException;
  }

  /**
   * Get the value relative to the database
   * column [exception_id: int].
   * @return The value of the column [exception_id].
   */
  @Override
  public java.lang.Integer getExceptionId() {
    return exceptionId;
  }

  /**
   * Set the value relative to the database
   * column [exception_id: int].
   * @param exceptionId The value of the column [exception_id].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setExceptionId( java.lang.Integer exceptionId ) {
    this.exceptionId = exceptionId;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [cd_ctu: int].
   * @return The value of the column [cd_ctu].
   */
  @Override
  public java.lang.Integer getCdCtu() {
    return cdCtu;
  }

  /**
   * Set the value relative to the database
   * column [cd_ctu: int].
   * @param cdCtu The value of the column [cd_ctu].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setCdCtu( java.lang.Integer cdCtu ) {
    this.cdCtu = cdCtu;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [is_root_cause: tinyint].
   * @return The value of the column [is_root_cause].
   */
  @Override
  public java.lang.Short getIsRootCause() {
    return isRootCause;
  }

  /**
   * Set the value relative to the database
   * column [is_root_cause: tinyint].
   * @param isRootCause The value of the column [is_root_cause].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setIsRootCause( java.lang.Short isRootCause ) {
    this.isRootCause = isRootCause;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [parent_exception_id: int].
   * @return The value of the column [parent_exception_id].
   */
  @Override
  public java.lang.Integer getParentExceptionId() {
    return parentExceptionId;
  }

  /**
   * Set the value relative to the database
   * column [parent_exception_id: int].
   * @param parentExceptionId The value of the column [parent_exception_id].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setParentExceptionId( java.lang.Integer parentExceptionId ) {
    this.parentExceptionId = parentExceptionId;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [class_name: varchar].
   * @return The value of the column [class_name].
   */
  @Override
  public java.lang.String getClassName() {
    return className;
  }

  /**
   * Set the value relative to the database
   * column [class_name: varchar].
   * @param className The value of the column [class_name].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setClassName( java.lang.String className ) {
    this.className = className;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [message: varchar].
   * @return The value of the column [message].
   */
  @Override
  public java.lang.String getMessage() {
    return message;
  }

  /**
   * Set the value relative to the database
   * column [message: varchar].
   * @param message The value of the column [message].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setMessage( java.lang.String message ) {
    this.message = message;
    return this;
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
  public ExceptionLog setRequestId( java.lang.Integer requestId ) {
    this.requestId = requestId;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [stack_size: int].
   * @return The value of the column [stack_size].
   */
  @Override
  public java.lang.Integer getStackSize() {
    return stackSize;
  }

  /**
   * Set the value relative to the database
   * column [stack_size: int].
   * @param stackSize The value of the column [stack_size].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setStackSize( java.lang.Integer stackSize ) {
    this.stackSize = stackSize;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [cd_usu: char].
   * @return The value of the column [cd_usu].
   */
  @Override
  public java.lang.String getCdUsu() {
    return cdUsu;
  }

  /**
   * Set the value relative to the database
   * column [cd_usu: char].
   * @param cdUsu The value of the column [cd_usu].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setCdUsu( java.lang.String cdUsu ) {
    this.cdUsu = cdUsu;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [nm_usu: varchar].
   * @return The value of the column [nm_usu].
   */
  @Override
  public java.lang.String getNmUsu() {
    return nmUsu;
  }

  /**
   * Set the value relative to the database
   * column [nm_usu: varchar].
   * @param nmUsu The value of the column [nm_usu].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setNmUsu( java.lang.String nmUsu ) {
    this.nmUsu = nmUsu;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [ts_exception: timestamp].
   * @return The value of the column [ts_exception].
   */
  @Override
  public java.sql.Timestamp getTsException() {
    return tsException;
  }

  /**
   * Set the value relative to the database
   * column [ts_exception: timestamp].
   * @param tsException The value of the column [ts_exception].
   * @return This modified object instance.
   */
  @Override
  public ExceptionLog setTsException( java.sql.Timestamp tsException ) {
    this.tsException = tsException;
    return this;
  }

  @Override
  public Throwable getException() {
    return exception;
  }
  
  @Override
  public IExceptionLog setException(Throwable th) {
    if(th != null) {
      this.exception = th;
      setClassName(th.getClass().getName())
          .setMessage(th.getMessage())
          .setStackSize(th.getStackTrace().length);
    }
    return this;
  }

}