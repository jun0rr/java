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

import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 08/09/2017
 */
public interface MappingResult {

  public String getUID();
  
  public ObjectStore getMapper();
  
  
  public static MappingResult of(String uid, ObjectStore dmp) {
    return new MappingResultImpl(uid, dmp);
  }
  
  
  
  
  
  public static class MappingResultImpl implements MappingResult {
    
    private final String uid;
    
    private final ObjectStore mapper;
    
    
    public MappingResultImpl(String uid, ObjectStore dmp) {
      this.uid = NotNull.of(uid).getOrFail("Bad null string uid");
      this.mapper = NotNull.of(dmp).getOrFail("Bad null DistributedMapper");
    }
    
    
    @Override
    public String getUID() {
      return this.uid;
    }
    
    
    @Override
    public ObjectStore getMapper() {
      return mapper;
    }
    
  }
  
}
