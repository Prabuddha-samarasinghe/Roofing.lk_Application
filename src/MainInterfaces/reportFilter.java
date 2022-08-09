/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainInterfaces;


//import InformationPickers.itmPick3;
import InformationPickers.CusPickFromReportFilter;
import InformationPickers.CusPickToReportFilte;
import InformationPickers.SalesInvoiceDetailPickeReportFrom;
import InformationPickers.SalesInvoiceDetailPickeReportTo;
import InformationPickers.SalesOrderDetailPickReortFrom;
import InformationPickers.SalesOrderDetailPickReortTo;
import InformationPickers.itmPickFromReportFilter;
import InformationPickers.itmPickToReportFilter;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JOptionPane;
import mycode.DBconnect;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class reportFilter extends javax.swing.JFrame {
    
Connection con = null;
PreparedStatement pst = null;
ResultSet rs = null;
int x,y;
String Username;
   
   
    public reportFilter() {
        initComponents();
        con = DBconnect.connect();        
    }

    
    public reportFilter(String a, String userName) {
        
        //for List of Customers
        
        
        initComponents();        
        con = DBconnect.connect();
        
        this.Username = userName;
        
        lblReport.setText("List of Customers");
        
        lblItemCodeFrom.enable(false);
        txtItmCodeFrom.enable(false);       
        lblItemCodeTo.enable(false);
        txtItmCodeTo.enable(false); 
        
        txtFromDocDate.setEnabled(false);
        lblDocumentDateFrom.enable(false);      
        txtToDocDate.setEnabled(false);
        lblDocumentdateTo.enable(false);
        
        txtCustomerCodeFrom.enable(false);
        lblCustomerCodeFrom.enable(false);
        txtCustomerCodeTo.enable(false);   
        lblCustomerCodeTo.enable(false);
        
        btnSelectItem.setEnabled(false);
        btnSelectItem1.setEnabled(false);
        btnSelectItem2.setEnabled(false);
        btnSelectItem3.setEnabled(false);
        
        lblSalesOrdeNoFrom.setEnabled(false);
        txtSalesOrdeNoFrom.setEnabled(false);
        btnSalesOrdeNoFrom.setEnabled(false);
        
        lblSalesOrdeNoTo.setEnabled(false);
        txtSalesOrdeNoTo.setEnabled(false);
        btnSalesOrdeNoTo.setEnabled(false);
        
        lblnvoiceNoFrom.setEnabled(false);
        txtnvoiceNoFrom.setEnabled(false);
        btnnvoiceNoFrom.setEnabled(false);
        
        lblnvoiceNoTo.setEnabled(false);
        txtnvoiceNoTo.setEnabled(false);
        btnnvoiceNoTo.setEnabled(false);
        
        //Remove Unnecessary Items From the Dropdown menu
        cmbStatus.removeItem("Open");
        cmbStatus.removeItem("Closed");
        cmbStatus.removeItem("Canceled");
        
      }
    
    public reportFilter(String a,String b,String userName) {
        
        //for List of Items
        
        initComponents();
        con = DBconnect.connect();
        
      
       this.Username = userName;
        
        lblReport.setText("List of Items");
        
        lblItemCodeFrom.enable(false);
        txtItmCodeFrom.enable(false);       
        lblItemCodeTo.enable(false);
        txtItmCodeTo.enable(false);        
        txtFromDocDate.setEnabled(false);
        lblDocumentDateFrom.enable(false);      
        txtToDocDate.setEnabled(false);
        lblDocumentdateTo.enable(false);    
        txtCustomerCodeFrom.enable(false);
        lblCustomerCodeFrom.enable(false);
        txtCustomerCodeTo.enable(false);   
        lblCustomerCodeTo.enable(false);
        btnSelectItem.setEnabled(false);
        btnSelectItem1.setEnabled(false);
        btnSelectItem2.setEnabled(false);
        btnSelectItem3.setEnabled(false);
        
        lblSalesOrdeNoFrom.setEnabled(false);
        txtSalesOrdeNoFrom.setEnabled(false);
        btnSalesOrdeNoFrom.setEnabled(false);
        
        lblSalesOrdeNoTo.setEnabled(false);
        txtSalesOrdeNoTo.setEnabled(false);
        btnSalesOrdeNoTo.setEnabled(false);
        
        lblnvoiceNoFrom.setEnabled(false);
        txtnvoiceNoFrom.setEnabled(false);
        btnnvoiceNoFrom.setEnabled(false);
        
        lblnvoiceNoTo.setEnabled(false);
        txtnvoiceNoTo.setEnabled(false);
        btnnvoiceNoTo.setEnabled(false);
        
        //Remove Unnecessary Items From the Dropdown menu
        cmbStatus.removeItem("Open");
        cmbStatus.removeItem("Closed");
        cmbStatus.removeItem("Canceled");
        
        
       }
    
    
    public reportFilter(String a,String b,String c,String userName) {
        
        //for Inventry Screen
        
        initComponents();
        con = DBconnect.connect(); 
        
        
        this.Username = userName;
        
        lblReport.setText("Inventory");
        
        txtFromDocDate.setEnabled(false);
        lblDocumentDateFrom.enable(false);      
        txtToDocDate.setEnabled(false);
        lblDocumentdateTo.enable(false);
        
        txtCustomerCodeFrom.enable(false);
        lblCustomerCodeFrom.enable(false);
        txtCustomerCodeTo.enable(false);   
        lblCustomerCodeTo.enable(false);
        
        btnSelectItem2.setEnabled(false);
        btnSelectItem3.setEnabled(false);
        
        
        
        lblStatus.enable(false);
        cmbStatus.enable(false);
        
         lblSalesOrdeNoFrom.setEnabled(false);
        txtSalesOrdeNoFrom.setEnabled(false);
        btnSalesOrdeNoFrom.setEnabled(false);
        
        lblSalesOrdeNoTo.setEnabled(false);
        txtSalesOrdeNoTo.setEnabled(false);
        btnSalesOrdeNoTo.setEnabled(false);
        
        lblnvoiceNoFrom.setEnabled(false);
        txtnvoiceNoFrom.setEnabled(false);
        btnnvoiceNoFrom.setEnabled(false);
        
        lblnvoiceNoTo.setEnabled(false);
        txtnvoiceNoTo.setEnabled(false);
        btnnvoiceNoTo.setEnabled(false);
                  
        
    }
    public reportFilter(String a,String b,String c, String d,String userName) {
        
        //for Inventory Receipt Screen
        
        initComponents();
        con = DBconnect.connect(); 
        
        this.Username = userName;
        
        lblReport.setText("Inventory Receipt");         
              
       txtCustomerCodeFrom.enable(false);
        lblCustomerCodeFrom.enable(false);
        txtCustomerCodeTo.enable(false);   
        lblCustomerCodeTo.enable(false);
        
        btnSelectItem2.setEnabled(false);
        btnSelectItem3.setEnabled(false);
        
        
        
        lblStatus.enable(false);
        cmbStatus.enable(false);     
        
         lblSalesOrdeNoFrom.setEnabled(false);
        txtSalesOrdeNoFrom.setEnabled(false);
        btnSalesOrdeNoFrom.setEnabled(false);
        
        lblSalesOrdeNoTo.setEnabled(false);
        txtSalesOrdeNoTo.setEnabled(false);
        btnSalesOrdeNoTo.setEnabled(false);
        
        lblnvoiceNoFrom.setEnabled(false);
        txtnvoiceNoFrom.setEnabled(false);
        btnnvoiceNoFrom.setEnabled(false);
        
        lblnvoiceNoTo.setEnabled(false);
        txtnvoiceNoTo.setEnabled(false);
        btnnvoiceNoTo.setEnabled(false);
        
        
        
        
    }
     public reportFilter(String a,String b,String c, String d, String e,String userName) {
        
        //for Inventory Receipt Screen
        
        initComponents();
        con = DBconnect.connect(); 
        
        this.Username = userName;
        
        lblReport.setText("Inventory Return");         
              
        txtCustomerCodeFrom.enable(false);
        lblCustomerCodeFrom.enable(false);
        txtCustomerCodeTo.enable(false);   
        lblCustomerCodeTo.enable(false);
        
        btnSelectItem2.setEnabled(false);
        btnSelectItem3.setEnabled(false);     
                
        lblStatus.enable(false);
        cmbStatus.enable(false);    
        
        lblSalesOrdeNoFrom.setEnabled(false);
        txtSalesOrdeNoFrom.setEnabled(false);
        btnSalesOrdeNoFrom.setEnabled(false);
        
        lblSalesOrdeNoTo.setEnabled(false);
        txtSalesOrdeNoTo.setEnabled(false);
        btnSalesOrdeNoTo.setEnabled(false);
        
        lblnvoiceNoFrom.setEnabled(false);
        txtnvoiceNoFrom.setEnabled(false);
        btnnvoiceNoFrom.setEnabled(false);
        
        lblnvoiceNoTo.setEnabled(false);
        txtnvoiceNoTo.setEnabled(false);
        btnnvoiceNoTo.setEnabled(false);
        
        
        
    }   
    
    public reportFilter(String a,String b,String c, String d, String e, String f,String userName) {
        
        //for Sales Orders Screen
       
        initComponents();
        con = DBconnect.connect();  
        
        this.Username = userName;
        
        btnSelectItem.setEnabled(false);
        btnSelectItem1.setEnabled(false);
        
        lblItemCodeFrom.enable(false);
        txtItmCodeFrom.enable(false);       
        lblItemCodeTo.enable(false);
        txtItmCodeTo.enable(false);
        
        lblnvoiceNoFrom.setEnabled(false);
        txtnvoiceNoFrom.setEnabled(false);
        btnnvoiceNoFrom.setEnabled(false);
        
        lblnvoiceNoTo.setEnabled(false);
        txtnvoiceNoTo.setEnabled(false);
        btnnvoiceNoTo.setEnabled(false);
               
        lblReport.setText("Sales Order / Receipt Item Report");       
        
        //Remove Unnecessary Items From the Dropdown menu
        cmbStatus.removeItem("Active");
        cmbStatus.removeItem("Inactive");
        
        
    }   
    public reportFilter(String a,String b,String c, String d, String e, String f, String g,String userName) {
        
        //for Sales Invoices  Screen   
        
        initComponents();
        con = DBconnect.connect();    
        
        this.Username = userName;
        
        lblReport.setText("Sales Invoice Item Report"); 
        
        btnSelectItem.setEnabled(false);
        btnSelectItem1.setEnabled(false);
        
        lblItemCodeFrom.enable(false);
        txtItmCodeFrom.enable(false);       
        lblItemCodeTo.enable(false);
        txtItmCodeTo.enable(false);
        
        lblSalesOrdeNoFrom.setEnabled(false);
        txtSalesOrdeNoFrom.setEnabled(false);
        btnSalesOrdeNoFrom.setEnabled(false);
        
        lblSalesOrdeNoTo.setEnabled(false);
        txtSalesOrdeNoTo.setEnabled(false);
        btnSalesOrdeNoTo.setEnabled(false);
        
        //Remove Unnecessary Items From the Dropdown menu
        cmbStatus.removeItem("Open");
        cmbStatus.removeItem("Active");
        cmbStatus.removeItem("Inactive");
        
    }   

    reportFilter(String a, String b, String c, String d, String e, String f, String g, String h,String userName) {
        
        initComponents();
        con = DBconnect.connect();   
        
        this.Username = userName;
        
        lblReport.setText("Sales Order / Receipt Detail Report"); 
        
         btnSelectItem.setEnabled(false);
        btnSelectItem1.setEnabled(false);
        
        lblItemCodeFrom.enable(false);
        txtItmCodeFrom.enable(false);       
        lblItemCodeTo.enable(false);
        txtItmCodeTo.enable(false);
        
        lblnvoiceNoFrom.setEnabled(false);
        txtnvoiceNoFrom.setEnabled(false);
        btnnvoiceNoFrom.setEnabled(false);
        
        lblnvoiceNoTo.setEnabled(false);
        txtnvoiceNoTo.setEnabled(false);
        btnnvoiceNoTo.setEnabled(false);
               
         
        
        //Remove Unnecessary Items From the Dropdown menu
        cmbStatus.removeItem("Active");
        cmbStatus.removeItem("Inactive");
        
    }

    reportFilter(String a, String b, String c, String d, String e, String f, String g, String h, String i,String userName) {
        initComponents();
        con = DBconnect.connect();      
        
        this.Username = userName;
        
        lblReport.setText("Sales Invoice Detail Report"); 
        
        btnSelectItem.setEnabled(false);
        btnSelectItem1.setEnabled(false);
        
        lblItemCodeFrom.enable(false);
        txtItmCodeFrom.enable(false);       
        lblItemCodeTo.enable(false);
        txtItmCodeTo.enable(false);
        
        lblSalesOrdeNoFrom.setEnabled(false);
        txtSalesOrdeNoFrom.setEnabled(false);
        btnSalesOrdeNoFrom.setEnabled(false);
        
        lblSalesOrdeNoTo.setEnabled(false);
        txtSalesOrdeNoTo.setEnabled(false);
        btnSalesOrdeNoTo.setEnabled(false);
        
        //Remove Unnecessary Items From the Dropdown menu
        cmbStatus.removeItem("Open");
        cmbStatus.removeItem("Active");
        cmbStatus.removeItem("Inactive");
        
        
    }

 
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        lblReport = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtItmCodeFrom = new javax.swing.JTextField();
        txtItmCodeTo = new javax.swing.JTextField();
        txtFromDocDate = new com.toedter.calendar.JDateChooser();
        txtToDocDate = new com.toedter.calendar.JDateChooser();
        txtCustomerCodeFrom = new javax.swing.JTextField();
        txtCustomerCodeTo = new javax.swing.JTextField();
        lblItemCodeFrom = new javax.swing.JLabel();
        lblItemCodeTo = new javax.swing.JLabel();
        lblDocumentDateFrom = new javax.swing.JLabel();
        lblDocumentdateTo = new javax.swing.JLabel();
        lblCustomerCodeFrom = new javax.swing.JLabel();
        lblCustomerCodeTo = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        btnSelectItem = new javax.swing.JButton();
        btnSelectItem1 = new javax.swing.JButton();
        btnSelectItem2 = new javax.swing.JButton();
        btnSelectItem3 = new javax.swing.JButton();
        cmbStatus = new javax.swing.JComboBox<>();
        lblSalesOrdeNoFrom = new javax.swing.JLabel();
        lblSalesOrdeNoTo = new javax.swing.JLabel();
        txtSalesOrdeNoFrom = new javax.swing.JTextField();
        txtSalesOrdeNoTo = new javax.swing.JTextField();
        btnSalesOrdeNoFrom = new javax.swing.JButton();
        btnSalesOrdeNoTo = new javax.swing.JButton();
        lblnvoiceNoTo = new javax.swing.JLabel();
        lblnvoiceNoFrom = new javax.swing.JLabel();
        txtnvoiceNoFrom = new javax.swing.JTextField();
        txtnvoiceNoTo = new javax.swing.JTextField();
        btnnvoiceNoFrom = new javax.swing.JButton();
        btnnvoiceNoTo = new javax.swing.JButton();
        btnCustomerReoirt1 = new javax.swing.JButton();
        btnCustomerReoirt2 = new javax.swing.JButton();
        btnCustomerReoirt3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

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

        lblReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblReport.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(lblReport);
        lblReport.setBounds(160, 10, 190, 20);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Report Filter for ");
        jPanel3.add(jLabel16);
        jLabel16.setBounds(50, 10, 110, 20);

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
        jButton7.setBounds(430, 10, 30, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtItmCodeFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtItmCodeFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItmCodeFromKeyPressed(evt);
            }
        });

        txtItmCodeTo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtItmCodeTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItmCodeToKeyPressed(evt);
            }
        });

        txtFromDocDate.setDateFormatString("yyyy/MM/dd");
        txtFromDocDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtToDocDate.setDateFormatString("yyyy/MM/dd");
        txtToDocDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtCustomerCodeFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCustomerCodeFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCustomerCodeFromKeyPressed(evt);
            }
        });

        txtCustomerCodeTo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCustomerCodeTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCustomerCodeToKeyPressed(evt);
            }
        });

        lblItemCodeFrom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblItemCodeFrom.setText("Item Code From");

        lblItemCodeTo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblItemCodeTo.setText("Item Code To");

        lblDocumentDateFrom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDocumentDateFrom.setText("Document Date From");

        lblDocumentdateTo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDocumentdateTo.setText("Document date To");

        lblCustomerCodeFrom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCustomerCodeFrom.setText("Customer Code From");

        lblCustomerCodeTo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCustomerCodeTo.setText("Customer Code To");

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblStatus.setText("Status");

        btnSelectItem.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSelectItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSelectItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectItemActionPerformed(evt);
            }
        });

        btnSelectItem1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSelectItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSelectItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectItem1ActionPerformed(evt);
            }
        });

        btnSelectItem2.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSelectItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSelectItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectItem2ActionPerformed(evt);
            }
        });

        btnSelectItem3.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSelectItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSelectItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectItem3ActionPerformed(evt);
            }
        });

        cmbStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive", "Open", "Closed", "Canceled" }));

        lblSalesOrdeNoFrom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSalesOrdeNoFrom.setText("Sales Order No. From");

        lblSalesOrdeNoTo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSalesOrdeNoTo.setText("Sales Order No. To");

        txtSalesOrdeNoFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSalesOrdeNoFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSalesOrdeNoFromKeyPressed(evt);
            }
        });

        txtSalesOrdeNoTo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSalesOrdeNoTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSalesOrdeNoToKeyPressed(evt);
            }
        });

        btnSalesOrdeNoFrom.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSalesOrdeNoFrom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSalesOrdeNoFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesOrdeNoFromActionPerformed(evt);
            }
        });

        btnSalesOrdeNoTo.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnSalesOrdeNoTo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnSalesOrdeNoTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesOrdeNoToActionPerformed(evt);
            }
        });

        lblnvoiceNoTo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblnvoiceNoTo.setText("Invoice No. To");

        lblnvoiceNoFrom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblnvoiceNoFrom.setText("Invoice No. From");

        txtnvoiceNoFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtnvoiceNoFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnvoiceNoFromKeyPressed(evt);
            }
        });

        txtnvoiceNoTo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtnvoiceNoTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnvoiceNoToKeyPressed(evt);
            }
        });

        btnnvoiceNoFrom.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnnvoiceNoFrom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnnvoiceNoFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnvoiceNoFromActionPerformed(evt);
            }
        });

        btnnvoiceNoTo.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnnvoiceNoTo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow2.png"))); // NOI18N
        btnnvoiceNoTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnvoiceNoToActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCustomerCodeTo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemCodeTo)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblItemCodeFrom)
                                    .addComponent(lblDocumentDateFrom)
                                    .addComponent(lblDocumentdateTo)
                                    .addComponent(lblSalesOrdeNoFrom)
                                    .addComponent(lblSalesOrdeNoTo)
                                    .addComponent(lblnvoiceNoFrom)
                                    .addComponent(lblnvoiceNoTo)
                                    .addComponent(lblCustomerCodeFrom))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtnvoiceNoTo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnnvoiceNoTo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtSalesOrdeNoFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSalesOrdeNoFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                    .addComponent(txtToDocDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtSalesOrdeNoTo)
                                            .addComponent(txtnvoiceNoFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnnvoiceNoFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(9, 9, 9)
                                                .addComponent(btnSalesOrdeNoTo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtFromDocDate, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtItmCodeTo, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                                    .addComponent(txtItmCodeFrom))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnSelectItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(txtCustomerCodeTo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                                    .addComponent(txtCustomerCodeFrom, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnSelectItem2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnSelectItem3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(29, 29, 29))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblItemCodeFrom)
                    .addComponent(txtItmCodeFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblItemCodeTo)
                    .addComponent(txtItmCodeTo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblDocumentDateFrom)
                    .addComponent(txtFromDocDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblDocumentdateTo)
                    .addComponent(txtToDocDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSalesOrdeNoFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSalesOrdeNoFrom)
                    .addComponent(btnSalesOrdeNoFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSalesOrdeNoTo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSalesOrdeNoTo)
                    .addComponent(btnSalesOrdeNoTo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnnvoiceNoFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnvoiceNoFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnvoiceNoFrom))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnnvoiceNoTo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnvoiceNoTo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnvoiceNoTo))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnSelectItem2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustomerCodeFrom)
                    .addComponent(txtCustomerCodeFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnSelectItem3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustomerCodeTo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustomerCodeTo))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        btnCustomerReoirt1.setBackground(new java.awt.Color(3, 35, 55));
        btnCustomerReoirt1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerReoirt1.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomerReoirt1.setText("View Report");
        btnCustomerReoirt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerReoirt1ActionPerformed(evt);
            }
        });

        btnCustomerReoirt2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerReoirt2.setText("Reset");
        btnCustomerReoirt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerReoirt2ActionPerformed(evt);
            }
        });

        btnCustomerReoirt3.setBackground(new java.awt.Color(255, 0, 0));
        btnCustomerReoirt3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerReoirt3.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomerReoirt3.setText("Close");
        btnCustomerReoirt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerReoirt3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCustomerReoirt1)
                .addGap(18, 18, 18)
                .addComponent(btnCustomerReoirt2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCustomerReoirt3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCustomerReoirt1)
                    .addComponent(btnCustomerReoirt2)
                    .addComponent(btnCustomerReoirt3))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx-x, yy-y);
       
    }//GEN-LAST:event_jPanel3MouseDragged

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
        x = evt.getX();
        y = evt.getY();
       
    }//GEN-LAST:event_jPanel3MousePressed

    private void txtItmCodeFromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItmCodeFromKeyPressed

      

    }//GEN-LAST:event_txtItmCodeFromKeyPressed

    private void txtCustomerCodeFromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerCodeFromKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerCodeFromKeyPressed

    private void txtCustomerCodeToKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerCodeToKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerCodeToKeyPressed

    private void txtItmCodeToKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItmCodeToKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItmCodeToKeyPressed

    private void btnCustomerReoirt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerReoirt1ActionPerformed

        String x = lblReport.getText();

        switch (x){
            case "List of Customers" :

            HashMap m = new HashMap();
            m.put("status1",cmbStatus.getSelectedItem().toString());
            m.put("UserName",Username);

            try {

                JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/customerReport.jrxml");

                JasperReport jr = JasperCompileManager.compileReport(path);

                JasperPrint  jp = JasperFillManager.fillReport(jr,m,con);
                JasperViewer.viewReport(jp,false);
            } catch (JRException e)
            {
                JOptionPane.showMessageDialog(null, "error "+e);
            }

            break;
            case "List of Items":

            HashMap n = new HashMap();
            n.put("status2",cmbStatus.getSelectedItem().toString());
            n.put("UserName",Username);
            

            try {

                JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/itemReport.jrxml");

                JasperReport jr = JasperCompileManager.compileReport(path);

                JasperPrint  jp = JasperFillManager.fillReport(jr,n,con);
                JasperViewer.viewReport(jp,false);

            } catch (JRException e)
            {
                JOptionPane.showMessageDialog(null, "error "+e);
            }

            break;

            case "Inventory":

            if (txtItmCodeFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Item Code From Field is Required ");
                txtItmCodeFrom.requestFocus();
                break;
            }
            if(txtItmCodeTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Item Code To Field is Required ");
                txtItmCodeTo.requestFocus();
            }

            else{

                HashMap o = new HashMap();
                o.put("FromItemCode",txtItmCodeFrom.getText());
                o.put("toItemCode", txtItmCodeTo.getText());
                o.put("UserName",Username);
                o.put("UserName",Username);

                try {

                    JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/inventoryReport.jrxml");

                    JasperReport jr = JasperCompileManager.compileReport(path);

                    JasperPrint  jp = JasperFillManager.fillReport(jr,o,con);
                    JasperViewer.viewReport(jp,false);

                } catch (JRException e)
                {
                    JOptionPane.showMessageDialog(null, "error "+e);
                }

            }

            break;

            case "Inventory Receipt":

            if (txtItmCodeFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Item Code From Field is Required ");
                txtItmCodeFrom.requestFocus();
                break;
            }
            if(txtItmCodeTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Item Code To Field is Required ");
                txtItmCodeTo.requestFocus();
                break;
            }

            if(txtFromDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date From Field is Required ");
                txtFromDocDate.requestFocus();
                break;
            }

            if(txtToDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date To Field is Required ");
                txtToDocDate.requestFocus();

            }
            else{

                HashMap P = new HashMap();
                P.put("fromItemCod",txtItmCodeFrom.getText());
                P.put("ToItemCod", txtItmCodeTo.getText());
                P.put("FromDate",txtFromDocDate.getDate());
                P.put("ToDate",txtToDocDate.getDate());
                P.put("UserName",Username);
                try {

                    JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/inventoryReceiptReport1.jrxml");

                    JasperReport jr = JasperCompileManager.compileReport(path);

                    JasperPrint  jp = JasperFillManager.fillReport(jr,P,con);
                    JasperViewer.viewReport(jp,false);

                } catch (JRException e)
                {
                    JOptionPane.showMessageDialog(null, "error "+e);
                }

            }

            break;

            case "Inventory Return":
                
                 if (txtItmCodeFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Item Code From Field is Required ");
                txtItmCodeFrom.requestFocus();
                break;
            }
            if(txtItmCodeTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Item Code To Field is Required ");
                txtItmCodeTo.requestFocus();
                break;
            }

            if(txtFromDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date From Field is Required ");
                txtFromDocDate.requestFocus();
                break;
            }

            if(txtToDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date To Field is Required ");
                txtToDocDate.requestFocus();
            }
            else{
             HashMap P = new HashMap();
                P.put("fromItemCod",txtItmCodeFrom.getText());
                P.put("ToItemCod", txtItmCodeTo.getText());
                P.put("FromDate",txtFromDocDate.getDate());
                P.put("ToDate",txtToDocDate.getDate());
                P.put("UserName",Username);
                try {

                    JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/inventoryReturnReport.jrxml");

                    JasperReport jr = JasperCompileManager.compileReport(path);

                    JasperPrint  jp = JasperFillManager.fillReport(jr,P,con);
                    JasperViewer.viewReport(jp,false);

                } catch (JRException e)
                {
                    JOptionPane.showMessageDialog(null, "error "+e);
                }
                           
            
            }     

            break;
            
            
            case "Sales Order / Receipt Item Report" :
                
               if (txtSalesOrdeNoFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Sales Order No. From Field is Required ");
                txtSalesOrdeNoFrom.requestFocus();
                break;
            }
            if(txtSalesOrdeNoTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Sales Order No. To Field is Required ");
                txtSalesOrdeNoTo.requestFocus();
                break;
            }

            if(txtFromDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date From Field is Required ");
                txtFromDocDate.requestFocus();
                break;
            }

            if(txtToDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date To Field is Required ");
                txtToDocDate.requestFocus();
            }  
                
                
            if (txtCustomerCodeFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Customer Code From Field is Required ");
                txtCustomerCodeFrom.requestFocus();
                break;
            }
            if(txtCustomerCodeTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Customer Code To Field is Required ");
                txtCustomerCodeTo.requestFocus();
                
            }    
                
            else{
            
            HashMap P = new HashMap();
                
                P.put("FromSODNo",txtSalesOrdeNoFrom.getText());
                P.put("ToSODNo", txtSalesOrdeNoTo.getText());               
                                                
                P.put("FromDate",txtFromDocDate.getDate());
                P.put("Todate",txtToDocDate.getDate());
                
                P.put("FromCustomerCode",txtCustomerCodeFrom.getText());
                P.put("ToCustomerCode", txtCustomerCodeTo.getText());
                
                P.put("Status", cmbStatus.getSelectedItem());
                P.put("UserName",Username);
                
                
                
                try {

                    JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/salesOrdersItemReport.jrxml");

                    JasperReport jr = JasperCompileManager.compileReport(path);

                    JasperPrint  jp = JasperFillManager.fillReport(jr,P,con);
                    JasperViewer.viewReport(jp,false);

                } catch (JRException e)
                {
                    JOptionPane.showMessageDialog(null, "error "+e);
                }
            
                break;
        }
            
            case "Sales Invoice Item Report":
                
                            
               if (txtnvoiceNoFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Invoice No. From Field is Required ");
                txtnvoiceNoFrom.requestFocus();
                break;
            }
            if(txtnvoiceNoTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Invoice No. To Field is Required ");
                txtnvoiceNoTo.requestFocus();
                break;
            }

            if(txtFromDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date From Field is Required ");
                txtFromDocDate.requestFocus();
                break;
            }

            if(txtToDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date To Field is Required ");
                txtToDocDate.requestFocus();
            }  
                
                
            if (txtCustomerCodeFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Customer Code From Field is Required ");
                txtCustomerCodeFrom.requestFocus();
                break;
            }
            if(txtCustomerCodeTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Customer Code To Field is Required ");
                txtCustomerCodeTo.requestFocus();
                
            }               
                
                  else{
            
            HashMap P = new HashMap();
                
                P.put("FromDOCCod",txtnvoiceNoFrom.getText());
                P.put("ToDOCCod", txtnvoiceNoTo.getText());               
                                                
                P.put("FromDate",txtFromDocDate.getDate());
                P.put("ToDate",txtToDocDate.getDate());
                
                P.put("FromCustomerCod",txtCustomerCodeFrom.getText());
                P.put("ToCustomerCod", txtCustomerCodeTo.getText());
                
                P.put("Status", cmbStatus.getSelectedItem());
                P.put("UserName",Username);
                
                
                
                try {

                    JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/salesInvoceItemReport.jrxml");

                    JasperReport jr = JasperCompileManager.compileReport(path);

                    JasperPrint  jp = JasperFillManager.fillReport(jr,P,con);
                    JasperViewer.viewReport(jp,false);

                } catch (JRException e)
                {
                    JOptionPane.showMessageDialog(null, "error "+e);
                }    
                
                
            }
                
                
                
                break;
            
            case "Sales Order / Receipt Detail Report":
                
                            
               if (txtSalesOrdeNoFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Sales Order No. From Field is Required ");
                txtSalesOrdeNoFrom.requestFocus();
                break;
            }
            if(txtSalesOrdeNoTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Sales Order No. To Field is Required ");
                txtSalesOrdeNoTo.requestFocus();
                break;
            }

            if(txtFromDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date From Field is Required ");
                txtFromDocDate.requestFocus();
                break;
            }

            if(txtToDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date To Field is Required ");
                txtToDocDate.requestFocus();
            }  
                
                
            if (txtCustomerCodeFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Customer Code From Field is Required ");
                txtCustomerCodeFrom.requestFocus();
                break;
            }
            if(txtCustomerCodeTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Customer Code To Field is Required ");
                txtCustomerCodeTo.requestFocus();
                
            }    
                
            else{
            
            HashMap P = new HashMap();
                
                P.put("FromSODNo",txtSalesOrdeNoFrom.getText());
                P.put("ToSODNo", txtSalesOrdeNoTo.getText());               
                                                
                P.put("FromDate",txtFromDocDate.getDate());
                P.put("Todate",txtToDocDate.getDate());
                
                P.put("FromCustomerCode",txtCustomerCodeFrom.getText());
                P.put("ToCustomerCode", txtCustomerCodeTo.getText());
                
                P.put("Status", cmbStatus.getSelectedItem());
                P.put("UserName",Username);
                
                
                
                try {

                    JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/salesOrdersDetailReport.jrxml");

                    JasperReport jr = JasperCompileManager.compileReport(path);

                    JasperPrint  jp = JasperFillManager.fillReport(jr,P,con);
                    JasperViewer.viewReport(jp,false);

                } catch (JRException e)
                {
                    JOptionPane.showMessageDialog(null, "error "+e);
                }
            
                break;
            
            }       
         case "Sales Invoice Detail Report":
                
                            
               if (txtnvoiceNoFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Invoice No. From Field is Required ");
                txtnvoiceNoFrom.requestFocus();
                break;
            }
            if(txtnvoiceNoTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Invoice No. To Field is Required ");
                txtnvoiceNoTo.requestFocus();
                break;
            }

            if(txtFromDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date From Field is Required ");
                txtFromDocDate.requestFocus();
                break;
            }

            if(txtToDocDate.getDate()== null) {

                JOptionPane.showMessageDialog(null, "Document Date To Field is Required ");
                txtToDocDate.requestFocus();
            }  
                
                
            if (txtCustomerCodeFrom.getText().equals("")){

                JOptionPane.showMessageDialog(null, "Customer Code From Field is Required ");
                txtCustomerCodeFrom.requestFocus();
                break;
            }
            if(txtCustomerCodeTo.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Customer Code To Field is Required ");
                txtCustomerCodeTo.requestFocus();
                
            }               
                
                  else{
            
            HashMap P = new HashMap();
                
                P.put("FromDocumentCod",txtnvoiceNoFrom.getText());
                P.put("ToDocumentCod", txtnvoiceNoTo.getText());               
                                                
                P.put("FromDate",txtFromDocDate.getDate());
                P.put("ToDate",txtToDocDate.getDate());
                
                P.put("FromCustomerCod",txtCustomerCodeFrom.getText());
                P.put("ToCustomerCod", txtCustomerCodeTo.getText());
                
                P.put("Status", cmbStatus.getSelectedItem());
                P.put("UserName",Username);
                
                
                
                try {

                    JasperDesign path = JRXmlLoader.load(new File("").getAbsolutePath()+ "/src/PrintLayouts/salesInvoceDetailReport.jrxml");

                    JasperReport jr = JasperCompileManager.compileReport(path);

                    JasperPrint  jp = JasperFillManager.fillReport(jr,P,con);
                    JasperViewer.viewReport(jp,false);

                } catch (JRException e)
                {
                    JOptionPane.showMessageDialog(null, "error "+e);
                }    
                
                
            }
                
                
                
                break;
            
            
            }    
                
                
                
                
                
                
                
                

    }//GEN-LAST:event_btnCustomerReoirt1ActionPerformed

    private void btnCustomerReoirt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerReoirt2ActionPerformed

        txtItmCodeFrom.setText("");
        txtItmCodeTo.setText("");
        txtFromDocDate.setDate(null);
        txtToDocDate.setDate(null);
        txtCustomerCodeFrom.setText("");
        txtCustomerCodeTo.setText("");
        txtSalesOrdeNoFrom.setText("");
        txtSalesOrdeNoTo.setText("");
        txtnvoiceNoFrom.setText("");
        txtnvoiceNoTo.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_btnCustomerReoirt2ActionPerformed

    private void btnCustomerReoirt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerReoirt3ActionPerformed
       
        this.setVisible(false);
        
    }//GEN-LAST:event_btnCustomerReoirt3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setExtendedState(HomePage.ICONIFIED);

    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnSelectItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectItemActionPerformed

        itmPickFromReportFilter itmp = new itmPickFromReportFilter(this);
        itmp.setVisible(true);

    }//GEN-LAST:event_btnSelectItemActionPerformed

    private void btnSelectItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectItem1ActionPerformed
            itmPickToReportFilter itmp = new itmPickToReportFilter(this);
            itmp.setVisible(true);
    }//GEN-LAST:event_btnSelectItem1ActionPerformed

    private void btnSelectItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectItem2ActionPerformed
        
        CusPickFromReportFilter cusp = new CusPickFromReportFilter(this);
        cusp.setVisible(true);
        
    }//GEN-LAST:event_btnSelectItem2ActionPerformed

    private void btnSelectItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectItem3ActionPerformed
       CusPickToReportFilte cusp = new CusPickToReportFilte(this);
       cusp.setVisible(true);
    }//GEN-LAST:event_btnSelectItem3ActionPerformed

    private void txtSalesOrdeNoFromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalesOrdeNoFromKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSalesOrdeNoFromKeyPressed

    private void txtSalesOrdeNoToKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalesOrdeNoToKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSalesOrdeNoToKeyPressed

    private void btnSalesOrdeNoFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesOrdeNoFromActionPerformed
       SalesOrderDetailPickReortFrom SODPRFrom = new SalesOrderDetailPickReortFrom(this);
       SODPRFrom.setVisible(true);
    }//GEN-LAST:event_btnSalesOrdeNoFromActionPerformed

    private void btnSalesOrdeNoToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesOrdeNoToActionPerformed
        SalesOrderDetailPickReortTo SODPRTo  = new SalesOrderDetailPickReortTo(this);
        SODPRTo.setVisible(true);
    }//GEN-LAST:event_btnSalesOrdeNoToActionPerformed

    private void txtnvoiceNoFromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnvoiceNoFromKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnvoiceNoFromKeyPressed

    private void txtnvoiceNoToKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnvoiceNoToKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnvoiceNoToKeyPressed

    private void btnnvoiceNoFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnvoiceNoFromActionPerformed
    SalesInvoiceDetailPickeReportFrom  salesInvoiceFrom = new SalesInvoiceDetailPickeReportFrom(this);  
    salesInvoiceFrom.setVisible(true);
    }//GEN-LAST:event_btnnvoiceNoFromActionPerformed

    private void btnnvoiceNoToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnvoiceNoToActionPerformed
        SalesInvoiceDetailPickeReportTo salesInvoiceTo = new SalesInvoiceDetailPickeReportTo(this);
        salesInvoiceTo.setVisible(true);
    }//GEN-LAST:event_btnnvoiceNoToActionPerformed

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
            java.util.logging.Logger.getLogger(reportFilter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(reportFilter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(reportFilter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(reportFilter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new reportFilter().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomerReoirt1;
    private javax.swing.JButton btnCustomerReoirt2;
    private javax.swing.JButton btnCustomerReoirt3;
    private javax.swing.JButton btnSalesOrdeNoFrom;
    private javax.swing.JButton btnSalesOrdeNoTo;
    private javax.swing.JButton btnSelectItem;
    private javax.swing.JButton btnSelectItem1;
    private javax.swing.JButton btnSelectItem2;
    private javax.swing.JButton btnSelectItem3;
    private javax.swing.JButton btnnvoiceNoFrom;
    private javax.swing.JButton btnnvoiceNoTo;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblCustomerCodeFrom;
    private javax.swing.JLabel lblCustomerCodeTo;
    private javax.swing.JLabel lblDocumentDateFrom;
    private javax.swing.JLabel lblDocumentdateTo;
    private javax.swing.JLabel lblItemCodeFrom;
    private javax.swing.JLabel lblItemCodeTo;
    private javax.swing.JLabel lblReport;
    private javax.swing.JLabel lblSalesOrdeNoFrom;
    private javax.swing.JLabel lblSalesOrdeNoTo;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblnvoiceNoFrom;
    private javax.swing.JLabel lblnvoiceNoTo;
    private javax.swing.JTextField txtCustomerCodeFrom;
    private javax.swing.JTextField txtCustomerCodeTo;
    private com.toedter.calendar.JDateChooser txtFromDocDate;
    private javax.swing.JTextField txtItmCodeFrom;
    private javax.swing.JTextField txtItmCodeTo;
    private javax.swing.JTextField txtSalesOrdeNoFrom;
    private javax.swing.JTextField txtSalesOrdeNoTo;
    private com.toedter.calendar.JDateChooser txtToDocDate;
    private javax.swing.JTextField txtnvoiceNoFrom;
    private javax.swing.JTextField txtnvoiceNoTo;
    // End of variables declaration//GEN-END:variables

    private void selectItemCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setText(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
public void setC (String x){
    txtItmCodeFrom.setText(x);
    txtItmCodeFrom.requestFocus();

}
public void setD(String y){
    txtItmCodeTo.setText(y);
    txtItmCodeTo.requestFocus();

}

public void setE(String Z){
    txtCustomerCodeFrom.setText(Z);
    txtCustomerCodeFrom.requestFocus();

}

public void setF(String A){
    txtCustomerCodeTo.setText(A);
    txtCustomerCodeTo.requestFocus();

}

public void setG(String AA){
    txtnvoiceNoFrom.setText(AA);
    txtnvoiceNoFrom.requestFocus();

}

public void setH(String AB){
    txtnvoiceNoTo.setText(AB);
    txtnvoiceNoTo.requestFocus();

}
public void setI(String cde){
    txtSalesOrdeNoFrom.setText(cde);
    txtSalesOrdeNoFrom.requestFocus();

}
public void setJ(String cdef){
    txtSalesOrdeNoTo.setText(cdef);
    txtSalesOrdeNoTo.requestFocus();

}



}
