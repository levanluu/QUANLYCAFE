package edu.poly.models;

public class TaiKhoan {
    private String tenDangNhap, matKhau, vaiTro;
    private int iDNhanVien;

    public int getiDNhanVien() {
        return iDNhanVien;
    }

    public void setiDNhanVien(int iDNhanVien) {
        this.iDNhanVien = iDNhanVien;
    }

    public TaiKhoan(String tenDangNhap, String matKhau, String vaiTro, int iDNhanVien) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.iDNhanVien = iDNhanVien;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
}
