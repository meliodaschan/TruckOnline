package com.example.user.truckonlinetaixe;

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

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.user.truckonlinetaixe.Constant.URL_BASE;

public class DangNhapActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_IDXE = "id_xe";
    public static final String KEY_BIENSOXE = "biensoxe";
    public static final String KEY_PASSWORDTX = "passwordtx";
    public static final String KEY_LOAIXE = "loaixe";
    public static final String KEY_TRONGTAIXE = "trongtaixe";
    public static final String KEY_TENCHUXE = "tenchuxe";
    public static final String KEY_SDTTX = "sdttx";
    public static final String KEY_MOTA = "mota";
    private static final String KEY__TOKEN = "token";
    private static final String KEY_TAIKHOAN = "taikhoan";
    private static final String KEY_EMPTY = "";

    private String login_url = URL_BASE+"doan/logintx.php";


    private EditText etBiensoxe;
    private EditText etPasswordtx;
    private String biensoxe;
    private String passwordtx;
    private ProgressDialog pDialog;
    private SessionHandler session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
//
//        if (session.isLoggedIn()) {
//            loadDashboard();
//        }
        setContentView(R.layout.activity_dang_nhap);
        etBiensoxe = findViewById(R.id.etBienSoXeDangNhap);
        etPasswordtx = findViewById(R.id.etPasswordDangNhapTX);


        Button dangky = findViewById(R.id.btnDangKyTX);
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(i);
                finish();
            }
        });
        Button login = findViewById(R.id.btnLoginTX);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                biensoxe = etBiensoxe.getText().toString().toLowerCase().trim();
                passwordtx = etPasswordtx.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });


    }
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), HomeTXActivity.class);
        startActivity(i);
        finish();
    }
    private void displayLoader() {
        pDialog = new ProgressDialog(DangNhapActivity.this);
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
            request.put(KEY_BIENSOXE, biensoxe);
            request.put(KEY_PASSWORDTX, passwordtx);

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
                                session.loginUser(response.getString(KEY_IDXE),biensoxe,response.getString(KEY_LOAIXE),response.getString(KEY_TRONGTAIXE),response.getString(KEY_TENCHUXE),response.getString(KEY_SDTTX),response.getString(KEY_MOTA),response.getString(KEY__TOKEN),response.getString(KEY_TAIKHOAN));
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
        if(KEY_EMPTY.equals(biensoxe)){
            etBiensoxe.setError("Username cannot be empty");
            etBiensoxe.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(passwordtx)){
            etPasswordtx.setError("Password cannot be empty");
            etPasswordtx.requestFocus();
            return false;
        }
        return true;
    }
}
