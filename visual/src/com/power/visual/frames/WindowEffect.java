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

import java.awt.Window;

/**
 * Interface que deve ser implementada por
 * efeitos visuais aplicados � janelas.
 * @author Juno Roesler - juno.rr@gmail.com
 */
public interface WindowEffect {
	
	/**
	 * Aplica o efeito visual �(s) janela(s)
	 * adicionada(s).
	 */
	public void applyEffect();
	
	/**
	 * Remove o efeito visual da(s) janela(s)
	 * adicionada(s).
	 */
	public void removeEffect();
	
	/**
	 * Adiciona uma janela � qual o efeito
	 * dever� ser aplicado.
	 * @param w java.awt.Window a ser adicionada.
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Window.html">java.awt.Window</a>
	 */
	public void addWindow(Window w);
	
	/**
	 * Retorna todas as inst�ncias de Window
	 * adicionadas ao efeito.
	 * @return java.awt.Window array.
	 */
	public Window[] getWindows();
	
	/**
	 * Retorna a quantidade de janelas
	 * adicionadas ao efeito.
	 * @return Quantidade de janelas adicionadas.
	 */
	public int numWindows();
	
	/**
	 * Remove a janela especificada do efeito.
	 * @param w java.awt.Window a ser removida.
	 * @return <code>true</code> se a janela foi
	 * removida com sucesso, <code>false</code>
	 * caso contr�rio.
	 */
	public boolean removeWindow(Window w);
	
	/**
	 * Remove todas as janelas adicionadas ao efeito.
	 */
	public void clearWindows();

}
