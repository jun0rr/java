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

package us.pserver.ironbit.record;

import java.util.Optional;
import us.pserver.ironbit.ClassID;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 28/09/2017
 */
public interface SerialRecord<T> {
  
  public static final int HEADER_SIZE = Integer.BYTES * 2 + Short.BYTES;
  
  /* Serialized Objects format
   * classID : int | length : int | nameSize : short | name : String | [value : bytes]
   */
  
  public ClassID getClassID();
  
  public byte[] toByteArray();
  
  public int length();
  
  public T getValue();
  
  public Optional<String> getName();
  
  
  public static <U> SerialRecord<U> of(U obj) {
    
    return null;
  }
  
  
  public static <U> SerialRecord<U> of(byte[] bs) {
    return null;
  }
  
}
