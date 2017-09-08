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
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import us.pserver.tools.Hash;
import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 08/09/2017
 */
public interface MapHash {
  
  public static final String DEFAULT_HASH = "sha1";
  

  public String calculate(Map map);
  
  public byte[] calculateBytes(Map map);
  
  
  public static MapHash shallowMapHash() {
    return new ShallowMapHash(Hash.sha1());
  }
  
  public static MapHash shallowMapHash(Hash hash) {
    return new ShallowMapHash(hash);
  }
  
  public static String shallowMapHash(Map map) {
    return new ShallowMapHash(Hash.sha1()).calculate(map);
  }
  
  
  public static MapHash deepMapHash() {
    return new DeepMapHash(Hash.sha1());
  }
  
  public static MapHash deepMapHash(Hash hash) {
    return new DeepMapHash(hash);
  }
  
  public static String deepMapHash(Map map) {
    return new DeepMapHash(Hash.sha1()).calculate(map);
  }
  
  
  
  
  
  public static class ShallowMapHash implements MapHash {
    
    private final Hash hash;
    
    
    public ShallowMapHash(Hash hash) {
      this.hash = NotNull.of(hash).getOrFail("Bad null Hash");
    }
    
    
    @Override
    public String calculate(Map map) {
      Set<Entry> set = NotNull.of(map).getOrFail("Bad null Map").entrySet();
      set.forEach(e->hash
          .put(Objects.toString(e.getKey()))
          .put(Objects.toString(e.getValue()))
      );
      return hash.get();
    }
    
    
    @Override
    public byte[] calculateBytes(Map map) {
      Set<Entry> set = NotNull.of(map).getOrFail("Bad null Map").entrySet();
      set.forEach(e->hash
          .put(Objects.toString(e.getKey()))
          .put(Objects.toString(e.getValue()))
      );
      return hash.getBytes();
    }
    
  }
  
  
  
  
  
  public static class DeepMapHash implements MapHash {
    
    private final Hash hash;
    
    
    public DeepMapHash(Hash hash) {
      this.hash = NotNull.of(hash).getOrFail("Bad null Hash");
    }
    
    
    private void calc(Map map) {
      Set<Entry> set = NotNull.of(map).getOrFail("Bad null Map").entrySet();
      for(Entry e : set) {
        hash.put(Objects.toString(e.getKey()));
        if(Map.class.isAssignableFrom(e.getValue().getClass())) {
          Map val = (Map) e.getValue();
          calc(val);
        }
      }
    }
    
    
    @Override
    public String calculate(Map map) {
      this.calc(map);
      return hash.get();
    }
    
    
    @Override
    public byte[] calculateBytes(Map map) {
      this.calc(map);
      return hash.getBytes();
    }
    
  }
  
}
