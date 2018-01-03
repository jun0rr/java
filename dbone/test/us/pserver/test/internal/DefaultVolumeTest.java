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

package us.pserver.test.internal;

import us.pserver.dbone.volume.DefaultVolume;
import us.pserver.dbone.volume.Record;
import us.pserver.dbone.volume.Volume;
import java.io.IOException;
import us.pserver.dbone.store.Storage;
import us.pserver.dbone.store.FileStorage;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Test;
import us.pserver.date.SimpleDate;
import us.pserver.test.bean.AObj;
import us.pserver.test.bean.BObj;
import us.pserver.dbone.serial.GsonSerializationService;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 23/11/2017
 */
public class DefaultVolumeTest {

  private static final AObj a = new AObj("Aobj", 123, new int[]{1,2,3}, new char[]{'a','b','c'}, SimpleDate.parseDate("24/11/2017 13:27:00"));
  
  private static final BObj b = new BObj("Bobj", a, Arrays.asList(1,2,3));
  
  private static final Path path = Paths.get("/home/juno/dbone");
  
  //private static final Storage store = new MockStorage(512);
  
  private static final Storage store = new FileStorage(path, 512, ByteBuffer::allocateDirect);
  
  private static final Volume vol = new DefaultVolume(store, new GsonSerializationService(), ByteBuffer::allocateDirect);
  
  
  @AfterClass
  public static void closeVolume() throws IOException {
    vol.close();
  }
  
  
  @Test
  public void writeReadConsistency() throws IOException {
    Record vid = vol.put(a);
    System.out.printf("writeReadConsistency.put: %s%n", vid);
    System.out.printf("writeReadConsistency.put: %s%n", a);
    AObj aget = (AObj) vol.get(vid);
    System.out.printf("writeReadConsistency.get: %s%n", aget);
    Assert.assertEquals(a, aget);
  }
  
}