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

package us.pserver.finalson.handles;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.function.BiFunction;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 11/02/2018
 */
@FunctionalInterface
public interface InvokableMatch extends BiFunction<Invokable, List<InvokableParam>, Boolean> {

  public static InvokableMatch matchParameters(ParameterMatch match) {
    return (i,l)->i.getParameters().stream()
        .allMatch(p->l.stream().anyMatch(v->
            match.apply(p, v.getJsonProperty())));
  }
  
  public static InvokableMatch matchName(String name) {
    return (i,l)->i.getName().equals(name);
  }
  
  public static InvokableMatch matchReturnType(Class type) {
    return (i,l)->i.getReturnType().isAssignableFrom(type);
  }
  
}