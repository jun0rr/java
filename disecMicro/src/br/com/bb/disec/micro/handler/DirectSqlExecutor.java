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

package br.com.bb.disec.micro.handler;

import br.com.bb.disec.micro.response.CsvDirectResponse;
import br.com.bb.disec.micro.response.JsonDirectResponse;
import br.com.bb.disec.micro.response.XlsDirectResponse;
import br.com.bb.disec.micro.util.SqlObjectType;
import com.google.gson.JsonObject;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import java.sql.ResultSet;
import org.jboss.logging.Logger;
import us.pserver.timer.Timer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 01/09/2016
 */
public class DirectSqlExecutor extends AbstractSqlExecutor {
  
  @Override
  public void exec(HttpServerExchange hse, JsonObject req) throws Exception {
    super.exec(hse, req);
    ResultSet rst = query.getResultSet();
    Timer tm = new Timer.Nanos().start();
    if(req.has("format") && "csv".equalsIgnoreCase(
        req.get("format").getAsString())) {
      hse.getResponseHeaders().put(
          Headers.CONTENT_DISPOSITION, "attachment; filename=\""
              + req.get("query").getAsString()+ ".csv\""
      );
      new CsvDirectResponse().doResponse(hse, rst);
    }
    else if(req.has("format") && "xls".equalsIgnoreCase(
        req.get("format").getAsString())) {
      hse.getResponseHeaders().put(
          Headers.CONTENT_DISPOSITION, "attachment; filename=\""
              + req.get("query").getAsString()+ ".xls\""
      );
      new XlsDirectResponse().doResponse(hse, rst);
    }
    else {
      new JsonDirectResponse().doResponse(hse, rst);
    }
    Logger.getLogger(getClass()).info("DIRECT RETRIEVE TIME: "+ tm.lapAndStop());
  }
  
}
