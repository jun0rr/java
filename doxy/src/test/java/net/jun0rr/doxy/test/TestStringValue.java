/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import net.jun0rr.doxy.server.http.util.StringValue;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestStringValue {
  
  @Test
  public void inetAddress() {
    System.out.println("---- inetAddress ----");
    StringValue sv = StringValue.of("127.0.0.1");
    System.out.println(sv + " --> " + sv.getAsInetAddress());
  }
  
  @Test
  public void host() {
    System.out.println("---- host ----");
    StringValue sv = StringValue.of("127.0.0.1:443");
    System.out.println(sv + " --> " + sv.getAsHost());
  }
  
  @Test
  public void localDate() {
    System.out.println("---- localDate ----");
    StringValue sv = StringValue.of("29/03/2020");
    System.out.println(sv + " --> " + sv.getAsLocalDate());
    sv = StringValue.of("2020-03-29");
    System.out.println(sv + " --> " + sv.getAsLocalDate());
  }
  
  @Test
  public void localDateTime() {
    System.out.println("---- localDateTime ----");
    StringValue sv = StringValue.of("2020-03-29 18:06:20");
    System.out.println(sv + " --> " + sv.getAsLocalDateTime());
    sv = StringValue.of("2020-03-29T18:06:20");
    System.out.println(sv + " --> " + sv.getAsLocalDateTime());
  }
  
  @Test
  public void object() {
    System.out.println("---- object ----");
    StringValue sv = StringValue.of("2020-03-29 18:06:20");
    System.out.println(sv + " --> " + sv.getAsObject());
    sv = StringValue.of("TrUe");
    System.out.println(sv + " --> " + sv.getAsObject());
    sv = StringValue.of("2020,true,127.0.0.1,2020-03-29");
    System.out.println(sv + " --> " + sv.getAsObject());
  }
  
}
