/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;

import InformationPickers.itmPick1;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mycode.DBconnect;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Prasanna
 */
public class ItemMaster extends javax.swing.JFrame {
    
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int x,y;

    /**
     * Creates new form ItemMaster
     */
    public ItemMaster() {
        
        setIconImage();
        
        initComponents();
        
        btnDelete.setVisible(false);
        
        //Connect to DB
        con = DBconnect.connect();
        
        //Load the table
        tableLoad();
        
        ItemCodeCreation();
    }
    
    public void tableLoad(){
        
        try {
            
            String sql = "SELECT ItemCode As 'Item Code', ItemName as 'Item Name', qty As 'Quantity', UoM As 'Unit Of Measurement', Cost, UnitPrice As 'Selling Price', DiscountPct As 'Disc %', Status FROM itemmaster";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            itemTable.setModel(DbUtils.resultSetToTableModel(rs));
                        
            
        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
                  
    }
    
    public void ItemCodeCreation(){ 
        
        
        try {
            Statement st = con.createStatement();
                ResultSet rs1 = st.executeQuery("select MAX(ItemCode) from itemmaster");
            rs1.next();
            
            rs1.getString("MAX(ItemCode)");
            
            if(rs1.getString("MAX(ItemCode)")== null){
                txtItemCode.setText("ITM00001");
            }
            else{
                long id = Long.parseLong(rs1.getString("MAX(ItemCode)").substring(3, rs1.getString("MAX(ItemCode)").length()));
                id++;
                txtItemCode.setText("ITM" + String.format("%05d", id));
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void addItem(){
        
        txtQty.setText("0");
        String itmCode = txtItemCode.getText();
        String itmName = txtName.getText();
        String qty = txtQty.getText();
        String uom = cmbUom.getSelectedItem().toString();
        String cost = txtCost.getText();
        String uPrice = txtUnitPrice.getText();
        String disc = txtDiscount.getText();
        String status = cmbStatus.getSelectedItem().toString();
//        String inactive = "Active";
//        if (chkInactive.isSelected()){
//            inactive = "Inactive";
//                   
//        }
                
        try {
            
            if (itmCode.equals ("")){
            JOptionPane.showMessageDialog(null, "Item Code is Required");
            txtItemCode.requestFocus();
            }
            
            else if (itmName.equals ("")){
            JOptionPane.showMessageDialog(null, "Item Name is Required");
            txtName.requestFocus();
            }
            
            else if (cmbUom.getSelectedItem().equals("Select UoM Code")){
            JOptionPane.showMessageDialog(null, "UoM Code is Required");
            cmbUom.requestFocus();
            }
            
            else if (cost.equals ("") || (Double.parseDouble(txtCost.getText()) < 1)){
            JOptionPane.showMessageDialog(null, "Invalid Item Cost");
            txtCost.requestFocus();
            }
            
            else if (uPrice.equals ("") || (Double.parseDouble(txtUnitPrice.getText()) < 1)){
            JOptionPane.showMessageDialog(null, "Invalid Selling Price");
            txtUnitPrice.requestFocus();
            }
            
            else if (disc.equals ("")){
            JOptionPane.showMessageDialog(null, "Discount Rate of the is Required");
            txtDiscount.requestFocus();
            }
            
            else if (Double.parseDouble(txtDiscount.getText()) > 100){
                JOptionPane.showMessageDialog(null, "Invalid Discount Percentage");
                txtDiscount.setText("");
                txtDiscount.requestFocus();
            }
                       
            else{
           
            String q = "INSERT INTO itemmaster (ItemCode, ItemName, UoM, Cost, UnitPrice, DiscountPct, qty, Status) values ('"+ itmCode +"','"+ itmName +"', '"+ uom +"', '"+ cost +"','"+  uPrice +"' ,'"+ disc +"','"+ qty +"', '"+ status +"')";
                pst = con.prepareStatement(q);
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "Updated Successfully");
                
                ItemCodeCreation();
                
                tableLoad();
                
                //txtItemCode.setText("");
                txtName.setText("");
                txtQty.setText("");
                cmbUom.setSelectedItem("Select UoM Code");
                txtCost.setText("");
                txtUnitPrice.setText("");
                txtDiscount.setText("");
                txtSearch.setText("");
                cmbStatus.setSelectedItem("Active");
                //chkInactive.setSelected(false);
                txtName.requestFocus();
                //ItemCodeCreation();
            }
            
            
        } catch (SQLException e) {
            
             JOptionPane.showMessageDialog(null, e);
             
             Logger.getLogger(CustomerMaster.class.getName()).log(Level.SEVERE, null, e);
            
        }

    }
    
    
    public void updateItem(){
        
        int x = JOptionPane.showConfirmDialog(null, "Do you need to update?");
        
        if (x == 0){
        String iCode = txtItemCode.getText();
        String iName = txtName.getText();
        String qty = txtQty.getText();
        String uom = cmbUom.getSelectedItem().toString();
        String cost = txtCost.getText();
        String uPrice = txtUnitPrice.getText();
        String disc = txtDiscount.getText();
        String status = cmbStatus.getSelectedItem().toString();
//        String inactive = "Active";
//        if (chkInactive.isSelected()){
//            inactive = "Inactive";
//        }
        
        try {
                
                if (iCode.equals ("")){
            JOptionPane.showMessageDialog(null, "Item Code is Required");
            txtItemCode.requestFocus();
            }
            
                else if (iName.equals ("")){
            JOptionPane.showMessageDialog(null, "Item Name is Required");
            txtName.requestFocus();
            }
            
                else if (cmbUom.getSelectedItem().equals("Select UoM Code")){
            JOptionPane.showMessageDialog(null, "UoM Code is Required");
            txtQty.requestFocus();
            }
            
                else if (cost.equals ("") || (Double.parseDouble(txtCost.getText()) < 1)){
            JOptionPane.showMessageDialog(null, "Invalid Item Cost");
            }
            
            else if (uPrice.equals ("") || (Double.parseDouble(txtUnitPrice.getText()) < 1)){
            JOptionPane.showMessageDialog(null, "Invalid Selling Price");
            txtCost.requestFocus();
            }
            
                else if (disc.equals ("")){
            JOptionPane.showMessageDialog(null, "Item Discount is Required");
            txtDiscount.requestFocus();
            }
                
                else if (Double.parseDouble(txtDiscount.getText()) > 100){
                JOptionPane.showMessageDialog(null, "Invalid Discount Percentage");
                txtDiscount.setText("");
                txtDiscount.requestFocus();
            }
                
               else{
                String sql = "UPDATE itemmaster SET ItemName= '"+ iName +"', UoM = '"+ uom +"', Cost = '"+ cost +"', UnitPrice = '"+ uPrice +"', DiscountPct = '"+ disc +"', Status ='"+ status +"' WHERE ItemCode = '"+ iCode +"' ";
                //String sql = "UPDATE itemmaster SET ItemCode ='"+ iCode +"', ItemName= '"+ iName +"', qty='"+ qty +"', UoM = '"+ uom +"', Cost = '"+ cost +"', UnitPrice = '"+ uPrice +"', DiscountPct = '"+ disc +"', IsInactive = '"+ inactive +"' WHERE ItemCode = '"+ iCode +"' ";
                pst = con.prepareStatement(sql);
                pst.execute();
                
                //Table Load
                tableLoad();
                
                txtItemCode.setText("");
                txtName.setText("");
                txtQty.setText("");
                cmbUom.setSelectedItem("Select UoM Code");
                txtCost.setText("");
                txtUnitPrice.setText("");
                txtDiscount.setText("");
                txtSearch.setText("");
                cmbStatus.setSelectedItem("Active");
                //chkInactive.setSelected(false);
                
                btnAdd.setEnabled(true);
                txtItemCode.setEditable(true);
                ItemCodeCreation();
            }
                           

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                
            }
        }    
    }
    
    
    public void deleteItem(){
        
        int x = JOptionPane.showConfirmDialog (null, "Do You need to delete the record?");
        
        if (x==0){
            String iCode = txtItemCode.getText();
            
            String sql = "DELETE from itemmaster WHERE ItemCode ='"+ iCode +"'";
            try {
                pst = con.prepareStatement(sql);
                pst.execute();
                
                //Table Load
                tableLoad();
                
                txtItemCode.setText("");
                txtName.setText("");
                txtQty.setText("");
                cmbUom.setSelectedItem("Select UoM Code");
                txtCost.setText("");
                txtUnitPrice.setText("");
                txtDiscount.setText("");
                txtSearch.setText("");
                cmbStatus.setSelectedItem("Active");
                //chkInactive.setSelected(false);
                
                btnAdd.setEnabled(true);
                txtItemCode.setEditable(true);
                
                ItemCodeCreation();
                           
                                
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
        itemTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtItemCode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbUom = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtCost = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUnitPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Item Master");
        setName("ItemMaster"); // NOI18N
        setUndecorated(true);

        itemTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        itemTable.setModel(new javax.swing.table.DefaultTableModel(
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
        itemTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                itemTableMouseMoved(evt);
            }
        });
        itemTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemTableMouseClicked(evt);
            }
        });
        itemTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemTableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(itemTable);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Item Name");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnSearch, org.jdesktop.beansbinding.ObjectProperty.create(), txtSearch, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnSearch.setText("Search");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtName, org.jdesktop.beansbinding.ObjectProperty.create(), btnSearch, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtItemCode.setEditable(false);
        txtItemCode.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtItemCode.setForeground(new java.awt.Color(0, 0, 153));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtName, org.jdesktop.beansbinding.ObjectProperty.create(), txtItemCode, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtItemCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItemCodeKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Item Code");

        txtName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cmbUom, org.jdesktop.beansbinding.ObjectProperty.create(), txtName, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Item Name");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Quantity");

        txtQty.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Unit Of Measurement");

        cmbUom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbUom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select UoM Code", "Nos", "Meters", " " }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtCost, org.jdesktop.beansbinding.ObjectProperty.create(), cmbUom, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        cmbUom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbUomKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Cost Price LKR");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtUnitPrice, org.jdesktop.beansbinding.ObjectProperty.create(), txtCost, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostActionPerformed(evt);
            }
        });
        txtCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Selling Price LKR");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDiscount, org.jdesktop.beansbinding.ObjectProperty.create(), txtUnitPrice, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtUnitPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUnitPriceKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Discount %");

        txtDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountActionPerformed(evt);
            }
        });
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDiscountKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Status");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(txtQty)
                            .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbUom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCost, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(txtUnitPrice)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiscount)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 43, Short.MAX_VALUE)))))
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCost, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbUom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

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
        jButton7.setBounds(700, 10, 30, 20);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Item Master");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(60, 10, 120, 20);

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel)
                    .addComponent(btnUpdate))
                .addGap(20, 20, 20))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete))
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        bindingGroup.bind();

        setSize(new java.awt.Dimension(746, 620));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        this.dispose();
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        
        txtItemCode.setText("");
        txtName.setText("");
        txtQty.setText("");
        cmbUom.setSelectedItem("Select UoM Code");
        txtCost.setText("");
        txtUnitPrice.setText("");
        txtDiscount.setText("");
        txtSearch.setText("");
        cmbStatus.setSelectedItem("Active");
        //chkInactive.setSelected(false);
        
        btnAdd.setEnabled(true);
        txtItemCode.setEditable(true);
        tableLoad();
        ItemCodeCreation();
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        addItem();
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscountActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void itemTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemTableMouseClicked
        
        int r = itemTable.getSelectedRow();
        String iCode = itemTable.getValueAt(r, 0).toString();
        String iName = itemTable.getValueAt(r, 1).toString();
        String qty = itemTable.getValueAt(r, 2).toString();
        String uom = itemTable.getValueAt(r, 3).toString();
        String cost = itemTable.getValueAt(r, 4).toString();
        String sPrice = itemTable.getValueAt(r, 5).toString();
        String disc = itemTable.getValueAt(r, 6).toString();
        String status = itemTable.getValueAt(r, 7).toString();
        
        txtItemCode.setText(iCode);
        txtName.setText(iName);
        txtQty.setText(qty);
        cmbUom.setSelectedItem(uom);
        txtCost.setText(cost);
        txtUnitPrice.setText(sPrice);
        txtDiscount.setText(disc);
        cmbStatus.setSelectedItem(status);
//        if (inactive.equals("Active"))
//            chkInactive.setSelected(false);
//        else
//            chkInactive.setSelected(true);
        
        btnAdd.setEnabled(false);
        txtItemCode.setEditable(false);
              

    }//GEN-LAST:event_itemTableMouseClicked

    private void itemTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemTableMouseMoved

                
    }//GEN-LAST:event_itemTableMouseMoved

    private void itemTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemTableKeyPressed
        // TODO add your handling code here:      
    }//GEN-LAST:event_itemTableKeyPressed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        
        updateItem();
        
    }                                           


    private void btnUrSearchActionPerformed(java.awt.event.ActionEvent evt) {                                            

        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
        deleteItem();

        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        
        String iName = txtSearch.getText();
        
        String sql = "SELECT ItemCode, ItemName, qty, UoM, Cost, UnitPrice, DiscountPct, Status from itemmaster WHERE ItemName LIKE '%"+ iName +"%' ";
        
        try {
            
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            itemTable.setModel(DbUtils.resultSetToTableModel(rs));
            
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void itemTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemTableKeyReleased
        
        int r = itemTable.getSelectedRow(); 
        String iCode = itemTable.getValueAt(r, 0).toString();
        String iName = itemTable.getValueAt(r, 1).toString();
        String uom = itemTable.getValueAt(r, 2).toString();
        String cost = itemTable.getValueAt(r, 3).toString();
        String sPrice = itemTable.getValueAt(r, 4).toString();
        String disc = itemTable.getValueAt(r, 5).toString();
        String status = itemTable.getValueAt(r, 6).toString();
        
        txtItemCode.setText(iCode);
        txtName.setText(iName);
        cmbUom.setSelectedItem(uom);
        txtCost.setText(cost);
        txtUnitPrice.setText(sPrice);
        txtDiscount.setText(disc);
        cmbStatus.setSelectedItem(status);
        //chkInactive.setSelected(inactive);
        
    }//GEN-LAST:event_itemTableKeyReleased

    private void txtCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostKeyPressed
                
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            JOptionPane.showMessageDialog(null, "Please enter only numeric figures");
            txtCost.setText("");
            }

    }//GEN-LAST:event_txtCostKeyPressed

    private void txtUnitPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitPriceKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            JOptionPane.showMessageDialog(null, "Please enter only numeric figures");
            txtUnitPrice.setText("");
        }

    }//GEN-LAST:event_txtUnitPriceKeyPressed

    private void txtDiscountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            JOptionPane.showMessageDialog(null, "Please enter only numeric figures");
            txtDiscount.setText("");
        }
   
    }//GEN-LAST:event_txtDiscountKeyPressed

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameKeyPressed

    private void cmbUomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbUomKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUomKeyPressed

    private void txtItemCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemCodeKeyPressed

    private void txtCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostActionPerformed

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed
        
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
             
             addItem();
         }
        
    }//GEN-LAST:event_btnAddKeyPressed

    private void btnResetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnResetKeyPressed

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
        txtName.setText("");
        txtQty.setText("");
        cmbUom.setSelectedItem("Select UoM Code");
        txtCost.setText("");
        txtUnitPrice.setText("");
        txtDiscount.setText("");
        txtSearch.setText("");
        cmbStatus.setSelectedItem("Active");
//        chkInactive.setSelected(false);
        
        btnAdd.setEnabled(true);
        txtItemCode.setEditable(true);
        ItemCodeCreation();
            
        }

    }//GEN-LAST:event_btnResetKeyPressed

    private void btnCancelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            this.dispose();
            
        }
 
    }//GEN-LAST:event_btnCancelKeyPressed

    private void btnUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUpdateKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            updateItem();
            
        }

    }//GEN-LAST:event_btnUpdateKeyPressed

    private void btnDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDeleteKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            deleteItem();
            
        }

    }//GEN-LAST:event_btnDeleteKeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(HomePage.ICONIFIED);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged

        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx-x, yy-y);

    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed

        x = evt.getX();
        y = evt.getY();

    }//GEN-LAST:event_jPanel2MousePressed

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
            java.util.logging.Logger.getLogger(ItemMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemMaster().setVisible(true);
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
    private javax.swing.JComboBox<String> cmbUom;
    private javax.swing.JTable itemTable;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUnitPrice;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }

}
