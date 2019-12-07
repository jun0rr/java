/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;


/**
 *
 * @author juno
 */
public class TestFnSerial {
  
  @Test
  public void test_function_serialization() throws IOException, ClassNotFoundException {
    try {
      Supplier<Integer> sup = (Supplier & Serializable)()->new Random().nextInt(100);
      Function<Supplier<Integer>,Integer> fun = s->s.get();
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
        oos.writeObject(sup);
      }
      String b64sup = Base64.getEncoder().encodeToString(bos.toByteArray());
      System.out.println("sup.get(): " + sup.get() + " => " + b64sup);

      ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(b64sup));
      try (ObjectInputStream ois = new ObjectInputStream(bis)) {
        //Supplier<Integer> y = (Supplier<Integer> & Serializable) ois.readObject();
        Supplier<Integer> y = (Supplier<Integer>) ois.readObject();
        System.out.println("y.get(): " + y.get());
      }
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
