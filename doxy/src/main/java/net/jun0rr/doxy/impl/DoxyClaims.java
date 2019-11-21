/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.impl;


/**
 *
 * @author juno
 */
public abstract class DoxyClaims {
  
  public static final String AUDIENCE_SERVER = "doxy-server";
  
  public static final String ISSUER_CLIENT = "doxy-client";
  
  public static final String SUBJECT_AUTH = "channel-auth";
  
  public static final String CLAIM_CHANNEL_ID = "channel-id";
  
  public static final String CLAIM_REMOTE_HOST = "remote-host";
  
  public static final float EXPIRATION_MINUTES = 24 * 60;
  
  private DoxyClaims() {
    throw new IllegalStateException();
  }
  
}
