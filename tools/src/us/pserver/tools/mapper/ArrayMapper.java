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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 02/09/2017
 */
public class ArrayMapper extends AbstractMapper {

  private final ObjectMapper mapper;
  
  public ArrayMapper(ObjectMapper omp) {
    super(Object.class);
    this.mapper = NotNull.of(omp).getOrFail("Bad null ObjectMapper");
  }
  
  public boolean canMap(Class cls) {
    return cls != null && (cls.isArray());
  }

  @Override
  public Function mapping() {
    return this::array2list;
  }
  
  private Object obj2array(Object o) {
    List ls = (List) o;
    Object[] array = new Object[ls.size()];
    for(int i = 0; i < ls.size(); i++) {
      array[i] = mapper.unmapping().apply(ls.get(i));
    }
    return array;
  }
  
  private List array2list(Object o) {
    int len = Array.getLength(o);
    List ls = new ArrayList();
    for(int i = 0; i < len; i++) {
      ls.add(mapper.mapping().apply(Array.get(o, i)));
    }
    return ls;
  }
  
  @Override
  public Function unmapping() {
    return this::obj2array;
  }

}
