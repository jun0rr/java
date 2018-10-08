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

package us.pserver.jpx.event;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BiConsumer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 11/08/2018
 */
@FunctionalInterface
public interface EventListener<T, E extends Event> extends BiConsumer<T,E> {
  
  public default Collection<Event.Type> getInterests() {
    return Collections.unmodifiableList(new LinkedList() {
      public boolean contains(Object o) {
        return true;
      }
      public boolean containsAll(Collection c) {
        return true;
      }
    });
  }
  
  
  public static <U,F extends Event> EventListener<U,F> create(BiConsumer<U,F> cs, Event.Type ... interests) {
    return new EventListener<U,F>() {
      public void accept(U u, F f) {
        cs.accept(u, f);
      }
      public Collection<Event.Type> getInterests() {
        return Arrays.asList(interests);
      }
    };
  }
  
}