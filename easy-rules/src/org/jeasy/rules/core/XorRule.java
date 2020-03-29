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

package org.jeasy.rules.core;

import java.util.Collection;
import java.util.Collections;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

/**
 * Regra de agrupamento que avalia todas as regras do grupo e,
 * se alguma for verdadeira, somente essa regra é disparada, 
 * não avaliando ou disparando as demais. 
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 24/07/2018
 */
public class XorRule extends CompositeRule {

  public XorRule() {
    super();
  }
  
  public XorRule(Object ... rules) {
    this();
    this.addRules(rules);
  }
  
  public XorRule(Collection<Object> rules) {
    this();
    this.addRules(rules);
  }
  
  @Override
  public boolean evaluate(Facts facts) {
    Collections.sort(this.rules, (r,s)->Integer.compare(r.getPriority(), s.getPriority()));
    return rules.stream()
        .anyMatch(r->r.evaluate(facts));
  }
  
  @Override
  public void execute(Facts facts) throws Exception {
    for(Rule r : rules) {
      if(r.evaluate(facts)) {
        r.execute(facts);
        break;
      }
      else {
        r.otherwise(facts);
      }
    }
  }
  
}
