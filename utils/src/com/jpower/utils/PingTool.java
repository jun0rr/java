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

import java.net.InetAddress;

/**
 * Interface para classe utilit�ria
 * para execu��o do comando ping.
 * @author Juno Roesler
 */
public interface PingTool extends Runnable {

  /**
   * Seta o host alvo.
   * @param host Nome ou endere�o ip.
   */
  public void setHost(String host);

  /**
   * Retorna o host alvo.
   * @return Nome ou endere�o ip.
   */
  public String getHost();

  /**
   * Retorna o endere�o do host alvo.
   * @return Endere�o InetAddress.
   */
  public InetAddress getHostAddress();

  /**
   * Seta a quantidade de ping's
   * a executar.
   * @param count Quantidade de comandos
   * ping sucessivos.
   */
  public void setCount(int count);

  /**
   * Retorna a quantidade de ping's
   * a executar.
   * @return Quantidade de comandos
   * ping sucessivos.
   */
  public int getCount();

  /**
   * Seta o tamanho do pacote.
   * @param size tamanho do pacote em bytes.
   */
  public void setPacketSize(int size);

  /**
   * Retorna o tamanho do pacote.
   * @return tamanho do pacote em bytes.
   */
  public int getPacketSize();

  /**
   * Seta o "tempo de vida" do pacote, ou seja,
   * quantos "saltos" pode passar at� tornar-se
   * inv�lido e seja descartado.
   * @param ttl Time To Live.
   */
  public void setTTL(int ttl);

  /**
   * Retorna o "tempo de vida" do pacote, ou seja,
   * quantos "saltos" pode passar at� tornar-se
   * inv�lido e seja descartado.
   * @return Time To Live.
   */
  public int getTTL();

  /**
   * Seta o tempo de espera at� o
   * retorno do ECHO_REPLY.
   * @param timeout Em segundos
   */
  public void setTimeout(int timeout);

  /**
   * Retorna o tempo de espera at� o
   * retorno do ECHO_REPLY.
   * @return Em segundos
   */
  public int getTimeout();

  /**
   * Executa o comando ping.
   */
  public void run();

  /**
   * Representa��o em String.
   * @return String
   */
  @Override
  public String toString();

  /**
   * Retorna a sa�da do comando.
   * @return
   */
  public String getOutput();

  /**
   * Retorna true se o host foi
   * atingido com sucesso, false
   * caso contr�rio.
   * @return boolean
   */
  public boolean isSuccessful();

  /**
   * Retorna a linha principal de retorno do
   * comando.
   * @return String
   */
  public String getReturnLine();

  /**
   * Retorna a quantidade de disparos
   * que atingiram o alvo com sucesso.
   * @return Disparos atingidos.
   */
  public int getHits();

}
