package com.example.user.truckonlinetaixe.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.truckonlinetaixe.Model.DonHangDangChoKH;
import com.example.user.truckonlinetaixe.Model.KhachHang;
import com.example.user.truckonlinetaixe.MySingleton;
import com.example.user.truckonlinetaixe.R;
import com.example.user.truckonlinetaixe.SessionHandler.SessionHandlerDonHang;
import com.example.user.truckonlinetaixe.SessionHandler.SessionHandlerKH;
import com.example.user.truckonlinetaixe.Adapter.YeuCauBaoGiaKHAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.user.truckonlinetaixe.Connect.APIconnect.URL_BASE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_DON_HANG_KH_CHO_DUYET;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_HUY_CHUYEN_XE_DANG_CHO;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_XEM_CHI_TIET;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_SEND_PUSH;
import static com.example.user.truckonlinetaixe.Constant.getTAG;


public class YeuCauBaoGiaKHActivity extends AppCompatActivity {
    String urlGetData = URL_BASE + API_DON_HANG_KH_CHO_DUYET;
    String urlHuyChuyenXe = URL_BASE + API_HUY_CHUYEN_XE_DANG_CHO;
    String urlXemChiTiet = URL_BASE + API_XEM_CHI_TIET;
    private SessionHandlerKH session;

    String urlSendPush = URL_BASE + API_SEND_PUSH;
    ListView lvChuyenXe1;
    ArrayList<DonHangDangChoKH> mangCX = new ArrayList<DonHangDangChoKH>();
    YeuCauBaoGiaKHAdapter adapter;
    SessionHandlerDonHang sessiondh;
    private Context context;
    private ArrayList<String> donHanglist;

    DonHangDangChoKH donhangcanxoa;
    String tokenkh;
    Button btnChiTiet1;
    String tmp ="2";
    String tmp3 ="3";

    public static Handler mHandlerThread2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView imgBack;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_yeucaubaogia);
        context = this;
//        adapter.notifyDataSetChanged();

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YeuCauBaoGiaKHActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        lvChuyenXe1 = (ListView) findViewById(R.id.listViewChuyenXe1);

        GetDataDonHang(urlGetData);


        mHandlerThread2 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(YeuCauBaoGiaKHActivity.this, msg.arg2+" ", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                    }
                }, 2000);
            }
        };

    }

    public void HuyDonHang(int idchuyenxe) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlHuyChuyenXe,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(YeuCauBaoGiaKHActivity.this, "Hủy thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(YeuCauBaoGiaKHActivity.this, YeuCauBaoGiaKHActivity.class));
//                            Intent intent =new Intent(context, YeuCauBaoGiaKHActivity.class);
//
//                            context.startActivity(intent);
                            GetDataDonHang(urlGetData);
                        } else {
                            Toast.makeText(YeuCauBaoGiaKHActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(YeuCauBaoGiaKHActivity.this, "Loi phia api", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_chuyenxe", String.valueOf(idchuyenxe));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void guiThongBaoChoKhachHang(String tokenKh) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("token", tokenKh);
            request.put("tmp", tmp );
            Log.d(getTAG(MapsActivity.class), request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, urlSendPush, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    public void guiThongBaoChoKhachHang2(String tokenKh) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("token",  tokenKh);
            request.put("tmp", tmp3 );
            Log.d(getTAG(MapsActivity.class),request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, urlSendPush, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }


    public void GetDataDonHang(String url) {
        String idkh;
        session = new SessionHandlerKH(getApplicationContext());
        KhachHang user = session.getUserDetails();
        idkh=user.getId_khachhang();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("id_khachhang", idkh);

            Log.d(getTAG(YeuCauBaoGiaKHActivity.class), request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                mangCX.add(new DonHangDangChoKH(
                                        object.getInt("ID"),
                                        object.getInt("IDXe"),
                                        object.getInt("IDKH"),
                                        object.getString("TokenKH"),
                                        object.getString("TokenTX"),
                                        object.getString("TenKH"),
                                        object.getString("SdtKH"),
                                        object.getString("TenChuXe"),
                                        object.getString("SdtTx"),
                                        object.getString("BienSoXe"),
                                        object.getString("LoaiXe"),
                                        object.getString("TrongTaiXe"),
                                        object.getString("TenHang"),
                                        object.getString("LoaiHang"),
                                        object.getString("DiemDi"),
                                        object.getString("DiemDen"),
                                        object.getInt("GiaCuoc"),
                                        object.getInt("TrongLuongHang"),
                                        object.getString("ThoiGianKhoiHanh"),
                                        object.getString("TinhTrang"),
                                        object.getString("TaiKhoan")
                                ));

                                adapter=new YeuCauBaoGiaKHAdapter(YeuCauBaoGiaKHActivity.this, R.layout.activity_yeucaubaogia_kh,mangCX);
                                lvChuyenXe1.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d(getTAG(DonHangDangChoKHActivity.class), error.getLocalizedMessage());
                    }
                });
//        requestQueue.add(jsonArrayRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

}


