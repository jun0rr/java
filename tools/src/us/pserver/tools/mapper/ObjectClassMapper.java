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

import java.util.Map;
import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/09/2017
 */
public class ObjectClassMapper extends ObjectMapper {
  
  public static final String KEY_TYPE = "@type";

  public ObjectClassMapper() {
    super();
  }
  
  @Override
  public Object map(Object obj) {
    NotNull.of(obj).failIfNull("Bad null object");
    Object mop = super.map(obj);
    if(Map.class.isAssignableFrom(mop.getClass())) {
      ((Map)mop).put(KEY_TYPE, obj.getClass().getName());
    }
    return mop;
  }
  
  public <T> T unmap(Object obj) {
    NotNull.of(obj).failIfNull("Bad null object");
    if(Map.class.isAssignableFrom(obj.getClass())) {
      Class cls = (Class) super.unmap(Class.class, ((Map)obj).get(KEY_TYPE));
      return (T) super.unmap(cls, obj);
    }
    else {
      return (T) super.unmap(obj.getClass(), obj);
    }
  }
  
}
