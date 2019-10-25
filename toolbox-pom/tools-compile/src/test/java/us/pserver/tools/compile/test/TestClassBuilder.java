/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import us.pserver.tools.compile.builder.ClassBuilder;
import us.pserver.tools.compile.impl.FieldImpl;
import us.pserver.tools.compile.impl.GetterMethodImpl;
import us.pserver.tools.compile.impl.ParameterFieldInitializer;


/**
 *
 * @author Juno
 */
public class TestClassBuilder {
  
  @Test
  public void method() {
    try {
      ClassBuilder bld = new ClassBuilder("us.pserver.tools.compile.test.Person1");
      bld.newField()
          .setName("name")
          .setType(String.class)
          .setMods(Modifier.PRIVATE | Modifier.FINAL)
          .buildStep();
      bld.newField()
          .setName("lastname")
          .setType(String.class)
          .setMods(Modifier.PRIVATE | Modifier.FINAL)
          .buildStep();
      bld.newField()
          .setName("birth")
          .setType(LocalDate.class)
          .setMods(Modifier.PRIVATE | Modifier.FINAL)
          .buildStep();
      bld.newConstructor()
          .addParameter(String.class, "name")
          .addParameter(String.class, "lastname")
          .addParameter(LocalDate.class, "birth")
          .addInitializer(new ParameterFieldInitializer("name"))
          .addInitializer(new ParameterFieldInitializer("lastname"))
          .addInitializer(new ParameterFieldInitializer("birth"))
          .setModifiers(Modifier.PUBLIC)
          .buildStep();
      bld.addMethod(new GetterMethodImpl(Collections.EMPTY_LIST, (FieldImpl)bld.getFields().get(0), Modifier.PUBLIC));
      bld.addMethod(new GetterMethodImpl(Collections.EMPTY_LIST, (FieldImpl)bld.getFields().get(1), Modifier.PUBLIC));
      bld.addMethod(new GetterMethodImpl(Collections.EMPTY_LIST, (FieldImpl)bld.getFields().get(2), Modifier.PUBLIC));
      bld.implement(IPerson.class);
      System.out.println(bld.build().getSourceCode());
    }
    catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
