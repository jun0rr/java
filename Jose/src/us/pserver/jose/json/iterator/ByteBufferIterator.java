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

package us.pserver.jose.json.iterator;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import us.pserver.jose.json.ByteType;
import us.pserver.jose.json.JsonType;
import static us.pserver.jose.json.JsonType.END_ARRAY;
import static us.pserver.jose.json.JsonType.END_OBJECT;
import static us.pserver.jose.json.JsonType.FIELD;
import static us.pserver.jose.json.JsonType.IGNORE;
import static us.pserver.jose.json.JsonType.START_ARRAY;
import static us.pserver.jose.json.JsonType.START_OBJECT;
import us.pserver.jose.json.JsonValue;
import us.pserver.tools.UTF8String;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 06/03/2017
 */
public class ByteBufferIterator extends AbstractIterator {

  private final ByteBuffer src;
  
  private final ByteArrayOutputStream buffer;


  public ByteBufferIterator(ByteBuffer buf) {
    super();
    if(buf == null || buf.remaining() < 1) {
      throw new IllegalArgumentException("Bad Null/Empty ByteBuffer");
    }
    this.src = buf;
  }


  protected void unreadByte() {
    if(src.position() > 0) {
      src.position(src.position() -1);
    }
  }


  @Override
  public String readField() throws ByteIteratorException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    while((current = src.get()) != ByteType.VALUE
        && src.hasRemaining()) {
      if(current == ByteType.IGNORE 
          || current == ByteType.STRING) continue;
      bos.write(current);
    }
    if(bos.size() < 1) throw new ByteIteratorException("Can not read field");
    unreadByte();
    curfld = UTF8String.from(bos.toByteArray()).toString();
    return curfld;
  }


  @Override
  public String readString() throws ByteIteratorException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    JsonType tk = JsonType.of(current);
    if(tk == JsonType.NUMBER || tk == JsonType.BOOLEAN || tk == JsonType.NULL) {
      bos.write(current);
    }
    while((current = src.get()) != ByteType.FIELD
        && current != ByteType.END_ARRAY
        && current != ByteType.END_OBJECT
        && src.hasRemaining()) {
      if(current == ByteType.IGNORE 
          || current == ByteType.STRING) continue;
      bos.write(current);
    }
    if(bos.size() < 1) throw new ByteIteratorException("Can not read value");
    unreadByte();
    return UTF8String.from(bos.toByteArray()).toString();
  }


  @Override
  public boolean readBoolean() throws ByteIteratorException {
    return Boolean.parseBoolean(readString());
  }


  @Override
  public Number readNumber() throws ByteIteratorException {
    String str = readString();
    if(str.contains(".")) {
      return Double.valueOf(str);
    } else {
      return Long.valueOf(str);
    }
  }


  @Override
  public Object readNull() throws ByteIteratorException {
    readString();
    return null;
  }
  

  @Override
  public JsonValue read(JsonType tkn) {
    return JsonValue.of(tkn, readString());
  }


  @Override
  public JsonValue readNext() {
    JsonType tp = next();
    switch(tp) {
      case VALUE:
      case IGNORE:
        tp = next();
    }
    return read(tp);
  }


  @Override
  public void skip() {
    readString();
  }


  @Override
  public boolean hasNext() {
    return src.hasRemaining();
  }


  @Override
  public JsonType next() {
    current = src.get();
    JsonType tk = JsonType.of(current);
    switch(tk) {
      case START_ARRAY:
        arraylvl++;
        break;
      case START_OBJECT:
        objectlvl++;
        break;
      case END_ARRAY:
        arraylvl--;
        break;
      case END_OBJECT:
        objectlvl--;
        break;
      case FIELD:
        if(isInsideArray()) return next();
        break;
      case IGNORE:
        return next();
    }
    return tk;
  }
  
}
