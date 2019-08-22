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

package us.pserver.bitbox.impl;

import us.pserver.bitbox.BitBox;
import us.pserver.bitbox.BitBoxConfiguration;
import us.pserver.tools.io.BitBuffer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 3 de mai de 2019
 */
public class BitBoxImpl implements BitBox {
  
  private final BitBoxConfiguration cfg;
  
  public BitBoxImpl(BitBoxConfiguration cfg) {
    this.cfg = new BitBoxConfiguration();
  }
  
  public BitBoxImpl() {
    this(new BitBoxConfiguration());
  }
  
  @Override
  public void box(Object obj, BitBuffer buf) {
    cfg.getGlobalTransform().box(obj, buf);
  }
  
  @Override
  public <T> T unbox(BitBuffer buf) {
    return (T) cfg.getGlobalTransform().unbox(buf);
  }
  
  @Override
  public BitBoxConfiguration configure() {
    return cfg;
  }
  
}