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

package us.pserver.micron.security;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import us.pserver.tools.Match;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 22/01/2019
 */
public abstract class AbstractGroup {
  
  protected final String name;
  
  protected final Set<String> items;
  
  protected final Instant created;
  
  
  protected AbstractGroup(String name, Set<String> items, Instant created) {
    this.name = Match.notEmpty(name).getOrFail("Bad null/empty name");
    this.items = Collections.unmodifiableSet(
        Match.notNull(items).getOrFail("Bad null/empty items Set")
    );
    this.created = Match.notNull(created).getOrFail("Bad null created Instant");
  }
  
  protected AbstractGroup(String name, Set<String> members) {
    this(name, members, Instant.now());
  }
  
  
  public String getName() {
    return name;
  }
  
  public Instant getCreated() {
    return created;
  }
  
  protected Set<String> getItems() {
    return items;
  }
  
  public boolean contains(String item) {
    return items.contains(item);
  }
  
  public abstract <B extends AbstractGroupBuilder> B edit();


  @Override
  public int hashCode() {
    int hash = 3;
    hash = 47 * hash + Objects.hashCode(this.name);
    hash = 47 * hash + Objects.hashCode(this.items);
    return hash;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final AbstractGroup other = (AbstractGroup) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.items, other.items)) {
      return false;
    }
    return true;
  }


  @Override
  public String toString() {
    return name + "{ items=" + items + ", created=" + created + " }";
  }
  
  
  
  
  
  public static abstract class AbstractGroupBuilder<T extends AbstractGroup> {

    protected String name;
    
    protected Set<String> items;
    
    protected Instant created;
    
    
    public AbstractGroupBuilder() {
      this.name = null;
      this.created = Instant.now();
      this.items = new HashSet<>();
    }
    
    
    public String getName() {
      return name;
    }
    
    public AbstractGroupBuilder setName(String name) {
      this.name = name;
      return this;
    }
    
    
    public Instant getCreated() {
      return created;
    }
    
    public AbstractGroupBuilder setCreated(Instant created) {
      this.created = created;
      return this;
    }
    
    
    protected Set<String> getItems() {
      return items;
    }
    
    protected AbstractGroupBuilder setItems(Collection<String> items) {
      this.items.addAll(Match.notNull(items).getOrFail("Bad null items Set"));
      return this;
    }
    
    public AbstractGroupBuilder add(String item) {
      this.items.add(Match.notEmpty(item).getOrFail("Bad null/empty item"));
      return this;
    }
    
    public abstract T build();
    
  }

}
