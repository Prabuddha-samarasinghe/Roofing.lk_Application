/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;
//import Main.HomePage;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mycode.DBconnect;


public class Login extends javax.swing.JFrame {
    Connection con = null;
    PreparedStatement pst = null;
    

    /**
     * Creates new form Login1
     */
    public Login() {
        setIconImage();
        initComponents();
        
        //Connect to DB
        con = DBconnect.connect();
                
    }
    
    
    public void userLogin(){
        
        try{
            
            if (txtUlUserCode.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "User Code is empty");
                txtUlUserCode.requestFocus();
            }
            
            else if (pwUlPassword.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Password is empty");
                pwUlPassword.requestFocus();
            }
            
            else{
            String q = "select * from userregistration where userCode =? and password = ?  ";
            pst = con.prepareStatement(q);
            pst.setString(1, txtUlUserCode.getText());
            //pst.setString(2, pwUlPassword.getText());
            
            //Password Decryption
            String encripString1 = getEncodedString(pwUlPassword.getText());
            pst.setString(2,encripString1 );
            
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
            String s1 = rs.getString("isAdmin");
            String un = rs.getString("userName");
            String status = rs.getString("Status");
            String uID =  txtUlUserCode.getText();
            
            if(status.equals("Inactive")){
                JOptionPane.showMessageDialog(this, "User is Inactive. Contact your Administrator");
                txtUlUserCode.setText("");
                pwUlPassword.setText("");
                }
            
            else if(s1.equals("Admin")){
                HomePage home = new HomePage(un, s1,uID);
                home.setVisible(true);
                setVisible(false);
                this.dispose();
                }
            else if(!s1.equals("Admin")){
                HomePage home = new HomePage(un, s1,uID);
                home.setVisible(true);
                this.dispose();
                }
            
            }
            else{
            JOptionPane.showMessageDialog(this, "Incorrect User Name or Password");
            //System.out.println("Incorrect User Name or Password");
            txtUlUserCode.setText("");
            pwUlPassword.setText("");
        
            }
            
            }
                     
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(this, e);
            //System.out.println(e);
                
        }

    }
    
       
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbsUlUserCode = new javax.swing.JLabel();
        lbsUlPassword = new javax.swing.JLabel();
        txtUlUserCode = new javax.swing.JTextField();
        pwUlPassword = new javax.swing.JPasswordField();
        btnUlLogin = new javax.swing.JButton();
        btnUlReset = new javax.swing.JButton();
        btnUlExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User Login");
        setAlwaysOnTop(true);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(3, 36, 55));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Login.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbsUlUserCode.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbsUlUserCode.setText("User Code");

        lbsUlPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbsUlPassword.setText("Password");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pwUlPassword, org.jdesktop.beansbinding.ObjectProperty.create(), txtUlUserCode, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtUlUserCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUlUserCodeActionPerformed(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUlLogin, org.jdesktop.beansbinding.ObjectProperty.create(), pwUlPassword, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        pwUlPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwUlPasswordActionPerformed(evt);
            }
        });

        btnUlLogin.setBackground(new java.awt.Color(3, 35, 55));
        btnUlLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUlLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnUlLogin.setText("Login");
        btnUlLogin.setMaximumSize(new java.awt.Dimension(70, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUlReset, org.jdesktop.beansbinding.ObjectProperty.create(), btnUlLogin, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUlLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUlLoginActionPerformed(evt);
            }
        });
        btnUlLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUlLoginKeyPressed(evt);
            }
        });

        btnUlReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUlReset.setText("Reset");
        btnUlReset.setMaximumSize(new java.awt.Dimension(70, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUlExit, org.jdesktop.beansbinding.ObjectProperty.create(), btnUlReset, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUlReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUlResetActionPerformed(evt);
            }
        });
        btnUlReset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUlResetKeyPressed(evt);
            }
        });

        btnUlExit.setBackground(new java.awt.Color(255, 51, 51));
        btnUlExit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUlExit.setForeground(new java.awt.Color(255, 255, 255));
        btnUlExit.setText("Exit");
        btnUlExit.setMaximumSize(new java.awt.Dimension(70, 35));
        btnUlExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUlExitActionPerformed(evt);
            }
        });
        btnUlExit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUlExitKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnUlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUlReset, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUlExit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbsUlUserCode)
                            .addComponent(lbsUlPassword))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUlUserCode)
                            .addComponent(pwUlPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUlUserCode, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbsUlUserCode))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwUlPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbsUlPassword))
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUlReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUlExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        setSize(new java.awt.Dimension(545, 275));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pwUlPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwUlPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwUlPasswordActionPerformed

    private void btnUlLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUlLoginActionPerformed
        
        userLogin();
 
        
    }//GEN-LAST:event_btnUlLoginActionPerformed

    private void btnUlExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUlExitActionPerformed
        
          System.exit(0);
        
    }//GEN-LAST:event_btnUlExitActionPerformed

    private void btnUlResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUlResetActionPerformed
        
        txtUlUserCode.setText("");
        pwUlPassword.setText("");
        
    }//GEN-LAST:event_btnUlResetActionPerformed

    private void txtUlUserCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUlUserCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUlUserCodeActionPerformed

    private void btnUlLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUlLoginKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            userLogin();

        }
         
    }//GEN-LAST:event_btnUlLoginKeyPressed

    private void btnUlResetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUlResetKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            txtUlUserCode.setText("");
            pwUlPassword.setText("");
        }
        
    }//GEN-LAST:event_btnUlResetKeyPressed

    private void btnUlExitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUlExitKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            this.dispose();
        }
        
    }//GEN-LAST:event_btnUlExitKeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUlExit;
    private javax.swing.JButton btnUlLogin;
    private javax.swing.JButton btnUlReset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbsUlPassword;
    private javax.swing.JLabel lbsUlUserCode;
    private javax.swing.JPasswordField pwUlPassword;
    private javax.swing.JTextField txtUlUserCode;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private String getEncodedString(String text) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return Base64.getEncoder().encodeToString(pwUlPassword.getText().getBytes());
    }
    
    
  private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }
    
    
}
