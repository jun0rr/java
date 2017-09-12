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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/09/2017
 */
public class MapMapper extends AbstractMapper<Map> {

  private final ObjectMapper mapper;
  
  public MapMapper(ObjectMapper omp) {
    super(Map.class);
    this.mapper = NotNull.of(omp).getOrFail("Bad null ObjectMapper");
  }


  @Override
  public MappedValue map(Map obj) {
    NotNull.of(obj).failIfNull("Bad null object");
    Map<String,MappedValue> nmp = new HashMap();
    obj.keySet().forEach(o->nmp.put(
        Objects.toString(o), 
        mapper.map(obj.get(o)))
    );
    return new MappedMap(nmp);
  }


  @Override
  public Map unmap(Class cls, MappedValue value) {
    NotNull.of(cls).failIfNull("Bad null Class");
    NotNull.of(value).failIfNull("Bad null value");
    Map<String,MappedValue> map = value.asMap();
    Map nmp = new HashMap();
    map.keySet().forEach(k->nmp.put(k, 
        mapper.unmap(map.get(k).getClass(), map.get(k)))
    );
    return nmp;
  }
  
}
