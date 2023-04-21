package application;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;

public class JFrame extends javax.swing.JFrame {
    /**
     * Creates new form JFrame
     */
    public JFrame() {
        initComponents();
        setPlaceholder(iptUsuario, "Ex: admin");
        setPlaceholder(iptSenha, "***********");
    }

    private void setPlaceholder(JTextField field, String placeholder) {
    field.setForeground(Color.GRAY);
    field.setText(placeholder);

    field.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (field.getText().equals(placeholder)) {
                field.setText("");
                field.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (field.getText().isEmpty()) {
                field.setForeground(Color.GRAY);
                field.setText(placeholder);
            }
        }
    });
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tittleError = new javax.swing.JLabel();
        tittleEagle = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        iptUsuario = new javax.swing.JTextField();
        iptSenha = new javax.swing.JPasswordField();
        txtLogin = new javax.swing.JLabel();
        txtSenha = new javax.swing.JLabel();
        tittleEagle1 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        tittleError.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        tittleError.setForeground(new java.awt.Color(153, 0, 0));
        tittleError.setText("Error");
        tittleError.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tittleError.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tittleEagle.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        tittleEagle.setForeground(new java.awt.Color(255, 255, 255));
        tittleEagle.setText("Eagle");
        tittleEagle.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tittleEagle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnLogin.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(102, 0, 0));
        btnLogin.setText("Entrar");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        iptUsuario.setToolTipText("");
        iptUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        iptUsuario.setMinimumSize(new java.awt.Dimension(64, 25));
        iptUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iptUsuarioActionPerformed(evt);
            }
        });

        iptSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iptSenhaActionPerformed(evt);
            }
        });

        txtLogin.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtLogin.setForeground(new java.awt.Color(255, 255, 255));
        txtLogin.setText("USUÁRIO:");

        txtSenha.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtSenha.setForeground(new java.awt.Color(255, 255, 255));
        txtSenha.setText("SENHA:");

        tittleEagle1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        tittleEagle1.setForeground(new java.awt.Color(255, 255, 255));
        tittleEagle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tittleEagle1.setText("LOGIN");
        tittleEagle1.setToolTipText("");
        tittleEagle1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tittleEagle1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(163, 163, 163)
                            .addComponent(txtLogin)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(iptUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(174, 174, 174)
                            .addComponent(txtSenha)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(iptSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tittleError)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tittleEagle)))
                .addContainerGap(203, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tittleEagle1)
                        .addGap(251, 251, 251))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLogin)
                        .addGap(254, 254, 254))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tittleError)
                    .addComponent(tittleEagle))
                .addGap(22, 22, 22)
                .addComponent(tittleEagle1)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iptUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLogin))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iptSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenha))
                .addGap(18, 18, 18)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        iptUsuario.getAccessibleContext().setAccessibleParent(iptUsuario);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iptUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iptUsuarioActionPerformed

    }//GEN-LAST:event_iptUsuarioActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        if (iptUsuario.equals("admin") && iptSenha.equals("1234")){
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Usuário ou senha incoretos");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void iptSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iptSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_iptSenhaActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JPasswordField iptSenha;
    private javax.swing.JTextField iptUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel tittleEagle;
    private javax.swing.JLabel tittleEagle1;
    private javax.swing.JLabel tittleError;
    private javax.swing.JLabel txtLogin;
    private javax.swing.JLabel txtSenha;
    // End of variables declaration//GEN-END:variables
}
