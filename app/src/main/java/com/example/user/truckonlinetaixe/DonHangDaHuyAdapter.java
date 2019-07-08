package com.example.user.truckonlinetaixe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DonHangDaHuyAdapter extends BaseAdapter{
    private DonHangDaHuyKHActivity context;
    private int layout;
    private ArrayList<DonHangDangChoKH> donHanglist;
    LinearLayout lndonhang;

    @Override
    public int getCount() {
        return donHanglist.size();
    }

    @Override
    public Object getItem(int position) {
        return donHanglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
//            holder.txtHoTen1 = (TextView) convertView.findViewById(R.id.textViewTenKH1);
//            holder.txtID1 = (TextView) convertView.findViewById(R.id.textViewId1);
//            holder.txtDiemDi1 = (TextView) convertView.findViewById(R.id.textViewDiemDi1);
            holder.txtDiemDen1 = (TextView) convertView.findViewById(R.id.textViewDiemDen1);
//            holder.txtGiaCuoc1 = (TextView) convertView.findViewById(R.id.textViewGiaCuoc1);
            holder.txtThoiGianKhoiHanh1 = (TextView) convertView.findViewById(R.id.textViewThoiGianKhoiHanh1);
            holder.textViewTinhTrang1 = (TextView) convertView.findViewById(R.id.textViewTinhTrang1);

//            holder.btnTuChoi1 = (Button) convertView.findViewById(R.id.btnTuChoi1);
//            holder.btnChiTiet1 = (Button) convertView.findViewById(R.id.btnChiTiet1);
            holder.lndonhang=(LinearLayout)convertView.findViewById(R.id.lndonhang) ;

            convertView.setTag(holder);

        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        DonHangDangChoKH donHang=donHanglist.get(position);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = chuyenxe.getThoiGianKhoiHanh();
//        String dateFormat = formatter.format(date);

//        holder.txtID1.setText(donHang.getID()+"");
//        holder.txtHoTen1.setText(donHang.getTenKH());
//        holder.txtDiemDi1.setText(donHang.getDiemDi());
        holder.txtDiemDen1.setText(donHang.getDiemDen());
//        holder.txtGiaCuoc1.setText(donHang.getGiaCuoc()+""+" VNĐ");
        holder.txtThoiGianKhoiHanh1.setText(donHang.getThoiGianKhoiHanh());
        holder.textViewTinhTrang1.setText((donHang.getTinhTrang()));

//
//        holder.btnTuChoi1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                XacNhanXoa(donHang.getID());
//                context.guiThongBaoChoKhachHang(donHang.getTokenKH());
//            }
//        });
        holder.lndonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                XacNhanXemChiTiet(donHang.getID());
                Intent intent =new Intent(context, ChiTietDonHangDaHuyKHActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("donHang",donHanglist.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
//    private void XacNhanXemChiTiet(int id){
//        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
//        dialogXoa.setMessage("Bạn chắc chắn xem đơn hàng này ?");
//        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                context.XemDonHang();
//            }
//        });
//        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        dialogXoa.show();
//    }


    private void XacNhanXoa(int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn chắc chắn từ chối đơn hàng này ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.XoaDonHang(id);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }


    public DonHangDaHuyAdapter(DonHangDaHuyKHActivity context, int layout, ArrayList<DonHangDangChoKH> donHanglist) {
        this.context = context;
        this.layout = layout;
        this.donHanglist = donHanglist;
    }

    private class ViewHolder {
        TextView txtHoTen1, txtDiemDi1, txtDiemDen1, txtGiaCuoc1,textViewTinhTrang1;
        TextView txtThoiGianKhoiHanh1,txtID1;
//        Button btnTuChoi1, btnChapNhan1,btnChiTiet1;
        LinearLayout lndonhang;


    }

}
