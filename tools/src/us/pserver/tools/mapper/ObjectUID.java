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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Objects;
import us.pserver.tools.Hash;
import us.pserver.tools.NotNull;
import us.pserver.tools.rfl.Reflector;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 08/09/2017
 */
public class ObjectUID implements Serializable {

  private final String uid;
  
  private String className;
  
  private final transient Object obj;
  
  private final transient Hash hash;
  
  
  public ObjectUID(Hash hash, Object obj) {
    this.hash = NotNull.of(hash).getOrFail("Bad null Hash");
    this.obj = NotNull.of(obj).getOrFail("Bad null object");
    this.className = obj.getClass().getName();
    this.calcUID(obj);
    this.uid = hash.get();
  }
  
  public ObjectUID(Object obj) {
    this(Hash.sha1(), obj);
  }
  
  public String getUID() {
    return this.uid;
  }
  
  public Object getObject() {
    return this.obj;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  private void calcUID(Object obj) {
    if(MappingUtils.isNativeSupported(obj.getClass())) {
      hash.put(obj.getClass().getName());
      hash.put(Objects.toString(obj));
    }
    else {
      hash.put(obj.getClass().getName());
      Field[] fs = Reflector.of(obj).fields();
      for(Field f : fs) {
        Object of = Reflector.of(obj).selectField(f.getName()).get();
        if(of != null) {
          calcUID(of);
        }
      }
    }
  }
  
  
  public static ObjectUID of(Object obj) {
    return new ObjectUID(obj);
  }
  
  public static ObjectUID of(Hash hash, Object obj) {
    return new ObjectUID(hash, obj);
  }
  
}
