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
public class ArrayMapper extends AbstractMapper<List> {

  private final ObjectMapper mapper;
  
  public ArrayMapper(ObjectMapper omp) {
    super(null);
    this.mapper = NotNull.of(omp).getOrFail("Bad null ObjectMapper");
  }
  
  public boolean canMap(Class cls) {
    return cls != null && (cls.isArray() || Collection.class.isAssignableFrom(cls));
  }

  @Override
  public Function<Object,List> mapping() {
    return this::obj2list;
  }
  
  private List obj2list(Object o) {
    return o.getClass().isArray() 
        ? array2list(o) 
        : coll2list(o);
  }
  
  private List array2list(Object o) {
    int len = Array.getLength(o);
    List ls = new ArrayList();
    ls.add(o.getClass());
    for(int i = 0; i < len; i++) {
      ls.add(mapper.mapping().apply(Array.get(o, i)));
    }
    return ls;
  }
  
  private List coll2list(Object o) {
    Collection col = (Collection)o;
    return col.stream().map(mapper::mapping).collect(Collectors.toList());
  }

  @Override
  public Function<List,Object> unmapping() {
    
  }

}
