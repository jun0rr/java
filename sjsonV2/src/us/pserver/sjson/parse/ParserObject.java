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

package us.pserver.sjson.parse;

import us.pserver.sjson.JsonObject;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 23/12/2014
 */
public class ParserObject {

  
  public JsonObject parse(String str) {
    if(str == null 
        || !str.startsWith("{") 
        || !str.endsWith("}"))
      throw new IllegalArgumentException(
          "String is not a representation of json object: "+ str);
    
    char[] cs = str.toCharArray();
    for(int i = 1; i < cs.length -1; i++) {
      if(cs[i] == '"')
    }
  }
  
  
}
