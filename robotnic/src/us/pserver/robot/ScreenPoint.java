/*
 * Direitos Autorais Reservados (c) 2011 Juno Roesler
 * Contato: juno.rr@gmail.com
 * 
 * Esta biblioteca é software livre; você pode redistribuí-la e/ou modificá-la sob os
 * termos da Licença Pública Geral Menor do GNU conforme publicada pela Free
 * Software Foundation; tanto a versão 2.1 da Licença, ou qualquer
 * versão posterior.
 * 
 * Esta biblioteca é distribuída na expectativa de que seja útil, porém, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE
 * OU ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública
 * Geral Menor do GNU para mais detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral Menor do GNU junto
 * com esta biblioteca; se não, acesse 
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html, 
 * ou escreva para a Free Software Foundation, Inc., no
 * endereço 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 */

package us.pserver.robot;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_F5;
import static java.awt.event.KeyEvent.VK_F6;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON2;
import static java.awt.event.MouseEvent.BUTTON3;
import java.util.Arrays;

/**
 *
 * @author Juno Roesler - juno@pserver.us
 * @version 0.0 - 28/08/2019
 */
public class ScreenPoint {
  
  private static MouseEvent event;
  
  private static final Robot robot = getRobot();
  
  private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  
  
  private static Robot getRobot() {
    try {
      return new Robot();
    }
    catch(AWTException e) {
      throw new RuntimeException(e.toString(), e);
    }
  }
  
  private static Image getScreenCapture() {
    return robot.createScreenCapture(new Rectangle(screenSize));
  }
  
  private static int getMouseButtonMask(int btn) {
    switch(btn) {
      case BUTTON1:
        return MouseEvent.BUTTON1_DOWN_MASK;
      case BUTTON2:
        return MouseEvent.BUTTON2_DOWN_MASK;
      case BUTTON3:
        return MouseEvent.BUTTON3_DOWN_MASK;
      default: 
        return btn;
    }
  }
  
  
  static class ImagePanel extends Panel {
    private Image image;
    public ImagePanel() {
      this(null);
    }
    public ImagePanel(Image img) {
      super();
      this.image = img;
    }
    public Image getImage() {
      return image;
    }
    public void setImage(Image img) {
      this.image = img;
      this.repaint();
    }
    @Override
    public void paint(Graphics g) {
      super.paint(g);
      if(image != null) {
        g.drawImage(image, 0, 0, screenSize.width, screenSize.height, null);
      }
    }
  }

  
  public static void main(String[] args) throws AWTException {
    Frame f = new Frame();
    f.setUndecorated(true);
    f.setLayout(null);
    f.setBounds(0, 0, screenSize.width, screenSize.height);
    System.out.println("* screenSize=" + screenSize);
    Arrays.asList(GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getScreenDevices()).stream()
        .map(d->d.getDefaultConfiguration().getBounds())
        .forEach(System.out::println);
    Label lbl = new Label();
    lbl.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    lbl.setBounds(screenSize.width / 2 - 50, screenSize.height / 2 - 15, 100, 30);
    lbl.setText("0000 x 0000");
    
    ImagePanel pnl = new ImagePanel(getScreenCapture());
    pnl.setLayout(null);
    pnl.setBounds(f.getBounds());
    pnl.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        System.out.println(e);
      }
      @Override
      public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
          case VK_F5:
            System.out.println("VK_F5");
            f.setVisible(false);
            pnl.setImage(getScreenCapture());
            f.setVisible(true);
            break;
          case VK_F6:
            System.out.println("VK_F6");
            f.setVisible(false);
            robot.mouseMove(event.getXOnScreen(), event.getYOnScreen());
            robot.mousePress(getMouseButtonMask(event.getButton()));
            robot.delay(50);
            robot.mouseRelease(getMouseButtonMask(event.getButton()));
            f.setVisible(true);
            pnl.requestFocus();
            pnl.requestFocusInWindow();
            break;
          default:
            System.out.println(e);
            break;
        }
        if(KeyEvent.VK_F5 == e.getKeyCode()) {
        }
      }
      @Override
      public void keyReleased(KeyEvent e) {
        System.out.println(e);
      }
    });
    pnl.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event = e;
        switch(e.getButton()) {
          case BUTTON1:
            Point p = e.getPoint();
            lbl.setText(String.format("%d x %d", p.x, p.y));
            lbl.repaint();
            break;
          case BUTTON2:
            f.setVisible(false);
            f.dispose();
            System.exit(0);
            break;
          default:
            System.out.println(e);
            break;
        }
      }
      @Override
      public void mousePressed(MouseEvent e) {
        System.out.println(e);
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        System.out.println(e);
      }
      @Override
      public void mouseEntered(MouseEvent e) {
        System.out.println(e);
      }
      @Override
      public void mouseExited(MouseEvent e) {
        System.out.println(e);
      }
    });
    
    pnl.add(lbl);
    f.add(pnl);
    f.setVisible(true);
    pnl.requestFocus();
    pnl.requestFocusInWindow();
  }
  
}
