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
public class PerbaruiSkripsi extends javax.swing.JPanel {
    
    Connection conn = null;
    
    public void peringatan(String pesan) {
        Component rootPane = null;
        JOptionPane.showMessageDialog(rootPane, pesan);
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Akhir", "postgres", "uinsa");
        } catch (SQLException ex) {
            Logger.getLogger(PerbaruiSkripsi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ArrayList<Test> dataTest;

    private int masukkanData(Connection conn, String judul, String pengarang, String tahun, String halaman) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("INSERT INTO SKRIPSI (judul,pengarang,tahun,halaman) VALUES(?,?,?,?)");
        pst.setString(1, judul);
        pst.setString(2, pengarang);
        pst.setString(3, tahun);
        pst.setString(4, halaman);
        return pst.executeUpdate();
    }
    
    private int editData(Connection conn, String judul, String pengarang, String tahun, String halaman) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("UPDATE SKRIPSI set pengarang = ?,tahun = ?,halaman = ? where judul = ? ");
        pst.setString(4, judul);
        pst.setString(1, pengarang);
        pst.setString(2, tahun);
        pst.setString(3, halaman);
        return pst.executeUpdate();
    }
    
    private int hapusData(Connection conn, String judul) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("DELETE From SKRIPSI where judul = ? ");
        pst.setString(1, judul);
        return pst.executeUpdate();
    }
    
    private void cariData(){
        try {
            String sql = "";
            if(jComboBoxSearch.getSelectedIndex()==0){
                sql = "SELECT * from skripsi WHERE judul ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==1){
                sql = "SELECT * from skripsi WHERE tahun ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==2){
                sql = "SELECT * from skripsi WHERE pengarang ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }else if(jComboBoxSearch.getSelectedIndex()==3){
                sql = "SELECT * from skripsi WHERE halaman ILIKE '%"+jTextFieldSearch.getText()+"%' ";
            }
            
            Statement rt = conn.createStatement();
            ResultSet st = rt.executeQuery(sql);
            DefaultTableModel tb = (DefaultTableModel) jTableSkripsi.getModel();
            tb.setRowCount(0);
            while(st.next()){
                Object[] ob = new Object[4];
                ob[0] = st.getString("judul");
                ob[1] = st.getString("tahun");
                ob[2] = st.getString("pengarang");
                ob[3] = st.getString("halaman");
                tb.addRow(ob);
                
            }
            jTableSkripsi.setModel(tb);
        } catch (SQLException ex) {
            Logger.getLogger(PerbaruiSkripsi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void Tampil(Connection conn) {
        dataTest.clear();
        try {
            String sql = "select * from skripsi";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Test data = new Test();
                data.setJudul(String.valueOf(rs.getObject(1)));
                data.setPengarang(String.valueOf(rs.getObject(2)));
                data.setTahun(String.valueOf(rs.getObject(3)));
                data.setHalaman(String.valueOf(rs.getObject(4)));
                dataTest.add(data);
            }
            DefaultTableModel model = (DefaultTableModel) jTableSkripsi.getModel();
            model.setRowCount(0);
            for (Test data : dataTest) {

                Object[] baris = new Object[4];
                baris[0] = data.getJudul();
                baris[1] = data.getPengarang();
                baris[2] = data.getTahun();
                baris[3] = data.getHalaman();

                model.addRow(baris);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerbaruiSkripsi.class.getName()).log(Level.SEVERE, null, ex);
        }
        SkripsiJudul.setText("");
        SkripsiPengarang.setText("");
        SkripsiTahun.setText("");
        SkripsiHalaman.setText("");
    }
    
    /**
     * Creates new form PerbaruiSkripsi
     */
    public PerbaruiSkripsi() {
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
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        SkripsiJudul = new javax.swing.JTextField();
        SkripsiPengarang = new javax.swing.JTextField();
        SkripsiTahun = new javax.swing.JTextField();
        SkripsiHalaman = new javax.swing.JTextField();
        SkripsiDelete = new javax.swing.JButton();
        SkripsiEdit = new javax.swing.JButton();
        SkripsiInsert = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSkripsi = new javax.swing.JTable();
        jTextFieldSearch = new javax.swing.JTextField();
        jComboBoxSearch = new javax.swing.JComboBox<>();
        jButtonCari = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(153, 102, 0));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("Perbarui Skripsi");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel2.setText("judul");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel3.setText("pengarang");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel4.setText("tahun");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel5.setText("halaman");

        SkripsiDelete.setText("Delete");
        SkripsiDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkripsiDeleteActionPerformed(evt);
            }
        });

        SkripsiEdit.setText("Edit");
        SkripsiEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkripsiEditActionPerformed(evt);
            }
        });

        SkripsiInsert.setText("Insert");
        SkripsiInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SkripsiInsertActionPerformed(evt);
            }
        });

        jTableSkripsi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Judul", "Pengarang", "Tahun", "Halaman"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSkripsi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSkripsiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSkripsi);

        jTextFieldSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextFieldSearchMouseReleased(evt);
            }
        });

        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "judul", "pengarang", "tahun", "halaman" }));

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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldSearch)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(SkripsiInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(SkripsiEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(SkripsiDelete)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButtonCari, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(SkripsiJudul, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(SkripsiPengarang)
                                    .addComponent(SkripsiTahun)
                                    .addComponent(SkripsiHalaman))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(20, 20, 20)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(SkripsiJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(SkripsiPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(SkripsiTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(SkripsiHalaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonCari))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SkripsiInsert)
                            .addComponent(SkripsiEdit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SkripsiDelete))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void SkripsiInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkripsiInsertActionPerformed
      
        String judul = SkripsiJudul.getText();
        String pengarang = SkripsiPengarang.getText();
        String tahun = SkripsiTahun.getText();
        String halaman = SkripsiHalaman.getText();
        //
        EntityManager entityManager;
        entityManager = Persistence.createEntityManagerFactory("apkPU").createEntityManager();
        entityManager.getTransaction().begin();
        Skripsi m = new Skripsi();
        m.setJudul(judul);
        m.setPengarang(pengarang);
        m.setTahun(tahun);
        m.setHalaman(halaman);
        entityManager.persist(m);
        entityManager.getTransaction().commit();
        //
            Tampil(conn);
    }//GEN-LAST:event_SkripsiInsertActionPerformed

    private void jTableSkripsiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSkripsiMouseClicked
        int row = jTableSkripsi.getSelectedRow();
        SkripsiJudul.setText(jTableSkripsi.getValueAt(row, 0).toString());
        SkripsiPengarang.setText(jTableSkripsi.getValueAt(row, 1).toString());
        SkripsiTahun.setText(jTableSkripsi.getValueAt(row, 2).toString());
        SkripsiHalaman.setText(jTableSkripsi.getValueAt(row, 3).toString());
    }//GEN-LAST:event_jTableSkripsiMouseClicked

    private void SkripsiEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkripsiEditActionPerformed
       
        String judul = SkripsiJudul.getText();
        String pengarang = SkripsiPengarang.getText();
        String tahun = SkripsiTahun.getText();
        String halaman = SkripsiHalaman.getText();
        //
        EntityManager em;
        em = Persistence.createEntityManagerFactory("apkPU").createEntityManager();
        em.getTransaction().begin();
        Skripsi m = em.find(Skripsi.class, judul);
        m.setJudul(judul);
        m.setPengarang(pengarang);
        m.setTahun(tahun);
        m.setHalaman(halaman);
        em.persist(m);
        em.getTransaction().commit();
        //
            Tampil(conn);
    }//GEN-LAST:event_SkripsiEditActionPerformed

    private void SkripsiDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SkripsiDeleteActionPerformed
        String judul = SkripsiJudul.getText().trim();
        //awal persistence
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("apkPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Skripsi s = em.find(Skripsi.class, judul);
        em.remove(s);
        em.getTransaction().commit();
        // akhir persistence
            Tampil(conn);
    }//GEN-LAST:event_SkripsiDeleteActionPerformed

    private void jTextFieldSearchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldSearchMouseReleased
        
    }//GEN-LAST:event_jTextFieldSearchMouseReleased

    private void jButtonCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCariActionPerformed
       
        cariData();
    
    }//GEN-LAST:event_jButtonCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SkripsiDelete;
    private javax.swing.JButton SkripsiEdit;
    private javax.swing.JTextField SkripsiHalaman;
    private javax.swing.JButton SkripsiInsert;
    private javax.swing.JTextField SkripsiJudul;
    private javax.swing.JTextField SkripsiPengarang;
    private javax.swing.JTextField SkripsiTahun;
    private javax.swing.JButton jButtonCari;
    private javax.swing.JComboBox<String> jComboBoxSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableSkripsi;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables

}
