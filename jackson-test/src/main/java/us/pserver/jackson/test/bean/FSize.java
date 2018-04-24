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

package us.pserver.jackson.test.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/12/2016
 */
public class FSize implements IFSize {

  private final long bytes;
  
  @JsonCreator
  public FSize(long bytes) {
    this.bytes = bytes;
  }
  
  
  public FSize(IFSize size) {
    this.bytes = size.bytes();
  }
  
  
  @Override
  public long bytes() {
    return bytes;
  }


  @Override
  public double value() {
    Unit unit = Unit.from(bytes);
    return Long.valueOf(bytes).doubleValue() / unit.bytes();
  }


  @Override
  public Unit unit() {
    return Unit.from(bytes);
  }


  @Override
  public int hashCode() {
    int hash = 5;
    hash = 41 * hash + (int) (this.bytes ^ (this.bytes >>> 32));
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
    final FSize other = (FSize) obj;
    if (this.bytes != other.bytes) {
      return false;
    }
    return true;
  }
  
  
  @Override
  public String toString() {
    return new DecimalFormat("0.00").format(value())+ " "+ unit().name();
  }
  
  
  public static FSize of(long bytes) {
    return new FSize(bytes);
  }
  
  
  public static FSize of(Path path) {
    FSize size = of(Long.MIN_VALUE);
    try { size = of(Files.size(path)); } 
    catch(IOException e) {}
    return size;
  }
  
}