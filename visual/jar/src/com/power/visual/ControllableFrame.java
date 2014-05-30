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

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Interface que representa uma uma janela
 * a ser controlada pela class FrameController.
 * O �nico m�todo adicional � uma inst�ncia de
 * java.awt.Frame que deve ser implementado �
 * <code>getTitleBarArea():java.awt.Shape</code>
 * que deve retornar a �rea (e.g. java.awt.Rectangle)
 * correspondente � barra de t�tulo.
 * @see com.power.visual.FrameController
 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
 * @author Juno Roesler - juno.rr@gmail.com
 */
public interface ControllableFrame {

	/**
	 * Retorna a �rea correspondente � barra
	 * de t�tulo da janela.
	 * @return java.awt.Shape
	 */
	public Shape getTitleBarArea();
	
	
	//java.awt.Frame required methods
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public void addMouseListener(MouseListener l);
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public void addMouseMotionListener(MouseMotionListener l);
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public void addComponentListener(ComponentListener l);
	
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public int getWidth();
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public int getHeight();
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public Cursor getCursor();
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public void repaint();
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public void setCursor(Cursor c);
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public void setSize(int width, int height);
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public Dimension getSize();
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public Point getLocation();
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public void setLocation(int x, int y);
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public boolean isResizable();
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public int getExtendedState();
	
	/**
	 * See <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Frame.html">java.awt.Frame</a>
	 */
	public void setExtendedState(int state);
	
}
