package br.com.bb.disec.bean.iface;

/**
 * Generated JavaBean to encapsulate information
 * from the Database table [intranet.stack_trace_log]
 */
public interface IStackTraceLog {

  /**
   * Table column name [exception_id: int].
   */
  public static final String COLUMN_EXCEPTION_ID = "exception_id";

  /**
   * Table column name [stack_level: int].
   */
  public static final String COLUMN_STACK_LEVEL = "stack_level";

  /**
   * Table column name [class_name: varchar].
   */
  public static final String COLUMN_CLASS_NAME = "class_name";

  /**
   * Table column name [method_name: varchar].
   */
  public static final String COLUMN_METHOD_NAME = "method_name";

  /**
   * Table column name [file_name: varchar].
   */
  public static final String COLUMN_FILE_NAME = "file_name";

  /**
   * Table column name [line_number: int].
   */
  public static final String COLUMN_LINE_NUMBER = "line_number";


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
  public IStackTraceLog setExceptionId( java.lang.Integer exceptionId );


  /**
   * Get the value relative to the database
   * column [stack_level: int].
   * @return The value of the column [stack_level].
   */
  public java.lang.Integer getStackLevel();

  /**
   * Set the value relative to the database
   * column [stack_level: int].
   * @param stackLevel The value of the column [stack_level].
   * @return This modified object instance.
   */
  public IStackTraceLog setStackLevel( java.lang.Integer stackLevel );


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
  public IStackTraceLog setClassName( java.lang.String className );


  /**
   * Get the value relative to the database
   * column [method_name: varchar].
   * @return The value of the column [method_name].
   */
  public java.lang.String getMethodName();

  /**
   * Set the value relative to the database
   * column [method_name: varchar].
   * @param methodName The value of the column [method_name].
   * @return This modified object instance.
   */
  public IStackTraceLog setMethodName( java.lang.String methodName );


  /**
   * Get the value relative to the database
   * column [file_name: varchar].
   * @return The value of the column [file_name].
   */
  public java.lang.String getFileName();

  /**
   * Set the value relative to the database
   * column [file_name: varchar].
   * @param fileName The value of the column [file_name].
   * @return This modified object instance.
   */
  public IStackTraceLog setFileName( java.lang.String fileName );


  /**
   * Get the value relative to the database
   * column [line_number: int].
   * @return The value of the column [line_number].
   */
  public java.lang.Integer getLineNumber();

  /**
   * Set the value relative to the database
   * column [line_number: int].
   * @param lineNumber The value of the column [line_number].
   * @return This modified object instance.
   */
  public IStackTraceLog setLineNumber( java.lang.Integer lineNumber );


}