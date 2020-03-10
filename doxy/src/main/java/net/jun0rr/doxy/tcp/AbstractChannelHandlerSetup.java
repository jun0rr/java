/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


/**
 *
 * @author Juno
 */
public abstract class AbstractChannelHandlerSetup<I,H> implements ChannelHandlerSetup<I,H> {

  private final List<Supplier<H>> handlers;
  
  private final List<Supplier<H>> connectHandlers;
  
  private Optional<SSLHandlerFactory> sslHandlerFactory;
  
  protected AbstractChannelHandlerSetup() {
    this.handlers = new LinkedList<>();
    this.connectHandlers = new LinkedList<>();
    this.sslHandlerFactory = Optional.empty();
  }
  
  @Override
  public ChannelHandlerSetup<I,H> addMessageHandler(Supplier<H> sup) {
    if(sup != null) {
      this.handlers.add(sup);
    }
    return this;
  }
  
  @Override
  public ChannelHandlerSetup<I,H> addConnectHandler(Supplier<H> sup) {
    if(sup != null) {
      this.connectHandlers.add(sup);
    }
    return this;
  }
  
  @Override
  public ChannelHandlerSetup<I,H> enableSSL(SSLHandlerFactory shf) {
    this.sslHandlerFactory = Optional.ofNullable(shf);
    return this;
  }
  
  @Override
  public Optional<SSLHandlerFactory> disableSSL() {
    final Optional<SSLHandlerFactory> ret = this.sslHandlerFactory;
    this.sslHandlerFactory = Optional.empty();
    return ret;
  }
  
  @Override
  public Optional<SSLHandlerFactory> sslHandlerFactory() {
    return this.sslHandlerFactory;
  }
  
  @Override
  public List<Supplier<H>> messageHandlers() {
    return handlers;
  }
  
  @Override
  public List<Supplier<H>> connectHandlers() {
    return connectHandlers;
  }
  
}
