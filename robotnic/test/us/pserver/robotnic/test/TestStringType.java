/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import us.pserver.robotnic.Robotnic;


/**
 *
 * @author Juno
 */
public class TestStringType {
  
  //O Cão roubou o Açai da "Moça Fenômeno"™ que possuia o "Relatório de Previsão de Avanço Tenológico"©?!
  
  public static void main(String[] args) {
    Robotnic r = Robotnic.getDefault();
    r.delay(2000);
    r.stringType("O Cão roubou o Açai da \"Moça Fenômeno\"™ que possuia o \"Relatório de Previsão de Avanço Tenológico\"©?!");
  }
  
}
