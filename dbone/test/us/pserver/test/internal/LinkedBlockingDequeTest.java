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

package us.pserver.test.internal;

import java.util.concurrent.LinkedBlockingDeque;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 17/11/2017
 */
public class LinkedBlockingDequeTest {

  private final LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
  
  
  @Test
  public void addAndTakeFirstTest() throws InterruptedException {
    int cur = 1024;
    int inc = 1024;
    for(int i = 0; i < 10; i++) {
      deque.add(cur);
      cur += inc;
    }
    Assert.assertEquals(Integer.valueOf(1024), deque.takeFirst());
    Assert.assertEquals(Integer.valueOf(2048), deque.takeFirst());
    Assert.assertEquals(Integer.valueOf(cur-inc), deque.takeLast());
  }
  
}