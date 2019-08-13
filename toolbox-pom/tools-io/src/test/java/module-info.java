/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module toolbox.tools.io.test {
  requires java.base;
  requires toolbox.tools;
  requires toolbox.tools.io;
  requires tinylog.api;
  requires tinylog.impl;
  opens us.pserver.tools.io to toolbox.tools;
}
