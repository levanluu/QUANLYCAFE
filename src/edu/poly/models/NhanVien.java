package edu.poly.models;

import java.sql.Date;

public class NhanVien {
    private String tenNhanvien, soDienThoai;
    private Date ngaySinh;
    private int iDNhanVien;
    private boolean gioiTinh;

    public NhanVien(String tenNhanvien, String soDienThoai, Date ngaySinh, int iDNhanVien, boolean gioiTinh) {
        this.tenNhanvien = tenNhanvien;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
        this.iDNhanVien = iDNhanVien;
        this.gioiTinh = gioiTinh;
    }

    public String getTenNhanvien() {
        return tenNhanvien;
    }

    public void setTenNhanvien(String tenNhanvien) {
        this.tenNhanvien = tenNhanvien;
    }

    public int getiDNhanVien() {
        return iDNhanVien;
    }

    public void setiDNhanVien(int iDNhanVien) {
        this.iDNhanVien = iDNhanVien;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }


}
