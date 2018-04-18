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

package us.pserver.micro;

import java.io.IOException;
import java.nio.file.Paths;
import us.pserver.micro.config.ServerConfig;
import us.pserver.micro.ResourceLoader.ResourceLoadException;
import us.pserver.orb.Orb;
import us.pserver.orb.gson.JsonOrbConfig;
import us.pserver.tools.Match;


/**
 *
 * @author Juno Roesler - juno.roesler@bb.com.br
 * @version 1.0.201609
 */
public enum ServerSetup {
  
  INSTANCE(ResourceLoader.self());
  
  public static final String DEFAULT_CONFIG = "/resources/serverconf.json";
  
  private final ResourceLoader loader;
  
  private final ServerConfig config;
  
  
  /**
   * Construtor para a configuração do servidor. O servidor é configurado com
   * as configurações padrões.
   * @param rld
   * @throws br.com.bb.disec.micro.ResourceLoader.ResourceLoadException 
   * Se nenhum recurso for encontrado ou se ocorrer um erro na busca.
   */
  private ServerSetup(ResourceLoader rld) throws ResourceLoadException {
    this.loader = rld;
    try {
      ServerConfig
      this.config = Orb.get().fromConfiguration(JsonOrbConfig.of(Paths.get(DEFAULT_CONFIG))).create(ServerConfig.class);
    }
    catch(IOException e) {
      throw new RuntimeException(e.toString(), e);
    }
  }
  
  private boolean validate(ServerConfig cfg) {
    return cfg.getAddress() != null
        && cfg.getPort() > 0
        && cfg.getHandlers() != null;
        && cfg.getDbConfig() != null
  }
  
  /**
   * Pega a configuração de servidor existente na classe.
   * @return configuração existente
   */
  public ServerConfig config() {
    return config.get();
  }
  
  /**
   * Define uma instancia de ServerConfig.
   * @param cfg ServerConfig
   * @return ServerSetup INSTANCE
   */
  public ServerSetup config(ServerConfig cfg) {
    config.set(Match.notNull(cfg).getOrFail("Bad null ServerConfig"));
    return this;
  }
  
  /**
   * Pega o carregador de recursos existente na classe.
   * @return carregador de recursos
   */
  public ResourceLoader loader() {
    return loader;
  }
  
  /**
   * Cria o servidor com as configurações existentes na classe.
   * @return servidor criado
   */
  public Server createServer() {
    return new Server(config.get());
  }
  
}
