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

package us.pserver.xprops.converter;

import java.net.SocketAddress;
import us.pserver.tools.Valid;
import us.pserver.xprops.XTag;
import us.pserver.xprops.XValue;
import us.pserver.xprops.transformer.SocketAddressTransformer;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 30/07/2015
 */
public class SocketAddressXConverter implements XConverter<SocketAddress> {


  @Override
  public XTag toXml(SocketAddress obj) {
    return new XValue(
        new SocketAddressTransformer().toString(Valid.off(obj).forNull()
                .getOrFail(SocketAddress.class))
    );
  }


  @Override
  public SocketAddress fromXml(XTag tag) {
    return Valid.off(tag)
        .forNull().getOrFail(XTag.class)
        .xvalue().asSocketAddress();
  }

}