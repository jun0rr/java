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

package us.pserver.jc.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import us.pserver.jc.WakeRule;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 28/11/2015
 */
public class ComposedRule implements WakeRule {
  
  private final List<WakeRule> rules;
  
  private int index;
  
  
  public ComposedRule() {
    this(0);
  }
  
  
  private ComposedRule(int index) {
    rules = new ArrayList<>();
    this.index = index;
  }
  
  
  public ComposedRule addRule(WakeRule wr) {
    if(wr != null) {
      rules.add(wr);
    }
    return this;
  }
  
  
  public boolean addRule(Optional<WakeRule> opt) {
    if(opt != null && opt.isPresent()) {
      rules.add(opt.get());
      return true;
    }
    return false;
  }
  
  
  public List<WakeRule> rules() {
    return rules;
  }
  
  
  @Override
  public long resolve() {
    if(rules.isEmpty() || index >= rules.size()) {
      return -1;
    }
    return rules.get(index).resolve();
  }
	
	
	@Override
	public Optional<WakeRule> next() {
    //System.out.println("* [ComposedRule.next] index="+ index);
    if(++index >= rules.size()) {
      ComposedRule cr = new ComposedRule();
      rules.forEach(r->cr.addRule(r.next()));
      return (cr.rules().isEmpty() 
          ? Optional.empty() 
          : Optional.of(cr)
      );
    }
    return Optional.of(this);
	}


  public Iterator<WakeRule> iterator() {
    return rules.iterator();
  }


  @Override
  public String toString() {
    return "ComposedRule{" + "rules=" + rules + ", index=" + index + '}';
  }
  
}