/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import mycode.DBconnect;
import net.proteanit.sql.DbUtils;


public class CustomerMaster extends javax.swing.JFrame {
    int x,y;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public CustomerMaster() {
        setIconImage();
        initComponents();
        
        //Connect to DB
        con = DBconnect.connect();
        
        //Load the table
        tableLoad();
        
        //Customer Code Creation
        customerCodeCreation();
        
        btnDelete.setVisible(false);
                        
    }
    
    public void tableLoad(){
        
        try {
            
            String sql = "SELECT CustomerCode As 'Customer Code', CustomerName as 'Customer Name', PostalAddress As 'Postal Address', NicOrBrNo as 'NIC / BR No', Phone1, Phone2, FaxNo As 'Fax No', eMailAddress, WebSite, ContactPerson, Status FROM customermaster";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            customerTable.setModel(DbUtils.resultSetToTableModel(rs));
                        
            
        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
                  
    }
    
        
    
    public void customerCodeCreation(){ 
        
       
        try {
            Statement st = con.createStatement();
                ResultSet rs1 = st.executeQuery("select MAX(CustomerCode) from customermaster");
            rs1.next();
            
            rs1.getString("MAX(CustomerCode)");
            
            if(rs1.getString("MAX(CustomerCode)")== null){
                txtCusCode.setText("CUS00001");
            }
            else{
                long id = Long.parseLong(rs1.getString("MAX(CustomerCode)").substring(3, rs1.getString("MAX(CustomerCode)").length()));
                id++;
                txtCusCode.setText("CUS" + String.format("%05d", id));
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    
    public void addCustomer(){
        
        String cusCode = txtCusCode.getText();
        String cusName = txtCusName.getText();
        String pAddress = txaPostAddress.getText();
        String nic = txtNic.getText();
        String phn1 = txtPhn1.getText();
        String phn2 = txtPhn2.getText();
        String fax = txtFax.getText();
        String email = txtEmail.getText();
        String web = txtWeb.getText();
        String contact = txtContact.getText();
        String status = cmbStatus.getSelectedItem().toString();
//        String inactive = "Active";
//        if (chkInactive.isSelected()){
//            inactive = "Inactive";
//        }
        
        try {
            
            if (cusCode.equals ("")){
            JOptionPane.showMessageDialog(null, "Customer Code is Required");
            txtCusCode.requestFocus();
            }
            
            else if (cusName.equals ("")){
            JOptionPane.showMessageDialog(null, "Customer Name is Required");
            txtCusName.requestFocus();
            }
            
                        
            else if (pAddress.equals ("")){
            JOptionPane.showMessageDialog(null, "Postal Address is Required");
            txaPostAddress.requestFocus();
            }
            
            else if (nic.equals ("")){
            JOptionPane.showMessageDialog(null, "NIC or Business Registration No is Required");
            txtNic.requestFocus();
            }
            
            else if (phn1.equals ("")){
            JOptionPane.showMessageDialog(null, "Telephone No is Required");
            txtPhn1.requestFocus();
            }
            
                  
            else{
            String q = "INSERT INTO customermaster (CustomerCode, CustomerName, PostalAddress, NicOrBrNo, Phone1, Phone2, FaxNo, eMailAddress, WebSite, ContactPerson, Status) values ('"+ cusCode +"','"+ cusName +"','"+ pAddress +"', '"+ nic +"','"+  phn1 +"' ,'"+ phn2 +"','"+ fax +"','"+ email +"','"+ web +"','"+ contact +"', '"+ status +"')";
                pst = con.prepareStatement(q);
                pst.execute();
                
                JOptionPane.showMessageDialog(this, "Updated Successfully");
                //System.out.println ("Updated Successfully");
                
                customerCodeCreation();
                
                tableLoad();
                
                //txtCusCode.setText("");
                txtCusName.setText("");
                txaPostAddress.setText("");
                txtNic.setText("");
                txtPhn1.setText("");
                txtPhn2.setText("");
                txtFax.setText("");
                txtEmail.setText("");
                txtWeb.setText("");
                txtContact.setText("");
                txtSearch.setText("");
                cmbStatus.setSelectedItem("Active");
                //chkInactive.setSelected(false);
                txtCusName.requestFocus();
                //customerCodeCreation();
            }
            
            
        } catch (SQLException e) {
            
             JOptionPane.showMessageDialog(null, e);
             Logger.getLogger(CustomerMaster.class.getName()).log(Level.SEVERE, null, e);
             
        }

    }
    
    
    public void customerUpdate(){
    
    
        int x = JOptionPane.showConfirmDialog(null, "Do you need to update the Customer?");
        
        if (x == 0){
        String cusCode = txtCusCode.getText();
        String cusName = txtCusName.getText();
        String pAddress = txaPostAddress.getText();
        String nic = txtNic.getText();
        String phn1 = txtPhn1.getText();
        String phn2 = txtPhn2.getText();
        String fax = txtFax.getText();
        String email = txtEmail.getText();
        String web = txtWeb.getText();
        String contact = txtContact.getText();
        String status = cmbStatus.getSelectedItem().toString();
//        String inactive = "Active";
//        if (chkInactive.isSelected()){
//            inactive = "Inactive";
//        
//        }
            
            try {
                
                if (cusCode.equals ("")){
            JOptionPane.showMessageDialog(null, "Customer Code is Required");
            txtCusCode.requestFocus();
            }
            
                else if (cusName.equals ("")){
            JOptionPane.showMessageDialog(null, "Customer Name is Required");
            txtCusName.requestFocus();
            }
                    
                else if (pAddress.equals ("")){
            JOptionPane.showMessageDialog(null, "Postal Address is Required");
            txaPostAddress.requestFocus();
            }
            
                else if (nic.equals ("")){
            JOptionPane.showMessageDialog(null, "NIC or Business Registration No is Required");
            txtNic.requestFocus();
            }
                
                else if (phn1.equals ("")){
            JOptionPane.showMessageDialog(null, "Telephone No is Required");
            txtPhn1.requestFocus();
            }
                
               else{
                String sql = "UPDATE customermaster SET CustomerCode ='"+ cusCode +"', CustomerName= '"+ cusName +"', PostalAddress = '"+ pAddress +"', NicOrBrNo = '"+ nic +"', Phone1 = '"+ phn1 +"', Phone2 = '"+ phn2 +"', FaxNo = '"+ fax +"',eMailAddress = '"+ email +"', WebSite = '"+ web +"',ContactPerson = '"+ contact +"', Status = '"+ status +"' WHERE CustomerCode = '"+ cusCode +"' ";
                pst = con.prepareStatement(sql);
                pst.execute();
                
                customerCodeCreation();
                
                //Table Load
                tableLoad();
                
                //txtCusCode.setText("");
                txtCusName.setText("");
                txaPostAddress.setText("");
                txtNic.setText("");
                txtPhn1.setText("");
                txtPhn2.setText("");
                txtFax.setText("");
                txtEmail.setText("");
                txtWeb.setText("");
                txtContact.setText("");
                txtSearch.setText("");
//                chkInactive.setSelected(false);
                cmbStatus.setSelectedItem("Active");

                btnAdd.setEnabled(true);
                //txtCusCode.setEditable(true);
                
            }
                           
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
                
            }
        }

    }
    
    
    public void deleteCustomer(){
        
        int x = JOptionPane.showConfirmDialog (null, "Do You need to delete the Customer?");
        
        if (x==0){
            String cusCode = txtCusCode.getText();
            
            String sql = "DELETE from customermaster WHERE CustomerCode ='"+ cusCode +"'";
            try {
                pst = con.prepareStatement(sql);
                pst.execute();
                
                customerCodeCreation();
                
                //Table Load
                tableLoad();
                
                //txtCusCode.setText("");
                txtCusName.setText("");
                txaPostAddress.setText("");
                txtNic.setText("");
                txtPhn1.setText("");
                txtPhn2.setText("");
                txtFax.setText("");
                txtEmail.setText("");
                txtWeb.setText("");
                txtContact.setText("");
                txtSearch.setText("");
                cmbStatus.setSelectedItem("Active");
//                chkInactive.setSelected(false);
                
                btnAdd.setEnabled(true);
                //txtCusCode.setEditable(true);
                
              
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        
        }
    
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jScrollPane3 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCusCode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCusName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaPostAddress = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        txtNic = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPhn1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPhn2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFax = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtWeb = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        btnDelete = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Customer Master");
        setName("CustomerMaster"); // NOI18N
        setUndecorated(true);

        customerTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerTableMouseClicked(evt);
            }
        });
        customerTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                customerTableKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(customerTable);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Customer Name");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnSearch, org.jdesktop.beansbinding.ObjectProperty.create(), txtSearch, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnSearch.setText("Search");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtCusName, org.jdesktop.beansbinding.ObjectProperty.create(), btnSearch, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(3, 35, 55));
        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnReset, org.jdesktop.beansbinding.ObjectProperty.create(), btnAdd, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddKeyPressed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setText("Reset");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnCancel, org.jdesktop.beansbinding.ObjectProperty.create(), btnReset, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        btnReset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnResetKeyPressed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 0, 0));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnDelete, org.jdesktop.beansbinding.ObjectProperty.create(), btnUpdate, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        btnUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUpdateKeyPressed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancel.setText("Close");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUpdate, org.jdesktop.beansbinding.ObjectProperty.create(), btnCancel, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        btnCancel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Customer Code");

        txtCusCode.setEditable(false);
        txtCusCode.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCusCode.setForeground(new java.awt.Color(0, 0, 153));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtCusName, org.jdesktop.beansbinding.ObjectProperty.create(), txtCusCode, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtCusCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusCodeActionPerformed(evt);
            }
        });
        txtCusCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCusCodeKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Customer Name");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txaPostAddress, org.jdesktop.beansbinding.ObjectProperty.create(), txtCusName, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtCusName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusNameActionPerformed(evt);
            }
        });
        txtCusName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCusNameKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Postal Address ");

        txaPostAddress.setColumns(20);
        txaPostAddress.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtNic, org.jdesktop.beansbinding.ObjectProperty.create(), txaPostAddress, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txaPostAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaPostAddressKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txaPostAddress);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("NIC / BR No");

        txtNic.setToolTipText("Enter National Identity Card No or Business Registration No");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtPhn1, org.jdesktop.beansbinding.ObjectProperty.create(), txtNic, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Status");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Phone Number1");

        txtPhn1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtPhn2, org.jdesktop.beansbinding.ObjectProperty.create(), txtPhn1, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtPhn1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhn1KeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Phione Number2");

        txtPhn2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtFax, org.jdesktop.beansbinding.ObjectProperty.create(), txtPhn2, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtPhn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhn2ActionPerformed(evt);
            }
        });
        txtPhn2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhn2KeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Fax Number");

        txtFax.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtEmail, org.jdesktop.beansbinding.ObjectProperty.create(), txtFax, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtFax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFaxActionPerformed(evt);
            }
        });
        txtFax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFaxKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("E-Mail");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtWeb, org.jdesktop.beansbinding.ObjectProperty.create(), txtEmail, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Web Site ");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtContact, org.jdesktop.beansbinding.ObjectProperty.create(), txtWeb, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWebActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Contact Person");

        txtContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContactActionPerformed(evt);
            }
        });

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive", " " }));

        btnDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtSearch, org.jdesktop.beansbinding.ObjectProperty.create(), btnDelete, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        btnDelete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDeleteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel13)
                        .addComponent(jLabel5)
                        .addComponent(jLabel1)))
                .addGap(63, 63, 63)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCusCode, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(txtCusName)
                            .addComponent(txtNic))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPhn1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtPhn2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel8)
                            .addComponent(txtPhn1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNic, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhn2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(jLabel4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtCusCode, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(191, 191, 191)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnDelete.getAccessibleContext().setAccessibleDescription("");
        btnDelete.getAccessibleContext().setAccessibleParent(this);

        jPanel3.setBackground(new java.awt.Color(3, 35, 55));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });
        jPanel3.setLayout(null);

        jButton6.setBackground(new java.awt.Color(3, 35, 55));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainInterfaces/HED ICON.png"))); // NOI18N
        jButton6.setBorder(null);
        jPanel3.add(jButton6);
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
        jPanel3.add(jButton7);
        jButton7.setBounds(780, 10, 30, 20);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Customer Master");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(60, 10, 120, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel14)
                .addGap(27, 27, 27)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addContainerGap(142, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        setSize(new java.awt.Dimension(822, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCusNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusNameActionPerformed

    private void txtFaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFaxActionPerformed

    private void txtContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactActionPerformed

    private void txtPhn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhn2ActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        this.dispose();
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        
        //txtCusCode.setText("");
        txtCusName.setText("");
        txaPostAddress.setText("");
        txtNic.setText("");
        txtPhn1.setText("");
        txtPhn2.setText("");
        txtFax.setText("");
        txtEmail.setText("");
        txtWeb.setText("");
        txtContact.setText("");
        txtSearch.setText("");
        cmbStatus.setSelectedItem("Active");
//        chkInactive.setSelected(false);
        
        btnAdd.setEnabled(true);
        //txtCusCode.setEditable(true);
        tableLoad();
        customerCodeCreation();
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        addCustomer();
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void customerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMouseClicked
        
        int r = customerTable.getSelectedRow();
        String cusCode = customerTable.getValueAt(r, 0).toString();
        String cusName = customerTable.getValueAt(r, 1).toString();
        String pAddress = customerTable.getValueAt(r, 2).toString();
        String nic = customerTable.getValueAt(r, 3).toString();
        String phn1 = customerTable.getValueAt(r, 4).toString();
        String phn2 = customerTable.getValueAt(r, 5).toString();
        String fax = customerTable.getValueAt(r, 6).toString();
        String eMail = customerTable.getValueAt(r, 7).toString();
        String web = customerTable.getValueAt(r, 8).toString();
        String contact = customerTable.getValueAt(r, 9).toString();
        String status = customerTable.getValueAt(r, 10).toString();
        
        txtCusCode.setText(cusCode);
        txtCusName.setText(cusName);
        txaPostAddress.setText(pAddress);
        txtNic.setText(nic);
        txtPhn1.setText(phn1);
        txtPhn2.setText(phn2);
        txtFax.setText(fax);
        txtEmail.setText(eMail);
        txtWeb.setText(web);
        txtContact.setText(contact);
        cmbStatus.setSelectedItem(status);
//        if (inactive.equals("Active"))
//            chkInactive.setSelected(false);
//        else
//            chkInactive.setSelected(true);
        
        btnAdd.setEnabled(false);
        //txtCusCode.setEditable(false);
        
    }//GEN-LAST:event_customerTableMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        
        String cusName = txtSearch.getText();
        
        String sql = "SELECT CustomerCode, CustomerName, PostalAddress, NicOrBrNo, Phone1, Phone2, FaxNo, eMailAddress, WebSite, ContactPerson, Status from customermaster WHERE CustomerName LIKE '%"+ cusName +"%' ";
        
        try {
            
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            customerTable.setModel(DbUtils.resultSetToTableModel(rs));
            
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        
        customerUpdate();
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
        deleteCustomer();
                
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void customerTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerTableKeyReleased
        
        int r = customerTable.getSelectedRow();
        String cusCode = customerTable.getValueAt(r, 0).toString();
        String cusName = customerTable.getValueAt(r, 1).toString();
        String pAddress = customerTable.getValueAt(r, 2).toString();
        String nic = customerTable.getValueAt(r, 3).toString();
        String phn1 = customerTable.getValueAt(r, 4).toString();
        String phn2 = customerTable.getValueAt(r, 5).toString();
        String fax = customerTable.getValueAt(r, 6).toString();
        String eMail = customerTable.getValueAt(r, 7).toString();
        String web = customerTable.getValueAt(r, 8).toString();
        String contact = customerTable.getValueAt(r, 9).toString();
        String status = customerTable.getValueAt(r, 10).toString();
        
        txtCusCode.setText(cusCode);
        txtCusName.setText(cusName);
        txaPostAddress.setText(pAddress);
        txtNic.setText(nic);
        txtPhn1.setText(phn1);
        txtPhn2.setText(phn2);
        txtFax.setText(fax);
        txtEmail.setText(eMail);
        txtWeb.setText(web);
        txtContact.setText(contact);
        cmbStatus.setSelectedItem(status);
//        if (inactive.equals("Active"))
//            chkInactive.setSelected(false);
//        else
//            chkInactive.setSelected(true);
        
        btnAdd.setEnabled(false);
        //txtCusCode.setEditable(false);
        
    }//GEN-LAST:event_customerTableKeyReleased

    private void txtCusCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusCodeActionPerformed

    private void txtCusCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCusCodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusCodeKeyPressed

    private void txtCusNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCusNameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusNameKeyReleased

    private void txaPostAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaPostAddressKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txaPostAddressKeyPressed

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            addCustomer();
            
        }

    }//GEN-LAST:event_btnAddKeyPressed

    private void btnResetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnResetKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            //txtCusCode.setText("");
            txtCusName.setText("");
            txaPostAddress.setText("");
            txtNic.setText("");
            txtPhn1.setText("");
            txtPhn2.setText("");
            txtFax.setText("");
            txtEmail.setText("");
            txtWeb.setText("");
            txtContact.setText("");
            txtSearch.setText("");
            cmbStatus.setSelectedItem("Active");
//            chkInactive.setSelected(false);
        
            btnAdd.setEnabled(true);
            //txtCusCode.setEditable(true);
            customerCodeCreation();

        }

    }//GEN-LAST:event_btnResetKeyPressed

    private void btnCancelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            this.dispose();
            
        }
        
        
    }//GEN-LAST:event_btnCancelKeyPressed

    private void btnUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUpdateKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
             customerUpdate();
            
        }

    }//GEN-LAST:event_btnUpdateKeyPressed

    private void btnDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDeleteKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            deleteCustomer();
            
        }

    }//GEN-LAST:event_btnDeleteKeyPressed

    private void txtPhn1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhn1KeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            String phn1 =txtPhn1.getText();
                if(phn1.length() == 10){
                txtPhn1.getText();
                }
                else{
                JOptionPane.showMessageDialog(null, "Invalid Telephone No", "Error", JOptionPane.ERROR_MESSAGE);
                txtPhn1.setText("");
                }
            
        }
            
        String phn1=txtPhn1.getText();
        int length=phn1.length();
        
        char c=evt.getKeyChar();
        
        //check for no 0 to 9
        if(evt.getKeyChar()>='0' && evt.getKeyChar()<='9'){
            //check for length more than 10 digit
            if(length<10){
                txtPhn1.setEditable(true);
            }else{
                //not editable more than 10
                txtPhn1.setEditable(false);
            }
        }else{
            //not allow keys  'back space' and 'delete' for edit
            if(evt.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode()==KeyEvent.VK_DELETE){
                //than allow editable
                txtPhn1.setEditable(true);
            }
//            else{
//                txtPhn1.setEditable(false);
//            }
        }

    }//GEN-LAST:event_txtPhn1KeyPressed

    private void txtPhn2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhn2KeyPressed
                                    
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            String phn1 =txtPhn2.getText();
                if(phn1.length() == 10){
                txtPhn2.getText();
                }
                else{
                JOptionPane.showMessageDialog(null, "Invalid Telephone No", "Error", JOptionPane.ERROR_MESSAGE); 
                txtPhn2.setText("");
                }
            
        }

        String phn1=txtPhn2.getText();
        int length=phn1.length();
        
        char c=evt.getKeyChar();
        
        //check for no 0 to 9
        if(evt.getKeyChar()>='0' && evt.getKeyChar()<='9'){
            //check for length more than 10 digit
            if(length<10){
                txtPhn2.setEditable(true);
            }else{
               //not editable more than 10
                txtPhn2.setEditable(false);
            }
        }else{
            //not allow keys  'back space' and 'delete' for edit
            if(evt.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode()==KeyEvent.VK_DELETE){
                //than allow editable
                txtPhn2.setEditable(true);
            }
//            else{
//                txtPhn2.setEditable(false);
//            }
        }
        
    }//GEN-LAST:event_txtPhn2KeyPressed

    private void txtFaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFaxKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            String phn1 =txtFax.getText();
                if(phn1.length() == 10){
                txtFax.getText();
                }
                else{
                JOptionPane.showMessageDialog(null, "Invalid Fax No", "Error", JOptionPane.ERROR_MESSAGE); 
                txtFax.setText("");
                }
        }

        String phn1=txtFax.getText();
        int length=phn1.length();
        
        char c=evt.getKeyChar();
        //check for no 0 to 9
        if(evt.getKeyChar()>='0' && evt.getKeyChar()<='9'){
            //check for length more than 10 digit
            if(length<10){
                txtFax.setEditable(true);
            }else{
                //not editable more than 10
                txtFax.setEditable(false);
            }
        }else{
            //not allow keys  'back space' and 'delete' for edit
            if(evt.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode()==KeyEvent.VK_DELETE){
                //than allow editable
                txtFax.setEditable(true);
            }
//            else{
//                txtFax.setEditable(false);
//            }
        }

        
    }//GEN-LAST:event_txtFaxKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        
        if (Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$", txtEmail.getText())) 
        {
            JOptionPane.showMessageDialog(null, "The email is valid", "Good!", JOptionPane.INFORMATION_MESSAGE);
            
        }
            else
        {
            JOptionPane.showMessageDialog(null, "Invalid Email Address", "Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.setText("");
        }

    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWebActionPerformed
                                     
        if (Pattern.matches("(http://|https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?", txtWeb.getText())) 
        {
            //((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)
            //"(http://|https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?
            JOptionPane.showMessageDialog(null, "The website URL is valid", "Good!", JOptionPane.INFORMATION_MESSAGE);
            
        }
            else
        {
            JOptionPane.showMessageDialog(null, "Invalid URL", "Error", JOptionPane.ERROR_MESSAGE);
            txtWeb.setText("");
        }

    }//GEN-LAST:event_txtWebActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged

        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx-x, yy-y);
    }//GEN-LAST:event_jPanel3MouseDragged

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed

        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel3MousePressed

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
            java.util.logging.Logger.getLogger(CustomerMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerMaster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JTable customerTable;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea txaPostAddress;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtCusCode;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtNic;
    private javax.swing.JTextField txtPhn1;
    private javax.swing.JTextField txtPhn2;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtWeb;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

 private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }




}
