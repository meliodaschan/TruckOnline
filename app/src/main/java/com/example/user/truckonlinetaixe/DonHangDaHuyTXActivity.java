package com.example.user.truckonlinetaixe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.user.truckonlinetaixe.Constant.URL_BASE;
import static com.example.user.truckonlinetaixe.Constant.getTAG;


public class DonHangDaHuyTXActivity extends AppCompatActivity {
    String urlGetData = URL_BASE + "doan/getdonhangtxdahuy.php";
    String urlXoaChuyenXe = URL_BASE + "doan/xoachuyenxe.php";
    String urlXemChiTiet = URL_BASE + "doan/xemchitiet.php";
    String urlSendPush = URL_BASE + "doan/send_push.php";
    private  SessionHandler session;

    ListView lvChuyenXe1;
    ArrayList<DonHangDangChoKH> mangCX = new ArrayList<DonHangDangChoKH>();
    DonHangDaHuyTXAdapter adapter;
    SessionHandlerDonHang sessiondh;
    private Context context;
    private ArrayList<String> donHanglist;

    int idchuyenxecanxoa;
    String tokenkh;
    Button btnChiTiet1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView imgBack;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_donhang);
        context = this;
//        adapter.notifyDataSetChanged();

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonHangDaHuyTXActivity.this, HomeTXActivity.class);
                startActivity(intent);
            }
        });


        lvChuyenXe1 = (ListView) findViewById(R.id.listViewChuyenXe1);
//        Putdata(urlGetData);
//        btnChiTiet1=(Button)findViewById(R.id.btnChiTiet1);
//        btnChiTiet1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(DonHangActivity.this,DonHangActivity.class);
//                startActivity(intent);
//            }
//        });
        lvChuyenXe1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, donHanglist.get(position), Toast.LENGTH_SHORT).show();
//                Intent intent =new Intent(DonHangActivity.this,DangNhapActivity.class);
//                startActivity(intent);

            }
        });
//                Putdata(urlGetData);
        GetDataDonHang(urlGetData);


//        lvChuyenXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                tokenkh = mangCX.get(position).getToken();
//
//                }
//            });

    }

    public void XoaDonHang(int idchuyenxe) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlXoaChuyenXe,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(DonHangDaHuyTXActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                            GetDataDonHang(urlGetData);
                        } else {
                            Toast.makeText(DonHangDaHuyTXActivity.this, "Loi xoa", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DonHangDaHuyTXActivity.this, "Loi phia api", Toast.LENGTH_SHORT).show();
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

//        public void XemDonHang() {
//            Intent intent =new Intent(DonHangActivity.this,ChiTietDonHangMain.class);
//            startActivity(intent);
//        }

    public void guiThongBaoChoKhachHang(String tokenKh) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("token", tokenKh);
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

    private void Putdata(String urlGetData) {
        String idkh = "4";


        JSONObject request = new JSONObject();
        try {

            //Populate the request parameters
//            request.put("id_khachhang",id_kh);
            request.put("id_khachhang", idkh);

//            request.put(KEY__TOKEN,  Constant.token);
            Log.d(getTAG(MapsActivity.class), request.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, this.urlGetData, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getInt("status") == 0) {
                                sessiondh.postDonHang(idkh);

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        GetTaiXeGanNhat(urlGetDiaDiemTaiXe);
                        Toast.makeText(DonHangDaHuyTXActivity.this, "Thành công !!!", Toast.LENGTH_SHORT).show();


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
        String biensoxe;
        session = new SessionHandler(getApplicationContext());
        TaiXe user = session.getUserDetails();
        biensoxe=user.getBiensoxe();

        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("biensoxe", biensoxe);

            Log.d(getTAG(DonHangDaHuyTXActivity.class), request.toString());
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

                                adapter=new DonHangDaHuyTXAdapter(DonHangDaHuyTXActivity.this, R.layout.activity_donhang_dahoanthanh,mangCX);
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
                        Log.d(getTAG(DonHangDaHuyTXActivity.class), error.getLocalizedMessage());
                    }
                });
//        requestQueue.add(jsonArrayRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

}


