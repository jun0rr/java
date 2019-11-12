/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class TestSocketConnection {
  
  private static final ExecutorService exec = Executors.newFixedThreadPool(2);
  
  private static final CountDownLatch _server = new CountDownLatch(1);
  
  private static final CountDownLatch _client = new CountDownLatch(1);
  
  private static final CountDownLatch _threads = new CountDownLatch(2);
  
  @Test
  public void server() {
    //exec.submit(() -> {
      ServerSocketChannel server = null;
      SocketChannel ch = null;
      try {
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("localhost", 3333));
        System.out.printf("[SERVER] Listening at: %s%n", server.getLocalAddress());
        _server.countDown();
        ch = server.accept();
        System.out.printf("[SERVER] Connected: %s->%s%n", ch.getLocalAddress(), ch.getRemoteAddress());
        System.out.printf("[SERVER] channel.isOpen().............: %s%n", ch.isOpen());
        System.out.printf("[SERVER] channel.isBlocking().........: %s%n", ch.isBlocking());
        System.out.printf("[SERVER] channel.isConnectionPending(): %s%n", ch.isConnectionPending());
        System.out.printf("[SERVER] channel.isConnected()........: %s%n", ch.isConnected());
      }
      catch(Exception e) {
        throw Unchecked.unchecked(e);
      }
      finally {
        try {
          ch.close();
          ch.close();
          server.close();
          server.close();
          Thread.sleep(1000);
          _client.countDown();
          _threads.countDown();
        }
        catch(Exception e) {
          throw Unchecked.unchecked(e);
        }
      }
    //});
    Unchecked.call(()->_threads.await());
  }
  
  @Test
  public void client() {
    exec.submit(() -> {
      try(SocketChannel ch = SocketChannel.open()) {
        System.out.printf("[CLIENT] Waiting for server...%n");
        _server.await();
        ch.connect(new InetSocketAddress("localhost", 3333));
        System.out.printf("[CLIENT] Connected: %s->%s%n", ch.getLocalAddress(), ch.getRemoteAddress());
        System.out.printf("[CLIENT] Waiting for close...%n");
        _client.await();
        System.out.printf("[CLIENT] channel.isOpen().............: %s%n", ch.isOpen());
        System.out.printf("[CLIENT] channel.isBlocking().........: %s%n", ch.isBlocking());
        System.out.printf("[CLIENT] channel.isConnectionPending(): %s%n", ch.isConnectionPending());
        System.out.printf("[CLIENT] channel.isConnected()........: %s%n", ch.isConnected());
        System.out.printf("[CLIENT] channel.read()...............: %d%n", ch.read(ByteBuffer.allocate(10)));
      }
      catch(Exception e) {
        throw Unchecked.unchecked(e);
      }
      finally {
        _threads.countDown();
      }
    });
    //Unchecked.call(()->_threads.await());
  }
  
}
