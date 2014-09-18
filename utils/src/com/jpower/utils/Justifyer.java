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
 * Justifyer � um justificador de texto.
 * @author Juno Roesler
 */
public class Justifyer {

  public static final String endl = "\n", spc = " ", codln = "&&&";


  /**
   * Construtor �nico e sem argumentos.
   */
  public Justifyer() {
  }//construtor


  /**
   * Justifica uma linha de texto com o n�mero de
   * colunas menor do o n�mero especificado por
   * <code>cols</code>.
   */
  private String justifyLesser(int cols, String text) {
    if(text == null || text.length() > cols)
      return null;

    int nspc, ispc;
    ispc = text.lastIndexOf(spc);

    if( (text.length() == cols || ispc < 0)
        || text.endsWith(".") )
      return text;

    StringBuffer buf = new StringBuffer();

    nspc = cols - text.length();
    buf.append(text);

    int inicounter = 0;
    for(int i = 0; i < nspc; i++) {
      buf.insert(ispc, spc);

      int ospc = buf.lastIndexOf(spc, ispc-1);
      if(ospc > 0)
        ispc = ospc;
      else {
        //reinicia o indice dos espa�os
        ispc = buf.lastIndexOf(spc);
        inicounter++;
        //volta o indice at� o inicio da
        //�ltima sequencia de espa�os.
        for(int j = 0; j < inicounter; j++)
          ispc = buf.lastIndexOf(spc, ispc);
      }//else
    }//for

    return buf.toString();
  }//method


  /**
   * Justifica um texto formatando-o de acordo com o
   * n�mero de colunas especificado (inserindo quebras
   * de linha).
   */
  private String justifyGreater(int cols, String text, StringBuffer buf) {
    if( (cols <= 0 || text == null) ||
        text.length() <= cols)
      return null;

    if(buf == null)
      buf = new StringBuffer();

    int ispc = text.lastIndexOf(spc, cols);
    int iendl = text.lastIndexOf(endl, cols);

    if(iendl > 0)
      ispc = iendl;

    if(ispc > 0) {
      buf.append(
        justifyLesser(cols,
          text.substring(0, ispc)));

      buf.append(endl);

      text = text.substring(ispc+1);
    } else {
      buf.append(
        text.substring(0, cols));
      buf.append(endl);

      text = text.substring(cols+1);
    }//if-else

    if(text != null && text.length() > cols)
      justifyGreater(cols, text, buf);
    else if(text != null && text.length() > 0)
      buf.append(
        justifyLesser(cols, text));

    return buf.toString();
  }//method


  /**
   * Justifica um texto ao n�mero de colunas indicado.
   * Caso o texto tenha tamanho menor que o n�mero de
   * colunas indicado, ser�o inseridos espa�os para
   * atingir o n�mero de colunas. Caso seja maior,
   * ser�o inseridas quebras de linha para respeitar
   * o n�mero de colunas informado.
   * @param cols N�mero de colunas que
   * o testo deve ter.
   * @param text Texto a ser justificado.
   */
  public String justify(int cols, String text) {
    if(text == null || cols <= 0)
      return null;

    text = text.replaceAll(codln, endl);

    if(text.length() > cols)
      return justifyGreater(cols, text, null);
    else
      return justifyLesser(cols, text);
  }//method


}//class
