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
 * Classe abstrata que implementa uma lista
 * de nodos conectados entre si.
 * @author Juno Roesler
 */
public abstract class AbstractList {

  private Node head, tail, pointer;

  private int size;


  /**
   * Construtor protegido padr�o.
   */
  protected AbstractList() {
    head = tail = pointer = null;
    size = 0;
  }//method()


  /**
   * Retorna o primeiro nodo da lista.
   * @return Primeiro nodo.
   */
  public Node getHead() {
    return head;
  }//method()


  /**
   * Seta o primeiro nodo da lista.
   * @param head Primeiro nodo.
   */
  public void setHead(Node head) {
    this.head = head;
  }//method()


  /**
   * Retorna o �ltimo nodo da lista.
   * @return �ltimo nodo.
   */
  public Node getTail() {//{{{
    return tail;
  }//method()}}}



  /**
   * Seta o �ltimo nodo da lista.
   * @param tail �ltimo nodo.
   */
  public void setTail(Node tail) {
//{{{
    this.tail = tail;
  }//method()
//}}}


  /**
   * Retorna o n�mero de nodos existentes
   * na lista.
   * @return N�mero de nodos existentes.
   */
  public int size() {
    return size;
  }//method()


  /**
   * Retorna o pr�ximo nodo da lista.
   * @return Pr�ximo nodo da lista.
   */
  public Node next() {
    if(pointer == null) {
      pointer = head;
      return null;
    }//if

    Node n = pointer;
    pointer = pointer.next();
    return n;
  }//method()


  /**
   * Retorna o nodo anterior da lista.
   * @return Nodo anterior.
   */
  public Node prev() {
    if(pointer == null)
      return null;

    pointer = pointer.prev();

    if(pointer != null)
      return pointer;
    else {
      pointer = tail;
      return null;
    }//if-else
  }//method()


  /**
   * Retorna o objeto armazenado pelo
   * pr�ximo nodo da lista.
   * @return O objeto armazenado.
   */
  public Object nextObject() {
    Node n = this.next();
    if(n != null)
      return n.getObject();
    else
      return null;
  }//method()


  /**
   * Indica se existem mais nodos na lista.
   * @return <code>boolean</code> indicando
   * se existem mais nodos na lista ou n�o.
   */
  public boolean hasMoreObjects() {
    if(pointer != null)
      return true;
    else {
      resetPointer();
      return false;
    }//else
  }//method()


  /**
   * Reseta o ponteiro do nodo atual,
   * indicando o primeiro elemento da
   * lista.
   */
  public void resetPointer() {
    pointer = head;
  }//method()


  /**
   * Adiciona um objeto � lista.
   * @param obj Objeto a ser adicionado.
   */
  public void add(Object obj) {
    if(head == null) {
      head = new Node(obj);
      tail = pointer = head;
    } else {
      tail = new Node(tail, obj, null);
      tail.prev().setNext(tail);
    }//if-else
    size++;
  }//method()


  /**
   * Verifica se o objeto indicado existe
   * na lista, atrav�s de <code>obj.equals(Object)</code>.
   * @param obj Objeto a ser verificado.
   * @return <code>boolean</code> indicando
   * se a lista cont�m o objeto indicado.
   */
  public boolean contains(Object obj) {
    if(this.containsObject(obj) != null)
      return true;
    return false;
  }//method()


  /**
   * Verifica se o objeto indicado existe
   * na lista, atrav�s de <code>obj.equals(Object)</code>,
   * instanciando um <code>Integer</code> com o �ndice
   * dele no segundo par�metro.
   * @param obj Objeto a ser verificado.
   * @return Nodo que cont�m o objeto indicado, ou
   * <code>null</code> caso n�o seja encontrado na
   * lista.
   */
  public Node containsObject(Object obj) {
    if(size == 0)
      return null;

    this.resetPointer();
    while(this.hasMoreObjects()) {
      Node n = this.next();
      if(n.getObject() == null && obj == null)
        return n;
      else if(n.getObject().equals(obj))
        return n;
    }//while
    return null;
  }//method()


  /**
   * Pesquisa e retorna o �ndice do objeto
   * na lista.
   * @param obj Objeto a ser pesquisado.
   * @return O �ndice do objeto na lista, ou
   * <code>-1</code> se este for <code>null</code>
   * ou n�o existir.
   */
  public int indexOf(Object obj) {
    int value = -1;
    this.resetPointer();
    while(this.hasMoreObjects()) {
      value++;
      Object o = this.nextObject();
      if(o == null && obj == null)
        return value;
      else if(o.equals(obj))
        return value;
    }//while
    return value;
  }//method()


  /**
   * Retorna o objeto contido
   * no �ndice informado da lista.
   * @param index �ndice do objeto.
   * @return O objeto contido no �ndice.
   */
  public Object get(int index) {
    if(index < 0 || index > this.size())
      return null;

    this.resetPointer();
    Object o = null;
    for(int i = 0; i <= index; i++) {
      o = this.nextObject();
    }//for
    return o;
  }//method()


  /**
   * Remove o nodo da lista.
   * @param n Nodo a ser removido.
   * @return <code>boolean</code> indicando
   * o se o nodo foi removido com sucesso.
   */
  public boolean remove(Node n) {
    if(size == 0)
      return false;

    while(this.hasMoreObjects()) {
      Node node = this.next();
      if(node == n) {
        node.prev().setNext(node.next());
        size--;
        return true;
      }//if
    }//while

    return false;
  }//method()


  /**
   * Remove o nodo indicado da lista sem
   * verificar se este encontra-se na lista.
   */
  private boolean directRemove(Node n) {
    if(n == null)
      return false;
    n.prev().setNext(n.next());
    n = null;
    size--;
    return true;
  }//method()


  /**
   * Remove o objeto indicado da lista.
   * @param obj Objeto a ser removido.
   * @return <code>boolean</code> indicando
   * se o objeto foi removido com sucesso.
   */
  public boolean remove(Object obj) {
    return this.directRemove(this.containsObject(obj));
  }//method()


  /**
   * Elimina todos os elementos armazenados.
   */
  public void clear() {
    head = tail = pointer = null;
    size = 0;
  }//method()


}//class
