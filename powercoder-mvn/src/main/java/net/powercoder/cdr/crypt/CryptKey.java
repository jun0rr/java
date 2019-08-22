/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca é software livre; você pode redistribuí-la e/ou modificá-la sob os
 * termos da Licença Pública Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a versão 2.1 da Licença, ou qualquer
 * versão posterior.
 * 
 * Esta biblioteca é distribuída na expectativa de que seja útil, porém, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE
 * OU ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública
 * Geral Menor do GNU para mais detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral Menor do GNU junto
 * com esta biblioteca; se não, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endereço 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 *
*/

package net.powercoder.cdr.crypt;

import net.powercoder.cdr.digest.Digester;
import net.powercoder.cdr.crypt.iv.SecureRandomIV;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import net.powercoder.cdr.StringByteConverter;
import net.powercoder.cdr.crypt.iv.BasicIV;
import net.powercoder.cdr.crypt.iv.InitVector;
import net.powercoder.cdr.crypt.iv.UnsecurePasswordIV;
import net.powercoder.cdr.hex.HexByteCoder;


/**
 * Classe que representa uma chave de criptografia
 * com o respectivo algorítmo.
 * 
 * @author Juno Roesler - juno@pserver.us
 * @version 1.0 - 21/08/2013
 */
public class CryptKey {
  
  private transient SecretKeySpec spec;
  
  private InitVector iv;
  
  private byte[] hash;
  
  private CryptAlgorithm algorithm;
  
  
  /**
   * Cria uma chave vazia.
   */
  public CryptKey() {
    hash = null;
    iv = null;
    spec = null;
    algorithm = null;
  }
  
  
  /**
   * Construtor padrão que recebe a senha 
   * <code>String key</code> e o algorítmo de criptografia.
   * @param key Senha utilizada na criptografia.
   * @param algo Algorítmo de criptografia.
   */
  public CryptKey(String key, CryptAlgorithm algo) {
    this(key, new SecureRandomIV(algo), algo);
  }
  
  
  /**
   * Construtor padrão que recebe a senha 
   * <code>String key</code> e o algorítmo de criptografia.
   * @param key Senha utilizada na criptografia.
   * @param iv Initialization Vector Object needed for cryptography.
   * @param algo Algorítmo de criptografia.
   */
  public CryptKey(String key, InitVector iv, CryptAlgorithm algo) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(iv);
    Objects.requireNonNull(algo);
    this.iv = iv;
    spec = KeySpecFactory.createKey(
        CryptUtils.truncate(hash, algo.getBytesSize()), algo
    );
  }


  /**
   * Retorna a especificação da chave <code>SecretKeySpec</code>.
   * @return especificação da chave <code>SecretKeySpec</code>.
   */
  public SecretKeySpec getKeySpec() {
    if(spec == null && hash != null && algorithm != null)
      spec = KeySpecFactory.createKey(
          CryptUtils.truncate(hash, 
              algorithm.getBytesSize()), algorithm);
    return spec;
  }


  /**
   * Define a especificação da chave <code>SecretKeySpec</code>.
   * @param spec especificação da chave <code>SecretKeySpec</code>.
   */
  public void setSpec(SecretKeySpec spec) {
    this.spec = spec;
  }


  /**
   * Retorna o hash SHA-256 gerado a partir da senha.
   * @return Hash SHA-256.
   */
  public byte[] getHash() {
    return hash;
  }


  /**
   * Define o hash SHA-256 gerado a partir da senha.
   * @param hash Hash SHA-256.
   */
  public void setHash(byte[] hash) {
    this.hash = hash;
  }


  public InitVector getIV() {
    return iv;
  }


  public void setIV(InitVector iv) {
    this.iv = iv;
  }


  /**
   * Retorna o algorítmo de criptografia.
   * @return algorítmo de criptografia <code>CryptAlgorithm</code>.
   */
  public CryptAlgorithm getAlgorithm() {
    return algorithm;
  }


  /**
   * Define o algorítmo de criptografia.
   * @param algorithm algorítmo de criptografia 
   * <code>CryptAlgorithm</code>.
   */
  public void setAlgorithm(CryptAlgorithm algorithm) {
    this.algorithm = algorithm;
  }
  
  
  /**
   * Define uma chave vazia com o hash SHA-256 gerado a partir da senha,
   * initialization vector e o algorítmo de criptografia.
   * @param bs Hash SHA-256.
   * @param iv Initialization Vector Object needed for cryptography.
   * @param algo algorítmo de criptografia 
   * <code>CryptAlgorithm</code>.
   */
  public void setKey(byte[] hash, InitVector iv, CryptAlgorithm algo) {
    Objects.requireNonNull(hash);
    Objects.requireNonNull(iv);
    Objects.requireNonNull(algo);
    this.hash = hash;
    algorithm = algo;
    this.iv = iv;
    spec = KeySpecFactory.createKey(
        CryptUtils.truncate(hash, algo.getBytesSize()), algo
    );
  }
  
  
  @Override
  public String toString() {
    if(hash == null || iv == null || algorithm == null)
      return null;
    HexByteCoder cd = new HexByteCoder();
    StringByteConverter cv = new StringByteConverter();
    byte[] encAlgo = cd.encode(cv.convert(algorithm.toString()));
    byte[] encHash = cd.encode(hash);
    byte[] encIV = cd.encode(iv.getVector());
    return cv.reverse(encAlgo)
        + "||" + cv.reverse(encHash) 
        + "||" + cv.reverse(encIV);
  }
  
  
  public static CryptKey createWithUnsecurePasswordIV(String key, CryptAlgorithm algo) {
    return new CryptKey(key, new UnsecurePasswordIV(algo, key), algo);
  }
  
  
  public static CryptKey createWithSecureRandomIV(String key, CryptAlgorithm algo) {
    return new CryptKey(key, new UnsecurePasswordIV(algo, key), algo);
  }
  
  
  /**
   * Decodifica a <code>String</code> informada
   * no objeto <code>CryptKey</code> retornado, 
   * ou <code>null</code> caso a <code>String</code> 
   * não esteja no formato adequado.
   * @param str <code>CryptKey String</code>
   * @return <code>CryptKey</code> interpretada
   * a partir da <code>String</code> informada,
   * ou <code>null</code> em caso de erro.
   */
  public static CryptKey fromString(String str) {
    Objects.requireNonNull(str);
    String[] ss = str.split("\\|\\|");
    if(ss.length < 3) throw new IllegalArgumentException("Bad String key format: "+ str);
    HexByteCoder cd = new HexByteCoder();
    StringByteConverter cv = new StringByteConverter();
    byte[] algoBytes = cv.convert(ss[0]);
    algoBytes = cd.decode(algoBytes);
    CryptAlgorithm algo = CryptAlgorithm.fromString(cv.reverse(algoBytes));
    CryptKey k = new CryptKey();
    k.setKey(
        cd.decode(cv.convert(ss[1])), 
        new BasicIV(cd.decode(cv.convert(ss[2]))), 
        algo
    );
    return k;
  }
  
  
  /**
   * Cria um objeto <code>CryptKey</code> com o 
   * algorítmo informado, utilizando uma senha
   * gerada aleatoriamente.
   * @param algo Algorítmo de criptografia.
   * @return <code>CryptKey</code>.
   */
  public static CryptKey createRandomKey(CryptAlgorithm algo) {
    Objects.requireNonNull(algo);
    CryptKey key = new CryptKey();
    byte[] hash = Digester.toSHA256(
        CryptUtils.randomBytes(algo.getBytesSize())
    );
    key.setKey(hash, new SecureRandomIV(algo), algo);
    return key;
  }
  
  
  public static void main(String[] args) {
    CryptKey key = new CryptKey("123456", CryptAlgorithm.AES_CBC_256_PKCS5);
    System.out.println("* key.algo = "+ key.getAlgorithm().toString());
    System.out.println("* key.hash = "+ Arrays.toString(key.getHash()));
    System.out.println("* key.iv = "+ Arrays.toString(key.getIV().getVector()));
    System.out.println("* key = "+ key.toString());
    key = CryptKey.fromString(key.toString());
    System.out.println("* key = "+ key.toString());
    System.out.println("* key.algo = "+ key.getAlgorithm().toString());
    System.out.println("* key.hash = "+ Arrays.toString(key.getHash()));
    System.out.println("* key.iv = "+ Arrays.toString(key.getIV().getVector()));
  }
  
}