/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module doxy {
  requires java.base;
  requires java.net.http;
  requires toolbox.tools;
  requires toolbox.tools.io;
  requires io.netty.all;
  requires org.jose4j;
  exports net.jun0rr.doxy;
  exports net.jun0rr.doxy.impl;
  exports net.jun0rr.doxy.client;
  exports net.jun0rr.doxy.server.ex;
  exports net.jun0rr.doxy.server.http;
}
