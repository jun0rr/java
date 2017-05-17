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

package us.pserver.jose.json;

import com.jsoniter.JsonIterator;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 17/03/2017
 */
@FunctionalInterface
public interface JsonObjFunction extends BiFunction<String,Class,Object> {

  @Override public Object apply(String s, Class c);
  
  public default Object fromJsonBytes(ByteBuffer buf, Class c) {
    return apply(StandardCharsets.UTF_8.decode(buf).toString(), c);
  }
  
  
  
  public static JsonObjFunction get() {
    return new JsonFromImpl();
  }
  
  
  
  
  
  static class JsonFromImpl implements JsonObjFunction {

    @Override
    public Object apply(String s, Class c) {
      if(s == null) return null;
      return JsonIterator.deserialize(s, c);
    }
    
  }
  
}