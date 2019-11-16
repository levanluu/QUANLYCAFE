package edu.poly.views;

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class AddAccountJFrame extends javax.swing.JFrame {

    public AddAccountJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Phanv\\"
                + "OneDrive\\Documents\\NetBeansProjects\\QuanLyCafe\\src\\edu\\poly\\image\\logo_title.png"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panBackgoundDoiPass = new javax.swing.JPanel();
        lblTitleDoiPass = new javax.swing.JLabel();
        lblOldPass = new javax.swing.JLabel();
        sptOldPass = new javax.swing.JSeparator();
        lblNewPass = new javax.swing.JLabel();
        lblCFNewPass = new javax.swing.JLabel();
        lblLamMoiDoiPass = new javax.swing.JButton();
        pwdCFNewPass = new javax.swing.JPasswordField();
        pwdNewPass = new javax.swing.JPasswordField();
        sptNewPass = new javax.swing.JSeparator();
        pwdOldPass = new javax.swing.JPasswordField();
        sptCFNewPass = new javax.swing.JSeparator();
        lblUserName = new javax.swing.JLabel();
        sptUserName = new javax.swing.JSeparator();
        lblSaveChange1 = new javax.swing.JButton();
        txtUserName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thêm tài khoản");

        panBackgoundDoiPass.setBackground(new java.awt.Color(255, 255, 255));

        lblTitleDoiPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerAccount.jpg"))); // NOI18N

        lblOldPass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblOldPass.setForeground(new java.awt.Color(23, 35, 51));
        lblOldPass.setText("Mật khẩu cũ:");

        sptOldPass.setBackground(new java.awt.Color(23, 35, 51));

        lblNewPass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblNewPass.setForeground(new java.awt.Color(23, 35, 51));
        lblNewPass.setText("Mật khẩu mới:");

        lblCFNewPass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblCFNewPass.setForeground(new java.awt.Color(23, 35, 51));
        lblCFNewPass.setText("Nhập lại mật khẩu mới:");

        lblLamMoiDoiPass.setBackground(new java.awt.Color(23, 35, 51));
        lblLamMoiDoiPass.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblLamMoiDoiPass.setForeground(new java.awt.Color(255, 255, 255));
        lblLamMoiDoiPass.setText("Làm mới");
        lblLamMoiDoiPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblLamMoiDoiPassActionPerformed(evt);
            }
        });

        pwdCFNewPass.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        pwdCFNewPass.setForeground(new java.awt.Color(23, 35, 51));
        pwdCFNewPass.setBorder(null);

        pwdNewPass.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        pwdNewPass.setForeground(new java.awt.Color(23, 35, 51));
        pwdNewPass.setBorder(null);

        sptNewPass.setBackground(new java.awt.Color(23, 35, 51));

        pwdOldPass.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        pwdOldPass.setForeground(new java.awt.Color(23, 35, 51));
        pwdOldPass.setBorder(null);

        sptCFNewPass.setBackground(new java.awt.Color(23, 35, 51));

        lblUserName.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(23, 35, 51));
        lblUserName.setText("Tên tài khoản:");

        sptUserName.setBackground(new java.awt.Color(23, 35, 51));

        lblSaveChange1.setBackground(new java.awt.Color(23, 35, 51));
        lblSaveChange1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblSaveChange1.setForeground(new java.awt.Color(255, 255, 255));
        lblSaveChange1.setText("Lưu");

        txtUserName.setForeground(new java.awt.Color(23, 35, 51));
        txtUserName.setBorder(null);

        javax.swing.GroupLayout panBackgoundDoiPassLayout = new javax.swing.GroupLayout(panBackgoundDoiPass);
        panBackgoundDoiPass.setLayout(panBackgoundDoiPassLayout);
        panBackgoundDoiPassLayout.setHorizontalGroup(
            panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitleDoiPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panBackgoundDoiPassLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panBackgoundDoiPassLayout.createSequentialGroup()
                        .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNewPass)
                            .addComponent(lblOldPass))
                        .addGap(38, 38, 38)
                        .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(sptOldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(sptNewPass)
                                    .addComponent(pwdNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(pwdOldPass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panBackgoundDoiPassLayout.createSequentialGroup()
                        .addComponent(lblUserName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sptUserName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panBackgoundDoiPassLayout.createSequentialGroup()
                        .addComponent(lblCFNewPass)
                        .addGap(18, 18, 18)
                        .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sptCFNewPass)
                            .addComponent(pwdCFNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panBackgoundDoiPassLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSaveChange1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblLamMoiDoiPass, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
        );
        panBackgoundDoiPassLayout.setVerticalGroup(
            panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panBackgoundDoiPassLayout.createSequentialGroup()
                .addComponent(lblTitleDoiPass, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(sptUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOldPass)
                    .addComponent(pwdOldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(sptOldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwdNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNewPass))
                .addGap(3, 3, 3)
                .addComponent(sptNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCFNewPass)
                    .addComponent(pwdCFNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sptCFNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSaveChange1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLamMoiDoiPass, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBackgoundDoiPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBackgoundDoiPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblLamMoiDoiPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblLamMoiDoiPassActionPerformed
        txtUserName.setText("");
        pwdOldPass.setText("");
        pwdNewPass.setText("");
        pwdCFNewPass.setText("");
        txtUserName.requestFocus();
    }//GEN-LAST:event_lblLamMoiDoiPassActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddAccountJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddAccountJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddAccountJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddAccountJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new AddAccountJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblCFNewPass;
    private javax.swing.JButton lblLamMoiDoiPass;
    private javax.swing.JLabel lblNewPass;
    private javax.swing.JLabel lblOldPass;
    private javax.swing.JButton lblSaveChange1;
    private javax.swing.JLabel lblTitleDoiPass;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JPanel panBackgoundDoiPass;
    private javax.swing.JPasswordField pwdCFNewPass;
    private javax.swing.JPasswordField pwdNewPass;
    private javax.swing.JPasswordField pwdOldPass;
    private javax.swing.JSeparator sptCFNewPass;
    private javax.swing.JSeparator sptNewPass;
    private javax.swing.JSeparator sptOldPass;
    private javax.swing.JSeparator sptUserName;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
