package com.example.user.truckonlinetaixe;

import java.util.Date;

public class ChuyenXe {
    private int ID;
    private String Token;
    private String TenKH;
    private String DiemDi;
    private String DiemDen;
    private int GiaCuoc;
    private String ThoiGianKhoiHanh;



    public ChuyenXe(int id, String token, String tenKH, String diemDi, String diemDen, int giaCuoc, String thoiGianKhoiHanh) {
        ID = id;
        Token = token;
        TenKH = tenKH;
        DiemDi = diemDi;
        DiemDen = diemDen;
        GiaCuoc = giaCuoc;
        ThoiGianKhoiHanh = thoiGianKhoiHanh;
    }

    public void setId(int id) {
        ID = id;
    }

    public int getId(){
        return  ID;

    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getDiemDi() {
        return DiemDi;
    }

    public void setDiemDi(String diemDi) {
        DiemDi = diemDi;
    }

    public String getDiemDen() {
        return DiemDen;
    }

    public void setDiemDen(String diemDen) {
        DiemDen = diemDen;
    }

    public int getGiaCuoc() {
        return GiaCuoc;
    }

    public void setGiaCuoc(int giaCuoc) {
        GiaCuoc = giaCuoc;
    }

    public String getThoiGianKhoiHanh() {
        return ThoiGianKhoiHanh;
    }

    public void setThoiGianKhoiHanh(String thoiGianKhoiHanh) {
        ThoiGianKhoiHanh = thoiGianKhoiHanh;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }

    @Override
    public String toString() {
        return "ChuyenXe{" +
                "ID=" + ID +
                ", Token='" + Token + '\'' +
                ", TenKH='" + TenKH + '\'' +
                ", DiemDi='" + DiemDi + '\'' +
                ", DiemDen='" + DiemDen + '\'' +
                ", GiaCuoc=" + GiaCuoc +
                ", ThoiGianKhoiHanh='" + ThoiGianKhoiHanh + '\'' +
                '}';
    }
}
