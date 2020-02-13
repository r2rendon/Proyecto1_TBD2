/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_tbd2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

//CREATE VIEW PRUEBAVIEW 
//AS
//SELECT * FROM DENOU;

/**
 *
 * @author arturorendon
 */
public class MainPage extends javax.swing.JFrame {

    private String dbURL = "", username = "", pass = "", crearTSQL = "";
    boolean createComenzado = false;
    
    /**
     * Creates new form MainPage
     */
    public MainPage(String url, String u, String p) {
        initComponents();
        this.dbURL = url;
        this.username = u;
        this.pass = p;
        this.tablas.setEditable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        ddlTA.setLineWrap(true);
        this.tamanioLabel.setVisible(false);
        this.tamanioSpinner.setVisible(false);
        this.confirmarBORINDEX.setVisible(true);
        initTablasTA();
        this.initTablasComboBox();
        this.listarIndices();
        this.confirmarButton.setVisible(false);
        this.initIndicesComboBox();
        
        //------> TRIGGERS <------
        this.initTriggerTablas();
        this.initTriggerTablasComboBox();
        this.triggerTablas.setEditable(false);
        
        //------> VIEWS <------
        this.viewsListTA.setEditable(false);
        this.confirmarCreateView.setVisible(false);
        this.confirmarViewMod.setVisible(false);
        this.initViewsList();
        this.initViewsCombo(this.viewCOMB);
        this.initViewsCombo(this.viewMODCOMB);
        
        //------> USERS <------
        this.usersTA.setEditable(false);
        this.initUsersCombo();
        this.initUsersTA();
        this.nUserLabel.setVisible(false);
        this.nPassLabel1.setVisible(false);
        this.nUser.setVisible(false);
        this.nPass.setVisible(false);
        

        
    }
    
    private void initTablasComboBox(){
        this.tablasCombo.removeAllItems();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC';");
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.first();
            String tShow = "";
            tShow = rs.getString("TABLE_NAME")+"\n";
            this.tablasCombo.addItem(tShow);
            while(rs.next()){
                tShow = rs.getString("TABLE_NAME")+"\n";
                this.tablasCombo.addItem(tShow);
            }
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initTablasTA(){
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC';");
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.first();
            String tShow = "";
            
            System.out.println(rs.getString(1));
            System.out.println(rowCount);
            tShow = "\tTABLAS\n";
            tShow += rs.getString("TABLE_NAME")+"\n";
            while(rs.next()){
                tShow += rs.getString("TABLE_NAME")+"\n";
            }
            
            
            tablas.setText(tShow);
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listarIndices(){
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT INDEX_NAME, TABLE_NAME FROM INFORMATION_SCHEMA.INDEXES;");
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.first();
            String tShow = "";
            
            System.out.println(rs.getString(1));
            System.out.println(rowCount);
            tShow = "NOMBRE DEL INDICE\tNOMBRE DE LA TABLA\n";
            tShow += rs.getString("INDEX_NAME")+"\t\t"+rs.getString("TABLE_NAME")+"\n";
            while(rs.next()){
                tShow += rs.getString("INDEX_NAME")+"\t\t"+rs.getString("TABLE_NAME")+"\n";
            }
            
            
            indicesTA.setText(tShow);
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initFKTablas(){
        this.fkTable.removeAllItems();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC';");
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.first();
            String tShow = "";
            tShow = rs.getString("TABLE_NAME")+"\n";
            this.fkTable.addItem(tShow);
            while(rs.next()){
                tShow = rs.getString("TABLE_NAME")+"\n";
                this.fkTable.addItem(tShow);
            }
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initIndicesComboBox(){
        this.indicesCOMBOBOX.removeAllItems();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT INDEX_NAME FROM INFORMATION_SCHEMA.INDEXES;");
            rs.first();
            String tShow = "";
            tShow = rs.getString("INDEX_NAME")+"\n";
            this.indicesCOMBOBOX.addItem(tShow);
            while(rs.next()){
                tShow = rs.getString("INDEX_NAME")+"\n";
                this.indicesCOMBOBOX.addItem(tShow);
            }
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initTriggerTablas(){
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC';");
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.first();
            String tShow = "";
            
            System.out.println(rs.getString(1));
            System.out.println(rowCount);
            tShow = "\tTABLAS\n";
            tShow += rs.getString("TABLE_NAME")+"\n";
            while(rs.next()){
                tShow += rs.getString("TABLE_NAME")+"\n";
            }
            
            
            this.triggerTablas.setText(tShow);
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initTriggerTablasComboBox(){
        this.triggerTablCOMB.removeAllItems();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC';");
            rs.first();
            String tShow = "";
            tShow = rs.getString("TABLE_NAME")+"\n";
            this.triggerTablCOMB.addItem(tShow);
            while(rs.next()){
                tShow = rs.getString("TABLE_NAME")+"\n";
                this.triggerTablCOMB.addItem(tShow);
            }
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initViewsList(){
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = 'PUBLIC';");
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.first();
            String tShow = "";
            
            System.out.println(rs.getString(1));
            System.out.println(rowCount);
            tShow = "\tVIEWS\n";
            tShow += rs.getString("TABLE_NAME")+"\n";
            while(rs.next()){
                tShow += rs.getString("TABLE_NAME")+"\n";
            }
            
            
            this.viewsListTA.setText(tShow);
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initViewsCombo(javax.swing.JComboBox<String> app){
        app.removeAllItems();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = 'PUBLIC';");
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.first();
            String tShow = "";
            tShow = rs.getString("TABLE_NAME")+"\n";
            app.addItem(tShow);
            while(rs.next()){
                tShow = rs.getString("TABLE_NAME")+"\n";
                app.addItem(tShow);
            }
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initUsersCombo(){
        this.usersCOMB.removeAllItems();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT NAME FROM INFORMATION_SCHEMA.USERS ;");
            rs.first();
            String tShow = "";
            tShow = rs.getString("NAME")+"\n";
            this.usersCOMB.addItem(tShow);
            while(rs.next()){
                tShow = rs.getString("NAME")+"\n";
                this.usersCOMB.addItem(tShow);
            }
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initUsersTA(){
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT NAME FROM INFORMATION_SCHEMA.USERS ;");
            rs.first();
            String tShow = "";
            tShow = "\tUSERS\n";
            tShow += rs.getString("NAME")+"\n";
            while(rs.next()){
                tShow += rs.getString("NAME")+"\n";
            }
            
            
            this.usersTA.setText(tShow);
            
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void editUser(String user, String nUser, String nPass, boolean n, boolean nU){
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement();
            
            if (n) {
                st.executeUpdate("ALTER USER "+user+" RENAME TO "+nUser+";");
                st.executeUpdate("ALTER USER "+nUser+" SET PASSWORD '"+nPass+"'");
            }
            else if(nU){
                st.executeUpdate("ALTER USER "+user+" RENAME TO "+nUser+";");
                st.executeUpdate("ALTER USER "+user+" SET PASSWORD '"+nPass+"'");
            }else if(!nU){  
                st.executeUpdate("ALTER USER "+user+" SET PASSWORD '"+nPass+"'");
            }
            
            
            JOptionPane.showMessageDialog(null, "El usuario se ha editado exitosamente!!!");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
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

        tiposDeDato = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Main = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablas = new javax.swing.JTextArea();
        crearTabla = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        nombreCol = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        intRB = new javax.swing.JRadioButton();
        varcharRB = new javax.swing.JRadioButton();
        charRB = new javax.swing.JRadioButton();
        doubleRB = new javax.swing.JRadioButton();
        tamanioLabel = new javax.swing.JLabel();
        tamanioSpinner = new javax.swing.JSpinner();
        insertarColumna = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        nombreTabla = new javax.swing.JTextField();
        crearT = new javax.swing.JButton();
        pkCHECKBOX = new javax.swing.JCheckBox();
        fkCHECKBOX = new javax.swing.JCheckBox();
        fkTable = new javax.swing.JComboBox<>();
        fkColumn = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dropName = new javax.swing.JTextField();
        dropButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nomIndex = new javax.swing.JTextField();
        tablasCombo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        crearIndice = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        tablasCOLUMNCOMB = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        indicesCOMBOBOX = new javax.swing.JComboBox<>();
        confirmarBORINDEX = new javax.swing.JButton();
        borrarIndice = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ddlTA = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        indicesTA = new javax.swing.JTextArea();
        confirmarButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        triggerTablas = new javax.swing.JTextArea();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        triggerNombre = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        triggerAFTERCOMB = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        triggerTablCOMB = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        triggerCOLUMNCOMB = new javax.swing.JComboBox<>();
        triggerGDDL = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        triggerDDLTA = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        viewNombre = new javax.swing.JTextField();
        crearView = new javax.swing.JButton();
        confirmarCreateView = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        viewCOMB = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        viewBORRARCONFIRM = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        viewMODCOMB = new javax.swing.JComboBox<>();
        modificarView = new javax.swing.JButton();
        confirmarViewMod = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        viewDDLTA = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        viewsListTA = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        usersCOMB = new javax.swing.JComboBox<>();
        eliminarUsuario = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        userEditU = new javax.swing.JCheckBox();
        userEditP = new javax.swing.JCheckBox();
        nUserLabel = new javax.swing.JLabel();
        nUser = new javax.swing.JTextField();
        nPassLabel1 = new javax.swing.JLabel();
        nPass = new javax.swing.JTextField();
        editarUser = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        usersTA = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablas.setColumns(20);
        tablas.setRows(5);
        jScrollPane1.setViewportView(tablas);

        crearTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                crearTablaMouseClicked(evt);
            }
        });

        jLabel1.setText("Nombre de la columna:");

        tiposDeDato.add(intRB);
        intRB.setLabel("Int");
        intRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intRBActionPerformed(evt);
            }
        });

        tiposDeDato.add(varcharRB);
        varcharRB.setLabel("VarChar");
        varcharRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varcharRBActionPerformed(evt);
            }
        });

        tiposDeDato.add(charRB);
        charRB.setLabel("Char");
        charRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charRBActionPerformed(evt);
            }
        });

        tiposDeDato.add(doubleRB);
        doubleRB.setText("Double");
        doubleRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doubleRBActionPerformed(evt);
            }
        });

        tamanioLabel.setText("Tamaño:");

        tamanioSpinner.setSize(new java.awt.Dimension(100, 26));

        insertarColumna.setText("Insertar Columna");
        insertarColumna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarColumnaActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre de la tabla:");

        crearT.setText("Crear Tabla");
        crearT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearTActionPerformed(evt);
            }
        });

        pkCHECKBOX.setText("Llave Primaria");
        pkCHECKBOX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pkCHECKBOXActionPerformed(evt);
            }
        });

        fkCHECKBOX.setText("Llave Foranea");
        fkCHECKBOX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fkCHECKBOXActionPerformed(evt);
            }
        });

        fkTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fkTableActionPerformed(evt);
            }
        });

        jLabel7.setText("Tabla");

        jLabel8.setText("Columna");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fkColumn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addComponent(nombreCol)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(intRB)
                                        .addComponent(charRB))
                                    .addGap(6, 6, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(doubleRB)
                                        .addComponent(varcharRB)))
                                .addComponent(tamanioSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(insertarColumna)
                                .addComponent(jLabel2)
                                .addComponent(nombreTabla))
                            .addComponent(tamanioLabel)
                            .addComponent(pkCHECKBOX)
                            .addComponent(crearT)
                            .addComponent(fkCHECKBOX))
                        .addGap(0, 48, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fkTable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(nombreTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(nombreCol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intRB)
                    .addComponent(varcharRB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(charRB)
                    .addComponent(doubleRB))
                .addGap(7, 7, 7)
                .addComponent(pkCHECKBOX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tamanioLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tamanioSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fkCHECKBOX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fkTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fkColumn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(insertarColumna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crearT)
                .addContainerGap())
        );

        crearTabla.addTab("Crear Tabla", jPanel1);

        jLabel3.setText("Nombre de la tabla:");

        dropButton.setText("Borrar");
        dropButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(dropName, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dropButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dropName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dropButton)
                .addContainerGap(396, Short.MAX_VALUE))
        );

        crearTabla.addTab("Borrar Tabla", jPanel3);

        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel4.setText("Nombre del indice:");

        tablasCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tablasComboActionPerformed(evt);
            }
        });

        jLabel5.setText("Tabla:");

        crearIndice.setText("Crear Indice");
        crearIndice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearIndiceActionPerformed(evt);
            }
        });

        jLabel6.setText("Columna:");

        tablasCOLUMNCOMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tablasCOLUMNCOMBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablasCOLUMNCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4)
                        .addComponent(nomIndex)
                        .addComponent(tablasCombo, 0, 147, Short.MAX_VALUE))
                    .addComponent(jLabel5)
                    .addComponent(crearIndice)
                    .addComponent(jLabel6))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nomIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(tablasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(tablasCOLUMNCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(crearIndice)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        crearTabla.addTab("Crear Indice", jPanel4);

        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel9.setText("Indice a Borrar:");

        indicesCOMBOBOX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indicesCOMBOBOXActionPerformed(evt);
            }
        });

        confirmarBORINDEX.setText("Confirmar");
        confirmarBORINDEX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarBORINDEXActionPerformed(evt);
            }
        });

        borrarIndice.setText("Borrar Indice");
        borrarIndice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarIndiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(indicesCOMBOBOX, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borrarIndice)
                    .addComponent(confirmarBORINDEX))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(indicesCOMBOBOX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borrarIndice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmarBORINDEX)
                .addContainerGap(356, Short.MAX_VALUE))
        );

        crearTabla.addTab("Borrar Indice", jPanel5);

        ddlTA.setColumns(20);
        ddlTA.setRows(5);
        jScrollPane2.setViewportView(ddlTA);

        indicesTA.setColumns(20);
        indicesTA.setRows(5);
        jScrollPane3.setViewportView(indicesTA);

        confirmarButton.setText("Confirmar");
        confirmarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MainLayout = new javax.swing.GroupLayout(Main);
        Main.setLayout(MainLayout);
        MainLayout.setHorizontalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crearTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MainLayout.createSequentialGroup()
                        .addComponent(confirmarButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        MainLayout.setVerticalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(crearTabla, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmarButton))
                    .addGroup(MainLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tables", Main);

        triggerTablas.setColumns(20);
        triggerTablas.setRows(5);
        jScrollPane4.setViewportView(triggerTablas);

        jLabel10.setText("Nombre del trigger");

        jLabel11.setText("Despues de que?");

        triggerAFTERCOMB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Insert", "Update", "Delete" }));

        jLabel12.setText("Nombre de la tabla");

        triggerTablCOMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerTablCOMBActionPerformed(evt);
            }
        });

        jLabel13.setText("Nombre de la columna");

        triggerGDDL.setText("Generar DDL");
        triggerGDDL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerGDDLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(triggerNombre)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)
                        .addComponent(triggerAFTERCOMB, 0, 164, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addComponent(triggerTablCOMB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addComponent(triggerCOLUMNCOMB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(triggerGDDL))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(triggerNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(triggerAFTERCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(triggerTablCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(triggerCOLUMNCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(triggerGDDL)
                .addContainerGap(283, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Crear Trigger", jPanel6);

        triggerDDLTA.setColumns(20);
        triggerDDLTA.setRows(5);
        jScrollPane5.setViewportView(triggerDDLTA);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                .addGap(356, 356, 356)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Triggers", jPanel2);

        jLabel14.setText("Nombre del view:");

        crearView.setText("Crear View");
        crearView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearViewActionPerformed(evt);
            }
        });

        confirmarCreateView.setText("Confirmar");
        confirmarCreateView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarCreateViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(viewNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(crearView)
                    .addComponent(confirmarCreateView))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(crearView)
                .addGap(18, 18, 18)
                .addComponent(confirmarCreateView)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Crear View", jPanel8);

        jLabel15.setText("View:");

        viewBORRARCONFIRM.setText("Borrar Trigger");
        viewBORRARCONFIRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBORRARCONFIRMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewBORRARCONFIRM)
                    .addComponent(jLabel15)
                    .addComponent(viewCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(viewBORRARCONFIRM)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Borrar View", jPanel9);

        jLabel16.setText("View");

        modificarView.setText("Modificar");
        modificarView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarViewActionPerformed(evt);
            }
        });

        confirmarViewMod.setText("Confirmar");
        confirmarViewMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarViewModActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirmarViewMod)
                    .addComponent(modificarView)
                    .addComponent(jLabel16)
                    .addComponent(viewMODCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewMODCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modificarView)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmarViewMod)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Modificar View", jPanel10);

        viewDDLTA.setColumns(20);
        viewDDLTA.setRows(5);
        jScrollPane6.setViewportView(viewDDLTA);

        viewsListTA.setColumns(20);
        viewsListTA.setRows(5);
        jScrollPane7.setViewportView(viewsListTA);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(442, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))))
                .addContainerGap(330, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Views", jPanel7);

        eliminarUsuario.setText("Eliminar");
        eliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(usersCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(eliminarUsuario)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(usersCOMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(eliminarUsuario)
                .addContainerGap(232, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Borrar User", jPanel12);

        userEditU.setText("Editar username");
        userEditU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userEditUActionPerformed(evt);
            }
        });

        userEditP.setText("Editar password");
        userEditP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userEditPActionPerformed(evt);
            }
        });

        nUserLabel.setText("Nuevo Username:");

        nUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nUserActionPerformed(evt);
            }
        });

        nPassLabel1.setText("Nuevo Password:");

        editarUser.setText("Editar");
        editarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editarUser)
                    .addComponent(nPass, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nPassLabel1)
                    .addComponent(nUserLabel)
                    .addComponent(userEditP)
                    .addComponent(nUser, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userEditU))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(userEditU)
                .addGap(18, 18, 18)
                .addComponent(userEditP)
                .addGap(29, 29, 29)
                .addComponent(nUserLabel)
                .addGap(18, 18, 18)
                .addComponent(nUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nPassLabel1)
                .addGap(18, 18, 18)
                .addComponent(nPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(editarUser)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Editar User", jPanel13);

        usersTA.setColumns(20);
        usersTA.setRows(5);
        jScrollPane8.setViewportView(usersTA);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(910, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(447, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Users", jPanel11);

        jButton1.setText("Desconectar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(7, 7, 7)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crearTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crearTablaMouseClicked

    }//GEN-LAST:event_crearTablaMouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked

    }//GEN-LAST:event_jPanel4MouseClicked

    private void tablasCOLUMNCOMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tablasCOLUMNCOMBActionPerformed
        
    }//GEN-LAST:event_tablasCOLUMNCOMBActionPerformed

    private void crearIndiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearIndiceActionPerformed
        
        this.ddlTA.setText("CREATE INDEX "+this.nomIndex.getText()+" ON "+this.tablasCombo.getSelectedItem().toString()+ "("+this.tablasCOLUMNCOMB.getSelectedItem().toString()+");");
        this.confirmarButton.setVisible(true);
        
    }//GEN-LAST:event_crearIndiceActionPerformed

    private void tablasComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tablasComboActionPerformed
        this.tablasCOLUMNCOMB.removeAllItems();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String q = "SELECT * FROM "+this.tablasCombo.getSelectedItem().toString()+";";
            System.out.println("TABLAS COMBO"+q);
            ResultSet rs =  st.executeQuery(q);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            String columnNames[] = new String[columns];
            for (int i = 0; i < columnNames.length; i++)
            columnNames[i] = rsmd.getColumnName(i+1);
            for (int i = 0; i < columnNames.length; i++)
            this.tablasCOLUMNCOMB.addItem(columnNames[i]);

            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tablasComboActionPerformed

    private void dropButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropButtonActionPerformed
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement();

            boolean rs = st.execute("DROP TABLE "+this.dropName.getText()+";");
            if (!rs)
            JOptionPane.showMessageDialog(null, "Tabla borrada exitosamente!!");
            else
            JOptionPane.showMessageDialog(null, "ALERTA", "La tabla no existe!!", JOptionPane.WARNING_MESSAGE);
            this.dropName.setText("");
            this.initTablasTA();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dropButtonActionPerformed

    private void crearTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearTActionPerformed
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement();

            boolean rs = st.execute(this.crearTSQL+"\n);");
            if (!rs) {
                JOptionPane.showMessageDialog(null, "La tabla ha sido creada exitosamente");
                this.initTablasTA();
                this.createComenzado = false;
                this.tiposDeDato.clearSelection();
                this.nombreCol.setText("");
                this.nombreTabla.setText("");
                this.ddlTA.setText("");
            }else{
                JOptionPane.showMessageDialog(null, "ALERTA", "Por favor revise la sentencia SQL . . .", JOptionPane.WARNING_MESSAGE);
            }
            this.nombreTabla.setEditable(true);
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.initTablasComboBox();
        this.listarIndices();
        
    }//GEN-LAST:event_crearTActionPerformed

    private void insertarColumnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarColumnaActionPerformed

        if(!createComenzado){
            this.crearTSQL = "CREATE TABLE "+this.nombreTabla.getText()+"(\n";

            if (intRB.isSelected()){
                this.crearTSQL += "\t"+this.nombreCol.getText()+" \t"+"INT";
                if(this.pkCHECKBOX.isSelected())
                    this.crearTSQL += " CONSTRAINT "+this.nombreCol.getText()+"_pk PRIMARY KEY";
            }
            else if(doubleRB.isSelected()){
                int value = (Integer) this.tamanioSpinner.getValue();
                System.out.println(value);
                String precis = (value > 0) ? "("+value+")" : "" ;
                this.crearTSQL += "\t"+this.nombreCol.getText()+" \t"+"DOUBLE"+precis;
                if(this.pkCHECKBOX.isSelected())
                    this.crearTSQL += " CONSTRAINT "+this.nombreCol.getText()+"_pk PRIMARY KEY";
            }
            else if(charRB.isSelected()){
                this.crearTSQL += "\t"+this.nombreCol.getText()+" \t"+"CHAR";
                if(this.pkCHECKBOX.isSelected())
                    this.crearTSQL += " CONSTRAINT "+this.nombreCol.getText()+"_pk PRIMARY KEY";
            }
            else if(varcharRB.isSelected()){
                int value = (Integer) this.tamanioSpinner.getValue();
                String t = (value > 0) ? "("+value+")" : "";
                this.crearTSQL += "\t"+this.nombreCol.getText()+" \t"+"VARCHAR"+t;
                if(this.pkCHECKBOX.isSelected())
                    this.crearTSQL += " CONSTRAINT "+this.nombreCol.getText()+"_pk PRIMARY KEY";
            }
            this.nombreCol.setText("");
            tiposDeDato.clearSelection();
            this.createComenzado = true;
            this.nombreTabla.setEditable(false);
            this.tamanioSpinner.setValue(0);
            this.tamanioLabel.setVisible(false);
            this.tamanioSpinner.setVisible(false);

        }else{
            if (intRB.isSelected()){
                this.crearTSQL += ",\n\t"+this.nombreCol.getText()+" \t"+"INT";
                if(this.pkCHECKBOX.isSelected())
                    this.crearTSQL += " CONSTRAINT "+this.nombreCol.getText()+"_pk PRIMARY KEY";
            }
            else if(doubleRB.isSelected()){
                int value = (Integer) this.tamanioSpinner.getValue();
                System.out.println(value);
                String precis = (value > 0) ? "("+value+")" : "" ;
                this.crearTSQL += ",\n\t"+this.nombreCol.getText()+" \t"+"DOUBLE"+precis;
                if(this.pkCHECKBOX.isSelected())
                    this.crearTSQL += " CONSTRAINT "+this.nombreCol.getText()+"_pk PRIMARY KEY";
            }
            else if(charRB.isSelected()){
                this.crearTSQL += ",\n\t"+this.nombreCol.getText()+" \t"+"CHAR";
                if(this.pkCHECKBOX.isSelected())
                    this.crearTSQL += " CONSTRAINT "+this.nombreCol.getText()+"_pk PRIMARY KEY";
            }
            else if(varcharRB.isSelected()){
                int value = (Integer) this.tamanioSpinner.getValue();
                String t = (value > 0) ? "("+value+")" : "";
                this.crearTSQL += ",\n\t"+this.nombreCol.getText()+" \t"+"VARCHAR"+t;
                if(this.pkCHECKBOX.isSelected())
                    this.crearTSQL += " CONSTRAINT "+this.nombreCol.getText()+"_pk PRIMARY KEY";
            }
            this.nombreCol.setText("");
            tiposDeDato.clearSelection();
            this.tamanioSpinner.setValue(0);
            this.tamanioLabel.setVisible(false);
            this.tamanioSpinner.setVisible(false);
        }
        this.pkCHECKBOX.setSelected(false);
        ddlTA.setText(this.crearTSQL+"\n);");

    }//GEN-LAST:event_insertarColumnaActionPerformed

    private void doubleRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doubleRBActionPerformed

        this.tamanioLabel.setText("Precision:");
        SpinnerModel sm = new SpinnerNumberModel(0, 0, 64, 1);
        this.tamanioSpinner.setModel(sm);

        this.tamanioLabel.setVisible(true);
        this.tamanioSpinner.setVisible(true);
    }//GEN-LAST:event_doubleRBActionPerformed

    private void charRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charRBActionPerformed
        this.tamanioLabel.setVisible(false);
        this.tamanioSpinner.setVisible(false);
    }//GEN-LAST:event_charRBActionPerformed

    private void varcharRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varcharRBActionPerformed

        this.tamanioLabel.setText("Tamaño:");
        SpinnerModel sm = new SpinnerNumberModel();
        this.tamanioSpinner.setModel(sm);
        Dimension d = tamanioSpinner.getPreferredSize();
        d.width = 100;
        tamanioSpinner.setPreferredSize(d);

        this.tamanioLabel.setVisible(true);
        this.tamanioSpinner.setVisible(true);
    }//GEN-LAST:event_varcharRBActionPerformed

    private void intRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intRBActionPerformed
        this.tamanioLabel.setVisible(false);
        this.tamanioSpinner.setVisible(false);
    }//GEN-LAST:event_intRBActionPerformed

    private void fkCHECKBOXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fkCHECKBOXActionPerformed
        this.pkCHECKBOX.setSelected(false);
        this.initFKTablas();
        
    }//GEN-LAST:event_fkCHECKBOXActionPerformed

    private void pkCHECKBOXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pkCHECKBOXActionPerformed
        this.fkCHECKBOX.setSelected(false);
    }//GEN-LAST:event_pkCHECKBOXActionPerformed

    private void fkTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fkTableActionPerformed
        this.fkColumn.removeAllItems();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String q = "SELECT * FROM "+this.fkTable.getSelectedItem().toString()+";";
            System.out.println("TABLAS COMBO"+q);
            ResultSet rs =  st.executeQuery(q);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            String columnNames[] = new String[columns];
            for (int i = 0; i < columnNames.length; i++)
            columnNames[i] = rsmd.getColumnName(i+1);
            for (int i = 0; i < columnNames.length; i++)
            this.fkColumn.addItem(columnNames[i]);

            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_fkTableActionPerformed

    private void confirmarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarButtonActionPerformed
        
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            boolean rs = st.execute("CREATE INDEX "+this.nomIndex.getText()+" ON "+this.tablasCombo.getSelectedItem().toString()+ "("+this.tablasCOLUMNCOMB.getSelectedItem().toString()+");");
            
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ALERTA", "Ha habido un error en el sql, revise el ddl por favor . . .", JOptionPane.WARNING_MESSAGE);
        }
        this.confirmarButton.setVisible(false);
        this.listarIndices();
        this.ddlTA.setText("");
    }//GEN-LAST:event_confirmarButtonActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5MouseClicked

    private void indicesCOMBOBOXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indicesCOMBOBOXActionPerformed
        
        
        
    }//GEN-LAST:event_indicesCOMBOBOXActionPerformed

    private void borrarIndiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarIndiceActionPerformed
        
        this.confirmarBORINDEX.setVisible(true);
        this.ddlTA.setText("DROP INDEX "+this.indicesCOMBOBOX.getSelectedItem().toString()+";");
        
    }//GEN-LAST:event_borrarIndiceActionPerformed

    private void confirmarBORINDEXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarBORINDEXActionPerformed
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement();
            st.execute("DROP INDEX "+this.indicesCOMBOBOX.getSelectedItem().toString()+";");
            JOptionPane.showMessageDialog(null, "Indice borrado exitosamente!!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.initIndicesComboBox();
        this.listarIndices();
        this.ddlTA.setText("");
    }//GEN-LAST:event_confirmarBORINDEXActionPerformed

    private void triggerTablCOMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerTablCOMBActionPerformed
        this.triggerCOLUMNCOMB.removeAllItems();
        this.triggerCOLUMNCOMB.addItem("EACH ROW");
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String q = "SELECT * FROM "+this.triggerTablCOMB.getSelectedItem().toString()+";";
            System.out.println("TRIGGER TABLAS COMBO"+q);
            ResultSet rs =  st.executeQuery(q);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            String columnNames[] = new String[columns];
            for (int i = 0; i < columnNames.length; i++)
                columnNames[i] = rsmd.getColumnName(i+1);
            for (int i = 0; i < columnNames.length; i++)
                this.triggerCOLUMNCOMB.addItem(columnNames[i]);

            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_triggerTablCOMBActionPerformed

    private void triggerGDDLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerGDDLActionPerformed
        
        this.triggerDDLTA.setText("CREATE TRIGGER "+this.triggerNombre.getText()+" \nAFTER "+this.triggerAFTERCOMB.getSelectedItem().toString().toUpperCase()+" ON "+
                this.triggerTablCOMB.getSelectedItem()+" FOR "+this.triggerCOLUMNCOMB.getSelectedItem().toString());
        
    }//GEN-LAST:event_triggerGDDLActionPerformed

    private void crearViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearViewActionPerformed
        this.viewDDLTA.setText("CREATE VIEW "+this.viewNombre.getText()+"\nAS\n\n\n;");
        this.confirmarCreateView.setVisible(true);
    }//GEN-LAST:event_crearViewActionPerformed

    private void confirmarCreateViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarCreateViewActionPerformed
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement();
            boolean rs = st.execute(this.viewDDLTA.getText());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.viewDDLTA.setText("");
        this.viewNombre.setText("");
        this.initViewsList();
    }//GEN-LAST:event_confirmarCreateViewActionPerformed

    private void viewBORRARCONFIRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBORRARCONFIRMActionPerformed
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement();
            st.execute("DROP VIEW "+this.viewCOMB.getSelectedItem().toString()+";");
            JOptionPane.showMessageDialog(null, "View borrada exitosamente!!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.confirmarCreateView.setVisible(false);
        this.initIndicesComboBox();
        this.listarIndices();
        this.ddlTA.setText("");
        this.initViewsList();
        this.initTablasTA();
    }//GEN-LAST:event_viewBORRARCONFIRMActionPerformed

    private void modificarViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarViewActionPerformed
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs =  st.executeQuery("SELECT VIEW_DEFINITION FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = '"+this.viewMODCOMB.getSelectedItem().toString()+"';");
            System.out.println(rs);
            if(rs.next()){
                System.out.println("entre");
                this.viewDDLTA.setText(rs.getString("VIEW_DEFINITION"));
            }
            System.out.println("NO");
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto1_TBD2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.confirmarViewMod.setVisible(true);
    }//GEN-LAST:event_modificarViewActionPerformed

    private void confirmarViewModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarViewModActionPerformed
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement();
            st.execute("DROP VIEW "+this.viewCOMB.getSelectedItem().toString()+";");
            st.execute(this.viewDDLTA.getText());
            JOptionPane.showMessageDialog(null, "View borrada exitosamente!!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_confirmarViewModActionPerformed

    private void eliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuarioActionPerformed
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.pass);
            Statement st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("SELECT COUNT(NAME) FROM INFORMATION_SCHEMA.USERS;");
            rs.next();
            if (rs.getInt("COUNT(NAME)") == 1) {
                JOptionPane.showMessageDialog(null, "ALERT", "La base de datos necesita tener usuarios!!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            st.executeUpdate("DROP USER "+this.usersCOMB.getSelectedItem().toString()+";");
            JOptionPane.showMessageDialog(rootPane, "El usuario se ha borrado exitosamente!!");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.initUsersCombo();
        this.initUsersTA();
    }//GEN-LAST:event_eliminarUsuarioActionPerformed

    private void userEditUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userEditUActionPerformed
        this.nUserLabel.setVisible(true);
        this.nUser.setVisible(true);
    }//GEN-LAST:event_userEditUActionPerformed

    private void nUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nUserActionPerformed

    private void userEditPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userEditPActionPerformed
        this.nPassLabel1.setVisible(true);
        this.nPass.setVisible(true);
    }//GEN-LAST:event_userEditPActionPerformed

    private void editarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarUserActionPerformed
        if(this.userEditP.isSelected() && this.userEditU.isSelected()){
            this.editUser(this.username, this.nUser.getText(), this.nPass.getText(), true, false);
        }
        else if (this.userEditU.isSelected()){
            this.editUser(this.username, this.nUser.getText(), this.pass, false, true);
        }else if(this.userEditP.isSelected()){
            this.editUser(this.username, this.nUser.getText(), this.nPass.getText(), true, false);
        }
        Login l = new Login();
            l.setVisible(true);
            this.dispose();
    }//GEN-LAST:event_editarUserActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Login l = new Login();
        this.dispose();
        l.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Main;
    private javax.swing.JButton borrarIndice;
    private javax.swing.JRadioButton charRB;
    private javax.swing.JButton confirmarBORINDEX;
    private javax.swing.JButton confirmarButton;
    private javax.swing.JButton confirmarCreateView;
    private javax.swing.JButton confirmarViewMod;
    private javax.swing.JButton crearIndice;
    private javax.swing.JButton crearT;
    private javax.swing.JTabbedPane crearTabla;
    private javax.swing.JButton crearView;
    private javax.swing.JTextArea ddlTA;
    private javax.swing.JRadioButton doubleRB;
    private javax.swing.JButton dropButton;
    private javax.swing.JTextField dropName;
    private javax.swing.JButton editarUser;
    private javax.swing.JButton eliminarUsuario;
    private javax.swing.JCheckBox fkCHECKBOX;
    private javax.swing.JComboBox<String> fkColumn;
    private javax.swing.JComboBox<String> fkTable;
    private javax.swing.JComboBox<String> indicesCOMBOBOX;
    private javax.swing.JTextArea indicesTA;
    private javax.swing.JButton insertarColumna;
    private javax.swing.JRadioButton intRB;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JButton modificarView;
    private javax.swing.JTextField nPass;
    private javax.swing.JLabel nPassLabel1;
    private javax.swing.JTextField nUser;
    private javax.swing.JLabel nUserLabel;
    private javax.swing.JTextField nomIndex;
    private javax.swing.JTextField nombreCol;
    private javax.swing.JTextField nombreTabla;
    private javax.swing.JCheckBox pkCHECKBOX;
    private javax.swing.JTextArea tablas;
    private javax.swing.JComboBox<String> tablasCOLUMNCOMB;
    private javax.swing.JComboBox<String> tablasCombo;
    private javax.swing.JLabel tamanioLabel;
    private javax.swing.JSpinner tamanioSpinner;
    private javax.swing.ButtonGroup tiposDeDato;
    private javax.swing.JComboBox<String> triggerAFTERCOMB;
    private javax.swing.JComboBox<String> triggerCOLUMNCOMB;
    private javax.swing.JTextArea triggerDDLTA;
    private javax.swing.JButton triggerGDDL;
    private javax.swing.JTextField triggerNombre;
    private javax.swing.JComboBox<String> triggerTablCOMB;
    private javax.swing.JTextArea triggerTablas;
    private javax.swing.JCheckBox userEditP;
    private javax.swing.JCheckBox userEditU;
    private javax.swing.JComboBox<String> usersCOMB;
    private javax.swing.JTextArea usersTA;
    private javax.swing.JRadioButton varcharRB;
    private javax.swing.JButton viewBORRARCONFIRM;
    private javax.swing.JComboBox<String> viewCOMB;
    private javax.swing.JTextArea viewDDLTA;
    private javax.swing.JComboBox<String> viewMODCOMB;
    private javax.swing.JTextField viewNombre;
    private javax.swing.JTextArea viewsListTA;
    // End of variables declaration//GEN-END:variables
}
