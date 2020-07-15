package com.example.user.truckonlinetaixe.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.truckonlinetaixe.Constant;
import com.example.user.truckonlinetaixe.MySingleton;
import com.example.user.truckonlinetaixe.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.user.truckonlinetaixe.Connect.APIconnect.URL_BASE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_POST_CHUYEN_XE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_CAP_NHAT_TOKEN_TX;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_CAP_NHAT_TOKEN_KH;
import static com.example.user.truckonlinetaixe.Constant.getTAG;
import static com.example.user.truckonlinetaixe.Constant.token;


public class MainActivity extends AppCompatActivity {
    Button btnThemtaixe;
    Button btnThemkhachhang;
    String urlPostChuyenXe = URL_BASE+ API_POST_CHUYEN_XE;
    String urlCapNhatTokenTX = URL_BASE+ API_CAP_NHAT_TOKEN_TX;
    String urlCapNhatTokenKH = URL_BASE+ API_CAP_NHAT_TOKEN_KH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(getTAG(MainActivity.class), "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    Constant.token = token;
                    registerUser(token);
                });

        btnThemtaixe=(Button)findViewById(R.id.btnThemtaixe);
        btnThemtaixe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapNhatTokenTX(urlCapNhatTokenTX);
                Intent intent =new Intent(MainActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });

        btnThemkhachhang=(Button)findViewById(R.id.btnThemkhachhang);
        btnThemkhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapNhatTokenKH(urlCapNhatTokenKH);
                Intent intent =new Intent(MainActivity.this,DangNhapKHActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser(String token) {

        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("token", token);

            Log.d(getTAG(MainActivity.class),request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, urlPostChuyenXe, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),
                                "dang ky token thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("/////",error.getMessage());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private void CapNhatTokenTX(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Log.d("TokenTaiXe",token);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Lỗi",Toast.LENGTH_SHORT);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Xảy ra lỗi",Toast.LENGTH_SHORT);

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CapNhatTokenKH(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Log.d("TokenKH",token);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Lỗi",Toast.LENGTH_SHORT);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Xảy ra lỗi",Toast.LENGTH_SHORT);

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
