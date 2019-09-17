/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 *
 * @author juno
 */
public interface ConditionalSupplier<T> extends Supplier<T>, BooleanSupplier {
  
  public <S> ConditionalSupplier<S> map(Function<T,S> fn);
  
  
  
  public static <X> ConditionalSupplierBuilder<X> eval(BooleanSupplier test) {
    return new ConditionalSupplierBuilder(Objects.requireNonNull(test));
  }
  
  
  static class ConditionalSupplierImpl<X> implements ConditionalSupplier<X> {
    
    private final BooleanSupplier test;
    
    private final Supplier<X> trueValue; 
    
    private final Optional<Supplier<X>> elseValue;
    
    private final Optional<Supplier<Throwable>> elseThrow;
    
    private ConditionalSupplierImpl(
        BooleanSupplier test,
        Supplier<X> trueValue,
        Optional<Supplier<X>> elseValue,
        Optional<Supplier<Throwable>> elseThrow
    ) {
      this.test = test;
      this.trueValue = trueValue;
      this.elseValue = elseValue;
      this.elseThrow = elseThrow;
    }
    
    @Override
    public X get() {
      if(test.getAsBoolean()) {
        return trueValue.get();
      }
      else if(elseValue.isPresent()) {
        return elseValue.get().get();
      }
      else {
        throw Unchecked.<RuntimeException>unchecked(elseThrow.get().get());
      }
    }
    
    @Override
    public boolean getAsBoolean() {
      return test.getAsBoolean();
    }
    
    @Override
    public <Y> ConditionalSupplier<Y> map(Function<X,Y> fn) {
      Supplier<Y> trueValue = ()->fn.apply(this.trueValue.get());
      Optional<Supplier<Y>> elseValue = this.elseValue.isPresent()
          ? Optional.of(()->fn.apply(this.elseValue.get().get()))
          : Optional.empty();
      return new ConditionalSupplierImpl(test, trueValue, elseValue, elseThrow);
    }
    
    @Override
    public String toString() {
      return "ConditionalSupplierImpl{" + "test=" + test + ", trueValue=" + trueValue + ", elseValue=" + elseValue + ", elseThrow=" + elseThrow + '}';
    }
    
  }
  
  
  
  public static class ConditionalSupplierBuilder<X> {
    
    private Optional<ConditionalSupplierBuilder<X>> parent;
    
    private final BooleanSupplier test;
    
    private final Optional<Supplier<X>> trueValue; 
    
    private final Optional<Supplier<X>> elseValue;
    
    private final Optional<Supplier<Throwable>> elseThrow;
    
    private ConditionalSupplierBuilder(
        Optional<ConditionalSupplierBuilder<X>> parent,
        BooleanSupplier test,
        Optional<Supplier<X>> trueValue,
        Optional<Supplier<X>> elseValue,
        Optional<Supplier<Throwable>> elseThrow
    ) {
      this.parent = parent;
      this.test = test;
      this.trueValue = trueValue;
      this.elseValue = elseValue;
      this.elseThrow = elseThrow;
    }
    
    private ConditionalSupplierBuilder(ConditionalSupplierBuilder<X> parent, BooleanSupplier test) {
      this(
          Optional.of(parent),
          test,
          Optional.empty(),
          Optional.empty(),
          Optional.empty()
      );
    }
    
    protected ConditionalSupplierBuilder(BooleanSupplier test) {
      this(
          Optional.empty(),
          test,
          Optional.empty(),
          Optional.empty(),
          Optional.empty()
      );
    }
    
    public ConditionalSupplierBuilder<X> ifTrueSupply(Supplier<X> s) {
      return new ConditionalSupplierBuilder(
          parent,
          test, 
          Optional.ofNullable(s),
          elseValue,
          elseThrow
      );
    }
    
    public ConditionalSupplierBuilder<X> ifTrueEval(BooleanSupplier test) {
      return new ConditionalSupplierBuilder(this, test);
    }
    
    public ConditionalSupplierBuilder<X> elseSupply(Supplier<X> s) {
      return new ConditionalSupplierBuilder(
          parent,
          test, 
          trueValue,
          Optional.ofNullable(s),
          elseThrow
      );
    }
    
    public ConditionalSupplierBuilder<X> elseEval(BooleanSupplier test) {
      return new ConditionalSupplierBuilder(this, test);
    }
    
    public ConditionalSupplierBuilder<X> elseThrows(Supplier<Throwable> s) {
      return new ConditionalSupplierBuilder(
          parent,
          test, 
          trueValue,
          elseValue,
          Optional.ofNullable(s)
      );
    }
    
    public ConditionalSupplier<X> build() {
      if(trueValue.isEmpty()) {
        throw new IllegalStateException("No Supplier for true state");
      }
      if(elseValue.isEmpty() && elseThrow.isEmpty()) {
        throw new IllegalStateException("No Supplier for else state");
      }
      return new ConditionalSupplierImpl<X>(test, trueValue.get(), elseValue, elseThrow);
    }
    
    public ConditionalSupplierBuilder<X> endTrueConditional() {
      if(parent.isEmpty()) {
        throw new IllegalStateException("Not in appendable ConditionalSupplierBuilder");
      }
      return new ConditionalSupplierBuilder(
          parent.get().parent,
          parent.get().test, 
          Optional.of(build()),
          parent.get().elseValue,
          parent.get().elseThrow
      );
    }
    
    public ConditionalSupplierBuilder<X> endElseConditional() {
      if(parent.isEmpty()) {
        throw new IllegalStateException("Not in appendable ConditionalSupplierBuilder");
      }
      return new ConditionalSupplierBuilder(
          parent.get().parent,
          parent.get().test, 
          parent.get().trueValue,
          Optional.of(build()),
          parent.get().elseThrow
      );
    }
    
  }
  
}
