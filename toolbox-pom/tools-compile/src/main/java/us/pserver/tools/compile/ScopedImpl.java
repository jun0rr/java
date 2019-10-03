/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile;

import java.util.Objects;


/**
 *
 * @author juno
 */
public abstract class ScopedImpl extends Annotated implements SourceCode {
  
  protected final Scope scope;
  
  public ScopedImpl(Scope s) {
    super();
    this.scope = Objects.requireNonNull(s);
  }
  
  public Scope getScope() {
    return scope;
  }
  
}
