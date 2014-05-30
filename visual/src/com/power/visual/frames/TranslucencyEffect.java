/*
  Direitos Autorais Reservados (c) 2011 Juno Roesler
  Contato: juno.rr@gmail.com
  
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

package com.power.visual.frames;

import com.sun.awt.AWTUtilities;
import java.awt.Frame;
import java.awt.Window;

/**
 * Efeito visual que aplica transpar�ncia �s
 * janelas adicionadas, atrav�s da classe 
 * <code>com.sun.awt.AWTUtilities</code>.
 * @see com.power.visual.AbstractWindowEffect
 * @author Juno Roesler - juno.rr@gmail.com
 */
public class TranslucencyEffect extends AbstractWindowEffect {

	private float translucency;
	
	
	/**
	 * Construtor padr�o.
	 */
	public TranslucencyEffect() {
		super();
		this.translucency = 1f;
	}
	
	
	@Override
	public void addWindow(Window w) {
		if(w == null) return;
		
		if(w instanceof Frame
				&& !((Frame)w).isUndecorated())
			throw new IllegalArgumentException(
					"Frame must be Undecorated");
		
		super.addWindow(w);
	}
	
	
	/**
	 * Define o percentual de transpar�ncia a
	 * ser aplicado � janela.
	 * @param f Percentual de transpar�ncia, sendo
	 * <code>1.0</code> opaco e <code>0.0</code>
	 * totalmente transparente.
	 */
	public void setTranslucency(float f) {
		if(f >= 0)
			this.translucency = f;
	}
	
	
	/**
	 * Retorna o percentual de transpar�ncia
	 * aplicado � janela.
	 * @return percentual de transpar�ncia.
	 */
	public float getTranslucency() {
		return this.translucency;
	}
	

	@Override
	public void applyEffect() {
		for(int i = 0; i < windows.size(); i++) {
			Window w = windows.get(i);
			AWTUtilities.setWindowOpacity(w, translucency);
			w.repaint();
		}
	}


	@Override
	public void removeEffect() {
		for(int i = 0; i < windows.size(); i++) {
			Window w = windows.get(i);
			AWTUtilities.setWindowOpacity(w, 1f);
			w.repaint();
		}
	}

}
