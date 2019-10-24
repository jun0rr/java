/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import us.pserver.tools.compile.SourceCode;


/**
 *
 * @author Juno
 */
public interface AnnotationSourceCode {
  
  public static SourceCode of(Object o) {
    if(Class.class == o.getClass()) {
      return of((Class)o);
    }
    else if(CharSequence.class.isAssignableFrom(o.getClass())) {
      return of(o.toString());
    }
    else if(Number.class.isAssignableFrom(o.getClass())) {
      return of((Number)o);
    }
    else if(Character.class.isAssignableFrom(o.getClass()) || char.class == o.getClass()) {
      return of((char)o);
    }
    else if(o.getClass().isArray()) {
      return ofArray(o);
    }
    else return ()->Objects.toString(o);
  }
  
  public static SourceCode of(Number n) {
    if(Long.class == n.getClass()
        || long.class == n.getClass()) {
      return of(n.longValue());
    }
    else if(Double.class == n.getClass()
        || double.class == n.getClass()) {
      return of(n.doubleValue());
    }
    else if(Float.class == n.getClass()
        || float.class == n.getClass()) {
      return of(n.floatValue());
    }
    else {
      return of(n.intValue());
    }
  }
  
  public static SourceCode of(char c) {
    return ()->String.format("'%s'", Character.toString(c));
  }
  
  public static SourceCode of(int i) {
    return ()->Integer.toString(i);
  }
  
  public static SourceCode of(long l) {
    return ()->String.format("%sL", Long.toString(l));
  }
  
  public static SourceCode of(double d) {
    return ()->Double.toString(d);
  }
  
  public static SourceCode of(float f) {
    return ()->String.format("%sF", Float.toString(f));
  }
  
  public static SourceCode of(CharSequence s) {
    return ()->String.format("\"%s\"", s);
  }
  
  public static SourceCode of(Class c) {
    return ()->c.getName();
  }
  
  public static SourceCode ofArray(Object o) {
    if(!o.getClass().isArray()) return of(o);
    ArrayIterator a = new ArrayIterator(o);
    Iterable<String> i = a.stream()
        .map(AnnotationSourceCode::of)
        .map(SourceCode::getSourceCode)
        ::iterator;
    return ()->String.format("{%s}", String.join(",", i));
  }
  
  
  
  static final class ArrayIterator implements Iterator<Object> {
    
    private final int length;
    
    private final Object array;

    private int idx;
    
    public ArrayIterator(Object array) {
      if(array == null || !array.getClass().isArray()) {
        throw new IllegalArgumentException("Not an array: " + Objects.toString(array));
      }
      this.array = array;
      this.length = Array.getLength(array);
      this.idx = 0;
    }
    
    public Stream<Object> stream() {
      return StreamSupport.stream(Spliterators.spliterator(this, length, Spliterator.IMMUTABLE), false);
    }
    
    public int length() {
      return length;
    }
    
    public ArrayIterator reset() {
      idx = 0;
      return this;
    }
    
    @Override
    public boolean hasNext() {
      return idx < length;
    }
    
    @Override
    public Object next() {
      return Array.get(array, idx++);
    }
    
  }
  
}
