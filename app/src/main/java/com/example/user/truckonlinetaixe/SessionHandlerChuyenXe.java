package com.example.user.truckonlinetaixe;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SessionHandlerChuyenXe {
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandlerChuyenXe(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param //username
     * @param //fullName
     */
    public void postChuyenXe(String id_xe,String idkh,String tenhang, String loaihang, String trongluonghang,String diemdi, String diemden, String giacuoc,String thoigiankhoihanh) {
        mEditor.putString("id_xe", id_xe);
        mEditor.putString("id_khachhang", idkh);
        mEditor.putString("tenhang", tenhang);
        mEditor.putString("loaihang", loaihang);
        mEditor.putString("trongluonghang", trongluonghang);
        mEditor.putString("diemdi", diemdi);
        mEditor.putString("diemden", diemden);
        mEditor.putString("giacuoc", giacuoc);
        mEditor.putString("thoigiankhoihanh",thoigiankhoihanh);
        mEditor.commit();
    }
}
