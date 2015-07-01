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

package us.pserver.typing.test;

import java.io.IOException;
import java.util.Date;
import us.pserver.tcp.TcpListener;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 01/07/2015
 */
public class EchoServer {

  
  public static void main(String[] args) throws IOException {
    TcpListener tl = new TcpListener("localhost", 50500);
    System.out.println(tl);
    tl.startServer(t->{
      try {
        String str = t.getSocketHandler().receive();
        if(str == null) return;
        System.out.println("<- "+ str);
        if(str.toLowerCase().startsWith("quit")) {
          System.out.println(":: bye");
          System.exit(1);
        }
        t.getSocketHandler().send(new Date().toString() + ":"+ str);
      } catch(IOException e) {
        throw new RuntimeException(e.toString(), e);
      }
    });
  }
  
}
