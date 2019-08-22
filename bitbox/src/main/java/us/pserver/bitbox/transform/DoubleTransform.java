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

package us.pserver.bitbox.transform;

import java.util.Optional;
import us.pserver.bitbox.BitTransform;
import us.pserver.tools.io.BitBuffer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 11 de abr de 2019
 */
public class DoubleTransform implements BitTransform<Double> {
  
  public static final byte BYTE_ID = 9;
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return c == double.class || c == Double.class;
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  public int doubleBox(double d, BitBuffer buf) {
    buf.putDouble(d);
    return Double.BYTES;
  }
  
  /**
   * [byte][double]
   * @param d
   * @param buf
   * @return 
   */
  @Override
  public int box(Double d, BitBuffer buf) {
    buf.put(BYTE_ID).putDouble(d);
    return 1 + Double.BYTES;
  }
  
  @Override
  public Double unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a double buffer (%d)", id, BYTE_ID));
    return buf.getDouble();
  }
  
  public double doubleUnbox(BitBuffer buf) {
    return buf.getDouble();
  }
  
}
