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

package br.com.bb.disec.micro.handler.resp;

import br.com.bb.disec.micro.channel.CsvChannel;
import com.mongodb.client.MongoCursor;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import java.nio.channels.Channels;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 09/09/2016
 */
public class CsvCachedResponse extends AbstractCachedResponse {
  
  
  public CsvCachedResponse(List<String> columns) {
    super(columns);
  }
  
  
  @Override
  public void doResponse(HttpServerExchange hse, MongoCursor<Document> cursor) throws Exception {
    hse.getResponseHeaders().put(
        Headers.CONTENT_TYPE, "text/csv; charset=utf-8"
    );
    hse.startBlocking();
    CsvChannel channel = new CsvChannel(Channels.newChannel(hse.getOutputStream()));
    for(int i = 0; i < columns.size(); i++) {
      channel.put(columns.get(i));
      if(i < columns.size() -1) {
        channel.nextElement();
      }
    }
    channel.newLine();
    Document next = (cursor.hasNext() ? cursor.next() : null);
    do {
      if(next != null) {
        Object[] keys = next.keySet().toArray();
        for(int i = 0; i < keys.length; i++) {
          if(!"_id".equals(keys[i].toString())
              && !"created".equals(keys[i].toString())) {
            channel.put(next.get(keys[i]));
            if(i < keys.length -1) {
              channel.nextElement();
            }
          }
        }
      }
      next = (cursor.hasNext() ? cursor.next() : null);
      if(next != null) channel.newLine();
    } while(next != null);
    channel.close();
  }

}