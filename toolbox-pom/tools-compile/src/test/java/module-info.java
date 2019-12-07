/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module toolbox.tools.compile.test {
  requires java.base;
  requires java.compiler;
  requires toolbox.tools;
  requires transitive toolbox.tools.compile;
  requires org.junit.jupiter.api;
  requires tinylog.api;
  requires tinylog.impl;
  requires java.net.http;
}
