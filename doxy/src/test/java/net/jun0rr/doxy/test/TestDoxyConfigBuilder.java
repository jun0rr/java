/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.jun0rr.doxy.DoxyConfigBuilder;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestDoxyConfigBuilder {
  
  @Test
  public void testLoadProperties() throws IOException {
    Properties props = new Properties();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("doxy.properties")) {
      props.load(is);
    }
    System.out.println(DoxyConfigBuilder.newBuilder().loadProperties(props).build());
  }
  
}
