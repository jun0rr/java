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
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import javax.swing.JWindow;

/**
 * Implementa o efeito visual de sombra abaixo e
 * � direita da janela.
 * @author Juno Roesler - juno.rr@gmail.com
 * @see com.power.visual.AbstractWindowEffect
 * @see com.power.visual.WindowEffect
 */
public class ShadowEffect extends AbstractWindowEffect
		implements ComponentListener, WindowListener {
		
	/**
	 * Percentual padr�o de transpar�ncia da sombra (0.5).
	 */
	public static final float DEFAULT_OPACITY = 0.5f;
		
	/**
	 * Tamanho padr�o da sombra em pixels (6).
	 */
	public static final int DEFAULT_SHADOW_WIDTH = 5;
	
	/**
	 * Dist�ncia do in�cio de �ngulo de arredondamento da borda da sombra (10).
	 */
	public static final int ANGLE_WIDTH = 10;
	
	/**
	 * Dist�ncia do canto da borda pela qual passa a curva de arredondamento (1).
	 */
	public static final int ANGLE_CTRL = 1;
	
	/**
	 * Tamanho da parte da sombra que ficar� sob o componente sombreado, em pixels (2).
	 */
	public static final int SHADOW_SIZE_UNDER_COMPONENT = 2;
	
	
	private int angleWidth, angleCtrl, shwUndComp;
	
	private float opacity;
		
	private int shwWidth;
	
	private boolean roundedCorner;
	
	private GeneralPath shadowShape;
	
	private Window src;
	
	private JWindow shadow;
		
	
	/**
	 * Construtor padr�o.
	 * @see com.power.visual.AbstractWindowEffect
	 */
	public ShadowEffect() {
		super();
		this.setOpacity(DEFAULT_OPACITY);
		this.setShadowWidth(DEFAULT_SHADOW_WIDTH);
		angleWidth = ANGLE_WIDTH;
		angleCtrl = ANGLE_CTRL;
		shwUndComp = SHADOW_SIZE_UNDER_COMPONENT;
	}
		
	
	/**
	 * Retorna a inst�ncia de java.awt.Window que 
	 * exibe o efeito de sombra da janela.
	 * @return java.awt.Window
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Window.html">java.awt.Window</a>
	 */
	public Window getEffectContainer() {
		return shadow;
	}
	

	@Override	
	public void addWindow(Window w) {
		src = w;
		shadow = new JWindow(src) {
			@Override
			public void paint(Graphics g) {
				draw(g);
			}
		};
		src.addComponentListener(this);
		src.addWindowListener(this);
	}

	
	/**
	 * Retorna o percentual de transpar�ncia da sombra.
	 * @return percentual de transpar�ncia entre <code>0.0 e 1.0</code>.
	 */
	public float getOpacity() {
		return opacity;
	}

		
	/**
	 * Define o percentual de transpar�ncia da sombra.
	 * @param opacity percentual de transpar�ncia entre <code>0.0 e 1.0</code>.
	 */
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}


	/**
	 * Retorna a espessura da sombra em pixels.
	 * @return espessura da sombra em pixels.
	 */
	public int getShadowWidth() {
		return shwWidth;
	}
	
	
	/**
	 * Retorna a dist�ncia antes de come�ar o �ngulo
	 * de curvatura da forma da sombra, que ir� 
	 * influenciar no tamanho da curva.
	 * @return dist�ncia antes do �ngulo
	 * de curvatura.
	 */
	public int getAngleWidth() {
		return angleWidth;
	}
	
	
	/**
	 * Define a dist�ncia antes de come�ar o �ngulo
	 * de curvatura da forma da sombra, que ir� 
	 * influenciar no tamanho da curva.
	 * @param angle dist�ncia antes do �ngulo
	 * de curvatura.
	 */
	public void setAngleWidth(int angle) {
		if(angle >= 0) angleWidth = angle;
	}

	
	/**
	 * Retorna a dist�ncia do canto da janela,
	 * por onde passar� a curvatura da forma
	 * da sombra, influenciando no tamanho da
	 * curvatura.
	 * @return dist�ncia do canto da janela,
	 * por onde passa a curvatura.
	 */
	public int getAngleCtrl() {
		return angleCtrl;
	}
	
	
	/**
	 * Define a dist�ncia do canto da janela,
	 * por onde passar� a curvatura da forma
	 * da sombra, influenciando no tamanho da
	 * curvatura.
	 * @param angle dist�ncia do canto da janela,
	 * por onde passa a curvatura.
	 */
	public void setAngleCtrl(int angle) {
		if(angle >= 0) angleCtrl = angle;
	}

		
	/**
	 * Define a espessura da sombra em pixels, que deve ser 
	 * alguns pixels maior (2 pixels por padr�o) do que a �rea a ser exibida, 
	 * definido pelo m�todo 
	 * <code>setShadowSizeUnderComponent(int)</code>.
	 * Esses pixels adicionais ficam por baixo da
	 * janela original, para ajuste visual do efeito.
	 * @param width espessura da sombra em pixels.
	 * @see com.power.visual.frames.ShadowEffect#setShadowSizeUnderComponent(int) 
	 */
	public void setShadowWidth(int width) {
		this.shwWidth = width;
	}
	
	
	/**
	 * Retorna o tamanho da parte da sombra que 
	 * ficar� sob o componente sombreado, em pixels.
	 * @return tamanho da parte sob o componente.
	 */
	public int getShadowSizeUnderComponent() {
		return shwUndComp;
	}
	
	
	/**
	 * Define o tamanho da parte da sombra que 
	 * ficar� sob o componente sombreado, em pixels.
	 * @param size tamanho da parte sob o componente.
	 */
	public void setShadowSizeUnderComponent(int size) {
		if(size >= 0) shwUndComp = size;
	}
	
	
	/**
	 * Retorna <code>true</code> se a sombra ser�
	 * desenhada com a bordas arrendadas, <code>false</code>
	 * caso contr�rio.
	 * @return <code>true</code> se a sombra ser�
	 * desenhada com a bordas arrendadas, <code>false</code>
	 * caso contr�rio.
	 */
	public boolean isRoundedCorner() {
		return roundedCorner;
	}
	
	
	/**
	 * Define se as bordas da sombra devem possuir 
	 * os cantos arredondados ou n�o.
	 * @param rounded <code>true</code> para bordas da sombra com
	 * cantos arredondados, <code>false</code> caso contr�rio. 
	 * @return A inst�ncia modificada de ShadowEffect.
	 */
	public ShadowEffect setRoundedCorner(boolean rounded) {
		roundedCorner = rounded;
		return this;
	}
	
	
	/**
	 * Cria o contorno da sombra a ser desenhada.
	 */
	private void createShadowShape() {
		shadowShape = new GeneralPath();
		shadowShape.moveTo(shadow.getWidth() - shwWidth, 0);
		if(roundedCorner)
			this.createRoundedShadowShape();
		else {
			shadowShape.lineTo(shadow.getWidth() - shwWidth, 
					shadow.getHeight() - shwWidth);
			shadowShape.lineTo(0, shadow.getHeight() - shwWidth);
			shadowShape.lineTo(0, shadow.getHeight());
			shadowShape.lineTo(shadow.getWidth(), shadow.getHeight());
			shadowShape.lineTo(shadow.getWidth(), 0);
			shadowShape.closePath();
		}
	}
	
	
	/**
	 * Cria o contorno da sombra a ser desenhada com 
	 * cantos arredondados da borda.
	 */
	private void createRoundedShadowShape() {
		shadowShape.lineTo(shadow.getWidth() - shwWidth, 
				shadow.getHeight() - shwWidth - angleWidth);
		shadowShape.quadTo(shadow.getWidth() - shwWidth - angleCtrl, 
				shadow.getHeight() - shwWidth - angleCtrl, 
				shadow.getWidth() - shwWidth - angleWidth, 
				shadow.getHeight() - shwWidth);
		shadowShape.lineTo(0, shadow.getHeight() - shwWidth);
		shadowShape.quadTo(angleWidth / 2, shadow.getHeight() - angleCtrl, 
				angleWidth, shadow.getHeight());
		shadowShape.lineTo(shadow.getWidth() - angleWidth, 
				shadow.getHeight());
		shadowShape.quadTo(shadow.getWidth() - angleCtrl, 
				shadow.getHeight() - angleCtrl, shadow.getWidth(), 
				shadow.getHeight() - angleWidth);
		shadowShape.lineTo(shadow.getWidth(), angleWidth);
		shadowShape.quadTo(shadow.getWidth() - angleCtrl, angleWidth / 2, 
				shadow.getWidth() - shwWidth, 0);
	}
		
	
	/**
	 * Desenha a sombra.
	 * @param g java.awt.Graphics
	 */
	protected void draw(Graphics g) {
		if(AWTUtilities.isWindowOpaque(shadow))
			AWTUtilities.setWindowOpaque(shadow, false);
		
		this.createShadowShape();

		BufferedImage buf = new BufferedImage(shadow.getWidth(), 
				shadow.getHeight(), BufferedImage.TRANSLUCENT);
		
		Graphics2D g2 = buf.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(20, 20, 20, (int)(255 * opacity)));
		g2.fill(shadowShape);
		g2.dispose();
		
		g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, shadow.getWidth(), shadow.getHeight());
		g2.setComposite(AlphaComposite.Src);
		g.drawImage(buf, 0, 0, null);
	}
		
	
	@Override
	public void applyEffect() {
		if(src == null || !src.isVisible()) return;
		componentShown(null);
		shadow.setVisible(true);
	}


	@Override
	public void removeEffect() {
		if(src == null) return;
		shadow.dispose();
	}


	@Override
	public void componentResized(ComponentEvent e) {
		componentShown(null);
	}


	@Override
	public void componentMoved(ComponentEvent e) {
		componentShown(null);
	}


	@Override
	public void componentShown(ComponentEvent e) {
		shadow.setSize(src.getWidth() - shwWidth,
				src.getHeight() - shwWidth);
		shadow.setLocation(src.getLocation().x + shwWidth * 2 - shwUndComp, 
				src.getLocation().y + shwWidth * 2 - shwUndComp);
		if(shadow.isVisible())
			shadow.repaint();
	}


	@Override
	public void windowClosing(WindowEvent e) {
		this.removeEffect();
	}


	@Override
	public void windowIconified(WindowEvent e) {
		this.removeEffect();
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		this.applyEffect();
	}


	@Override	public void componentHidden(ComponentEvent e) {}
	@Override	public void windowOpened(WindowEvent e) {}
	@Override	public void windowClosed(WindowEvent e) {}
	@Override	public void windowActivated(WindowEvent e) {}
	@Override	public void windowDeactivated(WindowEvent e) {}
		
}
