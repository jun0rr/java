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

package us.pserver.test.mapping;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import us.pserver.finalson.mapping.LocalDateMapping;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 11/02/2018
 */
public class TestLocalDateMapping {

  private final LocalDate date = LocalDate.of(2018, 2, 11);
  
  private final JsonElement jdate = new JsonPrimitive("2018-02-11");
  
  private final LocalDateMapping map = new LocalDateMapping();
  
  @Test
  public void dateToJson() {
    Assertions.assertEquals(jdate, map.toJson(date));
  }
  
  @Test
  public void jsonToDate() {
    Assertions.assertEquals(date, map.fromJson(jdate));
  }
  
}
