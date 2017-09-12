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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import us.pserver.tools.NotNull;
import us.pserver.tools.rfl.Reflector;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/09/2017
 */
public class ListMapper extends AbstractMapper<List> {

  private final ObjectMapper mapper;
  
  public ListMapper(ObjectMapper omp) {
    super(List.class);
    this.mapper = NotNull.of(omp).getOrFail("Bad null ObjectMapper");
  }


  @Override
  public MappedArray map(List obj) {
    NotNull.of(obj).failIfNull("Bad null object");
    MappedValue[] vals = new MappedValue[obj.size()];
    for(int i = 0; i < obj.size(); i++) {
      vals[i] = MappedValue.of(mapper.map(obj.get(i)));
    }
    return new MappedArray(vals);
  }
  
  
  private List newList(Class cls) {
    try {
      return (List) Reflector.of(cls).create();
    } catch(Exception e) {
      return new ArrayList();
    }
  }


  @Override
  public List unmap(Class cls, MappedValue value) {
    NotNull.of(cls).failIfNull("Bad null Class");
    NotNull.of(value).failIfNull("Bad null value");
    return Arrays.asList(value.asArray())
        .stream()
        .map(o->mapper.unmap(cls, o.get()))
        .collect(Collectors.toList());
  }
  
}
