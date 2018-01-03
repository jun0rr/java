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

package us.pserver.coreone.test;

import us.pserver.coreone.impl.CountDown;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 26/10/2017
 */
public class TestCountDown {

  public static void main(String[] args) {
    CountDown cd = new CountDown(500);
    new Thread(()->{
      long l = 0;
      while((l = cd.decrement()) > 0) {
        //System.out.printf("- decrement: %d%n", l);
        try { Thread.sleep(10); }
        catch(InterruptedException e) {}
      }
    }).start();
    System.out.println("* wait count down...");
    cd.waitCountDown();
    System.out.println("* done!");
  }
  
}