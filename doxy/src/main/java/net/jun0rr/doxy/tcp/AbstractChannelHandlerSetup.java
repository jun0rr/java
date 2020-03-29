/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.tcp;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 *
 * @author Juno
 */
public abstract class AbstractChannelHandlerSetup<H extends ChannelHandler> implements ChannelHandlerSetup<H> {

  private final List<Supplier<H>> handlers;
  
  private final List<Supplier<Consumer<TcpChannel>>> connectHandlers;
  
  private Optional<SSLHandlerFactory> sslHandlerFactory;
  
  protected AbstractChannelHandlerSetup() {
    this.handlers = new LinkedList<>();
    this.connectHandlers = new LinkedList<>();
    this.sslHandlerFactory = Optional.empty();
  }
  
  @Override
  public ChannelHandlerSetup<H> addMessageHandler(Supplier<H> sup) {
    if(sup != null) {
      this.handlers.add(sup);
    }
    return this;
  }
  
  @Override
  public ChannelHandlerSetup<H> addConnectHandler(Supplier<Consumer<TcpChannel>> sup) {
    if(sup != null) {
      this.connectHandlers.add(sup);
    }
    return this;
  }
  
  @Override
  public ChannelHandlerSetup<H> enableSSL(SSLHandlerFactory shf) {
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
  public List<Supplier<Consumer<TcpChannel>>> connectHandlers() {
    return connectHandlers;
  }
  
}
