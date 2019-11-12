/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Juno
 */
public interface A {
  
  public <T> List<T> sort(List<? super String> l);
  
  
  public static class B implements A {

    private final List<? super Number> nums;
    
    public B() {
      List<Integer> l = new ArrayList<Integer>();
      nums = this.<Integer>sort(l);
    }
    
    public <T> List<T> sort(List<String> l) {
      return (List<T>)l;
    }
    
  }
  
}
