/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.Compiler;
import us.pserver.tools.compile.ExtendedClassImpl;
import us.pserver.tools.compile.FunctionMethodImpl;
import us.pserver.tools.compile.MethodImpl;


/**
 *
 * @author juno
 */
public class TestExtendedClassImpl {
  
  @Test
  public void test_extended_class_impl() {
    try {
      ExtendedClassImpl ci = new ExtendedClassImpl(IPerson.class);
      Function<String,Integer> fn = (Function<String,Integer> & Serializable) s->new Random().nextInt();
      Method str2int = Reflect.of(IPerson.class).selectMethod("str2int").method().get();
      FunctionMethodImpl fi = new FunctionMethodImpl(fn, str2int);
      ci.add(fi);
      System.out.println("ci.getClassName() = '" + ci.getClassName() + "'");
      System.out.println(ci.getSourceCode());
      Compiler ccp = new Compiler();
      ccp.add(ci.getCompilationUnit()).compile();
      Reflect<IPerson> ref = ccp.reflectInstanceOf(IPerson.class);
      Supplier<IPerson> create = ref.selectConstructor().constructorAsSupplier();
      IPerson person = create.get();
      System.out.println(person);
      System.out.println("person.getAge(): " + person.getAge());
      System.out.println("person.getBirth(): " + person.getBirth());
      System.out.println("person.getFullName(): " + person.getFullName());
      System.out.println("person.str2int(\"52\"): " + person.str2int("52"));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
