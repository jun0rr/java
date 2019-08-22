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

package us.pserver.bitbox.transform;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import us.pserver.bitbox.BitTransform;
import static us.pserver.bitbox.transform.ClassTransform.BYTE_ID;
import us.pserver.tools.io.BitBuffer;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 11 de abr de 2019
 */
public class CharSequenceTransform implements BitTransform<CharSequence> {
  
  public static final byte BYTE_ID = 5;
  
  @Override
  public boolean match(byte id) {
    return BYTE_ID == id;
  }
  
  @Override
  public boolean match(Class c) {
    return CharSequence.class.isAssignableFrom(c);
  }
  
  @Override
  public Optional<Class> serialType() {
    return Optional.empty();
  }
  
  /**
   * [byte][int][byte*(length)]
   * @param s
   * @param buf
   * @return 
   */
  @Override
  public int box(CharSequence s, BitBuffer buf) {
    ByteBuffer bs = StandardCharsets.UTF_8.encode(s.toString());
    //Logger.debug("[{}][{}]", bs.remaining(), s);
    int len = 1 + bs.remaining() + Integer.BYTES;
    buf.put(BYTE_ID)
        .putInt(bs.remaining())
        .put(bs);
    return len;
  }
  
  @Override
  public CharSequence unbox(BitBuffer buf) {
    byte id = buf.get();
    if(BYTE_ID != id) throw new IllegalStateException(String.format(
        "Bad byte id: %d. Not a CharSequence buffer (%d)", id, BYTE_ID));
    int lim = buf.limit();
    int len = buf.getInt();
    //Logger.debug("[{}]", len);
    buf.limit(buf.position() + len);
    String str = StandardCharsets.UTF_8.decode(buf.toByteBuffer()).toString();
    buf.limit(lim);
    return str;
  }
  
}
