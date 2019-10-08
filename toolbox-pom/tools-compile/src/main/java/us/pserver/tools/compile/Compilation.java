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
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import us.pserver.tools.Pair;
import us.pserver.tools.Reflect;
import us.pserver.tools.Unchecked;


/**
 *
 * @author juno
 */
public class Compilation {
  
  private final Map<String,CompilationUnit> units;
  
  private final Map<String,Class> classes;
  
  
  public Compilation() {
    units = new TreeMap<>();
    classes = new TreeMap<>();
  }
  
  public Compilation add(CompilationUnit cd) {
    if(cd != null) {
      this.units.put(cd.getClassName(), cd);
    }
    return this;
  }
  
  public Compilation addAll(CompilationUnit... cds) {
    if(cds != null && cds.length > 0) {
      Arrays.asList(cds).forEach(c->units.put(c.getClassName(), c));
    }
    return this;
  }
  
  public CompilationUnit getCompilationUnit(String className) {
    return this.units.get(className);
  }
  
  public <T> Class<T> getCompiledClass(String className) {
    Optional<Class> c = Optional.ofNullable(classes.get(className));
    if(c.isEmpty()) {
      c = classes.entrySet().stream()
          .filter(e->e.getKey().contains(className))
          .map(e->e.getValue())
          .findAny();
    }
    return c.orElseThrow(()->new IllegalArgumentException(
        "Class not found: " + className
    ));
  }
  
  public Collection<Class> getAllCompiledClasses() {
    return classes.values();
  }
  
  public Collection<Class> getAllInstancesOf(Class superClass) {
    return classes.values().stream()
        .filter(c->superClass.isAssignableFrom(c))
        .collect(Collectors.toList());
  }
  
  public <T> Class<T> getInstanceOf(Class superClass) {
    return classes.values().stream()
        .filter(c->superClass.isAssignableFrom(c))
        .findFirst()
        .orElseThrow(()->new IllegalArgumentException(
            "Class not found: ? extends " + superClass.getName()
        ));
  }
  
  public MethodHandles.Lookup getCompiledLookup(String className) {
    return (MethodHandles.Lookup) Reflect.of(getCompiledClass(className))
        .selectMethod("_lookup_")
        .invoke();
  }
  
  public MethodHandles.Lookup getCompiledLookup(Class superClass) {
    return (MethodHandles.Lookup) Reflect.of(getInstanceOf(superClass))
        .selectMethod("_lookup_")
        .invoke();
  }
  
  public void compile() {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    ClassFileManager manager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
    compiler.getTask(null, manager, null, null, null, units.values()).call();
    manager.getCompiledClasses(classes);
  }
  
  public <T> Reflect<T> reflectCompiled(String className) {
    return Reflect.of(getCompiledClass(className), getCompiledLookup(className));
  }
  
  public <T> Reflect<T> reflectInstanceOf(Class superClass) {
    return Reflect.of(getInstanceOf(superClass), getCompiledLookup(superClass));
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
        JavaFileObject.Kind kind, FileObject sibling
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
