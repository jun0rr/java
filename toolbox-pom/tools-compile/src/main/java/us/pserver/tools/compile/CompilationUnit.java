/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.net.URI;
import java.util.Objects;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;


/**
 *
 * @author juno
 */
public class CompilationUnit extends SimpleJavaFileObject {
  
  public static final String LOOKUP_CODE = "public static MethodHandles.Lookup _lookup_() { return MethodHandles.lookup(); }";
  
  public static final String IMPORT_LOOKUP_CODE = "import java.lang.invoke.MethodHandles;";
  
  public static final String LN = "\n";
  
  
  private final String name;
  
  private final StringBuilder code;
  
  
  public CompilationUnit(String name) {
    super(URI.create("string:///" 
        + name.replace('.', '/') 
        + JavaFileObject.Kind.SOURCE.extension), 
        JavaFileObject.Kind.SOURCE
    );
    this.name = Objects.requireNonNull(name);
    this.code = new StringBuilder();
  }
  
  public String getClassName() {
    return name;
  }
  
  public CompilationUnit append(String s) {
    this.code.append(s);
    return this;
  }
  
  public CompilationUnit append(Object o) {
    this.code.append(o);
    return this;
  }
  
  public CompilationUnit append(String str, Object... args) {
    this.code.append(String.format(str, args));
    return this;
  }
  
  public CompilationUnit appendln(String s) {
    this.code.append(s).append(LN);
    return this;
  }
  
  public CompilationUnit appendln(Object o) {
    this.code.append(o).append(LN);
    return this;
  }
  
  public CompilationUnit appendln(String str, Object... args) {
    this.code.append(String.format(str, args)).append(LN);
    return this;
  }
  
  public String getSourceCode() {
    return this.code.toString();
  }
  
  private void insertLookupCode() {
    if(code.indexOf(LOOKUP_CODE) < 0) {
      int ipkg = code.indexOf("package");
      int afterpkg = code.indexOf(";", ipkg);
      code.insert(afterpkg + 1, IMPORT_LOOKUP_CODE);
      int ibrk = code.lastIndexOf("}");
      code.insert(ibrk, LOOKUP_CODE);
    }
  }
  
  @Override
  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
    insertLookupCode();
    return this.code.toString();
  }
  
}
