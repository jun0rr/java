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

package us.pserver.tools.om;

import us.pserver.tools.function.ThrowableConsumer;
import us.pserver.tools.function.ThrowableFunction;
import us.pserver.tools.function.ThrowableSupplier;
import us.pserver.tools.function.ThrowableTask;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 02/01/2018
 */
public class TypedStringException extends RuntimeException {

  public TypedStringException() {
  }

  public TypedStringException(String message) {
    super(message);
  }

  public TypedStringException(String message, Throwable cause) {
    super(message, cause);
  }

  public TypedStringException(Throwable cause) {
    super(cause);
  }
  
  
  public static <T,R> R rethrow(ThrowableFunction<T,R> fn, T t) {
    try {
      return fn.apply(t);
    }
    catch(Exception e) {
      throw new TypedStringException(e.toString(), e);
    }
  }
  
  
  public static <T> void rethrow(ThrowableConsumer<T> fn, T t) {
    try {
      fn.accept(t);
    }
    catch(Exception e) {
      throw new TypedStringException(e.toString(), e);
    }
  }
  
  
  public static <T> T rethrow(ThrowableSupplier<T> fn) {
    try {
      return fn.supply();
    }
    catch(Exception e) {
      throw new TypedStringException(e.toString(), e);
    }
  }
  
  
  public static void rethrow(ThrowableTask fn) {
    try {
      fn.run();
    }
    catch(Exception e) {
      throw new TypedStringException(e.toString(), e);
    }
  }
  
}