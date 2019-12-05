/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.IntFunction;
import net.jun0rr.doxy.cfg.DoxyConfig;
import net.jun0rr.doxy.client.HttpPacketRequest;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public interface DoxyEnvironment {
  
  public DoxyConfig configuration();
  
  public ExecutorService executor();
  
  public BlockingDeque<Packet> inbox();
  
  public BlockingDeque<Packet> outbox();
  
  public List<DoxyChannel> channels();
  
  public Optional<DoxyChannel> getChannelById(String id);
  
  public HttpPacketRequest http();
  
  public ByteBuffer alloc();
  
  public ByteBuffer alloc(int size);
  
  public PublicKey getPublicKey();
  
  public PrivateKey getPrivateKey();
  
  
  
  public static DoxyEnvironment of(DoxyConfig cfg) {
    return new DoxyEnvironmentImpl(cfg);
  }
  
  
  
  
  
  public class DoxyEnvironmentImpl implements DoxyEnvironment {

    private final DoxyConfig config;

    private final ExecutorService exec;

    private final BlockingDeque<Packet> inbox;

    private final BlockingDeque<Packet> outbox;

    private final List<DoxyChannel> channels;

    private final HttpPacketRequest http;

    private final PublicKey pub;

    private final PrivateKey pk;

    private final IntFunction<ByteBuffer> alloc;

    public DoxyEnvironmentImpl(DoxyConfig cfg) {
      this.config = Objects.requireNonNull(cfg, "Bad null DoxyConfig");
      this.exec = Executors.newCachedThreadPool();
      this.inbox = new LinkedBlockingDeque<>();
      this.outbox = new LinkedBlockingDeque<>();
      this.channels = new CopyOnWriteArrayList<>();
      this.http = new HttpPacketRequest(this);
      this.pub = Unchecked.call(()->DerKeyFactory.loadPublicKey(config.getSecurityConfig().getPublicKeyPath()));
      this.pk = Unchecked.call(()->DerKeyFactory.loadPrivateKey(config.getSecurityConfig().getPrivateKeyPath()));
      this.alloc = config.isDirectBuffer() ? ByteBuffer::allocateDirect : ByteBuffer::allocate;
    }

    @Override
    public DoxyConfig configuration() {
      return config;
    }

    @Override
    public ExecutorService executor() {
      return exec;
    }

    @Override
    public BlockingDeque<Packet> inbox() {
      return inbox;
    }

    @Override
    public BlockingDeque<Packet> outbox() {
      return outbox;
    }

    @Override
    public List<DoxyChannel> channels() {
      return channels;
    }

    @Override
    public Optional<DoxyChannel> getChannelById(String id) {
      return channels.stream().filter(c->c.uid().equals(id)).findAny();
    }

    @Override
    public HttpPacketRequest http() {
      return http;
    }

    @Override
    public ByteBuffer alloc() {
      return alloc.apply(config.getBufferSize());
    }

    @Override
    public ByteBuffer alloc(int size) {
      return alloc.apply(size);
    }

    @Override
    public PublicKey getPublicKey() {
      return pub;
    }

    @Override
    public PrivateKey getPrivateKey() {
      return pk;
    }

  }

}
