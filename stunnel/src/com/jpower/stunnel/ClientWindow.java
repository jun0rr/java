/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClientWindow.java
 *
 * Created on 17/07/2012, 14:12:16
 */
package com.jpower.stunnel;

import com.jpower.date.SimpleDate;
import com.jpower.nnet.conf.Configuration;
import java.awt.Point;


/**
 *
 * @author juno
 */
public class ClientWindow extends javax.swing.JFrame implements ReceiveListener {

  private StringBuilder messages = new StringBuilder();
  
  private Configuration conf;
  
  private TimeReceiver receiver;
  
  
  /** Creates new form ClientWindow */
  public ClientWindow(Configuration c) {
    initComponents();
    conf = c;
    receiver = new TimeReceiver(conf, this);
    this.addressInput.setText(
        conf.getRemoteAddress());
    this.portInput.setText(String.valueOf(
        conf.getRemotePort()));
  }


  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    addressInput = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    portInput = new javax.swing.JTextField();
    connectButton = new javax.swing.JButton();
    stopButton = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();
    scroll = new javax.swing.JScrollPane();
    outMessages = new javax.swing.JTextArea();
    clearButton = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("socket.tunnel.ClientWindow");
    setResizable(false);

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Configurações do Servidor"));

    jLabel1.setText("Servidor:");

    addressInput.setText("127.0.0.1");

    jLabel2.setText("Porta:");

    portInput.setText("29209");

    connectButton.setText("Conectar");
    connectButton.setMaximumSize(new java.awt.Dimension(100, 26));
    connectButton.setMinimumSize(new java.awt.Dimension(100, 26));
    connectButton.setPreferredSize(new java.awt.Dimension(100, 26));
    connectButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        connectButtonActionPerformed(evt);
      }
    });

    stopButton.setText("Parar");
    stopButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel1)
              .addComponent(jLabel2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(portInput, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
              .addComponent(addressInput, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(116, 116, 116)
            .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(addressInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(portInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(stopButton))
        .addContainerGap(19, Short.MAX_VALUE))
    );

    jLabel3.setText("Mensagens:");

    outMessages.setEditable(false);
    outMessages.setColumns(20);
    outMessages.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
    outMessages.setForeground(new java.awt.Color(102, 102, 102));
    outMessages.setRows(5);
    scroll.setViewportView(outMessages);

    clearButton.setText("Limpar");
    clearButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(scroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
          .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(clearButton)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(clearButton))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  
private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
  this.messages = new StringBuilder();
  this.outMessages.setText("");
}//GEN-LAST:event_clearButtonActionPerformed


private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
  this.addressInput.setEnabled(false);
  this.portInput.setEnabled(false);
  this.connectButton.setEnabled(false);
  
  conf.setRemoteAddress(this.getAddress());
  conf.setRemotePort(this.getPort());
  conf.save();
  receiver.setConfiguration(conf);
  
  try {
    receiver.connect();
    
  } catch(Exception ex) {
    ex.printStackTrace();
    this.error(ex);
    this.addressInput.setEnabled(true);
    this.portInput.setEnabled(true);
    this.connectButton.setEnabled(true);
  }
}//GEN-LAST:event_connectButtonActionPerformed

  private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
    receiver.stop();
    this.appendText(" * Client Stopped!");
    this.addressInput.setEnabled(true);
    this.portInput.setEnabled(true);
    this.connectButton.setEnabled(true);
  }//GEN-LAST:event_stopButtonActionPerformed



  @Override
  public void received(SimpleDate d) {
    this.appendText(" * New Message Received:");
    this.appendText("   "+ d.format(SimpleDate.DDMMYYYY_HHMMSS_DASH));
  }


  @Override
  public void error(Throwable th) {
    this.appendError(th.toString());
    th.printStackTrace();
    if(th.getMessage() != null)
      this.appendError(th.getMessage());
    if(th.getCause() != null)
      this.appendError(th.getCause().getMessage());
  
    this.addressInput.setEnabled(true);
    this.portInput.setEnabled(true);
    this.connectButton.setEnabled(true);
  }


  public void appendText(String text) {
    if(text == null) return;
    messages.append(text).append("\n");
    outMessages.setText(messages.toString());
    scroll.getViewport().setViewPosition(
        new Point(0, outMessages.getHeight()));
  }


  public void appendError(String text) {
    this.appendText(" # ".concat(text));
  }


  public int getPort() {
    String sport = portInput.getText();
    try {
      return Integer.parseInt(sport);
    } catch(NumberFormatException ex) {
      this.appendError("Invalid port: "+ sport);
      return 0;
    }
  }


  public String getAddress() {
    return addressInput.getText();
  }


  /**
   * @param args the command line arguments
   */
  public void open() {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Metal".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        ClientWindow.this.setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField addressInput;
  private javax.swing.JButton clearButton;
  private javax.swing.JButton connectButton;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JTextArea outMessages;
  private javax.swing.JTextField portInput;
  private javax.swing.JScrollPane scroll;
  private javax.swing.JButton stopButton;
  // End of variables declaration//GEN-END:variables

}
