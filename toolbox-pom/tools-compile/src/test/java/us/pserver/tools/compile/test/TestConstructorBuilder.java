/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.builder.ConstructorBuilder;
import us.pserver.tools.compile.impl.ParameterImpl;
import us.pserver.tools.compile.impl.SuperConstructorImpl;
import us.pserver.tools.compile.impl.VarConsumer;


/**
 *
 * @author Juno
 */
public class TestConstructorBuilder {
  
  @Test
  public void test_from_constructor() {
    SuperConstructorImpl sup = new SuperConstructorImpl(Arrays.asList(
        new ParameterImpl(Collections.EMPTY_LIST, String.class, "name"), 
        new ParameterImpl(Collections.EMPTY_LIST, String.class, "lastName"),
        new ParameterImpl(Collections.EMPTY_LIST, LocalDate.class, "birth")
    ));
    VarConsumer cs = os->System.out.println(Arrays.toString(os));
    System.out.println(new ConstructorBuilder<>()
        .from(Reflect.of(PersonDef.class).selectConstructor(String.class, String.class, LocalDate.class).constructor().get())
        .setModifiers(Modifier.PUBLIC)
        .setParametersConsumer(cs)
        .build());
  }
  
}
