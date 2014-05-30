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

package com.power.visual.frames;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Juno Roesler - juno.rr@gmail.com
 * @version 1.0 - 29/01/2014
 */
public class MyDialog extends JDialog {

  private FaderEffect fe;
  
  
  public MyDialog() {
    super((JFrame)null, "MyDialog", true);
    this.setLocationRelativeTo(null);
    this.setUndecorated(true);
    this.setSize(300, 200);
    this.add(new JLabel("MyDialog"));
    fe = new FaderEffect();
    fe.addWindow(this);
  }
  
  
  public void open() {
    fe.applyEffect();
  }
  
  
  public void close() {
    fe.removeEffect();
  }
  
  
  public static void main(String[] args) throws InterruptedException {
    MyDialog md = new MyDialog();
    md.open();
    Thread.sleep(5000);
    md.close();
  }
  
}
