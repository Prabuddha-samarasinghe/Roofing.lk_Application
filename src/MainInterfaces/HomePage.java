/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import mycode.DBconnect;


public final class HomePage extends javax.swing.JFrame {
    
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String isAdmin;
    String userName;
    String userID;
    int x,y;

    public HomePage() {
        
        setIconImage();
        initComponents();
        
        
        //Connect to DB
        con = DBconnect.connect();
        
        Date();
        Time();
        
                      
    }
    
        
     HomePage(String un, String s1,String uID) {
        
        initComponents();
        
        setIconImage();
        
        this.userID = uID;
        this.isAdmin = s1;
        this.userName = un;
        
        lblcurrentUser.setText(userName);
        
         Date();
         Time();
        
        
        if(!isAdmin.equals("Admin")){
                
            btnUsrRegister.setEnabled(false);
            btnItmMaster.setEnabled(false);
            btnCusMaster.setEnabled(false);
            
            //btnUsrRegister.setVisible(false);
            //btnItmMaster.setVisible(false);
            //btnCusMaster.setVisible(false);
        }

    }
    
   public void Date(){
       Date d = new Date();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
       String dd = sdf.format(d);
       lblDate.setText(dd);
   } 
   
   Timer t;
   SimpleDateFormat st;
    
   public void Time(){
       
       
       t = new Timer(0,new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
           Date dt =  new Date();
           st = new SimpleDateFormat("HH:mm:ss");
           
           String tt = st.format(dt);
           lblTime.setText(tt);
           
           }
       });
           
       t.start();
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
        jButton3 = new javax.swing.JButton();
        btnCusMaster = new javax.swing.JButton();
        btnGoodsReceipt = new javax.swing.JButton();
        btnGoodsIssue = new javax.swing.JButton();
        btnSalesOrder = new javax.swing.JButton();
        btnUsrRegister = new javax.swing.JButton();
        btnMainReports = new javax.swing.JButton();
        btnMainExit = new javax.swing.JButton();
        btnSalesInvoice = new javax.swing.JButton();
        btnItmMaster = new javax.swing.JButton();
        btnChangePw = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblDate = new javax.swing.JLabel();
        lblDate1 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblcurrentUser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home");
        setUndecorated(true);

        jPanel1.setForeground(new java.awt.Color(0, 51, 51));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/test5555.gif"))); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnCusMaster.setBackground(new java.awt.Color(3, 35, 55));
        btnCusMaster.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCusMaster.setForeground(new java.awt.Color(255, 255, 255));
        btnCusMaster.setText("Customer Master");
        btnCusMaster.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCusMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCusMasterActionPerformed(evt);
            }
        });

        btnGoodsReceipt.setBackground(new java.awt.Color(3, 35, 55));
        btnGoodsReceipt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnGoodsReceipt.setForeground(new java.awt.Color(255, 255, 255));
        btnGoodsReceipt.setText("Inventory Receipt");
        btnGoodsReceipt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGoodsReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoodsReceiptActionPerformed(evt);
            }
        });

        btnGoodsIssue.setBackground(new java.awt.Color(3, 35, 55));
        btnGoodsIssue.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnGoodsIssue.setForeground(new java.awt.Color(255, 255, 255));
        btnGoodsIssue.setText("Inventory Return");
        btnGoodsIssue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGoodsIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoodsIssueActionPerformed(evt);
            }
        });

        btnSalesOrder.setBackground(new java.awt.Color(3, 35, 55));
        btnSalesOrder.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSalesOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnSalesOrder.setText("Sales Order");
        btnSalesOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalesOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesOrderActionPerformed(evt);
            }
        });

        btnUsrRegister.setBackground(new java.awt.Color(3, 35, 55));
        btnUsrRegister.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnUsrRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnUsrRegister.setText("Register User");
        btnUsrRegister.setToolTipText("Add or view item master data");
        btnUsrRegister.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnUsrRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsrRegisterActionPerformed(evt);
            }
        });

        btnMainReports.setBackground(new java.awt.Color(3, 35, 55));
        btnMainReports.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMainReports.setForeground(new java.awt.Color(255, 255, 255));
        btnMainReports.setText("Reports");
        btnMainReports.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMainReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainReportsActionPerformed(evt);
            }
        });

        btnMainExit.setBackground(new java.awt.Color(3, 35, 55));
        btnMainExit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnMainExit.setForeground(new java.awt.Color(255, 255, 255));
        btnMainExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Closed.png"))); // NOI18N
        btnMainExit.setText("EXIT");
        btnMainExit.setBorder(null);
        btnMainExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainExitActionPerformed(evt);
            }
        });

        btnSalesInvoice.setBackground(new java.awt.Color(3, 35, 55));
        btnSalesInvoice.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSalesInvoice.setForeground(new java.awt.Color(255, 255, 255));
        btnSalesInvoice.setText("Sales Invoice");
        btnSalesInvoice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalesInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesInvoiceActionPerformed(evt);
            }
        });

        btnItmMaster.setBackground(new java.awt.Color(3, 35, 55));
        btnItmMaster.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnItmMaster.setForeground(new java.awt.Color(255, 255, 255));
        btnItmMaster.setText("Item Master");
        btnItmMaster.setToolTipText("Add or view item master data");
        btnItmMaster.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnItmMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItmMasterActionPerformed(evt);
            }
        });

        btnChangePw.setBackground(new java.awt.Color(3, 35, 55));
        btnChangePw.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnChangePw.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePw.setText("Change Password");
        btnChangePw.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnChangePw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePwActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(3, 35, 55));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });
        jPanel2.setLayout(null);

        jButton4.setBackground(new java.awt.Color(3, 35, 55));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Programming-Minimize-Window-icon resize.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4);
        jButton4.setBounds(1010, 10, 30, 20);

        jButton6.setBackground(new java.awt.Color(3, 35, 55));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainInterfaces/HED ICON.png"))); // NOI18N
        jButton6.setBorder(null);
        jPanel2.add(jButton6);
        jButton6.setBounds(10, 10, 30, 20);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Home");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(50, 10, 50, 20);

        jPanel3.setBackground(new java.awt.Color(3, 35, 55));

        lblDate.setBackground(new java.awt.Color(255, 255, 255));
        lblDate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblDate1.setBackground(new java.awt.Color(255, 255, 255));
        lblDate1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblDate1.setForeground(new java.awt.Color(255, 255, 255));
        lblDate1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTime.setBackground(new java.awt.Color(255, 255, 255));
        lblTime.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clock.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/calander.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(97, 97, 97)
                    .addComponent(lblDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(345, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblDate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Hello!");

        lblcurrentUser.setBackground(new java.awt.Color(0, 0, 0));
        lblcurrentUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblcurrentUser, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCusMaster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChangePw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGoodsReceipt, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                    .addComponent(btnGoodsIssue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalesOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMainExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMainReports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalesInvoice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUsrRegister, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnItmMaster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblcurrentUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(btnUsrRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnItmMaster, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCusMaster, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChangePw, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGoodsReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGoodsIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalesOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalesInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMainReports, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMainExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnCusMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCusMasterActionPerformed
        
        CustomerMaster cusm = new CustomerMaster();
        cusm.setVisible(true);
         //this.setExtendedState(HomePage.ICONIFIED);
                
    }//GEN-LAST:event_btnCusMasterActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnSalesInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesInvoiceActionPerformed
        
        SalesInvoice sinv = new SalesInvoice(this.userName);
        sinv.setVisible(true);
        //this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_btnSalesInvoiceActionPerformed

    private void btnUsrRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsrRegisterActionPerformed
        
        UserRegistration ur1 = new UserRegistration ();
        ur1.setVisible(true);
        //this.setExtendedState(HomePage.ICONIFIED);
        
    }//GEN-LAST:event_btnUsrRegisterActionPerformed

    private void btnMainExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainExitActionPerformed
        
       System.exit(0);
        
    }//GEN-LAST:event_btnMainExitActionPerformed

    private void btnItmMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItmMasterActionPerformed
       
        ItemMaster itmm = new ItemMaster ();
        itmm.setVisible(true);
        //this.setExtendedState(HomePage.ICONIFIED);
        
    }//GEN-LAST:event_btnItmMasterActionPerformed

    private void btnGoodsReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoodsReceiptActionPerformed
        
        InventoryReceipt inventRcpt = new InventoryReceipt();
        inventRcpt.setVisible(true);
         //this.setExtendedState(HomePage.ICONIFIED);
        
    }//GEN-LAST:event_btnGoodsReceiptActionPerformed

    private void btnGoodsIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoodsIssueActionPerformed
        
        InventoryReturn inventIss = new InventoryReturn();
        inventIss.setVisible(true);
        //this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_btnGoodsIssueActionPerformed

    private void btnSalesOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesOrderActionPerformed
        
        SalesOrder so = new SalesOrder(this.userName);
        so.setVisible(true);
        //this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_btnSalesOrderActionPerformed

    private void btnChangePwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePwActionPerformed
        
        changePw cpw = new changePw(this.userID); // Constrator for change Password 
        cpw.setVisible(true);
        //this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_btnChangePwActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
       
        x = evt.getX();
        y = evt.getY();
        
    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        
        
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx-x, yy-y);
        
    }//GEN-LAST:event_jPanel2MouseDragged

    private void btnMainReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainReportsActionPerformed
       Reports rp = new Reports(this.userName);
       rp.setVisible(true);
       //this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_btnMainReportsActionPerformed

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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePw;
    private javax.swing.JButton btnCusMaster;
    private javax.swing.JButton btnGoodsIssue;
    private javax.swing.JButton btnGoodsReceipt;
    private javax.swing.JButton btnItmMaster;
    private javax.swing.JButton btnMainExit;
    private javax.swing.JButton btnMainReports;
    private javax.swing.JButton btnSalesInvoice;
    private javax.swing.JButton btnSalesOrder;
    private javax.swing.JButton btnUsrRegister;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDate1;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblcurrentUser;
    // End of variables declaration//GEN-END:variables

    private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }
}