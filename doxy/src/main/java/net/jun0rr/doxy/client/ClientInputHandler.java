/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.client;

import java.io.EOFException;
import java.io.IOException;
import java.util.Objects;
import net.jun0rr.doxy.DoxyChannel;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class ClientInputHandler implements Runnable {
  
  private final DoxyChannel channel;
  
  public ClientInputHandler(DoxyChannel ch) {
    this.channel = Objects.requireNonNull(ch, "Bad null DoxyChannel (ch)");
  }
  
  @Override
  public void run() {
    try(channel) {
      while(true) {
        channel.readPacket().ifPresent(channel.environment().http()::send);
      }
    }
    catch(EOFException o) {
      //No more data available, channel will be closed.
    }
    catch(IOException e) {
      throw Unchecked.unchecked(e);
    }
  }
  
}
