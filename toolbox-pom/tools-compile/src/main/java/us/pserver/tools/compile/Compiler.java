/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import us.pserver.tools.Pair;
import us.pserver.tools.Reflect;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class Compiler {
  
  private final Map<String,CompilationUnit> code;
  
  private final Map<String,Class> classes;
  
  
  public Compiler() {
    code = new TreeMap<>();
    classes = new TreeMap<>();
  }
  
  public Compiler add(CompilationUnit cd) {
    if(cd != null) {
      this.code.put(cd.getClassName(), cd);
    }
    return this;
  }
  
  public CompilationUnit getCompilationUnit(String className) {
    return this.code.get(className);
  }
  
  public <T> Class<T> getCompiledClass(String className) {
    Class c = classes.get(className);
    if(c == null) {
      classes.
    }
    if(c == null) throw new IllegalArgumentException("Class not compiled: " + className);
    return c;
  }
  
  public MethodHandles.Lookup getCompiledLookup(String className) {
    return (MethodHandles.Lookup) Reflect.of(getCompiledClass(className))
        .selectMethod("_lookup_")
        .invoke();
  }
  
  public void compile() {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    ClassFileManager manager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
    compiler.getTask(null, manager, null, null, null, code.values()).call();
    manager.getCompiledClasses(classes);
  }
  
  
  public <T> Reflect<T> reflectCompiled(String className) {
    return Reflect.of(getCompiledClass(className), getCompiledLookup(className));
  }
  
  
  
  
  
  static final class ByteArrayJavaFileObject extends SimpleJavaFileObject {

    private final ByteArrayOutputStream os = new ByteArrayOutputStream();

    ByteArrayJavaFileObject(String name, JavaFileObject.Kind kind) {
      super(URI.create("string:///" 
          + name.replace('.', '/') 
          + kind.extension), kind
      );
    }

    byte[] getBytes() {
      return os.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() {
      return os;
    }
    
  }
  
  
  
  static final class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private final Map<String,ByteArrayJavaFileObject> objects;
    
    ClassFileManager(StandardJavaFileManager m) {
      super(m);
      this.objects = new TreeMap<>();
    }
    
    @Override
    public JavaFileObject getJavaFileForOutput(
        JavaFileManager.Location location, String className, 
        ClassCompilation.JavaFileObject.Kind kind, FileObject sibling
    ) {
      ByteArrayJavaFileObject obj = objects.get(className);
      if(obj == null) {
        obj = new ByteArrayJavaFileObject(className, kind);
        objects.put(className, obj);
      }
      return obj;
    }
    
    public byte[] getCompiledBytes(String className) {
      ByteArrayJavaFileObject obj = objects.get(className);
      if(obj == null) {
        throw new IllegalArgumentException("No such JavaFileObject: " + className);
      }
      return obj.getBytes();
    }
    
    public void getCompiledClasses(Map<String,Class> map) {
      ClassLoader loader = new ClassLoader() {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
          byte[] b = getCompiledBytes(name);
          return defineClass(name, b, 0, b.length);
        }
      };
      objects.entrySet().stream()
          .map(e->new Pair<String,Class>(e.getKey(), Unchecked.call(()->loader.loadClass(e.getKey()))))
          .forEach(p->map.put(p.a, p.b));
    }
    
  }
  
  
}
