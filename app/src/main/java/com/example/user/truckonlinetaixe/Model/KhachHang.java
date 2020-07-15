package com.example.user.truckonlinetaixe.Model;


import java.util.Date;

public class KhachHang {
    String id_khachhang;
    String usernamekh;
    String tenkh;
    String diachikh;
    String sdtkh;
    String namsinhkh;
    String token;
    Date sessionExpiryDate;

    public KhachHang() {
    }

    public KhachHang(String id_khachhang,String usernamekh, String tenkh, String diachikh, String sdtkh, String namsinhkh, String token, Date sessionExpiryDate) {
        this.id_khachhang = id_khachhang;
        this.usernamekh = usernamekh;
        this.tenkh = tenkh;
        this.diachikh = diachikh;
        this.sdtkh = sdtkh;
        this.namsinhkh = namsinhkh;
        this.token = token;
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getId_khachhang() {
        return id_khachhang;
    }

    public void setId_khachhang(String id_khachhang) {
        this.id_khachhang = id_khachhang;
    }

    public String getUsernamekh() {
        return usernamekh;
    }

    public void setUsernamekh(String usernamekh) {
        this.usernamekh = usernamekh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachikh() {
        return diachikh;
    }

    public void setDiachikh(String diachikh) {
        this.diachikh = diachikh;
    }

    public String getSdtkh() {
        return sdtkh;
    }

    public void setSdtkh(String sdtkh) {
        this.sdtkh = sdtkh;
    }

    public String getNamsinhkh() {
        return namsinhkh;
    }

    public void setNamsinhkh(String namsinhkh) {
        this.namsinhkh = namsinhkh;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }
}