/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjectbox;


/**
 *
 * @author juno
 */
public class Message {
  
  private String msg;
  
  public Message(String message) {
    this.msg = message;
  }
  
  public String getMessage() {
    return this.msg;
  }
  
  public Message setMessage(String message) {
    this.msg = message;
    return this;
  }
  
  public Message setMessage(String message, boolean upper) {
    this.msg = (upper ? message.toUpperCase() : message);
    return this;
  }
  
  public Message say() {
    System.out.println("--> "+ msg+ "!");
    return this;
  }

}