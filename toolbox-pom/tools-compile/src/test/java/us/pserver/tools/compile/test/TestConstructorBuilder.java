/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import us.pserver.tools.compile.builder.ConstructorBuilder;
import us.pserver.tools.compile.impl.ParameterFieldInitializer;
import us.pserver.tools.compile.impl.SuperConstructorImpl;


/**
 *
 * @author Juno
 */
public class TestConstructorBuilder {
  
  @Test
  public void test_from_constructor() {
    ConstructorBuilder bld = new ConstructorBuilder<>("Person")
        .addParameter(String.class, "firstName")
        .addParameter(String.class, "lastName")
        .addParameter(LocalDate.class, "birth")
        .addInitializer(new ParameterFieldInitializer("firstName"))
        .addInitializer(new ParameterFieldInitializer("lastName"))
        .addInitializer(new ParameterFieldInitializer("birth"));
    System.out.println(
        bld.setSuperConstructor(new SuperConstructorImpl(bld.getParameters()))
        .setModifiers(Modifier.PUBLIC)
        .build().getSourceCode()
    );
  }
  
}
