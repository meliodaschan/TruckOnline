package com.example.user.truckonlinetaixe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.truckonlinetaixe.Model.LoaiXe;
import com.example.user.truckonlinetaixe.R;
import com.example.user.truckonlinetaixe.SessionHandler.SessionHandler;
import com.example.user.truckonlinetaixe.Model.TaiXe;
import com.example.user.truckonlinetaixe.Model.TrongTaiXe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_LOAI_XE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_TRONG_TAI_XE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.URL_BASE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_CAP_NHAT_HO_SO_TX;


public class HoSoCaNhanTXActivity extends AppCompatActivity {
    String urlCapNhatHoSoTX=URL_BASE+ API_CAP_NHAT_HO_SO_TX;
    Spinner spnLoaiXe;
    Spinner spnTrongTaiXe;
    String tenloaixe;
    String trongtai;
    SessionHandler sessiontx;
    String biensoxe="";
    Button btnCapNhatTX;
    EditText edtTenChuXe,edtSDT,edtMoTa,edtBienSoXe;
    ImageView imgBack;
    private String loaixe;
    private String trongtaixe;
    TextView txttaikhoan;

    String urlGetDataLoaiXe = URL_BASE+ API_LOAI_XE;
    String urlGetDataTrongTaiXe = URL_BASE+ API_TRONG_TAI_XE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosocanhan_tx);
        GetDataLoaiXe(urlGetDataLoaiXe);
        spnLoaiXe = (Spinner)findViewById(R.id.spnLoaiXedk);
        txttaikhoan=(TextView)findViewById(R.id.txttaikhoan);

        GetDataTrongTaiXe(urlGetDataTrongTaiXe);
        spnTrongTaiXe = (Spinner)findViewById(R.id.spnTrongTaiXedk);

        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HoSoCaNhanTXActivity.this, HomeTXActivity.class);
                startActivity(intent);
            }
        });
        edtTenChuXe=(EditText)findViewById(R.id.edtTenChuXe);
        edtSDT=(EditText)findViewById(R.id.edtSDT);
        edtMoTa=(EditText)findViewById(R.id.edtMoTa);
        edtBienSoXe=(EditText)findViewById(R.id.edtBienSoXe);
        sessiontx = new SessionHandler(getApplicationContext());
        TaiXe kh = sessiontx.getUserDetails();
        edtTenChuXe.setText(""+kh.getTenchuxe());
        edtSDT.setText(""+kh.getSdttx());
        edtMoTa.setText(""+kh.getMota());
        edtBienSoXe.setText("Biển số xe: "+kh.getBiensoxe());
        edtBienSoXe.setEnabled(false);
        biensoxe=kh.getBiensoxe();
        txttaikhoan.setText("Tài khoản: "+kh.getTaikhoan());
/*
id_kh = kh.getId_khachhang();
Log.d("//////",id_kh+"");
String tokennnnnn = user.getToken();
Log.d("//////",tokennnnnn+"");
*/
        btnCapNhatTX=(Button)findViewById(R.id.btnCapNhatTX);
        btnCapNhatTX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaixe = tenloaixe;
                trongtaixe = trongtai;
                CapNhatHoSotx(urlCapNhatHoSoTX);
            }
        });





    }
    private void CapNhatHoSotx(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(HoSoCaNhanTXActivity.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HoSoCaNhanTXActivity.this,HomeTXActivity.class));

                        }
                        else {
                            Toast.makeText(HoSoCaNhanTXActivity.this,"Lỗi",Toast.LENGTH_SHORT);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HoSoCaNhanTXActivity.this,"Xảy ra lỗi",Toast.LENGTH_SHORT);



                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("loaixe", loaixe);
                params.put("trongtaixe",trongtaixe);
                params.put("biensoxe", String.valueOf(biensoxe));
                params.put("tenchuxe",edtTenChuXe.getText().toString().trim());
                params.put("sdttx",edtSDT.getText().toString().trim());
                params.put("mota",edtMoTa.getText().toString().trim());

                return params;

            }
        };
        requestQueue.add(stringRequest);
    }
    private void GetDataLoaiXe(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String result =  response.toString();
                        final List<LoaiXe> loaiXeList = new ArrayList<>();
                        List<String> loaiXeName = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                loaiXeList.add(new LoaiXe( explrObject.getInt("ID_lx"), explrObject.getString("TenLoaiXe")));
                                loaiXeName.add(explrObject.getString("TenLoaiXe"));
                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item, loaiXeName);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnLoaiXe.setAdapter(arrayAdapter);
                            spnLoaiXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    tenloaixe =loaiXeList.get(position).getTenLoaiXe();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HoSoCaNhanTXActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    private void GetDataTrongTaiXe(String url){
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String result =  response.toString();
                        final List<TrongTaiXe> trongTaiXeList = new ArrayList<>();
                        List<String> trongTaiXeName = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                trongTaiXeList.add(new TrongTaiXe( explrObject.getInt("ID_ttx"), explrObject.getString("TrongTai")));
                                trongTaiXeName.add(explrObject.getString("TrongTai"));
                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item, trongTaiXeName);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnTrongTaiXe.setAdapter(arrayAdapter);
                            spnTrongTaiXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    trongtai =trongTaiXeList.get(position).getTrongTai();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HoSoCaNhanTXActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

}
