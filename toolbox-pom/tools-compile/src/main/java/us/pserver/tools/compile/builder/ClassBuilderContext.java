/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;
import us.pserver.tools.Pair;
import us.pserver.tools.compile.impl.Typeable;


/**
 *
 * @author Juno
 */
public class ClassBuilderContext {
  
  private final Map<String,Object> props;
  
  private final String name;
  
  public ClassBuilderContext(String name) {
    this.name = Objects.requireNonNull(name, "Bad Null Class Name");
    this.props = new TreeMap<>();
  }
  
  public ClassBuilderContext putProperty(String k, Object v) {
    if(k != null && !k.isBlank()) {
      props.put(k, v);
    }
    return this;
  }
  
  public Optional<Object> getProperty(String k) {
    if(k != null && !k.isBlank()) {
      return Optional.ofNullable(props.get(k));
    }
    return Optional.empty();
  }
  
  public Optional<Object> remove(String k) {
    if(k != null && !k.isBlank()) {
      return Optional.ofNullable(props.remove(k));
    }
    return Optional.empty();
  }
  
  public ClassBuilderContext clearProperties() {
    this.props.clear();
    return this;
  }
  
  public Stream<Pair<String,Object>> streamProperties() {
    return props.entrySet().stream()
        .map(e->new Pair<>(e.getKey(), e.getValue()));
  }
  
  public String getName() {
    return name;
  }


  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + Objects.hashCode(this.props);
    hash = 17 * hash + Objects.hashCode(this.name);
    return hash;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ClassBuilderContext other = (ClassBuilderContext) obj;
    if (!Objects.equals(this.props, other.props)) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }


  @Override
  public String toString() {
    return "ClassBuilderContext{" + "name=" + name + ", props=" + props + '}';
  }
  
}
