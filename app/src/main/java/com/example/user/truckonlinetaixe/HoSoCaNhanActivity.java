package com.example.user.truckonlinetaixe;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.user.truckonlinetaixe.Constant.URL_BASE;


public class HoSoCaNhanActivity extends AppCompatActivity {
    String urlCapNhatHoSoKH=URL_BASE+"doan/capnhathosokh.php";

    SessionHandlerKH sessionkh;
    String idkh="";
    Button btnCapNhatKH;
    EditText edtTenKH,edtDiaChiKH,edtNamSinhKH,edtSdtKH;
    ImageView imgBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosocanhan);

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HoSoCaNhanActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        edtTenKH=(EditText)findViewById(R.id.edtTenKH);
        edtDiaChiKH=(EditText)findViewById(R.id.edtDiaChiKH);
        edtNamSinhKH=(EditText)findViewById(R.id.edtNamSinhKH);
        edtSdtKH=(EditText)findViewById(R.id.edtSdtKH);
        sessionkh = new SessionHandlerKH(getApplicationContext());
        KhachHang kh = sessionkh.getUserDetails();
        edtSdtKH.setText(kh.getSdtkh()+"");
        edtTenKH.setText(kh.getTenkh());
        edtNamSinhKH.setText(kh.getNamsinhkh()+"");
        edtDiaChiKH.setText(kh.getDiachikh());
        idkh=kh.getId_khachhang();
/*
id_kh = kh.getId_khachhang();
Log.d("//////",id_kh+"");
String tokennnnnn = user.getToken();
Log.d("//////",tokennnnnn+"");
*/
        btnCapNhatKH=(Button)findViewById(R.id.btnCapNhatKH);
        btnCapNhatKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapNhatHoSoKH(urlCapNhatHoSoKH);
            }
        });





    }
    private void CapNhatHoSoKH(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(HoSoCaNhanActivity.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HoSoCaNhanActivity.this,MapsActivity.class));

                        }
                        else {
                            Toast.makeText(HoSoCaNhanActivity.this,"Lỗi",Toast.LENGTH_SHORT);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HoSoCaNhanActivity.this,"Xảy ra lỗi",Toast.LENGTH_SHORT);



                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id_khachhang", String.valueOf(idkh));
                params.put("tenkh",edtTenKH.getText().toString().trim());
                params.put("diachikh",edtDiaChiKH.getText().toString().trim());
                params.put("namsinhkh",edtNamSinhKH.getText().toString().trim());
                params.put("sdtkh",edtSdtKH.getText().toString().trim());

                return params;

            }
        };
        requestQueue.add(stringRequest);
    }
}
