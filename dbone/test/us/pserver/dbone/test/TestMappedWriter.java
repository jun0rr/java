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

package us.pserver.dbone.test;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 20/09/2017
 */
public class TestMappedWriter {

  
  public static void main(String[] args) throws IOException, InterruptedException {
    FileChannel ch = FileChannel.open(
        Paths.get("/storage/mappedfile.dat"), 
        StandardOpenOption.CREATE, 
        StandardOpenOption.READ, 
        StandardOpenOption.WRITE
    );
    MappedByteBuffer buffer = ch.map(FileChannel.MapMode.READ_WRITE, 0, Integer.MAX_VALUE);
    ch.close();
    double inc = 0.001;
    double ori = 0.543;
    while(true) {
      ori += inc;
      System.out.println("* writing ["+ ori+ "] at "+ Instant.now());
      buffer.putDouble(ori);
      buffer.position(0);
      Thread.sleep(2000);
    }
  }
  
}
