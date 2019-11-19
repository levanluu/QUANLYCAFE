package edu.poly.views;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import static edu.poly.views.MainJFrame.tblQuanLyDoUong;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class UpdateProduct extends javax.swing.JFrame {

    private Connection conn;
    
    public UpdateProduct() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PROJECT_JAVA;", "java", "java");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Phanv\\"
                + "OneDrive\\Documents\\NetBeansProjects\\QuanLyCafe\\src\\edu\\poly\\image\\logo_title.png"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
      //  int index = tblQuanLyDoUong.getSelectedRow(); 


       //loadclicktable(index);
         
    }
   
  // public void loadclicktable(int index){
         
    //    txtTenMon.setText((String) (MainJFrame.tblQuanLyDoUong.getValueAt(index, 1)));
      //  txtDanhMuc.setText("2");
        //txtDonGia.setText(MainJFrame.tblQuanLyDoUong.getValueAt(index, 2).toString());
        
   //}
     public void capnhat(){
        if (txtTenMon.getText().equals("")||txtDonGia.getText().equals("")) {
           JOptionPane.showMessageDialog(this,"không được để trống ");
        }else{
            try {
                String tenduong=txtiddouong.getText();
                String sql="UPDATE DOUONG SET TENDOUONG=?,DONGIA=? WHERE IDDOUONG='"+txtiddouong.getText()+"'";
                 PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtTenMon.getText());
             ps.setString(2, txtDonGia.getText());
             ps.executeUpdate();
             JOptionPane.showMessageDialog(this,"cập nhật thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.dispose();
          
          
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panBackgoundThemMon = new javax.swing.JPanel();
        lblTitleThemMon = new javax.swing.JLabel();
        txtTenMon = new javax.swing.JTextField();
        sptTenMon = new javax.swing.JSeparator();
        lblTenMon = new javax.swing.JLabel();
        lblDanhMuc = new javax.swing.JLabel();
        txtDanhMuc = new javax.swing.JTextField();
        sptDanhMuc = new javax.swing.JSeparator();
        lblDonGia = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        sptDonGia = new javax.swing.JSeparator();
        btnLuuMon = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtiddouong = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sửa món");

        panBackgoundThemMon.setBackground(new java.awt.Color(255, 255, 255));

        lblTitleThemMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannersSuaMon.jpg"))); // NOI18N

        txtTenMon.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTenMon.setForeground(new java.awt.Color(23, 35, 51));
        txtTenMon.setBorder(null);

        sptTenMon.setBackground(new java.awt.Color(23, 35, 51));

        lblTenMon.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblTenMon.setForeground(new java.awt.Color(23, 35, 51));
        lblTenMon.setText("Tên món:");

        lblDanhMuc.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblDanhMuc.setForeground(new java.awt.Color(23, 35, 51));
        lblDanhMuc.setText("Danh mục:");

        txtDanhMuc.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtDanhMuc.setForeground(new java.awt.Color(23, 35, 51));
        txtDanhMuc.setBorder(null);

        sptDanhMuc.setBackground(new java.awt.Color(23, 35, 51));

        lblDonGia.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblDonGia.setForeground(new java.awt.Color(23, 35, 51));
        lblDonGia.setText("Đơn giá:");

        txtDonGia.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtDonGia.setForeground(new java.awt.Color(23, 35, 51));
        txtDonGia.setBorder(null);

        sptDonGia.setBackground(new java.awt.Color(23, 35, 51));

        btnLuuMon.setBackground(new java.awt.Color(23, 35, 51));
        btnLuuMon.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnLuuMon.setForeground(new java.awt.Color(255, 255, 255));
        btnLuuMon.setText("Lưu");
        btnLuuMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuMonActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(23, 35, 51));
        btnLamMoi.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        jLabel1.setText("ID ĐỒ UỐNG");

        javax.swing.GroupLayout panBackgoundThemMonLayout = new javax.swing.GroupLayout(panBackgoundThemMon);
        panBackgoundThemMon.setLayout(panBackgoundThemMonLayout);
        panBackgoundThemMonLayout.setHorizontalGroup(
            panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitleThemMon)
            .addGroup(panBackgoundThemMonLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panBackgoundThemMonLayout.createSequentialGroup()
                        .addComponent(lblDonGia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panBackgoundThemMonLayout.createSequentialGroup()
                                .addComponent(btnLuuMon, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(sptDonGia)
                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panBackgoundThemMonLayout.createSequentialGroup()
                        .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDanhMuc)
                            .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(lblTenMon)))
                        .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panBackgoundThemMonLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(sptDanhMuc)
                                        .addComponent(txtDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(sptTenMon)
                                        .addComponent(txtTenMon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panBackgoundThemMonLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtiddouong, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        panBackgoundThemMonLayout.setVerticalGroup(
            panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBackgoundThemMonLayout.createSequentialGroup()
                .addComponent(lblTitleThemMon, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTenMon)
                    .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sptTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtiddouong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDanhMuc))
                .addGap(2, 2, 2)
                .addComponent(sptDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDonGia))
                .addGap(2, 2, 2)
                .addComponent(sptDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(panBackgoundThemMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuuMon, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBackgoundThemMon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBackgoundThemMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        txtTenMon.setText("");
        txtDanhMuc.setText("");
        txtDonGia.setText("");
        txtTenMon.requestFocus();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnLuuMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuMonActionPerformed
        // TODO add your handling code here:
        capnhat();
    }//GEN-LAST:event_btnLuuMonActionPerformed

   
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdateProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //import JTattoo
        try {
            // select Look and Feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
            // start application
            new MainJFrame();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLuuMon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDanhMuc;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblTenMon;
    private javax.swing.JLabel lblTitleThemMon;
    private javax.swing.JPanel panBackgoundThemMon;
    private javax.swing.JSeparator sptDanhMuc;
    private javax.swing.JSeparator sptDonGia;
    private javax.swing.JSeparator sptTenMon;
    public static javax.swing.JTextField txtDanhMuc;
    public static javax.swing.JTextField txtDonGia;
    public static javax.swing.JTextField txtTenMon;
    public static javax.swing.JTextField txtiddouong;
    // End of variables declaration//GEN-END:variables
}
