/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;


/**
 *
 * @author Juno
 */
public interface OutputTcpChannel extends TcpChannel {
  
  public OutputTcpChannel send(Object msg);
  
}
