package com.example.user.truckonlinetaixe;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.Set;

/**
 * Created by Abhi on 20 Jan 2018 020.
 */

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_IDXE = "id_xe";
    public static final String KEY_BIENSOXE = "biensoxe";
    public static final String KEY_LOAIXE = "loaixe";
    public static final String KEY_TRONGTAIXE = "trongtaixe";
    public static final String KEY_TENCHUXE = "tenchuxe";
    public static final String KEY_SDTTX = "sdttx";
    public static final String KEY_MOTA = "mota";
    private static final String KEY__TOKEN = "token";
    private static final String KEY_TAIKHOAN = "taikhoan";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param //username
     * @param //fullName
     */
    public void loginUser(String id_xe,String biensoxe, String loaixe, String trongtaixe,String tenchuxe, String sdttx, String mota,String token, String taikhoan) {
        mEditor.putString(KEY_IDXE, id_xe);
        mEditor.putString(KEY_BIENSOXE, biensoxe);
        mEditor.putString(KEY_LOAIXE, loaixe);
        mEditor.putString(KEY_TRONGTAIXE, trongtaixe);
        mEditor.putString(KEY_TENCHUXE, tenchuxe);
        mEditor.putString(KEY_SDTTX, sdttx);
        mEditor.putString(KEY_MOTA, mota);
        mEditor.putString(KEY__TOKEN,token);
        mEditor.putString(KEY_TAIKHOAN,taikhoan);
        Date date = new Date();

//        Set user session for next 7 days
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
    public TaiXe getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        TaiXe user = new TaiXe();
        user.setId_xe(mPreferences.getString(KEY_IDXE, KEY_EMPTY));
        user.setLoaixe(mPreferences.getString(KEY_LOAIXE, KEY_EMPTY));
        user.setTrongtaixe(mPreferences.getString(KEY_TRONGTAIXE, KEY_EMPTY));
        user.setBiensoxe(mPreferences.getString(KEY_BIENSOXE, KEY_EMPTY));
        user.setTenchuxe(mPreferences.getString(KEY_TENCHUXE, KEY_EMPTY));
        user.setSdttx(mPreferences.getString(KEY_SDTTX,KEY_EMPTY));
        user.setMota(mPreferences.getString(KEY_MOTA,KEY_EMPTY));
        user.setToken(mPreferences.getString(KEY__TOKEN,KEY_EMPTY));
        user.setTaikhoan(mPreferences.getString(KEY_TAIKHOAN,KEY_EMPTY));
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
