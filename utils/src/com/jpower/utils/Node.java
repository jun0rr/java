/*
  Direitos Autorais Reservados (c) 2009 Juno Roesler
  Contato com o autor: powernet.de@gmail.com

  Esta biblioteca � software livre; voc� pode redistribu�-la e/ou modific�-la sob os
  termos da Licen�a P�blica Geral Menor do GNU conforme publicada pela Free
  Software Foundation; tanto a vers�o 2.1 da Licen�a, ou (a seu crit�rio) qualquer
  vers�o posterior.

  Esta biblioteca � distribu�do na expectativa de que seja �til, por�m, SEM
  NENHUMA GARANTIA; nem mesmo a garantia impl�cita de COMERCIABILIDADE
  OU ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a Licen�a P�blica
  Geral Menor do GNU para mais detalhes.

  Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor do GNU junto
  com esta biblioteca; se n�o, escreva para a Free Software Foundation, Inc., no
  endere�o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
*/

package com.jpower.utils;


/**
 * Node representa um nodo de uma lista
 * lincada e encapsula um objeto a ser
 * armazenado.
 * @author Juno Roesler
 */
public class Node {

  private Node prev, next;

  private Object object;


  /**
   * Construtor que recebe um objeto
   * @param obj Objeto a ser armazenado.
   */
  public Node(Object obj) {
    prev = next = null;
    this.object = obj;
  }//DescNode


  /**
   * Construtor que recebe o nodo anterior,
   * o objeto a ser armazenado e o pr�ximo nodo
   * da lista.
   * @param prev Nodo anterior.
   * @param obj Objeto a ser armazenado.
   * @param next Pr�ximo nodo.
   */
  public Node(Node prev, Object obj, Node next) {
    this.prev = prev;
    this.object = obj;
    this.next = next;
  }//DescNode


  /**
   * Retorna o objeto armazenado.
   * @return <code>Object</code> armazenado.
   */
  public Object getObject() {
    return object;
  }//getDescription


  /**
   * Retorna o nodo anterior.
   * @return Nodo anterior.
   */
  public Node prev() {
    return prev;
  }//prev


  /**
   * Rtorna o pr�ximo nodo da lista.
   * @return Pr�ximo nodo.
   */
  public Node next() {
    return next;
  }//next


  /**
   * Seta o objeto a ser encapsulada.
   * @param obj Objeto a ser armazenado.
   */
  public void setObject(Object obj) {
    this.object = obj;
  }//setDesc


  /**
   * Seta o nodo anterior.
   * @param prev Nodo anterior.
   */
  public void setPrev(Node prev) {
    this.prev = prev;
  }//setPrev


  /**
   * Seta o pr�ximo nodo da lista.
   * @param next Pr�ximo nodo.
   */
  public void setNext(Node next) {
    this.next = next;
  }//setNext


}//class
