/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.impl;

import java.lang.reflect.Modifier;


/**
 *
 * @author juno
 */
public enum Scope {
  PRIVATE, 
  PROTECTED, 
  PUBLIC;
  
  public static Scope forMods(int mods) {
    if(Modifier.isPublic(mods)) {
      return PUBLIC;
    }
    else if(Modifier.isProtected(mods)) {
      return PROTECTED;
    }
    else return PRIVATE;
  }
  
}
