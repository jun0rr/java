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

package com.jpower.tn3270.script.executor;

import com.jpower.tn3270.Session;
import com.jpower.tn3270.script.Command;
import com.jpower.tn3270.script.CommandType;
import com.jpower.tn3270.script.Var;
import com.jpower.tn3270.script.VarMemory;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 0.0 - 02/08/2013
 */
public class GetScreenExecutor extends Executor {


  @Override
  public boolean exec(Command cmd, Session sess) {
    this.checkCommand(cmd);
    this.checkSession(sess);
    if(cmd.getType() != CommandType.GET_SCREEN)
      return false;
    
    Var var = VarMemory.getInstance().get(cmd.getArg(0));
    if(var == null) var = new Var(cmd.getArg(0));
    
    var.setValue(
        this.breakLines(
        sess.getScreen()));
    
    VarMemory.getInstance().put(var);
    return true;
  }
  
  
  private String breakLines(String str) {
    if(str == null || str.length() < 80)
      return str;
    
    char[] cs = str.toCharArray();
    str = "";
    for(int i = 0; i < cs.length; i++) {
      if(i > 1 && i % 80 == 0)
        str += '\n';
      str += cs[i];
    }
    return str;
  }

}
