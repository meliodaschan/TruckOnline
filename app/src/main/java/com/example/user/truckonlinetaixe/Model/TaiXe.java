package com.example.user.truckonlinetaixe.Model;


import java.util.Date;

public class TaiXe {
    private String id_xe;
    private String loaixe;
    private String trongtaixe;
    private String biensoxe;
    private String tenchuxe;
    private double lattx;
    private double lngtx;
    private double khoangcach;
    private String token;
    private String taikhoan;
    String sdttx;
    String mota;
    Date sessionExpiryDate;

    public TaiXe() {
    }

    public TaiXe(String id_xe,String loaixe, String trongtaixe, String biensoxe, String tenchuxe, double lattx, double lngtx, String sdttx, String mota, String token, String taikhoan) {
        this.id_xe = id_xe;
        this.loaixe = loaixe;
        this.trongtaixe = trongtaixe;
        this.biensoxe = biensoxe;
        this.tenchuxe = tenchuxe;
        this.lattx = lattx;
        this.lngtx = lngtx;
        this.sdttx = sdttx;
        this.mota = mota;
        this.token = token;
        this.taikhoan = taikhoan;
//        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getId_xe() {
        return id_xe;
    }

    public void setId_xe(String id_xe) {
        this.id_xe = id_xe;
    }
    public String getSdttx() {
        return sdttx;
    }

    public void setSdttx(String sdttx) {
        this.sdttx = sdttx;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }


    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }
    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }

    public String getLoaixe() {
        return loaixe;
    }

    public void setLoaixe(String loaixe) {
        this.loaixe = loaixe;
    }

    public String getTrongtaixe() {
        return trongtaixe;
    }

    public void setTrongtaixe(String trongtaixe) {
        this.trongtaixe = trongtaixe;
    }

    public String getBiensoxe() {
        return biensoxe;
    }

    public void setBiensoxe(String biensoxe) {
        this.biensoxe = biensoxe;
    }

    public String getTenchuxe() {
        return tenchuxe;
    }

    public void setTenchuxe(String tenchuxe) {
        this.tenchuxe = tenchuxe;
    }

    public double getLattx() {
        return lattx;
    }

    public void setLattx(double lattx) {
        this.lattx = lattx;
    }

    public double getLngtx() {
        return lngtx;
    }

    public void setLngtx(double lngtx) {
        this.lngtx = lngtx;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    @Override
    public String toString() {
        return "TaiXe{" +
                "loaixe='" + loaixe + '\'' +
                ", trongtaixe='" + trongtaixe + '\'' +
                ", biensoxe='" + biensoxe + '\'' +
                ", tenchuxe='" + tenchuxe + '\'' +
                ", lattx=" + lattx +
                ", lngtx=" + lngtx +
                ", khoangcach=" + khoangcach +
                ", sdttx='" + sdttx + '\'' +
                ", mota='" + mota + '\'' +
                ", sessionExpiryDate=" + sessionExpiryDate +
                ", token='" + token + '\'' +
                ", taikhoan='" + taikhoan + '\'' +
                '}';
    }
}