/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 *
 * @author juno
 */
public class TestMods {
  
  @Test
  public void test_modifiers() {
    int mods = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;
    Assertions.assertTrue(Modifier.isPublic(mods));
    Assertions.assertTrue(Modifier.isStatic(mods));
    Assertions.assertTrue(Modifier.isFinal(mods));
  }
  
}
