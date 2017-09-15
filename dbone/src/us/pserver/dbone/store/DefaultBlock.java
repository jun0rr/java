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

package us.pserver.dbone.store;

import java.nio.ByteBuffer;
import java.util.Optional;
import us.pserver.tools.NotNull;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 15/09/2017
 */
public class DefaultBlock implements Block {
  
  protected final ByteBuffer buffer;
  
  protected final Region region;
  
  
  public DefaultBlock(Region r, ByteBuffer b) {
    this.region = NotNull.of(r).getOrFail("Bad null Region");
    this.buffer = NotNull.of(b).getOrFail("Bad null ByteBuffer");
  }

  @Override
  public Region getRegion() {
    return this.region;
  }


  @Override
  public ByteBuffer getBuffer() {
    return this.buffer;
  }


  @Override
  public Optional<Region> next() {
    int pos = buffer.position();
    buffer.position(buffer.limit() - 16);
    long off = buffer.getLong();
    long len = buffer.getLong();
    buffer.position(pos);
    return Optional.ofNullable(off >= 0 && len >= 1 
        ? Region.of(off, len) : null);
  }
  
  
  @Override
  public DefaultBlock setNext(Region r) {
    NotNull.of(r).failIfNull("Bad null Region");
    int pos = buffer.position();
    buffer.position(buffer.limit() - 16);
    buffer.putLong(r.offset());
    buffer.putLong(r.length());
    buffer.position(pos);
    return this;
  }

}