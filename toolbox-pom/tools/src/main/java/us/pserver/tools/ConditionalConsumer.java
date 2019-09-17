/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 *
 * @author juno
 */
public interface ConditionalConsumer<T> extends Consumer<T>, Predicate<T> {
  
  public <Z> ConditionalConsumer<Z> compose(Function<Z,T> fn);
  
  
  
  public static <X> ConditionalConsumerBuilder<X> eval(Predicate<X> test) {
    return new ConditionalConsumerBuilder(Objects.requireNonNull(test));
  }
  
  
  
  static class ConditionalConsumerImpl<X> implements ConditionalConsumer<X> {
    
    private final Predicate<X> test;
    
    private final Consumer<X> trueValue; 
    
    private final Optional<Consumer<X>> elseValue;
    
    private final Optional<Function<X,Throwable>> elseThrow;
    
    private ConditionalConsumerImpl(
        Predicate<X> test,
        Consumer<X> trueValue,
        Optional<Consumer<X>> elseValue,
        Optional<Function<X,Throwable>> elseThrow
    ) {
      this.test = test;
      this.trueValue = trueValue;
      this.elseValue = elseValue;
      this.elseThrow = elseThrow;
    }
    
    @Override
    public void accept(X x) {
      if(test.test(x)) {
        trueValue.accept(x);
      }
      else if(elseValue.isPresent()) {
        elseValue.get().accept(x);
      }
      else {
        throw Unchecked.<RuntimeException>unchecked(elseThrow.get().apply(x));
      }
    }
    
    @Override
    public boolean test(X x) {
      return test.test(x);
    }
    
    @Override
    public <Z> ConditionalConsumer<Z> compose(Function<Z,X> fn) {
      Consumer<Z> trueValue = z->this.trueValue.accept(fn.apply(z));
      Optional<Consumer<Z>> elseValue = this.elseValue.isPresent() 
          ? Optional.of(z->this.elseValue.get().accept(fn.apply(z)))
          : Optional.empty();
      Optional<Function<Z,Throwable>> elseThrow = this.elseThrow.isPresent()
          ? Optional.of(z->this.elseThrow.get().compose(fn).apply(z))
          : Optional.empty();
      return new ConditionalConsumerImpl(test, trueValue, elseValue, elseThrow);
    }
    
    @Override
    public String toString() {
      return "ConditionalImpl{" + "test=" + test + ", trueValue=" + trueValue + ", elseValue=" + elseValue + ", elseThrow=" + elseThrow + '}';
    }
    
  }
  
  
  
  public static class ConditionalConsumerBuilder<X> {
    
    private Optional<ConditionalConsumerBuilder<X>> parent;
    
    private final Predicate<X> test;
    
    private final Optional<Consumer<X>> trueValue; 
    
    private final Optional<Consumer<X>> elseValue;
    
    private final Optional<Function<X,Throwable>> elseThrow;
    
    private ConditionalConsumerBuilder(
        Optional<ConditionalConsumerBuilder<X>> parent,
        Predicate<X> test,
        Optional<Consumer<X>> trueValue,
        Optional<Consumer<X>> elseValue,
        Optional<Function<X,Throwable>> elseThrow
    ) {
      this.parent = parent;
      this.test = test;
      this.trueValue = trueValue;
      this.elseValue = elseValue;
      this.elseThrow = elseThrow;
    }
    
    private ConditionalConsumerBuilder(ConditionalConsumerBuilder<X> parent, Predicate<X> test) {
      this(
          Optional.of(parent),
          test,
          Optional.empty(),
          Optional.empty(),
          Optional.empty()
      );
    }
    
    protected ConditionalConsumerBuilder(Predicate<X> test) {
      this(
          Optional.empty(),
          test,
          Optional.empty(),
          Optional.empty(),
          Optional.empty()
      );
    }
    
    public ConditionalConsumerBuilder<X> ifTrueAccept(Consumer<X> cs) {
      return new ConditionalConsumerBuilder(
          parent,
          test, 
          Optional.ofNullable(cs),
          elseValue,
          elseThrow
      );
    }
    
    public ConditionalConsumerBuilder<X> ifTrueEval(Predicate<X> test) {
      return new ConditionalConsumerBuilder(this, test);
    }
    
    public ConditionalConsumerBuilder<X> elseAccept(Consumer<X> cs) {
      return new ConditionalConsumerBuilder(
          parent,
          test, 
          trueValue,
          Optional.ofNullable(cs),
          elseThrow
      );
    }
    
    public ConditionalConsumerBuilder<X> elseEval(Predicate<X> test) {
      return new ConditionalConsumerBuilder(this, test);
    }
    
    public ConditionalConsumerBuilder<X> elseThrows(Function<X,Throwable> fn) {
      return new ConditionalConsumerBuilder(
          parent,
          test, 
          trueValue,
          elseValue,
          Optional.ofNullable(fn)
      );
    }
    
    public ConditionalConsumer<X> build() {
      if(trueValue.isEmpty()) {
        throw new IllegalStateException("No Function to apply on true");
      }
      if(elseValue.isEmpty() && elseThrow.isEmpty()) {
        throw new IllegalStateException("No Function to apply on else");
      }
      return new ConditionalConsumerImpl<X>(test, trueValue.get(), elseValue, elseThrow);
    }
    
    public ConditionalConsumerBuilder<X> endTrueConditional() {
      if(parent.isEmpty()) {
        throw new IllegalStateException("Not in appendable ConditionalBuilder");
      }
      return new ConditionalConsumerBuilder(
          parent.get().parent,
          parent.get().test, 
          Optional.of(build()),
          parent.get().elseValue,
          parent.get().elseThrow
      );
    }
    
    public ConditionalConsumerBuilder<X> endElseConditional() {
      if(parent.isEmpty()) {
        throw new IllegalStateException("Not in appendable ConditionalBuilder");
      }
      return new ConditionalConsumerBuilder(
          parent.get().parent,
          parent.get().test, 
          parent.get().trueValue,
          Optional.of(build()),
          parent.get().elseThrow
      );
    }
    
  }
  
}
