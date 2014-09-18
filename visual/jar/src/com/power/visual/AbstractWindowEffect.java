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

package com.power.visual;

import com.sun.awt.AWTUtilities;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe abstrata que implementa as funcionalidades
 * comuns aos efeitos visuais WindowEffect.
 * @see com.power.visual.WindowEffect
 * @author Juno Roesler - juno.rr@gmail.com
 */
public abstract class AbstractWindowEffect implements WindowEffect {

	/**
	 * Lista para armazenar as janelas controladas pelo efeito visual.
	 */
	protected List<Window> windows;
	
	
	/**
	 * Construtor protegido que deve ser referenciado
	 * pelas sub classes. Verifica se o ambiente
	 * gr�fico possui suporte � transpar�ncia, 
	 * lan�ando uma exce��o caso n�o possua.
	 * @throws java.lang.IllegalStateException
	 */
	protected AbstractWindowEffect() {
		if(!isTranslucencyCapable())
			throw new IllegalStateException(
					"GraphicsEnvironment is not translucency capable.");
		windows = new LinkedList<Window>();
	}
	

	/**
	 * Verifica e retorna se o ambiente gr�fico possui
	 * suporte � transpar�ncia.
	 * @return <code>true</code> caso possua suporte 
	 * � transpar�ncia, <code>false</code> caso contr�rio.
	 */
	public static boolean isTranslucencyCapable() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsConfiguration conf = device.getDefaultConfiguration();
		return AWTUtilities.isTranslucencySupported(
				AWTUtilities.Translucency.PERPIXEL_TRANSLUCENT)
			&& AWTUtilities.isTranslucencyCapable(conf);
	}


	@Override
	public void addWindow(Window w) {
		if(w == null) return;
		windows.add(w);
	}


	@Override
	public Window[] getWindows() {
		if(windows.isEmpty()) return null;
		Window[] ws = new Window[windows.size()];
		return windows.toArray(ws);
	}


	@Override
	public int numWindows() {
		return windows.size();
	}


	@Override
	public boolean removeWindow(Window w) {
		return windows.remove(w);
	}


	@Override
	public void clearWindows() {
		windows.clear();
	}

}
