package edu.poly.models;

public class Ban {
    private String tenBan, trangThai;
    private int iDBan;

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getiDBan() {
        return iDBan;
    }

    public void setiDBan(int iDBan) {
        this.iDBan = iDBan;
    }

    public Ban(String tenBan, String trangThai, int iDBan) {
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.iDBan = iDBan;
    }
}
