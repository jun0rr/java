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
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * PowerFrame � uma Janela da �rea de trabalho que 
 * herda as funcionalidades de javax.swing.JFrame, por�m 
 * implementa toda a parte visual, bordas arredondadas, 
 * bot�es barra de t�tulo com efeito gradiente, al�m de
 * efeitos de transpar�ncia e "fade in/out".
 * As funcionalidades de "desktop", como redimensionamento,
 * movimenta��o da janela e maximiza��o/minimiza��o 
 * s�o providas pela classe FrameController, com o 
 * intuito de prover comportamento igual � um JFrame padr�o.
 * 
 * @author Juno Roesler - juno.rr@gmail.com
 */
public class PowerFrame extends PluggableEffectFrame 
		implements MouseListener, MouseMotionListener, 
		ControllableFrame {
	
	/**
	 * Cor padr�o do contorno da janela (46, 70, 120).
	 * @see com.power.visual.PowerFrame#setPaintStyle(com.power.visual.PaintStyle) 
	 */
	public static final Color DEFAULT_HEADER_OUT_COLOR = new Color(46, 70, 120);
	
	/**
	 * Cor padr�o do interior da barra de t�tulo (165, 194, 228).
	 * @see com.power.visual.PowerFrame#setPaintStyle(com.power.visual.PaintStyle) 
	 */
	public static final Color DEFAULT_HEADER_IN_COLOR = new Color(165, 194, 228);
	
	/**
	 * Percentual padr�o de transpar�ncia da barra de t�tulo e bordas (0.92).
	 */
	public static final double DEFAULT_BORDER_TRANSLUCENCY = 0.92;

	/**
	 * Altura da barra de t�tulo em pixels (30)
	 */
	public static final int HEADER_HEIGHT = 30;
	
	/**
	 * Dist�ncia do in�cio do �ngulo de arredondamento da borda (10).
	 */
	public static final int ANGLE_WIDTH = 10;
	
	/**
	 * Dist�ncia do canto da borda pela qual passa a curva de arredondamento (1).
	 */
	public static final int ANGLE_CTRL = 1;

	/**
	 * Tamanho do �cone a ser desenhado na barra de t�tulo (24).
	 */
	public static final int ICON_SIZE = 24;
	
	/**
	 * Tamanho do X do bot�o de fechar da barra de t�tulo (8).
	 */
	public static final int X_SIZE = 8;
	
	/**
	 * Dist�ncia das bordas para c�lculo de java.awt.Insets (5).
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Insets.html">java.awt.Insets</a>
	 */
	public static final int INSET = 5;

	/**
	 * Tamanho do bot�o de fechar da barra de t�tulo (40, 20).
	 */
	public static final Dimension CLOSE_SIZE = new Dimension(40, 20);
	
	/**
	 * Fonte padr�o de desenho do t�tulo da janela ("Arial", Font.BOLD, 16).
	 */
	public static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 16);
	
	/**
	 * Cor padr�o de desenho do t�tulo da janela (Color.WHITE).
	 */
	public static final Color DEFAULT_FONT_COLOR = Color.WHITE;
	
	/**
	 * Cor padr�o de desenho do X do bot�o de fechar (200, 229, 255).
	 */
	public static final Color DEFAULT_X_COLOR = new Color(200, 229, 255);
	
	/**
	 * Cor padr�o de desenho quando o mouse estiver em cima do X do bot�o 
	 * de fechar (248, 248, 248).
	 */
	public static final Color DEFAULT_OVER_X_COLOR = new Color(248, 248, 248);
	
	/**
	 * Cor padr�o da parte superior do bot�o de fechar (184, 31, 5).
	 */
	public static final Color DEFAULT_CLOSE_TOP_COLOR = new Color(184, 31, 5);

	/**
	 * Cor padr�o da parte inferior do bot�o de fechar (239, 137, 119).
	 */
	public static final Color DEFAULT_CLOSE_BOTTON_COLOR = new Color(239, 137, 119);

	/**
	 * Tra�o padr�o de desenho da borda (2.5).
	 */
	public static final BasicStroke DEFAULT_STROKE = new BasicStroke(2.5f);


	private GeneralPath header, close, 
			closeArea, X, border, windowShape;

	private boolean overClose;
	
	private boolean fullRepaint;
	
	private BufferedImage buffer;
	
	private FrameController controller;
	
	private PaintStyle headerPaint;
	
	private PaintStyle closePaint;
	
	private double translucency;
	

	/**
	 * Construtor padr�o.
	 */
	public PowerFrame() {
		super();
		init();
	}


	/**
	 * Construtor que recebe o t�tulo da janela como par�metro.
	 * @param title T�tulo da janela.
	 */
	public PowerFrame(String title) {
		super(title);
		init();
	}
	
	
	/**
	 * Inicia todas as vari�veis e configura a janela.
	 */
	private void init() {
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		header = new GeneralPath();
		close = new GeneralPath();
		closeArea = new GeneralPath();
		X = new GeneralPath();
		border = new GeneralPath();
		buffer = null;

		headerPaint = new PaintStyle(DEFAULT_HEADER_OUT_COLOR, 
				DEFAULT_HEADER_IN_COLOR, DEFAULT_FONT_COLOR);
		
		closePaint = new PaintStyle(DEFAULT_CLOSE_TOP_COLOR,
				DEFAULT_CLOSE_BOTTON_COLOR, DEFAULT_OVER_X_COLOR);
		
		controller = new FrameController(this);
		
		overClose = false;
		fullRepaint = true;
		
		this.setFont(DEFAULT_FONT);
		this.setForeground(DEFAULT_FONT_COLOR);
		
		translucency = DEFAULT_BORDER_TRANSLUCENCY;
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}


	/**
	 * Retorna a �rea da barra de t�tulo.
	 * @return java.awt.Shape contendo a �rea da barra
	 * de t�tulo.
	 * @see com.power.visual.ControllableFrame
	 */
	@Override
	public Shape getTitleBarArea() {
		return header;
	}
	
	
	/**
	 * Define o percentual de transpar�ncia da borda e
	 * da barra de t�tulo.
	 * @param d Percentual de transpar�ncia.
	 */
	public void setBorderTranslucency(double d) {
		if(d <= 0) return;
		this.translucency = d;
		applyBorderTranslucency();
	}
	
	/**
	 * Retorna o percentual de transpar�ncia da borda e
	 * da barra de t�tulo.
	 * @return Percentual de transpar�ncia.
	 */
	public double getBorderTranslucency() {
		return this.translucency;
	}
	
	/**
	 * Define as cores de desenho do bot�o de fechar 
	 * quando o bot�o possuir o foco do mouse.
	 * @param ps Cores de desenho quando o bot�o possuir
	 * o foco do mouse
	 * @see com.power.visual.PaintStyle
	 */
	public void setHighlightCloseButtonColors(PaintStyle ps) {
		if(ps != null) closePaint = ps;
	}
	
	/**
	 * Retorna as cores de desenho do bot�o de fechar 
	 * quando o bot�o possuir o foco do mouse.
	 * @return Cores de desenho quando o bot�o possuir
	 * o foco do mouse
	 * @see com.power.visual.PaintStyle
	 */
	public PaintStyle getHighlightCloseButtonColors() {
		return closePaint;
	}
	
	/**
	 * Define as cores de desenho da barra de t�tulo,
	 * da fonte do t�tulo e das bordas 
	 * (<code>PaintStyle.setUpColor(), PaintStyle.setDownColor(), 
	 * PaintStyle.setFontColor()</code>).
	 * @param ps PaintStyle com as cores de desenho da 
	 * barra de t�tulo, da fonte do t�tulo e das bordas.
	 * @see com.power.visual.PaintStyle
	 */
	public void setPaintStyle(PaintStyle ps) {
		if(ps == null) return;
		headerPaint = ps;
		applyBorderTranslucency();
	}
	
	/**
	 * Retorna as cores de desenho da barra de t�tulo,
	 * da fonte do t�tulo e das bordas 
	 * (<code>PaintStyle.setUpColor(), PaintStyle.setDownColor(), 
	 * PaintStyle.setFontColor()</code>).
	 * @return PaintStyle com as cores de desenho da 
	 * barra de t�tulo, da fonte do t�tulo e das bordas.
	 * @see com.power.visual.PaintStyle
	 */
	public PaintStyle getPaintStyle() {
		return headerPaint;
	}
	
	
	/**
	 * Configura o formato e a transpar�ncia
	 * da janela.
	 * @see com.sun.awt.AWTUtilities.
	 */
	private void configureWindow() {
		if(!AbstractWindowEffect.isTranslucencyCapable()) return;
		this.createWindowPath();
		if(AWTUtilities.getWindowShape(this) != windowShape)
			AWTUtilities.setWindowShape(this, windowShape);
		if(AWTUtilities.isWindowOpaque(this))
			AWTUtilities.setWindowOpaque(this, false);
		applyBorderTranslucency();
	}
	
	
	/**
	 * Aplica transpar�ncia �s cores da 
	 * barra de t�tulo, bordas e do bot�o 
	 * de fechar.
	 */
	private void applyBorderTranslucency() {
		int r = headerPaint.getUpColor().getRed();
		int g = headerPaint.getUpColor().getGreen();
		int b = headerPaint.getUpColor().getBlue();
		headerPaint.setUpColor(new Color(r, g, b, (int)(255 * translucency)));
		
		r = headerPaint.getDownColor().getRed();
		g = headerPaint.getDownColor().getGreen();
		b = headerPaint.getDownColor().getBlue();
		headerPaint.setDownColor(new Color(r, g, b, (int)(255 * translucency)));
		
		r = this.closePaint.getUpColor().getRed();
		g = this.closePaint.getUpColor().getGreen();
		b = this.closePaint.getUpColor().getBlue();
		this.closePaint.setUpColor(new Color(r, g, b, (int)(255 * translucency)));
		
		r = this.closePaint.getDownColor().getRed();
		g = this.closePaint.getDownColor().getGreen();
		b = this.closePaint.getDownColor().getBlue();
		this.closePaint.setDownColor(new Color(r, g, b, (int)(255 * translucency)));
	}
	
	
	/**
	 * Cria e retorna uma imagem "buferizada" compat�vel com
	 * o tamanho da janela.
	 * @return BufferedImage para desenho da janela.
	 */
	public BufferedImage getBufferedImage() {
		if(!this.isVisible()) return null;
		if(buffer == null || (buffer != null
				&& (buffer.getWidth() != this.getWidth()
				||  buffer.getHeight() != this.getHeight()))) {
			buffer = new BufferedImage(this.getWidth(), this.getHeight(),
					BufferedImage.TRANSLUCENT);
			
			Graphics2D bg = buffer.createGraphics();
			bg.setComposite(AlphaComposite.Clear); 
			bg.fillRect(0, 0, this.getWidth(), this.getHeight());
			bg.dispose();
		}
		return buffer;
	}
	
	
	/**
	 * Define o par�metro de renderiza��o 
	 * ANTIALIAS_ON para o Graphics2D informado.
	 * @param g Graphics2D a ser aplicado o
	 * par�metro de renderiza��o.
	 */
	private void setAntialiasing(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}


	/**
	 * Cria o contorno do desenho da barra de t�tulo.
	 */
	private void createHeaderPath() {
		header.moveTo(0, HEADER_HEIGHT);
		header.lineTo(this.getWidth(), HEADER_HEIGHT);
		header.lineTo(this.getWidth(), ANGLE_WIDTH);
		header.quadTo(this.getWidth() -ANGLE_CTRL, ANGLE_CTRL, 
				this.getWidth() -ANGLE_WIDTH, 0);
		header.lineTo(ANGLE_WIDTH, 0);
		header.quadTo(ANGLE_CTRL, ANGLE_CTRL, 0, ANGLE_WIDTH);
		header.closePath();
	}
	
	
	/**
	 * Cria o contorno do desenho da janela.
	 */
	private void createWindowPath() {
		windowShape = new GeneralPath();
		windowShape.moveTo(0, ANGLE_WIDTH);
		windowShape.lineTo(0, this.getHeight() - ANGLE_WIDTH);
		windowShape.quadTo(ANGLE_CTRL, this.getHeight() - ANGLE_CTRL, 
				ANGLE_WIDTH, this.getHeight());
		windowShape.lineTo(this.getWidth() - ANGLE_WIDTH, this.getHeight());
		windowShape.quadTo(this.getWidth() - ANGLE_CTRL, this.getHeight() - ANGLE_CTRL, 
				this.getWidth(), this.getHeight() - ANGLE_WIDTH);
		windowShape.lineTo(this.getWidth(), ANGLE_WIDTH);
		windowShape.quadTo(this.getWidth() - ANGLE_CTRL, ANGLE_CTRL, 
				this.getWidth() - ANGLE_WIDTH, 0);
		windowShape.lineTo(ANGLE_WIDTH, 0);
		windowShape.quadTo(ANGLE_CTRL, ANGLE_CTRL, 0, ANGLE_WIDTH);
	}


	/**
	 * Desenha em buffer a barra de t�tulo da janela.
	 */
	private void drawHeader() {
		if(this.getBufferedImage() == null) return;
		
		this.createHeaderPath();
		
		Graphics2D bg = buffer.createGraphics();
		
		bg.setComposite(AlphaComposite.Src);
		this.setAntialiasing(bg);
		bg.setPaint(new GradientPaint(0, 0, headerPaint.getUpColor(), 
				0, HEADER_HEIGHT / 2f, headerPaint.getDownColor()));
		bg.fill(header);
		bg.setPaint(new GradientPaint(0, HEADER_HEIGHT / 2f, headerPaint.getDownColor(), 
				0, HEADER_HEIGHT, headerPaint.getUpColor()));
		bg.fillRect(0, HEADER_HEIGHT / 2, this.getWidth(), HEADER_HEIGHT / 2);
		bg.dispose();
	}
	
	
	/**
	 * Desenha em buffer o t�tulo da janela.
	 */
	private void drawTitle() {
		if(this.getBufferedImage() == null) return;
		if(this.getTitle() == null) return;
		
		Graphics2D bg = (Graphics2D) buffer.createGraphics();
		FontMetrics met = bg.getFontMetrics(this.getFont());
		
		int gap = HEADER_HEIGHT - ICON_SIZE;
		int x = gap * 3 + ICON_SIZE;
		int y = met.getHeight();
		
		this.setAntialiasing(bg);
		
		bg.setColor(this.getForeground());
		bg.setFont(this.getFont());
		bg.drawString(this.getTitle(), x, y);
		bg.dispose();
	}
	
	
	/**
	 * Desenha em buffer o �cone da janela.
	 */
	private void drawIcon() {
		if(this.getBufferedImage() == null) return;
		if(this.getIconImage() == null) return;
		
		Graphics2D bg = (Graphics2D) buffer.createGraphics();
		int xy = HEADER_HEIGHT - ICON_SIZE;
		bg.drawImage(this.getIconImage(), (int)(xy * 1.5), xy/2, ICON_SIZE, ICON_SIZE, null);
		bg.dispose();
	}
	
	
	/**
	 * Limpa o desenho do bot�o de fechar da janela.
	 */
	private void clearClose(Graphics2D g) {
		if(buffer == null || g == null) return;
		
		int x = this.getWidth() - X_SIZE;
		int y = HEADER_HEIGHT / 2;
		int t = CLOSE_SIZE.height;
		
		g.setComposite(AlphaComposite.Clear); 
		g.fill(closeArea);
		
		g.setComposite(AlphaComposite.Src);
		g.setPaint(new GradientPaint(x, 0, headerPaint.getUpColor(),
				x, y, headerPaint.getDownColor()));
		g.fill(closeArea);
		
		g.setPaint(new GradientPaint(x, y, headerPaint.getDownColor(),
				x, HEADER_HEIGHT, headerPaint.getUpColor()));
		g.fillRect(this.getWidth() - CLOSE_SIZE.width, 
				y, CLOSE_SIZE.width, t - y);
		g.dispose();
	}
	
	
	/**
	 * Cria o contorno do desenho do bot�o de fechar da janela.
	 */
	private void createClosePath() {
		close.moveTo(this.getWidth() - CLOSE_SIZE.width, 0);
		close.lineTo(this.getWidth() - CLOSE_SIZE.width, CLOSE_SIZE.height - ANGLE_WIDTH);
		close.quadTo(this.getWidth() - CLOSE_SIZE.width + ANGLE_CTRL, 
				CLOSE_SIZE.height - ANGLE_CTRL, 
				this.getWidth() - CLOSE_SIZE.width + ANGLE_WIDTH, 
				CLOSE_SIZE.height);
		close.lineTo(this.getWidth(), CLOSE_SIZE.height);
		
		closeArea.moveTo(this.getWidth() - CLOSE_SIZE.width, 0);
		closeArea.lineTo(this.getWidth() - CLOSE_SIZE.width, CLOSE_SIZE.height - ANGLE_WIDTH);
		closeArea.quadTo(this.getWidth() - CLOSE_SIZE.width + ANGLE_CTRL, 
				CLOSE_SIZE.height - ANGLE_CTRL, 
				this.getWidth() - CLOSE_SIZE.width + ANGLE_WIDTH, 
				CLOSE_SIZE.height);
		closeArea.lineTo(this.getWidth(), CLOSE_SIZE.height);
		closeArea.lineTo(this.getWidth(), ANGLE_WIDTH);
		closeArea.quadTo(this.getWidth() - ANGLE_CTRL, ANGLE_CTRL, 
				this.getWidth() - ANGLE_WIDTH, 0);
		closeArea.closePath();
	}
	
	
	/**
	 * Cria o contorno do desenho do X do bot�o de fechar.
	 */
	private void createXPath() {
		int x = this.getWidth() - ((CLOSE_SIZE.width - X_SIZE) / 2) - X_SIZE + 1;
		int y = (CLOSE_SIZE.height - X_SIZE) / 2;
		X.moveTo(x, y);
		X.lineTo(x + X_SIZE, y + X_SIZE);
		X.moveTo(x + X_SIZE, y);
		X.lineTo(x, y + X_SIZE);
	}
	
	
	/**
	 * Desenha em buffer o bot�o de fechar da janela.
	 */
	private void drawCloseButton() {
		if(this.getBufferedImage() == null) return;
		
		this.createClosePath();
		
		Graphics2D bg = (Graphics2D) buffer.createGraphics();
		this.clearClose((Graphics2D) bg.create());
		
		this.setAntialiasing(bg);
		bg.setColor(headerPaint.getFontColor());
		bg.draw(close);
		
		this.createXPath();
		
		bg.setColor(headerPaint.getFontColor());
		bg.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		bg.draw(X);
		bg.dispose();
	}
	
	
	/**
	 * Desenha em buffer o bot�o de fechar quando possuir 
	 * o foco do mouse.
	 */
	private void drawCloseOver() {
		if(this.getBufferedImage() == null) return;
		
		Graphics2D bg = (Graphics2D) buffer.createGraphics();
		this.clearClose((Graphics2D) bg.create());
		
		this.setAntialiasing(bg);
		bg.setPaint(new GradientPaint(
				this.getWidth() - ANGLE_WIDTH, 0,
				closePaint.getUpColor(), this.getWidth() - ANGLE_WIDTH,
				CLOSE_SIZE.height, closePaint.getDownColor()));
		bg.fill(closeArea);
		bg.setColor(headerPaint.getDownColor()); 
		bg.draw(close);
		
		bg.setColor(closePaint.getFontColor());
		bg.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		bg.draw(X);
		bg.dispose();
	}
	
	
	/**
	 * Cria o contorno da borda da janela.
	 */
	private void createBorderPath() {
		int width = this.getWidth() - 2;
		int height = this.getHeight() - 2;
		
		border.moveTo(1, HEADER_HEIGHT);
		border.lineTo(1, height - ANGLE_WIDTH);
		border.quadTo(ANGLE_CTRL, height - ANGLE_CTRL,
				ANGLE_WIDTH, height);
		border.lineTo(width - ANGLE_WIDTH, height);
		border.quadTo(width - ANGLE_CTRL, height - ANGLE_CTRL, 
				width, height - ANGLE_WIDTH);
		border.lineTo(width, HEADER_HEIGHT);
	}
	
	
	/**
	 * Desenha em buffer a borda da janela.
	 */
	private void drawBorder() {
		if(this.getBufferedImage() == null) return;
		
		this.createBorderPath();
		
		Graphics2D bg = buffer.createGraphics();
		this.setAntialiasing(bg);
		
		bg.setColor(new Color(238, 238, 238, (int)(255 * 0.7)));
		bg.fill(border);
		
		bg.setStroke(DEFAULT_STROKE);
		bg.setColor(headerPaint.getUpColor());
		bg.draw(border);
		bg.dispose();
	}
	
	
	/**
	 * Desenha a janela utilizando t�cnica de 
	 * desenho em buffer e depois desenhando a
	 * imagem na janela.
	 * @param g java.awt.Graphics
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Window.html#paint(java.awt.Graphics)">java.awt.Window#paint(java.awt.Graphics)</a>
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(fullRepaint) {
			buffer = null;
			this.getBufferedImage();
			this.resetPaths();
			
			this.configureWindow();
			
			this.drawHeader();
			this.drawIcon();
			this.drawTitle();
			this.drawCloseButton();
			this.drawBorder();
		} else {
			if(overClose)
				this.drawCloseOver();
			else
				this.drawCloseButton();
			fullRepaint = true;
		}
		
		g.drawImage(buffer, 0, 0, null);
		super.paintComponents(g);
	}
	
	
	/**
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/Container.html#getInsets()">java.awt.Container.html#getInsets()</a>
	 */
	@Override
	public Insets getInsets() {
		return new Insets(HEADER_HEIGHT + INSET / 2,
				INSET, INSET, INSET);
	}
	
	
	/**
	 * Redefine os contornos dos desenhos da janela.
	 */
	private void resetPaths() {
		X = new GeneralPath();
		border = new GeneralPath();
		close = new GeneralPath();
		closeArea = new GeneralPath();
		header = new GeneralPath();
	}
	
	
	/**
	 * Chama o m�todo <code>repaint()</code>, para
	 * pintar todo o conte�do novamente.
	 */
	private void fullRepaint() {
		fullRepaint = true;
		this.repaint();
	}
	
	
	/**
	 * "Ouve" quando o mouse � movido dentro da janela
	 * para pintar o bot�o de fechar adequadamente
	 * quando possuir ou n�o o foco do mouse.
	 * @param me java.awt.event.MouseEvent
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/event/MouseMotionListener.html">java.awt.event.MouseMotionListener</a>
	 */
	@Override
	public void mouseMoved(MouseEvent me) {
		//over close area
		if(!overClose &&
				closeArea.contains(me.getPoint())) {
			fullRepaint = false;
			overClose = true;
			this.repaint();
			
		//out close area
		} else if(overClose
				&& !closeArea.contains(me.getPoint())) {
			fullRepaint = false;
			overClose = false;
			this.repaint();
		}
	}


	/**
	 * "Ouve" quando o mouse � clicado dentro da janela
	 * para fechar a janela quando for o caso.
	 * @param e java.awt.event.MouseEvent
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/event/MouseEvent.html">java.awt.event.MouseEvent</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/event/MouseListener.html">java.awt.event.MouseListener</a>
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(closeArea.contains(e.getPoint())) {
			this.close();
		}
	}


	@Override	public void mousePressed(MouseEvent e) {}
	@Override	public void mouseReleased(MouseEvent e) {}
	@Override	public void mouseDragged(MouseEvent e) {}


	/**
	 * "Ouve" quando o mouse entra na �rea da janela
	 * para pintar o bot�o de fechar adequadamente
	 * quando possuir ou n�o o foco do mouse.
	 * @param e java.awt.event.MouseEvent
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/event/MouseEvent.html">java.awt.event.MouseEvent</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/event/MouseListener.html">java.awt.event.MouseListener</a>
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if(!overClose &&
				close.contains(e.getX(), e.getY())) {
			fullRepaint = false;
			overClose = true;
			this.repaint();
		}
	}


	/**
	 * "Ouve" quando o mouse sai da �rea da janela
	 * para pintar o bot�o de fechar adequadamente
	 * quando possuir ou n�o o foco do mouse.
	 * @param e java.awt.event.MouseEvent
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/event/MouseEvent.html">java.awt.event.MouseEvent</a>
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/awt/event/MouseListener.html">java.awt.event.MouseListener</a>
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if(overClose) {
			fullRepaint = false;
			overClose = false;
			this.repaint();
		}
	}


	/**
	 * M�todo de execu��o principal para teste de <code>PowerFrame</code>.
	 * @param ags 
	 */
	public static void main(String[] ags) {
		PowerFrame pf = new PowerFrame("PowerFrame");

		PaintStyle ps = new PaintStyle(Color.BLACK, 
				new Color(190, 190, 190), Color.WHITE);
		//pf.setPaintStyle(ps);
		//pf.setXcloseColor(ps.getDownColor().brighter());
		//pf.setResizable(false);
		
		pf.addEffect(new FaderEffect());
		pf.addEffect(new ReflectionEffect());
		//pf.addEffect(new ShadowEffect().setRoundedCorner(true));
		
		pf.setIconImage(new ImageIcon(
				PowerFrame.class.getResource(
				"images/web-icon.png")).getImage());

		pf.setSize(300, 150);
		JPanel p = new JPanel();

		final JLabel label = new JLabel(" R�tulo ");
		label.setFont(new Font("Monospaced", Font.BOLD, 14));
		p.add(label);

		JButton button = new JButton("Bot�o");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(" Click! ");
				label.repaint();
			}
		});
		p.add(button);

		p.add(new JTextField("  Caixa de Texto  "));
		final JComboBox combo = new JComboBox(
				new String[] {"Gostou?", "Nota 0", "Nota 2",
				"Nota 4", "Nota 6", "Nota 8", "Nota 10"});

		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = combo.getSelectedItem().toString();
				if(item.equals("Gostou?"))
					label.setText("  ...   ");
				else if(item.equals("Nota 0")
						|| item.equals("Nota 2"))
					label.setText("   :(   ");
				else if(item.equals("Nota 4"))
					label.setText("   :p   ");
				else if(item.equals("Nota 6"))
					label.setText("   :p   ");
				else if(item.equals("Nota 8"))
					label.setText("   :)   ");
				else if(item.equals("Nota 10"))
					label.setText("   ;)   ");
				label.repaint();
			}
		});
		p.add(combo);
		pf.add(p);
		pf.setLocationRelativeTo(null);
		pf.open();
	}
	
}