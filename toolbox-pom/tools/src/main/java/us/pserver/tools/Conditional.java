/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 *
 * @author juno
 */
public interface Conditional<A,B> extends Function<A,B>, Predicate<A> {
  
  public <C> Conditional<A,C> andThen(Conditional<B,C> after);
  
  public <Z> Conditional<Z,B> compose(Conditional<Z,A> before);
  
  
  
  public static <X,Y> ConditionalBuilder<X,Y> eval(Predicate<X> test) {
    return new ConditionalBuilder(Objects.requireNonNull(test));
  }
  
  
  
  static class ConditionalImpl<X,Y> implements Conditional<X,Y> {
    
    private final Predicate<X> test;
    
    private final Function<X,Y> trueValue; 
    
    private final Optional<Function<X,Y>> elseValue;
    
    private final Optional<Function<X,Throwable>> elseThrow;
    
    private ConditionalImpl(
        Predicate<X> test,
        Function<X,Y> trueValue,
        Optional<Function<X,Y>> elseValue,
        Optional<Function<X,Throwable>> elseThrow
    ) {
      this.test = test;
      this.trueValue = trueValue;
      this.elseValue = elseValue;
      this.elseThrow = elseThrow;
    }
    
    @Override
    public Y apply(X x) {
      if(test.test(x)) {
        return trueValue.apply(x);
      }
      else if(elseValue.isPresent()) {
        return elseValue.get().apply(x);
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
    public <R> Conditional<X,R> andThen(Conditional<Y,R> after) {
      Predicate<X> then = x->test.test(x) && after.test(apply(x));
      ConditionalImpl<Y,R> afteri = (ConditionalImpl)after;
      Optional<Function<X,R>> elseValue = this.elseValue.isPresent() && afteri.elseValue.isPresent() 
          ? Optional.of(this.elseValue.get().andThen(afteri.elseValue.get())) 
          : Optional.empty();
      Optional<Function<X,Throwable>> elseThrow = afteri.elseThrow.isPresent() 
          ? Optional.of(x->afteri.elseThrow.get().apply(this.apply(x))) 
          : this.elseThrow;
      return new ConditionalImpl<X,R>(
          then, 
          this.trueValue.andThen(afteri.trueValue), 
          elseValue, 
          elseThrow
      );
    }
    
    @Override
    public <Z> Conditional<Z,Y> compose(Conditional<Z,X> before) {
      Predicate<Z> then = v->before.test(v) && test(before.apply(v));
      ConditionalImpl<Z,X> beforei = (ConditionalImpl)before;
      Function<Z,Y> trueValue = beforei.trueValue.andThen(this.trueValue);
      Optional<Function<Z,Y>> elseValue = beforei.elseValue.isPresent() && this.elseValue.isPresent() 
          ? Optional.of(beforei.elseValue.get().andThen(this.elseValue.get())) 
          : Optional.empty();
      Optional<Function<Z,Throwable>> elseThrow = beforei.elseThrow.isPresent() 
          ? beforei.elseThrow : this.elseThrow.isPresent() 
          ? Optional.of( z->this.elseThrow.get().apply(beforei.apply(z)) ) : Optional.empty();
      return new ConditionalImpl<Z,Y>(
          then, 
          trueValue, 
          elseValue, 
          elseThrow
      );
    }
    
    @Override
    public String toString() {
      return "ConditionalImpl{" + "test=" + test + ", trueValue=" + trueValue + ", elseValue=" + elseValue + ", elseThrow=" + elseThrow + '}';
    }
    
  }
  
  
  
  public static class ConditionalBuilder<X,Y> {
    
    private Optional<ConditionalBuilder<X,Y>> parent;
    
    private final Predicate<X> test;
    
    private final Optional<Function<X,Y>> trueValue; 
    
    private final Optional<Function<X,Y>> elseValue;
    
    private final Optional<Function<X,Throwable>> elseThrow;
    
    private ConditionalBuilder(
        Optional<ConditionalBuilder<X,Y>> parent,
        Predicate<X> test,
        Optional<Function<X,Y>> trueValue,
        Optional<Function<X,Y>> elseValue,
        Optional<Function<X,Throwable>> elseThrow
    ) {
      this.parent = parent;
      this.test = test;
      this.trueValue = trueValue;
      this.elseValue = elseValue;
      this.elseThrow = elseThrow;
    }
    
    private ConditionalBuilder(ConditionalBuilder<X,Y> parent, Predicate<X> test) {
      this(
          Optional.of(parent),
          test,
          Optional.empty(),
          Optional.empty(),
          Optional.empty()
      );
    }
    
    protected ConditionalBuilder(Predicate<X> test) {
      this(
          Optional.empty(),
          test,
          Optional.empty(),
          Optional.empty(),
          Optional.empty()
      );
    }
    
    public ConditionalBuilder<X,Y> ifTrueApply(Function<X,Y> fn) {
      return new ConditionalBuilder(
          parent,
          test, 
          Optional.ofNullable(fn),
          elseValue,
          elseThrow
      );
    }
    
    public ConditionalBuilder<X,Y> ifTrueEval(Predicate<X> test) {
      return new ConditionalBuilder(this, test);
    }
    
    public ConditionalBuilder<X,Y> elseApply(Function<X,Y> fn) {
      return new ConditionalBuilder(
          parent,
          test, 
          trueValue,
          Optional.ofNullable(fn),
          elseThrow
      );
    }
    
    public ConditionalBuilder<X,Y> elseEval(Predicate<X> test) {
      return new ConditionalBuilder(this, test);
    }
    
    public ConditionalBuilder<X,Y> elseThrows(Function<X,Throwable> fn) {
      return new ConditionalBuilder(
          parent,
          test, 
          trueValue,
          elseValue,
          Optional.ofNullable(fn)
      );
    }
    
    public Conditional<X,Y> build() {
      if(trueValue.isEmpty()) {
        throw new IllegalStateException("No Function to apply on true");
      }
      if(elseValue.isEmpty() && elseThrow.isEmpty()) {
        throw new IllegalStateException("No Function to apply on else");
      }
      return new ConditionalImpl<X,Y>(test, trueValue.get(), elseValue, elseThrow);
    }
    
    public ConditionalBuilder<X,Y> endTrueConditional() {
      if(parent.isEmpty()) {
        throw new IllegalStateException("Not in appendable ConditionalBuilder");
      }
      return new ConditionalBuilder(
          parent.get().parent,
          parent.get().test, 
          Optional.of(build()),
          parent.get().elseValue,
          parent.get().elseThrow
      );
    }
    
    public ConditionalBuilder<X,Y> endElseConditional() {
      if(parent.isEmpty()) {
        throw new IllegalStateException("Not in appendable ConditionalBuilder");
      }
      return new ConditionalBuilder(
          parent.get().parent,
          parent.get().test, 
          parent.get().trueValue,
          Optional.of(build()),
          parent.get().elseThrow
      );
    }
    
  }
  
}
