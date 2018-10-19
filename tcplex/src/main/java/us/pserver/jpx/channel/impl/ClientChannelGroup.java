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

package us.pserver.jpx.channel.impl;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import us.pserver.jpx.channel.Channel;
import us.pserver.jpx.channel.ChannelConfiguration;
import us.pserver.jpx.channel.ChannelEngine;
import us.pserver.jpx.channel.SwitchableChannel;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 14/09/2018
 */
public class ClientChannelGroup extends AbstractChannelGroup<SocketChannel> {
  
  
  public ClientChannelGroup(Selector select, ChannelConfiguration cfg, ChannelEngine eng, int maxSize) {
    super(select, cfg, eng, maxSize);
  }
  
  @Override
  public Channel add(SocketChannel socket) throws IOException {
    if(this.isFull()) {
      throw new IllegalStateException("ChannelGroup is full (count=" + count + ")");
    }
    Objects.requireNonNull(socket);
    count++;
    SwitchableChannel channel = new ClientChannel(socket, selector, config, engine);
    if(running) {
      functions.forEach(channel::appendFunction);
      listeners.forEach(channel::addListener);
    }
    socket.configureBlocking(false);
    sockets.put(socket, channel);
    socket.register(selector, SelectionKey.OP_CONNECT 
        | SelectionKey.OP_READ 
        | SelectionKey.OP_WRITE, channel
    );
    return channel;
  }

  
  @Override
  public void switchKey(SelectionKey key) throws IOException {
    SwitchableChannel channel = (SwitchableChannel) key.attachment();
    SocketChannel sock = (SocketChannel) key.channel();
    if(sock.isOpen() && sock.isConnected()) {
      channel.switchKey(key);
    }
    else {
      disconnect(sock);
    }
  }
  
}