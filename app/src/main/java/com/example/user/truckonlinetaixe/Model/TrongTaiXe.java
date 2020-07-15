package com.example.user.truckonlinetaixe.Model;

public class TrongTaiXe {
    private int ID_ttx;
    private String TrongTai;

    public TrongTaiXe() {
    }

    public TrongTaiXe(int ID_ttx, String trongTai) {
        this.ID_ttx = ID_ttx;
        TrongTai = trongTai;
    }

    public int getID_ttx() {
        return ID_ttx;
    }

    public void setID_ttx(int ID_ttx) {
        this.ID_ttx = ID_ttx;
    }

    public String getTrongTai() {
        return TrongTai;
    }

    public void setTrongTai(String trongTai) {
        TrongTai = trongTai;
    }
}
