package com.example.user.truckonlinetaixe;

import java.io.Serializable;

public class DonHangDangChoKH implements Serializable {
    private int ID;
    private int IDXe;
    private  int IDKH;
    private String TokenKH;
    private String TokenTX;
    private String TenKH;
    private String SdtKH;
    private String TenTaiXe;
    private String SdtTX;
    private String BienSoXe;
    private String LoaiXe;
    private String TrongTaiXe;
    private String TenHang;

    private String LoaiHang;
    private String DiemDi;
    private String DiemDen;
    private int GiaCuoc;
    private int TrongLuongHang;
    private String ThoiGianKhoiHanh;
    private String TinhTrang;

    private String TaiKhoan;

    public DonHangDangChoKH(int ID, int IDXe, int IDKH, String tokenKH, String tokenTX, String tenKH, String sdtKH, String tenTaiXe, String sdtTX, String bienSoXe, String loaiXe, String trongTaiXe, String tenHang, String loaiHang, String diemDi, String diemDen, int giaCuoc, int trongLuongHang, String thoiGianKhoiHanh, String tinhTrang, String taiKhoan) {
        this.ID = ID;
        this.IDXe = IDXe;
        this.IDKH = IDKH;
        TokenKH = tokenKH;
        TokenTX = tokenTX;
        TenKH = tenKH;
        SdtKH = sdtKH;
        TenTaiXe = tenTaiXe;
        SdtTX = sdtTX;
        BienSoXe = bienSoXe;
        LoaiXe = loaiXe;
        TrongTaiXe = trongTaiXe;
        TenHang = tenHang;
        LoaiHang = loaiHang;
        DiemDi = diemDi;
        DiemDen = diemDen;
        GiaCuoc = giaCuoc;
        TrongLuongHang = trongLuongHang;
        ThoiGianKhoiHanh = thoiGianKhoiHanh;
        TinhTrang = tinhTrang;
        TaiKhoan=taiKhoan;

    }

    public String getTenHang() {
        return TenHang;
    }

    public void setTenHang(String tenHang) {
        TenHang = tenHang;
    }


    public String getTrongTaiXe() {
        return TrongTaiXe;
    }

    public void setTrongTaiXe(String trongTaiXe) {
        TrongTaiXe = trongTaiXe;
    }

    public String getSdtTX() {
        return SdtTX;
    }

    public void setSdtTX(String sdtTX) {
        SdtTX = sdtTX;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDXe() {
        return IDXe;
    }

    public void setIDXe(int IDXe) {
        this.IDXe = IDXe;
    }

    public int getIDKH() {
        return IDKH;
    }

    public void setIDKH(int IDKH) {
        this.IDKH = IDKH;
    }

    public String getTokenKH() {
        return TokenKH;
    }

    public void setTokenKH(String tokenKH) {
        TokenKH = tokenKH;
    }

    public String getTokenTX() {
        return TokenTX;
    }

    public void setTokenTX(String tokenTX) {
        TokenTX = tokenTX;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getSdtKH() {
        return SdtKH;
    }

    public void setSdtKH(String sdtKH) {
        SdtKH = sdtKH;
    }

    public String getTenTaiXe() {
        return TenTaiXe;
    }

    public void setTenTaiXe(String tenTaiXe) {
        TenTaiXe = tenTaiXe;
    }

    public String getBienSoXe() {
        return BienSoXe;
    }

    public void setBienSoXe(String bienSoXe) {
        BienSoXe = bienSoXe;
    }

    public String getLoaiXe() {
        return LoaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        LoaiXe = loaiXe;
    }

    public String getLoaiHang() {
        return LoaiHang;
    }

    public void setLoaiHang(String loaiHang) {
        LoaiHang = loaiHang;
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

    public int getTrongLuongHang() {
        return TrongLuongHang;
    }

    public void setTrongLuongHang(int trongLuongHang) {
        TrongLuongHang = trongLuongHang;
    }

    public String getThoiGianKhoiHanh() {
        return ThoiGianKhoiHanh;
    }

    public void setThoiGianKhoiHanh(String thoiGianKhoiHanh) {
        ThoiGianKhoiHanh = thoiGianKhoiHanh;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }
}
