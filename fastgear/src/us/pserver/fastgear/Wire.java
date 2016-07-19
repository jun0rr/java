/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca é software livre; você pode redistribuí-la e/ou modificá-la sob os
 * termos da Licença Pública Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a versão 2.1 da Licença, ou qualquer
 * versão posterior.
 * 
 * Esta biblioteca é distribuída na expectativa de que seja útil, porém, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE
 * OU ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública
 * Geral Menor do GNU para mais detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral Menor do GNU junto
 * com esta biblioteca; se não, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endereço 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 */

package us.pserver.fastgear;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 31/05/2016
 */
public interface Wire<T> {

  public void push(T t);
  
  public Optional<T> pull(long timeout);
  
  public Optional<T> peek();
  
  public void close();
  
  public void closeOnEmpty();
  
  public boolean isClosed();
  
  public boolean isAvailable();
  
  public void onAvailable(Consumer<T> cs);
  
  
  public static <U> Wire<U> defaultWire() {
    return new DefWire<>();
  }
  
  
  public static Wire<Void> emptyWire() {
    return new EmptyWire();
  }
  
  
  
  
  public static class EmptyWire implements Wire<Void> {

    @Override public void push(Void t) {}

    @Override
    public Optional<Void> pull(long timeout) {
      return Optional.empty();
    }

    @Override
    public Optional<Void> peek() {
      return Optional.empty();
    }
    
    @Override public void close() {}
    
    @Override public void closeOnEmpty() {}
    
    @Override public boolean isClosed() {
      return false;
    }

    @Override
    public boolean isAvailable() {
      return false;
    }

    @Override public void onAvailable(Consumer<Void> cs) {}

  }
  
  
  
  
  public static class DefWire<T> implements Wire<T> {
    
    private final List<T> list;
    
    private final ReadWriteLock lock;
    
    private final Condition empty;
    
    private final List<Consumer<T>> consumers;
    
    private boolean close;
    
    private boolean closeOnEmpty;
    
    
    public DefWire() {
      list = new LinkedList<>();
      consumers = new LinkedList<>();
      lock = new ReentrantReadWriteLock(true);
      empty = lock.writeLock().newCondition();
      close = false;
      closeOnEmpty = false;
    }
    

    @Override
    public void push(T t) {
      if(close) {
        throw new IllegalStateException("Wire is closed");
      }
      if(t == null) return;
      lock.writeLock().lock();
      try {
        list.add(t);
        this.notifyAwaiters();
      }
      finally {
        lock.writeLock().unlock();
      }
    }
    
    
    private void notifyAwaiters() {
      if(!list.isEmpty()) {
        T value = list.get(list.size() -1);
        for(Consumer c : consumers) {
          c.accept(value);
        }
      }
      empty.signalAll();
    }
    
    
    private void waitFor(long timeout) {
      try {
        if(timeout > 0) {
          empty.await(timeout, TimeUnit.MILLISECONDS);
        } else {
          empty.await();
        }
      } 
      catch(InterruptedException e) {
        throw new RuntimeException(e.getMessage(), e);
      } 
    }
    

    @Override
    public Optional<T> pull(long timeout) {
      if(list.isEmpty() && close) {
        throw new IllegalStateException("Wire is closed");
      }
      lock.writeLock().lock();
      if(list.isEmpty()) {
        this.waitFor(timeout);
      }
      if(list.isEmpty() && close) {
        throw new IllegalStateException("Wire is closed");
      }
      Optional<T> opt;
      try {
        opt = Optional.ofNullable(
            (!list.isEmpty() ? list.remove(0) : null)
        );
      }
      finally {
        lock.writeLock().unlock();
      }
      return opt;
    }
    
    
    @Override
    public Optional<T> peek() {
      if(list.isEmpty() && close) {
        throw new IllegalStateException("Wire is closed");
      }
      lock.readLock().lock();
      try {
        return list.stream().findFirst();
      } finally {
        lock.readLock().unlock();
      }
    }
    
    
    @Override 
    public void close() {
      close = true;
      lock.writeLock().lock();
      try {
        empty.signalAll();
      }
      finally {
        lock.writeLock().unlock();
      }
    }
    
    
    @Override 
    public void closeOnEmpty() {
      closeOnEmpty = true;
      lock.writeLock().lock();
      try {
        empty.signalAll();
      }
      finally {
        lock.writeLock().unlock();
      }
    }
    
    
    @Override
    public boolean isClosed() {
      return close || (closeOnEmpty && !isAvailable());
    }
    
    
    @Override
    public boolean isAvailable() {
      lock.readLock().lock();
      try {
        return !list.isEmpty();
      } finally {
        lock.readLock().unlock();
      }
    }
  

    @Override
    public void onAvailable(Consumer<T> cs) {
      if(close) {
        throw new IllegalStateException("Wire is closed");
      }
      consumers.add(cs);
    }
    
  }
  
    
}