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

package us.pserver.orb.ds;

import java.io.InputStream;
import java.util.Objects;
import us.pserver.orb.OrbException;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 25/02/2019
 */
public class ClassPathDataSource implements DataSource<InputStream> {
  
  private final ClassLoader cl;
  
  private final String name;
  
  public ClassPathDataSource(String name, ClassLoader cl) {
    this.name = Objects.requireNonNull(name);
    this.cl = Objects.requireNonNull(cl);
  }

  @Override
  public InputStream get() throws OrbException {
    return OrbException.compute(() -> cl.getResourceAsStream(name));
  }
  
  
  
  public static DataSource<InputStream> of(String resource, ClassLoader ldr) {
    return new ClassPathDataSource(resource, ldr);
  }

}