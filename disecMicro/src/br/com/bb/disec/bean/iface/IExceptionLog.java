package br.com.bb.disec.bean.iface;

/**
 * Generated JavaBean to encapsulate information
 * from the Database table [intranet.exception_log]
 */
public interface IExceptionLog {

  /**
   * Table column name [exception_id: int].
   */
  public static final String COLUMN_EXCEPTION_ID = "exception_id";

  /**
   * Table column name [cd_ctu: int].
   */
  public static final String COLUMN_CD_CTU = "cd_ctu";

  /**
   * Table column name [is_root_cause: tinyint].
   */
  public static final String COLUMN_IS_ROOT_CAUSE = "is_root_cause";

  /**
   * Table column name [parent_exception_id: int].
   */
  public static final String COLUMN_PARENT_EXCEPTION_ID = "parent_exception_id";

  /**
   * Table column name [class_name: varchar].
   */
  public static final String COLUMN_CLASS_NAME = "class_name";

  /**
   * Table column name [message: varchar].
   */
  public static final String COLUMN_MESSAGE = "message";

  /**
   * Table column name [request_id: int].
   */
  public static final String COLUMN_REQUEST_ID = "request_id";

  /**
   * Table column name [stack_size: int].
   */
  public static final String COLUMN_STACK_SIZE = "stack_size";

  /**
   * Table column name [cd_usu: char].
   */
  public static final String COLUMN_CD_USU = "cd_usu";

  /**
   * Table column name [nm_usu: varchar].
   */
  public static final String COLUMN_NM_USU = "nm_usu";

  /**
   * Table column name [ts_exception: timestamp].
   */
  public static final String COLUMN_TS_EXCEPTION = "ts_exception";


  /**
   * Get the value relative to the database
   * column [exception_id: int].
   * @return The value of the column [exception_id].
   */
  public java.lang.Integer getExceptionId();

  /**
   * Set the value relative to the database
   * column [exception_id: int].
   * @param exceptionId The value of the column [exception_id].
   * @return This modified object instance.
   */
  public IExceptionLog setExceptionId( java.lang.Integer exceptionId );


  /**
   * Get the value relative to the database
   * column [cd_ctu: int].
   * @return The value of the column [cd_ctu].
   */
  public java.lang.Integer getCdCtu();

  /**
   * Set the value relative to the database
   * column [cd_ctu: int].
   * @param cdCtu The value of the column [cd_ctu].
   * @return This modified object instance.
   */
  public IExceptionLog setCdCtu( java.lang.Integer cdCtu );


  /**
   * Get the value relative to the database
   * column [is_root_cause: tinyint].
   * @return The value of the column [is_root_cause].
   */
  public java.lang.Short getIsRootCause();

  /**
   * Set the value relative to the database
   * column [is_root_cause: tinyint].
   * @param isRootCause The value of the column [is_root_cause].
   * @return This modified object instance.
   */
  public IExceptionLog setIsRootCause( java.lang.Short isRootCause );


  /**
   * Get the value relative to the database
   * column [parent_exception_id: int].
   * @return The value of the column [parent_exception_id].
   */
  public java.lang.Integer getParentExceptionId();

  /**
   * Set the value relative to the database
   * column [parent_exception_id: int].
   * @param parentExceptionId The value of the column [parent_exception_id].
   * @return This modified object instance.
   */
  public IExceptionLog setParentExceptionId( java.lang.Integer parentExceptionId );


  /**
   * Get the value relative to the database
   * column [class_name: varchar].
   * @return The value of the column [class_name].
   */
  public java.lang.String getClassName();

  /**
   * Set the value relative to the database
   * column [class_name: varchar].
   * @param className The value of the column [class_name].
   * @return This modified object instance.
   */
  public IExceptionLog setClassName( java.lang.String className );


  /**
   * Get the value relative to the database
   * column [message: varchar].
   * @return The value of the column [message].
   */
  public java.lang.String getMessage();

  /**
   * Set the value relative to the database
   * column [message: varchar].
   * @param message The value of the column [message].
   * @return This modified object instance.
   */
  public IExceptionLog setMessage( java.lang.String message );


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
  public IExceptionLog setRequestId( java.lang.Integer requestId );


  /**
   * Get the value relative to the database
   * column [stack_size: int].
   * @return The value of the column [stack_size].
   */
  public java.lang.Integer getStackSize();

  /**
   * Set the value relative to the database
   * column [stack_size: int].
   * @param stackSize The value of the column [stack_size].
   * @return This modified object instance.
   */
  public IExceptionLog setStackSize( java.lang.Integer stackSize );


  /**
   * Get the value relative to the database
   * column [cd_usu: char].
   * @return The value of the column [cd_usu].
   */
  public java.lang.String getCdUsu();

  /**
   * Set the value relative to the database
   * column [cd_usu: char].
   * @param cdUsu The value of the column [cd_usu].
   * @return This modified object instance.
   */
  public IExceptionLog setCdUsu( java.lang.String cdUsu );


  /**
   * Get the value relative to the database
   * column [nm_usu: varchar].
   * @return The value of the column [nm_usu].
   */
  public java.lang.String getNmUsu();

  /**
   * Set the value relative to the database
   * column [nm_usu: varchar].
   * @param nmUsu The value of the column [nm_usu].
   * @return This modified object instance.
   */
  public IExceptionLog setNmUsu( java.lang.String nmUsu );


  /**
   * Get the value relative to the database
   * column [ts_exception: timestamp].
   * @return The value of the column [ts_exception].
   */
  public java.sql.Timestamp getTsException();

  /**
   * Set the value relative to the database
   * column [ts_exception: timestamp].
   * @param tsException The value of the column [ts_exception].
   * @return This modified object instance.
   */
  public IExceptionLog setTsException( java.sql.Timestamp tsException );
  
  
  public Throwable getException();
  
  public IExceptionLog setException(Throwable th);


}