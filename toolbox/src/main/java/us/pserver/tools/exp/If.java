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

package us.pserver.tools.exp;

import java.util.function.Function;
import java.util.function.Predicate;
import us.pserver.tools.Match;
import us.pserver.tools.check.Check;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 01/02/2018
 */
public class If<T,R> implements Predicate<T> {

  protected final Predicate<T> predicate;
  
  protected final Function<T,R> ifState;
  
  protected final Function<T,R> elseState;
  
  
  protected If(Predicate<T> prd, Function<T,R> ifs, Function<T,R> els) {
    this.predicate = prd;
    this.ifState = ifs;
    this.elseState = els;
  }
  
  
  public If(Predicate<T> prd) {
    this(Match.notNull(prd).getOrFail("Bad null Predicate"), null, null);
  }
  
  
  public If<T,R> then(Function<T,R> ifState) {
    return new If(predicate, ifState, elseState);
  }
  
  
  public If<T,R> elseDo(Function<T,R> elseState) {
    return new If(predicate, ifState, elseState);
  }
  
  
  public R eval(T obj) {
    if(predicate.test(obj)) {
      return Check.of(IllegalStateException.class)
          .on(ifState).getOrFail("Default statement not defined")
          .apply(obj);
    }
    else {
      return Check.of(IllegalStateException.class)
          .on(elseState).getOrFail("Else statement not defined")
          .apply(obj);
    }
  }


  @Override
  public boolean test(T t) {
    return predicate.test(t);
  }


  @Override
  public If<T,R> and(Predicate<? super T> other) {
    return new If(predicate.and(other), ifState, elseState);
  }


  @Override
  public If<T,R> negate() {
    return new If(predicate.negate(), ifState, elseState);
  }


  @Override
  public If<T,R> or(Predicate<? super T> other) {
    return new If(predicate.or(other), ifState, elseState);
  }
  
  
  
  public static <U,S> If<U,S> of(Predicate<U> prd) {
    return new If<>(prd);
  }
  
}