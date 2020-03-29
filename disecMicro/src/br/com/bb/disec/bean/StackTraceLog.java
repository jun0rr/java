package br.com.bb.disec.bean;

import br.com.bb.disec.bean.iface.*;

/**
 * Generated JavaBean to encapsulate information
 * from the Database table [intranet.stack_trace_log]
 */
public class StackTraceLog implements IStackTraceLog {

  private java.lang.Integer exceptionId;
  private java.lang.Integer stackLevel;
  private java.lang.String className;
  private java.lang.String methodName;
  private java.lang.String fileName;
  private java.lang.Integer lineNumber;

  public StackTraceLog() {
    this.exceptionId = 0;
    this.stackLevel = -1;
    this.className = null;
    this.methodName = null;
    this.fileName = null;
    this.lineNumber = -1;
  }

  public StackTraceLog(
      java.lang.Integer exceptionId, 
      java.lang.Integer stackLevel, 
      java.lang.String className, 
      java.lang.String methodName, 
      java.lang.String fileName, 
      java.lang.Integer lineNumber) {
    this.exceptionId = exceptionId;
    this.stackLevel = stackLevel;
    this.className = className;
    this.methodName = methodName;
    this.fileName = fileName;
    this.lineNumber = lineNumber;
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
  public StackTraceLog setExceptionId( java.lang.Integer exceptionId ) {
    this.exceptionId = exceptionId;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [stack_level: int].
   * @return The value of the column [stack_level].
   */
  @Override
  public java.lang.Integer getStackLevel() {
    return stackLevel;
  }

  /**
   * Set the value relative to the database
   * column [stack_level: int].
   * @param stackLevel The value of the column [stack_level].
   * @return This modified object instance.
   */
  @Override
  public StackTraceLog setStackLevel( java.lang.Integer stackLevel ) {
    this.stackLevel = stackLevel;
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
  public StackTraceLog setClassName( java.lang.String className ) {
    this.className = className;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [method_name: varchar].
   * @return The value of the column [method_name].
   */
  @Override
  public java.lang.String getMethodName() {
    return methodName;
  }

  /**
   * Set the value relative to the database
   * column [method_name: varchar].
   * @param methodName The value of the column [method_name].
   * @return This modified object instance.
   */
  @Override
  public StackTraceLog setMethodName( java.lang.String methodName ) {
    this.methodName = methodName;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [file_name: varchar].
   * @return The value of the column [file_name].
   */
  @Override
  public java.lang.String getFileName() {
    return fileName;
  }

  /**
   * Set the value relative to the database
   * column [file_name: varchar].
   * @param fileName The value of the column [file_name].
   * @return This modified object instance.
   */
  @Override
  public StackTraceLog setFileName( java.lang.String fileName ) {
    this.fileName = fileName;
    return this;
  }


  /**
   * Get the value relative to the database
   * column [line_number: int].
   * @return The value of the column [line_number].
   */
  @Override
  public java.lang.Integer getLineNumber() {
    return lineNumber;
  }

  /**
   * Set the value relative to the database
   * column [line_number: int].
   * @param lineNumber The value of the column [line_number].
   * @return This modified object instance.
   */
  @Override
  public StackTraceLog setLineNumber( java.lang.Integer lineNumber ) {
    this.lineNumber = lineNumber;
    return this;
  }


}