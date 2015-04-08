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

package us.pserver.rob.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import us.pserver.rob.NetConnector;
import us.pserver.rob.channel.Transport;
import us.pserver.rob.channel.TcpXmlChannel;
import us.pserver.streams.IO;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 11/08/2014
 */
public class TestXmlServer {

  
  public static void main(String[] args) throws IOException {
    NetConnector nc = new NetConnector();
    System.out.println("* Listening on "+ nc);
    ServerSocket ss = nc.connectServerSocket();
    while(true) {
      Socket s = ss.accept();
      System.out.println("* Connection from "+ s);
      TcpXmlChannel ch = new TcpXmlChannel(s);
      Transport t = ch.read();
      System.out.println("* received: "+ t);
      if(t.hasContentEmbedded()) {
        IO.tc(t.getInputStream(), IO.os(IO.p("c:/.local/server.png")));
      }
      System.out.println("---------------------------");
      ch.close();
    }
  }
  
}
