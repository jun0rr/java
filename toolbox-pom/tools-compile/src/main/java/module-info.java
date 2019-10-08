/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module toolbox.tools.compile {
  requires java.base;
  requires java.compiler;
  requires transitive toolbox.tools;
  exports us.pserver.tools.compile;
  exports us.pserver.tools.compile.impl;
}
