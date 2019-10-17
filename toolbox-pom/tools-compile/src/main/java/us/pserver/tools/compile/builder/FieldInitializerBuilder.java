/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.builder;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import us.pserver.tools.compile.impl.FieldInitializer;
import us.pserver.tools.compile.impl.NewFieldInitializer;
import us.pserver.tools.compile.impl.SupplierFieldInitializer;


/**
 *
 * @author juno
 */
public class FieldInitializerBuilder<P extends Builder<?>> extends AbstractNestedBuilder<P,FieldInitializer> {

  private Class initType;
  
  private String name;
  
  private Optional<Supplier<?>> supplier;
  
  public FieldInitializerBuilder(P parent, Consumer<FieldInitializer> onbuild) {
    super(parent, onbuild);
    this.initType = null;
    this.name = null;
    this.supplier = Optional.empty();
  }
  
  public FieldInitializerBuilder<P> setName(String name) {
    if(name != null && !name.isBlank()) {
      this.name = name;
    }
    return this;
  }
  
  public String getName() {
    return name;
  }
  
  public FieldInitializerBuilder<P> setType(Class type) {
    if(type != null) {
      this.initType = type;
    }
    return this;
  }
  
  public Class getType() {
    return initType;
  }
  
  public FieldInitializerBuilder<P> setSupplier(Supplier<?> s) {
    this.supplier = Optional.ofNullable(s);
    return this;
  }
  
  public Optional<Supplier<?>> getSupplier() {
    return supplier;
  }
  
  @Override
  public FieldInitializer build() {
    Objects.requireNonNull(name, "Bad Null Field Name");
    Objects.requireNonNull(initType, "Bad Null Field Type");
    return supplier.isPresent()
        ? new SupplierFieldInitializer(name, initType)
        : new NewFieldInitializer(name, initType);
  }
  
}
