package com.example.user.truckonlinetaixe.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.truckonlinetaixe.Adapter.DonHangAdapter;
import com.example.user.truckonlinetaixe.Model.DonHangDangChoKH;
import com.example.user.truckonlinetaixe.R;

import java.util.HashMap;
import java.util.Map;

import static com.example.user.truckonlinetaixe.Connect.APIconnect.URL_BASE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_XOA_CHUYEN_XE;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_HUY_CHUYEN_XE_DANG_CHO;
import static com.example.user.truckonlinetaixe.Constant.getTAG;


public class ChiTietDonHangDaHoanThanhTXActivity extends AppCompatActivity {
    String urlXoaChuyenXe = URL_BASE + API_XOA_CHUYEN_XE;

    int idchuyenxedangcho=0;
    final Context context = this;
    DonHangAdapter adapter;
    DonHangDangChoKHActivity a;



    String urlHuyDonHangDangCho=URL_BASE+API_HUY_CHUYEN_XE_DANG_CHO;

    TextView  textViewTenKH,textViewDiemDi,textViewDiemDen,textViewGiaCuoc,
            textViewThoiGianKhoiHanh,textViewLoaiXe,textViewTenHang,
            textViewLoaiHang,textViewTrongLuongHang,btnHuyDangCho,btnXoaDangCho;
    ImageView imgBack;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();

        setContentView(R.layout.activity_chitietdonhang_dahoanthanh);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietDonHangDaHoanThanhTXActivity.this, DonHangDaHoanThanhTXActivity.class);
                startActivity(intent);
            }
        });
        textViewTenKH=(TextView)findViewById(R.id.textViewTenKH);
        textViewLoaiXe=(TextView)findViewById(R.id.textViewLoaiXe);
        textViewTenHang=(TextView)findViewById(R.id.textViewTenHang);
        textViewLoaiHang=(TextView)findViewById(R.id.textViewLoaiHang);
        textViewTrongLuongHang=(TextView)findViewById(R.id.textViewTrongLuongHang);
        textViewDiemDi=(TextView)findViewById(R.id.textViewDiemDi);
        textViewDiemDen=(TextView)findViewById(R.id.textViewDiemDen);
        textViewGiaCuoc=(TextView)findViewById(R.id.textViewGiaCuoc);
        textViewThoiGianKhoiHanh=(TextView)findViewById(R.id.textViewThoiGianKhoiHanh);
        DonHangDangChoKH dh=  (DonHangDangChoKH) getIntent().getExtras().getSerializable("donHang");
        textViewTenKH.setText(dh.getTenKH());
        textViewLoaiXe.setText(dh.getTenHang()+" | "+dh.getSdtKH());
        textViewDiemDi.setText(dh.getDiemDi());
        textViewTenHang.setText(dh.getTenHang());
        textViewLoaiHang.setText(dh.getLoaiHang());
        textViewTrongLuongHang.setText(dh.getTrongLuongHang()+"");
        textViewDiemDen.setText(dh.getDiemDen());
        textViewGiaCuoc.setText(dh.getGiaCuoc()+""+" VNĐ");
        textViewThoiGianKhoiHanh.setText(dh.getThoiGianKhoiHanh());
//        btnHuyDangCho=(Button)findViewById(R.id.btnHuyDangCho);
        btnXoaDangCho=(Button)findViewById(R.id.btnXoaDangCho);



        idchuyenxedangcho=dh.getID();
//        btnHuyDangCho.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                XacNhanHuy();            }
//        });
       btnXoaDangCho.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               XacNhanXoa();



           }
       });

    }
    private void XacNhanXoa(){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn chắc chắn xóa đơn hàng này ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XoaDonHang(urlXoaChuyenXe);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
    public void XoaDonHang(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(ChiTietDonHangDaHoanThanhTXActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ChiTietDonHangDaHoanThanhTXActivity.this,DonHangDaHoanThanhKHActivity.class));
                        } else {

                            Toast.makeText(ChiTietDonHangDaHoanThanhTXActivity.this, "Loi xoa", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(getTAG(DonHangDangChoKHActivity.class), error.getLocalizedMessage());
//                        Toast.makeText(ChiTietDonHangDangChoKHActivity.this, "Loi phia api", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id="46";
                Map<String, String> params = new HashMap<>();
                params.put("id_chuyenxe",String.valueOf(idchuyenxedangcho) );
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void XacNhanHuy(){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn chắc chắn hủy đơn hàng này ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HuyDonHangDangCho(urlHuyDonHangDangCho);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        dialogXoa.show();
    }

    private void HuyDonHangDangCho(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(ChiTietDonHangDaHoanThanhTXActivity.this, "Đã hủy thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChiTietDonHangDaHoanThanhTXActivity.this,DonHangDaHoanThanhKHActivity.class));

                }
                else {
                    Toast.makeText(ChiTietDonHangDaHoanThanhTXActivity.this,"Lỗi",Toast.LENGTH_SHORT);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChiTietDonHangDaHoanThanhTXActivity.this,"Xảy ra lỗi",Toast.LENGTH_SHORT);



                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id_chuyenxe", String.valueOf(idchuyenxedangcho));
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }


}
