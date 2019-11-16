package edu.poly.views;

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class AddTableJFrame extends javax.swing.JFrame {

    public AddTableJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Phanv\\"
                + "OneDrive\\Documents\\NetBeansProjects\\QuanLyCafe\\src\\edu\\poly\\image\\logo_title.png"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panBackgoundTable = new javax.swing.JPanel();
        panTitleTable = new javax.swing.JPanel();
        lblMaBan = new javax.swing.JLabel();
        txtMaBan = new javax.swing.JTextField();
        btnThemBan = new javax.swing.JButton();
        sptMaBan = new javax.swing.JSeparator();
        sptTenBan = new javax.swing.JSeparator();
        lblTenBan = new javax.swing.JLabel();
        txtTenBan = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thêm bàn");

        panBackgoundTable.setBackground(new java.awt.Color(255, 255, 255));

        panTitleTable.setBackground(new java.awt.Color(255, 255, 255));
        panTitleTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm bàn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semilight", 0, 16), new java.awt.Color(23, 35, 51))); // NOI18N

        lblMaBan.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblMaBan.setForeground(new java.awt.Color(23, 35, 51));
        lblMaBan.setText("Mã bàn:");

        txtMaBan.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtMaBan.setForeground(new java.awt.Color(23, 35, 51));
        txtMaBan.setBorder(null);
        txtMaBan.setEnabled(false);

        btnThemBan.setBackground(new java.awt.Color(23, 35, 51));
        btnThemBan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnThemBan.setForeground(new java.awt.Color(255, 255, 255));
        btnThemBan.setText("Thêm");

        sptMaBan.setForeground(new java.awt.Color(23, 35, 51));

        sptTenBan.setForeground(new java.awt.Color(23, 35, 51));

        lblTenBan.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblTenBan.setForeground(new java.awt.Color(23, 35, 51));
        lblTenBan.setText("Tên bàn:");

        txtTenBan.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        txtTenBan.setForeground(new java.awt.Color(23, 35, 51));
        txtTenBan.setBorder(null);

        javax.swing.GroupLayout panTitleTableLayout = new javax.swing.GroupLayout(panTitleTable);
        panTitleTable.setLayout(panTitleTableLayout);
        panTitleTableLayout.setHorizontalGroup(
            panTitleTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTitleTableLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panTitleTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panTitleTableLayout.createSequentialGroup()
                        .addComponent(lblMaBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panTitleTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sptMaBan)
                            .addComponent(txtMaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)))
                    .addGroup(panTitleTableLayout.createSequentialGroup()
                        .addComponent(lblTenBan)
                        .addGap(25, 25, 25)
                        .addGroup(panTitleTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemBan, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panTitleTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(sptTenBan)
                                .addComponent(txtTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panTitleTableLayout.setVerticalGroup(
            panTitleTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTitleTableLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panTitleTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaBan)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sptMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(panTitleTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenBan)
                    .addComponent(txtTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sptTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnThemBan)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout panBackgoundTableLayout = new javax.swing.GroupLayout(panBackgoundTable);
        panBackgoundTable.setLayout(panBackgoundTableLayout);
        panBackgoundTableLayout.setHorizontalGroup(
            panBackgoundTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
            .addGroup(panBackgoundTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panBackgoundTableLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panTitleTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panBackgoundTableLayout.setVerticalGroup(
            panBackgoundTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
            .addGroup(panBackgoundTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panBackgoundTableLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panTitleTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBackgoundTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBackgoundTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddTableJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTableJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTableJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTableJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new AddTableJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThemBan;
    private javax.swing.JLabel lblMaBan;
    private javax.swing.JLabel lblTenBan;
    private javax.swing.JPanel panBackgoundTable;
    private javax.swing.JPanel panTitleTable;
    private javax.swing.JSeparator sptMaBan;
    private javax.swing.JSeparator sptTenBan;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtTenBan;
    // End of variables declaration//GEN-END:variables
}
