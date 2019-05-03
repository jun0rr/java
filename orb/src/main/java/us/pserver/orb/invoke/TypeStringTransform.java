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

package us.pserver.orb.invoke;

import java.util.Objects;
import us.pserver.orb.TypeStrings;
import us.pserver.tools.Match;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 01/04/2018
 */
public class TypeStringTransform implements MethodTransform<Object> {
  
  private final TypeStrings types;
  
  public TypeStringTransform(TypeStrings types) {
    this.types = Match.notNull(types).getOrFail("Bad null TypedStrings");
  }

  @Override
  public boolean canHandle(InvocationContext ctx) {
    System.out.println(ctx);
    Object value = ctx.getValue();
    Class rtype = ctx.getMethod().getReturnType();
    System.out.println("rtype="+ rtype);
    System.out.println("value="+ value);
    System.out.println("value != null="+ (value != null));
    if(ctx.getValue() != null) {
      return CharSequence.class.isAssignableFrom(ctx.getValue().getClass())
          || !ctx.getMethod().getReturnType().isAssignableFrom(ctx.getValue().getClass());
    }
    return false;
  }


  @Override
  public Object apply(InvocationContext ctx) {
    return types.asType(
        Objects.toString(ctx.getValue()), 
        ctx.getMethod().getReturnType()
    );
  }

}