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

package us.pserver.micron.config;

import java.nio.file.Path;
import us.pserver.micron.config.impl.MicronConfigBuilderImpl;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 02/02/2019
 */
public interface MicronConfig {

  public ServerConfig getServerConfig();
  
  public SecurityConfig getSecurityConfig();
  
  public IgniteConfig getIgniteConfig();
  
  
  
  public static MicronConfigBuilder builder() {
    return new MicronConfigBuilderImpl();
  }
  
  
  
  
  
  public interface MicronConfigBuilder {
    
    public ServerConfig getServerConfig();

    public SecurityConfig getSecurityConfig();

    public IgniteConfig getIgniteConfig();
    
    public MicronConfigBuilder setServerConfig(String addr, int port);
  
    public MicronConfigBuilder setServerConfig(ServerConfig cfg);
  
    public MicronConfigBuilder setSecurityConfig(SecurityConfig cfg);
  
    public MicronConfigBuilder setIgniteConfig(IgniteConfig cfg);
    
    public MicronConfig build();
    
    public MicronConfig buildFromAppYaml();
        
    public MicronConfig buildFromAppYaml(Path appYaml);
    
    public SecurityConfig.SecurityConfigBuilder buildSecurityConfig();
    
    public IgniteConfig.IgniteConfigBuilder buildIgniteConfig();
  
  }
  
}