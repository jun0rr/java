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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 24/07/2018
 */
public class ElifRule extends CompositeRule {
  
  private final List<Rule> elses;

  public ElifRule() {
    super();
    this.elses = new ArrayList<>();
  }
  
  public ElifRule(Rule ... rules) {
    this(Arrays.asList(rules), Collections.EMPTY_LIST);
  }
  
  public ElifRule(Collection<Rule> rules, Collection<Rule> elses) {
    this();
    this.rules.addAll(rules);
    this.elses.addAll(elses);
  }
  
  public ElifRule(Rule ifrule, Rule elserule) {
    this();
    if(ifrule != null) this.rules.add(ifrule);
    if(elserule != null) this.elses.add(elserule);
  }
  
  public List<Rule> getElseRules() {
    return elses;
  }
  
  public ElifRule addElseRule(Object r) {
    if(r != null) {
      Rule rule = Rule.class.isAssignableFrom(r.getClass()) 
          ? (Rule)r : RuleProxy.asRule(r);
      elses.add(rule);
    }
    return this;
  }
  
  public ElifRule addElseRules(Object ... rs) {
    if(rs != null) {
      for(Object r : rs) addElseRule(r);
    }
    return this;
  }
  
  public boolean containsElseRule(String name) {
    return elses.stream()
        .anyMatch(r->r.getName().equals(name));
  }
  
  public Rule getElseRule(String name) {
    Optional<Rule> opt = elses.stream()
        .filter(r->r.getName().equals(name))
        .findAny();
    return opt.isPresent() ? opt.get() : null;
  }
  
  @Override
  public boolean evaluate(Facts facts) {
    Collections.sort(this.rules, (r,s)->Integer.compare(r.getPriority(), s.getPriority()));
    Collections.sort(this.elses, (r,s)->Integer.compare(r.getPriority(), s.getPriority()));
    Predicate<Rule> match = r->r.evaluate(facts);
    return rules.stream().allMatch(match) || elses.stream().anyMatch(match);
  }
  
  @Override
  public void execute(Facts facts) throws Exception {
    AndRule and = new AndRule(rules);
    if(and.evaluate(facts)) {
      and.execute(facts);
    }
    else {
      for(Rule r : elses) {
        if(r.evaluate(facts)) r.execute(facts);
        else r.otherwise(facts);
      }
    }
  }
  
}
