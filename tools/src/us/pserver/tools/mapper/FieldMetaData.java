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

import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 17/08/2017
 */
public interface FieldMetaData {

  public boolean isNumber();
  
  public boolean isDecimal();
  
  public boolean isString();
  
  public boolean isBoolean();
  
  public boolean isArray();
  
  public boolean isPrimitive();
  
  public Class getType();
  
  
  public static FieldMetaData of(Class cls) {
    return new MetaDataImpl(cls);
  }
  
  
  
  
  public class MetaDataImpl implements FieldMetaData {

    protected final Class type;

    protected MetaDataImpl(Class type) {
      this.type = NotNull.of(type).getOrFail("Bad null Class");
    }

    @Override
    public boolean isNumber() {
      return Number.class.isAssignableFrom(type)
          || byte.class.isAssignableFrom(type)
          || short.class.isAssignableFrom(type)
          || int.class.isAssignableFrom(type)
          || long.class.isAssignableFrom(type)
          || float.class.isAssignableFrom(type)
          || double.class.isAssignableFrom(type);
    }

    @Override
    public boolean isDecimal() {
      return float.class.isAssignableFrom(type)
          || Float.class.isAssignableFrom(type)
          || double.class.isAssignableFrom(type)
          || Double.class.isAssignableFrom(type);
    }

    @Override
    public boolean isPrimitive() {
      return type.isPrimitive();
    }

    @Override
    public boolean isString() {
      return String.class.isAssignableFrom(type);
    }

    @Override
    public boolean isBoolean() {
      return Boolean.class.isAssignableFrom(type) 
          || boolean.class.isAssignableFrom(type);
    }

    @Override
    public boolean isArray() {
      return type.isArray();
    }

    @Override
    public Class getType() {
      return type;
    }

  }
  
}
