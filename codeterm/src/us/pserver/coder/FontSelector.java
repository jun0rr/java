/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package us.pserver.coder;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;


/**
 *
 * @author juno
 */
public class FontSelector extends javax.swing.JDialog {

  private Font current;
  
  private String[] fonts;
  
  private boolean retnull;
  
  
  /**
   * Creates new form FontSelector
   */
  public FontSelector(Window parent, boolean modal) {
    super(parent, (modal 
        ? ModalityType.APPLICATION_MODAL 
        : ModalityType.MODELESS));
    retnull = false;
    fonts = GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getAvailableFontFamilyNames();
    initComponents();
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        retnull = true;
        setVisible(false);
        dispose();
      }
    });
    KeyListener kl = new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          setVisible(false);
          dispose();
        }
      }
    };
    fontField.addKeyListener(kl);
    fontList.addKeyListener(kl);
    styleField.addKeyListener(kl);
    styleList.addKeyListener(kl);
    sizeField.addKeyListener(kl);
    sizeList.addKeyListener(kl);
    okButton.addKeyListener(kl);
    this.setLocationRelativeTo(parent);
  }
  
  
  public FontSelector(Window parent, boolean modal, Font f) {
    this(parent, modal);
    if(f != null) {
      int size = f.getSize();
      int style = f.getStyle();
      String name = f.getFamily();
      current = f;
      this.setFontName(name);
      this.setFontSize(size);
      this.setFontStyle(style);
    }
    viewLabel.setFont(f);
  }
  
  
  public void setFontName(String name) {
    if(name == null) return;
    for(int i = 0; i < fonts.length; i++) {
      if(name.equals(fonts[i])) {
        fontList.setSelectedIndex(i);
        return;
      }
    }
  }
  
  
  public String getFontName() {
    return fontField.getText();
  }
  
  
  public void setFontStyle(int style) {
    if(style == Font.BOLD) {
      styleList.setSelectedIndex(1);
    }
    else if(style == Font.ITALIC) {
      styleList.setSelectedIndex(2);
    }
    else if(style == (Font.BOLD | Font.ITALIC)) {
      styleList.setSelectedIndex(3);
    }
    else {
      styleList.setSelectedIndex(0);
    }
  }
  
  
  public int getFontStyle() {
    String style = styleField.getText();
    if(style.equals("Bold")) {
      return Font.BOLD;
    }
    else if(style.equals("Italic")) {
      return Font.ITALIC;
    }
    else if(style.equals("Bold + Italic")) {
      return Font.BOLD | Font.ITALIC;
    }
    else {
      return Font.PLAIN;
    }
  }
  
  
  public int getFontSize() {
    try {
      return Integer.parseInt(sizeField.getText());
    } catch(NumberFormatException e) {
      return -1;
    }
  }
  
  
  public void setFontSize(int size) {
    if(size <= 0) return;
    sizeField.setText(String.valueOf(size));
  }
  
  
  public Font getSelectedFont() {
    if(retnull) return null;
    if(getFontName() != null && getFontSize() > 0)
      current = new Font(getFontName(),
          getFontStyle(), getFontSize());
    return current;
  }


  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel2 = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    fontField = new javax.swing.JTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    fontList = new javax.swing.JList();
    jLabel2 = new javax.swing.JLabel();
    styleField = new javax.swing.JTextField();
    jScrollPane2 = new javax.swing.JScrollPane();
    styleList = new javax.swing.JList();
    jLabel3 = new javax.swing.JLabel();
    sizeField = new javax.swing.JTextField();
    jScrollPane3 = new javax.swing.JScrollPane();
    sizeList = new javax.swing.JList();
    showPanel = new javax.swing.JPanel();
    viewLabel = new javax.swing.JLabel();
    jSeparator1 = new javax.swing.JSeparator();
    okButton = new javax.swing.JButton();

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 100, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 100, Short.MAX_VALUE)
    );

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Font Selector");
    setBackground(new java.awt.Color(239, 239, 239));
    setIconImage(IconGetter.getIconFontGray());

    jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
    jLabel1.setText(" Font Family");

    fontField.setEditable(false);
    fontField.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N

    fontList.setModel(new javax.swing.AbstractListModel() {
      public int getSize() { return fonts.length; }
      public Object getElementAt(int i) { return fonts[i]; }
    });
    fontList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        fontListValueChanged(evt);
      }
    });
    jScrollPane1.setViewportView(fontList);

    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
    jLabel2.setText(" Font Style");

    styleField.setEditable(false);
    styleField.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N

    styleList.setModel(new javax.swing.AbstractListModel() {
      String[] strings = { "Plain", "Bold", "Italic", "Bold + Italic" };
      public int getSize() { return strings.length; }
      public Object getElementAt(int i) { return strings[i]; }
    });
    styleList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        styleListValueChanged(evt);
      }
    });
    jScrollPane2.setViewportView(styleList);

    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
    jLabel3.setText(" Font Size");

    sizeField.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
    sizeField.setText("12");
    sizeField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        sizeFieldKeyReleased(evt);
      }
    });

    final String[] sizes = new String[21];
    for(int i = 0; i < 21; i++) {
      sizes[i] = String.valueOf(((i+2)*2));
    }
    sizeList.setModel(new javax.swing.AbstractListModel() {
      public int getSize() { return sizes.length; }
      public Object getElementAt(int i) { return sizes[i]; }
    });
    sizeList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        sizeListValueChanged(evt);
      }
    });
    jScrollPane3.setViewportView(sizeList);

    showPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Font View", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N

    viewLabel.setText("The Qu1ck Brown FOX Jumps 0ver The L4zy Do9.");

    javax.swing.GroupLayout showPanelLayout = new javax.swing.GroupLayout(showPanel);
    showPanel.setLayout(showPanelLayout);
    showPanelLayout.setHorizontalGroup(
      showPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(viewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
    );
    showPanelLayout.setVerticalGroup(
      showPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(viewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    okButton.setText("OK");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        okButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(jLabel1)
                  .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                  .addComponent(fontField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addComponent(styleField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                      .addComponent(jLabel2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addComponent(jLabel3)
                      .addComponent(sizeField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
          .addComponent(showPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(jLabel2)
          .addComponent(jLabel3))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(fontField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(styleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(sizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
          .addComponent(jScrollPane2)
          .addComponent(jScrollPane3))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(showPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(okButton)
        .addContainerGap(12, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  
  private void fontListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_fontListValueChanged
    String name = (String) fontList.getSelectedValue();
    fontField.setText(name);
    viewLabel.setFont(this.getSelectedFont());
    viewLabel.repaint();
  }//GEN-LAST:event_fontListValueChanged

  
  private void styleListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_styleListValueChanged
    String style = (String) styleList.getSelectedValue();
    styleField.setText(style);
    viewLabel.setFont(this.getSelectedFont());
    viewLabel.repaint();
  }//GEN-LAST:event_styleListValueChanged

  
  private void sizeListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_sizeListValueChanged
    sizeField.setText((String)sizeList.getSelectedValue());
    viewLabel.setFont(this.getSelectedFont());
    viewLabel.repaint();
  }//GEN-LAST:event_sizeListValueChanged

  
  private void sizeFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sizeFieldKeyReleased
    viewLabel.setFont(this.getSelectedFont());
    viewLabel.repaint();
  }//GEN-LAST:event_sizeFieldKeyReleased

  private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    this.getSelectedFont();
    this.setVisible(false);
    this.dispose();
  }//GEN-LAST:event_okButtonActionPerformed

  
  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(FontSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(FontSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(FontSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(FontSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
        //</editor-fold>

    Font f = new Font("Verdana", Font.BOLD | Font.ITALIC, 14);
    FontSelector dialog = new FontSelector(new JFrame(), true, f);
    dialog.setVisible(true);
    System.out.println("* font="+dialog.getSelectedFont());
    System.exit(0);
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField fontField;
  private javax.swing.JList fontList;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JButton okButton;
  private javax.swing.JPanel showPanel;
  private javax.swing.JTextField sizeField;
  private javax.swing.JList sizeList;
  private javax.swing.JTextField styleField;
  private javax.swing.JList styleList;
  private javax.swing.JLabel viewLabel;
  // End of variables declaration//GEN-END:variables
}