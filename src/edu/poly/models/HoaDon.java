package edu.poly.models;

import java.sql.Date;

public class HoaDon {
    private int iDHoaDon, iDBan, iDNhanVien;
    private String trangThai;
    private Date ngayLap;

    public int getiDNhanVien() {
        return iDNhanVien;
    }

    public void setiDNhanVien(int iDNhanVien) {
        this.iDNhanVien = iDNhanVien;
    }

    public HoaDon(int iDHoaDon, int iDBan, int iDNhanVien, String trangThai, Date ngayLap, float tongTien) {
        this.iDHoaDon = iDHoaDon;
        this.iDBan = iDBan;
        this.iDNhanVien = iDNhanVien;
        this.trangThai = trangThai;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }
    private float tongTien;

    public int getiDHoaDon() {
        return iDHoaDon;
    }

    public void setiDHoaDon(int iDHoaDon) {
        this.iDHoaDon = iDHoaDon;
    }

    public int getiDBan() {
        return iDBan;
    }

    public void setiDBan(int iDBan) {
        this.iDBan = iDBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
}
