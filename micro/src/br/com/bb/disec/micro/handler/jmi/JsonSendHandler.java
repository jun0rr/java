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

package br.com.bb.disec.micro.handler.jmi;

import br.com.bb.disec.micro.box.OpResult;
import br.com.bb.disec.micro.handler.JsonHandler;
import br.com.bb.disec.micro.util.JsonClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.undertow.server.HttpServerExchange;

/**
 * 
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 01/08/2016
 */
public abstract class JsonSendHandler implements JsonHandler {
  
  private final Gson gson;
  
  public JsonSendHandler() {
    gson = new GsonBuilder()
        .registerTypeHierarchyAdapter(Class.class, new JsonClass())
        .setPrettyPrinting()
        .create();
  }
  
  public Gson gson() {
    return gson;
  }
  
  public void send(HttpServerExchange hse, OpResult res) throws Exception {
    if(res == null) {
      hse.endExchange();
      return;
    }
    hse.setStatusCode(res.isSuccessful() ? 200 : 500);
    this.putJsonHeader(hse);
    hse.getResponseSender().send(gson.toJson(res));
    hse.endExchange();
  }
  
}
