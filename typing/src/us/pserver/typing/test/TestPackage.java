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

import java.nio.file.Paths;
import us.pserver.timer.Timer;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 08/07/2015
 */
public class TestPackage {

  
  public static void main(String[] args) {
    System.out.println("* Timer.class.getResourceAsStream(\"/us/pserver/tcp/BasicSocketHandler.class\")");
    System.out.print("   ");
    System.out.println(Timer.class.getResourceAsStream("/us/pserver/tcp/BasicSocketHandler.class"));
    System.out.println("* Timer.class.getResource(\"\").getPath()");
    System.out.print("   ");
    System.out.println(Timer.class.getResource("").getPath());
    System.out.println("* Timer.class.getProtectionDomain().getCodeSource().getLocation()");
    System.out.print("   ");
    System.out.println(Timer.class.getProtectionDomain().getCodeSource().getLocation());
    System.out.println("* TestPackage.class.getResource(\"\").getPath()");
    System.out.print("   ");
    System.out.println(TestPackage.class.getResource("").getPath());
    System.out.println("* TestPackage.class.getProtectionDomain().getCodeSource().getLocation()");
    System.out.print("   ");
    System.out.println(TestPackage.class.getProtectionDomain().getCodeSource().getLocation());
    System.out.println("* TestPackage.class.getProtectionDomain().getClassLoader().getResource(\"\")");
    System.out.print("   ");
    System.out.println(TestPackage.class.getProtectionDomain().getClassLoader().getResource(""));
    System.out.println("* Paths.get(\"\").toAbsolutePath()");
    System.out.print("   ");
    System.out.println(Paths.get("").toAbsolutePath());
  }
  
}
