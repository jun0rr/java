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

package us.pserver.insane.checkup;

import us.pserver.insane.SanityCheck;
import us.pserver.insane.Insane;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 20/05/2016
 */
public class NumberLesserEquals implements SanityCheck<Number> {
  
  private final Number parameter;
  
  
  public NumberLesserEquals(Number parameter) {
    this.parameter = Insane.of(parameter).check(new NotNull());
  }
  

  @Override
  public boolean test(Number t) {
    return Double.compare(Insane.of(t).check(new NotNull()).doubleValue(), 
        parameter.doubleValue()
    ) <= 0;
  }
  
  
  @Override
  public String failMessage() {
    return String.format("Number must be lesser than or equals to %1$s. (X <= %1$s)", parameter);
  }

}
