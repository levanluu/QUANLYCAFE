package edu.poly.models;

public class DoUong {
    private int iDDoUong,iDDanhMuc;
    private String tenDoUong;
    private float donGia;

    public int getiDDoUong() {
        return iDDoUong;
    }

    public void setiDDoUong(int iDDoUong) {
        this.iDDoUong = iDDoUong;
    }

    public int getiDDanhMuc() {
        return iDDanhMuc;
    }

    public void setiDDanhMuc(int iDDanhMuc) {
        this.iDDanhMuc = iDDanhMuc;
    }

    public String getTenDoUong() {
        return tenDoUong;
    }

    public void setTenDoUong(String tenDoUong) {
        this.tenDoUong = tenDoUong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public DoUong(int iDDoUong, int iDDanhMuc, String tenDoUong, float donGia) {
        this.iDDoUong = iDDoUong;
        this.iDDanhMuc = iDDanhMuc;
        this.tenDoUong = tenDoUong;
        this.donGia = donGia;
    }
}
