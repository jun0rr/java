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

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import us.pserver.date.DateTime;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/09/2017
 */
public class TestDateFormat {

  
  public static void main(String[] args) {
    System.out.println(Instant.now());
    System.out.println(DateTime.now().toLocalDT());
    System.out.println(DateTime.now().toZonedDT());
    Date dt = new Date();
    Instant now = Instant.now();
    System.out.println(DateTime.of(dt).toInstant());
    System.out.println(DateTime.of(dt).toZonedDT().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    System.out.println(DateTime.of(DateTime.of(dt).toInstant()).format("yyyy-MM-dd'T'HH:mm:ss.SSS"));
  }
  
}
