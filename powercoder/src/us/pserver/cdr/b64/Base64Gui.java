/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.cdr.b64;

import java.awt.Frame;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import us.pserver.cdr.FileUtils;


/**
 * Interface gráfica utilitária para
 * Codificação/Decodificação de texto e arquivos no
 * formato Base64.
 * 
 * @author Juno Roesler - juno@pserver.us
 * @version 1.0 - 21/08/2013
 */
public class Base64Gui extends javax.swing.JFrame {

  private Base64FileCoder fc;
  
  private Base64StringCoder sc;
  
  private Path def;
  
  /**
   * Creates new form Gui
   */
  public Base64Gui() {
    super();
    initComponents();
    fc = new Base64FileCoder();
    sc = new Base64StringCoder();
    def = null;
    this.setLocationRelativeTo(null);
  }
  
  
  /**
   * Creates new form Gui with parent <code>Frame</code>.
   * @param parent <code>Frame</code>.
   */
  public Base64Gui(Frame parent) {
    this();
    this.setLocationRelativeTo(parent);
  }


  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    fileTab = new javax.swing.JTabbedPane();
    jPanel1 = new javax.swing.JPanel();
    textLabel = new javax.swing.JLabel();
    passField = new javax.swing.JPasswordField();
    encButton = new javax.swing.JButton();
    decButton = new javax.swing.JButton();
    jPanel3 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    outField = new javax.swing.JTextArea();
    jPanel2 = new javax.swing.JPanel();
    origLabel = new javax.swing.JLabel();
    origField = new javax.swing.JTextField();
    origButton = new javax.swing.JButton();
    destLabel = new javax.swing.JLabel();
    destField = new javax.swing.JTextField();
    destButton = new javax.swing.JButton();
    encFileButton = new javax.swing.JButton();
    decFileButton = new javax.swing.JButton();
    outLabel = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Base64 Coder");

    textLabel.setText("Texto / Senha:");

    encButton.setText("Codificar");
    encButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        encButtonActionPerformed(evt);
      }
    });

    decButton.setText("Decodificar");
    decButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        decButtonActionPerformed(evt);
      }
    });

    jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Saída", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(0, 51, 102))); // NOI18N

    outField.setEditable(false);
    outField.setColumns(20);
    outField.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
    outField.setLineWrap(true);
    outField.setRows(5);
    jScrollPane2.setViewportView(outField);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jScrollPane2)
    );
    jPanel3Layout.setVerticalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        .addContainerGap(48, Short.MAX_VALUE)
        .addComponent(encButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(decButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(38, 38, 38))
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(textLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(passField)
        .addContainerGap())
      .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(textLabel)
          .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(encButton)
          .addComponent(decButton))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    fileTab.addTab("Texto", jPanel1);

    origLabel.setText("Origem:");

    origButton.setText("...");
    origButton.setToolTipText("Selecionar Arquivo");
    origButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        origButtonActionPerformed(evt);
      }
    });

    destLabel.setText("Destino:");

    destButton.setText("...");
    destButton.setToolTipText("Selecionar Arquivo");
    destButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        destButtonActionPerformed(evt);
      }
    });

    encFileButton.setText("Codificar");
    encFileButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        encFileButtonActionPerformed(evt);
      }
    });

    decFileButton.setText("Decodificar");
    decFileButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        decFileButtonActionPerformed(evt);
      }
    });

    outLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
    outLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(outLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(origLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(destLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(destField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(destButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(origField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(origButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
          .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap(36, Short.MAX_VALUE)
            .addComponent(encFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(decFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(40, 40, 40)))
        .addContainerGap())
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(origLabel)
          .addComponent(origField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(origButton))
        .addGap(18, 18, 18)
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(destLabel)
          .addComponent(destField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(destButton))
        .addGap(18, 18, 18)
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(encFileButton)
          .addComponent(decFileButton))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
        .addComponent(outLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    fileTab.addTab("Arquivos", jPanel2);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(fileTab, javax.swing.GroupLayout.Alignment.TRAILING)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(fileTab)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void encButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encButtonActionPerformed
    this.applyString(true);
  }//GEN-LAST:event_encButtonActionPerformed

  private void decButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decButtonActionPerformed
    this.applyString(false);
  }//GEN-LAST:event_decButtonActionPerformed

  private void origButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_origButtonActionPerformed
    outLabel.setText(null);
    origField.setText(chooseFile());
  }//GEN-LAST:event_origButtonActionPerformed

  private void destButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destButtonActionPerformed
    destField.setText(chooseFile());
  }//GEN-LAST:event_destButtonActionPerformed

  private void encFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encFileButtonActionPerformed
    outLabel.setText(null);
    this.applyFile(true);
  }//GEN-LAST:event_encFileButtonActionPerformed

  private void decFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decFileButtonActionPerformed
    outLabel.setText(null);
    this.applyFile(false);
  }//GEN-LAST:event_decFileButtonActionPerformed

  
  public String chooseFile() {
    JFileChooser chooser = new JFileChooser(
        (def == null ? null : def.toFile()));
    chooser.setMultiSelectionEnabled(false);
    int resp = chooser.showOpenDialog(this);
    if(resp != JFileChooser.APPROVE_OPTION) {
      return null;
    }
    String path = chooser.getSelectedFile().getAbsolutePath();
    def = FileUtils.path(path);
    return path;
  }
  
  
  /**
   * Aplica (de)codificação no conteúdo do campo de texto.
   * @param encode <code>true</code> para codificar,
   * <code>false</code> para decodificar.
   */
  public void applyString(boolean encode) {
    String pass = new String(passField.getPassword());
    if(pass == null || pass.isEmpty()) {
      outField.setText("# Texto inválido para codificar");
      passField.requestFocus();
      return;
    }
    outField.setText(sc.apply(pass, encode));
  }
  
  
  /**
   * Aplica (de)codificação no arquivo informado no campo.
   * @param encode <code>true</code> para codificar,
   * <code>false</code> para decodificar.
   */
  public void applyFile(boolean encode) {
    String src = origField.getText();
    String dst = destField.getText();
    Path psrc = FileUtils.path(src);
    Path pdst = FileUtils.path(dst);
    if(psrc == null) {
      outLabel.setText("# Arquivo de origem inválido");
      origField.requestFocus();
      return;
    }
    if(pdst == null) {
      outLabel.setText("# Arquivo de destino inválido");
      destField.requestFocus();
      return;
    }
    boolean success = fc.apply(psrc, pdst, encode);
    if(success)
      outLabel.setText("* Processamento OK");
    else
      outLabel.setText("# Erro no Processamento");
  }
  

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
      java.util.logging.Logger.getLogger(Base64Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(Base64Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(Base64Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(Base64Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new Base64Gui().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton decButton;
  private javax.swing.JButton decFileButton;
  private javax.swing.JButton destButton;
  private javax.swing.JTextField destField;
  private javax.swing.JLabel destLabel;
  private javax.swing.JButton encButton;
  private javax.swing.JButton encFileButton;
  private javax.swing.JTabbedPane fileTab;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JButton origButton;
  private javax.swing.JTextField origField;
  private javax.swing.JLabel origLabel;
  private javax.swing.JTextArea outField;
  private javax.swing.JLabel outLabel;
  private javax.swing.JPasswordField passField;
  private javax.swing.JLabel textLabel;
  // End of variables declaration//GEN-END:variables
}
