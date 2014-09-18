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

import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

/**
 * Classe que estende javax.swing.JFrame, provendo
 * funcionalidades "plug and play" para efeitos visuais,
 * sem a necessidade de altera��o de c�digo.
 * @author Juno Roesler - juno.rr@gmail.com
 * @see <a href="http://download.oracle.com/javase/6/docs/api/javax/swing/JFrame.html">javax.swing.JFrame</a>
 */
public class PluggableEffectFrame extends JFrame {
	
	private List<WindowEffect> effects;
	
	
	/**
	 * Construtor padr�o sem argumentos.
	 */
	public PluggableEffectFrame() {
		effects = new LinkedList<WindowEffect>();
	}
	
	
	/**
	 * Construtor que recebe o t�tulo da janela.
	 * @param title T�tulo a ser exibido na janela.
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/javax/swing/JFrame.html">javax.swing.JFrame</a>
	 */
	public PluggableEffectFrame(String title) {
		super(title);
		effects = new LinkedList<WindowEffect>();
	}
	
	
	/**
	 * Adiciona um efeito visual � janela.
	 * @param e Efeito visual.
	 * @see com.power.visual.WindowEffect
	 */
	public void addEffect(WindowEffect e) {
		if(e == null) return;
		if(!effects.isEmpty()) {
			WindowEffect i = null;
			while((i = this.searchIncompatibleEffect(e)) != null)
				this.removeEffect(i);
		}
		e.addWindow(this);
		effects.add(e);
	}
	
	
	/**
	 * Remove um efeito visual da janela.
	 * @param e Efeito visual.
	 * @return <code>true</code> se WindowEffect
	 * foi removido com sucesso, <code>false</code>
	 * caso contr�rio.
	 * @see com.power.visual.WindowEffect
	 */
	public boolean removeEffect(WindowEffect e) {
		return effects.remove(e);
	}
	
	
	/**
	 * Retorna a quantidade de efeitos visuais
	 * adicionados � janela.
	 * @return Quantidade de efeitos adicionados.
	 */
	public int numEffects() {
		return effects.size();
	}
	
	
	/**
	 * Remove todos os efeitos visuais da janela.
	 */
	public void clearEffects() {
		effects.clear();
	}
	
	
	/**
	 * Retorna um array contendo todos os
	 * efeitos visuais da janela.
	 * @return <code>WindowEffect array</code>.
	 */
	public WindowEffect[] getEffects() {
		WindowEffect[] efs = new WindowEffect[effects.size()];
		return effects.toArray(efs);
	}
	
	
	/**
	 * Procura se existe algum efeito adicionado incompat�vel
	 * com o efeito informado (e.g. ReflectionEffect / ShadowEffect).
	 * @param e WindowEffect
	 * @return WindowEffect incompat�vel, ou <code>null</code>, caso 
	 * n�o exista.
	 */
	public WindowEffect searchIncompatibleEffect(WindowEffect e) {
		if(e == null || effects.isEmpty()) return null;
		
		if(e instanceof ReflectionEffect)
			return this.serchShadowEffect();
		else if(e instanceof ShadowEffect)
			return this.serchReflectionEffect();
		else return null;
	}
	
	
	/**
	 * Procura e retorna a primeira inst�ncia de 
	 * <code>ReflectionEffect</code>
	 * @return ReflectionEffect.
	 */
	private ReflectionEffect serchReflectionEffect() {
		if(effects.isEmpty()) return null;
		for(int i = 0; i < effects.size(); i++) {
			if(effects.get(i) instanceof ReflectionEffect)
				return (ReflectionEffect) effects.get(i);
		}
		return null;
	}

	
	/**
	 * Procura e retorna a primeira inst�ncia de 
	 * <code>ShadowEffect</code>
	 * @return ReflectionEffect.
	 */
	private ShadowEffect serchShadowEffect() {
		if(effects.isEmpty()) return null;
		for(int i = 0; i < effects.size(); i++) {
			if(effects.get(i) instanceof ShadowEffect)
				return (ShadowEffect) effects.get(i);
		}
		return null;
	}
	
	
	/**
	 * M�todo para tornar vis�vel a janela
	 * (deve ser utilizado no lugar de <code>setVisible(boolean)</code>, 
	 * aplicando todos os efeitos visuais adicionados. 
	 */
	public void open() {
		boolean isFader = false;
		if(!effects.isEmpty()) {
			for(WindowEffect e : effects) {
				if(e instanceof FaderEffect) {
					isFader = true;
          e.applyEffect();
        }
			}
		}
		if(!isFader) this.setVisible(true);
	}
  
  
  public void setVisible(boolean b) {
    super.setVisible(b);
		if(!effects.isEmpty()) {
			for(WindowEffect e : effects) {
        if(!(e instanceof FaderEffect))
          e.applyEffect();
			}
		}
  }
	

	/**
	 * M�todo para fechar a janela (deve ser utilizado
	 * no lugar de <code>dispose() ou setVisible(boolean)</code>, 
	 * removendo todos os efeitos visuais adicionados. 
	 */
	public void close() {
		boolean isFader = false;
		if(!effects.isEmpty()) {
			for(WindowEffect e : effects) {
				e.removeEffect();
				if(e instanceof FaderEffect)
					isFader = true;
			}
		}
		if(!isFader) this.dispose();
	}
	
}
