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

package us.pserver.orb.types;

import us.pserver.tools.fn.ThrowableConsumer;
import us.pserver.tools.fn.ThrowableFunction;
import us.pserver.tools.fn.ThrowableSupplier;
import us.pserver.tools.fn.ThrowableTask;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 02/01/2018
 */
public class TypeStringException extends RuntimeException {

  public TypeStringException() {
  }

  public TypeStringException(String message) {
    super(message);
  }

  public TypeStringException(String message, Throwable cause) {
    super(message, cause);
  }

  public TypeStringException(Throwable cause) {
    super(cause);
  }
  
  
  public static <T,R> R rethrow(ThrowableFunction<T,R> fn, T t) {
    try {
      return fn.apply(t);
    }
    catch(Exception e) {
      throw new TypeStringException(e.toString(), e);
    }
  }
  
  
  public static <T> void rethrow(ThrowableConsumer<T> fn, T t) {
    try {
      fn.accept(t);
    }
    catch(Exception e) {
      throw new TypeStringException(e.toString(), e);
    }
  }
  
  
  public static <T> T rethrow(ThrowableSupplier<T> fn) {
    try {
      return fn.supply();
    }
    catch(Exception e) {
      throw new TypeStringException(e.toString(), e);
    }
  }
  
  
  public static void rethrow(ThrowableTask fn) {
    try {
      fn.run();
    }
    catch(Exception e) {
      throw new TypeStringException(e.toString(), e);
    }
  }
  
}