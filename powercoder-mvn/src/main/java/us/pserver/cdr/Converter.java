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
 *
*/

package us.pserver.cdr;


/**
 * Conversor de objetos.
 * 
 * @author Juno Roesler - juno@pserver.us
 * @version 1.0 - 25/06/2014
 * @param <A> Tipo do objeto a ser convertido 
 * para o tipo <code>&lt;B&gt;</code>.
 * @param <B> Tipo do objeto convertido.
 */
public interface Converter<A,B> {
  
  /**
   * Converte o objeto do tipo <code>A</code> 
   * para um objeto do tipo <code>B</code>.
   * @param a Objeto a ser convertido.
   * @return Objeto convertido.
   */
  public B convert(A a);
  
  /**
   * Converte o objeto do tipo <code>B</code> 
   * para um objeto do tipo <code>A</code>.
   * @param b Objeto a ser convertido.
   * @return Objeto convertido.
   */
  public A reverse(B b);
  
}