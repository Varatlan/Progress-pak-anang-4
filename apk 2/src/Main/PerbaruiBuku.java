/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Main;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RIGISEFA
 */
public class PerbaruiBuku extends javax.swing.JPanel {

        Connection conn = null;
    
    public void peringatan(String pesan) {
        Component rootPane = null;
        JOptionPane.showMessageDialog(rootPane, pesan);
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Akhir", "postgres", "uinsa");
        } catch (SQLException ex) {
            Logger.getLogger(PerbaruiBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ArrayList<Test> dataTest;

    private int masukkanData(Connection conn, String isbn, String judul, String subjudul, String pengarang, String tahun, String halaman, String penerbit) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("INSERT INTO BUKU (isbn,judul,subjudul,pengarang,tahun,halaman,penerbit) VALUES(?,?,?,?,?,?,?)");
        pst.setString(1, isbn);
        pst.setString(2, judul);
        pst.setString(3, subjudul);
        pst.setString(4, pengarang);
        pst.setString(5, tahun);
        pst.setString(6, halaman);
        pst.setString(7, penerbit);
        return pst.executeUpdate();
    }
    
    private int editData(Connection conn, String isbn, String judul, String subjudul, String pengarang, String tahun, String halaman, String penerbit) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("UPDATE BUKU set judul = ?, subjudul = ?, pengarang = ?,tahun = ?,halaman = ?,penerbit = ? where isbn = ? ");
        pst.setString(7, isbn);
        pst.setString(1, judul);
        pst.setString(2, subjudul);
        pst.setString(3, pengarang);
        pst.setString(4, tahun);
        pst.setString(5, halaman);
        pst.setString(6, penerbit);
        return pst.executeUpdate();
    }
    
    private int hapusData(Connection conn, String isbn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("DELETE From BUKU where isbn = ? ");
        pst.setString(1, isbn);
        return pst.executeUpdate();
    }
    
    private void cariData(){
        try {
            String sql = "";
            if(jComboBoxSearch.getSelectedIndex()==0){
                sql = "SELECT * from buku WHERE isbn ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==1){
                sql = "SELECT * from buku WHERE judul ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==2){
                sql = "SELECT * from buku WHERE subjudul ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==3){
                sql = "SELECT * from buku WHERE pengarang ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==4){
                sql = "SELECT * from buku WHERE tahun ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==5){
                sql = "SELECT * from buku WHERE halaman ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==6){
                sql = "SELECT * from buku WHERE penerbit ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }
            
            Statement rt = conn.createStatement();
            ResultSet st = rt.executeQuery(sql);
            DefaultTableModel tb = (DefaultTableModel) jTableBuku.getModel();
            tb.setRowCount(0);
            while(st.next()){
                Object[] ob = new Object[7];
                ob[0] = st.getString("isbn");
                ob[1] = st.getString("judul");
                ob[2] = st.getString("subjudul");
                ob[3] = st.getString("pengarang");
                ob[4] = st.getString("tahun");
                ob[5] = st.getString("halaman");
                ob[6] = st.getString("penerbit");
                tb.addRow(ob);
                
            }
            jTableBuku.setModel(tb);
        } catch (SQLException ex) {
            Logger.getLogger(PerbaruiSkripsi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void Tampil(Connection conn) {
        dataTest.clear();
        try {
            String sql = "select * from buku";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Test data = new Test();
                data.setIsbn(String.valueOf(rs.getObject(1)));
                data.setJudul(String.valueOf(rs.getObject(2)));
                data.setSubjudul(String.valueOf(rs.getObject(3)));
                data.setPengarang(String.valueOf(rs.getObject(4)));
                data.setTahun(String.valueOf(rs.getObject(5)));
                data.setHalaman(String.valueOf(rs.getObject(6)));
                data.setPenerbit(String.valueOf(rs.getObject(7)));
                dataTest.add(data);
            }
            DefaultTableModel model = (DefaultTableModel) jTableBuku.getModel();
            model.setRowCount(0);
            for (Test data : dataTest) {

                Object[] baris = new Object[7];
                baris[0] = data.getIsbn();
                baris[1] = data.getJudul();
                baris[2] = data.getSubjudul();
                baris[3] = data.getPengarang();
                baris[4] = data.getTahun();
                baris[5] = data.getHalaman();
                baris[6] = data.getPenerbit();

                model.addRow(baris);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerbaruiBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        BukuIsbn.setText("");
        BukuJudul.setText("");
        BukuSubjudul.setText("");
        BukuPengarang.setText("");
        BukuTahun.setText("");
        BukuHalaman.setText("");
        BukuPenerbit.setText("");
    }
    
    /**
     * Creates new form PerbaruiBuku
     */
    public PerbaruiBuku() {
        try {
            dataTest = new ArrayList<>();
            initComponents();
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Akhir", "postgres", "uinsa");
            Tampil(conn);
        } catch (SQLException ex) {
            Logger.getLogger(BukuP.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BukuIsbn = new javax.swing.JTextField();
        BukuJudul = new javax.swing.JTextField();
        BukuSubjudul = new javax.swing.JTextField();
        BukuPengarang = new javax.swing.JTextField();
        BukuTahun = new javax.swing.JTextField();
        BukuHalaman = new javax.swing.JTextField();
        BukuPenerbit = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        InsertBuku = new javax.swing.JButton();
        EditBuku = new javax.swing.JButton();
        DeleteBuku = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBuku = new javax.swing.JTable();
        jTextFieldSearch = new javax.swing.JTextField();
        jComboBoxSearch = new javax.swing.JComboBox<>();
        jButtonCari = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(153, 102, 0));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("Perbarui Buku");

        BukuIsbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BukuIsbnActionPerformed(evt);
            }
        });

        BukuHalaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BukuHalamanActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel2.setText("isbn");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel3.setText("judul");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel4.setText("subjudul");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel5.setText("pengarang");

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel6.setText("tahun");

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel7.setText("halaman");

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel8.setText("penerbit");

        InsertBuku.setText("Insert");
        InsertBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertBukuActionPerformed(evt);
            }
        });

        EditBuku.setText("Edit");
        EditBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBukuActionPerformed(evt);
            }
        });

        DeleteBuku.setText("Delete");
        DeleteBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBukuActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jTableBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "isbn", "judul", "subjudul", "pengarang", "tahun", "halaman", "penerbit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableBuku);

        jTextFieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSearchActionPerformed(evt);
            }
        });

        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "isbn", "judul", "subjudul", "pengarang", "tahun", "halaman", "penerbit" }));
        jComboBoxSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSearchActionPerformed(evt);
            }
        });

        jButtonCari.setText("Cari");
        jButtonCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BukuIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BukuTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(BukuPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)
                                        .addGap(14, 14, 14))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(InsertBuku)
                                                .addGap(18, 18, 18)
                                                .addComponent(EditBuku)
                                                .addGap(18, 18, 18)
                                                .addComponent(DeleteBuku))
                                            .addComponent(BukuPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(BukuSubjudul)
                                        .addComponent(BukuJudul)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jButtonCari, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(BukuHalaman, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BukuIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(BukuJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(BukuTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BukuSubjudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(BukuPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(BukuPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BukuHalaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InsertBuku)
                    .addComponent(EditBuku)
                    .addComponent(DeleteBuku)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void InsertBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertBukuActionPerformed
        // TODO add your handling code here:Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Akhir", "postgres", "bajumuslim");
        String isbn = BukuIsbn.getText();
        String judul = BukuJudul.getText();
        String subjudul = BukuSubjudul.getText();
        String pengarang = BukuPengarang.getText();
        String tahun = BukuTahun.getText();
        String halaman = BukuHalaman.getText();
        String penerbit = BukuPenerbit.getText();
        //
        EntityManager entityManager;
        entityManager = Persistence.createEntityManagerFactory("apkPU").createEntityManager();
        entityManager.getTransaction().begin();
        Buku m = new Buku();
        m.setIsbn(isbn);
        m.setJudul(judul);
        m.setSubjudul(subjudul);
        m.setPengarang(pengarang);
        m.setTahun(tahun);
        m.setHalaman(halaman);
        m.setPenerbit(penerbit);
        entityManager.persist(m);
        entityManager.getTransaction().commit();
        //
            Tampil(conn);
    }//GEN-LAST:event_InsertBukuActionPerformed

    private void BukuIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BukuIsbnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BukuIsbnActionPerformed

    private void jTableBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBukuMouseClicked
        int row = jTableBuku.getSelectedRow();
        BukuIsbn.setText(jTableBuku.getValueAt(row, 0).toString());
        BukuJudul.setText(jTableBuku.getValueAt(row, 1).toString());
        BukuSubjudul.setText(jTableBuku.getValueAt(row, 2).toString());
        BukuPengarang.setText(jTableBuku.getValueAt(row, 3).toString());
        BukuTahun.setText(jTableBuku.getValueAt(row, 4).toString());
        BukuHalaman.setText(jTableBuku.getValueAt(row, 5).toString());
        BukuPenerbit.setText(jTableBuku.getValueAt(row, 6).toString());
    }//GEN-LAST:event_jTableBukuMouseClicked

    private void EditBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBukuActionPerformed
        String isbn = BukuIsbn.getText();
        String judul = BukuJudul.getText();
        String subjudul = BukuSubjudul.getText();
        String pengarang = BukuPengarang.getText();
        String tahun = BukuTahun.getText();
        String halaman = BukuHalaman.getText();
        String penerbit = BukuPenerbit.getText();
        //
        EntityManager em;
        em = Persistence.createEntityManagerFactory("apkPU").createEntityManager();
        em.getTransaction().begin();
        Buku m = em.find(Buku.class, isbn);
        m.setIsbn(isbn);
        m.setJudul(judul);
        m.setSubjudul(subjudul);
        m.setPengarang(pengarang);
        m.setTahun(tahun);
        m.setHalaman(halaman);
        m.setPenerbit(penerbit);
        em.persist(m);
        em.getTransaction().commit();
        //
            Tampil(conn);
    }//GEN-LAST:event_EditBukuActionPerformed

    private void DeleteBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBukuActionPerformed
        
        String isbn = BukuIsbn.getText().trim();
        //awal persistence
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("apkPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Buku s = em.find(Buku.class, isbn);
        em.remove(s);
        em.getTransaction().commit();
        // akhir persistence
            Tampil(conn);
    }//GEN-LAST:event_DeleteBukuActionPerformed

    private void BukuHalamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BukuHalamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BukuHalamanActionPerformed

    private void jComboBoxSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSearchActionPerformed

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

    private void jButtonCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCariActionPerformed
        cariData();
    }//GEN-LAST:event_jButtonCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BukuHalaman;
    private javax.swing.JTextField BukuIsbn;
    private javax.swing.JTextField BukuJudul;
    private javax.swing.JTextField BukuPenerbit;
    private javax.swing.JTextField BukuPengarang;
    private javax.swing.JTextField BukuSubjudul;
    private javax.swing.JTextField BukuTahun;
    private javax.swing.JButton DeleteBuku;
    private javax.swing.JButton EditBuku;
    private javax.swing.JButton InsertBuku;
    private javax.swing.JButton jButtonCari;
    private javax.swing.JComboBox<String> jComboBoxSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableBuku;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
