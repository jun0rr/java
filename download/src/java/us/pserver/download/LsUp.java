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

package us.pserver.download;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static us.pserver.download.AppSetup.CURRENT_PATH;
import us.pserver.download.file.IFPath;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 22/03/2017
 */
@WebServlet("/up/*")
public class LsUp extends Base {
  
  private final Gson gson;
  
  
  public LsUp() {
    gson = new GsonBuilder().setPrettyPrinting().create();
  }


  private List<IFPath> ls(Path path) throws IOException {
    return Files.walk(path, 1)
        .map(p->IFPath.from(p))
        .collect(Collectors.toList());
  }
  
  
  @Override
  public String request(HttpServletRequest req, HttpServletResponse res) throws Exception {
    HttpSession ses = req.getSession();
    Path path = AppSetup.getAppSetup().getCurrentDir(ses);
    Path np = path.getParent();
    if(np == null) {
      res.sendError(403);
      return null;
    }
    List<IFPath> ls = ls(np);
    path = np;
    ses.setAttribute(CURRENT_PATH, path);
    res.getWriter().write(gson.toJson(ls));
    return null;
  }

}