/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.cfg;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import us.pserver.tools.StringPad;


/**
 *
 * @author Juno
 */
public interface SecurityConfig {
  
  public Path getKeystorePath();
  
  public char[] getKeystorePassword();
  
  public Path getPrivateKeyPath();
  
  public Path getPublicKeyPath();
  
  public String getCryptAlgorithm();
  
  
  
  public static SecurityConfig of(Path privateKey, Path publicKey, Path keystore, char[] keystorePassword, String cryptAlg) {
    return new SecurityConfigImpl(privateKey, publicKey, keystore, keystorePassword, cryptAlg);
  }
  
  
  
  
  
  public static class SecurityConfigImpl implements SecurityConfig {
    
    private final Path kspath;
    
    private final char[] kspass;
    
    private final Path pkpath;
    
    private final Path pubpath;
    
    private final String cryptAlg;
    
    public SecurityConfigImpl(Path pkpath, Path pubpath, Path kspath, char[] kspass, String cryptAlg) {
      this.pkpath = Objects.requireNonNull(pkpath, "Bad null private key Path");
      this.pubpath = Objects.requireNonNull(pubpath, "Bad null public key Path");
      this.kspath = Objects.requireNonNull(kspath, "Bad null KeyStore Path");
      this.kspass = Objects.requireNonNull(kspass, "Bad null KeyStore password");
      this.cryptAlg = Objects.requireNonNull(cryptAlg, "Bad null cryptography algorithm");
    } 
    
    @Override
    public Path getKeystorePath() {
      return kspath;
    }
    
    @Override
    public char[] getKeystorePassword() {
      return kspass;
    }
    
    @Override
    public Path getPrivateKeyPath() {
      return pkpath;
    }
    
    @Override
    public Path getPublicKeyPath() {
      return pubpath;
    }
    
    @Override
    public String getCryptAlgorithm() {
      return cryptAlg;
    }
    
    @Override
    public int hashCode() {
      int hash = 7;
      hash = 47 * hash + Objects.hashCode(this.kspath);
      hash = 47 * hash + Arrays.hashCode(this.kspass);
      hash = 47 * hash + Objects.hashCode(this.pkpath);
      hash = 47 * hash + Objects.hashCode(this.pubpath);
      hash = 47 * hash + Objects.hashCode(this.cryptAlg);
      return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final SecurityConfig other = (SecurityConfig) obj;
      if (!Objects.equals(this.kspath, other.getKeystorePath())) {
        return false;
      }
      if (!Arrays.equals(this.kspass, other.getKeystorePassword())) {
        return false;
      }
      if (!Objects.equals(this.pkpath, other.getPrivateKeyPath())) {
        return false;
      }
      if (!Objects.equals(this.cryptAlg, other.getCryptAlgorithm())) {
        return false;
      }
      return Objects.equals(this.pubpath, other.getPublicKeyPath());
    }
    
    @Override
    public String toString() {
      return "SecurityConfig{" + "private=" + pkpath + ", public=" + pubpath + ", keystore=" + kspath + ", pass=" + StringPad.of("").lpad("*", kspass.length) + ", cryptAlg=" + cryptAlg + '}';
    }
    
  }
  
}
