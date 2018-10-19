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

package us.pserver.jpx.event;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 11/08/2018
 */
public abstract class AbstractEvent implements Event {
  
  private final Type type;
  
  private final Instant inst;
  
  private final Map<Attribute,Object> attrs;
  
  
  public AbstractEvent(Type type, Instant inst, Map<Attribute,Object> attrs) {
    this.type = Objects.requireNonNull(type);
    this.inst = Objects.requireNonNull(inst);
    if(attrs != null && !attrs.isEmpty()) {
      this.attrs = Collections.unmodifiableMap(attrs);
    }
    else {
      this.attrs = Collections.EMPTY_MAP;
    }
  }
  
  
  public AbstractEvent(Type type, Map<Attribute,Object> attrs) {
    this(type, Instant.now(), attrs);
  }
  
  
  @Override
  public Type getType() {
    return type;
  }

  @Override
  public Map<Attribute,Object> attributes() {
    return attrs;
  }

  @Override
  public Instant getInstant() {
    return inst;
  }


  @Override
  public int hashCode() {
    int hash = 3;
    hash = 19 * hash + Objects.hashCode(this.type);
    hash = 19 * hash + Objects.hashCode(this.inst.toEpochMilli() / 10);
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
    final AbstractEvent other = (AbstractEvent) obj;
    if (this.type != other.type) {
      return false;
    }
    if (!Objects.equals(this.inst.toEpochMilli() / 10, other.inst.toEpochMilli() / 10)) {
      return false;
    }
    return true;
  }


  @Override
  public String toString() {
    return "Event{" + "type=" + type + ", inst=" + inst + ", attrs=" + attrs + '}';
  }
  
}