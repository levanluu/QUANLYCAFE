package edu.poly.views;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class AddAccountJFrame extends javax.swing.JFrame {
 private Connection conn;
    public AddAccountJFrame() {
        initComponents();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PROJECT_JAVA;", "java", "java");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Phanv\\"
                + "OneDrive\\Documents\\NetBeansProjects\\QuanLyCafe\\src\\edu\\poly\\image\\logo_title.png"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
     public void them(){
          if (txtUserName.getText().equals("")||txtngaysinh.getText().equals("")||txtsdt.getText().equals("")||txtgioitinh.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "vui lòng điền đầy đủ thông tin");
        } else {

            try {

                String sql = "INSERT INTO NHANVIEN VALUES (?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtUserName.getText());
               
                ps.execute();
                JOptionPane.showMessageDialog(this, "thêm mới món thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
          }
     
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
        sptNewPass = new javax.swing.JSeparator();
        sptCFNewPass = new javax.swing.JSeparator();
        lblUserName = new javax.swing.JLabel();
        sptUserName = new javax.swing.JSeparator();
        lblSaveChange1 = new javax.swing.JButton();
        txtUserName = new javax.swing.JTextField();
        txtngaysinh = new javax.swing.JTextField();
        txtsdt = new javax.swing.JTextField();
        txtgioitinh = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thêm tài khoản");

        panBackgoundDoiPass.setBackground(new java.awt.Color(255, 255, 255));

        lblTitleDoiPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerAccount.jpg"))); // NOI18N

        lblOldPass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblOldPass.setForeground(new java.awt.Color(23, 35, 51));
        lblOldPass.setText("Ngày Sinh:");

        sptOldPass.setBackground(new java.awt.Color(23, 35, 51));

        lblNewPass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblNewPass.setForeground(new java.awt.Color(23, 35, 51));
        lblNewPass.setText("Số Điện Thoại:");

        lblCFNewPass.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblCFNewPass.setForeground(new java.awt.Color(23, 35, 51));
        lblCFNewPass.setText("Giới Tính:");

        lblLamMoiDoiPass.setBackground(new java.awt.Color(23, 35, 51));
        lblLamMoiDoiPass.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblLamMoiDoiPass.setForeground(new java.awt.Color(255, 255, 255));
        lblLamMoiDoiPass.setText("Làm mới");
        lblLamMoiDoiPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblLamMoiDoiPassActionPerformed(evt);
            }
        });

        sptNewPass.setBackground(new java.awt.Color(23, 35, 51));

        sptCFNewPass.setBackground(new java.awt.Color(23, 35, 51));

        lblUserName.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(23, 35, 51));
        lblUserName.setText("Tên Nhân viên:");

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
                        .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(sptOldPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                            .addComponent(sptNewPass, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtngaysinh)
                            .addComponent(txtsdt)))
                    .addGroup(panBackgoundDoiPassLayout.createSequentialGroup()
                        .addComponent(lblUserName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sptUserName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panBackgoundDoiPassLayout.createSequentialGroup()
                        .addComponent(lblCFNewPass)
                        .addGap(81, 81, 81)
                        .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sptCFNewPass, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(txtgioitinh))))
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
                .addGap(36, 36, 36)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOldPass)
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(sptOldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNewPass)
                    .addGroup(panBackgoundDoiPassLayout.createSequentialGroup()
                        .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGap(10, 10, 10)
                .addComponent(sptNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(panBackgoundDoiPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCFNewPass)
                    .addComponent(txtgioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sptCFNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
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
    private javax.swing.JSeparator sptCFNewPass;
    private javax.swing.JSeparator sptNewPass;
    private javax.swing.JSeparator sptOldPass;
    private javax.swing.JSeparator sptUserName;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtgioitinh;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txtsdt;
    // End of variables declaration//GEN-END:variables
}
