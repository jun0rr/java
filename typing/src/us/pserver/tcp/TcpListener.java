/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca � software livre; voc� pode redistribu�-la e/ou modific�-la sob os
 * termos da Licen�a P�blica Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a vers�o 2.1 da Licen�a, ou qualquer
 * vers�o posterior.
 * 
 * Esta biblioteca � distribu�da na expectativa de que seja �til, por�m, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia impl�cita de COMERCIABILIDADE
 * OU ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a Licen�a P�blica
 * Geral Menor do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor do GNU junto
 * com esta biblioteca; se n�o, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endere�o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 */

package us.pserver.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.function.Consumer;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 01/07/2015
 */
public class TcpListener {

  private String address;
  
  private int port;
  
  private int timeout;
  
  private ServerSocket svr;
  
  boolean run;
  
  private Socket sock;
  
  
  public TcpListener() {
    address = null;
    port = 0;
    timeout = 0;
    run = false;
  }
  
  
  public TcpListener(String address, int port) {
    this.address = address;
    this.port = port;
    timeout = 0;
    run = false;
  }


  public String getAddress() {
    return address;
  }


  public void setAddress(String address) {
    this.address = address;
  }


  public int getPort() {
    return port;
  }


  public void setPort(int port) {
    this.port = port;
  }


  public int getTimeout() {
    return timeout;
  }


  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }


  public SocketAddress getSocketAddress() {
    if(address == null || port < 1)
      throw new IllegalStateException("Invalid "+ toString());
    return new InetSocketAddress(address, port);
  }
  
  
  public ServerSocket getServerSocket() {
    return svr;
  }
  
  
  public TcpListener bind() throws IOException {
    svr = new ServerSocket();
    svr.bind(getSocketAddress());
    return this;
  }
  
  
  public void close() throws IOException {
    if(svr == null) return;
    run = false;
    svr.close();
  }
  
  
  public Socket accept() throws IOException {
    if(svr == null) return null;
    if(timeout > 0) svr.setSoTimeout(timeout);
    return svr.accept();
  }
  
  
  public Socket getAcceptedSocket() {
    return sock;
  }
  
  
  public SocketHandler getSocketHandler() {
    if(sock == null) return null;
    return new BasicSocketHandler(sock);
  }
  
  
  public boolean isRunning() {
    return true;
  }
  
  
  public void stop() {
    run = false;
  }
  
  
  public void startServer(Consumer<TcpListener> cs) throws IOException {
    if(cs == null) return;
    run = true;
    svr = new ServerSocket();
    svr.bind(getSocketAddress());
    while(run) {
      try { 
        sock = svr.accept();
        cs.accept(this);
      } 
      catch(SocketTimeoutException e) {}
      catch(RuntimeException r) {
        throw new IOException(r.toString(), r);
      }
    }
  }


  @Override
  public String toString() {
    return "TcpListener{" + "address=" + address + ", port=" + port + ", running=" + run + '}';
  }
  
}
