/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca � software livre; voc� pode redistribu�-la e/ou modific�-la sob os
 * termos da Licen�a P�blica Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a vers�o 2.1 da Licen�a, ou qualquer
 * vers�o posterior.
 * 
 * Esta biblioteca � distribu�da na expectativa de que seja �til, por�m, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia impl�cita de COMERCIABILIDADE
 * OU ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a Licen�a P�blica
 * Geral Menor do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor do GNU junto
 * com esta biblioteca; se n�o, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endere�o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 */

package us.pserver.tools;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 21/11/2017
 */
public class Iterate<T> {

  private final Iterator<T> iter;
  
  
  public Iterate(Iterator<T> it) {
    this.iter = NotNull.of(it).getOrFail("Bad null Iterator");
  }
  
  
  public void go(Consumer<T> cs) {
    while(iter.hasNext()) {
      cs.accept(iter.next());
    }
  }


  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.iter);
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
    final Iterate<?> other = (Iterate<?>) obj;
    return Objects.equals(this.iter, other.iter);
  }
  
  
  public static <U> Iterate<U> on(Iterator<U> it) {
    return new Iterate(it);
  }
  
}
