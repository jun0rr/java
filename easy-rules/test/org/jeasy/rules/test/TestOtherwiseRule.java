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

package org.jeasy.rules.test;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Otherwise;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 24/07/2018
 */
public class TestOtherwiseRule {
  
  @org.jeasy.rules.annotation.Rule(name = "else-rule", description = "Execute otherwise on else", priority = 0)
  public static class ElseRule {
    @Condition
    public boolean _if(Facts facts) {
      int magic = facts.get("magic");
      return magic > 50;
    }
    @Action
    public void then(Facts facts) {
      System.out.printf("%s is greater (>) then 50%n", facts.get("magic").toString());
    }
    @Otherwise
    public void _else(Facts facts) {
      System.out.printf("%s is lesser (<) then 50%n", facts.get("magic").toString());
    }
  }

  
  public static void main(String[] args) throws Exception {
    Facts facts = new Facts();
    facts.put("magic", 43);
    Rule r1 = new RuleBuilder()
        .name("r1").priority(3)
        .when(f->((Integer)f.get("magic")) > 50)
        .then(f->System.out.println("magic is greater then 50"))
        .otherwise(f->System.out.println("magic is less then 50"))
        .build();
    Rules rs = new Rules(new ElseRule());
    DefaultRulesEngine en = new DefaultRulesEngine();
    en.fire(rs, facts);
  }
  
}
