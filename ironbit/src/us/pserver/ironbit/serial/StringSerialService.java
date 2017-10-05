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

package us.pserver.ironbit.serial;

import us.pserver.ironbit.IronbitConfiguration;
import us.pserver.ironbit.SerialCommons;
import us.pserver.ironbit.SerialService;
import us.pserver.ironbit.record.DefaultSerialRecord;
import us.pserver.ironbit.record.SerialRecord;
import us.pserver.tools.UTF8String;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 03/10/2017
 */
public class StringSerialService implements SerialService<String> {

  @Override
  public SerialRecord serialize(String name, String obj) {
    return new DefaultSerialRecord(
        IronbitConfiguration.get().registerClassID(obj.getClass()), 
        name, 
        UTF8String.from(obj).getBytes()
    );
  }

  @Override
  public String deserialize(SerialRecord rec) {
    return SerialCommons.readString(rec.getValue(), 0);
  }

}
