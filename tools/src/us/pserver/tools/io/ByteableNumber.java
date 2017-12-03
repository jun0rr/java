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

package us.pserver.tools.io;

import java.nio.ByteBuffer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/11/2017
 */
public interface ByteableNumber {

  public byte[] getBytes();
  
  public ByteBuffer toByteBuffer();
  
  public Number getNumber();
  
  
  
  public static ByteableNumber of(short n) {
    return new ByteableShort(n);
  }
  
  
  public static ByteableNumber of(int n) {
    return new ByteableInteger(n);
  }
  
  
  public static ByteableNumber of(long n) {
    return new ByteableLong(n);
  }
  
  
  public static ByteableNumber of(float n) {
    return new ByteableFloat(n);
  }
  
  
  public static ByteableNumber of(double n) {
    return new ByteableDouble(n);
  }
  
}
