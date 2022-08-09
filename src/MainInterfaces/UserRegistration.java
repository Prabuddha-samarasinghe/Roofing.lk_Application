/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;

import java.awt.Color;
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
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Prasanna
 */
public class UserRegistration extends javax.swing.JFrame {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
     int x,y;

    /**
     * Creates new form UserRegistration
     */
    public UserRegistration() {
        
      
        initComponents();
        
        setIconImage();
        //Connect to DB
        con = DBconnect.connect();
        //load the table
        tableLoad();
        
        btnUrDelete.setVisible(false);
        
        
    }
    
    public void tableLoad(){
        
        try {
            
            
            
            
            String sql = "SELECT userCode As 'User Code', userName as 'User Name', isAdmin As 'User Role', Status FROM userregistration";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            userRegnTable.setModel(DbUtils.resultSetToTableModel(rs));
            //txtUrUserCode.setEditable(false);
            
            
        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    public void addUser(){
        
        String uCode = txtUrUserCode.getText();
        String uName = txtUrUserName.getText();
        String pwd = pwUrPassword.getText();
        String cPwd = pwUrCpassword.getText();
        String admin = cmbUrAdmin.getSelectedItem().toString();
        String status = cmbStatus.getSelectedItem().toString();
        int numCount = 0;
        int capCount = 0;

                                  
        try{
            
            for(int i =0; i < pwd.length(); i++){
            if ((pwd.charAt(i)>47 && pwd.charAt(i)<58)){
                numCount++;
            }
            
            if((pwd.charAt(i)>64 && pwd.charAt(i)< 91))
                capCount++;
            }
            
            if (uCode.equals ("")){
            JOptionPane.showMessageDialog(null, "User Code is Required");
            txtUrUserCode.requestFocus();
            }

            else if (uName.equals ("")){
            JOptionPane.showMessageDialog(null, "User Name is Required");
            txtUrUserName.requestFocus();
            }
            
         else if (pwd.equals ("")){
            JOptionPane.showMessageDialog(null, "Password is Required");
            pwUrPassword.requestFocus();
            }
         
         else if(pwd.length()< 8){    
            JOptionPane.showMessageDialog(null, "Password is Too Short");
            pwUrPassword.requestFocus();
         }
         
         else if(numCount < 2){    
            JOptionPane.showMessageDialog(null, "Not enough numerals in the password");
            pwUrPassword.requestFocus();
         }
         
         else if(capCount < 2){    
            JOptionPane.showMessageDialog(null, "Not enough Capital Letters in the password");
            pwUrPassword.requestFocus();
         }
        
         else if (cPwd.equals ("")){
            JOptionPane.showMessageDialog(null, "Password Confirmation is Required");
            pwUrCpassword.requestFocus();
            }
         else if(capCount < 2){    
            JOptionPane.showMessageDialog(null, "Not enough Capital Letters in the password");
            pwUrPassword.requestFocus();
         }
        
            
         else if(!pwd.equals(cPwd)){
            JOptionPane.showMessageDialog(this, "Password does not match, Pls check"); 
            pwUrCpassword.setText("");
            pwUrCpassword.requestFocus();
            }       
            
         else{
                //Password Encryption
                String encripString1 = getEncodedString(pwd);//Emcription
                //String encripString2 = getEncodedString(cPwd);//Emcription2


                String q = "INSERT INTO userregistration (userCode, userName, password, isAdmin, Status) values ('"+ uCode +"','"+ uName +"', '"+ encripString1 +"','"+ admin +"', '"+ status +"')";
                pst = con.prepareStatement(q);
                pst.execute();
        
                JOptionPane.showMessageDialog(null, "Updated Successfully");
        
                //Load Table
                tableLoad();
                
                txtUrUserCode.setText("");
                txtUrUserName.setText("");
                pwUrPassword.setText("");
                pwUrCpassword.setText("");
                txtUrSearch.setText("");
                cmbStatus.setSelectedItem("Active");
                txtUrUserCode.requestFocus();
                    
                    }
                       
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Duplicate User Codes, Cannot Process");
             //JOptionPane.showMessageDialog(null, e);
             txtUrUserCode.setText("");
             txtUrUserCode.requestFocus();
         }

    }
    
    
    public void updateUser(){
        
        txtUrUserCode.setEditable(false); 
        
        int x = JOptionPane.showConfirmDialog(null, "Do you need to update?");
        
        if (x == 0){
        String uCode = txtUrUserCode.getText();
        String uName = txtUrUserName.getText();
        String pwd = pwUrPassword.getText();
        String cPwd = pwUrCpassword.getText();
        String admin = cmbUrAdmin.getSelectedItem().toString();
        String status = cmbStatus.getSelectedItem().toString(); 
        int numCount = 0;
        int capCount = 0;
        
        try {
                
            for(int i =0; i < pwd.length(); i++){
            if ((pwd.charAt(i)>47 && pwd.charAt(i)<58)){
                numCount++;
            }
            
            if((pwd.charAt(i)>64 && pwd.charAt(i)< 91))
                capCount++;
            }
            
                if (uCode.equals ("")){
            JOptionPane.showMessageDialog(null, "User Code is Required");
            txtUrUserCode.requestFocus();
            }
                
                else if (uName.equals ("")){
            JOptionPane.showMessageDialog(null, "User Name is Required");
            txtUrUserName.requestFocus();
            }
                
                else if(pwd.length()< 8 /*&& pwd.length() >0*/){
             
            JOptionPane.showMessageDialog(null, "Password is Too Short");
            pwUrPassword.requestFocus();
         
         }
                
                else if(numCount < 2 /*&& pwd.length() >0*/ ){    
            JOptionPane.showMessageDialog(null, "Not enough numerals in the password");
            pwUrPassword.requestFocus();
         }
         
                else if(capCount < 2 /*&& pwd.length() >0*/){    
            JOptionPane.showMessageDialog(null, "Not enough Capital Letters in the password");
            pwUrPassword.requestFocus();
         }
          
                else if (pwd.equals ("")){
            JOptionPane.showMessageDialog(null, "Password is Required");
            pwUrPassword.requestFocus();
            }
        
                else if (cPwd.equals ("")){
            JOptionPane.showMessageDialog(null, "Password Confirmation is Required");
            pwUrCpassword.requestFocus();
            }
        
            
                else if(!pwd.equals(cPwd)){
            JOptionPane.showMessageDialog(this, "Password does not match, Pls check"); 
            pwUrCpassword.setText("");
            pwUrCpassword.requestFocus();
            }       
        
              else{  
                    
                    String encripString1 = getEncodedString(pwd);//Emcription
                    //String encripString2 = getEncodedString(cPwd);//Emcription2

                
                String sql = "UPDATE userregistration SET userName ='"+ uName +"', password= '"+ encripString1 +"', isAdmin = '"+ admin +"', Status = '"+ status +"' WHERE userCode = '"+ uCode +"' ";
                
                pst = con.prepareStatement(sql);
                pst.execute();
                
                //Table Load
                tableLoad();
                
                txtUrUserCode.setText("");
                txtUrUserName.setText("");
                pwUrPassword.setText("");
                pwUrCpassword.setText("");
                txtUrSearch.setText("");
                cmbUrAdmin.setSelectedItem("User");
                cmbStatus.setSelectedItem("Active");
                cmbStatus.setEnabled(false);
                txtUrUserCode.requestFocus();
                
                
                
                btnUrAdd.setEnabled(true);
                txtUrUserCode.setEditable(true);
                
                
            }
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println(e);
                
            }

        }

    }
    
    
    
    public void deleteUser(){
    
        int x = JOptionPane.showConfirmDialog (null, "Do You need to delete the record?");
        
        if (x==0){
            String uCode = txtUrUserCode.getText();
            
            String sql = "DELETE from userregistration WHERE userCode ='"+ uCode +"'";
            try {
                pst = con.prepareStatement(sql);
                pst.execute();
                
                //Table Load
                tableLoad();
                
                txtUrUserCode.setText("");
                txtUrUserName.setText("");
                pwUrPassword.setText("");
                pwUrCpassword.setText("");
                txtUrSearch.setText("");
                
                btnUrAdd.setEnabled(true);
                txtUrUserCode.setEditable(true);
                           
                                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        
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

        jScrollPane1 = new javax.swing.JScrollPane();
        userRegnTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtUrSearch = new javax.swing.JTextField();
        btnUrSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnUrAdd = new javax.swing.JButton();
        btnUrReset = new javax.swing.JButton();
        btnUrCancel = new javax.swing.JButton();
        btnUrUpdate = new javax.swing.JButton();
        btnUrDelete = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cmbUrAdmin = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        pwUrCpassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        pwUrPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        txtUrUserName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUrUserCode = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User Registratipn");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("userRegister"); // NOI18N
        setUndecorated(true);

        userRegnTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        userRegnTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        userRegnTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userRegnTableMouseClicked(evt);
            }
        });
        userRegnTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userRegnTableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                userRegnTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(userRegnTable);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Usre Name");

        txtUrSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUrSearch, org.jdesktop.beansbinding.ObjectProperty.create(), txtUrSearch, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUrSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUrSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtUrUserCode, org.jdesktop.beansbinding.ObjectProperty.create(), btnUrSearch, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUrSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUrSearchActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(3, 35, 55));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
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

        jButton6.setBackground(new java.awt.Color(3, 35, 55));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainInterfaces/HED ICON.png"))); // NOI18N
        jButton6.setBorder(null);
        jPanel2.add(jButton6);
        jButton6.setBounds(10, 10, 30, 20);

        jButton7.setBackground(new java.awt.Color(3, 35, 55));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Programming-Minimize-Window-icon resize.png"))); // NOI18N
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7);
        jButton7.setBounds(710, 10, 30, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Register User");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(60, 10, 250, 20);

        btnUrAdd.setBackground(new java.awt.Color(3, 35, 55));
        btnUrAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUrAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnUrAdd.setText("Add");
        btnUrAdd.setPreferredSize(new java.awt.Dimension(100, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUrReset, org.jdesktop.beansbinding.ObjectProperty.create(), btnUrAdd, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUrAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUrAddActionPerformed(evt);
            }
        });
        btnUrAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUrAddKeyPressed(evt);
            }
        });

        btnUrReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUrReset.setText("Reset");
        btnUrReset.setPreferredSize(new java.awt.Dimension(100, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUrCancel, org.jdesktop.beansbinding.ObjectProperty.create(), btnUrReset, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUrReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUrResetActionPerformed(evt);
            }
        });
        btnUrReset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUrResetKeyPressed(evt);
            }
        });

        btnUrCancel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUrCancel.setText("Close");
        btnUrCancel.setPreferredSize(new java.awt.Dimension(100, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUrUpdate, org.jdesktop.beansbinding.ObjectProperty.create(), btnUrCancel, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUrCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUrCancelActionPerformed(evt);
            }
        });
        btnUrCancel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUrCancelKeyPressed(evt);
            }
        });

        btnUrUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUrUpdate.setText("Update");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUrDelete, org.jdesktop.beansbinding.ObjectProperty.create(), btnUrUpdate, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUrUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUrUpdateActionPerformed(evt);
            }
        });
        btnUrUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUrUpdateKeyPressed(evt);
            }
        });

        btnUrDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnUrDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUrDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnUrDelete.setText("Delete");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtUrSearch, org.jdesktop.beansbinding.ObjectProperty.create(), btnUrDelete, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUrDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUrDeleteActionPerformed(evt);
            }
        });
        btnUrDelete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUrDeleteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUrAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUrReset, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUrCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUrUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUrDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUrAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUrReset, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUrCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUrUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUrDelete))
                .addGap(20, 20, 20))
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cmbUrAdmin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin", " ", " ", " " }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUrAdd, org.jdesktop.beansbinding.ObjectProperty.create(), cmbUrAdmin, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        cmbUrAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbUrAdminKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("User Role");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cmbUrAdmin, org.jdesktop.beansbinding.ObjectProperty.create(), pwUrCpassword, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        pwUrCpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pwUrCpasswordKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Confirm Password");

        pwUrPassword.setToolTipText("Password Must be contained atleast 8 characors including atleast 2 Capital letters and 2 numbers");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pwUrCpassword, org.jdesktop.beansbinding.ObjectProperty.create(), pwUrPassword, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        pwUrPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pwUrPasswordKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Password");

        txtUrUserName.setPreferredSize(new java.awt.Dimension(150, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pwUrPassword, org.jdesktop.beansbinding.ObjectProperty.create(), txtUrUserName, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtUrUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUrUserNameKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("User's Name");

        txtUrUserCode.setPreferredSize(new java.awt.Dimension(150, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtUrUserName, org.jdesktop.beansbinding.ObjectProperty.create(), txtUrUserCode, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtUrUserCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUrUserCodeKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("User Code");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Status");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));
        cmbStatus.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtUrUserName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbUrAdmin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUrUserCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pwUrPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addComponent(pwUrCpassword, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUrUserCode, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUrUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwUrPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwUrCpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbUrAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtUrSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUrSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnUrSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtUrSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        setSize(new java.awt.Dimension(749, 447));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUrResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUrResetActionPerformed
        
        txtUrUserCode.setText("");
        txtUrUserName.setText("");
        pwUrPassword.setText("");
        pwUrCpassword.setText("");
        txtUrSearch.setText("");
        cmbUrAdmin.setSelectedItem("User");
        cmbStatus.setSelectedItem("Active");
        cmbStatus.setEnabled(false);
        
       btnUrAdd.setEnabled(true);
       txtUrUserCode.setEditable(true);
       tableLoad();
                
        
        
    }//GEN-LAST:event_btnUrResetActionPerformed

    private void btnUrCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUrCancelActionPerformed

           this.dispose();
           
    }//GEN-LAST:event_btnUrCancelActionPerformed

    private void btnUrAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUrAddActionPerformed
       
            addUser();
        
                
    }//GEN-LAST:event_btnUrAddActionPerformed

    private void userRegnTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userRegnTableMouseClicked
        
        int r = userRegnTable.getSelectedRow();
        String uCode = userRegnTable.getValueAt(r, 0).toString();
        String uName = userRegnTable.getValueAt(r, 1).toString();
        String admin = userRegnTable.getValueAt(r, 2).toString();
        String status = userRegnTable.getValueAt(r, 3).toString();
        
        txtUrUserCode.setText(uCode);
        txtUrUserName.setText(uName);
        cmbUrAdmin.setSelectedItem(admin);
        cmbStatus.setSelectedItem(status);
                
        btnUrAdd.setEnabled(false);
        txtUrUserCode.setEditable(false);
        cmbStatus.setEnabled(true);
        
    }//GEN-LAST:event_userRegnTableMouseClicked

    private void btnUrUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUrUpdateActionPerformed

        updateUser();
        
    }//GEN-LAST:event_btnUrUpdateActionPerformed

    private void btnUrDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUrDeleteActionPerformed
        
        deleteUser();
        
    }//GEN-LAST:event_btnUrDeleteActionPerformed

    private void btnUrSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUrSearchActionPerformed
        
        String uName = txtUrSearch.getText();
        
        String sql = "SELECT userCode as 'User Code', userName as 'User Name', isAdmin as 'User Role' from userregistration WHERE userName LIKE '%"+ uName +"%' ";
        
        try {
            
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            userRegnTable.setModel(DbUtils.resultSetToTableModel(rs));
            
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
    }//GEN-LAST:event_btnUrSearchActionPerformed

    private void userRegnTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userRegnTableKeyPressed
          // TODO add your handling code here:
    }//GEN-LAST:event_userRegnTableKeyPressed

    private void userRegnTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userRegnTableKeyReleased
       
        int r = userRegnTable.getSelectedRow(); 
        String uCode = userRegnTable.getValueAt(r, 0).toString();
        String uName = userRegnTable.getValueAt(r, 1).toString();
        String admin = userRegnTable.getValueAt(r, 2).toString();
        String status = userRegnTable.getValueAt(r, 3).toString();
        
        txtUrUserCode.setText(uCode);
        txtUrUserName.setText(uName);
        cmbUrAdmin.setSelectedItem(admin);
        cmbStatus.setSelectedItem(status);
        
    }//GEN-LAST:event_userRegnTableKeyReleased

    private void txtUrUserCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUrUserCodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUrUserCodeKeyPressed

    private void txtUrUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUrUserNameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUrUserNameKeyPressed

    private void pwUrPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwUrPasswordKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwUrPasswordKeyPressed

    private void pwUrCpasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwUrCpasswordKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwUrCpasswordKeyPressed

    private void cmbUrAdminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbUrAdminKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUrAdminKeyPressed

    private void btnUrAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUrAddKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            addUser();
        }

    }//GEN-LAST:event_btnUrAddKeyPressed

    private void btnUrResetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUrResetKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            txtUrUserCode.setText("");
            txtUrUserName.setText("");
            pwUrPassword.setText("");
            pwUrCpassword.setText("");
            txtUrSearch.setText("");
            cmbUrAdmin.setSelectedItem("User");
            cmbStatus.setSelectedItem("Active");
            cmbStatus.setEnabled(false);

        
            btnUrAdd.setEnabled(true);
            txtUrUserCode.setEditable(true);
            
        }

    }//GEN-LAST:event_btnUrResetKeyPressed

    private void btnUrCancelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUrCancelKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            this.dispose();
        }

    }//GEN-LAST:event_btnUrCancelKeyPressed

    private void btnUrUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUrUpdateKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            updateUser();
        }

    }//GEN-LAST:event_btnUrUpdateKeyPressed

    private void btnUrDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUrDeleteKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            deleteUser();
            
        }
  
    }//GEN-LAST:event_btnUrDeleteKeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        
        x = evt.getX();
        y = evt.getY();
        
        
        
    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx-x, yy-y);
        
        
    }//GEN-LAST:event_jPanel2MouseDragged

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
            java.util.logging.Logger.getLogger(UserRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUrAdd;
    private javax.swing.JButton btnUrCancel;
    private javax.swing.JButton btnUrDelete;
    private javax.swing.JButton btnUrReset;
    private javax.swing.JButton btnUrSearch;
    private javax.swing.JButton btnUrUpdate;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbUrAdmin;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField pwUrCpassword;
    private javax.swing.JPasswordField pwUrPassword;
    private javax.swing.JTextField txtUrSearch;
    private javax.swing.JTextField txtUrUserCode;
    private javax.swing.JTextField txtUrUserName;
    private javax.swing.JTable userRegnTable;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private void elseif(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getEncodedString(String pwd) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return Base64.getEncoder().encodeToString(pwd.getBytes());
    }

private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }


}
