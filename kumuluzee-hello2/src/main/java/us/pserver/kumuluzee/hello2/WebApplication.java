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

package us.pserver.kumuluzee.hello2;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 16/04/2018
 */
@ApplicationPath("/")
public class WebApplication extends Application {
  
  public static final String JERSEY_CLASSNAMES = "jersey.config.server.provider.classnames";
  
  public static final String MULTIPART_FEATURE = "org.glassfish.jersey.media.multipart.MultiPartFeature";
  
  
  private final Map<String,Object> props;
  
  public WebApplication() {
    props = new HashMap<>();
    props.put(JERSEY_CLASSNAMES, MULTIPART_FEATURE);
  }

  @Override
  public Map<String,Object> getProperties() {
    return props;
  }
  
}