/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;

import java.util.Objects;
import net.jun0rr.doxy.DoxyEnvironment;


/**
 *
 * @author Juno
 */
public abstract class AbstractRunnable implements Runnable {
  
  protected final DoxyEnvironment env;
  
  protected volatile boolean running;
  
  public AbstractRunnable(DoxyEnvironment env) {
    this.env = Objects.requireNonNull(env, "Bad null DoxyEnvironment (env)");
    this.running = false;
  }
  
  public void start() {
    this.running = true;
    env.executor().submit(this);
  }
  
  public boolean isRunning() {
    return running;
  }
  
  public void stop() {
    this.running = false;
  }
  
}
