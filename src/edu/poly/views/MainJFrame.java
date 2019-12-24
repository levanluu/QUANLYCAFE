package edu.poly.views;

import edu.poly.models.HoaDon;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import java.time.Year;
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

    ArrayList<HoaDon> list = new ArrayList<>();
    DefaultTableModel model1, model2, model3, model4, model5, model, model6, model7;
    private Connection conn;
    int index = 0;
    NumberFormat formatter = new DecimalFormat("#,###");
    SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    PreparedStatement ps;

    public MainJFrame() {
        initComponents();

        model1 = (DefaultTableModel) tblthongke.getModel();
        model2 = (DefaultTableModel) tblQuanLyDoUong.getModel();
        model3 = (DefaultTableModel) tblQuanLyBan.getModel();
        model4 = (DefaultTableModel) tblTaiKhoan.getModel();
        model5 = (DefaultTableModel) tblNV.getModel();
        model = (DefaultTableModel) tblGoiMon.getModel();
        model6 = (DefaultTableModel) tblsanpham.getModel();
        model7 = (DefaultTableModel) tbltknhanvien.getModel();
        setLocationRelativeTo(null);

        //Kết nối database;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PROJECT_JAVA1;", "JAVA", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Phanv\\"
                + "OneDrive\\Documents\\NetBeansProjects\\QuanLyCafe_1\\src\\edu\\poly\\image\\logo_title.png"));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Set màu hiển thị cho menu gọi món
        setColor(btnGoiMon);
        pan_btnGoiMon.setOpaque(true);
        resetColor(new JPanel[]{btnThongTin, btnQuanLy, btnThongKe}, new JPanel[]{pan_btnThongTin, pan_btnQuanLy, pan_btnThongKe});

        //Set màu title bảng
        tblGoiMon.getTableHeader().setBackground(Color.WHITE);
        tblQuanLyDoUong.getTableHeader().setBackground(Color.WHITE);
        tblQuanLyBan.getTableHeader().setBackground(Color.WHITE);
        tblTaiKhoan.getTableHeader().setBackground(Color.WHITE);
        //  tblthongke.getTableHeader().setBackground(Color.WHITE);

        //Ẩn hiện các panel
        tbpQuanLi.setVisible(false);
        panThongKe.setVisible(false);
        panThietLap.setVisible(false);

        //hiển thị username lên form MainJFrame bằng với tên đăng nhập;
        txtTenNhanVien.setText(LoginJFrame.txtUsername.getText());
        lblHello.setText(LoginJFrame.txtUsername.getText());
        // hiển thị id nhân viên lên txtidnhanvien
        runstart();
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

    public void runstart() {
        cmbTenDoUong.removeAllItems();
        cmbTenDoUong.addItem(" ");
        cmbBan.removeAllItems();
        cmbBan.addItem(" ");
        //hiển thị combbox;
        loadDataToCbo();
        //hiển thị combbox bàn
        loadBan();
        idnhanvien();
        hienthimonQL();
        hienthibanQL();
        hienthiNHANVIENQL();
        hienthithongtinnhanvien();
        loadNV();

    }

    // thêm vào bảng từ combbox của bàn và đồ uống ;
    public void selectItem() {
        if (cmbTenDoUong.getSelectedItem().equals(" ") && cmbBan.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(this, "Chưa chọn món & bàn");
        } else if (cmbTenDoUong.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(this, "Chưa chọn món");
        } else if (cmbBan.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(this, "Chưa chọn bàn");
        } else {
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
        }

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
                cmbthongkesanpham.addItem(rs.getString("TENDOUONG"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadNV() {
        try {
            String sql = "SELECT TENDANGNHAP FROM TAIKHOAN";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                cmbnhanvien.addItem(rs.getString("TENDANGNHAP"));

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

            String path = "C:\\Users\\Phanv\\OneDrive\\Desktop\\QuanLyCafe_1 2345\\QuanLyCafe_1\\billcustomer.txt";
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            Date now = new Date();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("\t\t\tVCL COFFEE\r\n\r\n");
            bw.write("\t\t137 Nguyễn Thị Thập\r\n");
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
            FileReader fr = new FileReader("C:\\Users\\Phanv\\OneDrive\\Desktop\\QuanLyCafe_1 2345\\QuanLyCafe_1\\billcustomer.txt");
            BufferedReader br = new BufferedReader(fr);

            int i;
            while ((i = br.read()) != -1) {
                System.out.print((char) i);
            }
            br.close();
            fr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectstatistic() {

        try {
            Date date = txtdate.getDate();
            String dateinput = new SimpleDateFormat("yyyy-MM-dd").format(date);
            System.out.println(dateinput);
            String sql = "DECLARE @RC int\n"
                    + "DECLARE @DATE date\n"
                    + "\n"
                    + "-- TODO: Set parameter values here.\n"
                    + "\n"
                    + "EXECUTE @RC = [dbo].[sp_ThongKeDoanhThu] \n"
                    + "?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dateinput);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("tendouong"));
                row.add(rs.getString("tennhanvien"));
                row.add(rs.getString("ngaylap"));
                row.add(rs.getString("tongtien"));

                model1.addRow(row);

            }
            tblthongke.setModel(model1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doanhthusanpham() {
        try {
            Date date = datemonth.getDate();
            String month = new SimpleDateFormat("MM").format(date);
            String year = new SimpleDateFormat("yyyy").format(date);
            String chooser = (String) cmbthongkesanpham.getSelectedItem();

            String sql = "DECLARE @RC int\n"
                    + "DECLARE @sanpham nvarchar(50)\n"
                    + "DECLARE @month int\n"
                    + "DECLARE @YEAR int\n"
                    + "\n"
                    + "-- TODO: Set parameter values here.\n"
                    + "\n"
                    + "EXECUTE @RC = [dbo].[sp_ThongKesanpham] \n"
                    + "?,?,?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chooser);
            ps.setString(2, month);
            ps.setString(3, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("tendouong"));
                row.add(rs.getString("ngaylap"));
                row.add(rs.getString("tennhanvien"));
                model6.addRow(row);
            }
            tblsanpham.setModel(model6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doanhthunhanvien() {
        try {
            Date date = dateday.getDate();
            String day = new SimpleDateFormat("dd").format(date);
            String chooserNV = (String) cmbnhanvien.getSelectedItem();

            String sql = "DECLARE @RC int\n"
                    + "DECLARE @NV nvarchar(50)\n"
                    + "DECLARE @day int\n"
                    + "\n"
                    + "-- TODO: Set parameter values here.\n"
                    + "\n"
                    + "EXECUTE @RC = [dbo].[sp_ThongKenhanvien] \n"
                    + "?,?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chooserNV);
            ps.setString(2, day);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("tendouong"));
                row.add(rs.getString("tendangnhap"));
                row.add(rs.getString("ngaylap"));
                row.add(rs.getString("tongtien"));
                model7.addRow(row);
            }
            tbltknhanvien.setModel(model7);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalstastistic() {
        float sum = 0;
        for (int i = 0; i < tblthongke.getRowCount(); i++) {
            sum = sum + Float.parseFloat(tblthongke.getValueAt(i, 3).toString());
        }
        lbltongdoanhthu.setText("Tổng Doanh Thu: " + sum);
    }

    public void tongsoluong() {

        lbltongsanpham.setText("Tổng sản phẩm: " + tblsanpham.getRowCount());
    }

    public void tongtiennhanvien() {
        float sum = 0;
        for (int i = 0; i < tbltknhanvien.getRowCount(); i++) {
            sum = sum + Float.parseFloat(tbltknhanvien.getValueAt(i, 3).toString());
        }
        lbltongtiennhanvien.setText("Tổng tiền nhân viên : " + cmbnhanvien.getSelectedItem() + " là " + sum);
    }

    public void hienthimonQL() {
        try {
            model2.setRowCount(0);
            String sql = "SELECT IDDOUONG ,TENDOUONG,DONGIA FROM DOUONG ";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
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

    public void timkiemmon() {
        try {
            String sql = "select IDDOUONG,TENDOUONG,DONGIA FROM DOUONG WHERE TENDOUONG=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtTimMon.getText());
            ResultSet rs = ps.executeQuery();
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

    public void clicktable(int index) {
        txtmadouong.setText((tblQuanLyDoUong.getValueAt(index, 0)).toString());
        txttendouong.setText((tblQuanLyDoUong.getValueAt(index, 1)).toString());
        txtdongia.setText(tblQuanLyDoUong.getValueAt(index, 2).toString());
        txtdanhmuc.setText("2");

    }

    public void clicktableBAN(int index) {
        txtmaban.setText((tblQuanLyBan.getValueAt(index, 0)).toString());
        txttenban.setText((tblQuanLyBan.getValueAt(index, 1)).toString());
    }

    public void clicktablenhanvien(int index) {
        txtdangnhap.setText((tblTaiKhoan.getValueAt(index, 0)).toString());
        txtmatkhau.setText((tblTaiKhoan.getValueAt(index, 1)).toString());
        txtvaitro.setText((tblTaiKhoan.getValueAt(index, 2)).toString());
        txtidnv.setText((tblTaiKhoan.getValueAt(index, 3)).toString());
    }

    public void clickhienthiNV(int index) {
        txttennhanvien.setText((tblNV.getValueAt(index, 0)).toString());
        txtiddangnhap.setText((tblNV.getValueAt(index, 1)).toString());
        txtngaysinh.setText((tblNV.getValueAt(index, 2)).toString());
        txtdt.setText((tblNV.getValueAt(index, 3)).toString());
        cmbgt.setSelectedItem((tblNV.getValueAt(index, 4)).toString());

    }

    public void update() {
        if (txtmadouong.getText().equals("") || txttendouong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "không được để trống ");
        } else {
            try {
                String tenduong = txtmadouong.getText();
                String sql = "UPDATE DOUONG SET TENDOUONG=?,DONGIA=? WHERE IDDOUONG='" + txtmadouong.getText() + "'";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txttendouong.getText());
                ps.setString(2, txtdongia.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "cập nhật thành công");

            } catch (Exception e) {
                e.printStackTrace();
            }
            hienthimonQL();

        }
    }

    public void deletemon() {
        try {
            String ma = txtmadouong.getText();
            String sql = "DELETE FROM DOUONG WHERE IDDOUONG = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void xoatrangmon() {
        txtmadouong.setText("");
        txtdanhmuc.setText("");
        txttendouong.setText("");
        txtdongia.setText("");
    }

    public void xoatrangban() {
        txtmaban.setText("");
        txttenban.setText("");

    }

    public void hienthibanQL() {
        try {
            model3.setRowCount(0);
            String sql = "SELECT IDBAN ,TENBAN FROM BAN ";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(Integer.parseInt(rs.getString("iDBAN")));
                row.add(rs.getString("tenBan"));

                model3.addRow(row);
            }
            tblQuanLyBan.setModel(model3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateban() {
        if (txttenban.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "không được để trống ");
        } else {
            try {
                String tenduong = txtmadouong.getText();
                String sql = "UPDATE BAN SET TENBAN=?,TRANGTHAI=? WHERE IDBAN='" + txtmaban.getText() + "'";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txttenban.getText());
                ps.setString(2, "Trống");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "cập nhật thành công");

            } catch (Exception e) {
                e.printStackTrace();
            }
            hienthimonQL();

        }
    }

    public void deleteban() {
        try {
            String ma = txtmaban.getText();
            String sql = "DELETE FROM BAN WHERE IDBAN = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void hienthiNHANVIENQL() {
        try {
            model4.setRowCount(0);
            String sql = "SELECT TENDANGNHAP ,MATKHAU,VAITRO,IDNHANVIEN FROM TAIKHOAN ";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("tenDangnhap"));
                row.add(rs.getString("matKhau"));
                row.add(rs.getString("vaiTro"));
                row.add(rs.getString("idnhanvien"));

                model4.addRow(row);
            }
            tblTaiKhoan.setModel(model4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatetaikhoan() {
        if (txtdangnhap.getText().equals("") || txtmatkhau.getText().equals("") || txtvaitro.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "vui lòng điền đầy đủ thông tin");
        } else {

            try {

                String sql = "UPDATE TAIKHOAN SET TENDANGNHAP=?,MATKHAU=?,VAITRO=? WHERE TENDANGNHAP='" + txtdangnhap.getText() + "'";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtdangnhap.getText());
                ps.setInt(2, Integer.parseInt(txtmatkhau.getText()));
                ps.setString(3, txtvaitro.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, " Sữa thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void deletetaikhoan() {
        try {
            String ma = txtdangnhap.getText();
            String sql = "DELETE FROM TAIKHOAN WHERE TENDANGNHAP =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void hienthithongtinnhanvien() {
        try {
            model5.setRowCount(0);
            String sql = "SELECT*FROM NHANVIEN";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("tenNhanvien"));
                row.add(rs.getString("iDNhanVien"));
                row.add(rs.getString("ngaySinh"));
                row.add(Integer.parseInt(rs.getString("sdt")));
                row.add(rs.getString("gioiTinh"));
                model5.addRow(row);
            }
            tblNV.setModel(model5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatenhanvien() {
        if (txttennhanvien.getText().equals("") || txtngaysinh.getText().equals("") || txtdt.getText().equals("") || cmbgt.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(this, "vui lòng điền đầy đủ thông tin");
        } else {

            try {
                 
                String ngaysinh = txtngaysinh.getText();
                DateFormat df = new SimpleDateFormat("yyyy-dd-MM");
                Date date = df.parse(ngaysinh);
                String sql = "UPDATE NHANVIEN SET TENNHANVIEN=?,NGAYSINH=?,SDT=?,GIOITINH=? WHERE IDNHANVIEN='" + txtiddangnhap.getText() + "'";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txttennhanvien.getText());
                ps.setString(2, df.format(date));
                ps.setInt(3, Integer.parseInt(txtdt.getText()));
                ps.setInt(4, cmbgt.getSelectedIndex());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, " Sữa thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        hienthithongtinnhanvien();

    }

    public void deletenhanvien() {
        try {
            String ma = txtidnhanvien.getText();
            String sql = "DELETE FROM NHANVIEN WHERE IDNHANVIEN = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void showThongKe() {
        //  lblTongSoHD.setText("Tổng số hoá đơn: " + Integer.toString(tblthongke.getRowCount()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panMenu = new javax.swing.JPanel();
        lblIconUser = new javax.swing.JLabel();
        btnGoiMon = new javax.swing.JPanel();
        pan_btnGoiMon = new javax.swing.JPanel();
        lblGoiMon = new javax.swing.JLabel();
        btnQuanLy = new javax.swing.JPanel();
        pan_btnQuanLy = new javax.swing.JPanel();
        lblQuanLy = new javax.swing.JLabel();
        btnQuanLyDisable = new javax.swing.JPanel();
        pan_btnQuanLy1 = new javax.swing.JPanel();
        lblQuanLy1 = new javax.swing.JLabel();
        btnThongKe = new javax.swing.JPanel();
        pan_btnThongKe = new javax.swing.JPanel();
        lblThongKe = new javax.swing.JLabel();
        btnThongKeDisable = new javax.swing.JPanel();
        pan_btnThongKe1 = new javax.swing.JPanel();
        lblThongKe1 = new javax.swing.JLabel();
        btnThongTin = new javax.swing.JPanel();
        pan_btnThongTin = new javax.swing.JPanel();
        lblThongTin = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblHello = new javax.swing.JLabel();
        panBackgound = new javax.swing.JPanel();
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
        lblTongCong = new javax.swing.JLabel();
        sptTongCong = new javax.swing.JSeparator();
        txtTongCong = new javax.swing.JTextField();
        lbltienkhachdua = new javax.swing.JLabel();
        txtTienThoi = new javax.swing.JTextField();
        sptTienThoi = new javax.swing.JSeparator();
        lblTienThoi = new javax.swing.JLabel();
        lblBan = new javax.swing.JLabel();
        cmbBan = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cmbhoadon = new javax.swing.JComboBox<>();
        lblTenNhanVien1 = new javax.swing.JLabel();
        txtidnhanvien = new javax.swing.JTextField();
        sptTenNhanVien1 = new javax.swing.JSeparator();
        txtGia = new javax.swing.JTextField();
        sptTongCong1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        btnLuu = new java.awt.Button();
        jButton7 = new javax.swing.JButton();
        panQuanLy = new javax.swing.JPanel();
        lblBannerTitleQuanLy = new javax.swing.JLabel();
        tbpQuanLi = new javax.swing.JTabbedPane();
        panQuanLyDoUong = new javax.swing.JPanel();
        txtTimMon = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQuanLyDoUong = new javax.swing.JTable();
        btnLamMoiMon = new javax.swing.JButton();
        btnThemMon = new javax.swing.JButton();
        btnSuaMon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtmadouong = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txttendouong = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtdongia = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtdanhmuc = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        btnXoaMon = new javax.swing.JButton();
        panQuanLyBan = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblQuanLyBan = new javax.swing.JTable();
        btnXoaBan = new javax.swing.JButton();
        btnThemBan = new javax.swing.JButton();
        btnSuaBan = new javax.swing.JButton();
        lblTimBan = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtmaban = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txttenban = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        btnLamMoiBan = new javax.swing.JButton();
        panQuanLyTaiKhoan = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTaiKhoan = new javax.swing.JTable();
        btnXoaTK = new javax.swing.JButton();
        btnThemTK = new javax.swing.JButton();
        btnSuaTK = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtmatkhau = new javax.swing.JTextField();
        txtdangnhap = new javax.swing.JTextField();
        txtvaitro = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtidnv = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        btnLamMoiAcc = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txttennhanvien = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtiddangnhap = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtngaysinh = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtdt = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        cmbgt = new javax.swing.JComboBox<>();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        btnLamMoiEmp = new javax.swing.JButton();
        panThongKe = new javax.swing.JPanel();
        lblBannerThongKe = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblthongke = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtdate = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        lbltongdoanhthu = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblsanpham = new javax.swing.JTable();
        cmbthongkesanpham = new javax.swing.JComboBox<>();
        btnthongkesanpham = new javax.swing.JButton();
        datemonth = new com.toedter.calendar.JDateChooser();
        lbltongsanpham = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbltknhanvien = new javax.swing.JTable();
        cmbnhanvien = new javax.swing.JComboBox<>();
        dateday = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        lbltongtiennhanvien = new javax.swing.JLabel();
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

        lblIconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_user_male_circle_100px_1.png"))); // NOI18N
        panMenu.add(lblIconUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 60, 90, 90));

        btnGoiMon.setBackground(new java.awt.Color(23, 35, 51));
        btnGoiMon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnGoiMonMousePressed(evt);
            }
        });

        pan_btnGoiMon.setOpaque(false);
        pan_btnGoiMon.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout pan_btnGoiMonLayout = new javax.swing.GroupLayout(pan_btnGoiMon);
        pan_btnGoiMon.setLayout(pan_btnGoiMonLayout);
        pan_btnGoiMonLayout.setHorizontalGroup(
            pan_btnGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        pan_btnGoiMonLayout.setVerticalGroup(
            pan_btnGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblGoiMon.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblGoiMon.setForeground(new java.awt.Color(255, 255, 255));
        lblGoiMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_buy_25px.png"))); // NOI18N
        lblGoiMon.setText("   Gọi món");

        javax.swing.GroupLayout btnGoiMonLayout = new javax.swing.GroupLayout(btnGoiMon);
        btnGoiMon.setLayout(btnGoiMonLayout);
        btnGoiMonLayout.setHorizontalGroup(
            btnGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGoiMonLayout.createSequentialGroup()
                .addComponent(pan_btnGoiMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lblGoiMon)
                .addGap(0, 48, Short.MAX_VALUE))
        );
        btnGoiMonLayout.setVerticalGroup(
            btnGoiMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGoiMonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGoiMon, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pan_btnGoiMon, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        panMenu.add(btnGoiMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 170, 70));

        btnQuanLy.setBackground(new java.awt.Color(23, 35, 51));
        btnQuanLy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQuanLyMousePressed(evt);
            }
        });
        btnQuanLy.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pan_btnQuanLy.setOpaque(false);
        pan_btnQuanLy.setPreferredSize(new java.awt.Dimension(3, 43));

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

        btnQuanLy.add(pan_btnQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 70));

        lblQuanLy.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblQuanLy.setForeground(new java.awt.Color(255, 255, 255));
        lblQuanLy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_start_menu_25px.png"))); // NOI18N
        lblQuanLy.setText("   Quản lý");
        btnQuanLy.add(lblQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 11, -1, 48));

        panMenu.add(btnQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 170, 70));

        btnQuanLyDisable.setBackground(new java.awt.Color(23, 35, 51));
        btnQuanLyDisable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnQuanLyDisableMousePressed(evt);
            }
        });
        btnQuanLyDisable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pan_btnQuanLy1.setOpaque(false);
        pan_btnQuanLy1.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout pan_btnQuanLy1Layout = new javax.swing.GroupLayout(pan_btnQuanLy1);
        pan_btnQuanLy1.setLayout(pan_btnQuanLy1Layout);
        pan_btnQuanLy1Layout.setHorizontalGroup(
            pan_btnQuanLy1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        pan_btnQuanLy1Layout.setVerticalGroup(
            pan_btnQuanLy1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        btnQuanLyDisable.add(pan_btnQuanLy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 70));

        lblQuanLy1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblQuanLy1.setForeground(new java.awt.Color(255, 255, 255));
        lblQuanLy1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_start_menu_25px.png"))); // NOI18N
        lblQuanLy1.setText("   Quản lý");
        btnQuanLyDisable.add(lblQuanLy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 11, -1, 48));

        panMenu.add(btnQuanLyDisable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 170, 70));

        btnThongKe.setBackground(new java.awt.Color(23, 35, 51));
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThongKeMousePressed(evt);
            }
        });
        btnThongKe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pan_btnThongKe.setOpaque(false);
        pan_btnThongKe.setPreferredSize(new java.awt.Dimension(3, 43));

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

        btnThongKe.add(pan_btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 70));

        lblThongKe.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_statistics_25px.png"))); // NOI18N
        lblThongKe.setText("   Thống kê");
        btnThongKe.add(lblThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 11, -1, 48));

        panMenu.add(btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 170, 70));

        btnThongKeDisable.setBackground(new java.awt.Color(23, 35, 51));
        btnThongKeDisable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThongKeDisableMousePressed(evt);
            }
        });
        btnThongKeDisable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pan_btnThongKe1.setOpaque(false);
        pan_btnThongKe1.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout pan_btnThongKe1Layout = new javax.swing.GroupLayout(pan_btnThongKe1);
        pan_btnThongKe1.setLayout(pan_btnThongKe1Layout);
        pan_btnThongKe1Layout.setHorizontalGroup(
            pan_btnThongKe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        pan_btnThongKe1Layout.setVerticalGroup(
            pan_btnThongKe1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        btnThongKeDisable.add(pan_btnThongKe1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 70));

        lblThongKe1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblThongKe1.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_statistics_25px.png"))); // NOI18N
        lblThongKe1.setText("   Thống kê");
        btnThongKeDisable.add(lblThongKe1, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 11, -1, 48));

        panMenu.add(btnThongKeDisable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 170, 70));

        btnThongTin.setBackground(new java.awt.Color(23, 35, 51));
        btnThongTin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThongTinMousePressed(evt);
            }
        });

        pan_btnThongTin.setOpaque(false);
        pan_btnThongTin.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout pan_btnThongTinLayout = new javax.swing.GroupLayout(pan_btnThongTin);
        pan_btnThongTin.setLayout(pan_btnThongTinLayout);
        pan_btnThongTinLayout.setHorizontalGroup(
            pan_btnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        pan_btnThongTinLayout.setVerticalGroup(
            pan_btnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        lblThongTin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblThongTin.setForeground(new java.awt.Color(255, 255, 255));
        lblThongTin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_admin_settings_male_25px.png"))); // NOI18N
        lblThongTin.setText("   Thông tin");

        javax.swing.GroupLayout btnThongTinLayout = new javax.swing.GroupLayout(btnThongTin);
        btnThongTin.setLayout(btnThongTinLayout);
        btnThongTinLayout.setHorizontalGroup(
            btnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThongTinLayout.createSequentialGroup()
                .addComponent(pan_btnThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lblThongTin))
        );
        btnThongTinLayout.setVerticalGroup(
            btnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pan_btnThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(btnThongTinLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panMenu.add(btnThongTin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 170, 70));

        jPanel3.setBackground(new java.awt.Color(23, 35, 51));

        lblHello.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblHello.setForeground(new java.awt.Color(255, 255, 255));
        lblHello.setText("Hello User");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 55, Short.MAX_VALUE)
                    .addComponent(lblHello)
                    .addGap(0, 56, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblHello)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panMenu.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 170, 50));

        getContentPane().add(panMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 700));

        panBackgound.setBackground(new java.awt.Color(255, 255, 255));
        panBackgound.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panBanHang.setBackground(new java.awt.Color(255, 255, 255));
        panBanHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBannerTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerDanhMuc.jpg"))); // NOI18N
        panBanHang.add(lblBannerTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 134));

        panGoiMon.setBackground(new java.awt.Color(255, 255, 255));
        panGoiMon.setForeground(new java.awt.Color(255, 255, 255));
        panGoiMon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panGoiMon.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 235, 530, 280));

        lblTenNhanVien.setBackground(new java.awt.Color(23, 35, 51));
        lblTenNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenNhanVien.setForeground(new java.awt.Color(23, 35, 51));
        lblTenNhanVien.setText("Tên nhân viên");
        panGoiMon.add(lblTenNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 5, -1, -1));

        sptTenNhanVien.setBackground(new java.awt.Color(23, 35, 51));
        sptTenNhanVien.setForeground(new java.awt.Color(23, 35, 51));
        panGoiMon.add(sptTenNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 36, 216, -1));

        txtTenNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenNhanVien.setForeground(new java.awt.Color(23, 35, 51));
        txtTenNhanVien.setBorder(null);
        txtTenNhanVien.setEnabled(false);
        panGoiMon.add(txtTenNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 0, 216, 30));

        lblTenDoUong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenDoUong.setForeground(new java.awt.Color(23, 35, 51));
        lblTenDoUong.setText("Mã Bàn");
        panGoiMon.add(lblTenDoUong, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        cmbTenDoUong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbTenDoUong.setForeground(new java.awt.Color(23, 35, 51));
        cmbTenDoUong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cmbTenDoUong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTenDoUongActionPerformed(evt);
            }
        });
        panGoiMon.add(cmbTenDoUong, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 122, 216, 30));

        btnThem.setBackground(new java.awt.Color(23, 35, 51));
        btnThem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setLabel("Thêm");
        btnThem.setPreferredSize(new java.awt.Dimension(68, 25));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        panGoiMon.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 110, 40));

        lblTongCong.setBackground(new java.awt.Color(23, 35, 51));
        lblTongCong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongCong.setForeground(new java.awt.Color(23, 35, 51));
        lblTongCong.setText("Tổng cộng");
        panGoiMon.add(lblTongCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 96, -1, -1));

        sptTongCong.setBackground(new java.awt.Color(23, 35, 51));
        panGoiMon.add(sptTongCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 130, 218, 10));

        txtTongCong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTongCong.setForeground(new java.awt.Color(23, 35, 51));
        txtTongCong.setBorder(null);
        panGoiMon.add(txtTongCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 90, 218, 30));

        lbltienkhachdua.setBackground(new java.awt.Color(23, 35, 51));
        lbltienkhachdua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbltienkhachdua.setForeground(new java.awt.Color(23, 35, 51));
        lbltienkhachdua.setText("Tiền khách đưa");
        panGoiMon.add(lbltienkhachdua, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 165, -1, -1));

        txtTienThoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTienThoi.setForeground(new java.awt.Color(23, 35, 51));
        txtTienThoi.setBorder(null);
        panGoiMon.add(txtTienThoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 230, 218, 30));

        sptTienThoi.setBackground(new java.awt.Color(23, 35, 51));
        sptTienThoi.setForeground(new java.awt.Color(23, 35, 51));
        panGoiMon.add(sptTienThoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 270, 218, 10));

        lblTienThoi.setBackground(new java.awt.Color(23, 35, 51));
        lblTienThoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTienThoi.setForeground(new java.awt.Color(23, 35, 51));
        lblTienThoi.setText("Tiền thối");
        panGoiMon.add(lblTienThoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 238, -1, -1));

        lblBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBan.setForeground(new java.awt.Color(23, 35, 51));
        lblBan.setText("Chọn Đồ Uống ");
        panGoiMon.add(lblBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        cmbBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbBan.setForeground(new java.awt.Color(23, 35, 51));
        cmbBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        panGoiMon.add(cmbBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 182, 216, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(23, 35, 51));
        jLabel11.setText("Mã hoá đơn");
        panGoiMon.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, -1, 24));

        panGoiMon.add(cmbhoadon, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 300, 220, 30));

        lblTenNhanVien1.setBackground(new java.awt.Color(23, 35, 51));
        lblTenNhanVien1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenNhanVien1.setForeground(new java.awt.Color(23, 35, 51));
        lblTenNhanVien1.setText("Mã nhân viên");
        panGoiMon.add(lblTenNhanVien1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 67, -1, -1));

        txtidnhanvien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidnhanvien.setForeground(new java.awt.Color(23, 35, 51));
        txtidnhanvien.setBorder(null);
        txtidnhanvien.setEnabled(false);
        panGoiMon.add(txtidnhanvien, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 216, 30));

        sptTenNhanVien1.setBackground(new java.awt.Color(23, 35, 51));
        sptTenNhanVien1.setForeground(new java.awt.Color(23, 35, 51));
        panGoiMon.add(sptTenNhanVien1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 216, -1));

        txtGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGia.setForeground(new java.awt.Color(23, 35, 51));
        txtGia.setBorder(null);
        panGoiMon.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 160, 218, 30));

        sptTongCong1.setBackground(new java.awt.Color(23, 35, 51));
        sptTongCong1.setForeground(new java.awt.Color(23, 35, 51));
        panGoiMon.add(sptTongCong1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 200, 218, 10));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thanh toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(23, 35, 51))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(23, 35, 51));

        btnLuu.setBackground(new java.awt.Color(23, 35, 51));
        btnLuu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setLabel("Thanh Toán");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(319, Short.MAX_VALUE)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );

        panGoiMon.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 420, 460));

        jButton7.setBackground(new java.awt.Color(23, 35, 51));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Làm mới");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        panGoiMon.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 110, 40));

        panBanHang.add(panGoiMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        panBackgound.add(panBanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -4, 1080, 700));

        panQuanLy.setBackground(new java.awt.Color(255, 255, 255));
        panQuanLy.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBannerTitleQuanLy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerQuanLi.jpg"))); // NOI18N
        panQuanLy.add(lblBannerTitleQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 134));

        tbpQuanLi.setBackground(new java.awt.Color(255, 255, 255));
        tbpQuanLi.setForeground(new java.awt.Color(23, 35, 51));
        tbpQuanLi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        panQuanLyDoUong.setBackground(new java.awt.Color(255, 255, 255));
        panQuanLyDoUong.setForeground(new java.awt.Color(255, 255, 255));

        txtTimMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimMon.setForeground(new java.awt.Color(23, 35, 51));
        txtTimMon.setBorder(null);

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
        });
        jScrollPane2.setViewportView(tblQuanLyDoUong);

        btnLamMoiMon.setBackground(new java.awt.Color(23, 35, 51));
        btnLamMoiMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLamMoiMon.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiMon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_refresh_25px.png"))); // NOI18N
        btnLamMoiMon.setText("  Làm mới");
        btnLamMoiMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiMonActionPerformed(evt);
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

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(23, 35, 51));
        jLabel13.setText("Mã đồ uống");

        txtmadouong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtmadouong.setForeground(new java.awt.Color(23, 35, 51));
        txtmadouong.setBorder(null);
        txtmadouong.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(23, 35, 51));
        jLabel14.setText("Tên đồ uống ");

        txttendouong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttendouong.setForeground(new java.awt.Color(23, 35, 51));
        txttendouong.setBorder(null);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(23, 35, 51));
        jLabel15.setText("Đơn giá ");

        txtdongia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtdongia.setForeground(new java.awt.Color(23, 35, 51));
        txtdongia.setBorder(null);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(23, 35, 51));
        jLabel20.setText("Danh mục ");

        txtdanhmuc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtdanhmuc.setForeground(new java.awt.Color(23, 35, 51));
        txtdanhmuc.setBorder(null);
        txtdanhmuc.setEnabled(false);
        txtdanhmuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdanhmucActionPerformed(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(23, 35, 51));

        jSeparator3.setBackground(new java.awt.Color(23, 35, 51));

        jSeparator4.setBackground(new java.awt.Color(23, 35, 51));

        jSeparator5.setBackground(new java.awt.Color(23, 35, 51));

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

        javax.swing.GroupLayout panQuanLyDoUongLayout = new javax.swing.GroupLayout(panQuanLyDoUong);
        panQuanLyDoUong.setLayout(panQuanLyDoUongLayout);
        panQuanLyDoUongLayout.setHorizontalGroup(
            panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTimMon, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator2)
                                    .addComponent(txtmadouong, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                                .addGap(124, 124, 124)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(21, 21, 21)
                                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                                        .addComponent(txttendouong, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                                                .addComponent(jLabel20)
                                                .addGap(26, 26, 26))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panQuanLyDoUongLayout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addGap(43, 43, 43))))
                                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtdanhmuc, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtdongia, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(427, Short.MAX_VALUE))
            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnThemMon, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSuaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnXoaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );
        panQuanLyDoUongLayout.setVerticalGroup(
            panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1))
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addComponent(txtTimMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addComponent(btnThemMon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnSuaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnXoaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLamMoiMon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtmadouong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20))
                    .addComponent(txtdanhmuc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panQuanLyDoUongLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel15))
                            .addComponent(txtdongia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txttendouong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14))))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panQuanLyDoUongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbpQuanLi.addTab("Quản lí đồ uống", panQuanLyDoUong);

        panQuanLyBan.setBackground(new java.awt.Color(255, 255, 255));
        panQuanLyBan.setForeground(new java.awt.Color(255, 255, 255));

        tblQuanLyBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã bàn", "Tên bàn"
            }
        ));
        tblQuanLyBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuanLyBanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblQuanLyBan);

        btnXoaBan.setBackground(new java.awt.Color(23, 35, 51));
        btnXoaBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoaBan.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_delete_25px.png"))); // NOI18N
        btnXoaBan.setText("  Xoá");
        btnXoaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaBanActionPerformed(evt);
            }
        });

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(23, 35, 51));
        jLabel2.setText("Có ... bàn");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(23, 35, 51));
        jLabel4.setText("Mã bàn");

        txtmaban.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtmaban.setForeground(new java.awt.Color(23, 35, 51));
        txtmaban.setBorder(null);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(23, 35, 51));
        jLabel21.setText("Tên bàn");

        txttenban.setBorder(null);

        jSeparator6.setBackground(new java.awt.Color(23, 35, 51));

        jSeparator7.setBackground(new java.awt.Color(23, 35, 51));

        btnLamMoiBan.setBackground(new java.awt.Color(23, 35, 51));
        btnLamMoiBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLamMoiBan.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_refresh_25px.png"))); // NOI18N
        btnLamMoiBan.setText("  Làm mới");
        btnLamMoiBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panQuanLyBanLayout = new javax.swing.GroupLayout(panQuanLyBan);
        panQuanLyBan.setLayout(panQuanLyBanLayout);
        panQuanLyBanLayout.setHorizontalGroup(
            panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyBanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator6)
                            .addComponent(txtmaban, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttenban, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panQuanLyBanLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThemBan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSuaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLamMoiBan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panQuanLyBanLayout.createSequentialGroup()
                                .addGap(813, 813, 813)
                                .addComponent(jLabel2))
                            .addComponent(lblTimBan))
                        .addContainerGap(38, Short.MAX_VALUE))))
        );
        panQuanLyBanLayout.setVerticalGroup(
            panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuanLyBanLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTimBan)
                .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(btnThemBan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnSuaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnXoaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnLamMoiBan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuanLyBanLayout.createSequentialGroup()
                        .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtmaban, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panQuanLyBanLayout.createSequentialGroup()
                            .addGroup(panQuanLyBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(txttenban, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(16, 16, 16))
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(261, 261, 261)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbpQuanLi.addTab("Quản lý bàn", panQuanLyBan);

        panQuanLyTaiKhoan.setBackground(new java.awt.Color(255, 255, 255));
        panQuanLyTaiKhoan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên đăng nhập", "Mật khẩu", "Vai trò", "ID NHÂN VIÊN"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblTaiKhoan);

        panQuanLyTaiKhoan.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 73, 840, 270));

        btnXoaTK.setBackground(new java.awt.Color(23, 35, 51));
        btnXoaTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoaTK.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_delete_25px.png"))); // NOI18N
        btnXoaTK.setText("  Xoá");
        btnXoaTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTKActionPerformed(evt);
            }
        });
        panQuanLyTaiKhoan.add(btnXoaTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 220, 125, 46));

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
        panQuanLyTaiKhoan.add(btnThemTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 80, 125, 46));

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
        panQuanLyTaiKhoan.add(btnSuaTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 150, 125, 46));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(23, 35, 51));
        jLabel24.setText("Vai trò");
        panQuanLyTaiKhoan.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 380, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(23, 35, 51));
        jLabel22.setText("Tên Đăng nhập");
        panQuanLyTaiKhoan.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 365, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Mật khẩu");
        panQuanLyTaiKhoan.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 436, -1, -1));

        txtmatkhau.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtmatkhau.setBorder(null);
        panQuanLyTaiKhoan.add(txtmatkhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 218, 30));

        txtdangnhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtdangnhap.setBorder(null);
        panQuanLyTaiKhoan.add(txtdangnhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 218, 30));

        txtvaitro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtvaitro.setBorder(null);
        panQuanLyTaiKhoan.add(txtvaitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, 218, 30));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(23, 35, 51));
        jLabel31.setText("Mã nhân viên");
        panQuanLyTaiKhoan.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 440, -1, -1));

        txtidnv.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidnv.setBorder(null);
        panQuanLyTaiKhoan.add(txtidnv, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 218, 30));

        jSeparator8.setBackground(new java.awt.Color(23, 35, 51));
        panQuanLyTaiKhoan.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 218, 10));

        jSeparator9.setBackground(new java.awt.Color(23, 35, 51));
        panQuanLyTaiKhoan.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 218, 10));

        jSeparator10.setBackground(new java.awt.Color(23, 35, 51));
        panQuanLyTaiKhoan.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, 218, 10));

        jSeparator11.setBackground(new java.awt.Color(23, 35, 51));
        panQuanLyTaiKhoan.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 470, 218, 10));

        btnLamMoiAcc.setBackground(new java.awt.Color(23, 35, 51));
        btnLamMoiAcc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLamMoiAcc.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_refresh_25px.png"))); // NOI18N
        btnLamMoiAcc.setText("  Làm mới");
        btnLamMoiAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiAccActionPerformed(evt);
            }
        });
        panQuanLyTaiKhoan.add(btnLamMoiAcc, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 290, 125, 46));

        tbpQuanLi.addTab("Quản lý tài khoản", panQuanLyTaiKhoan);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Nhân Viên", "ID Nhân Viên", "Ngày sinh", "SDT", "Giới Tính"
            }
        ));
        tblNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNVMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblNV);

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 22, 850, 290));
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 289, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(23, 35, 51));
        jLabel26.setText("Tên Nhân Viên");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 336, -1, 23));

        txttennhanvien.setBorder(null);
        jPanel2.add(txttennhanvien, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 334, 235, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(23, 35, 51));
        jLabel27.setText("Mã nhân viên");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 396, -1, -1));

        txtiddangnhap.setBorder(null);
        txtiddangnhap.setEnabled(false);
        jPanel2.add(txtiddangnhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 391, 235, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(23, 35, 51));
        jLabel28.setText("Ngày Sinh");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 453, -1, -1));

        txtngaysinh.setBorder(null);
        jPanel2.add(txtngaysinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 448, 235, 30));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(23, 35, 51));
        jLabel29.setText("Số điện thoại");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(426, 339, -1, -1));

        txtdt.setBorder(null);
        jPanel2.add(txtdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 330, 235, 30));

        jButton2.setBackground(new java.awt.Color(23, 35, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_add_25px.png"))); // NOI18N
        jButton2.setText("  Thêm ");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 30, 125, 46));

        jButton3.setBackground(new java.awt.Color(23, 35, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_update_tag_25px.png"))); // NOI18N
        jButton3.setText("  Sửa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 110, 125, 46));

        jButton4.setBackground(new java.awt.Color(23, 35, 51));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_delete_25px.png"))); // NOI18N
        jButton4.setText("  Xóa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 180, 125, 46));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(23, 35, 51));
        jLabel30.setText("Giới tính");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(426, 391, -1, -1));

        cmbgt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "0", "1" }));
        jPanel2.add(cmbgt, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 390, 235, 30));

        jSeparator12.setBackground(new java.awt.Color(23, 35, 51));
        jPanel2.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 370, 235, 10));

        jSeparator13.setBackground(new java.awt.Color(23, 35, 51));
        jPanel2.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 427, 235, 10));

        jSeparator14.setBackground(new java.awt.Color(23, 35, 51));
        jPanel2.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 484, 235, 10));

        jSeparator15.setBackground(new java.awt.Color(23, 35, 51));
        jPanel2.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 370, 235, 10));

        btnLamMoiEmp.setBackground(new java.awt.Color(23, 35, 51));
        btnLamMoiEmp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLamMoiEmp.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/icons8_refresh_25px.png"))); // NOI18N
        btnLamMoiEmp.setText("  Làm mới");
        btnLamMoiEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiEmpActionPerformed(evt);
            }
        });
        jPanel2.add(btnLamMoiEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 250, 125, 46));

        tbpQuanLi.addTab("Quản lý thông tin nhân viên ", jPanel2);

        panQuanLy.add(tbpQuanLi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 1060, 540));

        panBackgound.add(panQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -4, 1070, 700));

        panThongKe.setBackground(new java.awt.Color(255, 255, 255));

        lblBannerThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edu/poly/image/bannerThongKe.jpg"))); // NOI18N

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(23, 35, 51));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblthongke.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN ĐỒ UỐNG ", "TÊN NHÂN VIÊN ", "NGÀY LẬP ", "TỔNG TIỀN"
            }
        ));
        jScrollPane7.setViewportView(tblthongke);

        jPanel5.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 1035, 373));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Ngày tháng thống kê");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 23));

        txtdate.setDateFormatString(" yyyy-M-dd");
        txtdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(txtdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, 35));

        jButton5.setBackground(new java.awt.Color(23, 35, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Thống kê");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 94, 34));

        lbltongdoanhthu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbltongdoanhthu.setForeground(new java.awt.Color(23, 35, 51));
        lbltongdoanhthu.setText("Tổng doanh thu :");
        jPanel5.add(lbltongdoanhthu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 477, 910, -1));

        jTabbedPane1.addTab("Doanh Thu", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        tblsanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SẢN PHẨM ", "NGÀY BÁN ", "NHÂN VIÊN "
            }
        ));
        jScrollPane5.setViewportView(tblsanpham);

        cmbthongkesanpham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbthongkesanpham.setForeground(new java.awt.Color(23, 35, 51));

        btnthongkesanpham.setBackground(new java.awt.Color(23, 35, 51));
        btnthongkesanpham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnthongkesanpham.setForeground(new java.awt.Color(255, 255, 255));
        btnthongkesanpham.setText("Thống kê");
        btnthongkesanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthongkesanphamActionPerformed(evt);
            }
        });

        datemonth.setForeground(new java.awt.Color(23, 35, 51));
        datemonth.setDateFormatString("yyyy-MM");
        datemonth.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbltongsanpham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbltongsanpham.setForeground(new java.awt.Color(23, 35, 51));
        lbltongsanpham.setText("Tổng sản phẩm :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1035, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(cmbthongkesanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(datemonth, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btnthongkesanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbltongsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(cmbthongkesanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(datemonth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnthongkesanpham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(lbltongsanpham)
                .addGap(25, 25, 25))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        tbltknhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sản phẩm ", " nhân viên", "ngày bán ", "giá "
            }
        ));
        jScrollPane8.setViewportView(tbltknhanvien);

        cmbnhanvien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbnhanvien.setForeground(new java.awt.Color(23, 35, 51));
        cmbnhanvien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        dateday.setForeground(new java.awt.Color(23, 35, 51));
        dateday.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton6.setBackground(new java.awt.Color(23, 35, 51));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Thống kê");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        lbltongtiennhanvien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbltongtiennhanvien.setForeground(new java.awt.Color(23, 35, 51));
        lbltongtiennhanvien.setText("Tổng :");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(cmbnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(dateday, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1023, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltongtiennhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(dateday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbnhanvien))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(lbltongtiennhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("Nhân viên", jPanel7);

        javax.swing.GroupLayout panThongKeLayout = new javax.swing.GroupLayout(panThongKe);
        panThongKe.setLayout(panThongKeLayout);
        panThongKeLayout.setHorizontalGroup(
            panThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongKeLayout.createSequentialGroup()
                .addComponent(lblBannerThongKe)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        panThongKeLayout.setVerticalGroup(
            panThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panThongKeLayout.createSequentialGroup()
                .addComponent(lblBannerThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        selectItem();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        this.dispose();
        new LoginJFrame().setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnThemMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMonActionPerformed
        if (txttendouong.getText().equals("") || txtdongia.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "vui lòng điền đầy đủ thông tin");
        } else {

            try {

                String sql = "INSERT INTO DOUONG VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txttendouong.getText());
                ps.setString(2, "2");
                ps.setDouble(3, Double.parseDouble(txtdongia.getText()));
                ps.execute();
                JOptionPane.showMessageDialog(this, "thêm mới món thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
            hienthimonQL();
        }
        xoatrangmon();
        runstart();
    }//GEN-LAST:event_btnThemMonActionPerformed

    private void btnSuaTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTKActionPerformed
        updatetaikhoan();
        hienthiNHANVIENQL();
        runstart();
    }//GEN-LAST:event_btnSuaTKActionPerformed

    private void btnSuaMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMonActionPerformed
        update();
        xoatrangmon();
        runstart();
    }//GEN-LAST:event_btnSuaMonActionPerformed

    private void btnThemTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTKActionPerformed
        if (txtdangnhap.getText().equals("") || txtmatkhau.getText().equals("") || txtvaitro.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "vui lòng điền đầy đủ thông tin");
        } else {
            try {
                String sql = "INSERT INTO TAIKHOAN VALUES (?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtdangnhap.getText());
                ps.setString(2, txtmatkhau.getText());
                ps.setString(3, txtvaitro.getText());
                ps.setString(4, txtidnv.getText());
                ps.execute();
                JOptionPane.showMessageDialog(this, "thêm mới Tài khoản thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hienthiNHANVIENQL();
    }//GEN-LAST:event_btnThemTKActionPerformed

    private void btnThemBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemBanActionPerformed
        if (txttenban.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "vui lòng điền đầy đủ thông tin");
        } else {

            try {

                String sql = "INSERT INTO BAN VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txttenban.getText());
                ps.setString(2, "Trống");
                ps.execute();
                JOptionPane.showMessageDialog(this, "thêm mới Bàn thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
            hienthibanQL();
        }
        xoatrangban();
        runstart();
    }//GEN-LAST:event_btnThemBanActionPerformed

    private void btnSuaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaBanActionPerformed
        updateban();
        hienthibanQL();
        runstart();
    }//GEN-LAST:event_btnSuaBanActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        if (txtGia.getText().equals("")) {
            txtGia.setText(txtTongCong.getText());
        }
        if (txtTongCong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "vui lòng chọn món để thanh toán");
        } else {
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
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void cmbTenDoUongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTenDoUongActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbTenDoUongActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tblQuanLyDoUong.getModel();
        model2.setRowCount(0);
        timkiemmon();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblQuanLyDoUongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLyDoUongMouseClicked
        index = tblQuanLyDoUong.getSelectedRow();
        this.clicktable(index);
        /* try {
 
            int row = tblQuanLyDoUong.rowAtPoint(evt.getPoint());
        int col = tblQuanLyDoUong.columnAtPoint(evt.getPoint());
        
            new UpdateProduct().setVisible(true);
            System.out.println(col);
            System.out.println(row);
            UpdateProduct.txtTenMon.setText(tblQuanLyDoUong.getValueAt(row, col).toString());
            UpdateProduct.txtiddouong.setText(tblQuanLyDoUong.getValueAt(row, col-1).toString());
            UpdateProduct.txtDanhMuc.setText("1");
            UpdateProduct.txtDonGia.setText(tblQuanLyDoUong.getValueAt(row, col+1).toString());
           
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "click bị lỗi");
            System.out.println(e);
            e.printStackTrace();

        }
         */
    }//GEN-LAST:event_tblQuanLyDoUongMouseClicked

    private void btnLamMoiMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiMonActionPerformed
        txtmadouong.setText("");
        txtdanhmuc.setText("");
        txttendouong.setText("");
        txtdongia.setText("");
    }//GEN-LAST:event_btnLamMoiMonActionPerformed

    private void txtdanhmucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdanhmucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdanhmucActionPerformed

    private void tblQuanLyBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLyBanMouseClicked
        // TODO add your handling code here:
        index = tblQuanLyBan.getSelectedRow();
        this.clicktableBAN(index);

    }//GEN-LAST:event_tblQuanLyBanMouseClicked

    private void btnXoaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaBanActionPerformed
        // TODO add your handling code here:
        index = tblQuanLyBan.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn hàng để xóa!");
        } else {
            this.deleteban();

            this.jButton1ActionPerformed(evt);
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");

        }
        hienthibanQL();
        xoatrangban();
        runstart();

    }//GEN-LAST:event_btnXoaBanActionPerformed

    private void tblTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiKhoanMouseClicked
        // TODO add your handling code here:
        index = tblTaiKhoan.getSelectedRow();
        this.clicktablenhanvien(index);
    }//GEN-LAST:event_tblTaiKhoanMouseClicked

    private void tblNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMouseClicked
        // TODO add your handling code here:
        index = tblNV.getSelectedRow();
        this.clickhienthiNV(index);
    }//GEN-LAST:event_tblNVMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        if (txttennhanvien.getText().equals("") || txtngaysinh.getText().equals("") || txtdt.getText().equals("") || cmbgt.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin");
        } else {

            try {
                String ngaysinh = txtngaysinh.getText();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = df.parse(ngaysinh);
                String sql = "INSERT INTO NHANVIEN VALUES (?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txttennhanvien.getText());
                ps.setString(2, df.format(date));
                ps.setInt(3, Integer.parseInt(txtdt.getText()));
                ps.setInt(4, cmbgt.getItemCount());
                ps.execute();
                JOptionPane.showMessageDialog(this, "thêm mới Nhân viên thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        updatenhanvien();
        hienthithongtinnhanvien();
     
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        index = tblNV.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn hàng để xóa!");
        } else {
            this.deletenhanvien();

            this.jButton1ActionPerformed(evt);
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");

        }
        hienthithongtinnhanvien();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnXoaTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTKActionPerformed
        // TODO add your handling code here:
        index = tblTaiKhoan.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn hàng để xóa!");
        } else {
            this.deletetaikhoan();

            this.jButton1ActionPerformed(evt);
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");
        }
        hienthiNHANVIENQL();
    }//GEN-LAST:event_btnXoaTKActionPerformed

    private void btnGoiMonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGoiMonMousePressed
        setColor(btnGoiMon);
        pan_btnGoiMon.setOpaque(true);
        resetColor(new JPanel[]{btnThongTin, btnQuanLy, btnThongKe}, new JPanel[]{pan_btnThongTin, pan_btnQuanLy, pan_btnThongKe});
        panQuanLy.setVisible(false);
        panThongKe.setVisible(false);
        panBanHang.setVisible(true);
        panThietLap.setVisible(false);
    }//GEN-LAST:event_btnGoiMonMousePressed

    private void btnQuanLyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuanLyMousePressed
        setColor(btnQuanLy);
        pan_btnQuanLy.setOpaque(true);
        resetColor(new JPanel[]{btnThongTin, btnGoiMon, btnThongKe}, new JPanel[]{pan_btnThongTin, pan_btnGoiMon, pan_btnThongKe});
        panBanHang.setVisible(false);
        panQuanLy.setVisible(true);
        panThongKe.setVisible(false);
        tbpQuanLi.setVisible(true);
        //  btnThongKeHD.setVisible(true);
        btnDangXuat.setVisible(false);
    }//GEN-LAST:event_btnQuanLyMousePressed

    private void btnThongKeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMousePressed
        setColor(btnThongKe);
        pan_btnThongKe.setOpaque(true);
        resetColor(new JPanel[]{btnThongTin, btnQuanLy, btnGoiMon}, new JPanel[]{pan_btnThongTin, pan_btnQuanLy, pan_btnGoiMon});
        panBanHang.setVisible(false);
        panQuanLy.setVisible(false);
        panThongKe.setVisible(true);
        cmbBan.setSelectedIndex(0);
        showThongKe();
    }//GEN-LAST:event_btnThongKeMousePressed

    private void btnThongTinMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongTinMousePressed
        setColor(btnThongTin);
        pan_btnThongTin.setOpaque(true);
        resetColor(new JPanel[]{btnGoiMon, btnQuanLy, btnThongKe}, new JPanel[]{pan_btnGoiMon, pan_btnQuanLy, pan_btnThongKe});
        panQuanLy.setVisible(false);
        panThongKe.setVisible(false);
        panBanHang.setVisible(false);
        panThietLap.setVisible(true);
        btnDangXuat.setVisible(true);
    }//GEN-LAST:event_btnThongTinMousePressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        model1.setRowCount(0);
        selectstatistic();
        totalstastistic();
        if (model1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "không có doanh thu ngày này ");
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnthongkesanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthongkesanphamActionPerformed
        model6.setRowCount(0);
        doanhthusanpham();
        tongsoluong();
        if (model6.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Sản phẩm không có doanh thu tháng này ");
        }
    }//GEN-LAST:event_btnthongkesanphamActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        model7.setRowCount(0);
        doanhthunhanvien();
        tongtiennhanvien();
        if (model7.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nhân viên không có doanh thu ngày này ");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnQuanLyDisableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuanLyDisableMousePressed
        JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập", "Lỗi", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnQuanLyDisableMousePressed

    private void btnThongKeDisableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeDisableMousePressed
        JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập", "Lỗi", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_btnThongKeDisableMousePressed

    private void btnXoaMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMonActionPerformed
        // TODO add your handling code here:
        index = tblQuanLyDoUong.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn hàng để xóa!");
        } else {
            this.deletemon();

            this.jButton1ActionPerformed(evt);
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");

        }
        hienthimonQL();
        xoatrangmon();
        runstart();
    }//GEN-LAST:event_btnXoaMonActionPerformed

    private void btnLamMoiBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiBanActionPerformed
        txtmaban.setText("");
        txttenban.setText("");
    }//GEN-LAST:event_btnLamMoiBanActionPerformed

    private void btnLamMoiAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiAccActionPerformed
        txtdangnhap.setText("");
        txtmatkhau.setText("");
        txtvaitro.setText("");
        txtidnv.setText("");
    }//GEN-LAST:event_btnLamMoiAccActionPerformed

    private void btnLamMoiEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiEmpActionPerformed
        txttennhanvien.setText("");
        txtiddangnhap.setText("");
        txtngaysinh.setText("");
        txtdt.setText("");
    }//GEN-LAST:event_btnLamMoiEmpActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        model.setRowCount(0);
        cmbTenDoUong.setSelectedIndex(0);
        cmbBan.setSelectedIndex(0);
        txtTongCong.setText("");
        cmbTenDoUong.setSelectedIndex(0);
        txtTongCong.setText("");
        txtGia.setText("");
        txtTienThoi.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JPanel btnGoiMon;
    public static javax.swing.JButton btnLamMoiAcc;
    public static javax.swing.JButton btnLamMoiBan;
    public static javax.swing.JButton btnLamMoiEmp;
    public static javax.swing.JButton btnLamMoiMon;
    private java.awt.Button btnLuu;
    public javax.swing.JPanel btnQuanLy;
    public javax.swing.JPanel btnQuanLyDisable;
    private javax.swing.JButton btnSuaBan;
    public static javax.swing.JButton btnSuaMon;
    private javax.swing.JButton btnSuaTK;
    private java.awt.Button btnThem;
    private javax.swing.JButton btnThemBan;
    public static javax.swing.JButton btnThemMon;
    private javax.swing.JButton btnThemTK;
    public javax.swing.JPanel btnThongKe;
    public javax.swing.JPanel btnThongKeDisable;
    private javax.swing.JPanel btnThongTin;
    private javax.swing.JButton btnXoaBan;
    public static javax.swing.JButton btnXoaMon;
    private javax.swing.JButton btnXoaTK;
    private javax.swing.JButton btnthongkesanpham;
    private javax.swing.JComboBox<String> cmbBan;
    private javax.swing.JComboBox<String> cmbTenDoUong;
    private javax.swing.JComboBox<String> cmbgt;
    private javax.swing.JComboBox<String> cmbhoadon;
    private javax.swing.JComboBox<String> cmbnhanvien;
    private javax.swing.JComboBox<String> cmbthongkesanpham;
    private com.toedter.calendar.JDateChooser dateday;
    private com.toedter.calendar.JDateChooser datemonth;
    public static javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    public static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    public static javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBan;
    private javax.swing.JLabel lblBannerThongKe;
    private javax.swing.JLabel lblBannerTitle;
    private javax.swing.JLabel lblBannerTitle1;
    private javax.swing.JLabel lblBannerTitleQuanLy;
    private javax.swing.JLabel lblGoiMon;
    private javax.swing.JLabel lblHello;
    private javax.swing.JLabel lblIconUser;
    private javax.swing.JLabel lblQuanLy;
    private javax.swing.JLabel lblQuanLy1;
    private javax.swing.JLabel lblTenDoUong;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblTenNhanVien1;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblThongKe1;
    private javax.swing.JLabel lblThongTin;
    private javax.swing.JLabel lblTienThoi;
    private javax.swing.JLabel lblTimBan;
    private javax.swing.JLabel lblTongCong;
    private javax.swing.JLabel lbltienkhachdua;
    private javax.swing.JLabel lbltongdoanhthu;
    private javax.swing.JLabel lbltongsanpham;
    private javax.swing.JLabel lbltongtiennhanvien;
    private javax.swing.JPanel panBackgound;
    private javax.swing.JPanel panBanHang;
    private javax.swing.JPanel panGoiMon;
    private javax.swing.JPanel panMenu;
    public javax.swing.JPanel panQuanLy;
    public static javax.swing.JPanel panQuanLyBan;
    public static javax.swing.JPanel panQuanLyDoUong;
    private javax.swing.JPanel panQuanLyTaiKhoan;
    private javax.swing.JPanel panThietLap;
    public javax.swing.JPanel panThongKe;
    private javax.swing.JPanel pan_btnGoiMon;
    private javax.swing.JPanel pan_btnQuanLy;
    private javax.swing.JPanel pan_btnQuanLy1;
    private javax.swing.JPanel pan_btnThongKe;
    private javax.swing.JPanel pan_btnThongKe1;
    private javax.swing.JPanel pan_btnThongTin;
    private javax.swing.JSeparator sptTenNhanVien;
    private javax.swing.JSeparator sptTenNhanVien1;
    private javax.swing.JSeparator sptTienThoi;
    private javax.swing.JSeparator sptTongCong;
    private javax.swing.JSeparator sptTongCong1;
    private javax.swing.JTable tblGoiMon;
    private javax.swing.JTable tblNV;
    private javax.swing.JTable tblQuanLyBan;
    private javax.swing.JTable tblQuanLyDoUong;
    private javax.swing.JTable tblTaiKhoan;
    private javax.swing.JTable tblsanpham;
    private javax.swing.JTable tblthongke;
    private javax.swing.JTable tbltknhanvien;
    private javax.swing.JTabbedPane tbpQuanLi;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTienThoi;
    private javax.swing.JTextField txtTimMon;
    private javax.swing.JTextField txtTongCong;
    private javax.swing.JTextField txtdangnhap;
    private javax.swing.JTextField txtdanhmuc;
    private com.toedter.calendar.JDateChooser txtdate;
    private javax.swing.JTextField txtdongia;
    private javax.swing.JTextField txtdt;
    private javax.swing.JTextField txtiddangnhap;
    private javax.swing.JTextField txtidnhanvien;
    private javax.swing.JTextField txtidnv;
    private javax.swing.JTextField txtmaban;
    private javax.swing.JTextField txtmadouong;
    private javax.swing.JTextField txtmatkhau;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txttenban;
    private javax.swing.JTextField txttendouong;
    private javax.swing.JTextField txttennhanvien;
    private javax.swing.JTextField txtvaitro;
    // End of variables declaration//GEN-END:variables
}
