package edu.poly.views;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class LoginJFrame extends javax.swing.JFrame {

    public LoginJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Phanv\\"
                + "OneDrive\\Documents\\NetBeansProjects\\QuanLyCafe\\src\\edu\\poly\\image\\logo_title.png"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDangNhap = new javax.swing.JLabel();
        panImgLogin = new javax.swing.JPanel();
        lblImgLogin = new javax.swing.JLabel();
        panLoginForm = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        sptUsername = new javax.swing.JSeparator();
        lblPassword = new javax.swing.JLabel();
        sptPassword = new javax.swing.JSeparator();
        pwdPassword = new javax.swing.JPasswordField();
        chkShowPassword = new javax.swing.JCheckBox();
        lblLienhe = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        btnThoat = new java.awt.Button();
        btnDangNhap = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng nhập hệ thống");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDangNhap.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        lblDangNhap.setText("ĐĂNG NHẬP");
        getContentPane().add(lblDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, -1, -1));

        panImgLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblImgLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerLogin.jpg"))); // NOI18N
        panImgLogin.add(lblImgLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panImgLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panLoginForm.setBackground(new java.awt.Color(255, 255, 255));
        panLoginForm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsername.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(23, 35, 51));
        lblUsername.setText("Username");
        panLoginForm.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, -1));

        sptUsername.setBackground(new java.awt.Color(23, 35, 51));
        panLoginForm.add(sptUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 320, 10));

        lblPassword.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(23, 35, 51));
        lblPassword.setText("Password");
        panLoginForm.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, -1, -1));

        sptPassword.setBackground(new java.awt.Color(23, 35, 51));
        panLoginForm.add(sptPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 320, 10));

        pwdPassword.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        pwdPassword.setForeground(new java.awt.Color(23, 35, 51));
        pwdPassword.setText("123456");
        pwdPassword.setBorder(null);
        panLoginForm.add(pwdPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 118, 320, 30));

        chkShowPassword.setBackground(new java.awt.Color(255, 255, 255));
        chkShowPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chkShowPassword.setForeground(new java.awt.Color(23, 35, 51));
        chkShowPassword.setText("Hiển thị mật khẩu");
        chkShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowPasswordActionPerformed(evt);
            }
        });
        panLoginForm.add(chkShowPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, -1, -1));

        lblLienhe.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblLienhe.setForeground(new java.awt.Color(23, 35, 51));
        lblLienhe.setText("Liên hệ: 0123456789");
        panLoginForm.add(lblLienhe, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 326, -1, -1));

        txtUsername.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(23, 35, 51));
        txtUsername.setText("CONGTTTPD02658");
        txtUsername.setBorder(null);
        panLoginForm.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 48, 320, 30));

        btnThoat.setBackground(new java.awt.Color(23, 35, 51));
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnThoat.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setLabel("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        panLoginForm.add(btnThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 110, 40));

        btnDangNhap.setBackground(new java.awt.Color(23, 35, 51));
        btnDangNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDangNhap.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setLabel("Đăng nhập");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });
        panLoginForm.add(btnDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 110, 40));

        getContentPane().add(panLoginForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 670, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowPasswordActionPerformed
        if (chkShowPassword.isSelected()) {
            pwdPassword.setEchoChar((char) 0);
        } else {
            pwdPassword.setEchoChar('*');
        }
    }//GEN-LAST:event_chkShowPasswordActionPerformed

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        if (txtUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được để trống Username");
        } else if (pwdPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được để trống Password");

        } else {

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String urlg = "jdbc:sqlserver://localhost:1433;databaseName=PROJECT_JAVA;";
                Connection con = DriverManager.getConnection(urlg, "java", "java");
                String sql = "select*from TAIKHOAN where TENDANGNHAP=? and MATKHAU=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtUsername.getText());
                ps.setString(2, pwdPassword.getText());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {

                    JOptionPane.showMessageDialog(rootPane, "Đăng nhập thành công");

                    new MainJFrame().setVisible(true);
                    this.dispose();
                    new MainJFrame().pack();
                    new MainJFrame().setLocationRelativeTo(null);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnThoatActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //import JTattoo
        try {
            // select Look and Feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
            // start application
            //new MainJFrame();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginJFrame().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btnDangNhap;
    private java.awt.Button btnThoat;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JLabel lblDangNhap;
    private javax.swing.JLabel lblImgLogin;
    private javax.swing.JLabel lblLienhe;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel panImgLogin;
    private javax.swing.JPanel panLoginForm;
    private javax.swing.JPasswordField pwdPassword;
    private javax.swing.JSeparator sptPassword;
    private javax.swing.JSeparator sptUsername;
    public static javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
