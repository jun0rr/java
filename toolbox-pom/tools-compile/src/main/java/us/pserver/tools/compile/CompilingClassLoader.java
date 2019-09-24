/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import us.pserver.tools.compile.ClassCompilation.CharSequenceJavaFileObject;


/**
 *
 * @author juno
 */
public class CompilingClassLoader extends ClassLoader {
  
  private final Map<String,Class> cache;
  
  private final Map<String,ClassCompilation> cmap;
  
  public CompilingClassLoader() {
    this.cmap = new TreeMap<>();
    this.cache = new TreeMap<>();
  }
  
  public CompilingClassLoader add(ClassCompilation cc) {
    if(cc != null) {
      cmap.put(cc.getClassName(), cc);
    }
    return this;
  }
  
  public CompilingClassLoader clearCache(String name) {
    cache.remove(name);
    return this;
  }
  
  public List<CharSequenceJavaFileObject> getSourceCodeFileList() {
    return cmap.values().stream()
        .map(c->new CharSequenceJavaFileObject(c.getClassName(), c.getSourceCode()))
        .collect(Collectors.toList());
  }
  
  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    if(cache.containsKey(name)) {
      return cache.get(name);
    }
    else {
      return super.loadClass(name);
    }
  }
  
  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    Class<?> cls = cache.get(name);
    if(cls == null) {
      ClassCompilation cc = cmap.get(name);
      if(cc == null) throw new IllegalStateException("ClassCompilation not found: " + name);
      byte[] bytes = cc.compileToBytes();
      cls = defineClass(cc.getClassName(), bytes, 0, bytes.length);
      cache.put(name, cls);
    }
    return cls;
  }
  
}
