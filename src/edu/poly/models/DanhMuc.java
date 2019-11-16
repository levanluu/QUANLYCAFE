package edu.poly.models;

public class DanhMuc {
    private int iDDanhMuc;
    private String tenDanhMuc;

    public int getiDDanhMuc() {
        return iDDanhMuc;
    }

    public void setiDDanhMuc(int iDDanhMuc) {
        this.iDDanhMuc = iDDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public DanhMuc(int iDDanhMuc, String tenDanhMuc) {
        this.iDDanhMuc = iDDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }
}
