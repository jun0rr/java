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

package us.pserver.fpack.test;

import com.cedarsoftware.util.io.JsonReader;
import java.io.IOException;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 02/11/2015
 */
public class TestJson {

  
  public static void main(String[] args) throws IOException {
    String js = "{\"@type\":\"us.pserver.fpack.FPackFileEntry\",\"values\":{\"@id\":1,\"@type\":\"java.util.HashMap\",\"owner\":\"BUILTIN\\\\Administrators\",\"path\":\"D:\\\\docs\\\\auto-logon.reg\",\"lastModifiedTime\":1318949004000,\"creationTime\":1429978958954,\"hidden\":false,\"readonly\":false,\"directory\":false},\"name\":\"auto-logon.reg\",\"position\":\"0000000000000001302\",\"size\":\"0000000000000000440\"}";
    System.out.println("* js: '"+ js+ "'");
    Object ob = JsonReader.jsonToJava(js);
    System.out.println("* ob: "+ ob);
  }
  
}
