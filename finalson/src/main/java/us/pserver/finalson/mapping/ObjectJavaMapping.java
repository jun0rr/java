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

package us.pserver.finalson.mapping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import us.pserver.finalson.FinalsonConfig;
import us.pserver.finalson.construct.ConstructHandleInference;
import us.pserver.finalson.construct.DefaultConstructInference;
import static us.pserver.finalson.tools.JsonObjectProperties.PROP_CLASS;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 15/12/2017
 */
public class ObjectJavaMapping implements JavaMapping {
  
  private final FinalsonConfig config;
  
  public ObjectJavaMapping(FinalsonConfig config) {
    this.config = config;
  }

  @Override
  public Object fromJson(JsonElement elt) {
    if(!elt.isJsonObject() || !elt.getAsJsonObject().has(PROP_CLASS)) {
      throw new IllegalArgumentException("Not a JsonObject");
    }
    JsonObject job = elt.getAsJsonObject();
    TypeMapping<Class> tmap = config.getTypeMappingFor(Class.class).get();
    Class type = tmap.fromJson(job.get(PROP_CLASS));
    ConstructHandleInference cif = new DefaultConstructInference(config, type, job);
    return cif.infer().create();
  }
  
  
  @Override
  public boolean accept(Class type) {
    return Object.class.isAssignableFrom(type);
  }

}