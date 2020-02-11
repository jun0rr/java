/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import java.util.Optional;


/**
 *
 * @author Juno
 */
public interface MessageContainer {
  
  public <T> Optional<T> message();
  
  public Optional<? extends MessageContainer> withMessage(Object msg);
  
  public Optional<? extends MessageContainer> noMessage();
  
  public Optional<? extends MessageContainer> forward();
  
  public Optional<? extends MessageContainer> empty();
  
}
