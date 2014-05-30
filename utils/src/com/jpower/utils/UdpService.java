/*
 *  Direitos Autorais Reservados (c) 2009 Juno Roesler
 *  Contato com o autor: juno.rr@gmail.com
 *
 *  Esta biblioteca � software livre; voc� pode redistribu�-la e/ou modific�-la sob os
 *  termos da Licen�a P�blica Geral Menor do GNU conforme publicada pela Free
 *  Software Foundation; tanto a vers�o 2.1 da Licen�a, ou (a seu crit�rio) qualquer
 *  vers�o posterior.
 *
 *  Este software � distribu�do na expectativa de que seja �til, por�m, SEM
 *  NENHUMA GARANTIA; nem mesmo a garantia impl�cita de COMERCIABILIDADE
 *  OU ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a Licen�a P�blica
 *  Geral Menor do GNU para mais detalhes.
 *
 *  Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor do GNU junto
 *  com esta biblioteca; se n�o, escreva para a Free Software Foundation, Inc., no
 *  endere�o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 * */
package com.jpower.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Juno Roesler
 */
public class UdpService {

  private DatagramSocket socket;

  private DatagramPacket packet;

  private int port;

  private int destinyPort;

  private String destiny;

  public static final int DEFAULT_PORT = 8001;

  public static final int MAX_SIZE = 5120; //5 kb


  /**
   * Construtor padr�o, n�o inicia
   * o socket.
   */
  public UdpService()
  {
    destinyPort = DEFAULT_PORT;
    port = -1;
    destiny = null;
  }

  /**
   * Retorna o destino.
   * @return Nome ou endere�o ip.
   */
  public String getDestiny() {
    return destiny;
  }

  /**
   * Seta o destino.
   * @param destiny Nome ou endere�o ip.
   */
  public void setDestiny(String destiny) {
    this.destiny = destiny;
  }

  /**
   * Retorna a porta do socket.
   * @return Porta.
   */
  public int getSocketPort() {
    return port;
  }

  /**
   * Seta a porta do socket.
   * @param port Porta
   */
  public void setSocketPort(int port) {
    this.port = port;
  }

  /**
   * Retorna a porta de destino.
   * @return Porta.
   */
  public int getDestinyPort() {
    return destinyPort;
  }

  /**
   * Seta a porta de destino.
   * @param port Porta
   */
  public void setDestinyPort(int port) {
    this.destinyPort = port;
  }

  /**
   * Abre o socket.
   * @return boolean indicando o sucesso
   * na cria��o do socket.
   */
  public boolean openSocket()
  {
    if(socket != null) return true;

    try {
      if(port > 0)
        socket = new DatagramSocket(port);
      else
        socket = new DatagramSocket();

      socket.setBroadcast(false);
      socket.setReuseAddress(true);

      return true;

    } catch(SocketException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * Fecha o socket.
   */
  public void closeSocket()
  {
    if(socket == null)
      return;
    
    socket.close();
    socket = null;
  }

  /**
   * Verifica se o socket est� fechado.
   * @return boolean
   */
  public boolean isClosed()
  {
    return (socket == null);
  }

  /**
   * Retorna a primeira porta dispon�vel.
   * @return Porta dispon�vel.
   */
  public static int getAvaliablePort()
  {
    try {
      return new DatagramSocket().getLocalPort();
    } catch(SocketException ex) {
      return -1;
    }
  }

  /**
   * Cria o pacote com as configura��es atuais
   * e o buffer especificado.
   * @param buf byte array.
   * @return boolean indicando se o pacote
   * foi criado com sucesso.
   */
  public boolean createPacket(byte[] buf)
  {
    if(destiny == null || destinyPort < 0)
      return false;
    
    try {
      packet = new DatagramPacket(buf, buf.length, 
          InetAddress.getByName(destiny), destinyPort);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  /**
   * Envia o pacote e seta como null.
   * @return boolean indicando o sucesso
   * do envio.
   */
  public boolean send() {
    if(isClosed())
      return false;

    try {
      socket.send(packet);
      return true;
    } catch (IOException ex) {
      return false;
    }
  }

  /**
   * Escuta por conex�es pelo
   * tempo especificado. Caso informado
   * valor negativo, bloquear� indefinidamente.
   * @param time Tempo em milisegundos.
   * @return Pacote recebido ou null.
   */
  public DatagramPacket receive(int time)
  {
    if(isClosed()) return null;

    packet = new DatagramPacket(
        new byte[MAX_SIZE], MAX_SIZE);
    try {
      if(time > 0)
        socket.setSoTimeout(time);
      
      socket.receive(packet);
      return packet;
    } catch (Exception ex) {
      return null;
    }
  }

	/**
	 * M�todo dutilit�rio est�tico, serializa
	 * um objeto para um array de bytes.
	 */
  public static byte[] serialize(Object o) {
    if(o == null) return null;

    byte[] content = null;

    try {
      ByteArrayOutputStream bout =
        new ByteArrayOutputStream();
      ObjectOutputStream out =
        new ObjectOutputStream(bout);

      out.writeObject(o);
      out.flush();
      bout.flush();

      content = bout.toByteArray();

      out.close();
      bout.close();

    } catch(Exception ex) { }
    finally { return content; }
  }//method()


  /**
   * M�todo utilit�rio est�tico, l�
   * um objeto de um array de bytes.
   */
  public static Object deserialize(byte[] buf) {
    if(buf == null || buf.length == 0)
      return null;

    try {
      ByteArrayInputStream bin =
        new ByteArrayInputStream(buf);
      ObjectInputStream in =
        new ObjectInputStream(bin);

      Object o = in.readObject();
      bin.close();
      in.close();
      
      return o;
    } catch(Exception ex) {
      ex.printStackTrace();
      return null;
    }//try-catch
  }//method()


  public static void main(String[] args) {
    UdpService udp1 = new UdpService();
    UdpService udp2 = new UdpService();

    udp1.setDestinyPort(9008);
    udp2.setSocketPort(9008);

    System.out.println("Socket 1 escutando na porta "+ udp1.getSocketPort());
    System.out.println("Socket 2 escutando na porta "+ udp2.getSocketPort());

    System.out.println("open 1: "+ udp1.openSocket());
    System.out.println("open 2: "+ udp2.openSocket());

    System.out.println("udp1 envaindo: HELLO!");
    udp1.createPacket(
        UdpService.serialize("HELLO!"));
    System.out.println("send: "+ udp1.send());

    System.out.println("udp2 recebendo ...");
    DatagramPacket d = udp2.receive(1000);

    if(d != null)
      System.out.println(
          UdpService.deserialize(
          d.getData()).toString());
    else
      System.out.println("d == null");
  }

}
