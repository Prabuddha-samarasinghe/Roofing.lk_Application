/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;

import InformationPickers.itmPick;
import InformationPickers.CusPick;
import InformationPickers.SalesOrderDetailPicker2;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mycode.DBconnect;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Prasanna
 */
public class SalesOrder extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    ResultSet rs = null;
    DefaultTableModel df = null;
    int x,y;
    String userName;
    
    public SalesOrder() {
        initComponents();
        setIconImage();
        
       
        //Connect to DB
        con = DBconnect.connect();
        
        documentNumberGeneration();
        
        //Set Current date and Disale Future dates 
        Date date = new Date();
        txtDocDate.getJCalendar().setMaxSelectableDate(date);
        txtDocDate.setDate(date);
        
    }

    SalesOrder(String userName) {
        initComponents();
        setIconImage();
        //Connect to DB
        con = DBconnect.connect();
        this.userName=userName;
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
        }
        else if(rs.getString("Status").equals("Inactive")){
            JOptionPane.showMessageDialog(null, "Item Code is Inactive. Cannot Process");
            txtItmCode.setText("");
        }
        
        else {
            
            String iName = rs.getString("itemName");
            String uom = rs.getString("UoM");
            String up = rs.getString("UnitPrice");
            String disc = rs.getString("DiscountPct");
            
            txtItmName.setText(iName.trim());
            txtUom.setText(uom.trim());
            txtUp.setText(up.trim());
            txtDisc.setText(disc.trim());
            txtQty.requestFocus();
        }
         
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public void updateTable(){
        
        try {
            
            if (txtItmCode.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Item Code is empty");
                txtItmCode.requestFocus();
            }
            
            else if (txtItmName.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Item Name is empty");
                txtItmName.requestFocus();
            }
            
            else if (txtUp.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unit Price is empty");
                txtUp.requestFocus();
            }
            
            else if (txtQty.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Quantity is empty");
                txtQty.requestFocus();
            }
            
            else if (Integer.parseInt(txtQty.getText()) < 1){
                JOptionPane.showMessageDialog(null, "Invalid Quantity");
                txtQty.setText("");
                txtQty.requestFocus();
            }
            
            else if (txtUom.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "UoM Code is empty");
                txtUom.requestFocus();
            }
            
            else if (txtDisc.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Discount field is empty");
                txtDisc.requestFocus();
            }
            
            else if (txtDocNum.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Document Number is empty");
                txtDocNum.requestFocus();
            }
            
            else{
        
        double price = Double.parseDouble(txtUp.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double disc = Double.parseDouble(txtDisc.getText());
        
        double discVal = (price * qty)*(disc/100);
        double sTtl = ((price * qty)-discVal);
        
        
        df = (DefaultTableModel) salesOrderTable.getModel();
        df.addRow(new Object[]{
            
            txtDocNum.getText(),
            txtItmCode.getText(),
            txtItmName.getText(),
            txtUp.getText(),
            txtQty.getText(),
            txtUom.getText(),
            txtDisc.getText(),
            String.format("%.2f", sTtl)
        }
          );
        
        double sum = 0;
        
        for(int i =0; i < salesOrderTable.getRowCount(); i++){
            
            sum = sum + Double.parseDouble(salesOrderTable.getValueAt(i, 7).toString());
        
        }
        
        //txtTotal.setText(String.valueOf(sum));
        txtTotal.setText(String.format("%.2f", sum));
         
        txtItmCode.setText("");
        txtItmName.setText("");
        txtUp.setText("");
        txtQty.setText("");
        txtUom.setText("");
        txtDisc.setText("");
        txtItmCode.requestFocus();
        txtReceipt.setText(String.format("%.2f", 0.00));
        txtBalance.setText(String.format("%.2f", 0.00));
    }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(InventoryIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    

    public void selectCustomerCode(){
        
        try {
        String cCode = txtCusCode.getText();
        
        
        pst = con.prepareStatement("Select * from customermaster where CustomerCode = ?");
        pst.setString(1, cCode);
        rs = pst.executeQuery();
        
        if(rs.next() == false){
            JOptionPane.showMessageDialog(null, "Customer Code is not found");
            
            txtCusCode.setText("");
            txtCusName.setText("");
            txtCusCode.requestFocus();
        }
        else if(rs.getString("Status").equals("Inactive")){
            JOptionPane.showMessageDialog(null, "Customer Code is Inactive. Cannot Process");
            txtItmCode.setText("");
        }
        
        else {
            
            String cName = rs.getString("CustomerName");
                        
            txtCusName.setText(cName.trim());   
            txtDocDate.requestFocus();
        }
         
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public void add(){
        
        try {
            
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String date = dt.format(now);
                        
            if (!txtItmCode.getText().equals("") || !txtItmName.getText().equals("") || !txtUp.getText().equals("")  || !txtQty.getText().equals("") || !txtUom.getText().equals("") || !txtDisc.getText().equals("") ){
                JOptionPane.showMessageDialog(null, "Update the table before Add");
                btnUpdateTable.requestFocus();
            }
            
            else if (txtCusCode.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Customer Code is empty");
                txtCusCode.requestFocus();
            }
            
            else if (txtCusName.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Customer Name is empty");
                txtCusName.requestFocus();
            }
            
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
                        
            else if (txtTotal.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Total Field is empty");
                txtTotal.requestFocus();
            }
            
            else if (Double.parseDouble(txtTotal.getText()) < 1){
                JOptionPane.showMessageDialog(null, "Invalid Total");
                txtTotal.requestFocus();
            }
            
            else{
            
            
            String docNo = txtDocNum.getText();
            //String docDate = txtDocDate1.getText();
            String cusCode = txtCusCode.getText();
            String cusName = txtCusName.getText();
            String refNo = txtRefNum.getText();
            String rmks = txaRmks.getText();
            String total = txtTotal.getText();
            String receipt = txtReceipt.getText();
            String bal = txtBalance.getText();
            String status = cmbStatus.getSelectedItem().toString();
            
            String query1 = "insert into salesorder (documentno, documentdate, systemdate, customercode, customername, refno, remarks, total, receipt, balance, Status) values (?,?,?,?,?,?,?,?,?,?,?)";
            
            pst = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, docNo);
            //pst.setString(2, docDate);
            pst.setString(2, ((JTextField)txtDocDate.getDateEditor().getUiComponent()).getText());
            pst.setString(3, date);
            pst.setString(4, cusCode);
            pst.setString(5, cusName);
            pst.setString(6, refNo);
            pst.setString(7, rmks);
            pst.setString(8, total);
            pst.setString(9, receipt);
            pst.setString(10, bal);
            pst.setString(11, status);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
                  
            String query2 = "insert into salesorderitem (documentno, itemcode,itemname, unitprice, qty, uom, discountPct, subttl) values (?, ?, ?, ?, ?,?,?,?)";
            pst1 = con.prepareStatement(query2);
            
            String docNum;
            String iCode;
            String iName;
            String uPrice;
            String qty;
            String disc;
            String uom;
            String subTtl;
            
            for (int i = 0; i<salesOrderTable.getRowCount(); i++){
                
                docNum = (String)salesOrderTable.getValueAt(i, 0);
                iCode = (String)salesOrderTable.getValueAt(i, 1);
                iName = (String)salesOrderTable.getValueAt(i, 2);
                uPrice = (String)salesOrderTable.getValueAt(i, 3);
                qty = (String)salesOrderTable.getValueAt(i, 4);
                disc = (String)salesOrderTable.getValueAt(i, 5);
                uom = (String)salesOrderTable.getValueAt(i, 6);
                subTtl = (String)salesOrderTable.getValueAt(i, 7).toString();
                
//                pst1.setString(1, lastid);
                pst1.setString(1, docNum);
                pst1.setString(2, iCode);
                pst1.setString(3, iName);
                pst1.setString(4, uPrice);
                pst1.setString(5, qty);
                pst1.setString(6, disc);
                pst1.setString(7, uom);
                pst1.setString(8, subTtl);
                pst1.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this, "Sales Order is Completed");
            
            btnAdd.enable(false);
            
            documentNumberGeneration();
            
            txtItmCode.setText("");
            txtItmName.setText("");
            txtQty.setText("");
            txtUom.setText("");
            ((JTextField)txtDocDate.getDateEditor().getUiComponent()).setText("");
            txtRefNum.setText("");
            txaRmks.setText("");
            txtCusCode.setText("");
            txtCusName.setText("");
            txtTotal.setText("");
            txtReceipt.setText("");
            txtBalance.setText("");
            cmbStatus.setSelectedItem("Open");
            txtItmCode.requestFocus();
            salesOrderTable.setModel(new DefaultTableModel(null, new String []{"documentno", "itemcode","itemname", "unitprice", "qty", "uom", "discountPct", "subttl"}));
            
            }
                        
        } catch (Exception ex) {
            //Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex);
        }
        
    }
    
    
    public void viewSalesOrder(){
        
        try {
        String SoNo = txtDocNum.getText();
        
        pst = con.prepareStatement("Select * from salesorder where documentno = ?");
        pst.setString(1, SoNo);
        rs = pst.executeQuery();
        
        if(rs.next() == false){
            JOptionPane.showMessageDialog(null, "Incorrect Sales Order Number");     
            txtDocNum.requestFocus();
        }
        else {
            
            String cusCode = rs.getString("customercode");
            String cusName = rs.getString("customername");
            String docDate = rs.getString("documentdate");
            String refNo = rs.getString("refno");
            String remarks = rs.getString("remarks");
            String ttl = rs.getString("total");
            String rcpt = rs.getString("receipt");
            String bal = rs.getString("balance");
            String status = rs.getString("Status");
            
            txtCusCode.setText(cusCode.trim());
            txtCusName.setText(cusName.trim());
            //txtDocDate1.setText(docDate.trim());
            ((JTextField)txtDocDate.getDateEditor().getUiComponent()).setText(docDate.trim());
            txtRefNum.setText(refNo.trim());           
            txaRmks.setText(remarks.trim());
            txtTotal.setText(ttl.trim());
            txtReceipt.setText(rcpt.trim());
            txtBalance.setText(bal.trim());  
            cmbStatus.setSelectedItem(status.trim());
            txtDocNum.requestFocus();
            
            
            if(cmbStatus.getSelectedItem().equals("Closed")|| cmbStatus.getSelectedItem().equals("Canceled")){
                btnUpdate.setEnabled(false);
                cmbStatus.setEnabled(false);
                txtReceipt.setEditable(false);
            
            }
            else{
                btnUpdate.setEnabled(true);
                
                cmbStatus.setEnabled(true);
                txtReceipt.setEditable(true);
            
            }
            
                        
            pst1 = con.prepareStatement("Select documentno as 'Doc No', itemcode as 'Item Code', itemname as 'Item Name', UnitPrice as 'Unit Price' , qty as 'Quantity', uom as 'UoM', discountPct as 'Disc%', subttl as 'Sub Total (Rs)' from salesorderitem where documentno = ?");
            pst1.setString(1, SoNo);
            rs = pst1.executeQuery();
            
            salesOrderTable.setModel(DbUtils.resultSetToTableModel(rs));

            
        }
         
        } catch (SQLException ex) {
            Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
    
    public void documentNumberGeneration(){ 
        
        
        try {
            Statement st = con.createStatement();
                ResultSet rs1 = st.executeQuery("select MAX(documentno) from salesorder");
            rs1.next();
            
            rs1.getString("MAX(documentno)");
            
            if(rs1.getString("MAX(documentno)")== null){
                txtDocNum.setText("SOD00001");
            }
            else{
                long id = Long.parseLong(rs1.getString("MAX(documentno)").substring(3, rs1.getString("MAX(documentno)").length()));
                id++;
                txtDocNum.setText("SOD" + String.format("%05d", id));
            
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(CustomerMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updateSalesOrder(){
        
        int x = JOptionPane.showConfirmDialog(null, "Do you need to update?");
        
        if (x == 0){

        String docNo = txtDocNum.getText();
        String status = cmbStatus.getSelectedItem().toString();
        String receipt = txtReceipt.getText();
        String bal = txtBalance.getText();

        
        try {
                
                if (status.equals ("Closed")){
            JOptionPane.showMessageDialog(null, "You are not authorized for close a Sales Order manually");
            cmbStatus.requestFocus();
            }
          
                
               else{
                String sql = "UPDATE salesorder SET Status= '"+ status +"', Receipt = '"+ receipt +"', Balance = '"+ bal +"' WHERE DocumentNo = '"+ docNo +"' ";
                //String sql = "UPDATE itemmaster SET ItemCode ='"+ iCode +"', ItemName= '"+ iName +"', qty='"+ qty +"', UoM = '"+ uom +"', Cost = '"+ cost +"', UnitPrice = '"+ uPrice +"', DiscountPct = '"+ disc +"', IsInactive = '"+ inactive +"' WHERE ItemCode = '"+ iCode +"' ";
                pst = con.prepareStatement(sql);
                pst.execute();
                
            txtItmCode.setText("");
            txtItmName.setText("");
            txtQty.setText("");
            txtUom.setText("");
            ((JTextField)txtDocDate.getDateEditor().getUiComponent()).setText("");
            txtRefNum.setText("");
            txaRmks.setText("");
            txtCusCode.setText("");
            txtCusName.setText("");
            txtTotal.setText("");
            txtReceipt.setText("");
            txtBalance.setText("");
            cmbStatus.setSelectedItem("Open");
            txtItmCode.requestFocus();
            salesOrderTable.setModel(new DefaultTableModel(null, new String []{"documentno", "itemcode","itemname", "unitprice", "qty", "uom", "discountPct", "subttl"}));
            
            documentNumberGeneration();
            
            }
                           

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

        jDayChooser1 = new com.toedter.calendar.JDayChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        salesOrderTable = new javax.swing.JTable();
        btnUpdateTable = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtReceipt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnRowDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaRmks = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSelectCus = new javax.swing.JButton();
        btnSelectItem = new javax.swing.JButton();
        txtItmName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        txtCusName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCusCode = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUp = new javax.swing.JTextField();
        txtDisc = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtRefNum = new javax.swing.JTextField();
        txtDocNum = new javax.swing.JTextField();
        txtUom = new javax.swing.JTextField();
        txtItmCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        txtDocDate = new com.toedter.calendar.JDateChooser();
        btnView = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sales Order");
        setName("SalesOrder"); // NOI18N
        setUndecorated(true);

        salesOrderTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        salesOrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Doc No", "Item Code", "Item Name", "Unit Price (Rs)", "Quantity", "UoM", "Discount %", "Sub Total (Rs)"
            }
        ));
        salesOrderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesOrderTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(salesOrderTable);

        btnUpdateTable.setBackground(new java.awt.Color(255, 255, 153));
        btnUpdateTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdateTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        btnUpdateTable.setText("Update Table");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtCusCode, org.jdesktop.beansbinding.ObjectProperty.create(), btnUpdateTable, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnUpdateTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTableActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(3, 35, 55));
        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnRowDelete, org.jdesktop.beansbinding.ObjectProperty.create(), btnAdd, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
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

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Total (Rs)");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtReceipt, org.jdesktop.beansbinding.ObjectProperty.create(), txtTotal, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtReceipt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnAdd, org.jdesktop.beansbinding.ObjectProperty.create(), txtReceipt, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtReceipt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtReceiptMouseExited(evt);
            }
        });
        txtReceipt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtReceiptKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtReceiptKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Receipts (Rs)");

        txtBalance.setEditable(false);
        txtBalance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtItmCode, org.jdesktop.beansbinding.ObjectProperty.create(), txtBalance, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Balance (Rs)");

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

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setText("Reset");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnCancel, org.jdesktop.beansbinding.ObjectProperty.create(), btnReset, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
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
        jButton7.setBounds(660, 10, 30, 20);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Sales Order");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(60, 10, 120, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txaRmks.setColumns(20);
        txaRmks.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtTotal, org.jdesktop.beansbinding.ObjectProperty.create(), txaRmks, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txaRmks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaRmksKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txaRmks);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Remarks");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Reference No");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Document date");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Item Code");

        btnSelectCus.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSelectCus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSelectCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectCusActionPerformed(evt);
            }
        });

        btnSelectItem.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSelectItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSelectItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectItemActionPerformed(evt);
            }
        });

        txtItmName.setEditable(false);
        txtItmName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtUp, org.jdesktop.beansbinding.ObjectProperty.create(), txtItmName, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtItmName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItmNameActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Customer Code");

        txtQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtUom, org.jdesktop.beansbinding.ObjectProperty.create(), txtQty, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtQtyMouseExited(evt);
            }
        });
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQtyKeyTyped(evt);
            }
        });

        txtCusName.setEditable(false);
        txtCusName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCusName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusNameActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("UoM ");

        txtCusCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCusCodeKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Quantity");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Unit Price Rs");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Item Name");

        txtUp.setEditable(false);
        txtUp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtQty, org.jdesktop.beansbinding.ObjectProperty.create(), txtUp, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpActionPerformed(evt);
            }
        });
        txtUp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUpKeyPressed(evt);
            }
        });

        txtDisc.setEditable(false);
        txtDisc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnUpdateTable, org.jdesktop.beansbinding.ObjectProperty.create(), txtDisc, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtDisc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Discount%");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Document No");

        txtRefNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txaRmks, org.jdesktop.beansbinding.ObjectProperty.create(), txtRefNum, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtRefNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRefNumKeyPressed(evt);
            }
        });

        txtDocNum.setEditable(false);
        txtDocNum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDocNum.setForeground(new java.awt.Color(0, 0, 153));
        txtDocNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocNumActionPerformed(evt);
            }
        });
        txtDocNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDocNumKeyPressed(evt);
            }
        });

        txtUom.setEditable(false);
        txtUom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDisc, org.jdesktop.beansbinding.ObjectProperty.create(), txtUom, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtUom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUomActionPerformed(evt);
            }
        });

        txtItmCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtItmName, org.jdesktop.beansbinding.ObjectProperty.create(), txtItmCode, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtItmCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItmCodeActionPerformed(evt);
            }
        });
        txtItmCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItmCodeKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Customer Name");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Status");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open", "Closed", "Canceled" }));

        txtDocDate.setDateFormatString("yyyy/MM/dd");
        txtDocDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

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
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel11)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQty, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(txtDisc)
                            .addComponent(txtUom)
                            .addComponent(txtItmName)
                            .addComponent(txtUp)
                            .addComponent(txtItmCode))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRefNum, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDocDate, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(txtDocNum, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(57, 57, 57))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCusName, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCusCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnSelectCus, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(txtItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCusCode, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectCus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(txtItmName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(txtUp, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtDocNum, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnView, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtDocDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txtUom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRefNum, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtDisc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel17)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnPrint.setBackground(new java.awt.Color(255, 255, 204));
        btnPrint.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRowDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(40, 40, 40)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(18, 18, 18)
                            .addComponent(txtReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(btnUpdateTable)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRowDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        setSize(new java.awt.Dimension(701, 638));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtItmNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItmNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItmNameActionPerformed

    private void txtItmCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItmCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItmCodeActionPerformed

    private void txtUomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUomActionPerformed

    private void txtDocNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocNumActionPerformed

    private void btnUpdateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTableActionPerformed

        updateTable();

    }//GEN-LAST:event_btnUpdateTableActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        if (txtTotal.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Total Field is empty");
                txtTotal.requestFocus();
            }
        else if (txtReceipt.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Receipt Field is empty");
                txtReceipt.requestFocus();
            }
        else if (Double.parseDouble(txtTotal.getText()) < 1){
                JOptionPane.showMessageDialog(null, "Total Figure is Invalid");
                txtTotal.requestFocus();
            }
        
        else{
        
        double total = Double.parseDouble(txtTotal.getText());
        double receipt = Double.parseDouble(txtReceipt.getText());
        double bal = total - receipt;
        
        Double rcpt = Double.parseDouble(txtReceipt.getText());
        txtReceipt.setText(String.format("%.2f",rcpt));
        
        //txtBalance.setText(String.valueOf(bal));
        txtBalance.setText(String.format("%.2f", bal));
               
        add();
        
        }
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        this.dispose();

    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtDiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscActionPerformed

    private void txtUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpActionPerformed

    private void txtCusCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusCodeActionPerformed

    private void txtCusNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusNameActionPerformed

    private void txtItmCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItmCodeKeyPressed
       
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            selectItemCode();
        
        }
        
    }//GEN-LAST:event_txtItmCodeKeyPressed

    private void txtCusCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCusCodeKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            selectCustomerCode();
                    
        }
        
    }//GEN-LAST:event_txtCusCodeKeyPressed

    private void btnRowDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRowDeleteActionPerformed

        df.removeRow(salesOrderTable.getSelectedRow());
        double sum = 0;
        
        for(int i =0; i<salesOrderTable.getRowCount(); i++){            
            sum = sum + Double.parseDouble(salesOrderTable.getValueAt(i, 7).toString());
        }
        
        //txtTotal.setText(Double.toString(sum));
        txtTotal.setText(String.format("%.2f", sum));
        
    }//GEN-LAST:event_btnRowDeleteActionPerformed

    private void txtUpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUpKeyPressed
        
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            JOptionPane.showMessageDialog(null, "Please enter only numeric figures");
            txtUp.setText("");
        }
        
    }//GEN-LAST:event_txtUpKeyPressed

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c)){
            JOptionPane.showMessageDialog(null, "Please enter only numeric figures");
            txtQty.setText("");
        }   
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
        
            btnUpdateTable.requestFocus();
        
        }
        
//        char c = evt.getKeyChar();
//        if(Character.isLetter(c)){
//            JOptionPane.showMessageDialog(null, "Please enter only numeric figures");
//            txtQty.setText("");
//        }
        
    }//GEN-LAST:event_txtQtyKeyPressed

    private void txtDocNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocNumKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            viewSalesOrder();
        }
                        
    }//GEN-LAST:event_txtDocNumKeyPressed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        
        txtItmCode.setText("");
        txtItmName.setText("");
        txtUp.setText("");
        txtQty.setText("");
        txtUom.setText("");
        txtDisc.setText("");
        //txtDocDate1.setText("");
        //((JTextField)txtDocDate.getDateEditor().getUiComponent()).setText("");
        
        txtCusCode.setText("");
        txtCusName.setText("");
        txtRefNum.setText("");
        txaRmks.setText("");
        salesOrderTable.setModel(new DefaultTableModel(null, new String []{"documentno", "itemcode", "itemname", "unitprice", "qty", "uom", "discountPct", "subttl"}));
        documentNumberGeneration();
        txtTotal.setText("");
        txtReceipt.setText("");
        txtBalance.setText("");
        txtDocNum.setEditable(false);
        cmbStatus.setSelectedItem("Open");
        cmbStatus.setEnabled(true);
        btnUpdateTable.setEnabled(true);
        btnAdd.setEnabled(true);
        btnRowDelete.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnPrint.setEnabled(false);

        txtItmCode.requestFocus();

        txtItmName.setEditable(true);
        txtUom.setEditable(true);
        
        //Reset the date to current date
        Date date = new Date();
        txtDocDate.getJCalendar().setMaxSelectableDate(date);
        txtDocDate.setDate(date);
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtReceiptKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReceiptKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReceiptKeyReleased

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
           // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyActionPerformed

    private void txtQtyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQtyMouseExited
                // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyMouseExited

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
              // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyTyped
          // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyKeyTyped

    private void txtCusCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCusCodeKeyReleased
            // TODO add your handling code here:
    }//GEN-LAST:event_txtCusCodeKeyReleased

    private void txtRefNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRefNumKeyPressed

//        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
//        
//            txaRmks.requestFocus();
//        
//        }

    }//GEN-LAST:event_txtRefNumKeyPressed

    private void txaRmksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaRmksKeyPressed
//        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
//        
//            txtReceipt.requestFocus();
//        
//        }

    }//GEN-LAST:event_txaRmksKeyPressed

    private void txtReceiptKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReceiptKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            double total = Double.parseDouble(txtTotal.getText());
            double receipt = Double.parseDouble(txtReceipt.getText());
            double bal = total - receipt;
        
            txtBalance.setText(String.format("%.2f", bal));
        
            btnAdd.requestFocus();
        
        }

    }//GEN-LAST:event_txtReceiptKeyPressed

    private void btnSelectItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectItemActionPerformed
        
        txtItmCode.setText("");
        txtItmName.setText("");
        txtUp.setText("");
        txtUom.setText("");
        txtDisc.setText("");
        txtQty.setText("");
        itmPick itmp = new itmPick(this);
        itmp.setVisible(true);
        
        
        
    }//GEN-LAST:event_btnSelectItemActionPerformed

    private void btnSelectCusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectCusActionPerformed
        
        txtCusCode.setText("");
        txtCusName.setText("");
        CusPick cus = new CusPick(this); // button for next window Customer Pick
        cus.setVisible(true);
                
    }//GEN-LAST:event_btnSelectCusActionPerformed

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

    private void salesOrderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesOrderTableMouseClicked
        
        //btnAdd.setEnabled(false);
        //salesOrderTable.setEnabled(false);
        
    }//GEN-LAST:event_salesOrderTableMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        
        double total = Double.parseDouble(txtTotal.getText());
        double receipt = Double.parseDouble(txtReceipt.getText());
        double bal = total - receipt;
        
        Double rcpt = Double.parseDouble(txtReceipt.getText());
        txtReceipt.setText(String.format("%.2f",rcpt));
        
        txtBalance.setText(String.format("%.2f", bal));
        
        updateSalesOrder();
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtReceiptMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtReceiptMouseExited
        // TODO add your handling code here:
        double total = Double.parseDouble(txtTotal.getText());
        double receipt = Double.parseDouble(txtReceipt.getText());
        double bal = total - receipt;
        
        Double rcpt = Double.parseDouble(txtReceipt.getText());
        txtReceipt.setText(String.format("%.2f",rcpt));
        
        txtBalance.setText(String.format("%.2f", bal));
        
        
    }//GEN-LAST:event_txtReceiptMouseExited

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed

        HashMap m = new HashMap();
        m.put("SalesOrderNumber",txtDocNum.getText());
        m.put("UserName",userName);
        
        try {

            //String path="C:\\Users\\Prabudha\\Documents\\NetBeansProjects\\Roofing.lk\\src\\PrintLayouts\\SalesOrderLayout.jrxml";

            JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/SalesOrderLayout.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(path);

            JasperPrint  jp = JasperFillManager.fillReport(jr,m,con);
            JasperViewer.viewReport(jp,false);

        } catch (JRException ex) {
            Logger.getLogger(SalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        SalesOrderDetailPicker2 sODP2 = new SalesOrderDetailPicker2(this);
        sODP2.setVisible(true);
        
        txtDocNum.setEditable(true);
        btnUpdateTable.setEnabled(false);
        btnAdd.setEnabled(false);
        btnRowDelete.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnPrint.setEnabled(true);
        
        txtItmCode.setText("");
        txtItmName.setText("");
        txtUp.setText("");
        txtQty.setText("");
        txtUom.setText("");
        txtDisc.setText("");
        
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
            java.util.logging.Logger.getLogger(SalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRowDelete;
    private javax.swing.JButton btnSelectCus;
    private javax.swing.JButton btnSelectItem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateTable;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private com.toedter.calendar.JDayChooser jDayChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable salesOrderTable;
    private javax.swing.JTextArea txaRmks;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtCusCode;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JTextField txtDisc;
    private com.toedter.calendar.JDateChooser txtDocDate;
    private javax.swing.JTextField txtDocNum;
    private javax.swing.JTextField txtItmCode;
    private javax.swing.JTextField txtItmName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtReceipt;
    private javax.swing.JTextField txtRefNum;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtUom;
    private javax.swing.JTextField txtUp;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

public void setcode(String t){
    txtCusCode.setText(t); // methord for next window
    txtCusCode.requestFocus();
}

public void setC (String x){
    txtItmCode.setText(x);
    txtItmCode.requestFocus();

}

public void setCD (String cd){
    txtDocNum.setText(cd);
    txtDocNum.requestFocus();

}





private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }


}
