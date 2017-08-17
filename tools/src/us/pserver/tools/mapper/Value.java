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

package us.pserver.tools.mapper;

import java.util.Objects;
import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 17/08/2017
 */
public interface Value<T> {

  public FieldMetaData getMetaData();
  
  public T get();
  
  
  public static <U> Value of(U value) {
    return new ValueImpl(value);
  }
  
  
  
  
  
  public class ValueImpl<T> implements Value {

    protected final T value;

    protected final FieldMetaData meta;

    protected ValueImpl(T value) {
      this.value = NotNull.of(value).getOrFail("Bad null value");
      this.meta = FieldMetaData.of(value.getClass());
    }

    @Override
    public T get() {
      return value;
    }

    @Override
    public FieldMetaData getMetaData() {
      return meta;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 29 * hash + Objects.hashCode(this.value);
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
      final AbstractValue<?> other = (AbstractValue<?>) obj;
      return Objects.equals(this.value, other.value);
    }

  }
  
}
