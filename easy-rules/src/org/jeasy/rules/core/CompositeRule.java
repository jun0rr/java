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
import org.jeasy.rules.api.Rule;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 24/07/2018
 */
public class CompositeRule extends BasicRule {

  protected final List<Rule> rules;
  
  public CompositeRule() {
    this.rules = new ArrayList<>();
  }
  
  public CompositeRule(Rule ... rules) {
    this(Arrays.asList(rules));
  }
  
  public CompositeRule(Collection<Rule> rules) {
    this();
    this.rules.addAll(rules);
  }
  
  public List<Rule> getRules() {
    return rules;
  }
  
  public CompositeRule addRule(Object r) {
    if(r != null) {
      Rule rule = Rule.class.isAssignableFrom(r.getClass()) 
          ? (Rule)r : RuleProxy.asRule(r);
      rules.add(rule);
    }
    return this;
  }
  
  public CompositeRule addRules(Object ... rs) {
    if(rs != null) {
      for(Object r : rs) addRule(r);
    }
    return this;
  }
  
  public CompositeRule addRules(Collection<Object> ls) {
    if(ls != null) {
      for(Object r : ls) addRule(r);
    }
    return this;
  }
  
  public boolean containsRule(String name) {
    return rules.stream()
        .anyMatch(r->r.getName().equals(name));
  }
  
  public Rule getRule(String name) {
    Optional<Rule> opt = rules.stream()
        .filter(r->r.getName().equals(name))
        .findAny();
    return opt.isPresent() ? opt.get() : null;
  }
  
  @Override
  public int getPriority() {
    Collections.sort(this.rules, (r,s)->Integer.compare(r.getPriority(), s.getPriority()));
    return rules.get(0).getPriority();
  }
  
  @Override
  public String getName() {
    String name = this.getClass().getSimpleName();
    if(!rules.isEmpty())
      name += "." + rules.get(0).getName();
    return name;
  }
  
}
