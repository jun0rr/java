/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 *
 * @author juno
 */
public class ScriptCombo implements Script {
  
  private final Set<Script> actions;
  
  public ScriptCombo() {
    this.actions = new HashSet<>();
  }
  
  public ScriptCombo(Script... ss) {
    this();
    Arrays.asList(ss).forEach(this::add);
  }
  
  public ScriptCombo add(Script s) {
    if(s != null) {
      actions.add(s);
    }
    return this;
  }
  
  public boolean contains(Script s) {
    return actions.contains(s);
  }
  
  public boolean remove(Script s) {
    return actions.remove(s);
  }
  
  @Override
  public void exec(Robotnic r) throws ScriptException {
    actions.forEach(s -> s.exec(r));
  }


  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(this.actions);
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ScriptCombo other = (ScriptCombo) obj;
    if (!Objects.equals(this.actions, other.actions)) {
      return false;
    }
    return true;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Combo[");
    if(!actions.isEmpty()) {
      actions.forEach(s -> sb.append(s).append("+"));
      sb.deleteCharAt(sb.length() -1);
    }
    return sb.append("]").toString();
  }
  
}
