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

package us.pserver.json;

import com.jpower.rfl.Reflector;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 25/09/2014
 */
public class ObjectJson {
  
  private Reflector rf;
  
  private ListJson jl;
  
  
  public ObjectJson() {
    rf = new Reflector();
    jl = new ListJson(this);
  }
  
  
  public boolean isPrimitive(Object o) {
    if(o == null) return true;
    return Number.class.isAssignableFrom(o.getClass())
        || String.class.isAssignableFrom(o.getClass())
        || Boolean.class.isAssignableFrom(o.getClass())
        || Character.class.isAssignableFrom(o.getClass())
        || Byte.class.isAssignableFrom(o.getClass());
  }
  
  
  public boolean isPrimitive(Class c) {
    if(c == null) return true;
    return Number.class.isAssignableFrom(c)
        || String.class.isAssignableFrom(c)
        || Boolean.class.isAssignableFrom(c)
        || Character.class.isAssignableFrom(c)
        || Byte.class.isAssignableFrom(c);
  }
  
  
  public String primitiveToJson(Object o) {
    if(o == null) return "null";
    if(!isPrimitive(o)) return toJson(o);
    if(String.class.isAssignableFrom(o.getClass())
        || char.class.isAssignableFrom(o.getClass())
        || Character.class.isAssignableFrom(o.getClass())) {
      return "'" + o.toString() + "'";
    }
    else {
      return String.valueOf(o);
    }
  }
  
  
  public boolean isList(Object o) {
    if(o == null) return false;
    return List.class.isAssignableFrom(o.getClass());
  }
  
  
  public boolean isArray(Object o) {
    if(o == null) return false;
    return o.getClass().isArray();
  }
  
  
  public boolean isField(Object o) {
    if(o == null) return false;
    return Field.class.isAssignableFrom(o.getClass());
  }
  
  
  public boolean isList(Class c) {
    if(c == null) return false;
    return List.class.isAssignableFrom(c);
  }
  
  
  public boolean isArray(Class c) {
    if(c == null) return false;
    return c.isArray();
  }
  
  
  public boolean isField(Class c) {
    if(c == null) return false;
    return Field.class.isAssignableFrom(c);
  }
  
  
  public String toJson(Field f, Object o) {
    if(f == null || o == null)
      return "null";
    Object val = rf.on(o).field(f.getName()).get();
    StringBuffer sb = new StringBuffer();
    sb.append("{'")
        .append(f.getName())
        .append("':")
        .append(toJson(val))
        .append("}");
    return sb.toString();
  }
  
  
  public String toJson(Field[] fs, Object o) {
    if(fs == null || o == null)
      return "null";
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    for(int i = 0; i < fs.length; i++) {
      sb.append(toJson(fs[i], o));
      if(i < fs.length -1)
        sb.append(",");
    }
    return sb.append("]").toString();
  }
  
  
  public String toJson(Object o) {
    if(o == null) return "null";
    if(isPrimitive(o))
      return primitiveToJson(o);
    if(isList(o) || isArray(o))
      return jl.toJson(o);
    
    Reflector rf = new Reflector();
    Field[] fs = rf.on(o).fields();
    StringBuilder sb = new StringBuilder();
    sb.append("{")
        .append("'class':'")
        .append(o.getClass().getName())
        .append("',");
    
    for(int i = 0; i < fs.length; i++) {
      Object val = rf.on(o).field(fs[i].getName()).get();
      if(val == null) continue;
      sb.append("'")
          .append(fs[i].getName())
          .append("':");
      sb.append(toJson(val));
      if(i < fs.length-1)
        sb.append(",");
    }
    return sb.append("}").toString();
  }
  
}
