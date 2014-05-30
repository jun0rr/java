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

package com.jpower.tn3270.script.parser;

import com.jpower.tn3270.script.CommandType;
import com.jpower.tn3270.script.Command;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 0.0 - 03/08/2013
 */
public class PassParser extends CommandParser {
  
  @Override
  public boolean canParse(String str) {
    return canParseBasic(str, Command.STR_PASS);
  }
  
  
  @Override
  public Command parse(String str) {
    if(!this.canParse(str)) return null;
    str = this.removeInitSpaces(str);
    
    int ieq = str.indexOf("=");
    if(ieq < 0) return null;
    
    String name = str.substring(Command.STR_PASS.length(), ieq).trim();
    String val = str.substring(ieq+1).trim();
    if(name == null || val == null) return null;
    
    return new Command(CommandType.PASS)
        .setArg(name, 0).setArg(val, 1);
  }
  
}
