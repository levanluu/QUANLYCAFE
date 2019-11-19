package edu.poly.views;

import edu.poly.models.HoaDon;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Date;

import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class MainJFrame extends javax.swing.JFrame {
    int index = 0;
    ArrayList<HoaDon> list = new ArrayList<>();
    DefaultTableModel model,model1,model2;
    private Connection conn;
   
    NumberFormat formatter = new DecimalFormat("#,###");
    SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    PreparedStatement ps;
  
    public MainJFrame() {
        initComponents();
        model = (DefaultTableModel) tblGoiMon.getModel();
        model1 = (DefaultTableModel) tblthongke.getModel();
         model2 = (DefaultTableModel) tblQuanLyDoUong.getModel();
        setLocationRelativeTo(null);

        //Kết nối database;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PROJECT_JAVA;", "java", "java");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Phanv\\"
                + "OneDrive\\Documents\\NetBeansProjects\\QuanLyCafe\\src\\edu\\poly\\image\\logo_title.png"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set màu hiển thị cho menu gọi món
        setColor(btnBanHang);
        pan_btnBanHang.setOpaque(true);
        resetColor(new JPanel[]{btnQuanLy, btnThongKe, btnThietLap}, new JPanel[]{pan_btnQuanLy, pan_btnThongKe, pan_btnThietLap});

        //Set màu title bảng
        tblGoiMon.getTableHeader().setBackground(Color.WHITE);
        tblQuanLyDoUong.getTableHeader().setBackground(Color.WHITE);
        tblQuanLyBan.getTableHeader().setBackground(Color.WHITE);
        tblTaiKhoan.getTableHeader().setBackground(Color.WHITE);
        tblthongke.getTableHeader().setBackground(Color.WHITE);

        //Ẩn hiện các panel
        tbpQuanLi.setVisible(false);
        panThongKe.setVisible(false);

        //hiển thị combbox;
        loadDataToCbo();
        //hiển thị combbox bàn
        loadBan();

        //hiển thị tên nhân viên lên form mainjframe bằng với tên đăng nhập;
        txtTenNhanVien.setText(LoginJFrame.txtUsername.getText());
        // hiển thị id nhân viên lên txtidnhanvien
        idnhanvien();
        hienthimonQL();
    }

    private void setColor(JPanel pane) {
        pane.setBackground(new Color(41, 57, 80));
    }

    private void resetColor(JPanel[] pane, JPanel[] indicators) {
        for (int i = 0; i < pane.length; i++) {
            pane[i].setBackground(new Color(23, 35, 51));

        }
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setOpaque(false);
        }
    }

    void BanHang() {
        setColor(btnBanHang);
        pan_btnBanHang.setOpaque(true);
        resetColor(new JPanel[]{btnThongKe, btnQuanLy, btnThietLap}, new JPanel[]{pan_btnThongKe, pan_btnQuanLy, pan_btnThietLap});
        panQuanLy.setVisible(false);
        panThongKe.setVisible(false);
        panBanHang.setVisible(true);
    }

    void QuanLy() {
        setColor(btnQuanLy);
        pan_btnQuanLy.setOpaque(true);
        resetColor(new JPanel[]{btnBanHang, btnThongKe, btnThietLap}, new JPanel[]{pan_btnBanHang, pan_btnThongKe, pan_btnThietLap});
        panBanHang.setVisible(false);
        panQuanLy.setVisible(true);
        tblGoiMon.setVisible(false);
        tbpQuanLi.setVisible(true);
        btnThongKeHD.setVisible(true);
        btnDangXuat.setVisible(false);
    }

    void ThiepLap() {
        setColor(btnThietLap);
        pan_btnThietLap.setOpaque(true);
        resetColor(new JPanel[]{btnBanHang, btnQuanLy, btnThongKe}, new JPanel[]{pan_btnBanHang, pan_btnQuanLy, pan_btnThongKe});
        panQuanLy.setVisible(false);
        panThongKe.setVisible(false);
        panBanHang.setVisible(false);
        panThietLap.setVisible(true);
        btnDangXuat.setVisible(true);
    }

    void ThongKe() {
        setColor(btnThongKe);
        pan_btnThongKe.setOpaque(true);
        resetColor(new JPanel[]{btnBanHang, btnQuanLy, btnThietLap}, new JPanel[]{pan_btnBanHang, pan_btnQuanLy, pan_btnThietLap});
        panBanHang.setVisible(false);
        panQuanLy.setVisible(false);
        panThongKe.setVisible(true);
        cmbBan.setSelectedIndex(0);
    }

    // thêm vào bảng từ combbox của bàn và đồ uống ;
    public boolean selectItem() {
        try {
            //câu lệnh sql ;
            String sql = "SELECT * FROM DOUONG WHERE TENDOUONG = ?";
            String mysql = " SELECT TENBAN FROM BAN WHERE IDBAN= ? ";

            String ma = (String) cmbTenDoUong.getSelectedItem();
            String mas = (String) cmbBan.getSelectedItem();
            PreparedStatement ps = conn.prepareStatement(sql);
            PreparedStatement pst = conn.prepareStatement(mysql);

            ps.setString(1, ma);
            pst.setString(1, mas);

            ResultSet rs = ps.executeQuery();
            ResultSet rst = pst.executeQuery();

            while (rs.next() && rst.next()) {
                //lấy dữ liệu ra bảng ;
                Vector row = new Vector();
                row.add(Integer.parseInt(rs.getString("iDDouong")));
                row.add(rs.getString("tenDoUong"));
                row.add(Integer.parseInt(rs.getString("iDDanhMuc")));
                row.add(Float.parseFloat(rs.getString("DonGia")));
                row.add(rst.getString("TENBAN"));

                model.addRow(row);

            }
            tblGoiMon.setModel(model);
            // tổng của cột giá tiền ;
            getsum();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // hiển thị danh sách bàn ra combbox lấy từ sql ;
    public void selectItemBan() {
        try {
            String sql = " SELECT*FROM BAN WHERE IDBan=?";
            String ma = (String) cmbBan.getSelectedItem();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("IDBan"));
                model.addRow(row);
            }
            tblGoiMon.setModel(model);

        } catch (Exception e) {
        }
    }

    // hiển thị tên đồ ăn ra combbox ;
    public void loadDataToCbo() {
        try {
            String sql = "SELECT TENDOUONG FROM DOUONG";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                cmbTenDoUong.addItem(rs.getString("TENDOUONG"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //hiển thị bàn ra combbox ;
    public void loadBan() {
        try {
            String sql = "SELECT IDBAN FROM BAN";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cmbBan.addItem(rs.getString("IDBan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // hàm tính tổng của cột giá tiền ;
    public void getsum() {

        float sum = 0;
        for (int i = 0; i < tblGoiMon.getRowCount(); i++) {
            sum = sum + Float.parseFloat(tblGoiMon.getValueAt(i, 3).toString());
        }
        txtTongCong.setText(Float.toString(sum));
    }

    //hàm tính tiền thối lại  
    // tiền khách đưa - tổng tiền ;
    public void repay() {
        float rep = 0;
        float a = Float.parseFloat(txtTongCong.getText());
        float b = Float.parseFloat(txtGia.getText());
        rep = b - a;
        txtTienThoi.setText(Float.toString(rep));
        Object[] options1 = {"In Hóa Đơn", "OK"
        };

        JOptionPane.showOptionDialog(null,
                "Giá tiền của bạn thanh toán là " + rep,
                "Tiền Trả lại cho khách",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options1,
                null);
        this.printbill();
    }
    //lấy năm tháng ngày hiện tại để truyền vào databasa;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    //2016/11/16 12:08:43

    // Lưu lại các món đã chọn hiện ở bảng vào hóa đơn  
    public void SaveAndPrintBill() {

//      
        String idBan = String.valueOf(cmbBan.getSelectedItem());
        try {

            String sql = "INSERT INTO HOADON VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idBan));
            ps.setInt(2, Integer.parseInt(txtidnhanvien.getText()));
            ps.setString(3, now.toString());
            ps.setString(4, "đã thanh toán");
            ps.setDouble(5, Double.parseDouble(txtTongCong.getText()));
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //lưu thông tin vào hóa đơn chi tiết 
    public void saveprintdetail() {
        String idhoadon = String.valueOf(cmbhoadon.getSelectedItem());
        String iddouong = String.valueOf(cmbTenDoUong.getSelectedItem());
        try {

            String sql = "INSERT INTO CHITIETHOADON VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idhoadon));
            for (int i = 0; i < tblGoiMon.getRowCount(); i++) {
                ps.setInt(2, Integer.parseInt(tblGoiMon.getValueAt(i, 0).toString()));
            }
            ps.setInt(3, 1);
            ps.setInt(4, 1);
            ps.setDouble(5, Double.parseDouble(txtTongCong.getText()));
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //hiển thị id nhân viên lên txtidnhanvien 
    public void idnhanvien() {
        try {
            String sql = "SELECT IDNHANVIEN FROM TAIKHOAN WHERE TENDANGNHAP=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, txtTenNhanVien.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                txtidnhanvien.setText(rs.getString("idnhanvien"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // in hóa đơn vào file billcustomer ; khi coppy sang nhớ tạo file và đổi lại đường dẫn 
    public void printbill() {
        int line = tblGoiMon.getRowCount();
        try {

            String path = "D:\\bill\\billcustomer.txt";
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            Date now = new Date();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("\t\t\tTHE GARDEN COFFEE\r\n\r\n");
            bw.write("\t\t590 CMT8, P.11, Q.3, TPHCM\r\n");
            bw.write("\t\t\tSĐT: 01212692802\r\n\r\n");
            bw.write("\t\t\tHÓA ĐƠN BÁN HÀNG\r\n\r\n");
            bw.write("Mã Bàn: " + cmbBan.getSelectedItem() + "\r\n");
            bw.write("Thời gian: " + ft.format(now) + "\r\n");
            bw.write("Nhân vien: " + txtTenNhanVien.getText() + "\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write("-----------------------------------------------------------\r\n");
            for (int i = 0; i < line; i++) {
                int id = (int) tblGoiMon.getValueAt(i, 0);
                String name = (String) tblGoiMon.getValueAt(i, 1);
                int Danhmuc = (int) tblGoiMon.getValueAt(i, 2);
                float price = Float.parseFloat(tblGoiMon.getValueAt(i, 3).toString());
                String Ban = String.valueOf(tblGoiMon.getValueAt(i, 4));

                bw.write((i + 1) + ". " + name + "\r\n");
                bw.write(id + "\t" + name + "\t\t" + Danhmuc + "\t\t" + price + "\t" + Ban + "\r\n\r\n");

            }
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Tổng cộng:\t\t" + txtTongCong.getText() + " VNĐ\r\n");

            bw.write("\t\t--------------------------------------------\r\n");
            bw.write("\t\tThành tiền:\t\t\t" + Float.parseFloat(txtTongCong.getText()) + " VNĐ\r\n");
            bw.write("\t\t--------------------------------------------\r\n");
            bw.write("\t\tTiền khách đưa:\t\t\t" + txtGia.getText() + " VNĐ\r\n");
            bw.write("\t\tTiền trả lại:\t\t\t" + txtTienThoi.getText() + " VNĐ\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Chương trình khuyến mãi: Không có  ");

            // Close connection
            bw.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            FileReader fr = new FileReader("D:\\bill\\billcustomer.txt");
            BufferedReader br = new BufferedReader(fr);

            int i;
            while ((i = br.read()) != -1) {
                System.out.print((char) i);
            }
            br.close();
            fr.close();

        } catch (Exception e) {
        }

    }

    public void selectstatistic() {

        try {
            String string = txtngaytk.getText().trim();
            System.out.println("1");
            String sql = "SELECT * FROM HOADON WHERE NGAYLAP like ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, string);
            ResultSet rs = ps.executeQuery();
            System.out.println(string);
            while (rs.next()) {
                 Vector row = new Vector();
                row.add(Integer.parseInt(rs.getString("iDHoaDon")));
                row.add(Integer.parseInt(rs.getString("iDBan")));
                row.add(Integer.parseInt(rs.getString("iDNhanVien")));
                row.add(rs.getString("ngayLap"));
                row.add(rs.getString("trangThai"));
                row.add(Double.parseDouble(rs.getString("tongTien")));
               
                model1.addRow(row);
                
            }
            tblthongke.setModel(model1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void totalstastistic(){
        float sum=0;
        for (int i = 0; i < tblthongke.getRowCount(); i++) {
            sum = sum + Float.parseFloat(tblthongke.getValueAt(i, 5).toString());
        }
        JOptionPane.showMessageDialog(null,"Tổng tiền thống kê ngày "+txtngaytk.getText() +" là " +sum );
    }
    
    public void hienthimonQL(){
        try {
            String sql="SELECT IDDOUONG ,TENDOUONG,DONGIA FROM DOUONG ";
            Statement st =conn.createStatement();
            ResultSet rs= st.executeQuery(sql);
            while (rs.next()) {
                 Vector row = new Vector();
            row.add(Integer.parseInt(rs.getString("iDDoUong")));
            row.add(rs.getString("tenDoUong"));
            row.add(Double.parseDouble(rs.getString("donGia")));
            model2.addRow(row);
        }
        tblQuanLyDoUong.setModel(model2);
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void timkiemmon(){
        try {
             String sql ="SELECT IDDOUONG ,TENDOUONG,DONGIA FROM DOUONG WHERE TENDOUONG =?";
        PreparedStatement ps =conn.prepareStatement(sql);
        ps.setString(1,txtTimMon.getText());
        ResultSet rs= ps.executeQuery();
        while(rs.next()){
             Vector row = new Vector();
            row.add(Integer.parseInt(rs.getString("iDDoUong")));
            row.add(rs.getString("tenDoUong"));
            row.add(Double.parseDouble(rs.getString("donGia")));
            model2.addRow(row);
        }
        tblQuanLyDoUong.setModel(model2);
        } catch (Exception e) {
           
            e.printStackTrace();
        }
       
        
    }
    private void clicktable(int column){
        UpdateProduct.txtTenMon.setText((String) model2.getValueAt(column, 1));
        UpdateProduct.txtDanhMuc.setText("2");
          UpdateProduct.txtDonGia.setText((String) model2.getValueAt(column, 2));
 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panMenu = new javax.swing.JPanel();
        btnBanHang = new javax.swing.JPanel();
        pan_btnBanHang = new javax.swing.JPanel();
        lblBanHang = new javax.swing.JLabel();
        btnQuanLy = new javax.swing.JPanel();
        pan_btnQuanLy = new javax.swing.JPanel();
        lblQuanLy = new javax.swing.JLabel();
        btnThongKe = new javax.swing.JPanel();
        pan_btnThongKe = new javax.swing.JPanel();
        lblThongKe = new javax.swing.JLabel();
        btnThietLap = new javax.swing.JPanel();
        pan_btnThietLap = new javax.swing.JPanel();
        lblThietLap = new javax.swing.JLabel();
        lblIconUser = new javax.swing.JLabel();
        lblHello = new javax.swing.JLabel();
        panBackgound = new javax.swing.JPanel();
        panQuanLy = new javax.swing.JPanel();
        lblBannerTitleQuanLy = new javax.swing.JLabel();
        tbpQuanLi = new javax.swing.JTabbedPane();
        panQuanLyDoUong = new javax.swing.JPanel();
        txtTimMon = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQuanLyDoUong = new javax.swing.JTable();
        btnXoaMon = new javax.swing.JButton();
        btnThemMon = new javax.swing.JButton();
        btnSuaMon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        panQuanLyBan = new javax.swing.JPanel();
        txtTimBan = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblQuanLyBan = new javax.swing.JTable();
        btnXoaBan = new javax.swing.JButton();
        btnThemBan = new javax.swing.JButton();
        btnSuaBan = new javax.swing.JButton();
        lblTimBan = new javax.swing.JLabel();
        sptTimBan = new javax.swing.JSeparator();
        btnTimBan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        panQuanLyTaiKhoan = new javax.swing.JPanel();
        txtTimTaiKhoan = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTaiKhoan = new javax.swing.JTable();
        btnXoaTK = new javax.swing.JButton();
        btnThemTK = new javax.swing.JButton();
        btnSuaTK = new javax.swing.JButton();
        lblTimTaiKhoan = new javax.swing.JLabel();
        sptTimTaiKhoan = new javax.swing.JSeparator();
        btnTimTaiKhoan = new javax.swing.JButton();
        panBanHang = new javax.swing.JPanel();
        lblBannerTitle = new javax.swing.JLabel();
        panGoiMon = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGoiMon = new javax.swing.JTable();
        lblTenNhanVien = new javax.swing.JLabel();
        sptTenNhanVien = new javax.swing.JSeparator();
        txtTenNhanVien = new javax.swing.JTextField();
        lblTenDoUong = new javax.swing.JLabel();
        cmbTenDoUong = new javax.swing.JComboBox<>();
        btnThem = new java.awt.Button();
        panThanhToan = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTongCong = new javax.swing.JLabel();
        sptTongCong = new javax.swing.JSeparator();
        txtTongCong = new javax.swing.JTextField();
        sptGia = new javax.swing.JSeparator();
        txtGia = new javax.swing.JTextField();
        lbltienkhachdua = new javax.swing.JLabel();
        txtTienThoi = new javax.swing.JTextField();
        sptTienThoi = new javax.swing.JSeparator();
        lblTienThoi = new javax.swing.JLabel();
        btnLuu = new java.awt.Button();
        btnXoa = new java.awt.Button();
        lblBan = new javax.swing.JLabel();
        cmbBan = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtidnhanvien = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbhoadon = new javax.swing.JComboBox<>();
        panThongKe = new javax.swing.JPanel();
        lblBannerThongKe = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblthongke = new javax.swing.JTable();
        lblThongKeHoaDon = new javax.swing.JLabel();
        btnThongKeHD = new javax.swing.JButton();
        lblTongSoHD = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        txtngaytk = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btntongtk = new javax.swing.JButton();
        panThietLap = new javax.swing.JPanel();
        lblBannerTitle1 = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hệ thống quản lý");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panMenu.setBackground(new java.awt.Color(23, 35, 51));
        panMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBanHang.setBackground(new java.awt.Color(23, 35, 51));
        btnBanHang.setPreferredSize(new java.awt.Dimension(3, 70));
        btnBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnBanHangMousePressed(evt);
            }
        });

        pan_btnBanHang.setOpaque(false);
        pan_btnBanHang.setPreferredSize(new java.awt.Dimension(3, 70));

        javax.swing.GroupLayout pan_btnBanHangLayout = new javax.swing.GroupLayout(pan_btnBanHang);
        pan_btnBanHang.setLayout(pan_btnBanHangLayout);
        pan_btnBanHangLayout.setHorizontalGroup(
            pan_btnBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        pan_btnBanHangLayout.setVerticalGroup(
            pan_btnBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        lblBanHang.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblBanHang.setForeground(new java.awt.Color(255, 255, 255));
        lblBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_buy_25px.png"))); // NOI18N
        lblBanHang.setText("   Gọi món");

        javax.swing.GroupLayout btnBanHangLayout = new javax.swing.GroupLayout(btnBanHang);
        btnBanHang.setLayout(btnBanHangLayout);
        btnBanHangLayout.setHorizontalGroup(
            btnBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBanHangLayout.createSequentialGroup()
                .addComponent(pan_btnBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lblBanHang)
                .addGap(0, 48, Short.MAX_VALUE))
        );
        btnBanHangLayout.setVerticalGroup(
            btnBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBanHangLayout.createSequentialGroup()
                .addComponent(pan_btnBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btnBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pan_btnBanHang.getAccessibleContext().setAccessibleName("");

        panMenu.add(btnBanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 170, 70));

        btnQuanLy.setBackground(new java.awt.Color(23, 35, 51));
        btnQuanLy.setPreferredSize(new java.awt.Dimension(3, 70));
        btnQuanLy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQuanLyMousePressed(evt);
            }
        });

        pan_btnQuanLy.setOpaque(false);
        pan_btnQuanLy.setPreferredSize(new java.awt.Dimension(3, 70));

        javax.swing.GroupLayout pan_btnQuanLyLayout = new javax.swing.GroupLayout(pan_btnQuanLy);
        pan_btnQuanLy.setLayout(pan_btnQuanLyLayout);
        pan_btnQuanLyLayout.setHorizontalGroup(
            pan_btnQuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        pan_btnQuanLyLayout.setVerticalGroup(
            pan_btnQuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        lblQuanLy.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblQuanLy.setForeground(new java.awt.Color(255, 255, 255));
        lblQuanLy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_start_menu_25px.png"))); // NOI18N
        lblQuanLy.setText("   Quản lý");

        javax.swing.GroupLayout btnQuanLyLayout = new javax.swing.GroupLayout(btnQuanLy);
        btnQuanLy.setLayout(btnQuanLyLayout);
        btnQuanLyLayout.setHorizontalGroup(
            btnQuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnQuanLyLayout.createSequentialGroup()
                .addComponent(pan_btnQuanLy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lblQuanLy)
                .addGap(0, 53, Short.MAX_VALUE))
        );
        btnQuanLyLayout.setVerticalGroup(
            btnQuanLyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnQuanLyLayout.createSequentialGroup()
                .addComponent(pan_btnQuanLy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btnQuanLyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQuanLy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panMenu.add(btnQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 170, 70));

        btnThongKe.setBackground(new java.awt.Color(23, 35, 51));
        btnThongKe.setPreferredSize(new java.awt.Dimension(3, 70));
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThongKeMousePressed(evt);
            }
        });

        pan_btnThongKe.setOpaque(false);
        pan_btnThongKe.setPreferredSize(new java.awt.Dimension(3, 70));

        javax.swing.GroupLayout pan_btnThongKeLayout = new javax.swing.GroupLayout(pan_btnThongKe);
        pan_btnThongKe.setLayout(pan_btnThongKeLayout);
        pan_btnThongKeLayout.setHorizontalGroup(
            pan_btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        pan_btnThongKeLayout.setVerticalGroup(
            pan_btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        lblThongKe.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_statistics_25px.png"))); // NOI18N
        lblThongKe.setText("   Thống kê");

        javax.swing.GroupLayout btnThongKeLayout = new javax.swing.GroupLayout(btnThongKe);
        btnThongKe.setLayout(btnThongKeLayout);
        btnThongKeLayout.setHorizontalGroup(
            btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThongKeLayout.createSequentialGroup()
                .addComponent(pan_btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lblThongKe)
                .addGap(0, 45, Short.MAX_VALUE))
        );
        btnThongKeLayout.setVerticalGroup(
            btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThongKeLayout.createSequentialGroup()
                .addComponent(pan_btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btnThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panMenu.add(btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 170, -1));

        btnThietLap.setBackground(new java.awt.Color(23, 35, 51));
        btnThietLap.setPreferredSize(new java.awt.Dimension(3, 70));
        btnThietLap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThietLapMousePressed(evt);
            }
        });

        pan_btnThietLap.setOpaque(false);
        pan_btnThietLap.setPreferredSize(new java.awt.Dimension(3, 70));

        javax.swing.GroupLayout pan_btnThietLapLayout = new javax.swing.GroupLayout(pan_btnThietLap);
        pan_btnThietLap.setLayout(pan_btnThietLapLayout);
        pan_btnThietLapLayout.setHorizontalGroup(
            pan_btnThietLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        pan_btnThietLapLayout.setVerticalGroup(
            pan_btnThietLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        lblThietLap.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblThietLap.setForeground(new java.awt.Color(255, 255, 255));
        lblThietLap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_admin_settings_male_25px.png"))); // NOI18N
        lblThietLap.setText("   Thông tin");

        javax.swing.GroupLayout btnThietLapLayout = new javax.swing.GroupLayout(btnThietLap);
        btnThietLap.setLayout(btnThietLapLayout);
        btnThietLapLayout.setHorizontalGroup(
            btnThietLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThietLapLayout.createSequentialGroup()
                .addComponent(pan_btnThietLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lblThietLap)
                .addGap(0, 43, Short.MAX_VALUE))
        );
        btnThietLapLayout.setVerticalGroup(
            btnThietLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pan_btnThietLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblThietLap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panMenu.add(btnThietLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 170, -1));

        lblIconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_user_male_circle_100px_1.png"))); // NOI18N
        panMenu.add(lblIconUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 60, 90, 90));

        lblHello.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        lblHello.setForeground(new java.awt.Color(255, 255, 255));
        lblHello.setText("Hello User");
        panMenu.add(lblHello, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        getContentPane().add(panMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 700));

        panBackgound.setBackground(new java.awt.Color(255, 255, 255));
        panBackgound.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panQuanLy.setBackground(new java.awt.Color(255, 255, 255));
        panQuanLy.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBannerTitleQuanLy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerQuanLi.jpg"))); // NOI18N
        panQuanLy.add(lblBannerTitleQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 134));

        tbpQuanLi.setBackground(new java.awt.Color(255, 255, 255));
        tbpQuanLi.setForeground(new java.awt.Color(23, 35, 51));
        tbpQuanLi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        panQuanLyDoUong.setBackground(new java.awt.Color(255, 255, 255));
        panQuanLyDoUong.setForeground(new java.awt.Color(255, 255, 255));

        txtTimMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimMon.setForeground(new java.awt.Color(23, 35, 51));

        tblQuanLyDoUong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã đồ uống", "Tên đồ uống", "Đơn giá"
            }
        ));
        tblQuanLyDoUong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuanLyDoUongMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblQuanLyDoUongMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tblQuanLyDoUong);

        btnXoaMon.setBackground(new java.awt.Color(23, 35, 51));
        btnXoaMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoaMon.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_delete_25px.png"))); // NOI18N
        btnXoaMon.setText("  Xoá");
        btnXoaMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMonActionPerformed(evt);
            }
        });

        btnThemMon.setBackground(new java.awt.Color(23, 35, 51));
        btnThemMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThemMon.setForeground(new java.awt.Color(255, 255, 255));
        btnThemMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_add_25px.png"))); // NOI18N
        btnThemMon.setText("  Thêm");
        btnThemMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMonActionPerformed(evt);
            }
        });

        btnSuaMon.setBackground(new java.awt.Color(23, 35, 51));
        btnSuaMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSuaMon.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_update_tag_25px.png"))); // NOI18N
        btnSuaMon.setText("  Sửa");
        btnSuaMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(23, 35, 51));
        jLabel1.setText("Tìm món:");

        jSeparator1.setBackground(new java.awt.Color(23, 35, 51));

        jButton1.setBackground(new java.awt.Color(23, 35, 51));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_search_25px.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panQuanLyDoUongLayout = new javax.swing.GroupLayout(panQuanLyDoUong);
        panQuanLyDoUong.setLayout(panQuanLyDoUongLayout);
        panQuanLyDoUongLayout.setHorizontalGroup(
            panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemMon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(txtTimMon))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        panQuanLyDoUongLayout.setVerticalGroup(
            panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panQuanLyDoUongLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panQuanLyDoUongLayout.createSequentialGroup()
                                .addComponent(txtTimMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)))
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addComponent(btnThemMon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnSuaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnXoaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tbpQuanLi.addTab("Quản lí đồ uống", panQuanLyDoUong);

        panQuanLyBan.setBackground(new java.awt.Color(255, 255, 255));
        panQuanLyBan.setForeground(new java.awt.Color(255, 255, 255));

        txtTimBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimBan.setForeground(new java.awt.Color(23, 35, 51));

        tblQuanLyBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã bàn", "Tên bàn"
            }
        ));
        jScrollPane3.setViewportView(tblQuanLyBan);

        btnXoaBan.setBackground(new java.awt.Color(23, 35, 51));
        btnXoaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoaBan.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_delete_25px.png"))); // NOI18N
        btnXoaBan.setText("  Xoá");

        btnThemBan.setBackground(new java.awt.Color(23, 35, 51));
        btnThemBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThemBan.setForeground(new java.awt.Color(255, 255, 255));
        btnThemBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_add_25px.png"))); // NOI18N
        btnThemBan.setText("  Thêm");
        btnThemBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemBanActionPerformed(evt);
            }
        });

        btnSuaBan.setBackground(new java.awt.Color(23, 35, 51));
        btnSuaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSuaBan.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_update_tag_25px.png"))); // NOI18N
        btnSuaBan.setText("  Sửa");
        btnSuaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaBanActionPerformed(evt);
            }
        });

        lblTimBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTimBan.setForeground(new java.awt.Color(23, 35, 51));
        lblTimBan.setText("Tìm bàn:");

        sptTimBan.setBackground(new java.awt.Color(23, 35, 51));

        btnTimBan.setBackground(new java.awt.Color(23, 35, 51));
        btnTimBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_search_25px.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(23, 35, 51));
        jLabel2.setText("Có ... bàn");

        javax.swing.GroupLayout panQuanLyBanLayout = new javax.swing.GroupLayout(panQuanLyBan);
        panQuanLyBan.setLayout(panQuanLyBanLayout);
        panQuanLyBanLayout.setHorizontalGroup(
            panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyBanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panQuanLyBanLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThemBan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSuaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panQuanLyBanLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel2))))
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addComponent(lblTimBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sptTimBan, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(txtTimBan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimBan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        panQuanLyBanLayout.setVerticalGroup(
            panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyBanLayout.createSequentialGroup()
                .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblTimBan)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panQuanLyBanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTimBan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panQuanLyBanLayout.createSequentialGroup()
                                .addComponent(txtTimBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sptTimBan, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)))
                .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addComponent(btnThemBan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnSuaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnXoaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tbpQuanLi.addTab("Quản lý bàn", panQuanLyBan);

        panQuanLyTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));

        txtTimTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimTaiKhoan.setForeground(new java.awt.Color(23, 35, 51));

        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên đăng nhập", "Mật khẩu", "Vai trò"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblTaiKhoan);

        btnXoaTK.setBackground(new java.awt.Color(23, 35, 51));
        btnXoaTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoaTK.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_delete_25px.png"))); // NOI18N
        btnXoaTK.setText("  Xoá");

        btnThemTK.setBackground(new java.awt.Color(23, 35, 51));
        btnThemTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThemTK.setForeground(new java.awt.Color(255, 255, 255));
        btnThemTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_add_25px.png"))); // NOI18N
        btnThemTK.setText("  Thêm");
        btnThemTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTKActionPerformed(evt);
            }
        });

        btnSuaTK.setBackground(new java.awt.Color(23, 35, 51));
        btnSuaTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSuaTK.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_update_tag_25px.png"))); // NOI18N
        btnSuaTK.setText("  Sửa");
        btnSuaTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTKActionPerformed(evt);
            }
        });

        lblTimTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTimTaiKhoan.setForeground(new java.awt.Color(23, 35, 51));
        lblTimTaiKhoan.setText("Tìm tài khoản:");

        sptTimTaiKhoan.setBackground(new java.awt.Color(23, 35, 51));

        btnTimTaiKhoan.setBackground(new java.awt.Color(23, 35, 51));
        btnTimTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_search_25px.png"))); // NOI18N

        javax.swing.GroupLayout panQuanLyTaiKhoanLayout = new javax.swing.GroupLayout(panQuanLyTaiKhoan);
        panQuanLyTaiKhoan.setLayout(panQuanLyTaiKhoanLayout);
        panQuanLyTaiKhoanLayout.setHorizontalGroup(
            panQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyTaiKhoanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyTaiKhoanLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addGroup(panQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemTK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panQuanLyTaiKhoanLayout.createSequentialGroup()
                        .addComponent(lblTimTaiKhoan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sptTimTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(txtTimTaiKhoan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        panQuanLyTaiKhoanLayout.setVerticalGroup(
            panQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyTaiKhoanLayout.createSequentialGroup()
                .addGroup(panQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyTaiKhoanLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblTimTaiKhoan)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panQuanLyTaiKhoanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panQuanLyTaiKhoanLayout.createSequentialGroup()
                                .addComponent(txtTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sptTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)))
                .addGroup(panQuanLyTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyTaiKhoanLayout.createSequentialGroup()
                        .addComponent(btnThemTK, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnSuaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnXoaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tbpQuanLi.addTab("Quản lý tài khoản", panQuanLyTaiKhoan);

        panQuanLy.add(tbpQuanLi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 1040, 540));

        panBackgound.add(panQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -4, 1090, 700));

        panBanHang.setBackground(new java.awt.Color(255, 255, 255));
        panBanHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBannerTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerDanhMuc.jpg"))); // NOI18N
        panBanHang.add(lblBannerTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 134));

        panGoiMon.setBackground(new java.awt.Color(255, 255, 255));
        panGoiMon.setForeground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblGoiMon.setForeground(new java.awt.Color(23, 35, 51));
        tblGoiMon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã món", "Tên món", "Danh mục", "Đơn giá", "Bàn "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblGoiMon.setToolTipText("");
        tblGoiMon.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tblGoiMon);

        lblTenNhanVien.setBackground(new java.awt.Color(23, 35, 51));
        lblTenNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenNhanVien.setForeground(new java.awt.Color(23, 35, 51));
        lblTenNhanVien.setText("Tên nhân viên:");

        sptTenNhanVien.setBackground(new java.awt.Color(23, 35, 51));
        sptTenNhanVien.setForeground(new java.awt.Color(23, 35, 51));

        txtTenNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenNhanVien.setForeground(new java.awt.Color(23, 35, 51));
        txtTenNhanVien.setEnabled(false);

        lblTenDoUong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenDoUong.setForeground(new java.awt.Color(23, 35, 51));
        lblTenDoUong.setText("Tên đồ uống:");

        cmbTenDoUong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbTenDoUong.setForeground(new java.awt.Color(23, 35, 51));
        cmbTenDoUong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cmbTenDoUong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTenDoUongActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(23, 35, 51));
        btnThem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setLabel("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        panThanhToan.setBackground(new java.awt.Color(23, 35, 51));
        panThanhToan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("THANH TOÁN");

        javax.swing.GroupLayout panThanhToanLayout = new javax.swing.GroupLayout(panThanhToan);
        panThanhToan.setLayout(panThanhToanLayout);
        panThanhToanLayout.setHorizontalGroup(
            panThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 183, Short.MAX_VALUE)
            .addGroup(panThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panThanhToanLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panThanhToanLayout.setVerticalGroup(
            panThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(panThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panThanhToanLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        lblTongCong.setBackground(new java.awt.Color(23, 35, 51));
        lblTongCong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongCong.setForeground(new java.awt.Color(23, 35, 51));
        lblTongCong.setText("Tổng cộng:");

        sptTongCong.setBackground(new java.awt.Color(23, 35, 51));
        sptTongCong.setForeground(new java.awt.Color(23, 35, 51));

        txtTongCong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTongCong.setForeground(new java.awt.Color(23, 35, 51));

        sptGia.setBackground(new java.awt.Color(23, 35, 51));
        sptGia.setForeground(new java.awt.Color(23, 35, 51));

        txtGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGia.setForeground(new java.awt.Color(23, 35, 51));

        lbltienkhachdua.setBackground(new java.awt.Color(23, 35, 51));
        lbltienkhachdua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbltienkhachdua.setForeground(new java.awt.Color(23, 35, 51));
        lbltienkhachdua.setText("Tiền khách đưa");

        txtTienThoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTienThoi.setForeground(new java.awt.Color(23, 35, 51));

        sptTienThoi.setBackground(new java.awt.Color(23, 35, 51));
        sptTienThoi.setForeground(new java.awt.Color(23, 35, 51));

        lblTienThoi.setBackground(new java.awt.Color(23, 35, 51));
        lblTienThoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTienThoi.setForeground(new java.awt.Color(23, 35, 51));
        lblTienThoi.setText("Tiền thối:");

        btnLuu.setBackground(new java.awt.Color(23, 35, 51));
        btnLuu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setLabel("Thanh Toán");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(23, 35, 51));
        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setLabel("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        lblBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBan.setForeground(new java.awt.Color(23, 35, 51));
        lblBan.setText("Chọn bàn:");

        cmbBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbBan.setForeground(new java.awt.Color(23, 35, 51));
        cmbBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel7.setText("ID Nhân VIên");

        jLabel11.setText("ID HÓA ĐƠN");

        javax.swing.GroupLayout panGoiMonLayout = new javax.swing.GroupLayout(panGoiMon);
        panGoiMon.setLayout(panGoiMonLayout);
        panGoiMonLayout.setHorizontalGroup(
            panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panGoiMonLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panGoiMonLayout.createSequentialGroup()
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenNhanVien)
                            .addComponent(lblTenDoUong)
                            .addComponent(lblBan))
                        .addGap(10, 10, 10)
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sptTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTenDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbBan, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtidnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(109, 109, 109)
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addComponent(lblTongCong)
                                .addGap(10, 10, 10)
                                .addComponent(txtTongCong, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sptGia, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sptTongCong, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sptTienThoi, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTienThoi, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panGoiMonLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(lblTienThoi)
                                .addGap(170, 170, 170)
                                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panGoiMonLayout.createSequentialGroup()
                        .addComponent(lbltienkhachdua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        panGoiMonLayout.setVerticalGroup(
            panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panGoiMonLayout.createSequentialGroup()
                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panGoiMonLayout.createSequentialGroup()
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(panThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panGoiMonLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(lblTongCong))
                                    .addComponent(txtTongCong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addComponent(sptTongCong, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtidnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panGoiMonLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sptTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(cmbTenDoUong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panGoiMonLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16)
                                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(15, 15, 15))
                    .addGroup(panGoiMonLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblTenNhanVien)
                        .addGap(50, 50, 50)
                        .addComponent(lblTenDoUong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBan)
                            .addComponent(cmbBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)))
                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panGoiMonLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panGoiMonLayout.createSequentialGroup()
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTienThoi)
                                    .addComponent(txtTienThoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panGoiMonLayout.createSequentialGroup()
                                .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbltienkhachdua))
                                .addGap(6, 6, 6)
                                .addComponent(sptGia, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(sptTienThoi, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(panGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        panBanHang.add(panGoiMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        panBackgound.add(panBanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -4, 1080, 700));

        panThongKe.setBackground(new java.awt.Color(255, 255, 255));
        panThongKe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBannerThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerThongKe.jpg"))); // NOI18N
        panThongKe.add(lblBannerThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 134));

        tblthongke.setForeground(new java.awt.Color(23, 35, 51));
        tblthongke.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hoá đơn", "Mã bàn", "Mã nhân viên", "Ngày lập", "Trạng thái", "Tổng tiền"
            }
        ));
        jScrollPane5.setViewportView(tblthongke);

        panThongKe.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 1005, 340));

        lblThongKeHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblThongKeHoaDon.setForeground(new java.awt.Color(23, 35, 51));
        lblThongKeHoaDon.setText("Thống kê hoá đơn");
        panThongKe.add(lblThongKeHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 166, -1, -1));

        btnThongKeHD.setBackground(new java.awt.Color(23, 35, 51));
        btnThongKeHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThongKeHD.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKeHD.setText("Thống kê");
        btnThongKeHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeHDActionPerformed(evt);
            }
        });
        panThongKe.add(btnThongKeHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 160, 90, 30));

        lblTongSoHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongSoHD.setForeground(new java.awt.Color(23, 35, 51));
        lblTongSoHD.setText("Tổng số hoá đơn:");
        panThongKe.add(lblTongSoHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, -1, -1));

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(23, 35, 51));
        lblTongTien.setText("Tổng tiền:");
        panThongKe.add(lblTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 620, -1, -1));

        txtngaytk.setText("2019-11-17");
        txtngaytk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtngaytkActionPerformed(evt);
            }
        });
        panThongKe.add(txtngaytk, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 120, 30));

        jLabel12.setText("Ngày");
        panThongKe.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 164, 40, 20));

        btntongtk.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btntongtk.setText("Tổng");
        btntongtk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntongtkActionPerformed(evt);
            }
        });
        panThongKe.add(btntongtk, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 590, -1, -1));

        panBackgound.add(panThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -4, 1080, 700));

        panThietLap.setBackground(new java.awt.Color(255, 255, 255));
        panThietLap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBannerTitle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerThietLap.jpg"))); // NOI18N
        panThietLap.add(lblBannerTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 134));

        btnDangXuat.setBackground(new java.awt.Color(23, 35, 51));
        btnDangXuat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_export_24px.png"))); // NOI18N
        btnDangXuat.setText("  Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        panThietLap.add(btnDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 630, 150, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1035, 400));

        jLabel16.setText("Có thắc mắc vui lòng liên hệ theo hotline: 0123466789");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 51));
        jLabel17.setText("Coffee Manage Professional");

        jLabel18.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel18.setText("Phiên bản 1.0.0.1.0.0.10.10.10.");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 0, 51));
        jLabel19.setText("Được phát triển bởi VCL Group");

        jLabel5.setText("   Phần mềm được phát triển dựa trên mã nguồn mở Java. Được phát hành có phí thông qua tập đoàn VCL Group ");

        jLabel6.setText("Trường hợp sử dụng phần mềm không có giấy phép(crack, hack,...) sẽ bị khởi tố.");

        jLabel8.setText("Copyright © 2019 VCL Group. ltd. all rights reserved ");

        jLabel9.setText("Website: http://www.vclgroup.com");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel16)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(jLabel5)))
                        .addGap(43, 43, 43))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(761, Short.MAX_VALUE)
                        .addComponent(jLabel8)))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel18))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        panThietLap.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        panBackgound.add(panThietLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -4, 1080, 700));

        getContentPane().add(panBackgound, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 1070, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBanHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBanHangMousePressed
        BanHang();
    }//GEN-LAST:event_btnBanHangMousePressed

    private void btnQuanLyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuanLyMousePressed
        QuanLy();
    }//GEN-LAST:event_btnQuanLyMousePressed

    private void btnThietLapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThietLapMousePressed
        ThiepLap();
    }//GEN-LAST:event_btnThietLapMousePressed

    private void btnThongKeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMousePressed
        ThongKe();
    }//GEN-LAST:event_btnThongKeMousePressed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblGoiMon.getModel();
        model.setRowCount(0);
        cmbTenDoUong.setSelectedIndex(0);
        txtTongCong.setText("");
        cmbTenDoUong.setSelectedIndex(0);
        txtTongCong.setText("");
        txtGia.setText("");
        txtTienThoi.setText("");
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        selectItem();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        this.dispose();
        new LoginJFrame().setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnThemMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMonActionPerformed
        new AddProductJFrame().setVisible(true);
    }//GEN-LAST:event_btnThemMonActionPerformed

    private void btnSuaTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTKActionPerformed
        new ChangePasswordJFrame().setVisible(true);
    }//GEN-LAST:event_btnSuaTKActionPerformed

    private void btnSuaMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMonActionPerformed
        new UpdateProduct().setVisible(true);
    }//GEN-LAST:event_btnSuaMonActionPerformed

    private void btnThemTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTKActionPerformed
        new AddAccountJFrame().setVisible(true);
    }//GEN-LAST:event_btnThemTKActionPerformed

    private void btnThemBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemBanActionPerformed
        new AddTableJFrame().setVisible(true);
    }//GEN-LAST:event_btnThemBanActionPerformed

    private void btnSuaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaBanActionPerformed
        new UpdateTableJFrame().setVisible(true);
    }//GEN-LAST:event_btnSuaBanActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        repay();
        SaveAndPrintBill();
        try {
            String sql = "SELECT IDHOADON FROM HOADON WHERE IDBAN=? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, (String) cmbBan.getSelectedItem());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cmbhoadon.addItem(rs.getString("iDHoaDon"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        saveprintdetail();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void cmbTenDoUongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTenDoUongActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbTenDoUongActionPerformed

    private void btnThongKeHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeHDActionPerformed
        selectstatistic();
    }//GEN-LAST:event_btnThongKeHDActionPerformed

    private void txtngaytkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtngaytkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtngaytkActionPerformed

    private void btntongtkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntongtkActionPerformed
        // TODO add your handling code here:
        totalstastistic();
    }//GEN-LAST:event_btntongtkActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         DefaultTableModel model = (DefaultTableModel) tblQuanLyDoUong.getModel();
        model2.setRowCount(0);
        timkiemmon();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblQuanLyDoUongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLyDoUongMouseClicked

        index = tblQuanLyDoUong.getSelectedRow();
       this.clicktable(index);
       new UpdateProduct().setVisible(true);
      
    }//GEN-LAST:event_tblQuanLyDoUongMouseClicked

    private void tblQuanLyDoUongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLyDoUongMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblQuanLyDoUongMouseEntered

    private void btnXoaMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMonActionPerformed
        // TODO add your handling code here:
         DefaultTableModel model = (DefaultTableModel) tblQuanLyDoUong.getModel();
        model2.setRowCount(0);
    }//GEN-LAST:event_btnXoaMonActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnBanHang;
    private javax.swing.JButton btnDangXuat;
    private java.awt.Button btnLuu;
    private javax.swing.JPanel btnQuanLy;
    private javax.swing.JButton btnSuaBan;
    private javax.swing.JButton btnSuaMon;
    private javax.swing.JButton btnSuaTK;
    private java.awt.Button btnThem;
    private javax.swing.JButton btnThemBan;
    private javax.swing.JButton btnThemMon;
    private javax.swing.JButton btnThemTK;
    private javax.swing.JPanel btnThietLap;
    private javax.swing.JPanel btnThongKe;
    private javax.swing.JButton btnThongKeHD;
    private javax.swing.JButton btnTimBan;
    private javax.swing.JButton btnTimTaiKhoan;
    private java.awt.Button btnXoa;
    private javax.swing.JButton btnXoaBan;
    private javax.swing.JButton btnXoaMon;
    private javax.swing.JButton btnXoaTK;
    private javax.swing.JButton btntongtk;
    private javax.swing.JComboBox<String> cmbBan;
    private javax.swing.JComboBox<String> cmbTenDoUong;
    private javax.swing.JComboBox<String> cmbhoadon;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblBan;
    private javax.swing.JLabel lblBanHang;
    private javax.swing.JLabel lblBannerThongKe;
    private javax.swing.JLabel lblBannerTitle;
    private javax.swing.JLabel lblBannerTitle1;
    private javax.swing.JLabel lblBannerTitleQuanLy;
    private javax.swing.JLabel lblHello;
    private javax.swing.JLabel lblIconUser;
    private javax.swing.JLabel lblQuanLy;
    private javax.swing.JLabel lblTenDoUong;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblThietLap;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblThongKeHoaDon;
    private javax.swing.JLabel lblTienThoi;
    private javax.swing.JLabel lblTimBan;
    private javax.swing.JLabel lblTimTaiKhoan;
    private javax.swing.JLabel lblTongCong;
    private javax.swing.JLabel lblTongSoHD;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lbltienkhachdua;
    private javax.swing.JPanel panBackgound;
    private javax.swing.JPanel panBanHang;
    private javax.swing.JPanel panGoiMon;
    private javax.swing.JPanel panMenu;
    private javax.swing.JPanel panQuanLy;
    private javax.swing.JPanel panQuanLyBan;
    private javax.swing.JPanel panQuanLyDoUong;
    private javax.swing.JPanel panQuanLyTaiKhoan;
    private javax.swing.JPanel panThanhToan;
    private javax.swing.JPanel panThietLap;
    private javax.swing.JPanel panThongKe;
    private javax.swing.JPanel pan_btnBanHang;
    private javax.swing.JPanel pan_btnQuanLy;
    private javax.swing.JPanel pan_btnThietLap;
    private javax.swing.JPanel pan_btnThongKe;
    private javax.swing.JSeparator sptGia;
    private javax.swing.JSeparator sptTenNhanVien;
    private javax.swing.JSeparator sptTienThoi;
    private javax.swing.JSeparator sptTimBan;
    private javax.swing.JSeparator sptTimTaiKhoan;
    private javax.swing.JSeparator sptTongCong;
    private javax.swing.JTable tblGoiMon;
    private javax.swing.JTable tblQuanLyBan;
    public static javax.swing.JTable tblQuanLyDoUong;
    private javax.swing.JTable tblTaiKhoan;
    private javax.swing.JTable tblthongke;
    private javax.swing.JTabbedPane tbpQuanLi;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTienThoi;
    private javax.swing.JTextField txtTimBan;
    private javax.swing.JTextField txtTimMon;
    private javax.swing.JTextField txtTimTaiKhoan;
    private javax.swing.JTextField txtTongCong;
    private javax.swing.JTextField txtidnhanvien;
    private javax.swing.JTextField txtngaytk;
    // End of variables declaration//GEN-END:variables
}
