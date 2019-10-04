/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.Compiler;
import us.pserver.tools.compile.ExtendedClassImpl;
import us.pserver.tools.compile.FunctionMethodImpl;


/**
 *
 * @author juno
 */
public class TestExtendedClassImpl {
  
  @Test
  public void test_extended_class_impl() {
    try {
      ExtendedClassImpl ci = new ExtendedClassImpl(IPerson.class);
      Function<String,Integer> fn = s->new Random().nextInt();
      Method str2int = Reflect.of(IPerson.class).selectMethod("str2int").method().get();
      FunctionMethodImpl fi = new FunctionMethodImpl(str2int);
      ci.add(fi);
      System.out.println("ci.getClassName() = '" + ci.getClassName() + "'");
      System.out.println(ci.getSourceCode());
      Compiler ccp = new Compiler();
      ccp.add(ci.getCompilationUnit()).compile();
      Reflect<IPerson> ref = ccp.reflectInstanceOf(IPerson.class);
      Map lambdaMap = ref.selectField("LAMBDA_MAP").<Map>fieldGetterAsSupplier().get();
      lambdaMap.put(str2int.getName() + Arrays.toString(str2int.getParameterTypes()), fn);
      Supplier<IPerson> create = ref.selectConstructor().constructorAsSupplier();
      IPerson person = create.get();
      //TriFunction<String,String,LocalDate,IPerson> create = ref.selectConstructor(String.class, String.class, LocalDate.class).constructorAsLambda(TriFunction.class);
      //IPerson person = create.apply("Juno", "Roesler", LocalDate.of(1980, 7, 7));
      System.out.println(person);
      System.out.println("person.getAge(): " + person.getAge());
      System.out.println("person.getBirth(): " + person.getBirth());
      System.out.println("person.getFullName(): " + person.getFullName());
      System.out.println("person.str2int(\"52\"): " + person.str2int("52"));
      Function<String,Integer> fn2 = s -> Integer.parseInt(s);
      lambdaMap.put(str2int.getName() + Arrays.toString(str2int.getParameterTypes()), fn2);
      System.out.println("person.str2int(\"52\"): " + person.str2int("52"));
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
