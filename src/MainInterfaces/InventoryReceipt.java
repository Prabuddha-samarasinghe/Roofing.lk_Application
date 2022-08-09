/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;

import InformationPickers.InventoryReceptDoNumber;
import InformationPickers.itmPick2;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mycode.DBconnect;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Prasanna
 */
public class InventoryReceipt extends javax.swing.JFrame {
    int x,y;
    Connection con = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    ResultSet rs = null;
    DefaultTableModel df = null;
    
    public InventoryReceipt() {
        setIconImage();
        initComponents();
        
        //Connect to DB
        con = DBconnect.connect();
        
        documentNumberGeneration();
        
        //Set Current date and Disale Future dates 
        Date date = new Date();
        txtDocDate.getJCalendar().setMaxSelectableDate(date);
        txtDocDate.setDate(date);
    }
    
    public void selectItemCode(){
        
        try {
        String iCode = txtItmCode.getText();

        pst = con.prepareStatement("Select * from itemmaster where ItemCode = ?");
        pst.setString(1, iCode);
        rs = pst.executeQuery();
        
        if(rs.next() == false){
            JOptionPane.showMessageDialog(null, "Item Code is not found");
            txtItmCode.setText("");
            txtItmCode.requestFocus();
        }
        else if(rs.getString("Status").equals("Inactive")){
            JOptionPane.showMessageDialog(null, "Item Code is Inactive. Cannot Process");
            txtItmCode.setText("");
        }
        else {
            
            String iName = rs.getString("itemName");
            String uom = rs.getString("UoM");
            
            txtItmName.setText(iName.trim());
            txtUom.setText(uom.trim());
            
            txtItmName.setEditable(false);
            txtUom.setEditable(false);
            
            txtQty.requestFocus();
            
        }
         
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public void updateTable(){
        
         try{          
            if (txtItmCode.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Item Code is empty");
                txtItmCode.requestFocus();
            }
            
            else if (txtQty.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Quantity is empty");
                txtQty.requestFocus();
            }
            
            else if (Integer.parseInt(txtQty.getText()) < 1){
                JOptionPane.showMessageDialog(null, "Invalid Quantity");
                txtQty.requestFocus();
            }
            
            else if (txtDocNum.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Document Number is empty");
                txtDocNum.requestFocus();
            }
            
            else{
        
        
        df = (DefaultTableModel) inventRcptTable.getModel();
        df.addRow(new Object[]{
            
            txtDocNum.getText(),
            txtItmCode.getText(),
            txtItmName.getText(),
            txtQty.getText(),
            txtUom.getText()

        }
          );
        
        txtItmCode.setText("");
        txtItmName.setText("");
        txtQty.setText("");
        txtUom.setText("");
        txtItmCode.requestFocus();
       
    }
         }
         catch(Exception ex){
             JOptionPane.showMessageDialog(null, "Invalid Quantity," + " " +ex);
             txtQty.requestFocus();
         }
    }
    
    
    public void add(){
        
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String date = dt.format(now);
        
        try {
            
            if (!txtItmCode.getText().equals("") || !txtItmName.getText().equals("") || !txtQty.getText().equals("") || !txtUom.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Update the table before Add");
                btnUpdateTable.requestFocus();
            }
            
//            else if (inventRcptTable.){
//                JOptionPane.showMessageDialog(null, "Document Number is empty");
//            }
           
            else if (txtDocNum.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Document Number is empty");
                txtDocNum.requestFocus();
            }
            
//            else if (txtDocDate1.getText().isEmpty()){
//                JOptionPane.showMessageDialog(null, "Document date is empty");
//            }        
            
            else if (((JTextField)txtDocDate.getDateEditor().getUiComponent()).getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Document date is empty");
                txtDocDate.requestFocus();
            }        
            
            else if (!((JTextField)txtDocDate.getDateEditor().getUiComponent()).getText().equals(date)){
                JOptionPane.showMessageDialog(null, "Incorrect Document Date");
                txtDocDate.requestFocus();
            }        
            
            
            else{
            
                       
            String docNo = txtDocNum.getText();
            //String docDate1 = txtDocDate.getText();
            String refNo = txtRefNum.getText();
            String fromLoc = cmbFrmLoc.getSelectedItem().toString();
            String toLoc = cmbToLoc.getSelectedItem().toString();
            String rmks = txaRmks.getText();
            
            String query1 = "insert into inventrcpt (documentno, documentdate, systemdate, refno, fromlocation, tolocation, remarks) values (?,?,?,?,?,?,?)";
            
            pst = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, docNo);
            //pst.setString(2, docDate);
            pst.setString(2, ((JTextField)txtDocDate.getDateEditor().getUiComponent()).getText());
            pst.setString(3, date);
            pst.setString(4, refNo);
            pst.setString(5, fromLoc);
            pst.setString(6, toLoc);
            pst.setString(7, rmks);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            
            
            String query2 = "insert into inventrcptitem (documentno, itemcode,itemname, qty, uom) values (?, ?, ?, ?, ?)";
            pst1 = con.prepareStatement(query2);
            
            String docNum;
            String iCode;
            String iName;
            String qty;
            String uom;
            
            for (int i = 0; i<inventRcptTable.getRowCount(); i++){
                
                docNum = (String)inventRcptTable.getValueAt(i, 0);
                iCode = (String)inventRcptTable.getValueAt(i, 1);
                iName = (String)inventRcptTable.getValueAt(i, 2);
                qty = (String)inventRcptTable.getValueAt(i, 3);
                uom = (String)inventRcptTable.getValueAt(i, 4);
                
                pst1.setString(1, docNum);
                pst1.setString(2, iCode);
                pst1.setString(3, iName);
                pst1.setString(4, qty);
                pst1.setString(5, uom);
                pst1.executeUpdate();
            }
            
            
            String query3 = "update itemmaster set qty = qty + ? where ItemCode = ? ";
            pst2 = con.prepareStatement(query3);
            
            for (int i = 0; i<inventRcptTable.getRowCount(); i++){
                
                iCode = (String)inventRcptTable.getValueAt(i, 1);
                qty = (String)inventRcptTable.getValueAt(i, 3);
                
                pst2.setString(1, qty);
                pst2.setString(2, iCode);
                pst2.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this, "Inventory Receipt is Completed");
            
            documentNumberGeneration();
            
            txtItmCode.setText("");
            txtItmName.setText("");
            txtQty.setText("");
            txtUom.setText("");
            //txtDocNum.setText("");
            //txtDocDate1.setText("");
            ((JTextField)txtDocDate.getDateEditor().getUiComponent()).setText("");
            txtRefNum.setText("");
            txaRmks.setText("");
            inventRcptTable.setModel(new DefaultTableModel(null, new String []{"documentno", "itemcode","itemname", "qty", "uom"}));
            
            
            txtItmCode.requestFocus();
            
            txtItmName.setEditable(true);
            txtUom.setEditable(true);

          }              
                        
        } catch (SQLException ex) {
            //Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void viewInventoryReceipt(){
        
        try {
        String IrNo = txtDocNum.getText();
        
        pst = con.prepareStatement("Select * from inventrcpt where documentno = ?");
        pst.setString(1, IrNo);
        rs = pst.executeQuery();
        
        if(rs.next() == false){
            JOptionPane.showMessageDialog(null, "Incorrect Inventory Receipt Number");
            txtDocNum.setText("");
            txtDocNum.requestFocus();
        }
        else {
            
            String docDate = rs.getString("documentdate");
            String refNo = rs.getString("refno");
            String remarks = rs.getString("remarks");
            
            //txtDocDate1.setText(docDate.trim());
            ((JTextField)txtDocDate.getDateEditor().getUiComponent()).setText(docDate.trim());
            txtRefNum.setText(refNo.trim());           
            txaRmks.setText(remarks.trim());
            txtDocNum.requestFocus();
            
            pst1 = con.prepareStatement("Select documentno as 'Doc No', itemcode as 'Item Code', itemname as 'Item Name', qty as 'Quantity', uom as 'UoM' from inventrcptitem where documentno = ?");
            pst1.setString(1, IrNo);
            rs = pst1.executeQuery();
            
            inventRcptTable.setModel(DbUtils.resultSetToTableModel(rs));
            
            btnUpdateTable.setEnabled(false);
            btnAdd.setEnabled(false);
            btnRowDelete.setEnabled(false);
            
        }
         
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public void documentNumberGeneration(){ 
        
        
        try {
            Statement st = con.createStatement();
                ResultSet rs1 = st.executeQuery("select MAX(documentno) from inventrcpt");
            rs1.next();
            
            rs1.getString("MAX(documentno)");
            
            if(rs1.getString("MAX(documentno)")== null){
                txtDocNum.setText("RCP00001");
            }
            else{
                long id = Long.parseLong(rs1.getString("MAX(documentno)").substring(3, rs1.getString("MAX(documentno)").length()));
                id++;
                txtDocNum.setText("RCP" + String.format("%05d", id));
            
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(CustomerMaster.class.getName()).log(Level.SEVERE, null, ex);
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

        btnAdd = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRowDelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        inventRcptTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDocNum = new javax.swing.JTextField();
        cmbFrmLoc = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbToLoc = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtRefNum = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDocDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaRmks = new javax.swing.JTextArea();
        txtItmCode = new javax.swing.JTextField();
        txtItmName = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        txtUom = new javax.swing.JTextField();
        btnSelectItem = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnUpdateTable = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inventory Receipt");
        setName("InventoryReceipt"); // NOI18N
        setUndecorated(true);

        btnAdd.setBackground(new java.awt.Color(3, 35, 55));
        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnRowDelete, org.jdesktop.beansbinding.ObjectProperty.create(), btnAdd, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
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

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancel.setText("Close");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtItmCode, org.jdesktop.beansbinding.ObjectProperty.create(), btnCancel, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRowDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnRowDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRowDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnRowDelete.setText("Delete Row");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnReset, org.jdesktop.beansbinding.ObjectProperty.create(), btnRowDelete, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnRowDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRowDeleteActionPerformed(evt);
            }
        });

        inventRcptTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        inventRcptTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Doc No", "Item Code", "Item Name", "Quantity", "Uom"
            }
        ));
        inventRcptTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventRcptTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(inventRcptTable);

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
        jButton7.setBounds(730, 10, 30, 20);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Inventory Receipt");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(60, 10, 120, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Document No");

        txtDocNum.setEditable(false);
        txtDocNum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDocNum.setForeground(new java.awt.Color(0, 51, 153));
        txtDocNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDocNumKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDocNumKeyReleased(evt);
            }
        });

        cmbFrmLoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbFrmLoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Central Warehouse", "Correction" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Document date");

        cmbToLoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbToLoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nawala Showroom" }));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Reference No");

        txtRefNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txaRmks, org.jdesktop.beansbinding.ObjectProperty.create(), txtRefNum, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Item Code");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Item Name");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Quantity");

        txtDocDate.setDateFormatString("yyyy/MM/dd");
        txtDocDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("UoM ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("From Location");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("To Location");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Remarks");

        txaRmks.setColumns(20);
        txaRmks.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnAdd, org.jdesktop.beansbinding.ObjectProperty.create(), txaRmks, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(txaRmks);

        txtItmCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtItmName, org.jdesktop.beansbinding.ObjectProperty.create(), txtItmCode, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtItmCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItmCodeKeyPressed(evt);
            }
        });

        txtItmName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtQty, org.jdesktop.beansbinding.ObjectProperty.create(), txtItmName, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtUom, org.jdesktop.beansbinding.ObjectProperty.create(), txtQty, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
        });

        txtUom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUpdateTable, org.jdesktop.beansbinding.ObjectProperty.create(), txtUom, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnSelectItem.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSelectItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSelectItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectItemActionPerformed(evt);
            }
        });

        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbToLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtItmCode)
                                    .addComponent(txtItmName, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cmbFrmLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUom, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addComponent(btnSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRefNum, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDocNum)
                            .addComponent(txtDocDate, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtDocNum, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(txtItmName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtDocDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtRefNum, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtUom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel8)
                            .addComponent(cmbFrmLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cmbToLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnUpdateTable.setBackground(new java.awt.Color(255, 255, 153));
        btnUpdateTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdateTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        btnUpdateTable.setText("Update Table");
        btnUpdateTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addComponent(btnUpdateTable))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRowDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdateTable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRowDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );

        bindingGroup.bind();

        setSize(new java.awt.Dimension(774, 609));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        add();
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtItmCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItmCodeKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            selectItemCode();
        
        }
        
    }//GEN-LAST:event_txtItmCodeKeyPressed

    private void btnUpdateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTableActionPerformed
        
        updateTable();
                
    }//GEN-LAST:event_btnUpdateTableActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
             
        this.dispose();
        
        
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRowDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRowDeleteActionPerformed
        
        df.removeRow(inventRcptTable.getSelectedRow());
        
            txtItmCode.setText("");
            txtItmName.setText("");
            txtQty.setText("");
            txtUom.setText("");
        
    }//GEN-LAST:event_btnRowDeleteActionPerformed

    private void inventRcptTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventRcptTableMouseClicked

//        int r = inventRcptTable.getSelectedRow();
//        String iCode = inventRcptTable.getValueAt(r, 1).toString();
//        String iName = inventRcptTable.getValueAt(r, 2).toString();
//        String qty = inventRcptTable.getValueAt(r, 3).toString();
//        String uom = inventRcptTable.getValueAt(r, 4).toString();
//                
//        
//        txtItmCode.setText(iCode);
//        txtItmName.setText(iName);
//        txtQty.setText(qty);
//        txtUom.setText(uom);
        
//        btnAdd.setEnabled(false);
//        txtItmCode.setEditable(false);
        
    }//GEN-LAST:event_inventRcptTableMouseClicked

    private void txtDocNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocNumKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            viewInventoryReceipt();
        }
        
        
        
    }//GEN-LAST:event_txtDocNumKeyPressed

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            JOptionPane.showMessageDialog(null, "Please enter only numeric figures");
            txtQty.setText("");
            txtQty.requestFocus();
        }
        
    }//GEN-LAST:event_txtQtyKeyPressed

    private void txtDocNumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocNumKeyReleased
        
        btnUpdateTable.setEnabled(false);
        btnAdd.setEnabled(false);
        btnRowDelete.setEnabled(false);
        
    }//GEN-LAST:event_txtDocNumKeyReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        
        
            
            txtItmCode.setText("");
            txtItmName.setText("");
            txtQty.setText("");
            txtUom.setText("");
            //txtDocDate1.setText("");
            //((JTextField)txtDocDate.getDateEditor().getUiComponent()).setText("");
            txtRefNum.setText("");
            txaRmks.setText("");
            cmbFrmLoc.setSelectedItem("Central Warehouse");
            inventRcptTable.setModel(new DefaultTableModel(null, new String []{"documentno", "itemcode","itemname", "qty", "uom"}));
            documentNumberGeneration();
            txtDocNum.setEditable(false);
            btnUpdateTable.setEnabled(true);
            btnAdd.setEnabled(true);
            btnRowDelete.setEnabled(true);
            
            txtItmCode.requestFocus();
            
            txtItmName.setEditable(true);
            txtUom.setEditable(true);
            
            //Reset the date to current date
            Date date = new Date();
            txtDocDate.getJCalendar().setMaxSelectableDate(date);
            txtDocDate.setDate(date);
        
    }//GEN-LAST:event_btnResetActionPerformed

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

    private void btnSelectItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectItemActionPerformed

        txtItmCode.setText("");
        txtItmName.setText("");        
        txtUom.setText("");    
        txtQty.setText("");
        itmPick2 itmp = new itmPick2(this);
        itmp.setVisible(true);

    }//GEN-LAST:event_btnSelectItemActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        InventoryReceptDoNumber iRDNo = new InventoryReceptDoNumber(this);
        iRDNo.setVisible(true);
        
            txtDocNum.setEditable(true);
            btnUpdateTable.setEnabled(false);
            btnAdd.setEnabled(false);
            btnRowDelete.setEnabled(false);
        
            txtItmCode.setText("");
            txtItmName.setText("");
            txtQty.setText("");
            txtUom.setText("");
            cmbFrmLoc.setSelectedItem("Central Warehouse");
            
        
    }//GEN-LAST:event_btnViewActionPerformed

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
            java.util.logging.Logger.getLogger(InventoryReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventoryReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventoryReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventoryReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventoryReceipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRowDelete;
    private javax.swing.JButton btnSelectItem;
    private javax.swing.JButton btnUpdateTable;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<String> cmbFrmLoc;
    private javax.swing.JComboBox<String> cmbToLoc;
    private javax.swing.JTable inventRcptTable;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txaRmks;
    private com.toedter.calendar.JDateChooser txtDocDate;
    private javax.swing.JTextField txtDocNum;
    private javax.swing.JTextField txtItmCode;
    private javax.swing.JTextField txtItmName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtRefNum;
    private javax.swing.JTextField txtUom;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

 private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }
public void setC (String x){
    txtItmCode.setText(x);
    txtItmCode.requestFocus();

}
 public void setD (String y){
    txtDocNum.setText(y);
    txtDocNum.requestFocus();
 }


}
