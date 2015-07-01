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
import java.util.List;
import us.pserver.tcp.TcpConnector;
import us.pserver.timer.Timer;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 01/07/2015
 */
public class EchoClient {

  
  public static void main(String[] args) throws IOException, InterruptedException {
    TcpConnector con = new TcpConnector("localhost", 50500);
    Timer.Nanos tm = new Timer.Nanos();
    tm.start();
    
    con.connect();
    tm.lap();
    
    con.send("Hello World!");
    tm.lap();
    
    System.out.println(con.receive());
    tm.lap();
    
    con.disconnect();
    tm.lapAndStop();
    
    List<Double> laps =  tm.lapsElapsedFromLast().lapsToMillis();
    System.out.println(tm);
    System.out.println("Conn1: "+ laps.get(0));
    System.out.println("Send1: "+ laps.get(1));
    System.out.println("Rece1: "+ laps.get(2));
    System.out.println("Disc1: "+ laps.get(3));
    System.out.println();
    tm = new Timer.Nanos().start();
    
    con.connect();
    tm.lap();
    
    con.send("quit");
    tm.lap();
    
    System.out.println(con.receive());
    tm.lap();
    
    con.disconnect();
    tm.lapAndStop();
    
    
    laps =  tm.lapsElapsedFromLast().lapsToMillis();
    System.out.println(tm);
    System.out.println("Conn2: "+ laps.get(0));
    System.out.println("Send2: "+ laps.get(1));
    System.out.println("Rece2: "+ laps.get(2));
    System.out.println("Disc2: "+ laps.get(3));
  }
  
}
