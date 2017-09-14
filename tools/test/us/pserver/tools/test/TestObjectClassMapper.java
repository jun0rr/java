/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca � software livre; voc� pode redistribu�-la e/ou modific�-la sob os
 * termos da Licen�a P�blica Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a vers�o 2.1 da Licen�a, ou qualquer
 * vers�o posterior.
 * 
 * Esta biblioteca � distribu�da na expectativa de que seja �til, por�m, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia impl�cita de COMERCIABILIDADE
 * OU ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a Licen�a P�blica
 * Geral Menor do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor do GNU junto
 * com esta biblioteca; se n�o, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endere�o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 */

package us.pserver.tools.test;

import java.util.Date;
import java.util.LinkedList;
import us.pserver.tools.mapper.MappedValue;
import us.pserver.tools.mapper.ObjectClassMapper;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/09/2017
 */
public class TestObjectClassMapper {

  
  public static void main(String[] args) {
    ObjectClassMapper mapper = new ObjectClassMapper();
    //AObj a = new AObj("hello", 5, new int[] {5,4,3,2,1,0}, new char[] {'a', 'b', 'c', 'd'}, new Date());
    AObj a = new AObj("hello", new Date());
    LinkedList<Integer> ls = new LinkedList<>();
    ls.add(10);
    ls.add(9);
    ls.add(8);
    ls.add(7);
    ls.add(6);
    ls.add(5);
    BObj b = new BObj("world", a, ls);
    System.out.println("* a: "+ a);
    System.out.println("* b: "+ b);
    MappedValue omp = mapper.map(a);
    System.out.println("* a.mapped  : "+ omp);
    a = mapper.unmap(omp);
    System.out.println("* a.unmapped: "+ a);
    omp = mapper.map(b);
    System.out.println("* b.mapped  : "+ omp);
    b = mapper.unmap(omp);
    System.out.println("* b.unmapped: "+ b);
  }
  
}
