package com.example.user.truckonlinetaixe;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionHandlerDonHang {
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandlerDonHang(Context mContext) {
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
    public void postDonHang(String idkh){
        mEditor.putString("id_khachhang",idkh);

        mEditor.commit();
    }
}
