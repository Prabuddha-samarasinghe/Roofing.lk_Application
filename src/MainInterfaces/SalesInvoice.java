/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;

import InformationPickers.SalesInvoiceDetailPicke;
import InformationPickers.SalesOrderDetailPicker;
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


public class SalesInvoice extends javax.swing.JFrame {
    
    Connection con = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst4 = null;
    PreparedStatement pst5 = null;
    PreparedStatement pst6 = null;
    PreparedStatement pst7 = null;
    PreparedStatement pst8 = null;
    ResultSet rs = null;
    DefaultTableModel df = null;
    int x,y;
    String UserName =null;

    /**
     * Creates new form SalesInvoice
     */
    public SalesInvoice() {
        setIconImage();
        initComponents();
//        this.UserName=userName;
        //Connect to DB
        con = DBconnect.connect();
        
        documentNumberGeneration();
        
        //Set Current date and Disale Future dates 
        Date date = new Date();
        txtDocDate.getJCalendar().setMaxSelectableDate(date);
        txtDocDate.setDate(date);
    }

    SalesInvoice(String userName) {
        
        setIconImage();
        initComponents();
//        this.UserName=userName;
        //Connect to DB
        con = DBconnect.connect();
        
        documentNumberGeneration();
        
        this.UserName = userName;
        
        //Set Current date and Disale Future dates 
        Date date = new Date();
        txtDocDate.getJCalendar().setMaxSelectableDate(date);
        txtDocDate.setDate(date);
       
    }

   
    
    public void selectSalesOrder(){
        
        try {
        String SoNo = txtSoNo.getText();
        
        pst = con.prepareStatement("Select * from salesorder where documentno = ?");
        pst.setString(1, SoNo);
        rs = pst.executeQuery();
        
        if(rs.next() == false){
            JOptionPane.showMessageDialog(null, "Incorrect Sales Order Number");  
            txtSoNo.setText("");
        }
        
        else if(rs.getString("Status").equals("Canceled")){
            JOptionPane.showMessageDialog(null, "Sales Order is Canceled. Cannot Process");
            txtSoNo.setText("");
        }
                
        else if(rs.getString("Status").equals("Closed")){
            JOptionPane.showMessageDialog(null, "Sales Invoice is already created for selected Sales Order");
            txtSoNo.setText("");
        }
        
        else if(rs.getDouble("Balance")>0.00){
            JOptionPane.showMessageDialog(null, "Sales Order has not settled fully. Cannot Process");
            txtSoNo.setText("");
        }
        
        else {
            
            String cusCode = rs.getString("customercode");
            String cusName = rs.getString("customername");
            String total = rs.getString("total");
            String receipt = rs.getString("receipt");
            String balance = rs.getString("balance");
                      
            
            txtCusCode.setText(cusCode.trim());
            txtCusName.setText(cusName.trim());           
            txtTotal.setText(total.trim());
            txtReceipt.setText(receipt.trim());
            txtBalance.setText(balance.trim());
            txtDocDate.requestFocus();
            
            
            pst1 = con.prepareStatement("Select documentno as 'SO No', itemcode as 'Item Code', itemname as 'Item Name', unitprice 'Unit Price', qty as 'Quantity', uom as 'UoM', discountPct as 'Disc %', subttl as 'Sub Total' from salesorderitem where documentno = ?");
            pst1.setString(1, SoNo);
            rs = pst1.executeQuery();
            
            salesInvoiceTable.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
         
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
 
    public void add(){
        
        try {
            
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String date = dt.format(now);         
            
           if (((JTextField)txtDocDate.getDateEditor().getUiComponent()).getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Document date is empty");
                txtDocDate.requestFocus();
            }     
            
            else if (!((JTextField)txtDocDate.getDateEditor().getUiComponent()).getText().equals(date)){
                JOptionPane.showMessageDialog(null, "Incorrect Document Date");
                txtDocDate.requestFocus();
            }     
            
            else{
                        
            String soNo = txtSoNo.getText();
            String docNo = txtDocNum.getText();
            //String docDate = txtDocDate.getDateFormatString();
            String cusCode = txtCusCode.getText();
            String cusName = txtCusName.getText();
            String refNo = txtRefNum.getText();
            String rmks = txaRmks.getText();
            String total = txtTotal.getText();
            String receipt = txtReceipt.getText();
            String bal = txtBalance.getText();
            String status = cmbStatus.getSelectedItem().toString();
            
            String query1 = "insert into salesinvoice (sonumber, documentno, documentdate, systemdate, customercode, customername, refno, remarks, total, receipt, balance, Status) values (?,?,?,?,?,?,?,?,?,?,?,?)";
            
            pst = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, soNo);
            pst.setString(2, docNo);
            //pst.setString(3, docDate);
            pst.setString(3, ((JTextField)txtDocDate.getDateEditor().getUiComponent()).getText());
            pst.setString(4, date);
            pst.setString(5, cusCode);
            pst.setString(6, cusName);
            pst.setString(7, refNo);
            pst.setString(8, rmks);
            pst.setString(9, total);
            pst.setString(10, receipt);
            pst.setString(11, bal);
            pst.setString(12, status);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
           
            
            String query2 = "insert into salesinvoiceitem (sonumber, itemcode, itemname, unitprice, qty, uom, discountPct, subttl, documentno ) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst1 = con.prepareStatement(query2);
            
            String soNum;
            String iCode;
            String iName;
            String uPrice;
            String qty;
            String uom;
            String disc;
            String subTtl;
            String docNum;
            
            for (int i = 0; i<salesInvoiceTable.getRowCount(); i++){
                
                //docNum = (String)salesInvoiceTable.getValueAt(i, 0);
                soNum = (String)salesInvoiceTable.getValueAt(i, 0);
                iCode = (String)salesInvoiceTable.getValueAt(i, 1);
                iName = (String)salesInvoiceTable.getValueAt(i, 2);
                uPrice = (String)salesInvoiceTable.getValueAt(i, 3).toString();               
                qty = (String)salesInvoiceTable.getValueAt(i, 4).toString();
                uom = (String)salesInvoiceTable.getValueAt(i, 5);
                disc = (String)salesInvoiceTable.getValueAt(i, 6).toString();
                subTtl = (String)salesInvoiceTable.getValueAt(i, 7).toString();
                docNum = txtDocNum.getText();
                
                pst1.setString(1, soNum);
                pst1.setString(2, iCode);
                pst1.setString(3, iName);
                pst1.setString(4, uPrice);
                pst1.setString(5, qty);
                pst1.setString(6, uom);
                pst1.setString(7, disc);               
                pst1.setString(8, subTtl);
                pst1.setString(9, docNum);
                pst1.executeUpdate();
                //txtReceipt.setText(String.format("%.2f", 0.00));
            }
            //item master updating...
            
            String query3 = "update itemmaster set qty = qty - ? where ItemCode = ?";
            pst2 = con.prepareStatement(query3);
                       
            
            for (int i = 0; i<salesInvoiceTable.getRowCount(); i++){
                
                iCode = (String)salesInvoiceTable.getValueAt(i, 1);
                qty = (String)salesInvoiceTable.getValueAt(i, 4).toString();
                
                pst2.setString(1, qty);
                pst2.setString(2, iCode);
                pst2.executeUpdate();
            }
            
            //Sales Order Status updating...
            String SoNo = txtSoNo.getText();
            
            String query4 = "UPDATE salesorder SET Status = '"+ "Closed" +"' WHERE DocumentNo = '"+ SoNo +"'";
            pst3 = con.prepareStatement(query4);
            pst3.execute();
            
            JOptionPane.showMessageDialog(this, "Sales Invoice is Completed");
            
            documentNumberGeneration();
            
            txtItmCode.setText("");
            txtSoNo.setText("");
            txtItmName.setText("");
            txtQty.setText("");
            txtUom.setText("");
            //txtDocNum.setText("");
            //txtDocDate1.setText("");
            ((JTextField)txtDocDate.getDateEditor().getUiComponent()).setText("");
            txtRefNum.setText("");
            txaRmks.setText("");
            txtCusCode.setText("");
            txtCusName.setText("");
            txtTotal.setText("");
            txtReceipt.setText("");
            txtBalance.setText("");
            txtSoNo.requestFocus();
            salesInvoiceTable.setModel(new DefaultTableModel(null, new String []{"sonumber", "itemcode", "itemname", "unitprice", "qty", "uom", "discountPct", "subttl", "documentno"}));
            }     
            
                 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void documentNumberGeneration(){ 
        
        
        try {
            Statement st = con.createStatement();
                ResultSet rs1 = st.executeQuery("select MAX(documentno) from salesinvoice");
            rs1.next();
            
            rs1.getString("MAX(documentno)");
            
            if(rs1.getString("MAX(documentno)")== null){
                txtDocNum.setText("INV00001");
            }
            else{
                long id = Long.parseLong(rs1.getString("MAX(documentno)").substring(3, rs1.getString("MAX(documentno)").length()));
                id++;
                txtDocNum.setText("INV" + String.format("%05d", id));
            
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
            //Logger.getLogger(CustomerMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updateInvoice(){
        
        int x = JOptionPane.showConfirmDialog(null, "Do you need to update?");
        
        if (x == 0){

        String docNo = txtDocNum.getText();
        String rmks = txaRmks.getText();
        String status = cmbStatus.getSelectedItem().toString();
        
        try {
                
                String sql = "UPDATE salesinvoice SET Status= '"+ status +"', Remarks = '"+ rmks +"' WHERE DocumentNo = '"+ docNo +"' ";
                pst = con.prepareStatement(sql);
                pst.execute();
                
                
                //item master updating...
                String iCode;
                String qty;
                
                if(cmbStatus.getSelectedItem().equals("Canceled")){

                String sql2 = "update itemmaster set qty = qty + ? where ItemCode = ?";
                pst4 = con.prepareStatement(sql2);
            
                for (int i = 0; i<salesInvoiceTable.getRowCount(); i++){
                
                iCode = (String)salesInvoiceTable.getValueAt(i, 1);
                qty = (String)salesInvoiceTable.getValueAt(i, 4).toString();
                
                pst4.setString(1, qty);
                pst4.setString(2, iCode);
                pst4.executeUpdate();
            }
                }
                
                
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
            cmbStatus.setSelectedItem("Closed");
            txtSoNo.requestFocus();
            salesInvoiceTable.setModel(new DefaultTableModel(null, new String []{"SoNumber" ,"itemcode","itemname", "unitprice", "qty", "uom", "discountPct", "subttl", "documentno"}));
            
            documentNumberGeneration();
                                    

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                
            }
        }    
    }
    
    
    public void viewInvoice(){
        
        try {
        String DocNo = txtDocNum.getText();
        
        pst = con.prepareStatement("Select * from salesinvoice where documentno = ?");
        pst.setString(1, DocNo);
        rs = pst.executeQuery();
        
        if(rs.next() == false){
            JOptionPane.showMessageDialog(null, "Incorrect Sales Invoice Number");
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
            
            
            if(cmbStatus.getSelectedItem().equals("Canceled")){
                btnUpdate.setEnabled(false);
                cmbStatus.setEnabled(false);
                txaRmks.setEditable(false);
                
            
            }
            else{
                btnUpdate.setEnabled(true);               
                cmbStatus.setEnabled(true);
                txaRmks.setEditable(true);
            
            }
            
                        
            pst1 = con.prepareStatement("Select SoNumber as 'SO No', itemcode as 'Item Code', itemname as 'Item Name', UnitPrice as 'Unit Price' , qty as 'Quantity', uom as 'UoM', discountPct as 'Disc%', subttl as 'Sub Total (Rs)', documentno as 'Doc No' from salesinvoiceitem where documentno = ?");
            pst1.setString(1, DocNo);
            rs = pst1.executeQuery();
            
            salesInvoiceTable.setModel(DbUtils.resultSetToTableModel(rs));
            
            txtSoNo.setEditable(false);
            txtDocDate.setEnabled(false);
            txtRefNum.setEditable(false);
            //txtReceipt.setEditable(false);

            
        }
         
        } catch (SQLException ex) {
            Logger.getLogger(InventoryReceipt.class.getName()).log(Level.SEVERE, null, ex);
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

        btnUpdateTable = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtReceipt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        btnRowDelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        salesInvoiceTable = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCusName = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        txtCusCode = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtUp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDisc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaRmks = new javax.swing.JTextArea();
        txtDocNum = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtUom = new javax.swing.JTextField();
        txtItmCode = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtItmName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtDocDate = new com.toedter.calendar.JDateChooser();
        txtRefNum = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtSoNo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        btnSelectItem = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnCancel1 = new javax.swing.JButton();
        btnInvoicePrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sales Invoice");
        setName("SalesInvoice"); // NOI18N
        setUndecorated(true);

        btnUpdateTable.setBackground(new java.awt.Color(255, 255, 153));
        btnUpdateTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdateTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        btnUpdateTable.setText("Update Table");
        btnUpdateTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTableActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(3, 35, 55));
        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Total (Rs)");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtReceipt.setEditable(false);
        txtReceipt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Receipts (Rs)");

        txtBalance.setEditable(false);
        txtBalance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnRowDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnRowDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRowDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnRowDelete.setText("Delete Row");
        btnRowDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRowDeleteActionPerformed(evt);
            }
        });

        salesInvoiceTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        salesInvoiceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SO No", "Item Code", "Item Name", "Unit Price (Rs)", "Quantity", "UoM", "Discount %", "Sub Total (Rs)", "Doc No"
            }
        )

        {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        }
    );
    salesInvoiceTable.setAutoscrolls(false);
    salesInvoiceTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            salesInvoiceTableMouseClicked(evt);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            salesInvoiceTableMouseExited(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            salesInvoiceTableMouseReleased(evt);
        }
    });
    jScrollPane2.setViewportView(salesInvoiceTable);

    jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel15.setText("Balance (Rs)");

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

    jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel17.setForeground(new java.awt.Color(255, 255, 255));
    jLabel17.setText("Sales Invoice");
    jPanel3.add(jLabel17);
    jLabel17.setBounds(60, 10, 120, 20);

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

    txtCusName.setEditable(false);
    txtCusName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    txtCusName.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCusNameActionPerformed(evt);
        }
    });

    txtQty.setEditable(false);
    txtQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

    txtCusCode.setEditable(false);
    txtCusCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

    jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel7.setText("UoM ");

    jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel12.setText("Unit Price Rs");

    jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel6.setText("Quantity");

    txtUp.setEditable(false);
    txtUp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    txtUp.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtUpActionPerformed(evt);
        }
    });

    jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel5.setText("Item Name");

    txtDisc.setEditable(false);
    txtDisc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    txtDisc.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDiscActionPerformed(evt);
        }
    });

    jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel4.setText("Item Code");

    jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel11.setText("Discount%");

    txaRmks.setColumns(20);
    txaRmks.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
    txaRmks.setRows(5);
    jScrollPane1.setViewportView(txaRmks);

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

    jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel9.setText("Customer Name");

    txtUom.setEditable(false);
    txtUom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    txtUom.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtUomActionPerformed(evt);
        }
    });

    txtItmCode.setEditable(false);
    txtItmCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

    jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel8.setText("Customer Code");

    txtItmName.setEditable(false);
    txtItmName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    txtItmName.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtItmNameActionPerformed(evt);
        }
    });

    jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel2.setText("Document date");

    jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel1.setText("Document No");

    txtDocDate.setDateFormatString("yyyy/MM/dd");
    txtDocDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

    txtRefNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

    jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel16.setText("SO No");

    txtSoNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

    org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtItmCode, org.jdesktop.beansbinding.ObjectProperty.create(), txtSoNo, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
    bindingGroup.addBinding(binding);

    txtSoNo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtSoNoActionPerformed(evt);
        }
    });
    txtSoNo.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            txtSoNoKeyPressed(evt);
        }
    });

    jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel10.setText("Remarks");

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel3.setText("Reference No");

    jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel18.setText("Status");

    cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Closed", "Canceled" }));

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
            .addGap(34, 34, 34)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel11)
                                .addComponent(jLabel18))
                            .addGap(52, 52, 52))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(56, 56, 56))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(58, 58, 58)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDisc)
                        .addComponent(txtUom)
                        .addComponent(txtQty)
                        .addComponent(txtItmName)
                        .addComponent(txtItmCode)
                        .addComponent(txtSoNo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)
                    .addComponent(txtUp, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(28, 28, 28)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addComponent(jLabel8)
                .addComponent(jLabel2)
                .addComponent(jLabel3)
                .addComponent(jLabel10)
                .addComponent(jLabel9))
            .addGap(45, 45, 45)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(txtCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtCusCode, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtDocDate, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtRefNum, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(txtDocNum, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(23, 23, 23))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(7, 7, 7)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel8)
                .addComponent(txtCusCode, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel1)
                .addComponent(txtDocNum, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtDocDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel3)
                .addComponent(txtRefNum, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel10)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel16)
                .addComponent(txtSoNo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(9, 9, 9)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel4)
                .addComponent(txtItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel5)
                .addComponent(txtItmName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel12)
                .addComponent(txtUp, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel6)
                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel7)
                .addComponent(txtUom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel11)
                .addComponent(txtDisc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel18)
                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    btnUpdate.setText("Update");
    btnUpdate.setEnabled(false);
    btnUpdate.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnUpdateActionPerformed(evt);
        }
    });

    btnCancel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    btnCancel1.setText("Close");
    btnCancel1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCancel1ActionPerformed(evt);
        }
    });

    btnInvoicePrint.setBackground(new java.awt.Color(255, 255, 204));
    btnInvoicePrint.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    btnInvoicePrint.setText("Print");
    btnInvoicePrint.setEnabled(false);
    btnInvoicePrint.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnInvoicePrintActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
            .addGap(18, 18, 18))
        .addGroup(layout.createSequentialGroup()
            .addGap(315, 315, 315)
            .addComponent(btnUpdateTable)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(layout.createSequentialGroup()
            .addGap(33, 33, 33)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnRowDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnInvoicePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLabel15)
                    .addGap(35, 35, 35)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(50, 50, 50)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGap(36, 36, 36))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(1, 1, 1)
            .addComponent(btnUpdateTable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(3, 3, 3)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRowDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnInvoicePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel13)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel14)
                        .addComponent(txtReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel15)
                        .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addContainerGap(14, Short.MAX_VALUE))
    );

    bindingGroup.bind();

    setSize(new java.awt.Dimension(778, 639));
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateTableActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

try{
                int y = 0;
        
                for(int i =0; i < salesInvoiceTable.getRowCount(); i++){
            

                String itmCode = salesInvoiceTable.getValueAt(i, 1).toString();
                String Qt = salesInvoiceTable.getValueAt(i, 4).toString();
            
            
                pst = con.prepareStatement("select * from itemmaster where ItemCode = ? ");
                pst.setString(1, itmCode);
                rs = pst.executeQuery();
                 
            
                while (rs.next()){
                int currentQty;
                
                currentQty = rs.getInt("qty");
                
                int newQty =Integer.parseInt(Qt);
                //int newQty = Integer.parseInt(txtQty.getText());
                
                if(newQty > currentQty){
                    y = 1;
                    JOptionPane.showMessageDialog(this, itmCode +" " +"Available Quantity is" +" "+currentQty);
                    JOptionPane.showMessageDialog(this, "Quantity is not enough");
                    
             }
                
                else{
                    
                        y =2;
                    //JOptionPane.showMessageDialog(this, itmCode +" " +"Available Quantity is" +" "+currentQty);
                    //JOptionPane.showMessageDialog(this, "Quantity is enough");
                         }                           
                }
                   if (y != 2){
                       break;
                   }                
                 }               
                    if (y == 2){
                    add();
                    }                
                 }
                  
                catch (Exception ex) {                    
                //Logger.getLogger(SalesOrder.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex);
                
            }           
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed

        txtSoNo.setText("");
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
        salesInvoiceTable.setModel(new DefaultTableModel(null, new String []{"documentno", "itemcode", "itemname", "unitprice", "qty", "uom", "discountPct", "subttl"}));
        documentNumberGeneration();
        txtTotal.setText("");
        txtReceipt.setText("");
        txtBalance.setText("");
        txtSoNo.setEditable(true);
        txtDocNum.setEditable(false);
        cmbStatus.setSelectedItem("Closed");
        cmbStatus.setEnabled(true);
        btnUpdateTable.setEnabled(true);
        btnAdd.setEnabled(true);
        btnRowDelete.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnInvoicePrint.setEnabled(false);
        btnSelectItem.setEnabled(true);
        
        txtSoNo.requestFocus();
        
        //Reset the date to current date
        Date date = new Date();
        txtDocDate.getJCalendar().setMaxSelectableDate(date);
        txtDocDate.setDate(date);

    }//GEN-LAST:event_btnResetActionPerformed

    private void txtDiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscActionPerformed

    private void txtUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpActionPerformed

    private void txtCusCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusCodeActionPerformed

    private void txtCusCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCusCodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusCodeKeyPressed

    private void txtCusNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusNameActionPerformed

    private void txtUomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUomActionPerformed

    private void txtDocNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocNumActionPerformed

    private void btnRowDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRowDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRowDeleteActionPerformed

    private void txtItmNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItmNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItmNameActionPerformed

    private void txtItmCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItmCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItmCodeActionPerformed

    private void txtItmCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItmCodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItmCodeKeyPressed

    private void txtSoNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoNoActionPerformed

    private void txtSoNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoNoKeyPressed
  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            selectSalesOrder();
        
        }
    }//GEN-LAST:event_txtSoNoKeyPressed

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

    private void txtDocNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocNumKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            viewInvoice();
        }
             
    }//GEN-LAST:event_txtDocNumKeyPressed

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        
        this.dispose();
        
    }//GEN-LAST:event_btnCancel1ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

        updateInvoice();

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnInvoicePrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoicePrintActionPerformed
       
         HashMap m = new HashMap();
         m.put("SalesInvoicNo",txtDocNum.getText());   
         m.put("UserName",UserName); 
        
        try {   
                       
            //String path="C:\\Users\\Prabudha\\Documents\\NetBeansProjects\\Roofing.lk\\src\\PrintLayouts\\SalessInvoiceLayout.jrxml";
            JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/SalessInvoiceLayout.jrxml"); 
            
            JasperReport jr = JasperCompileManager.compileReport(path);       
            
            JasperPrint  jp = JasperFillManager.fillReport(jr,m,con);
            JasperViewer.viewReport(jp,false);
            
            
        } catch (JRException ex) {
            Logger.getLogger(SalesOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnInvoicePrintActionPerformed

    private void salesInvoiceTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesInvoiceTableMouseClicked
        // TODO add your handling code here:     
    }//GEN-LAST:event_salesInvoiceTableMouseClicked

    private void salesInvoiceTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesInvoiceTableMouseExited
        // TODO add your handling code here:       
    }//GEN-LAST:event_salesInvoiceTableMouseExited

    private void salesInvoiceTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesInvoiceTableMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_salesInvoiceTableMouseReleased

    private void btnSelectItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectItemActionPerformed
        
        SalesOrderDetailPicker itmp = new SalesOrderDetailPicker(this);
        itmp.setVisible(true);
                
        
    }//GEN-LAST:event_btnSelectItemActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
       SalesInvoiceDetailPicke sIDPick = new SalesInvoiceDetailPicke(this);
       sIDPick.setVisible(true);
       
        txtDocNum.setEditable(true);
        btnUpdateTable.setEnabled(false);
        btnAdd.setEnabled(false);
        btnRowDelete.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnInvoicePrint.setEnabled(true);
        btnSelectItem.setEnabled(false);
        
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
            java.util.logging.Logger.getLogger(SalesInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesInvoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JButton btnInvoicePrint;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRowDelete;
    private javax.swing.JButton btnSelectItem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateTable;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable salesInvoiceTable;
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
    private javax.swing.JTextField txtSoNo;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtUom;
    private javax.swing.JTextField txtUp;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

 private void setIconImage() {
       
       setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("HED ICON.png")));
        
    }
 public void setC (String x){
    txtSoNo.setText(x);
    txtSoNo.requestFocus();
 }
 public void setI (String i){
    txtDocNum.setText(i);
    txtDocNum.requestFocus();

}

}

