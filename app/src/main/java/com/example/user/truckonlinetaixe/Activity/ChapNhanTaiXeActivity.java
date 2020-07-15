package com.example.user.truckonlinetaixe.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.user.truckonlinetaixe.MySingleton;
import com.example.user.truckonlinetaixe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.user.truckonlinetaixe.Connect.APIconnect.API_SEND_PUSH;
import static com.example.user.truckonlinetaixe.Connect.APIconnect.URL_BASE;
import static com.example.user.truckonlinetaixe.Constant.getTAG;

public class ChapNhanTaiXeActivity extends AppCompatActivity {

    Button hoanthanh;
    String urlSendPush = URL_BASE + API_SEND_PUSH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap_nhan_tai_xe);

         hoanthanh = (Button) findViewById(R.id.btnHoanThanh);
         hoanthanh.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

    }

    private void XacNhanHoanThanh(int idchuyenxe, String tokenKH){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn chắc chắn hoàn thành đơn hàng này ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guiThongBaoChoKhachHang(tokenKH);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    public void guiThongBaoChoKhachHang(String tokenKh) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("token", tokenKh);
            request.put("tmp", 4 );
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

    private void CapNhatTinhTrangDonHang(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(ChapNhanTaiXeActivity.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ChapNhanTaiXeActivity.this, HomeTXActivity.class));

                        }
                        else {
                            Toast.makeText(ChapNhanTaiXeActivity.this,"Lỗi",Toast.LENGTH_SHORT);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChapNhanTaiXeActivity.this,"Xảy ra lỗi",Toast.LENGTH_SHORT);



                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
//                params.put("id_khachhang", String.valueOf(idkh));
                params.put("tinhtrang","Đã hoàn thành");

                return params;

            }
        };
        requestQueue.add(stringRequest);
    }
}
