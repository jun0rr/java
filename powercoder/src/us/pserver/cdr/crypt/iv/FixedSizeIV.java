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

package us.pserver.cdr.crypt.iv;

import us.pserver.cdr.crypt.CryptUtils;
import us.pserver.insane.Checkup;
import us.pserver.insane.Sane;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 17/10/2015
 */
public abstract class FixedSizeIV extends AbstractIV {
  
  
  public FixedSizeIV() {}
  
  
  public FixedSizeIV(int size) {
    this.init(size);
  }
  
  
  public FixedSizeIV(int size, byte[] vector) {
    this.init(size, vector);
  }
  
  
  public void init(int size, byte[] vector) {
    Sane.of(size).with("Invalid Size: "+ size)
        .check(Checkup.isGreaterThan(0));
    Sane.of(vector).check(Checkup.isNotEmptyArray());
    super.init(CryptUtils.truncate(vector, size));
  }
  
  
  public void init(int size) {
    Sane.of(size).with("Bad Size (must be > 0)")
        .check(Checkup.isGreaterThan(0));
    super.init(new byte[size]);
  }
  
  
}
