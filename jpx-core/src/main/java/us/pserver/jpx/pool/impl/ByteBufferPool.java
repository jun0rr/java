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

package us.pserver.jpx.pool.impl;

import java.nio.ByteBuffer;
import java.util.function.IntFunction;
import us.pserver.jpx.pool.Pooled;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 13/08/2018
 */
public class ByteBufferPool extends DefaultPool<ByteBuffer> {
  
  public static final IntFunction<ByteBuffer> DEFAULT_ALLOC_FUNCTION = ByteBuffer::allocateDirect;
  
  public static final ByteBuffer EMPTY_BUFFER = ByteBuffer.wrap(new byte[0]);
  
  
  public ByteBufferPool(BufferPoolConfiguration cfg, IntFunction<ByteBuffer> allocfun) {
    super(cfg, () -> allocfun.apply(cfg.getUnitBufferSize()));
  }
  
  public ByteBufferPool(BufferPoolConfiguration cfg) {
    this(cfg, DEFAULT_ALLOC_FUNCTION);
  }

  @Override
  public BufferPoolConfiguration getConfiguration() {
    return (BufferPoolConfiguration) super.getConfiguration();
  }
  
  @Override
  public Pooled<ByteBuffer> alloc() {
    Pooled<ByteBuffer> pooled = super.alloc();
    pooled.get().clear();
    return pooled;
  }
  
  @Override
  public Pooled<ByteBuffer> allocAwait() {
    Pooled<ByteBuffer> pooled = super.allocAwait();
    pooled.get().clear();
    return pooled;
  }
  
}