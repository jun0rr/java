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

package us.pserver.bitbox;

import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 12 de abr de 2019
 */
public interface BitTransform<T> {

  public Predicate<Class> matching();
  
  public BiConsumer<T,ByteBuffer> boxing();
  
  public Function<ByteBuffer,T> unboxing();
  
  
  public default DataBox<T> createBox(ByteBuffer buffer) {
    return DataBox.of(buffer, unboxing());
  }
  
  public default DataBox<T> createBox(T obj, ByteBuffer buffer) {
    boxing().accept(obj, buffer);
    return createBox(buffer);
  }
  
  
  public static <U> BitTransform of(Predicate<Class> prd, BiConsumer<U,ByteBuffer> box, Function<ByteBuffer,U> unbox) {
    return new BitTransform() {
      @Override
      public Predicate<Class> matching() {
        return prd;
      }
      @Override
      public BiConsumer<U,ByteBuffer> boxing() {
        return box;
      }
      @Override
      public Function<ByteBuffer,U> unboxing() {
        return unbox;
      }
    };
  }
  
}
