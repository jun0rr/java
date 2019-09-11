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


/**
 *
 * @author juno
 */
public interface Conditional<A,B> extends Function<A,B>, Predicate<A> {
  
  public Conditional<A,B> and(Conditional<A,B> and);
  
  public Conditional<A,B> or(Conditional<A,B> and);
  
  public <C> Conditional<A,C> andThen(Conditional<B,C> after);
  
  public <Z> Conditional<Z,B> compose(Conditional<Z,A> before);
  
  
  
  public static <X,Y> ConditionalBuilder<X,Y> eval(Predicate<X> test) {
    return new ConditionalBuilder(Objects.requireNonNull(test));
  }
  
  
  
  static class ConditionalImpl<X,Y> implements Conditional<X,Y> {
    
    private final Predicate<X> test;
    
    private final Y trueValue; 
    
    private final Optional<Y> elseValue;
    
    private final Optional<Throwable> elseThrow;
    
    private final Optional<Conditional<X,Y>> and;
    
    private final Optional<Conditional<X,Y>> or;
    
    private ConditionalImpl(
        Predicate<X> test,
        Y trueValue,
        Optional<Y> elseValue,
        Optional<Conditional<X,Y>> and,
        Optional<Conditional<X,Y>> or,
        Optional<Throwable> elseThrow
    ) {
      this.test = test;
      this.trueValue = trueValue;
      this.elseValue = elseValue;
      this.and = and;
      this.or = or;
      this.elseThrow = elseThrow;
    }
    
    @Override
    public Y apply(X x) {
      if(test.test(x)) {
        return and.isPresent() && and.get().test(x) ? and.get().apply(x) : trueValue;
      }
      else if(or.isPresent()) {
        return or.get().apply(x);
      }
      else if(elseValue.isPresent()) {
        return elseValue.get();
      }
      else {
        throw Unchecked.<RuntimeException>unchecked(elseThrow.get());
      }
    }
    
    @Override
    public boolean test(X x) {
      return test.test(x);
    }
    
    @Override
    public Conditional<X,Y> and(Conditional<X,Y> and) {
      return new ConditionalImpl(test, trueValue, elseValue, Optional.of(and), or, elseThrow);
    }
    
    @Override
    public Conditional<X,Y> or(Conditional<X,Y> or) {
      return new ConditionalImpl(test, trueValue, elseValue, and, Optional.of(or), elseThrow);
    }
    
    @Override
    public <R> Conditional<X,R> andThen(Conditional<Y,R> after) {
      Predicate<X> then = x->test.test(x) && after.test(apply(x));
      ConditionalImpl<Y,R> afteri = (ConditionalImpl)after;
      Optional<Conditional<X,R>> and = this.and.isPresent() && afteri.and.isPresent() 
          ? Optional.of(this.and.get().andThen(afteri.and.get()))
          : Optional.empty();
      Optional<Conditional<X,R>> or = this.or.isPresent() && afteri.or.isPresent() 
          ? Optional.of(this.or.get().andThen(afteri.or.get()))
          : Optional.empty();
      return new ConditionalImpl<X,R>(
          then, 
          afteri.trueValue, 
          afteri.elseValue, 
          and, or,
          afteri.elseThrow.isPresent() ? afteri.elseThrow : elseThrow
      );
    }
    
    @Override
    public <Z> Conditional<Z,Y> compose(Conditional<Z,X> before) {
      Predicate<Z> then = v->before.test(v) && test(before.apply(v));
      ConditionalImpl<Z,X> beforei = (ConditionalImpl)before;
      Optional<Conditional<Z,Y>> and = this.and.isPresent() && beforei.and.isPresent() 
          ? Optional.of(this.and.get().compose(beforei.and.get())) 
          : Optional.empty();
      Optional<Conditional<Z,Y>> or = this.or.isPresent() && beforei.or.isPresent() 
          ? Optional.of(this.or.get().compose(beforei.or.get())) 
          : Optional.empty();
      return new ConditionalImpl<Z,Y>(
          then, 
          this.trueValue, 
          this.elseValue, 
          and, or,
          beforei.elseThrow.or(()->this.elseThrow)
      );
    }


    @Override
    public String toString() {
      return "ConditionalImpl{" + "test=" + test + ", trueValue=" + trueValue + ", elseValue=" + elseValue + ", elseThrow=" + elseThrow + ", and=" + and + ", or=" + or + '}';
    }
    
  }
  
  
  
  public static class ConditionalBuilder<X,Y> {
    
    private Optional<ConditionalBuilder<X,Y>> parent;
    
    private final Predicate<X> test;
    
    private final Optional<Y> trueValue; 
    
    private final Optional<Y> elseValue;
    
    private final Optional<Throwable> elseThrow;
    
    private final Optional<Conditional<X,Y>> and;
    
    private final Optional<Conditional<X,Y>> or;
    
    private ConditionalBuilder(
        Optional<ConditionalBuilder<X,Y>> parent,
        Predicate<X> test,
        Optional<Y> trueValue,
        Optional<Y> elseValue,
        Optional<Conditional<X,Y>> and,
        Optional<Conditional<X,Y>> or,
        Optional<Throwable> elseThrow
    ) {
      this.test = test;
      this.trueValue = trueValue;
      this.elseValue = elseValue;
      this.and = and;
      this.or = or;
      this.elseThrow = elseThrow;
    }
    
    private ConditionalBuilder(ConditionalBuilder<X,Y> parent, Predicate<X> test) {
      this(
          Optional.of(parent),
          test,
          Optional.empty(),
          Optional.empty(),
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
          Optional.empty(),
          Optional.empty(),
          Optional.empty()
      );
    }
    
    public ConditionalBuilder<X,Y> ifTrueReturns(Y y) {
      return new ConditionalBuilder(
          parent,
          test, 
          Optional.ofNullable(y),
          elseValue,
          and,
          or,
          elseThrow
      );
    }
    
    public ConditionalBuilder<X,Y> elseReturns(Y y) {
      return new ConditionalBuilder(
          parent,
          test, 
          trueValue,
          Optional.ofNullable(y),
          and,
          or,
          elseThrow
      );
    }
    
    public ConditionalBuilder<X,Y> elseThrows(Throwable t) {
      return new ConditionalBuilder(
          parent,
          test, 
          trueValue,
          elseValue,
          and,
          or,
          Optional.ofNullable(t)
      );
    }
    
    public ConditionalBuilder<X,Y> and(Predicate<X> test) {
      return new ConditionalBuilder(this, test);
    }
    
    public ConditionalBuilder<X,Y> or(Predicate<X> test) {
      return new ConditionalBuilder(this, test);
    }
    
    public Conditional<X,Y> build() {
      if(trueValue.isEmpty()) {
        throw new IllegalStateException("No value to return on true");
      }
      if(elseValue.isEmpty() && elseThrow.isEmpty()) {
        throw new IllegalStateException("No value to return on else");
      }
      return new ConditionalImpl<X,Y>(test, trueValue.get(), elseValue, and, or, elseThrow);
    }
    
    public ConditionalBuilder<X,Y> buildAnd() {
      return new ConditionalBuilder(
          parent,
          test, 
          trueValue,
          elseValue,
          Optional.of(build()),
          or,
          elseThrow
      );
    }
    
    public ConditionalBuilder<X,Y> buildOr() {
      return new ConditionalBuilder(
          parent,
          test, 
          trueValue,
          elseValue,
          Optional.of(build()),
          or,
          elseThrow
      );
    }
    
  }
  
}
