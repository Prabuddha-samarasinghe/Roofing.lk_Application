/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;

import java.awt.Toolkit;

/**
 *
 * @author Prabudha
 */
public class StartUp extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public StartUp() {
        setIconImage();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        MyProgress = new javax.swing.JProgressBar();
        Presentage = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(3, 35, 55));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Login.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        MyProgress.setBackground(new java.awt.Color(3, 35, 55));
        MyProgress.setForeground(new java.awt.Color(255, 255, 255));
        MyProgress.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Presentage.setBackground(new java.awt.Color(255, 255, 255));
        Presentage.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Presentage.setForeground(new java.awt.Color(255, 255, 255));
        Presentage.setText("%");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SALES MANAGEMENT SYSTEM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(MyProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Presentage)
                        .addGap(218, 218, 218))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(56, 56, 56)
                .addComponent(Presentage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MyProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        StartUp MyStartUp = new StartUp();
        MyStartUp.setVisible(true);
        
        try{
            for(int i =0;i<=100;i++)
            {
                Thread.sleep(25);
                MyStartUp.MyProgress.setValue(i);
                MyStartUp.Presentage.setText(Integer.toString(i)+"%");
            }
        }
        catch(Exception e)
                {
                
                }
        
        
        new Login().setVisible(true);
        MyStartUp.dispose();
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar MyProgress;
    private javax.swing.JLabel Presentage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

 private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }




}
