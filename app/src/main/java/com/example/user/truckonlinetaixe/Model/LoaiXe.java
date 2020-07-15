package com.example.user.truckonlinetaixe.Model;

public class LoaiXe {
    private int ID_lx;
    private String TenLoaiXe;

    public LoaiXe() {
    }

    public LoaiXe(int ID_lx, String tenLoaiXe) {
        this.ID_lx = ID_lx;
        TenLoaiXe = tenLoaiXe;
    }

    public int getID_lx() {
        return ID_lx;
    }

    public void setID_lx(int ID_lx) {
        this.ID_lx = ID_lx;
    }

    public String getTenLoaiXe() {
        return TenLoaiXe;
    }

    public void setTenLoaiXe(String tenLoaiXe) {
        TenLoaiXe = tenLoaiXe;
    }
}
