package com.example.user.truckonlinetaixe;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

/**
 * Created by Abhi on 20 Jan 2018 020.
 */

public class SessionHandlerKH {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IDKH = "id_khachhang";
    private static final String KEY_USERNAME = "usernamekh";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_TENKH = "tenkh";
    private static final String KEY_DIACHIKH = "diachikh";
    private static final String KEY_NAMSINHKH = "namsinhkh";
    private static final String KEY_SDTKH= "sdtkh";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandlerKH(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param usernamekh
     * @param tenkh
     * @param diachikh
     * @param namsinhkh
     * @param sdtkh
     */
    public void loginUser(String id_khachhang,String usernamekh, String tenkh, String diachikh, String sdtkh, String namsinhkh, String token) {
        mEditor.putString(KEY_IDKH, id_khachhang);
        mEditor.putString(KEY_USERNAME, usernamekh);
        mEditor.putString(KEY_TENKH, tenkh);
        mEditor.putString(KEY_DIACHIKH, diachikh);
        mEditor.putString(KEY_NAMSINHKH, namsinhkh);
        mEditor.putString(KEY_SDTKH, sdtkh);
        mEditor.putString(KEY_TOKEN, token);



        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public KhachHang getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        KhachHang user = new KhachHang();
        user.setId_khachhang(mPreferences.getString(KEY_IDKH,KEY_EMPTY));
        user.setUsernamekh(mPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        user.setTenkh(mPreferences.getString(KEY_TENKH, KEY_EMPTY));
        user.setDiachikh(mPreferences.getString(KEY_DIACHIKH, KEY_EMPTY));
        user.setNamsinhkh(mPreferences.getString(KEY_NAMSINHKH, KEY_EMPTY));
        user.setSdtkh(mPreferences.getString(KEY_SDTKH, KEY_EMPTY));
        user.setToken(mPreferences.getString(KEY_TOKEN, KEY_EMPTY));
        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));
        return user;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}
