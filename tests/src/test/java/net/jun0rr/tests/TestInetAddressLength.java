/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.tests;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestInetAddressLength {
  @Test
  public void ipv4AddressLength() throws UnknownHostException {
    try {
      InetAddress[] localhost = InetAddress.getAllByName("localhost");
      byte[] raw;
      for(InetAddress i : localhost) {
        raw = i.getAddress();
        System.out.printf("* localhost: %s, class: %s, addr: %s, name: %s, length: %d, bytes: %s%n", i, i.getClass().getSimpleName(), i.getHostAddress(), i.getHostName(), raw.length, Arrays.toString(raw));
      }
      InetAddress[] _192_168_15_5 = InetAddress.getAllByName("192.168.15.5");
      for(InetAddress i : _192_168_15_5) {
        raw = i.getAddress();
        System.out.printf("* 192.168.15.5: %s, class: %s, addr: %s, name: %s, length: %d, bytes: %s%n", i, i.getClass().getSimpleName(), i.getHostAddress(), i.getHostName(), raw.length, Arrays.toString(raw));
      }
      InetAddress[] google = InetAddress.getAllByName("2001:4860:4860::8888");
      for(InetAddress i : google) {
        raw = i.getAddress();
        System.out.printf("* google: %s, class: %s, addr: %s, name: %s, length: %d, bytes: %s%n", i, i.getClass().getSimpleName(), i.getHostAddress(), i.getHostName(), raw.length, Arrays.toString(raw));
      }
      
    } catch (UnknownHostException ex) {
      ex.printStackTrace();
      throw ex;
    }
  }
}
