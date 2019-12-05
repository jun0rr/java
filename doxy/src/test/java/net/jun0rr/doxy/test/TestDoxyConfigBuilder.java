/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.jun0rr.doxy.cfg.DoxyConfigBuilder;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestDoxyConfigBuilder {
  
  @Test
  public void testLoadProperties() throws IOException {
    System.out.println("------ testLoadProperties ------");
    Properties props = new Properties();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("doxy.properties")) {
      props.load(is);
      System.out.println(DoxyConfigBuilder.newBuilder().loadProperties(props).build());
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void testLoadEnvironment() throws Exception {
    try {
      System.out.println("------ testLoadEnvironment ------");
      Map<String,String> env = new HashMap<>();
      env.put(DoxyConfigBuilder.ENV_REMOTE_HOST, "google.com:443");
      env.put(DoxyConfigBuilder.ENV_BUFFER_SIZE, "1024");
      env.put(DoxyConfigBuilder.ENV_THREADPOOL_SIZE, "2");
      setEnvironment(env);
      System.out.println(DoxyConfigBuilder.newBuilder().loadEnvironment().build());
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  private void setEnvironment(Map<String,String> newenv) throws Exception {
    try {
      Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
      Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
      theEnvironmentField.setAccessible(true);
      Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
      env.putAll(newenv);
      Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
      theCaseInsensitiveEnvironmentField.setAccessible(true);
      Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
      cienv.putAll(newenv);
    } catch (NoSuchFieldException e) {
      Class[] classes = Collections.class.getDeclaredClasses();
      Map<String, String> env = System.getenv();
      for(Class cl : classes) {
        if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
          Field field = cl.getDeclaredField("m");
          field.setAccessible(true);
          Object obj = field.get(env);
          Map<String, String> map = (Map<String, String>) obj;
          map.clear();
          map.putAll(newenv);
        }
      }
    }
  }
  
}
