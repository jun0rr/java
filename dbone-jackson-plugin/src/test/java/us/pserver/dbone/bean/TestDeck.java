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

package us.pserver.dbone.bean;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/06/2018
 */
public class TestDeck {

  @Test
  public void loadAndWeight() {
    List<Container> cts = new LinkedList<>();
    cts.add(new Container("Fruits", "Banana", 55.2));
    cts.add(new Container("Fruits", "Orange", 48.6));
    cts.add(new Container("Veggies", "Potato", 60.0));
    double maxWeight = 55.2 + 48.6 + 60.0;
    Deck deck = new Deck(cts);
    System.out.println(deck);
    System.out.println(deck.weight());
    Assertions.assertEquals(maxWeight, deck.weight());
  }
  
}