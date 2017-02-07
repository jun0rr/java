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

import br.com.bb.disec.micro.Server;
import br.com.bb.disec.micro.util.Infinispan;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

/**
 * Um handler que pode ser usado para parar a uma instancia do microserviço.
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 19/07/2016
 */
public class ShutdownHandler implements HttpHandler {
  
  public static final String SHUTDOWN_MESSAGE = "Server Shutdown";
  
  private final Server server;
  
  /**
   * Contrutor padrão da classe.
   * @param server Servidor que vai ser desligado.
   */
  public ShutdownHandler(Server server) {
    if(server == null) {
      throw new IllegalArgumentException("Invalid Undertow Server: "+ server);
    }
    this.server = server;
  }
  
  /**
   * Fecha as pools de conexão e para o servidor.
   * @param hse Exchanger de resquisição e resposta do servidor
   * @throws Exception 
   */
  @Override
  public void handleRequest(HttpServerExchange hse) throws Exception {
    hse.addExchangeCompleteListener((h,n)->{
      Infinispan.stop();
      server.stop();
    });
    hse.getResponseSender().send(SHUTDOWN_MESSAGE+ "\n");
    hse.endExchange();
  }

}
