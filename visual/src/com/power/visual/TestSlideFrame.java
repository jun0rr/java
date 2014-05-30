/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TestSlideFrame.java
 *
 * Created on 23/12/2009, 00:20:51
 */

package com.power.visual;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author f6036477
 */
public class TestSlideFrame extends javax.swing.JFrame {

    /** Creates new form TestSlideFrame */
    public TestSlideFrame() {
        initComponents();
        slide = new JFrame();
        slide.setUndecorated(true);
        //slide.setAlwaysOnTop(true);
        panel = new JPanel();
        panel.setLayout(null);
        slide.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setLocation(0, 0);
        panel.setSize(100, 200);
        slide.add(panel);

        slide.setSize(1, 1);
        slide.setVisible(true);
        extended = false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    blackButton1 = new com.power.visual.controls.BlackButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setLayout(null);

    blackButton1.setText("Slide Frame");
    blackButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        blackButton1ActionPerformed(evt);
      }
    });
    getContentPane().add(blackButton1);
    blackButton1.setBounds(10, 10, 101, 25);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void blackButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_blackButton1ActionPerformed
    {//GEN-HEADEREND:event_blackButton1ActionPerformed
      extended = !extended;
      if(!extended) {
        for(int i = slide.getHeight(); i >= 2; i -= 5)
        {
          slide.setSize(100, i);
          try {
            Thread.sleep(4);
          } catch (InterruptedException ex) {}
        }
        slide.setAlwaysOnTop(false);
        slide.setSize(1, 1);

      } else {
        int x = blackButton1.getLocationOnScreen().x;
        int y = blackButton1.getLocationOnScreen().y + 27;
        slide.setLocation(x, y);
        slide.setSize(100, 2);
        slide.setAlwaysOnTop(true);

        for(int i = 5; i <= 200; i += 5)
        {
          slide.setSize(100, i);
          try {
            Thread.sleep(4);
          } catch (InterruptedException ex) {}
        }
      }//else
    }//GEN-LAST:event_blackButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
      @Override
            public void run() {
                new TestSlideFrame().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.power.visual.controls.BlackButton blackButton1;
  // End of variables declaration//GEN-END:variables
  private JFrame slide;
  private JPanel panel;
  private boolean extended;


}
