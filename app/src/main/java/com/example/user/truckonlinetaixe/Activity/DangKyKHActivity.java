package com.example.user.truckonlinetaixe.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.user.truckonlinetaixe.Constant;
import com.example.user.truckonlinetaixe.MySingleton;
import com.example.user.truckonlinetaixe.R;
import com.example.user.truckonlinetaixe.SessionHandler.SessionHandlerKH;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.user.truckonlinetaixe.Connect.APIconnect.URL_BASE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_DANG_KY_KHACH_HANG;
import static com.example.user.truckonlinetaixe.Constant.getTAG;
//import static com.example.user.truckonlinetaixe.Constant.id_kh;
import static com.example.user.truckonlinetaixe.Constant.id_kh;

public class DangKyKHActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_TENKHACHHANG = "tenkh";
    private static final String KEY_DIACHI = "diachikh";
    private static final String KEY_NAMSINH = "namsinhkh";
    private static final String KEY_SDT = "sdtkh";
    private static final String KEY_USERNAME = "usernamekh";
    private static final String KEY_PASSWORD = "passwordkh";
    private static final String KEY__TOKEN = "token";
    private static final String KEY_EMPTY = "";
    private static final String KEY_IDKH = "id_khachhang";
    private EditText etTenKH;
    private EditText etDiaChi;
    private EditText etNamSinh;
    private EditText etSDT;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;

    private String tenkh;
    private String diachi;
    private String namsinh;
    private String sdt;
    private String usernamekh;
    private String passwordkh;
    private String confirmPassword;

    private ProgressDialog pDialog;
    private String register_url = URL_BASE+ API_DANG_KY_KHACH_HANG;
    private SessionHandlerKH session;

    private ImageView iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandlerKH(getApplicationContext());
        setContentView(R.layout.activity_dang_ky_kh);

        etTenKH = findViewById(R.id.etTen_KH);
        etDiaChi = findViewById(R.id.etDiaChi_KH);
        etNamSinh = findViewById(R.id.etNamSinh_KH);
        etSDT = findViewById(R.id.etSDT_KH);
        etUsername = findViewById(R.id.etUsernameDK_KH);
        etPassword = findViewById(R.id.etPasswordkh);
        etConfirmPassword = findViewById(R.id.etConfirmPasswordkh);

        Button register = findViewById(R.id.btnDangKyKH);

        iconBack = findViewById(R.id.iconBack);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DangKyKHActivity.this, DangNhapKHActivity.class);
                startActivity(i);
                finish();
            }
        });


        //Launch Login screen when Login Button is clicked
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                usernamekh = etUsername.getText().toString().toLowerCase().trim();
                passwordkh = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                tenkh = etTenKH.getText().toString().trim();
                diachi = etDiaChi.getText().toString().trim();
                namsinh = etNamSinh.getText().toString().trim();
                sdt = etSDT.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(DangKyKHActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */

    private void loadDangNhapkh() {
        Intent i = new Intent(getApplicationContext(), DangNhapKHActivity.class);
        startActivity(i);
        finish();

    }

    private void registerUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_IDKH, id_kh);
            request.put(KEY_USERNAME, usernamekh);
            request.put(KEY_PASSWORD, passwordkh);
            request.put(KEY_TENKHACHHANG, tenkh);
            request.put(KEY_DIACHI, diachi);
            request.put(KEY_NAMSINH, namsinh);
            request.put(KEY_SDT, sdt);
            request.put(KEY__TOKEN, Constant.token);
            Log.d(getTAG(DangKyKHActivity.class),request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
//                                session.loginUser(usernamekh,tenkh,diachi,namsinh,sdt,token);
                                loadDangNhapkh();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                etUsername.setError("Username đã tồn tại !");
                                etUsername.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(tenkh)) {
            etTenKH.setError("Full Name cannot be empty");
            etTenKH.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(diachi)) {
            etDiaChi.setError("Full Name cannot be empty");
            etDiaChi.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(namsinh)) {
            etNamSinh.setError("Full Name cannot be empty");
            etNamSinh.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(sdt)) {
            etSDT.setError("Full Name cannot be empty");
            etSDT.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(usernamekh)) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(passwordkh)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!passwordkh.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}