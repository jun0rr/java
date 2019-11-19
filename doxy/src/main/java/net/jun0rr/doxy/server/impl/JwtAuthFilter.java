/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.impl;

import java.util.Objects;
import java.util.Optional;
import net.jun0rr.doxy.DoxyConfig;
import net.jun0rr.doxy.server.HttpExchange;
import net.jun0rr.doxy.server.HttpHandler;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;


/**
 *
 * @author Juno
 */
public class JwtAuthFilter implements HttpHandler {
  
  private final DoxyConfig config;
  
  private final JwtConsumer consumer;
  
  public JwtAuthFilter(DoxyConfig cfg) {
    this.config = NonNullArgument.require(cfg, "cfg", getClass());
    this.consumer = new JwtConsumerBuilder()
        .setJweAlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, KeyManagementAlgorithmIdentifiers.RSA_OAEP_256)
        .setJweContentEncryptionAlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, ContentEncryptionAlgorithmIdentifiers.AES_256_CBC_HMAC_SHA_512)
        .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256)
        //.setVerificationKey(loadPublicKey(Paths.get("d:/java/doxy-pub.der")))
        //.setDecryptionKey(loadPrivateKey(Paths.get("d:/java/doxy-pk.der")))
        .setRequireExpirationTime()
        .setRequireIssuedAt()
        .setExpectedIssuer("doxy-client")
        .setExpectedSubject("channel-auth")
        .setExpectedAudience("doxy-server")
        .build();
  }

  @Override
  public Optional<HttpExchange> handle(HttpExchange he) throws Exception {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
