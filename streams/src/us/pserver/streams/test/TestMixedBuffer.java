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

package us.pserver.streams.test;

import java.io.IOException;
import java.nio.file.Path;
import us.pserver.cdr.crypt.CryptAlgorithm;
import us.pserver.cdr.crypt.CryptKey;
import us.pserver.streams.IO;
import us.pserver.streams.MixedReadBuffer;
import us.pserver.streams.MixedWriteBuffer;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 10/04/2015
 */
public class TestMixedBuffer {

  
  public static void main(String[] args) throws IOException {
    MixedWriteBuffer wbuf = new MixedWriteBuffer();
    Path pi = IO.p("/storage/pic.jpg");
    Path po = IO.p("/storage/pic-2.jpg");
    
    CryptKey key = CryptKey.createRandomKey(CryptAlgorithm.AES_CBC_PKCS5);
    wbuf.getCoderFactory().setBase64CoderEnabled(true).setGZipCoderEnabled(true).setCryptCoderEnabled(true, key);
    
    System.out.print("* buffer in  << ("+ pi+ ")\t");
    long millis = System.currentTimeMillis();
    wbuf.load(pi);
    System.out.println(" [OK]  ");
    System.out.println("  time: "+ (System.currentTimeMillis() - millis));
    System.out.println("* wbuf.length="+ wbuf.length());
    
    MixedReadBuffer rbuf = wbuf.getReadBuffer();
    rbuf.getCoderFactory().setBase64CoderEnabled(true).setGZipCoderEnabled(true).setCryptCoderEnabled(true, key);
    System.out.print("* buffer out >> ("+ po+ ")\t");
    millis = System.currentTimeMillis();
    rbuf.flush(po);
    System.out.println(" [OK]  ");
    System.out.println("  time: "+ (System.currentTimeMillis() - millis));
    
    wbuf.close();
  }
  
}
