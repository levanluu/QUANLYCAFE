package edu.poly.models;

public class ChiTietHoaDon {
    private int iDCTHoaDon, iDHoaDon, iDDoUong, iDDoAn, soLuong;
    private float donGia;

    public int getiDCTHoaDon() {
        return iDCTHoaDon;
    }

    public void setiDCTHoaDon(int iDCTHoaDon) {
        this.iDCTHoaDon = iDCTHoaDon;
    }

    public int getiDHoaDon() {
        return iDHoaDon;
    }

    public void setiDHoaDon(int iDHoaDon) {
        this.iDHoaDon = iDHoaDon;
    }

    public int getiDDoUong() {
        return iDDoUong;
    }

    public void setiDDoUong(int iDDoUong) {
        this.iDDoUong = iDDoUong;
    }

    public int getiDDoAn() {
        return iDDoAn;
    }

    public void setiDDoAn(int iDDoAn) {
        this.iDDoAn = iDDoAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public ChiTietHoaDon(int iDCTHoaDon, int iDHoaDon, int iDDoUong, int iDDoAn, int soLuong, float donGia) {
        this.iDCTHoaDon = iDCTHoaDon;
        this.iDHoaDon = iDHoaDon;
        this.iDDoUong = iDDoUong;
        this.iDDoAn = iDDoAn;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
}
