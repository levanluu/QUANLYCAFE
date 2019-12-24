package edu.poly.views;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class LoginJFrame extends javax.swing.JFrame {

    public LoginJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Phanv\\"
                + "OneDrive\\Documents\\NetBeansProjects\\QuanLyCafe_1\\src\\edu\\poly\\image\\logo_title.png"));
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
        btnOut = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();

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
        pwdPassword.setText("0123456");
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
        txtUsername.setText("VUPVPD02716");
        txtUsername.setBorder(null);
        panLoginForm.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 48, 320, 30));

        btnOut.setBackground(new java.awt.Color(23, 35, 51));
        btnOut.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnOut.setForeground(new java.awt.Color(255, 255, 255));
        btnOut.setText("Thoát");
        btnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutActionPerformed(evt);
            }
        });
        panLoginForm.add(btnOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 110, 40));

        btnLogin.setBackground(new java.awt.Color(23, 35, 51));
        btnLogin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Đăng nhập");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        panLoginForm.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 110, 40));

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

    private void btnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnOutActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        if (txtUsername.getText().equals("") && pwdPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thất bại", "Trống", JOptionPane.WARNING_MESSAGE);
        } else if (txtUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được để trống Username", "Trống", JOptionPane.WARNING_MESSAGE);
        } else if (pwdPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được để trống Password", "Trống", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=PROJECT_JAVA1;";
                Connection con = DriverManager.getConnection(url, "JAVA", "123456");
                String sql = "SELECT*FROM TAIKHOAN WHERE TENDANGNHAP=? AND MATKHAU=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtUsername.getText());
                ps.setString(2, pwdPassword.getText());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (txtUsername.getText().equals("VUPVPD02716")) {
                        JOptionPane.showMessageDialog(this, "Bạn đã đăng nhập quyền quản trị", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                        MainJFrame main = new MainJFrame();
                        main.setVisible(true);
                        main.btnQuanLyDisable.setVisible(false);
                        main.btnThongKeDisable.setVisible(false);
                        new MainJFrame().pack();
                    } else {
                        this.dispose();
                        MainJFrame main = new MainJFrame();
                        main.setVisible(true);
                        main.btnQuanLy.setVisible(false);
                        main.panQuanLy.setVisible(false);
                        main.btnThongKe.setVisible(false);
                        main.panThongKe.setVisible(false);
                        new MainJFrame().pack();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại", "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnOut;
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
