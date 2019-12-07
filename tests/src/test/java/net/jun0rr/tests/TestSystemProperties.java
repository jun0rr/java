/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.tests;

import org.junit.jupiter.api.Test;


/**
 *
 * @author juno
 */
public class TestSystemProperties {
  
  @Test
  public void method() {
    System.out.println(System.getProperty("user.home"));
    System.out.println(System.getProperty("user.dir"));
    System.out.println(System.getProperty("user.name"));
  }
  
}
