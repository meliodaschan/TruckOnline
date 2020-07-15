package com.example.user.truckonlinetaixe.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.user.truckonlinetaixe.MySingleton;
import com.example.user.truckonlinetaixe.R;
import com.example.user.truckonlinetaixe.SessionHandler.SessionHandlerKH;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.user.truckonlinetaixe.Connect.APIconnect.URL_BASE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_DANG_NHAP_KHACH_HANG;
//import static com.example.user.truckonlinetaixe.Constant.id_kh;

public class DangNhapKHActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    public static final String KEY_IDKH = "id_khachhang";
    public static final String KEY_USERNAME = "usernamekh";
    public static final String KEY_PASSWORD = "passwordkh";
    public static final String KEY_TENKH = "tenkh";
    public static final String KEY_DIACHIKH = "diachikh";
    public static final String KEY_NAMSINHKH = "namsinhkh";
    public static final String KEY_SDTKH = "sdtkh";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_EMPTY = "";
    private Button btnLogin;
    private Button btnDangKyKHH;
    private String login_url = URL_BASE+ API_DANG_NHAP_KHACH_HANG;


    private EditText etUsernamekh;
    private EditText etPasswordkh;
    private String usernamekh;
    private String passwordkh;
    private ProgressDialog pDialog;
    private SessionHandlerKH session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandlerKH(getApplicationContext());

//        if (session.isLoggedIn()) {
//            loadDashboard();
//        }
        setContentView(R.layout.activity_dang_nhap_kh);
        etUsernamekh = findViewById(R.id.etUsername_KH);
        etPasswordkh = findViewById(R.id.etPasswordDangNhap);


        Button dangky = findViewById(R.id.btnDangKyKHH);
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DangNhapKHActivity.this, DangKyKHActivity.class);
                startActivity(i);
                finish();
            }
        });
        Button login = findViewById(R.id.btnLoginKH);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                usernamekh = etUsernamekh.getText().toString().toLowerCase().trim();
                passwordkh = etPasswordkh.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });


    }
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(i);
        finish();

    }
    private void displayLoader() {
        pDialog = new ProgressDialog(DangNhapKHActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, usernamekh);
            request.put(KEY_PASSWORD, passwordkh);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {
                                session.loginUser(response.getString(KEY_IDKH),usernamekh,response.getString(KEY_TENKH),response.getString(KEY_DIACHIKH),response.getString(KEY_NAMSINHKH),response.getString(KEY_SDTKH),response.getString(KEY_TOKEN));
                                loadDashboard();

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

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(usernamekh)){
            etUsernamekh.setError("Username cannot be empty");
            etUsernamekh.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(passwordkh)){
            etPasswordkh.setError("Password cannot be empty");
            etPasswordkh.requestFocus();
            return false;
        }
        return true;
    }
}
