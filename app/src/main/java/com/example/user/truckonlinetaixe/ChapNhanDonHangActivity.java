package com.example.user.truckonlinetaixe;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.user.truckonlinetaixe.Constant.URL_BASE;

public class ChapNhanDonHangActivity extends AppCompatActivity {

    int idchuyenxedangcho=0;
    final Context context = this;
    DonHangAdapter adapter;
    DonHangDangChoKHActivity a;
    TextView textViewTenKH,textViewDiemDi,textViewDiemDen,textViewGiaCuoc,
            textViewThoiGianKhoiHanh,textViewLoaiXe,textViewTenHang,
            textViewLoaiHang,textViewTrongLuongHang;

    public static Handler mHandlerThread4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap_nhan_don_hang);

//        textViewTenKH=(TextView)findViewById(R.id.textViewTenKH);
//        textViewLoaiXe=(TextView)findViewById(R.id.textViewLoaiXe);
//        textViewTenHang=(TextView)findViewById(R.id.textViewTenHang);
//        textViewLoaiHang=(TextView)findViewById(R.id.textViewLoaiHang);
//        textViewTrongLuongHang=(TextView)findViewById(R.id.textViewTrongLuongHang);
//        textViewDiemDi=(TextView)findViewById(R.id.textViewDiemDi);
//        textViewDiemDen=(TextView)findViewById(R.id.textViewDiemDen);
//        textViewGiaCuoc=(TextView)findViewById(R.id.textViewGiaCuoc);
//        textViewThoiGianKhoiHanh=(TextView)findViewById(R.id.textViewThoiGianKhoiHanh);
//        DonHangDangChoKH dh=  (DonHangDangChoKH) getIntent().getExtras().getSerializable("donHang");
//        textViewTenKH.setText(dh.getTenTaiXe());
//        textViewLoaiXe.setText(dh.getLoaiXe()+" | "+dh.getBienSoXe());
//        textViewDiemDi.setText(dh.getDiemDi());
//        textViewTenHang.setText(dh.getTenHang());
//        textViewLoaiHang.setText(dh.getLoaiHang());
//        textViewTrongLuongHang.setText(dh.getTrongLuongHang()+"");
//        textViewDiemDen.setText(dh.getDiemDen());
//        textViewGiaCuoc.setText(dh.getGiaCuoc()+""+" VNĐ");
//        textViewThoiGianKhoiHanh.setText(dh.getThoiGianKhoiHanh());
//        idchuyenxedangcho=dh.getID();

        mHandlerThread4 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(ChapNhanDonHangActivity.this, msg.arg1+" ", Toast.LENGTH_SHORT).show();

            }
        };
    }
}
