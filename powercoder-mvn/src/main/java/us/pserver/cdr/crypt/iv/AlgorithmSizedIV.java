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

import java.util.Objects;
import us.pserver.cdr.crypt.CryptAlgorithm;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 17/10/2015
 */
public class AlgorithmSizedIV extends FixedSizeIV {
  
  
  public AlgorithmSizedIV() {}
  
  
  public AlgorithmSizedIV(int size) {
    super(size);
  }
  
  
  public AlgorithmSizedIV(int size, byte[] vector) {
    super(size, vector);
  }
  
  
  public AlgorithmSizedIV(CryptAlgorithm algo) {
    this.init(algo);
  }
  
  
  public AlgorithmSizedIV(CryptAlgorithm algo, byte[] vector) {
    this.init(algo, vector);
  }
  
  
  public void init(CryptAlgorithm algo) {
    super.init(Objects.requireNonNull(algo)
        .getStringAlgorithm().contains("AES") 
        ? 16 : 8
    );
  }
  
  
  public void init(CryptAlgorithm algo, byte[] vector) {
    super.init(Objects.requireNonNull(algo)
        .getStringAlgorithm().contains("AES") 
        ? 16 : 8, vector
    );
  }
  
  
}