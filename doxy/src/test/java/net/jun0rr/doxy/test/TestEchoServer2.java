/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import org.junit.jupiter.api.Test;


/**
 *
 * @author juno
 */
public class TestEchoServer2 {
  
  @Test
  public void method() {
    EchoServer2 server = new EchoServer2(2);
    server.start().sync();
    System.out.println("* Server stopped!");
  }
  
}
