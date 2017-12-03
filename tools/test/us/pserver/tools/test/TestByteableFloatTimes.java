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

package us.pserver.tools.test;

import java.nio.ByteBuffer;
import us.pserver.tools.io.ByteableFloat;
import us.pserver.tools.io.ByteableNumber;
import us.pserver.tools.timer.Timer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/11/2017
 */
public class TestByteableFloatTimes {
  
  public static final int TOTAL_INTS = 10_000_000;
  
  
  public static void encodeFloats(ByteableNumber[] bn) {
    for(int i = 0; i < bn.length; i++) {
      bn[i] = new ByteableFloat((float)i);
    }
  }
  
  
  public static void encodeFloatsBuffer(ByteBuffer[] bs) {
    for(int i = 0; i < bs.length; i++) {
      bs[i] = ByteBuffer.wrap(new byte[Integer.BYTES]);
      bs[i].putFloat((float)i);
    }
  }

  
  public static void main(String[] args) {
    System.out.printf("* encoding %d floats [ByteableNumber]...%n", TOTAL_INTS);
    ByteableNumber[] bn = new ByteableNumber[TOTAL_INTS];
    Timer tm = new Timer.Nanos().start();
    encodeFloats(bn);
    System.out.printf("-- time to encode floats: %s --%n", tm.stop());
    
    System.out.printf("* encoding %d floats [ByteBuffer]...%n", TOTAL_INTS);
    ByteBuffer[] bs = new ByteBuffer[TOTAL_INTS];
    tm.clear().start();
    encodeFloatsBuffer(bs);
    System.out.printf("-- time to encode floats: %s --%n", tm.stop());
  }
  
}
