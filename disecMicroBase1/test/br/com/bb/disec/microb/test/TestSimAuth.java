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

package br.com.bb.disec.microb.test;

import br.com.bb.disec.micro.client.AuthenticationClient;
import br.com.bb.disec.micro.client.SimAuthClient;
import java.io.IOException;
import us.pserver.timer.Timer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 19/09/2016
 */
public class TestSimAuth {

  
  public static void main(String[] args) throws IOException {
    SimAuthClient scli = SimAuthClient.ofDefault("f6036477");
    scli.doAuth();
    System.out.println("* token: "+ scli.getBBSSOToken());
    System.out.println("* user.: "+ scli.getUser());
    AuthenticationClient acli = AuthenticationClient.ofDefault();
    Timer tm = new Timer.Nanos().start();
    acli.doAuth(scli.getBBSSOToken());
    System.out.println("* AuthenticationClient time: "+ tm.lapAndStop());
    System.out.println("* auth: "+ acli.isAuthenticated());
    System.out.println("* user: "+ acli.getUser());
  }
  
}
