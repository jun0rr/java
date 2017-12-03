/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca � software livre; voc� pode redistribu�-la e/ou modific�-la sob os
 * termos da Licen�a P�blica Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a vers�o 2.1 da Licen�a, ou qualquer
 * vers�o posterior.
 * 
 * Esta biblioteca � distribu�da na expectativa de que seja �til, por�m, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia impl�cita de COMERCIABILIDADE
 * OU ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a Licen�a P�blica
 * Geral Menor do GNU para mais detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor do GNU junto
 * com esta biblioteca; se n�o, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endere�o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 */

package us.pserver.tools;

import java.util.Random;
import java.util.function.Function;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 10/10/2017
 */
public class RandomString {

  public static final char[] VOWELS = {
    'a', 'e', 'i', 'o', 'u', 'y'
  };
  
  public static final char[] CONSONANTS = {
    'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'
  };
  
  
  public static enum StringCase {
    LOWER(s->s), 
    UPPER(String::toUpperCase), 
    FIRST_UPPER(s->s.substring(0, 1).toUpperCase().concat(s.substring(1)));
    
    private final Function<String,String> fncase;
    
    private StringCase(Function<String,String> fncase) {
      this.fncase = fncase;
    }
    
    public String applyCase(String str) {
      return fncase.apply(str);
    }
    
  }
  
  
  private final StringCase scase;
  
  private final int size;
  
  private final Random rdm;
  
  
  public RandomString(int size, StringCase scase) {
    if(size < 1) throw new IllegalArgumentException("Bad String size (< 1)");
    this.size = size;
    this.scase = NotNull.of(scase).getOrFail("Bad null StringCase");
    this.rdm = new Random(System.currentTimeMillis());
  }
  
  
  public RandomString(int size) {
    this(size, StringCase.LOWER);
  }
  
  
  public String generate() {
    StringBuilder str = new StringBuilder();
    boolean isVowel = true;
    for(int i = 0; i < size; i++) {
      if(isVowel) {
        int x = rdm.nextInt(VOWELS.length);
        str.append(VOWELS[x]);
      }
      else {
        int x = rdm.nextInt(CONSONANTS.length);
        str.append(CONSONANTS[x]);
      }
      isVowel = !isVowel;
    }
    return scase.applyCase(str.toString());
  }
  
  
  public static RandomString of(int size) {
    return new RandomString(size);
  }
  
  
  public static RandomString of(int size, StringCase scase) {
    return new RandomString(size, scase);
  }
  
}
