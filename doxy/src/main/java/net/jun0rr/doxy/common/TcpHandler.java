/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import java.util.Optional;


/**
 *
 * @author juno
 */
@FunctionalInterface
public interface TcpHandler {
  
  public Optional<TcpExchange> handle(TcpExchange ex) throws Exception;
  
}
